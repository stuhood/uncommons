/*
 * Copyright 2009 - 2011 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.twitter.ostrich.admin

import java.util.concurrent.CountDownLatch
import com.twitter.conversions.time._
import com.twitter.logging.Logger
import com.twitter.util.{Duration, Time}

object BackgroundProcess {
  val log = Logger.get(getClass.getName)

  /**
   * Spawn a short-lived thread for a throw-away task.
   */
  def spawn(threadName: String, daemon: Boolean)(f: => Unit): Thread = {
    val thread = new Thread(threadName) {
      override def run() {
        try {
          f
        } catch {
          case e: Throwable =>
            log.error(e, "Spawned thread %s (%s) died with a terminal exception", threadName, Thread.currentThread)
        }
      }
    }

    thread.setDaemon(daemon)
    thread.start()
    thread
  }

  def spawn(threadName: String)(f: => Unit): Thread = spawn(threadName, false)(f)
  def spawnDaemon(threadName: String)(f: => Unit): Thread = spawn(threadName, true)(f)

  def apply(f: => Unit) {
    spawnDaemon("background")(f)
  }
}

/**
 * Generalization of a background process that runs in a thread, and can be
 * stopped. Stopping the thread waits for it to finish running.
 *
 * The code block will be run inside a "forever" loop, so it should either
 * call a method that can be interrupted (like sleep) or block for a low
 * timeout.
 */
abstract class BackgroundProcess(name: String, interruptable: Boolean) extends Service {
  def this(name: String) = this(name, true)

  private val log = Logger.get(getClass.getName)

  @volatile var running = false
  val startLatch = new CountDownLatch(1)

  val runnable = new Runnable() {
    def run() {
      startLatch.countDown()
      while (running) {
        try {
          runLoop()
        } catch {
          case e: InterruptedException =>
            log.info("%s exiting by request.", name)
            running = false
          case e: Throwable =>
            log.error(e, "Background process %s died with unexpected exception: %s", name, e)
            running = false
        }
      }
      log.info("%s exiting.", name)
    }
  }

  val thread = new Thread(runnable, name)

  def start() {
    if (!running) {
      log.info("Starting %s", name)
      running = true
      thread.start()
      startLatch.await()
    }
  }

  def stop() {
    running = false
  }

  def shutdown() {
    stop()
    try {
      if (interruptable) thread.interrupt()
      thread.join()
    } catch {
      case e: Throwable =>
        log.error(e, "Failed to shutdown background process %s", name)
    }
  }

  def runLoop()
}

/**
 * Background process that performs some task periodically, over a given duration. If the duration
 * is a useful multiple of seconds, the first event will be staggered so that it takes place on an
 * even multiple. (For example, a minutely event will first trigger at the top of a minute.)
 *
 * The `periodic()` method implements the periodic event.
 */
abstract class PeriodicBackgroundProcess(name: String, private val period: Duration, interruptable: Boolean)
extends BackgroundProcess(name, interruptable) {
  def this(name: String, period: Duration) = this(name, period, true)

  def nextRun: Duration = {
    val t = Time.now + period
    // truncate to nearest round multiple of the desired repeat in seconds.
    if (period > 1.second) {
      // add 1 second because we rounded off the seconds.
      Time.fromSeconds((t.inSeconds / period.inSeconds) * period.inSeconds + 1) - Time.now
    } else {
      t - Time.now
    }
  }

  def runLoop() {
    val delay = nextRun.inMilliseconds
    if (delay > 0) {
      Thread.sleep(delay)
    }

    periodic()
  }

  /**
   * Implement the periodic event here.
   */
  def periodic()
}
