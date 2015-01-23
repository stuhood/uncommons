package com.twitter.concurrent

import scala.collection.mutable.ArrayBuffer

import org.specs.SpecificationWithJUnit

import com.twitter.util.{Promise, Return, Throw}

import Spool.{*::, **::}

class SpoolSpec extends SpecificationWithJUnit {
  "Simple resolved Spool" should {
    val s = 1 **:: 2 **:: Spool.empty

    "iterate over all elements" in {
      val xs = new ArrayBuffer[Int]
      s foreach { xs += _ }
      xs.toSeq must be_==(Seq(1,2))
    }

    "buffer to a sequence" in {
      s.toSeq() must be_==(Seq(1, 2))
    }

    "map" in {
      (s map { _ * 2 } toSeq()) must be_==(Seq(2, 4))
    }

    "deconstruct" in {
      s must beLike {
        case x **:: rest =>
          x must be_==(1)
          rest must beLike {
            case y **:: rest if y == 2 && rest.isEmpty => true
          }
      }
    }
  }

  "Simple resolved spool with error" should {
    val p = new Promise[Spool[Int]](Throw(new Exception("sad panda")))
    val s = 1 **:: 2 *:: p

    "EOF iteration on error" in {
        val xs = new ArrayBuffer[Option[Int]]
        s foreachElem { xs += _ }
        xs.toSeq must be_==(Seq(Some(1), Some(2), None))
    }
  }

  "Simple delayed Spool" should {
    val p = new Promise[Spool[Int]]
    val p1 = new Promise[Spool[Int]]
    val p2 = new Promise[Spool[Int]]
    val s = 1 *:: p

    "iterate as results become available" in {
      val xs = new ArrayBuffer[Int]
      s foreach { xs += _ }
      xs.toSeq must be_==(Seq(1))
      p() = Return(2 *:: p1)
      xs.toSeq must be_==(Seq(1, 2))
      p1() = Return(Spool.empty)
      xs.toSeq must be_==(Seq(1, 2))
    }
    
    "EOF iteration on failure" in {
      val xs = new ArrayBuffer[Option[Int]]
      s foreachElem { xs += _ }
      xs.toSeq must be_==(Seq(Some(1)))
      p() = Throw(new Exception("sad panda"))
      xs.toSeq must be_==(Seq(Some(1), None))
    }

    "return a buffered seq when complete" in {
      val f = s.toSeq
      f.isDefined must beFalse
      p() = Return(2 *:: p1)
      f.isDefined must beFalse
      p1() = Return(Spool.empty)
      f.isDefined must beTrue
      f() must be_==(Seq(1,2))
    }

    "deconstruct" in {
      s must beLike {
        case fst *:: rest if fst == 1 && !rest.isDefined => true
      }
    }

    "collect" in {
      val f = s collect {
        case x if x % 2 == 0 => x * 2
      }

      f.isDefined must beFalse  // 1 != 2 mod 0
      p() = Return(2 *:: p1)
      f.isDefined must beTrue
      val s1 = f()
      s1 must beLike {
        case x *:: rest if x == 4 && !rest.isDefined => true
      }
      p1() = Return(3 *:: p2)
      s1 must beLike {
        case x *:: rest if x == 4 && !rest.isDefined => true
      }
      p2() = Return(4 **:: Spool.empty)
      val s1s = s1.toSeq
      s1s.isDefined must beTrue
      s1s() must be_==(Seq(4, 8))
    }
  }
}
