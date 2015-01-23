package com.twitter.util

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class TryTest extends FunSuite {
  class MyException extends Exception
  val e = new Exception("this is an exception")

  test("Try.apply(): should catch exceptions and lift into the Try type") {
    assert(Try[Int](1) == Return(1))
    assert(Try[Int] { throw e } == Throw(e))
  }

  test("Try.apply(): should propagate fatal exceptions") {
    intercept[AbstractMethodError] {
      Try[Int] { throw new AbstractMethodError }
    }
  }

  test("Try.rescue") {
    val myException = new MyException
    val result1 = Return(1) rescue { case _ => Return(2) }
    val result2 = Throw(e) rescue { case _ => Return(2) }
    val result3 = Throw(e) rescue { case _ => Throw(e) }

    assert(result1 == Return(1))
    assert(result2 == Return(2))
    assert(result3 == Throw(e))
  }

  test("Try.getOrElse") {
    assert(Return(1).getOrElse(2) == 1)
    assert(Throw(e).getOrElse(2) == 2)
  }

  test("Try.apply") {
    assert(Return(1)() == 1)
    intercept[Exception] { Throw[Int](e)() }
  }

  test("Try.map: when there is no exception") {
    val result1 = Return(1).map(1 + _)
    val result2 = Throw[Int](e).map(1 + _)

    assert(result1 == Return(2))
    assert(result2 == Throw(e))
  }

  test("Try.map: when there is an exception") {
    val result1 = Return(1) map(_ => throw e)
    assert(result1 == Throw(e))

    val e2 = new Exception
    val result2 = Throw[Int](e) map(_ => throw e2)
    assert(result2 == Throw(e))
  }

  test("Try.flatMap: when there is no exception") {
    val result1 = Return(1) flatMap(x => Return(1 + x))
    val result2 = Throw[Int](e) flatMap(x => Return(1 + x))

    assert(result1 == Return(2))
    assert(result2 == Throw(e))
  }

  test("Try.flatMap: when there is an exception") {
    val result1 = Return(1).flatMap[Int](_ => throw e)
    assert(result1 == Throw(e))

    val e2 = new Exception
    val result2 = Throw[Int](e).flatMap[Int](_ => throw e2)
    assert(result2 == Throw(e))
  }

  test("Try.flatten: is a Return(Return)") {
    assert(Return(Return(1)).flatten == Return(1))
  }

  test("Try.flatten: is a Return(Throw)") {
    val e = new Exception
    assert(Return(Throw(e)).flatten == Throw(e))
  }

  test("Try.flatten: is a Throw") {
    val e = new Exception
    assert(Throw[Try[Int]](e).flatten == Throw(e))
  }

  test("Try in for comprehension with no Throw values") {
    val result = for {
      i <- Return(1)
      j <- Return(1)
    } yield (i + j)
    assert(result == Return(2))
  }

  test("Try in for comprehension with Throw values throws before") {
    val result = for {
      i <- Throw[Int](e)
      j <- Return(1)
    } yield (i + j)
    assert(result == Throw(e))
  }

  test("Try in for comprehension with Throw values throws after") {
    val result = for {
      i <- Return(1)
      j <- Throw[Int](e)
    } yield (i + j)
    assert(result == Throw(e))
  }

  test("Try in for comprehension with Throw values returns the FIRST Throw") {
    val e2 = new Exception
    val result = for {
      i <- Throw[Int](e)
      j <- Throw[Int](e2)
    } yield (i + j)
    assert(result == Throw(e))
  }

  test("Try.collect: with an empty Seq") {
    assert(Try.collect(Seq.empty) == Return(Seq.empty))
  }

  test("Try.collect: with a Throw") {
    assert(Try.collect(Seq(Return(1), Throw(e))) == Throw(e))
  }

  test("Try.collect: with Returns") {
    assert(Try.collect(Seq(Return(1), Return(2))) == Return(Seq(1, 2)))
  }
}
