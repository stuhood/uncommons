package com.twitter.zk.coordination

import org.junit.runner.RunWith
import com.twitter.conversions.time._
import scala.collection.JavaConverters._
import org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE
import com.twitter.util.JavaTimer
import com.twitter.util.Await
import com.twitter.util.Future
import com.twitter.zk.{RetryPolicy, NativeConnector, ZkClient}
import org.scalatest.junit.JUnitRunner
import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class ShardCoordinatorSpec extends WordSpec with MustMatchers with MockitoSugar {

  "ShardCoordinator" should {

    val path = "/testing/twitter/service/charm/shards/test"

    Option { System.getProperty("com.twitter.zk.TEST_CONNECT") } foreach { connectString =>

      def withClient(f: (ZkClient) => Unit) = {
        implicit val timer = new JavaTimer(true)
        val connector = NativeConnector(connectString, 5.seconds, 10.minutes)
        val zk = ZkClient(connector)
            .withRetryPolicy(RetryPolicy.Basic(3))
            .withAcl(OPEN_ACL_UNSAFE.asScala)

        Await.result( Future { f(zk) } ensure { zk.release } )
      }

      def acquire(coord: ShardCoordinator) = {
        coord.acquire within(new JavaTimer(true), 1.second)
      }

      "provide shards" in {
        withClient { zk =>
          val coord = new ShardCoordinator(zk, path, 5)

          val shard0 = Await.result(acquire(coord))
          shard0.id must equal(0)

          val shard1 = Await.result(acquire(coord))
          shard1.id must equal(1)

          val shard2 = Await.result(acquire(coord))
          shard2.id must equal(2)

          val shard3 = Await.result(acquire(coord))
          shard3.id must equal(3)

          val shard4 = Await.result(acquire(coord))
          shard4.id must equal(4)

          val fshard5 = acquire(coord)
          fshard5.isDefined must be (false)
          shard3.release
          val shard5 = Await.result(fshard5)
          shard5.id must equal(3)

          shard0.release
          shard1.release
          shard2.release
          shard4.release
          shard5.release
        }
      }

    }
  }
}