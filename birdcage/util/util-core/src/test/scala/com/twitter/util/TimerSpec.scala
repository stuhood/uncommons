package com.twitter.util

import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger}
import java.util.concurrent.{CancellationException, ExecutorService}
import com.twitter.conversions.time._
import org.specs.SpecificationWithJUnit
import org.specs.mock.Mockito
import org.mockito.{Matchers, ArgumentCaptor}

class TimerSpec extends SpecificationWithJUnit with Mockito {
  "ThreadStoppingTimer" should {
    "stop timers in a different thread" in {
      // For some reason proper type inference fails here.
      type R = org.specs.specification.Result[java.util.concurrent.Future[_]]
      val executor = mock[ExecutorService]
      val underlying = mock[Timer]
      val timer = new ThreadStoppingTimer(underlying, executor)
      there was no(executor).submit(any[Runnable]): R
      timer.stop()
      there was no(underlying).stop()
      val runnableCaptor = ArgumentCaptor.forClass(classOf[Runnable])
      there was one(executor).submit(runnableCaptor.capture()): R
      runnableCaptor.getValue.run()
      there was one(underlying).stop()
    }
  }

  "ReferenceCountingTimer" should {
    val underlying = mock[Timer]
    val factory = mock[() => Timer]
    factory() returns underlying

    val refcounted = new ReferenceCountingTimer(factory)

    "call the factory when it is first acquired" in {
      there was no(factory)()
      refcounted.acquire()
      there was one(factory)()
    }

    "stop the underlying timer when acquire count reaches 0" in {
      refcounted.acquire()
      refcounted.acquire()
      refcounted.acquire()
      there was one(factory)()

      refcounted.stop()
      there was no(underlying).stop()
      refcounted.stop()
      there was no(underlying).stop()
      refcounted.stop()
      there was one(underlying).stop()
    }
  }

  "ScheduledThreadPoolTimer" should {
    "initialize and stop" in {
      val timer = new ScheduledThreadPoolTimer(1)
      timer must notBeNull
      timer.stop()
    }

    "increment a counter" in {
      val timer = new ScheduledThreadPoolTimer
      val counter = new AtomicInteger(0)
      timer.schedule(0.millis, 20.millis) {
        counter.incrementAndGet()
      }
      Thread.sleep(40.milliseconds.inMilliseconds)
      counter.get() must eventually(be_>=(2))
      timer.stop()
    }

    "schedule(when)" in {
      val timer = new ScheduledThreadPoolTimer
      val counter = new AtomicInteger(0)
      timer.schedule(Time.now + 20.millis) {
        counter.incrementAndGet()
      }
      Thread.sleep(40.milliseconds.inMilliseconds)
      counter.get() must eventually(be_==(1))
      timer.stop()
    }

    "cancel schedule(when)" in {
      val timer = new ScheduledThreadPoolTimer
      val counter = new AtomicInteger(0)
      val task = timer.schedule(Time.now + 20.millis) {
        counter.incrementAndGet()
      }
      task.cancel()
      Thread.sleep(40.milliseconds.inMilliseconds)
      counter.get() must not(eventually(be_==(1)))
      timer.stop()
    }
  }

  "JavaTimer" should {
    "not stop working when an exception is thrown" in {
      var errors = 0
      var latch = new CountDownLatch(1)

      val timer = new JavaTimer {
        override def logError(t: Throwable) {
          errors += 1
          latch.countDown
        }
      }

      timer.schedule(Time.now) {
        throw new scala.MatchError
      }

      latch.await(30.seconds)

      errors mustEqual 1

      var result = 0
      latch = new CountDownLatch(1)
      timer.schedule(Time.now) {
        result = 1 + 1
        latch.countDown
      } mustNot throwA[Throwable]

      latch.await(30.seconds)

      result mustEqual 2
      errors mustEqual 1
    }

    "schedule(when)" in {
      val timer = new JavaTimer
      val counter = new AtomicInteger(0)
      timer.schedule(Time.now + 20.millis) {
        counter.incrementAndGet()
      }
      Thread.sleep(40.milliseconds.inMilliseconds)
      counter.get() must eventually(be_==(1))
      timer.stop()
    }

    "cancel schedule(when)" in {
      val timer = new JavaTimer
      val counter = new AtomicInteger(0)
      val task = timer.schedule(Time.now + 20.millis) {
        counter.incrementAndGet()
      }
      task.cancel()
      Thread.sleep(40.milliseconds.inMilliseconds)
      counter.get() must not(eventually(be_==(1)))
      timer.stop()
    }
  }

  "Timer" should {
    val result = "boom"

    "doLater" in {
      val timer = new MockTimer
      val f = timer.doLater(1.millis)(result)
      f.isDefined must beFalse
      Thread.sleep(2)
      timer.tick()
      f.isDefined must beTrue
      Await.result(f) mustEqual result
    }

    "doLater throws exception" in {
      val timer = new MockTimer
      val ex = new Exception
      def task: String = throw ex
      val f = timer.doLater(1.millis)(task)
      f.isDefined must beFalse
      Thread.sleep(2)
      timer.tick()
      f.isDefined must beTrue
      Await.result(f, 0.millis) must throwA(ex)
    }

    "interrupt doLater" in {
      val timer = new MockTimer
      val f = timer.doLater(1.millis)(result)
      f.isDefined must beFalse
      f.raise(new Exception)
      Thread.sleep(2)
      timer.tick()
      f.isDefined must beTrue
      Await.result(f) must throwA[CancellationException]
    }

    "doAt" in {
      val timer = new MockTimer
      val f = timer.doAt(Time.now + 1.millis)(result)
      f.isDefined must beFalse
      Thread.sleep(2)
      timer.tick()
      f.isDefined must beTrue
      Await.result(f) mustEqual result
    }

    "cancel doAt" in {
      val timer = new MockTimer
      val f = timer.doAt(Time.now + 1.millis)(result)
      f.isDefined must beFalse
      val exc = new Exception
      f.raise(exc)
      Thread.sleep(2)
      timer.tick()
      f.poll must beLike {
        case Some(Throw(e: CancellationException)) if e.getCause eq exc => true
      }
    }

    "schedule(when)" in {
      val timer = new MockTimer
      val counter = new AtomicInteger(0)
      timer.schedule(Time.now + 1.millis)(counter.incrementAndGet())
      Thread.sleep(2)
      timer.tick()
      counter.get() mustEqual 1
    }

    "cancel schedule(when)" in {
      val timer = new MockTimer
      val counter = new AtomicInteger(0)
      val task = timer.schedule(Time.now + 1.millis)(counter.incrementAndGet())
      task.cancel()
      Thread.sleep(2)
      timer.tick()
      counter.get() mustEqual 0
    }
  }
}
