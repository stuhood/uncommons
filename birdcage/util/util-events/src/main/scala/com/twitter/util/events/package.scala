package com.twitter.util

import com.twitter.app.GlobalFlag

package object events {

  // Note: these flags should generally be specified via System properties
  // to ensure that their values are available very early in the application's
  // lifecycle.

  private[events] object sinkEnabled extends GlobalFlag[Boolean](
    false,
    "Whether or not event capture is enabled. Prefer setting via System properties.")

  private[events] object approxNumEvents extends GlobalFlag[Int](
    0,
    "Approximate number of events to keep in memory. Prefer setting via System properties.")

}
