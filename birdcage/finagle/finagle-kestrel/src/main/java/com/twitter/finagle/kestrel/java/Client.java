package com.twitter.finagle.kestrel.java;

import com.twitter.concurrent.Channel;
import com.twitter.concurrent.ChannelSource;
import com.twitter.concurrent.Offer;
import com.twitter.finagle.ServiceFactory;
import com.twitter.finagle.kestrel.protocol.Command;
import com.twitter.finagle.kestrel.protocol.Response;
import com.twitter.finagle.kestrel.ReadHandle;
import com.twitter.finagle.kestrel.ReadMessage;
import com.twitter.util.Duration;
import com.twitter.util.Future;
import com.twitter.util.Time;
import com.twitter.util.Timer;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.Iterator;

/**
 * A Java-friendly Client for interacting with Kestrel.
 */
public abstract class Client {
  public static Client newInstance(ServiceFactory<Command, Response> finagleClient) {
    com.twitter.finagle.kestrel.Client kestrelClient =
      com.twitter.finagle.kestrel.Client$.MODULE$.apply(finagleClient);
    return new com.twitter.finagle.kestrel.java.ClientBase(kestrelClient);
  }

  /**
   * Dequeue an item.
   *
   * @param key the name of the queue.
   * @param waitFor if the queue is empty, wait up to this duration for an item to arrive.
   * @return A Future<ChannelBuffer>
   */
  abstract public Future<ChannelBuffer> get(String key, Duration waitFor);

  /**
   * Enqueue an item.
   *
   * @param key the queue to enqueue into
   * @param value the value to enqueue
   * @param expiry indicates to Kestrel to discard the item if it isn't dequeued in time.
   * @return a Future<Response> indicating success for failure.
   */
  abstract public Future<Response> set(String key, ChannelBuffer value, Time expiry);

  /**
   * Delete a queue. Kestrel will actually delete the queue's journal file and all items in the queue.
   *
   * @param key the queue name.
   * @return a Future<Response> indicating success if the queue already exists
   */
  abstract public Future<Response> delete(String key);

  /**
   * Flush/empty a queue. The journal file is preserved
   *
   * @param key the queue name
   * @return a Future<Response> indicating success if the queue already exists
   */
  abstract public Future<Response> flush(String key);

  /**
   * A friendly Channel object for Dequeueing items from a queue as they arrive.
   * @param key queue name
   * @param waitFor if the queue is empty, wait up to this duration for something to arrive before explicitly calling dequeueing again. A sensible value for this is infinity.
   * @return a ChannelSource<ChannelBuffer>
   */
  abstract public Channel<ChannelBuffer> sink(String key, Duration waitFor);

  /**
   * A friendly ChannelSource object for Enqueuing items from a queue as they arrive.
   * @param key queue name
   * @return a ChannelSource<ChannelBuffer>
   */
  abstract public ChannelSource<ChannelBuffer> source(String key);

  /**
   * Write indefinitely to the given queue.  The given offer is
   * synchronized on indefinitely, writing the items as they become
   * available.  Unlike {{read}}, {{write}} does not reserve a
   * connection.
   *
   * @return a Future indicating client failure.
   */
  abstract public Future<Throwable> write(String queueName, Offer<ChannelBuffer> offer);

  /**
   * Read indefinitely from the given queue with transactions.  Note
   * that {{read}} will reserve a connection for the duration of the
   * read.  Note that this does no buffering: we await acknowledment
   * (through synchronizing on ReadMessage.ack) before acknowledging
   * that message to the kestrel server & reading the next one.
   *
   * @return A read handle.
   */
  abstract public ReadHandle read(String queueName);

  /**
   * Read from a queue reliably: retry streaming reads on failure
   * (which may indeed be backed by multiple kestrel hosts).  This
   * presents to the user a virtual "reliable" stream of messages, and
   * errors are transparent.
   *
   * @param queueName the queue to read from
   * @param timer a timer used to delay retries
   * @param retryBackoffs a (possibly infinite) stream of durations
   * comprising a backoff policy
   */
  abstract public ReadHandle readReliably(String queueName, Timer timer, Callable<Iterator<Duration>> backoffs);

  /**
   * {{readReliably}} with infinite, 0-second backoff retries.
   */
  abstract public ReadHandle readReliably(String queueName);

  /**
   * Release any resources (like threadpools) used by this client.
   */
  abstract public void close();

  /**
   * Dequeue an item
   *
   * @param key the queue name
   * @return a Channel buffer if the item exists, null otherwise.
   */
  public Future<ChannelBuffer> get(String key) {
    return this.get(key, Duration.apply(0, TimeUnit.SECONDS));
  }

  /**
   * Get a channel for the given queue.
   *
   * @param key the queue name
   * @return
   */
  public Channel<ChannelBuffer> sink(String key) {
    return this.sink(key, Duration.apply(10, TimeUnit.SECONDS));
  }

  /**
   * Enqueue an item with no expiry.
   *
   * @param key the queue
   * @param value the item as a ChannelBuffer
   * @return a Future<Reponse> indicating success or failure.
   */
  public Future<Response> set(String key, ChannelBuffer value) {
    return this.set(key, value, Time.fromMilliseconds(0));
  }

  /**
   * Enqueue an item.
   *
   * @param key the queue
   * @param value the item as a String. The bytes behind the String are stored in Kestrel
   * @return a Future<Response> indicating success or failutre.
   */
  public Future<Response> set(String key, String value) {
    return this.set(key, toChannelBuffer(value));
  }

  /**
   * Enqueue an item.
   *
   * @param key the queue
   * @param value the item as a string. The bytes behind the string are stored in Kestrel.
   * @param expiry indicates to Kestrel to delete the item if it is not enqueued in time.
   * @return a Future<Response> indicating success or failure.
   */
  public Future<Response> set(String key, String value, Time expiry) {
    return this.set(key, toChannelBuffer(value), expiry);
  }

  private ChannelBuffer toChannelBuffer(String value) {
    return ChannelBuffers.wrappedBuffer(value.getBytes());
  }
}
