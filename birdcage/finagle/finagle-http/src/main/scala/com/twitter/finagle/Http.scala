package com.twitter.finagle

import com.twitter.finagle.client._
import com.twitter.finagle.dispatch.SerialServerDispatcher
import com.twitter.finagle.http.codec.{HttpClientDispatcher, HttpServerDispatcher}
import com.twitter.finagle.http.HttpTransport
import com.twitter.finagle.http.filter.DtabFilter
import com.twitter.finagle.http.{HttpServerTracingFilter, HttpClientTracingFilter}
import com.twitter.finagle.netty3._
import com.twitter.finagle.server._
import com.twitter.util.Future
import java.net.{InetSocketAddress, SocketAddress}
import org.jboss.netty.handler.codec.http._

trait HttpRichClient { self: Client[HttpRequest, HttpResponse] =>
  def fetchUrl(url: String): Future[HttpResponse] = fetchUrl(new java.net.URL(url))
  def fetchUrl(url: java.net.URL): Future[HttpResponse] = {
    val addr = {
      val port = if (url.getPort < 0) url.getDefaultPort else url.getPort
      new InetSocketAddress(url.getHost, port)
    }
    val group = Group[SocketAddress](addr)
    val req = http.RequestBuilder().url(url).buildGet()
    val service = newClient(group).toService
    service(req) ensure {
      service.close()
    }
  }
}

object HttpTransporter extends Netty3Transporter[Any, Any](
  "http",
  http.Http().client(ClientCodecConfig("httpclient")).pipelineFactory
)

object HttpClient extends DefaultClient[HttpRequest, HttpResponse](
  name = "http",
  endpointer = Bridge[Any, Any, HttpRequest, HttpResponse](
    HttpTransporter, new HttpClientDispatcher(_))
) with HttpRichClient

object HttpListener extends Netty3Listener[Any, Any](
  "http",
  http.Http().server(ServerCodecConfig("httpserver", new SocketAddress{})).pipelineFactory
)

object HttpServer
extends DefaultServer[HttpRequest, HttpResponse, Any, Any](
  "http", HttpListener,
  {
    val dtab = new DtabFilter[HttpRequest, HttpResponse]
    val tracingFilter = new HttpServerTracingFilter[HttpRequest, HttpResponse]("http")
    (t, s) => new HttpServerDispatcher(
      new HttpTransport(t),
      tracingFilter andThen dtab andThen s
    )
  }
)

object Http extends Client[HttpRequest, HttpResponse] with HttpRichClient
    with Server[HttpRequest, HttpResponse]
{
  def newClient(name: Name, label: String): ServiceFactory[HttpRequest, HttpResponse] = {
    val tracingFilter = new HttpClientTracingFilter[HttpRequest, HttpResponse](label)
    tracingFilter andThen HttpClient.newClient(name, label)
  }

  def serve(addr: SocketAddress, service: ServiceFactory[HttpRequest, HttpResponse]): ListeningServer = {
    HttpServer.serve(addr, service)
  }
}

package exp {
  private[finagle]
  class HttpClient(client: StackClient[HttpRequest, HttpResponse, Any, Any])
    extends StackClientLike[HttpRequest, HttpResponse, Any, Any, HttpClient](client)
    with HttpRichClient {
    protected def newInstance(client: StackClient[HttpRequest, HttpResponse, Any, Any]) =
      new HttpClient(client)
  }

  object HttpClient extends HttpClient(new StackClient[HttpRequest, HttpResponse, Any, Any] {
    protected val newTransporter: Stack.Params => Transporter[Any, Any] = { prms =>
      val param.Label(label) = prms[param.Label]
      val httpPipeline = http.Http().client(ClientCodecConfig(label)).pipelineFactory
      Netty3Transporter(httpPipeline, prms)
    }

    protected val newDispatcher: Dispatcher = new HttpClientDispatcher(_)
  })

  object HttpServer extends StackServer[HttpRequest, HttpResponse, Any, Any] {
    protected val newListener: Stack.Params => Listener[Any, Any] = { prms =>
      val param.Label(label) = prms[param.Label]
      val httpPipeline = http.Http().server(ServerCodecConfig(label,
        new SocketAddress{})).pipelineFactory
      Netty3Listener(httpPipeline, prms)
    }

    protected val newDispatcher: Dispatcher = {
      val dtab = new DtabFilter[HttpRequest, HttpResponse]
      val tracingFilter = new HttpServerTracingFilter[HttpRequest, HttpResponse]("http")
      (t, s) => new HttpServerDispatcher(
        new HttpTransport(t),
        tracingFilter andThen dtab andThen s
      )
    }
  }
}
