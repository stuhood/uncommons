def make_lib(name, rev, alias=None):
  if not alias:
    alias = name
  jar_library(name=alias, dependencies=[jar(org='com.twitter', name=name, rev=rev).with_sources()])

def make_thrift_lib(org, name, rev):
  jar_library(name, dependencies=[thrift_jar(org=org, name=name, rev=rev)])

jar_library(name='algebird-core_2.9.3',
  dependencies=[
    thrift_jar(org='com.twitter', name='algebird-core_2.9.3', rev='0.3.0')
  ]
)

jar_library(name='algebird-thrift',
  dependencies=[
    thrift_jar(org='com.twitter', name='algebird_internal-thriftjava-only', rev='0.0.1')
  ]
)

idl_jar_thrift_library(name='app-proxy-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter', name='app-proxy-thrift-only', rev='2.1.1'),
  language='scala',
  rpc_style='finagle',
)

jar_library(name='beaks-data',
  dependencies=[
    jar(org='com.twitter', name='beaks-data', rev='0.0.3')
  ]
)

jar_library(name='chill_2.9.3',
  dependencies=[
    jar(org='com.twitter', name='chill_2.9.3', rev='0.3.4')
  ]
)

jar_library(name='expandodo-cards-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'expandodo-thrift-cards-only', '8.0.5')
  ]
)

jar_library(name='expandodo-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'expandodo-thrift-only', '8.1.5')
  ]
)

jar_library(name='expandodo-internal-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'expandodo-thrift-internal-only', '8.1.7')
  ]
)

idl_jar_thrift_library(name='image-proxy-service-thrift-scala',
  thrift_jar=thrift_jar('com.twitter', 'image-proxy-thrift-service-only', '0.0.5'),
  language='scala',
  rpc_style='finagle',
)

idl_jar_thrift_library(name='image-proxy-types-thrift-scala',
  thrift_jar=thrift_jar('com.twitter', 'image-proxy-thrift-types-only', '0.0.5'),
  language='scala',
  rpc_style='finagle',
)

jar_library(name='image-proxy-thrift-service',
  dependencies=[
    thrift_jar('com.twitter', 'image-proxy-thrift-service-only', '0.0.5')
  ]
)

jar_library(name='image-proxy-thrift-types',
  dependencies=[
    thrift_jar('com.twitter', 'image-proxy-thrift-types-only', '0.0.5')
  ]
)

jar_library(name='limiter-thrift-only',
  dependencies=[
    thrift_jar(org='com.twitter', name='limiter-thrift-only', rev='1.1.34')
  ]
)

jar_library(name='parquet-scrooge',
  dependencies=[
    jar(org='com.twitter', name='parquet-scrooge', rev='1.2.0')
  ]
)

jar_library(name='pink-floyd-thrift-derivatives',
  dependencies=[
    thrift_jar('com.twitter', 'pink-floyd-thrift-derivatives-only', '0.0.4')
  ]
)

jar_library(name='pink-floyd-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'pink-floyd-thrift-only', '0.0.19')
  ]
)


make_lib('json_2.8.1', '2.1.6')

RAINBIRD_REV = '1.2'

jar_library(name='rainbird-server',
  dependencies=[
    jar(org='com.twitter', name='rainbird-server', rev=RAINBIRD_REV).intransitive().with_sources(),
    pants('3rdparty/jvm/com/twitter/common:objectsize'),
    pants(':json_2.8.1'),
    pants(':rainbird-thrift'),
    pants(':scala-zookeeper-client'),
    pants('cassie/cassie-core'),
    pants('cassie/cassie-serversets'),
    pants('finagle/finagle-core'),
    pants('finagle/finagle-http'),
    pants('finagle/finagle-ostrich4'),
    pants('finagle/finagle-serversets'),
    pants('ostrich'),
    pants('util/util-core'),
    pants('util/util-logging'),
    pants('util/util-thrift')
  ]
)


#jar_library(name='rainbird-thrift',
#  dependencies=[
#    thrift_jar(org='com.twitter', name='rainbird-thrift', rev=RAINBIRD_REV).intransitive().with_sources(),
#    pants('finagle/finagle-core'),
#    pants('finagle/finagle-ostrich4'),
#    pants('finagle/finagle-thrift')
#  ]
#)

jar_library(name='scala-zookeeper-client',
  dependencies=[
    jar(org='com.twitter', name='scala-zookeeper-client', rev='3.0.2').intransitive().with_sources(),
    pants('3rdparty:zookeeper'),
    pants('ostrich')
  ]
)

jar_library(name='scribe-thrift',
  dependencies=[
    jar(org='com.twitter', name='scribe-thrift', rev='2.0.9').intransitive().with_sources(),
    pants('3rdparty:slf4j-api'),
    pants('finagle/finagle-core'),
    pants('finagle/finagle-ostrich4'),
    pants('finagle/finagle-thrift'),
    pants('scrooge/scrooge-runtime'),
    pants('util/util-logging'),
    pants('util/util-thrift')
  ]
)

jar_library(name='manhattan-thrift-v1',
  dependencies=[
    thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v1', rev='2.7.1')
  ]
)

jar_library(name='manhattan-thrift-v2',
  dependencies=[
    thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v2', rev='2.7.1')
  ]
)

