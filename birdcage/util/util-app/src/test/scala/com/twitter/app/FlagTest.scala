package com.twitter.app

import com.twitter.util.RandomSocket
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.Buffer

object MyGlobalFlag extends GlobalFlag[String]("a test flag", "a global test flag")
object MyGlobalFlagNoDefault extends GlobalFlag[Int]("a global test flag with no default")
object MyGlobalBooleanFlag extends GlobalFlag[Boolean](false, "a boolean flag")

@RunWith(classOf[JUnitRunner])
class FlagTest extends FunSuite {
  test("Flaggable: parse booleans") {
    assert(Flaggable.ofBoolean.parse("true"))
    assert(!Flaggable.ofBoolean.parse("false"))

    intercept[Throwable] { Flaggable.ofBoolean.parse("") }
    intercept[Throwable] { Flaggable.ofBoolean.parse("gibberish") }
  }

  test("Flaggable: parse strings") {
    assert(Flaggable.ofString.parse("blah") === "blah")
  }

  test("Flaggable: parse/show inet addresses") {
    val port = RandomSocket.nextPort()
    val local = Flaggable.ofInetSocketAddress.parse(":" + port)
    assert(local.getAddress.isAnyLocalAddress)
    assert(local.getPort === port)

    val ip = "141.211.133.111"
    val remote = Flaggable.ofInetSocketAddress.parse(ip + ":" + port)

    assert(remote.getHostName === remote.getHostName)
    assert(remote.getPort === port)

    assert(Flaggable.ofInetSocketAddress.show(local) === ":" + port)
    assert(Flaggable.ofInetSocketAddress.show(remote) === remote.getHostName + ":" + port)
  }

  test("Flaggable: parse seqs") {
    assert(Flaggable.ofSeq[Int].parse("1,2,3,4") === Seq(1,2,3,4))
  }

  test("Flaggable: parse maps") {
    assert(Flaggable.ofMap[Int, Int].parse("1=2,3=4") === Map(1 -> 2, 3 -> 4))
  }

  test("Flaggable: parse maps with comma-separated values") {
    assert(Flaggable.ofMap[String, Seq[Int]].parse("a=1,2,3,b=4,5") ===
      Map("a" -> Seq(1,2,3), "b" -> Seq(4,5)))
  }

  test("Flaggable: parse tuples") {
    assert(Flaggable.ofTuple[Int, String].parse("1,hello") === (1, "hello"))
    intercept[IllegalArgumentException] { Flaggable.ofTuple[Int, String].parse("1") }
  }

  class Ctx {
    val flag = new Flags("test")
    val fooFlag = flag("foo", 123, "The foo value")
    val barFlag = flag("bar", "okay", "The bar value")
  }

  test("Flag: defaults") {
    val ctx = new Ctx
    import ctx._

    flag.finishParsing()
    assert(fooFlag() === 123)
    assert(barFlag() === "okay")
  }

  test("Flag: add and parse flags") {
    val ctx = new Ctx
    import ctx._
    assert(flag.parseArgs(Array("-foo", "973", "-bar", "hello there")) === Flags.Ok(Nil))
    flag.finishParsing()
    assert(fooFlag() === 973)
    assert(barFlag() === "hello there")
  }

  test("Flag: override a flag") {
    val flag = new Flags("test")
    flag("foo", 1, "")
    flag("foo", 2, "")
    val allFlags = flag.getAll().toSet

    flag.finishParsing()
    assert(!allFlags.exists(_() == 1), "original flag was not overridden")
    assert(allFlags.exists(_() == 2), "overriding flag was not present in flags set")
  }

  test("Flag: let") {
    def current: Boolean = MyGlobalBooleanFlag()

    // track the order the blocks execute and that they only execute once
    var buf = Buffer[Int]()

    // make sure they stack properly
    assert(current === false)
    MyGlobalBooleanFlag.let(true) {
      buf += 1
      assert(current === true)
      MyGlobalBooleanFlag.let(false) {
        buf += 2
        assert(current === false)
      }
      buf += 3
      assert(current === true)
    }
    assert(current === false)

    assert(buf === Seq(1, 2, 3))
  }

