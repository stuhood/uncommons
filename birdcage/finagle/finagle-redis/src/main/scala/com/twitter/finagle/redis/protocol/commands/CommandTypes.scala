package com.twitter.finagle.redis.protocol

import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}

trait KeyCommand extends Command {
  val key: ChannelBuffer
  protected def validate() {
    RequireClientProtocol(key != null && key.readableBytes > 0, "Empty Key found")
  }
}
trait StrictKeyCommand extends KeyCommand {
  validate()
}

trait KeysCommand extends Command {
  val keys: List[ChannelBuffer]
  protected def validate() {
    RequireClientProtocol(keys != null && keys.length > 0, "Empty KeySet found")
    keys.foreach { key =>
      RequireClientProtocol(key != null && key.readableBytes > 0, "Empty key found")
    }
  }
}
trait StrictKeysCommand extends KeysCommand {
  validate()
}

trait ValueCommand extends Command {
  val value: ChannelBuffer
}
trait StrictValueCommand extends ValueCommand {
  RequireClientProtocol(value != null && value.readableBytes > 0,
    "Found unexpected empty value")
}

trait MemberCommand extends Command {
  val member: ChannelBuffer
}
trait StrictMemberCommand extends MemberCommand {
  RequireClientProtocol(member != null && member.readableBytes > 0,
    "Found unexpected empty set member")
}
