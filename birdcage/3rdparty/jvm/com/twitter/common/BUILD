def make_lib(name, rev):
  return jar_library(name=name,
    dependencies=[
      jar(org='com.twitter.common', name=name, rev=rev).with_sources()
    ]
  )

make_lib('application', '0.0.68')
make_lib('application-action', '0.0.65')
make_lib('application-module-http', '0.0.57')
make_lib('application-module-log', '0.0.51')
make_lib('application-module-stats', '0.0.41')
make_lib('args', '0.2.3')
make_lib('base', '0.0.80')
make_lib('collections', '0.0.67')
make_lib('hosebird-client', '0.0.2')
make_lib('io', '0.0.48')
make_lib('io-json', '0.0.37')
make_lib('io-thrift', '0.0.46')
make_lib('logging', '0.0.55')
make_lib('logging-buffered', '0.0.5')
make_lib('memcached', '0.0.8')
make_lib('metrics', '0.0.19')
make_lib('net-pool', '0.0.56')
make_lib('net-util', '0.0.71')
make_lib('objectsize', '0.0.8')
make_lib('quantity', '0.0.64')
make_lib('service-thrift', '1.0.37')
make_lib('stats', '0.0.87')
make_lib('stats-util', '0.0.40')
make_lib('thrift', '0.0.64')
make_lib('util', '0.0.89')
make_lib('zookeeper-testing', '0.0.40')

jar_library(name='logging-scribe',
   dependencies=[
      jar(org='com.twitter.common', name='logging-scribe', rev='0.0.19')
         .exclude(org='org.apache.scribe', name='scribe-client'),
      pants('3rdparty/jvm/org/apache/scribe:scribe_client'),
   ]
)