idl_jar_thrift_library(name='manhattanv1-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v1', rev='2.7.1'),
  language='scala',
  rpc_style='finagle',
  provided_by=jar(org='com.twitter.manhattan', name='manhattan-thrift-v1', rev='2.7.1'),
)

idl_jar_thrift_library(name='manhattanv2-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v2', rev='2.7.1'),
  language='scala',
  rpc_style='finagle',
  provided_by=jar(org='com.twitter.manhattan', name='manhattan-thrift-v2', rev='2.7.1'),
)

jar_library(name='jubjub-service-thrift',
  dependencies=[
    thrift_jar('com.twitter.jubjub.v2', 'jubjub-thrift-conf-only', '0.0.28'),
    thrift_jar('com.twitter.jubjub.v2', 'jubjub-thrift-data-only', '0.0.16'),
    thrift_jar('com.twitter.jubjub.v2', 'jubjub-thrift-model-only', '0.0.20'),
    thrift_jar('com.twitter.jubjub.v2', 'jubjub-thrift-service-only', '0.0.30'),
  ]
)

make_thrift_lib('com.twitter', 'spiderduck-fetcher-thrift-only', '0.0.84')

jar_library(name='storehaus_internal-memcache_2.9.2',
  dependencies=[
    jar(org='com.twitter', name='storehaus_internal-memcache_2.9.2', rev='0.0.10')
      .exclude(org='com.twitter', name='finagle-memcached_2.9.2')
  ]
)

jar_library(name='tdc-service-thrift-only',
  dependencies=[
    thrift_jar(org='com.twitter', name='tdc-service-thrift-only', rev='0.0.2')
  ]
)

jar_library(name='wtf_recent_impressions',
  dependencies=[
    jar(org='com.twitter', name='wtf_recent_impressions', rev='0.0.3')
      .exclude(org='com.twitter.manhattan', name='manhattan-thriftfinagle')
  ]
)

jar_library(name='wtf',
  dependencies=[
    jar(org='com.twitter', name='wtf', rev='0.2.14', classifier='thrift-class')
  ]
)

make_thrift_lib('com.twitter', 'wtf-thrift-only', '1.0.16')

jar_library(name='flockdb-thrift',
  dependencies=[
    jar('com.twitter', 'flockdb-thrift', '0.0.10').intransitive(),
    pants('3rdparty:commons-lang'),
    pants('3rdparty:libthrift-0.5.0'),
    pants('3rdparty:slf4j-api'),
    pants('finagle/finagle-core'),
    pants('finagle/finagle-thrift'),
    pants('util/util-core')
  ]
)

jar_library(name='gizzard-thrift',
  dependencies=[
    jar('com.twitter', 'gizzard-thrift', '0.0.4').intransitive(),
    pants('finagle/finagle-core'),
    pants('finagle/finagle-thrift'),
    pants('util/util-core')
  ]
)


jar_library(name='ads_entities_db_predicate',
  dependencies=[
    jar(org='com.twitter', name='ads_entities_db_predicate', rev='0.0.10'),
  ]
)

jar_library(name='ads_extended_revenue_dataservice',
  dependencies=[
    jar(org='com.twitter', name='ads_extended_revenue_dataservice', rev='0.0.22')
  ]
)

make_thrift_lib('com.twitter', 'talon-thrift-only', '0.0.13')

make_lib('platform-analytics-parser', '0.0.4-20130920100303-94f8588')
make_lib('thriftstore-thrift', '0.0.3')
make_lib('ml', '0.1.6')

make_lib('talon-backend-logging-protobuf', '0.0.2')
make_lib('url-canonicalizer-core', '0.0.10')
make_lib('twadoop-protobuf-tables', '0.0.2')

def scalding_lib(name, version='0.9.0rc4'):
  return make_lib('scalding-%s_2.9.3' % name, version)
scalding_lib('args')
scalding_lib('commons')
scalding_lib('core')
scalding_lib('date')

make_lib('summingbird-client_2.9.2', '0.0.7')
make_lib('summingbird-client_2.9.3', '0.2.5')

def summingbird_lib(name, version='0.2.2'):
  return make_lib('summingbird-%s_2.9.3' % name, version)
summingbird_lib('batch')
summingbird_lib('core')
summingbird_lib('scalding')
summingbird_lib('storm')

make_thrift_lib('com.twitter', 'adserver_rpc-only', '1.0.114')
make_thrift_lib('com.twitter', 'adserver_common-only', '1.0.156')
make_thrift_lib('com.twitter', 'metastore-thrift-only', '0.0.39')
make_thrift_lib('com.twitter', 'ads_cards_thrift-only', '0.0.1')

make_lib('algebird_internal-bijection', '0.0.4')
make_lib('algebird_internal-thriftscala', '0.0.4')
make_lib('bijection-json_2.9.3', '0.5.3')
make_lib('ouroboros-common', '0.0.18')
make_lib('ouroboros-thriftscala', '0.0.21')
make_lib('storehaus-core_2.9.3', '0.6.0')
make_lib('storehaus-memcache_2.9.3', '0.6.0')

jar_library(name='rainbird-thrift',
  dependencies=[
    thrift_jar(org='com.twitter', name='rainbird-thrift-only', rev='0.0.3')
  ]
)

make_thrift_lib('com.twitter', 'trends-service-thrift-only', '1.0.60')

make_thrift_lib('com.twitter', 'logbase-thrift-only', '0.1.1')
