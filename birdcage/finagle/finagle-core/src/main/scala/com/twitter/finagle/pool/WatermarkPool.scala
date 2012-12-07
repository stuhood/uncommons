package com.twitter.finagle.pool

import com.twitter.finagle.stats.{NullStatsReceiver, StatsReceiver}
import com.twitter.finagle.{CancelledConnectionException, ClientConnection, 
  Service, ServiceClosedException, ServiceFactory, ServiceProxy, TooManyWaitersException}
import com.twitter.util.{Future, Promise, Return, Throw}
import java.util.ArrayDeque
import scala.annotation.tailrec
import scala.collection.JavaConverters._

/**
 * The watermark pool is an object pool with low & high
 * watermarks. It keeps the number of services from a given service
 * factory in a certain range.
 *
 * This behaves as follows: the pool will persist up to
 * the low watermark number of items (as long as they have been
 * created), and won't start queueing requests until the high
 * watermark has been reached. Put another way: up to `lowWatermark'
 * items may persist indefinitely, while there are at no times more
 * than `highWatermark' items in concurrent existence.
 */
class WatermarkPool[Req, Rep](
    factory: ServiceFactory[Req, Rep],
    lowWatermark: Int, highWatermark: Int = Int.MaxValue,
    statsReceiver: StatsReceiver = NullStatsReceiver,
    maxWaiters: Int = Int.MaxValue)
  extends ServiceFactory[Req, Rep]
{
  private[this] val queue       = new ArrayDeque[ServiceWrapper]()
  private[this] val waiters     = new ArrayDeque[Promise[Service[Req, Rep]]]()
  private[this] var numServices = 0
  @volatile private[this] var isOpen      = true

  private[this] val waitersStat = statsReceiver.addGauge("pool_waiters") { synchronized { waiters.size } }
  private[this] val sizeStat = statsReceiver.addGauge("pool_size") { synchronized { numServices } }

  /**
   * Flush waiters by creating new services for them. This must
   * be called whenever we decrease the service count.
   */
  private[this] def flushWaiters() = synchronized {
    while (numServices < highWatermark && !waiters.isEmpty) {
      val waiter = waiters.removeFirst()
      waiter.become(this())
    }
  }

  private[this] class ServiceWrapper(underlying: Service[Req, Rep])
    extends ServiceProxy[Req, Rep](underlying)
  {
    override def release() = {
      val releasable = WatermarkPool.this.synchronized {
        if (!isOpen) {
          numServices -= 1
          true
        } else if (!isAvailable) {
          numServices -= 1
          // If we just disposed of an service, and this bumped us beneath
          // the high watermark, then we are free to satisfy the first
          // waiter.
          flushWaiters()
          true
        } else if (!waiters.isEmpty) {
          val waiter = waiters.removeFirst()
          waiter() = Return(this)
          false
        } else if (numServices <= lowWatermark) {
          queue.addLast(this)
          false
        } else {
          numServices -= 1
          true
        }
      }

      if (releasable)
        underlying.release()
    }
  }

  @tailrec private[this] def dequeue(): Option[Service[Req, Rep]] = {
    if (queue.isEmpty) {
      None
    } else {
      val service = queue.removeFirst()
      if (!service.isAvailable) {
        // Note: since these are ServiceWrappers, accounting is taken
        // care of by ServiceWrapper.release()
        service.release()
        dequeue()
      } else {
        Some(service)
      }
    }
  }

  def apply(conn: ClientConnection): Future[Service[Req, Rep]] = {
    if (!isOpen)
      return Future.exception(new ServiceClosedException)

    synchronized {
      dequeue() match {
        case Some(service) =>
          return Future.value(service)
        case None if numServices < highWatermark =>
          numServices += 1
        case None if waiters.size >= maxWaiters =>
          return Future.exception(new TooManyWaitersException)
        case None =>
          val p = new Promise[Service[Req, Rep]]
          waiters.addLast(p)
          p.setInterruptHandler { case _cause =>
            // TODO: use cause
            if (WatermarkPool.this.synchronized(waiters.remove(p)))
              p.setException(new CancelledConnectionException)
          }
          return p
      }
    }

    // If we reach this point, we've committed to creating a service
    // (numServices was increased by one).
    factory(conn) onFailure { _ =>
      synchronized {
        numServices -= 1
        flushWaiters()
      }
    } map { new ServiceWrapper(_) }
  }

  def close() = synchronized {
    // Mark the pool closed, relinquishing completed requests &
    // denying the issuance of further requests. The order here is
    // important: we mark the service unavailable before releasing the
    // individual channels so that they are actually released in the
    // wrapper.
    isOpen = false

    // Drain the pool.
    queue.asScala foreach { _.release() }
    queue.clear()

    // Kill the existing waiters.
    waiters.asScala foreach { _() = Throw(new ServiceClosedException) }
    waiters.clear()

    // Close the underlying factory.
    factory.close()
  }

  override def isAvailable = isOpen && factory.isAvailable

  override val toString = "watermark_pool_%s".format(factory.toString)
}
