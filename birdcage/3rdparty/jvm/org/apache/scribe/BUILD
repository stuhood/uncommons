jar_library(name='scribe_client',
  jars=[
    jar(org='org.apache.scribe', name='scribe_client', rev='0.1.19')
      .exclude(org='com.twitter', name='util-core')
      .exclude(org='com.twitter', name='finagle-core')
      .exclude(org='com.twitter', name='finagle-thrift'),
  ],
  dependencies=[
    pants('util/util-core'),
    pants('finagle/finagle-core'),
    pants('finagle/finagle-thrift'),
  ]
)
