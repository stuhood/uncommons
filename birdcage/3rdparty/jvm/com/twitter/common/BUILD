def make_lib(name, rev):
  return jar_library(name=name,
    dependencies=[
      jar(org='com.twitter.common', name=name, rev=rev).with_sources()
    ]
  )

make_lib('application', '0.0.75')
make_lib('application-action', '0.0.75')
make_lib('application-module-http', '0.0.66')
make_lib('application-module-log', '0.0.57')
make_lib('application-module-stats', '0.0.44')
make_lib('args', '0.2.13')
make_lib('base', '0.0.95')
make_lib('collections', '0.0.82')

jar_library(name='flags',
  dependencies=[
    jar(org='com.twitter.common', name='flags', rev='1.0.0')
      .exclude('com.twitter', 'util-core')
      .exclude('com.twitter.common', 'args')
      .with_sources(),
    pants('util/util-core'),
    pants(':args'),
  ],
)

make_lib('io', '0.0.56')
make_lib('io-json', '0.0.44')
make_lib('io-thrift', '0.0.53')
make_lib('logging', '0.0.61')
make_lib('logging-buffered', '0.0.6')
make_lib('memcached', '0.0.9')
make_lib('metastore-client_v2', '0.0.3')
make_lib('metrics', '0.0.29')
make_lib('metrics-data-sample', '0.0.1')
make_lib('net-pool', '0.0.60')
make_lib('net-util', '0.0.84')
make_lib('objectsize', '0.0.10')
make_lib('quantity', '0.0.79')
make_lib('service-thrift', '1.0.51')
make_lib('stats', '0.0.98')
make_lib('stats-util', '0.0.49')
make_lib('thrift', '0.0.68')
make_lib('util', '0.0.106')
make_lib('util-system-mocks', '0.0.56')
make_lib('util-testing', '0.0.11')
make_lib('zookeeper-testing', '0.0.46')

jar_library(name='logging-scribe',
   dependencies=[
      jar(org='com.twitter.common', name='logging-scribe', rev='0.0.21')
         .exclude(org='org.apache.scribe', name='scribe-client'),
      pants('3rdparty/jvm/org/apache/scribe:scribe_client'),
   ]
)
