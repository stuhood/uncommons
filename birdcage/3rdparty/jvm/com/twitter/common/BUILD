def make_lib(name, rev):
  return jar_library(name=name,
    dependencies=[
      jar(org='com.twitter.common', name=name, rev=rev).with_sources()
    ]
  )

make_lib('application', '0.0.71')
make_lib('application-action', '0.0.66')
make_lib('application-module-http', '0.0.59')
make_lib('application-module-log', '0.0.54')
make_lib('application-module-stats', '0.0.42')
make_lib('args', '0.2.6')
make_lib('base', '0.0.82')
make_lib('collections', '0.0.69')
make_lib('io', '0.0.51')
make_lib('io-json', '0.0.39')
make_lib('io-thrift', '0.0.48')
make_lib('logging', '0.0.58')
make_lib('logging-buffered', '0.0.5')
make_lib('memcached', '0.0.8')
make_lib('metrics', '0.0.24')
make_lib('net-pool', '0.0.57')
make_lib('net-util', '0.0.76')
make_lib('objectsize', '0.0.10')
make_lib('quantity', '0.0.66')
make_lib('service-thrift', '1.0.45')
make_lib('stats', '0.0.89')
make_lib('stats-util', '0.0.42')
make_lib('thrift', '0.0.65')
make_lib('util', '0.0.92')
make_lib('util-testing', '0.0.10')
make_lib('zookeeper-testing', '0.0.43')

jar_library(name='logging-scribe',
   dependencies=[
      jar(org='com.twitter.common', name='logging-scribe', rev='0.0.20')
         .exclude(org='org.apache.scribe', name='scribe-client'),
      pants('3rdparty/jvm/org/apache/scribe:scribe_client'),
   ]
)
