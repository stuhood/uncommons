package com.twitter.finagle.util

import org.jboss.netty.channel.ChannelFuture

import com.twitter.util.Future

object Conversions {
  implicit def channelFutureToRichChannelFuture(f: ChannelFuture) = new RichChannelFuture(f)
}
