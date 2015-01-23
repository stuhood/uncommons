package com.twitter.util

import java.util.concurrent.atomic.AtomicReference

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ActivityTest extends FunSuite {
  test("Activity#flatMap") {
    val v = Var(Activity.Pending: Activity.State[Int])
    val ref = new AtomicReference[Seq[Activity.State[Int]]]
    val act = Activity(v) flatMap {
      case i if i%2==0 => Activity.value(-i)
      case i => Activity.value(i)
    }
    act.states.build.register(Witness(ref))

    assert(ref.get === Seq(Activity.Pending))

    v() = Activity.Ok(1)
    assert(ref.get === Seq(Activity.Pending, Activity.Ok(1)))

    v() = Activity.Ok(2)
    assert(ref.get === Seq(Activity.Pending, Activity.Ok(1), Activity.Ok(-2)))
  }


  test("Activity#collect") {
    val v = Var(Activity.Pending: Activity.State[Int])
    val ref = new AtomicReference(Seq.empty: Seq[Try[String]])
    val act = Activity(v) collect {
      case i if i%2 == 0 => "EVEN%d".format(i)
    }
    act.values.build.register(Witness(ref))

    assert(ref.get.isEmpty)
    v() = Activity.Ok(1)
    assert(ref.get.isEmpty)
    v() = Activity.Ok(2)
    assert(ref.get === Seq(Return("EVEN2")))
    v() = Activity.Ok(3)
    assert(ref.get === Seq(Return("EVEN2")))
    v() = Activity.Ok(4)
    assert(ref.get === Seq(Return("EVEN2"), Return("EVEN4")))

    val exc = new Exception
    v() = Activity.Failed(exc)
    assert(ref.get === Seq(Return("EVEN2"), Return("EVEN4"), Throw(exc)))
  }

  test("Activity.collect") {
    val (acts, wits) = Seq.fill(10) { Activity[Int]() } unzip
    val ref = new AtomicReference(Seq.empty: Seq[Try[Seq[Int]]])
    Activity.collect(acts).values.build.register(Witness(ref))

    for ((w, i) <- wits.zipWithIndex) {
      assert(ref.get.isEmpty)
      w.notify(Return(i))
    }

    assert(ref.get === Seq(Return(Seq.range(0, 10))))

    val exc = new Exception
    wits(0).notify(Throw(exc))
    assert(ref.get === Seq(Return(Seq.range(0, 10)), Throw(exc)))

    wits(0).notify(Return(100))
    assert(ref.get === Seq(
      Return(Seq.range(0, 10)), Throw(exc), Return(100 +: Seq.range(1, 10))))
  }

  test("Exceptions are encoded") {
    val (a, w) = Activity[Int]()
    val exc1 = new Exception("1")
    val exc2 = new Exception("2")
    val exc3 = new Exception("3")

    val ref = new AtomicReference(Seq.empty: Seq[Try[Int]])
    val b = a map {
      case 111 => throw exc1
      case i => i
    } flatMap {
      case 222 => throw exc2
      case i => Activity.value(i)
    } transform {
      case Activity.Ok(333) => throw exc3
      case other => Activity(Var.value(other))
    }

    b.values.build.register(Witness(ref))

    assert(ref.get.isEmpty)

    w.notify(Return(1))
    assert(ref.get === Seq(Return(1)))

    w.notify(Return(111))
    assert(ref.get === Seq(Return(1), Throw(exc1)))

    w.notify(Return(2))
    assert(ref.get === Seq(Return(1), Throw(exc1), Return(2)))

    w.notify(Return(222))
    assert(ref.get === Seq(Return(1), Throw(exc1), Return(2), Throw(exc2)))

    w.notify(Return(3))
    assert(ref.get === Seq(Return(1), Throw(exc1), Return(2), Throw(exc2), Return(3)))

    w.notify(Return(333))
    assert(ref.get === Seq(Return(1), Throw(exc1), Return(2),
      Throw(exc2), Return(3), Throw(exc3)))
  }

  test("Var.sample") {
    val (a, w) = Activity[Int]()

    val exc = intercept[IllegalStateException] { a.sample() }
    assert(exc.getMessage === "Still pending")

    val exc1 = new Exception
    w.notify(Throw(exc1))
    assert(intercept[Exception] { a.sample() } === exc1)

    w.notify(Return(123))
    assert(a.sample() === 123)
  }
}
