package com.twitter.hashing

import com.twitter.util.Time
import org.specs.mock.Mockito
import org.specs.Specification
import scala.collection.mutable
import _root_.java.io.{BufferedReader, InputStreamReader}

object DiagnosticsSpec extends Specification with Mockito {
  "Diagnostics" should {
    "print distribution" in {
      val hosts = 1 until 500 map { "10.1.1." + _ + ":11211:4" }

      val nodes = hosts.map { s =>
        val Array(host, port, weight) = s.split(":")
        val identifier = host + ":" + port
        KetamaNode(identifier, weight.toInt, identifier)
      }

      val hashFunctions = List(
        "FNV1_32" -> KeyHasher.FNV1_32,
        "FNV1A_32" -> KeyHasher.FNV1A_32,
        "FNV1_64" -> KeyHasher.FNV1_64,
        "FNV1A_64" -> KeyHasher.FNV1A_64,
        "CRC32-ITU" -> KeyHasher.CRC32_ITU,
        "HSIEH" -> KeyHasher.HSIEH,
        "JENKINS" -> KeyHasher.JENKINS
      )

      val keys = (1 until 1000000).map(_.toString).toList
      // Comment this out unless you're running it results
      // hashFunctions foreach { case (s, h) =>
      //   val distributor = new KetamaDistributor(nodes, 160, h)
      //   val tester = new DistributionTester(distributor)
      //   val start = Time.now
      //   val dev = tester.distributionDeviation(keys)
      //   val duration = (Time.now - start).inMilliseconds
      //   println("%s\n  distribution: %.5f\n  duration: %dms\n".format(s, dev, duration))
      // }
    }
  }
}