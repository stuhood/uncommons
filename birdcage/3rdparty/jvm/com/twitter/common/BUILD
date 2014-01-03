def make_lib(name, rev):
  return jar_library(name=name,
    dependencies=[
      jar(org='com.twitter.common', name=name, rev=rev).with_sources()
    ]
  )

# 8/20/2013
make_lib('application', '0.0.64')
make_lib('application-module-http', '0.0.46')
make_lib('application-module-log', '0.0.46')
make_lib('application-module-stats', '0.0.38')

# 3/14/2013
make_lib('objectsize', '0.0.7')

# 7/8/2013
make_lib('metrics', '0.0.9')

# 10/16/2013
make_lib('net-pool', '0.0.54')

# 10/2/2013
make_lib('args', '0.1.72')
make_lib('net-util', '0.0.66')
make_lib('io', '0.0.45')

# 8/14/2013
make_lib('quantity', '0.0.56')

# 9/16/2013
make_lib('service-thrift', '1.0.31')

# 10/14/2013
make_lib('stats', '0.0.83')

# 4/12/2013
make_lib('application-action', '0.0.46')
make_lib('zookeeper-testing', '0.0.38')


make_lib('base', '0.0.64')
make_lib('collections', '0.0.16')
make_lib('logging', '0.0.44')
make_lib('logging-buffered', '0.0.4')
make_lib('stats-util', '0.0.29')
make_lib('thrift', '0.0.57')
make_lib('util', '0.0.72')

jar_library(name='logging-scribe',
   dependencies=[
      jar(org='com.twitter.common', name='logging-scribe', rev='0.0.18')
         .exclude(org='org.apache.scribe', name='scribe-client'),
      pants('3rdparty/jvm/org/apache/scribe:scribe_client'),
   ]
)