  class Bctx extends Ctx {
    val yesFlag = flag("yes", false, "Just say yes.")
  }

  test("Boolean: default") {
    val ctx = new Bctx
    import ctx._

    flag.finishParsing()
    assert(!yesFlag())
  }

  test("Boolean: -yes") {
    val ctx = new Bctx
    import ctx._
    assert(flag.parseArgs(Array("-yes")) === Flags.Ok(Nil))
    assert(yesFlag())
  }

  test("Boolean: -yes=true") {
    val ctx = new Bctx
    import ctx._
    assert(flag.parseArgs(Array("-yes=true")) === Flags.Ok(Nil))
    assert(yesFlag())
  }

  test("Boolean: -yes=false") {
    val ctx = new Bctx
    import ctx._
    assert(flag.parseArgs(Array("-yes=false")) === Flags.Ok(Nil))
    assert(!yesFlag())
  }

  test("Boolean: -yes ARG") {
    val ctx = new Bctx
    import ctx._
    val rem = flag.parseArgs(Array("-yes", "ARG"))
    assert(yesFlag())
    assert(rem === Flags.Ok(Seq("ARG")))
  }

  test("Flag: handle remainders (sequential)") {
    val ctx = new Ctx
    import ctx._
    assert(flag.parseArgs(Array("-foo", "333", "arg0", "arg1")) === Flags.Ok(Seq("arg0", "arg1")))
  }

  test("Flag: handle remainders (interpspersed)") {
    val ctx = new Ctx
    import ctx._
    assert(flag.parseArgs(Array("arg0", "-foo", "333", "arg1")) === Flags.Ok(Seq("arg0", "arg1")))
  }

  test("Flag: stop parsing at '--'") {
    val ctx = new Ctx
    import ctx._
    assert(flag.parseArgs(Array("arg0", "--", "-foo", "333")) === Flags.Ok(Seq("arg0", "-foo", "333")))
  }

  test("Flag: give nice parse errors") {
    val ctx = new Ctx
    import ctx._
    assert(flag.parseArgs(Array("-foo", "blah")).isInstanceOf[Flags.Error])
  }

  test("Flag: handle -help") {
    val ctx = new Ctx
    import ctx._
    assert(flag.parseArgs(Array("-help")).isInstanceOf[Flags.Help])
  }

  test("Flag: mandatory flag without argument") {
    val ctx = new Ctx
    import ctx._
    assert(flag.parseArgs(Array("-foo")).isInstanceOf[Flags.Error])
  }

  test("Flag: undefined") {
    val ctx = new Ctx
    import ctx._
    assert(flag.parseArgs(Array("-undefined")).isInstanceOf[Flags.Error])
    assert(flag.parseArgs(Array("-undefined"), true) === Flags.Ok(Seq("-undefined")))
  }

  class Dctx extends Ctx {
    val quuxFlag = flag[Int]("quux", "an int")
  }

  test("Flag: no default usage") {
    val ctx = new Dctx
    import ctx._
    assert(quuxFlag.usageString === "  -quux=<Int>: an int")
  }

  test("GlobalFlag: no default usage") {
    assert(MyGlobalFlagNoDefault.usageString ===
      "  -com.twitter.app.MyGlobalFlagNoDefault=<Int>: a global test flag with no default")
  }

  test("GlobalFlag: implicit value of true for booleans") {
    assert(MyGlobalBooleanFlag() === false)
    val flag = new Flags("my", includeGlobal = true)
    flag.parseArgs(Array("-com.twitter.app.MyGlobalBooleanFlag"))
    assert(MyGlobalBooleanFlag() === true)
    MyGlobalBooleanFlag.reset()
  }

  test("GlobalFlag") {
    assert(MyGlobalFlag() === "a test flag")
    val flag = new Flags("my", includeGlobal = true)
    flag.parseArgs(Array("-com.twitter.app.MyGlobalFlag", "okay"))
    assert(MyGlobalFlag() === "okay")
    System.setProperty("com.twitter.app.MyGlobalFlag", "not okay")
    assert(MyGlobalFlag() === "okay")
    MyGlobalFlag.reset()
    assert(MyGlobalFlag() === "not okay")
    System.clearProperty("com.twitter.app.MyGlobalFlag")
  }

