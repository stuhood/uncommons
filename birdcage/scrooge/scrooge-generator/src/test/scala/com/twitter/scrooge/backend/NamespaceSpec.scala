package com.twitter.scrooge.backend

import com.twitter.scrooge.testutil.Spec

class NamespaceSpec extends Spec {
  "Scala Generator" should {
    import foo._
    import bar._
    import com.fake._
    "import from another namespace" in {
      val service: Restaurant[Some] = new Restaurant[Some] {
        def isOpen(whichDay: Weekday) = Some(whichDay != Weekday.Monday)
        def makeReservation(whichDay: Weekday, howMany: Int) =
          Some(if (whichDay == Weekday.Monday) 0 else howMany)
      }
      service.makeReservation(Weekday.Monday, 2) must be(Some(0))
      service.makeReservation(Weekday.Tuesday, 2) must be(Some(2))
    }
  }

  "Java Generator" should {
    import foo._
    import java_bar._
    import com.java_fake._
    "import from another namespace" in {
      val service: Restaurant.Iface = new Restaurant.Iface {
        def isOpen(whichDay: Weekday) = whichDay != Weekday.MONDAY
        def makeReservation(whichDay: Weekday, howMany: Int) =
          if (whichDay == Weekday.MONDAY) 0 else howMany
      }
      service.makeReservation(Weekday.MONDAY, 2) must be(0)
      service.makeReservation(Weekday.TUESDAY, 2) must be(2)
    }
  }
}
