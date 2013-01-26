package com.twitter.finagle

import com.twitter.finagle.builder.Cluster
import com.twitter.finagle.client._
import com.twitter.finagle.dispatch.{SerialServerDispatcher, PipeliningDispatcher}
import com.twitter.finagle.memcached.protocol.text.{
  MemcachedClientPipelineFactory, MemcachedServerPipelineFactory
}
import com.twitter.finagle.memcached.protocol.{Command, Response}
import com.twitter.finagle.netty3._
import com.twitter.finagle.pool.ReusingPool
import com.twitter.finagle.server._
import com.twitter.finagle.stats.StatsReceiver
import java.net.SocketAddress

trait MemcachedRichClient { self: Client[Command, Response] =>
  def newRichClient(cluster: Cluster[SocketAddress]): memcached.Client = memcached.Client(newClient(cluster).toService)
  def newRichClient(cluster: String): memcached.Client = memcached.Client(newClient(cluster).toService)
}

object MemcachedBinder extends DefaultBinder[Command, Response, Command, Response](
  new Netty3Transporter(MemcachedClientPipelineFactory),
  new PipeliningDispatcher(_)
)

object MemcachedClient extends DefaultClient[Command, Response](
  MemcachedBinder, _ => new ReusingPool(_)
) with MemcachedRichClient

object MemcachedListener extends Netty3Listener[Response, Command](MemcachedServerPipelineFactory)
object MemcachedServer 
  extends DefaultServer[Command, Response, Response, Command](MemcachedListener, new SerialServerDispatcher(_, _))

object Memcached extends Client[Command, Response] with MemcachedRichClient with Server[Command, Response] {
  def newClient(cluster: Cluster[SocketAddress]): ServiceFactory[Command, Response] =
    MemcachedClient.newClient(cluster)

  def serve(addr: SocketAddress, service: ServiceFactory[Command, Response]): ListeningServer =
    MemcachedServer.serve(addr, service)
}
