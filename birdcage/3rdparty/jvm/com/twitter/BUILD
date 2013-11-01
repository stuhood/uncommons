def make_lib(name, rev, alias=None):
  if not alias:
    alias = name
  jar_library(name=alias, dependencies=[jar(org='com.twitter', name=name, rev=rev).with_sources()])

def make_thrift_lib(org, name, rev):
  jar_library(name, dependencies=[thrift_jar(org=org, name=name, rev=rev)])


jar_library(name='algebird-thrift',
  dependencies=[
    thrift_jar(org='com.twitter', name='algebird_internal-thriftjava-only', rev='0.0.1')
  ]
)

jar_library(name='app-proxy-thrift',
  dependencies=[
    thrift_jar(org='com.twitter', name='app-proxy-thrift-only', rev='2.1.0')
  ]
)

jar_library(name='beaks-data',
  dependencies=[
    jar(org='com.twitter', name='beaks-data', rev='0.0.3')
  ]
)

jar_library(name='expandodo-cards-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'expandodo-thrift-cards-only', '8.0.3')
  ]
)

jar_library(name='expandodo-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'expandodo-thrift-only', '8.1.4')
  ]
)

jar_library(name='expandodo-internal-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'expandodo-thrift-internal-only', '8.1.4')
  ]
)

jar_library(name='geoduck-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'geoduck-thrift-only', '2.0.1')
  ]
)

jar_library(name='image-proxy-thrift-service',
  dependencies=[
    thrift_jar('com.twitter', 'image-proxy-thrift-service-only', '0.0.4')
  ]
)

jar_library(name='image-proxy-thrift-types',
  dependencies=[
    thrift_jar('com.twitter', 'image-proxy-thrift-types-only', '0.0.4')
  ]
)

jar_library(name='limiter-thrift-only',
  dependencies=[
    thrift_jar(org='com.twitter', name='limiter-thrift-only', rev='1.1.32')
  ]
)

jar_library(name='pink-floyd-thrift-derivatives',
  dependencies=[
    thrift_jar('com.twitter', 'pink-floyd-thrift-derivatives-only', '0.0.1')
  ]
)

jar_library(name='pink-floyd-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'pink-floyd-thrift-only', '0.0.15')
  ]
)

jar_library(name='manhattan-thrift-v1',
  dependencies=[
    thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v1', rev='2.6.0')
  ]
)

jar_library(name='manhattan-thrift-v2',
  dependencies=[
    thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v2', rev='2.6.0')
  ]
)

jar_library(name='storehaus_internal-memcache_2.9.2',
  dependencies=[
    jar(org='com.twitter', name='storehaus_internal-memcache_2.9.2', rev='0.0.3')
      .exclude(org='com.twitter', name='finagle-memcached_2.9.2')
  ]
)

jar_library(name='wtf_recent_impressions',
  dependencies=[
    jar(org='com.twitter', name='wtf_recent_impressions', rev='0.0.2')
      .exclude(org='com.twitter.manhattan', name='manhattan-thriftfinagle')
  ]
)

jar_library(name='wtf',
  dependencies=[
    jar(org='com.twitter', name='wtf', rev='0.2.14', classifier='thrift-class')
  ]
)

make_lib('platform-analytics-parser', '0.0.4-20130920100303-94f8588')
make_lib('summingbird-client_2.9.2', '0.0.7')
make_lib('ml', '0.1.6')


make_thrift_lib('com.twitter', 'adserver_rpc-only', '1.0.96')
make_thrift_lib('com.twitter', 'metastore-thrift-only', '0.0.39')
make_thrift_lib('com.twitter', 'adserver_common-only', '1.0.123')

make_lib('algebird-core_2.9.3', '0.2.0')
make_lib('algebird_internal-bijection', '0.0.4')
make_lib('algebird_internal-thriftscala', '0.0.4')
make_lib('bijection-json_2.9.3', '0.5.3')
make_lib('ouroboros-common', '0.0.18')
make_lib('ouroboros-thriftscala', '0.0.21')
make_lib('storehaus-core_2.9.3', '0.5.1')
make_lib('storehaus-memcache_2.9.3', '0.5.1')
make_lib('summingbird-batch_2.9.3', '0.2.0')
make_lib('summingbird-client_2.9.3', '0.2.0')
