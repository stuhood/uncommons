package com.twitter.finagle.service

import java.net.ConnectException
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{ConcurrentLinkedQueue}
import com.twitter.finagle.{FailFastException, WriteException, ServiceFactory, ServiceFactoryProxy, ClientConnection}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.util.{Timer, TimerTask, Future, Duration, Time, Throw, Return}
import com.twitter.conversions.time._

class Chan[T](iteratee: T => Unit) {
  private[this] val q = new ConcurrentLinkedQueue[T]
  private[this] val nq = new AtomicInteger(0)

  def !(elem: T) {
    q.offer(elem)
    if (nq.getAndIncrement() == 0)
      do iteratee(q.remove()) while (nq.decrementAndGet() > 0)
  }
}

object Chan {
  def apply[T](iteratee: T => Unit): Chan[T] = new Chan(iteratee)
}

/*

add jitter?



*/

private[finagle] object FailFastFactory {
  sealed trait State
  case object Ok extends State
  case class Retrying(since: Time, task: TimerTask, ntries: Int, backoffs: Stream[Duration]) extends State
 
  object Observation extends Enumeration {
    type t = Value
    val Success, Fail, Timeout, TimeoutFail = Value
  }
  
  val defaultBackoffs = Backoff.exponential(1.second, 2) take 5
}

private[finagle] class FailFastFactory[Req, Rep](
  self: ServiceFactory[Req, Rep],
  statsReceiver: StatsReceiver,
  timer: Timer,
  backoffs: Stream[Duration] = FailFastFactory.defaultBackoffs
) extends ServiceFactoryProxy(self) {
  import FailFastFactory._
  
  @volatile private[this] var state: State = Ok
  var ch: Chan[Observation.t] = _
  ch = Chan[Observation.t] (/*{ x:Observation.t => println("obs", state, self, x); x} andThen*/ {
    case Observation.Success if state != Ok=>
      val Retrying(_, task, _, _) = state
      task.cancel()
      state = Ok

    case Observation.Fail if state == Ok =>
      val wait #:: rest = backoffs
      val task = timer.schedule(Time.now + wait) { ch ! Observation.Timeout }
      state = Retrying(Time.now, task, 0, rest)

    case Observation.TimeoutFail if state != Ok =>
      state match {
        case Retrying(_, _, _, Stream.Empty) =>
          state = Ok

        case Retrying(since, _, ntries, wait #:: rest) =>
          val task = timer.schedule(Time.now + wait) { ch ! Observation.Timeout }
          state = Retrying(since, task, ntries+1, rest)
          
        case Ok => assert(false)
      }

    case Observation.Timeout if state != Ok =>
      self(ClientConnection.nil) respond {
        case Throw(exc) => ch ! Observation.TimeoutFail
        case Return(service) =>
          ch ! Observation.Success
          service.release()
      }

    case _ => ()
  })

  private[this] val unhealthyForMsGauge =
    statsReceiver.addGauge("unhealthy_for_ms") {
      state match {
        case Retrying(since, _, _, _) => since.untilNow.inMilliseconds
        case _ => 0
      }
    }

  private[this] val unhealthyNumRetriesGauge = 
    statsReceiver.addGauge("unhealthy_num_tries") {
      state match {
        case Retrying(_, _, ntries, _) => ntries
        case _ => 0
      }
    }

  override def apply(conn: ClientConnection) =
    self(conn) respond {
      case Throw(exc) => ch ! Observation.Fail
      case Return(_) => ch ! Observation.Success
    }

  override def isAvailable = self.isAvailable && state == Ok

  override val toString = "fail_fast_%s".format(self.toString)
}
