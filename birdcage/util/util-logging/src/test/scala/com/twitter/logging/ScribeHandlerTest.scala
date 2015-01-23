/*
 * Copyright 2010 Twitter, Inc.
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

package com.twitter
package logging

import java.util.concurrent.TimeUnit
import java.util.{logging => javalog}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, WordSpec}

import com.twitter.conversions.string._
import com.twitter.conversions.time._
import com.twitter.util.Time

@RunWith(classOf[JUnitRunner])
class ScribeHandlerTest extends WordSpec with BeforeAndAfter {
  val record1 = new javalog.LogRecord(Level.INFO, "This is a message.")
  record1.setMillis(1206769996722L)
  record1.setLoggerName("hello")
  val record2 = new javalog.LogRecord(Level.INFO, "This is another message.")
  record2.setLoggerName("hello")
  record2.setMillis(1206769996722L)

  // This is a huge hack to make sure that the buffer doesn't
  // get flushed.
  val portWithoutListener = 50506

  "ScribeHandler" should {
    before {
      Logger.reset()
      Logger.get("").setLevel(Logger.FATAL)
    }

    // TODO: the hack mentioned in several places below is not preventing flush from being
    // called. if there's a service listening on localhost:50506 this test will hang again

    "build a scribe RPC call" in {
      Time.withCurrentTimeFrozen { _ =>
        val scribe = ScribeHandler(
          // This is a huge hack to make sure that the buffer doesn't
          // get flushed.
          port = portWithoutListener,
          bufferTime = 100.milliseconds,
          maxMessagesToBuffer = 10000,
          formatter = new Formatter(timezone = Some("UTC")),
          category = "test",
          level = Some(Level.DEBUG)
        ).apply()
        scribe.publish(record1)
        scribe.publish(record2)
        assert(scribe.queue.size === 2)
        assert(scribe.makeBuffer(2).array.hexlify === (
          "000000b080010001000000034c6f67000000000f0001" +
          "0c000000020b000100000004746573740b0002000000" +
          "36494e46205b32303038303332392d30353a35333a31" +
          "362e3732325d2068656c6c6f3a205468697320697320" +
          "61206d6573736167652e0a000b000100000004746573" +
          "740b00020000003c494e46205b32303038303332392d" +
          "30353a35333a31362e3732325d2068656c6c6f3a2054" +
          "68697320697320616e6f74686572206d657373616765" +
          "2e0a0000"))
      }
    }

    "be able to log binary data" in {
      val scribe = ScribeHandler(
        port = portWithoutListener,
        bufferTime = 100.milliseconds,
        maxMessagesToBuffer = 10000,
        formatter = new Formatter(timezone = Some("UTC")),
        category = "test",
        level = Some(Level.DEBUG)
      ).apply()

      val bytes = Array[Byte](1,2,3,4,5)

      scribe.publish(bytes)

      assert(scribe.queue.peek() === bytes)
    }

    "throw away log messages if scribe is too busy" in {
      val scribe = ScribeHandler(
        port = portWithoutListener,
        bufferTime = 5.seconds,
        maxMessagesToBuffer = 1,
        formatter = BareFormatter,
        category = "test"
      ).apply()
      scribe.updateLastTransmission()
      scribe.publish(record1)
      scribe.publish(record2)
      assert(scribe.droppedRecords.get() === 1)
      assert(scribe.sentRecords.get() === 0)
    }

    "have backoff on connection errors" in {
      val scribe = ScribeHandler(
        port = portWithoutListener,
        bufferTime = 5.seconds,
        maxMessagesToBuffer = 1,
        formatter = BareFormatter,
        category = "test"
      ).apply()
      scribe.updateLastLogStats()

      scribe.publish(record1)
      scribe.publish(record2)
      scribe.publish(record1)
      scribe.publish(record2)
      scribe.publish(record1)
      scribe.publish(record2)

      scribe.flusher.shutdown()
      scribe.flusher.awaitTermination(500, TimeUnit.MILLISECONDS)

      assert(scribe.reconnectionFailure.get() === 1)
      assert(scribe.reconnectionSkipped.get() === 5)
    }
  }
}
