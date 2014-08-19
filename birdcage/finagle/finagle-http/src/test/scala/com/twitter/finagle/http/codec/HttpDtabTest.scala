package com.twitter.finagle.http.codec

import com.twitter.finagle.{Dentry, Dtab, NameTree, Path}
import com.twitter.util.{Try, Throw}
import org.jboss.netty.handler.codec.http._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.{AssertionsForJUnit, JUnitRunner}

@RunWith(classOf[JUnitRunner])
class HttpDtabTest extends FunSuite with AssertionsForJUnit {
  val okDests = Vector("/$/inet/10.0.0.1/9000", "/foo/bar", "/")
  val okPrefixes = Vector("/foo", "/")
  val okDentries = for {
    prefix <- okPrefixes
    dest <- okDests
  } yield Dentry(Path.read(prefix), NameTree.read(dest))

  val okDtabs = 
    Dtab.empty +: (okDentries.permutations map(ds => Dtab(ds))).toIndexedSeq
  
  def newMsg(): HttpMessage =
    new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/")

  test("write(dtab, msg); read(msg) == dtab") {
    for (dtab <- okDtabs) {
      val m = newMsg()
      HttpDtab.write(dtab, m)
      val dtab1 = HttpDtab.read(m).get()
      assert(Equiv[Dtab].equiv(dtab, dtab1))
    }
  }

  // some base64 encoders insert newlines to enforce max line length.  ensure we aren't doing that
  test("long dest round-trips") {
    val expectedDtab = Dtab.read("/s/a => /s/abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz")
    val m = newMsg()
    HttpDtab.write(expectedDtab, m)
    val observedDtab = HttpDtab.read(m).get()
    assert(Equiv[Dtab].equiv(expectedDtab, observedDtab))
  }

  test("no headers") {
    val m = newMsg()
    assert(Equiv[Dtab].equiv(Dtab.empty, HttpDtab.read(m).get()))
  }

  test("Invalid: no shared prefix") {
    val m = newMsg()
    m.headers.set("X-Dtab-01-A", "a")
    m.headers.set("X-Dtab-02-B", "a")
    val result = HttpDtab.read(m)
    intercept[HttpDtab.UnmatchedHeaderException] { result.get() }
  }

  test("Invalid prefix") {
    val m = newMsg()
    m.headers.set("X-Dtab-01-A", "L2ZvbyA9PiAvZmFy") // /foo => /far
    m.headers.set("X-Dtab-01-B", "L2Zhcg==") // /far
    val result = HttpDtab.read(m)
    intercept[HttpDtab.InvalidPathException] { result.get() }
  }

  test("Invalid name") {
    val m = newMsg()
    m.headers.set("X-Dtab-01-A", "L2Zvbw==") // foo
    m.headers.set("X-Dtab-01-B", "L2ZvbyA9PiAvZmFy") // /foo => /far
    val result = HttpDtab.read(m)
    intercept[HttpDtab.InvalidNameException] { result.get() }
  }

  test("Invalid: missing entry") {
    val m = newMsg()
    m.headers.set("X-Dtab-01-A", "a")
    m.headers.set("X-Dtab-01-B", "a")
    m.headers.set("X-Dtab-02-B", "a")
    val result = HttpDtab.read(m)
    intercept[HttpDtab.UnmatchedHeaderException] { result.get() }
  }
  
  test("Invalid: non-ASCII encoding") {
    val m = newMsg()
    m.headers.set("X-Dtab-01-A", "☺")
    m.headers.set("X-Dtab-01-B", "☹")
    val result = HttpDtab.read(m)
    intercept[HttpDtab.HeaderDecodingException] { result.get() }
  }  

  test("clear()") {
    val m = newMsg()
    HttpDtab.write(Dtab.read("/a=>/b;/a=>/c"), m)
    m.headers.set("onetwothree", "123")

    val headers = Seq(
      "X-Dtab-00-A", "X-Dtab-00-B", 
      "X-Dtab-01-A", "X-Dtab-01-B")

    for (h <- headers)
      assert(m.headers.contains(h))

    assert(m.headers.contains("onetwothree"))
    
    HttpDtab.clear(m)
    
    assert(m.headers.contains("onetwothree"))
    for (h <- headers)
      assert(!m.headers.contains(h))
  }
}
