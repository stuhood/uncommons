package com.twitter.finagle.service

import java.net.ConnectException
import java.util.concurrent.atomic.AtomicInteger
import com.twitter.finagle.{FailFastException, WriteException, ServiceFactory, ServiceFactoryProxy, ClientConnection}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.util.{Timer, Future, Duration, Time}
import com.twitter.conversions.time._

private[finagle] object FailFastFactory {
  sealed trait State
  case object Ok extends State
  case class Retrying(since: Time, ntries: Int, backoffs: Stream[Duration]) extends State
  
  val defaultBackoffs = Backoff.exponential(1.second, 2).take(6)
}

private[finagle] class FailFastFactory[Req, Rep](
  self: ServiceFactory[Req, Rep],
  statsReceiver: StatsReceiver,
  timer: Timer,
  backoffs: Stream[Duration] = FailFastFactory.defaultBackoffs
    // Arbitrary but reasonable:
    
) extends ServiceFactoryProxy(self) {
  import FailFastFactory._

  @volatile private[this] var state: State = Ok
  
  private[this] val unhealthyForMsGauge =
    statsReceiver.addGauge("unhealthy_for_ms") {
      state match {
        case Retrying(since, _, _) => since.untilNow.inMilliseconds
        case _ => 0
      }
    }

  private[this] val unhealthyNumRetriesGauge = 
    statsReceiver.addGauge("unhealthy_num_tries") {
      state match {
        case Retrying(_, ntries, _) => ntries
        case _ => 0
      }
    }

  private[this] def reconnect(conn: ClientConnection) {
    state match {
      case Ok | Retrying(_, _, Stream.Empty) =>
        state = Ok
      case Retrying(since, ntries, wait #:: rest) =>
        val nextState = Retrying(since, ntries+1, rest)
        state = nextState
        timer.schedule(wait) {
          self(conn) onSuccess { _ =>
            if (state == nextState) state = Ok
          } onFailure { _ => 
            if (state == nextState) reconnect(conn)
          }
        }
      }
  }

  override def apply(conn: ClientConnection) =
    self(conn) onFailure { _ =>
      state match {
        case Ok =>
          state = Retrying(Time.now, 0, backoffs)
          reconnect(conn)
        case _ => // Already retrying
      }
    } onSuccess { _ =>
      state = Ok
    }

  override def isAvailable = self.isAvailable && state == Ok

  override val toString = "fail_fast_%s".format(self.toString)
}
