package com.twitter.finagle.javaapi;

import java.util.Collection;
import java.util.List;

import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpVersion;

import com.twitter.finagle.Client;
import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.http.Http;
import com.twitter.util.Future;
import com.twitter.util.FutureEventListener;
import com.twitter.util.Promise;

public final class HttpClientTest {

  private HttpClientTest() { }

  /**
   * Runs the client with the given {@code args}.
   */
  public static void main(String[] args) {
    Service<HttpRequest, HttpResponse> client =
      ClientBuilder.safeBuild(ClientBuilder.get()
        .codec(Http.get())
        .hosts("localhost:10000")
        .hostConnectionLimit(1));

    HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/");

    Future<HttpResponse> response = client.apply(request);
    response.addEventListener(
      new FutureEventListener<HttpResponse>() {
        public void onSuccess(HttpResponse response) {
          System.out.println("received response: " + response);
          throw new RuntimeException();
        }

        public void onFailure(Throwable cause) {
          System.out.println("failed with cause: " + cause);
          throw new RuntimeException();
        }
      });

    /* If the following compiles, variance annotations work (maybe!). */
    Promise<List<Integer>> promise = new Promise<List<Integer>>();
    promise.addEventListener(
      new FutureEventListener<Collection<Integer>>() {
        public void onSuccess(Collection<Integer> coll) { }
        public void onFailure(Throwable cause) { }
      });

    /* The following should *not* compile. Uncomment to test manually. */
    /*
    Promise<Collection<Integer>> badPromise = new Promise();
    badPromise.addEventListener(
      new FutureEventListener<List<Integer>>() {
        public void onSuccess(List<Integer> coll) {}
        public void onFailure(Throwable cause) {}
      });
    */

    // New APIs
    com.twitter.finagle.Http.newClient(":80");
    Client<HttpRequest, HttpResponse> newStyleClient =
      com.twitter.finagle.Http.client().withTls("foo.com");
  }
}
