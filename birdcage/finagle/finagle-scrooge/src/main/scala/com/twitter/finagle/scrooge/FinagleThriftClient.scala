package com.twitter.finagle.scrooge

import java.util.Arrays
import com.twitter.finagle.Service
import com.twitter.finagle.thrift.ThriftClientRequest
import com.twitter.util.Future
import org.apache.thrift.TApplicationException
import org.apache.thrift.protocol._
import org.apache.thrift.transport.{TMemoryInputTransport, TMemoryBuffer}

/**
 * Common code used by any finagle thrift client code generated by scrooge.
 */
trait FinagleThriftClient {
  val service: Service[ThriftClientRequest, Array[Byte]]
  val protocolFactory: TProtocolFactory

  protected def encodeRequest(name: String, args: ThriftStruct): Future[ThriftClientRequest] = {
    Future {
      val buf = new TMemoryBuffer(512)
      val oprot = this.protocolFactory.getProtocol(buf)

      oprot.writeMessageBegin(new TMessage(name, TMessageType.CALL, 0))
      args.write(oprot)
      oprot.writeMessageEnd()

      val bytes = Arrays.copyOfRange(buf.getArray, 0, buf.length)
      new ThriftClientRequest(bytes, false)
    }
  }

  protected def decodeResponse[T](resBytes: Array[Byte], decoder: TProtocol => T): Future[T] = {
    Future {
      val iprot = protocolFactory.getProtocol(new TMemoryInputTransport(resBytes))
      val msg = iprot.readMessageBegin()
      try {
        if (msg.`type` == TMessageType.EXCEPTION) {
          throw TApplicationException.read(iprot)
        } else {
          decoder(iprot)
        }
      } finally {
        iprot.readMessageEnd()
      }
    }
  }

  protected def missingResult(name: String): Future[Nothing] = {
    Future.exception {
      new TApplicationException(
        TApplicationException.MISSING_RESULT,
        "`" + name + "` failed: unknown result")
    }
  }
}
