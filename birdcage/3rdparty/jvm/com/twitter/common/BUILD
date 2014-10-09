def make_lib(name, rev):
  return jar_library(name=name,
    jars=[
      jar(org='com.twitter.common', name=name, rev=rev).with_sources()
    ]
  )

make_lib('application', '0.0.75')
make_lib('application-action', '0.0.80')
make_lib('application-http', '0.0.61')

jar_library(name='application-module-http',
  jars=[
    jar(org='com.twitter.common', name='application-module-http', rev='0.0.66')
      .intransitive()
      .with_sources(),
  ],
  dependencies=[
    ':application-action',
    ':application-http',
    ':application-module-lifecycle',
    ':base',
    ':net-http',
    ':net-http-handlers',
    ':net-http-handlers-time-series',
    ':net-http-handlers-pprof',
    '3rdparty:guice',
    'util/util-core',
    'util/util-jvm',
  ]
)
make_lib('application-module-lifecycle', '0.0.50')
make_lib('application-module-log', '0.0.57')
make_lib('application-module-stats', '0.0.44')
make_lib('args', '0.2.17')
make_lib('base', '0.0.100')
make_lib('collections', '0.0.89')

make_lib('io', '0.0.57')
make_lib('io-json', '0.0.45')
make_lib('io-thrift', '0.0.54')
make_lib('logging', '0.0.61')
make_lib('logging-buffered', '0.0.6')
make_lib('memcached', '0.0.9')

jar_library(name='metastore-client_v2',
  jars=[
    jar(org='com.twitter.common', name='metastore-client_v2', rev='0.0.3')
      .intransitive(),
  ],
  dependencies=[
    '3rdparty/jvm/com/twitter/common:base',
    '3rdparty/jvm/com/twitter/common_internal:manhattan',
    '3rdparty/jvm/com/twitter/metastore:data',
    '3rdparty:commons-lang',
    '3rdparty:guava',
    '3rdparty:javax.inject',
    '3rdparty:jsr305',
    '3rdparty:libthrift-0.5.0',
    '3rdparty:slf4j-api',
    '3rdparty:slf4j-jdk14',
    'util/util-core',
  ]
)

make_lib('metrics', '0.0.29')
make_lib('metrics-data-sample', '0.0.1')
make_lib('net-http', '0.0.46')
make_lib('net-http-handlers', '0.0.73')
make_lib('net-http-handlers-time-series', '0.0.53')
make_lib('net-http-handlers-params', '0.0.21')
jar_library(name='net-http-handlers-pprof',
  jars=[
    jar(org='com.twitter.common', name='net-http-handlers-pprof', rev='0.0.31')
      .intransitive()
      .with_sources(),
  ],
  dependencies=[
    ':net-http-handlers-params',
    ':quantity',
    '3rdparty:guava',
    'util/util-core',
    'util/util-jvm',
  ]
)
make_lib('net-pool', '0.0.60')
make_lib('net-util', '0.0.88')
make_lib('objectsize', '0.0.10')
make_lib('quantity', '0.0.85')
make_lib('service-thrift', '0.1.0')
make_lib('stats', '0.0.102')
make_lib('stats-util', '0.0.49')
make_lib('thrift', '0.0.68')
make_lib('util', '0.0.108')
make_lib('util-system-mocks', '0.0.88')
make_lib('util-testing', '0.0.11')
make_lib('zookeeper-testing', '0.0.46')

jar_library(name='logging-scribe',
  jars=[
     jar(org='com.twitter.common', name='logging-scribe', rev='0.0.21')
        .exclude(org='org.apache.scribe', name='scribe_client'),
  ],
  dependencies=[
     '3rdparty/jvm/org/apache/scribe:scribe_client',
  ]
)
