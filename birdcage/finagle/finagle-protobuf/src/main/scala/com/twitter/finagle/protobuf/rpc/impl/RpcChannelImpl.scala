package com.twitter.finagle.protobuf.rpc.impl

import java.net.InetSocketAddress
import com.google.protobuf.Descriptors.MethodDescriptor
import com.google.protobuf.RpcCallback
import com.google.protobuf.Message
import com.google.protobuf.RpcChannel
import com.google.protobuf.RpcController
import com.google.protobuf.Service
import org.slf4j.LoggerFactory
import com.twitter.util.Duration
import com.twitter.util.FuturePool
import com.twitter.finagle.builder.ClientBuilder
import java.util.concurrent.ExecutorService
import com.twitter.finagle.protobuf.rpc.RpcControllerWithOnFailureCallback
import com.twitter.finagle.protobuf.rpc.channel.ProtoBufCodec

class RpcChannelImpl(cb: ClientBuilder[(String, Message), (String, Message), Any, Any, Any], s: Service, executorService: ExecutorService) extends RpcChannel {

  private val log = LoggerFactory.getLogger(getClass)

  private val futurePool = FuturePool(executorService)

  private val client: com.twitter.finagle.Service[(String, Message), (String, Message)] = cb
    .codec(new ProtoBufCodec(s))
    .unsafeBuild()

  def callMethod(m: MethodDescriptor, controller: RpcController,
    request: Message, responsePrototype: Message,
    done: RpcCallback[Message]): Unit = {

    val req = (m.getName(), request)

    client(req) onSuccess { result =>
      futurePool({done.run(result._2)})
    } onFailure { e =>
      log.warn("Failed. ", e)
      controller.asInstanceOf[RpcControllerWithOnFailureCallback].setFailed(e)
    }
  }

  def release() {
     client.close()	
  } 
  
}
