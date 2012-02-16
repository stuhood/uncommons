package com.twitter.finagle.ssl

import java.lang.reflect.Method
import java.util.logging.Logger
import javax.net.ssl.SSLException

import org.jboss.netty.channel.{
  ChannelHandlerContext, ChannelStateEvent, ExceptionEvent, SimpleChannelUpstreamHandler
}

class SslShutdownHandler(o: Object) extends SimpleChannelUpstreamHandler {
  private[this] val log = Logger.getLogger(getClass().getName())
  private[this] val shutdownMethod: Option[Method] =
    try {
      Some(o.getClass().getMethod("shutdown"))
    } catch {
      case _: NoSuchMethodException => None
    }

  private[this] def shutdownAfterChannelClosure() {
    shutdownMethod foreach { method: Method =>
      method.invoke(o)
    }
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent) {
    e.getCause match {
      case sslException: SSLException =>
        ctx.getPipeline.remove("ssl")
      case _ =>
    }
    super.exceptionCaught(ctx, e)
  }

  override def channelClosed(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
    shutdownAfterChannelClosure()

    super.channelClosed(ctx, e)
  }
}
