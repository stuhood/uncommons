def make_lib(name, rev):
  return jar_library(name=name,
    jars=[
      jar(org='com.twitter.common', name=name, rev=rev).with_sources()
    ]
  )

make_lib('application', '0.0.78')
make_lib('application-action', '0.0.81')
make_lib('application-http', '0.0.63')

jar_library(name='application-module-http',
  jars=[
    jar(org='com.twitter.common', name='application-module-http', rev='0.0.73')
      .intransitive()
      .with_sources(),
  ],
  dependencies=[
    '3rdparty/jvm/com/google/inject:guice',
    ':application-action',
    ':application-http',
    ':application-module-lifecycle',
    ':base',
    ':net-http',
    ':net-http-handlers',
    ':net-http-handlers-pprof',
    ':net-http-handlers-time-series',
    'util/util-core',
    'util/util-jvm',
  ]
)
make_lib('application-module-lifecycle', '0.0.52')
make_lib('application-module-log', '0.0.60')
make_lib('application-module-stats', '0.0.45')
# TODO: Args version 0.2.18 breaks eventbus/loadtest:bundle (See DPB-2119)
make_lib('args', '0.2.17')
make_lib('base', '0.0.101')
make_lib('collections', '0.0.90')

make_lib('io', '0.0.58')
make_lib('io-json', '0.0.45')
make_lib('io-thrift', '0.0.55')
make_lib('logging', '0.0.64')
make_lib('logging-buffered', '0.0.6')
make_lib('memcached', '0.0.9')

make_lib('metrics', '0.0.31')
make_lib('metrics-data-sample', '0.0.1')
make_lib('net-http', '0.0.47')
make_lib('net-http-handlers', '0.0.75')
make_lib('net-http-handlers-time-series', '0.0.54')
make_lib('net-http-handlers-params', '0.0.23')
jar_library(name='net-http-handlers-pprof',
  jars=[
    jar(org='com.twitter.common', name='net-http-handlers-pprof', rev='0.0.37')
      .intransitive()
      .with_sources(),
  ],
  dependencies=[
    ':net-http-handlers-params',
    ':quantity',
    '3rdparty/jvm/com/google/guava',
    'util/util-core',
    'util/util-jvm',
  ]
)
make_lib('net-pool', '0.0.60')
make_lib('net-util', '0.0.89')
make_lib('objectsize', '0.0.10')
make_lib('quantity', '0.0.86')
make_lib('service-thrift', '0.1.0')
make_lib('stats', '0.0.103')
make_lib('stats-util', '0.0.49')
make_lib('thrift', '0.0.68')
make_lib('util', '0.0.109')
make_lib('util-system-mocks', '0.0.89')
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