  test("formatFlagValues") {

    val flagWithGlobal = new Flags("my", includeGlobal = true)
    flagWithGlobal("unset.local.flag", "a flag!", "this is a local flag")
    flagWithGlobal("set.local.flag", "a flag!", "this is a local flag")
    flagWithGlobal("flag.with.single.quote", "i'm so cool", "why would you do this?")
    flagWithGlobal.parseArgs(Array("-set.local.flag=hi"))

    val flagWithoutGlobal = new Flags("my", includeGlobal = false)
    flagWithoutGlobal("unset.local.flag", "a flag!", "this is a local flag")
    flagWithoutGlobal("set.local.flag", "a flag!", "this is a local flag")
    flagWithoutGlobal("flag.with.single.quote", "i'm so cool", "why would you do this?")
    flagWithoutGlobal.parseArgs(Array("-set.local.flag=hi"))


    val localOnly =
      """|Set flags:
         |-set.local.flag='hi' \
         |Unset flags:
         |-flag.with.single.quote='i'"'"'m so cool' \
         |-help='false' \
         |-unset.local.flag='a flag!' \""".stripMargin

    val WithGlobal = true
    val WithoutGlobal = false

    /**
     * This is done because global flags from other code can pollute the global flag space
     */
    def matchesGlobal(flagString: String): Boolean = {
      val localAndGlobal = Seq(
        """Set flags:""",
        """-set.local.flag='hi'""",
        """Unset flags:""",
        """-com.twitter.app.MyGlobalFlag='a test flag'""",
        """-flag.with.single.quote='i'"'"'m so cool'""",
        """-help='false'""",
        """-unset.local.flag='a flag!' \"""
      )

      // make sure every line in localAndGlobal exists in the flagString
      localAndGlobal map { flagString.contains } reduce { _ && _ }
    }

    assert(matchesGlobal(flagWithGlobal.formattedFlagValuesString(WithGlobal)))
    assert(matchesGlobal(flagWithoutGlobal.formattedFlagValuesString(WithGlobal)))

    assert(flagWithGlobal.formattedFlagValuesString(WithoutGlobal) === localOnly)
    assert(flagWithoutGlobal.formattedFlagValuesString(WithoutGlobal) === localOnly)

    assert(matchesGlobal(flagWithGlobal.formattedFlagValuesString()))
    assert(flagWithoutGlobal.formattedFlagValuesString() === localOnly)
  }

  // TODO: uncomment after we add script parsing mode
  /*
  test("Flag that needs parsing will complain without parsing") {
    val flag = new Flag[Int]("foo", "bar", Left(() => 3))
    intercept[IllegalStateException] {
      flag()
    }
    flag.finishParsing()
    assert(flag() === 3)
  }

  test("Flag that needs parsing ok after parsing") {
    val flag = new Flag[Int]("foo", "bar", Left(() => 3))
    intercept[IllegalStateException] {
      flag()
    }
    flag.parse("4")
    assert(flag() === 4)
  }

  test("Flag that needs parsing ok resets properly") {
    val flag = new Flag[Int]("foo", "bar", Left(() => 3))
    flag.parse("4")
    assert(flag() === 4)

    flag.reset()
    intercept[IllegalStateException] {
      flag()
    }
    flag.parse("4")
    assert(flag() === 4)
  }

  test("Flags fail before parsing, OK after") {
    val ctx = new Ctx()
    import ctx._

    intercept[IllegalStateException] {
      fooFlag()
    }
    assert(flag.parseArgs(Array()) === Flags.Ok(Nil))
    assert(fooFlag() === 123)
  }

  test("Flags reset properly with respect to failure") {
    val ctx = new Ctx()
    import ctx._

    assert(flag.parseArgs(Array()) === Flags.Ok(Nil))
    assert(fooFlag() === 123)

    flag.reset()
    intercept[IllegalStateException] {
      fooFlag()
    }
    assert(flag.parseArgs(Array()) === Flags.Ok(Nil))
    assert(fooFlag() === 123)
  }
   */
}
