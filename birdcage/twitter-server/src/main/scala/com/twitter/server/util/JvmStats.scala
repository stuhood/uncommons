package com.twitter.server.util

import com.twitter.finagle.stats.{BroadcastStatsReceiver, StatsReceiver}
import com.twitter.util.Try
import java.lang.management.ManagementFactory
import java.lang.reflect.Method
import scala.collection.mutable

object JvmStats {
  import com.twitter.conversions.string._
  import scala.collection.JavaConverters._

  def register(statsReceiver: StatsReceiver) = {
    val stats = statsReceiver.scope("jvm")

    val mem = ManagementFactory.getMemoryMXBean()

    def heap = mem.getHeapMemoryUsage()
    val heapStats = stats.scope("heap")
    heapStats.addGauge("committed") { heap.getCommitted() }
    heapStats.addGauge("max") { heap.getMax() }
    heapStats.addGauge("used") { heap.getUsed() }

    def nonHeap = mem.getNonHeapMemoryUsage()
    val nonHeapStats = stats.scope("nonheap")
    nonHeapStats.addGauge("committed") { nonHeap.getCommitted() }
    nonHeapStats.addGauge("max") { nonHeap.getMax() }
    nonHeapStats.addGauge("used") { nonHeap.getUsed() }

    val threads = ManagementFactory.getThreadMXBean()
    val threadStats = stats.scope("thread")
    threadStats.addGauge("daemon_count") { threads.getDaemonThreadCount().toLong }
    threadStats.addGauge("count") { threads.getThreadCount().toLong }
    threadStats.addGauge("peak_count") { threads.getPeakThreadCount().toLong }

    val runtime = ManagementFactory.getRuntimeMXBean()
    stats.addGauge("start_time") { runtime.getStartTime() }
    stats.addGauge("uptime") { runtime.getUptime() }

    val os = ManagementFactory.getOperatingSystemMXBean()
    stats.addGauge("num_cpus") { os.getAvailableProcessors().toLong }
    os match {
      case unix: com.sun.management.UnixOperatingSystemMXBean =>
        stats.addGauge("fd_count") { unix.getOpenFileDescriptorCount }
        stats.addGauge("fd_limit") { unix.getMaxFileDescriptorCount }
      case _ =>
    }

    val compilation = ManagementFactory.getCompilationMXBean()
    val compilationStats = stats.scope("compilation")
    compilationStats.addGauge("time_msec") { compilation.getTotalCompilationTime() }

    val classes = ManagementFactory.getClassLoadingMXBean()
    val classLoadingStats = stats.scope("classes")
    classLoadingStats.addGauge("total_loaded") { classes.getTotalLoadedClassCount() }
    classLoadingStats.addGauge("total_unloaded") { classes.getUnloadedClassCount() }
    classLoadingStats.addGauge("current_loaded") { classes.getLoadedClassCount().toLong }


    val memPool = ManagementFactory.getMemoryPoolMXBeans.asScala
    val memStats = stats.scope("mem")
    val currentMem = memStats.scope("current")
    // TODO: Refactor postGCStats when we confirmed that no one is using this stats anymore
    // val postGCStats = memStats.scope("postGC")
    val postGCMem = memStats.scope("postGC")
    val postGCStats = BroadcastStatsReceiver(Seq(stats.scope("postGC"), postGCMem))
    memPool foreach { pool =>
      val name = pool.getName.regexSub("""[^\w]""".r) { m => "_" }
      if (pool.getCollectionUsage != null) {
        def usage = pool.getCollectionUsage // this is a snapshot, we can't reuse the value
        postGCStats.addGauge(name, "used") { usage.getUsed }
        postGCStats.addGauge(name, "max") { usage.getMax }
      }
      if (pool.getUsage != null) {
        def usage = pool.getUsage // this is a snapshot, we can't reuse the value
        currentMem.addGauge(name, "used") { usage.getUsed }
        currentMem.addGauge(name, "max") { usage.getMax }
      }
    }
    postGCStats.addGauge("used") {
      memPool flatMap(p => Option(p.getCollectionUsage)) map(_.getUsed) sum
    }
    currentMem.addGauge("used") {
      memPool flatMap(p => Option(p.getUsage)) map(_.getUsed) sum
    }

    // `BufferPoolMXBean` and `ManagementFactory.getPlatfromMXBeans` are introduced in Java 1.7.
    // Use reflection to add these gauges so we can still compile with 1.6
    val bufferPoolStats = memStats.scope("buffer")
    for {
      bufferPoolMXBean <- Try[Class[_]] {
        ClassLoader.getSystemClassLoader.loadClass("java.lang.management.BufferPoolMXBean")
      }
      getPlatformMXBeans <- classOf[ManagementFactory].getMethods.find { m =>
        m.getName == "getPlatformMXBeans" && m.getParameterTypes.length == 1
      }
      pool <- getPlatformMXBeans.invoke(null /* static method */, bufferPoolMXBean)
        .asInstanceOf[java.util.List[_]].asScala
    } {
      val name = bufferPoolMXBean.getMethod("getName").invoke(pool).asInstanceOf[String]

      val getCount: Method = bufferPoolMXBean.getMethod("getCount")
      bufferPoolStats.addGauge(name, "count") { getCount.invoke(pool).asInstanceOf[Long] }

      val getMemoryUsed: Method = bufferPoolMXBean.getMethod("getMemoryUsed")
      bufferPoolStats.addGauge(name, "used") { getMemoryUsed.invoke(pool).asInstanceOf[Long] }

      val getTotalCapacity: Method = bufferPoolMXBean.getMethod("getTotalCapacity")
      bufferPoolStats.addGauge(name, "max") { getTotalCapacity.invoke(pool).asInstanceOf[Long] }
    }

    val gcPool = ManagementFactory.getGarbageCollectorMXBeans.asScala
    val gcStats = stats.scope("gc")
    gcPool foreach { gc =>
      val name = gc.getName.regexSub("""[^\w]""".r) { m => "_" }
      gcStats.addGauge(name, "cycles") { gc.getCollectionCount }
      gcStats.addGauge(name, "msec") { gc.getCollectionTime }
    }

    // note, these could be -1 if the collector doesn't have support for it.
    gcStats.addGauge("cycles") { gcPool map(_.getCollectionCount) filter(_ > 0) sum }
    gcStats.addGauge("msec") { gcPool map(_.getCollectionTime) filter(_ > 0) sum }
  }
}

