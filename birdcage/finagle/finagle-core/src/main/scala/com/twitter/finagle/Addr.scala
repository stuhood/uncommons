package com.twitter.finagle

import collection.immutable
import java.net.SocketAddress

/**
 * An address identifies the location of an object--it is a bound
 * name. An object may be replicated, and thus bound to multiple
 * physical locations; it may be delegated to an unbound name.
 * (Similar to a symbolic link in Unix.)
 */
sealed trait Addr
object Addr {
  /** 
   * A bound name. The object is replicated 
   * at each of the given socket addresses.
   *
   * Note: This currently protects the underlying addresses
   * from access since we want to add partially resolved addresses
   * in the future. At this point, the API will be fixed.
   */
  case class Bound private[finagle](addrs: immutable.Set[SocketAddress]) 
    extends Addr
  
  /** 
   * The address is failed: binding failed with
   * the given cause.
   */
  case class Failed(cause: Throwable) extends Addr
  
  /** 
   * Binding was delegated to `where`.
   */
  case class Delegated(where: String) extends Addr

  /**
   * The binding action is still pending.
   */
  object Pending extends Addr

  /**
   * A negative address: the name could not be bound.
   */
  object Neg extends Addr

  object Bound {
    def apply(addrs: SocketAddress*): Bound =
      Bound(immutable.Set(addrs:_*))
  }
  
  object Failed {
    def apply(why: String): Failed = Failed(new Exception(why))
  }
}

