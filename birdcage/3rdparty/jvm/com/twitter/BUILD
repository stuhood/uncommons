def make_lib(name, rev, alias=None):
  if not alias:
    alias = name
  jar_library(name=alias, dependencies=[jar(org='com.twitter', name=name, rev=rev).with_sources()])

def make_thrift_lib(name, rev):
  jar_library(name, dependencies=[thrift_jar(org='com.twitter', name=name, rev=rev)])

make_lib('ads_entities', '0.0.40')
make_lib('ads_shared_types', '0.0.57')

ADSERVER_RPC_REV = '1.0.103'
make_thrift_lib('adserver_rpc-only', ADSERVER_RPC_REV)

jar_library(name='adserver_rpc',
  dependencies=[
    jar(org='com.twitter', name='adserver_rpc', rev=ADSERVER_RPC_REV).with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter:ads_entities'),
    pants('3rdparty/jvm/com/twitter:adserver_common'),
    pants('3rdparty/jvm/com/twitter:adserver_log'),
    pants('3rdparty:slf4j-api'),
    pants('finagle/finagle-commons-stats'),
    pants('finagle/finagle-ostrich4'),
    pants('finagle/finagle-thrift'),
    pants('scrooge/scrooge-runtime')
  ]
)

ADSERVER_COMMON_REV = '1.0.145'
make_thrift_lib('adserver_common-only', ADSERVER_COMMON_REV)
make_lib('adserver_common', ADSERVER_COMMON_REV)

make_lib('adserver_log', '1.0.98')

make_lib('algebird_internal-bijection', '0.0.4')
make_lib('algebird_internal-thriftscala', '0.0.4')
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

make_lib('bijection-json_2.9.3', '0.5.3')

jar_library(name='cassovary',
  dependencies=[
    jar(org='com.twitter', name='cassovary_2.8.1', rev='2.0.3').intransitive(),
    
    # old version of util used by cassovary
    jar(org='com.twitter', name='util', rev='4.0.1').intransitive(),

    pants('3rdparty:configgy'),
    pants('3rdparty:fastutil'),
    pants('3rdparty:guava'),
    pants('ostrich')
  ]
)

make_lib('cassowary_v2_protobuf', '0.0.4', 'cassowary')

jar_library(name='chill_2.9.3',
  dependencies=[
    jar(org='com.twitter', name='chill_2.9.3', rev='0.3.4')
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

make_lib('expandodo-thrift-cards-only', '7.0.3')

jar_library(name='geoduck-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'geoduck-thrift-only', '2.0.5')
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

jar_library(name='parquet-scrooge',
  dependencies=[
    jar(org='com.twitter', name='parquet-scrooge', rev='1.2.0')
  ]
)

jar_library(name='pink-floyd-thrift-derivatives',
  dependencies=[
    thrift_jar('com.twitter', 'pink-floyd-thrift-derivatives-only', '0.0.3')
  ]
)

jar_library(name='pink-floyd-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'pink-floyd-thrift-only', '0.0.18')
  ]
)

jar_library(name='manhattan-thrift-v1',
  dependencies=[
    thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v1', rev='2.7.0')
  ]
)

jar_library(name='manhattan-thrift-v2',
  dependencies=[
    thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v2', rev='2.7.0')
  ]
)

jar_library(name='metastore-data',
  dependencies=[
    jar(org='com.twitter', name='metastore-data', rev='0.0.19').with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter/common:base'),
    pants('3rdparty:guava'),
    pants(':metastore-thrift')
  ]
)

jar_library(name='metastore-thrift',
  dependencies=[
    jar(org='com.twitter', name='metastore-thrift', rev='0.0.42').with_sources().intransitive(),
    pants('3rdparty:commons-lang'),
    pants('3rdparty:libthrift-0.5.0'),
    pants('3rdparty:slf4j-api'),
    pants('finagle/finagle-core'),
    pants('finagle/finagle-thrift'),
    pants('util/util-core')
  ]
)

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

make_thrift_lib('metastore-thrift-only', '0.0.39')

make_lib('ouroboros-common', '0.0.18')
make_lib('ouroboros-thriftscala', '0.0.21')
make_lib('sarus_v2', '0.0.5')
make_lib('science-flock-client', '0.0.8')

jar_library(name='spiderduck-fetcher-thrift',
  dependencies=[
    jar(org='com.twitter', name='spiderduck-fetcher-thrift', rev='0.0.81').with_sources().intransitive(),
    pants('3rdparty:commons-lang'),
    pants('3rdparty:slf4j-api'),
    pants('finagle/finagle-thrift'),
    pants('util/util-core')
  ]
)

jar_library(name='spiderduck-url-store-thrift',
  dependencies=[
    jar(org='com.twitter', name='spiderduck-url-store-thrift', rev='0.0.102').with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter:spiderduck-fetcher-thrift'),
    pants('3rdparty:commons-lang'),
    pants('3rdparty:slf4j-api'),
    pants('finagle/finagle-thrift'),
    pants('util/util-core')
  ]
)

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

TRENDS_SERVICE_THRIFT_REV = '1.0.52'

jar_library(name='trends-service-thrift',
  dependencies=[
    jar(org='com.twitter', name='trends-service-thrift', rev=TRENDS_SERVICE_THRIFT_REV).with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter/search:assistance-thrift'),
    pants('3rdparty/jvm/com/twitter/search:earlybird-thrift'),
    pants('3rdparty:commons-lang'),
    pants('3rdparty:slf4j-api'),
    pants('finagle/finagle-thrift'),
    pants('util/util-core')
  ]
)

jar_library(name='trends-service-thrift-only',
  dependencies=[
    thrift_jar(org='com.twitter', name='trends-service-thrift', rev=TRENDS_SERVICE_THRIFT_REV)
    ]
)

jar_library(name='tweetypie-tweet-thrift',
  dependencies=[
    jar(org='com.twitter', name='tweetypie-tweet-thrift', rev='3.0.5').with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter:expandodo-thrift-cards-only'),
    pants('finagle/finagle-thrift'),
    pants('scrooge/scrooge-core')
  ]
)

jar_library(name='tweetypie-tweet-thrift-java',
  dependencies=[
    jar(org='com.twitter', name='tweetypie-tweet-thrift-java', rev='3.0.5').with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter:expandodo-thrift-cards-only'),
    pants('3rdparty/jvm/com/twitter:tweetypie-tweet-thrift'),
    pants('3rdparty:commons-lang'),
    pants('3rdparty:slf4j-api')
  ]
)

make_lib('user_text_profile', '0.0.13')

jar_library(name='wtf',
  dependencies=[
    jar(org='com.twitter', name='wtf', rev='0.2.21', classifier='thrift-class').with_sources().intransitive(),
    
    # old version of cassie used by wtf
    jar(org='com.twitter', name='cassie', rev='0.9.0').intransitive(),
    pants('cassie/cassie-core'),
    pants('cassie/cassie-hadoop'),

    # old version of util used by wtf
    jar(org='com.twitter', name='util', rev='4.0.1').intransitive(),

    pants('3rdparty/jvm/com/twitter/common/zookeeper:client'),
    pants('3rdparty/jvm/com/twitter/common/zookeeper:node'),
    pants('3rdparty/jvm/com/twitter/common/zookeeper:server-set'),
    pants('3rdparty/jvm/com/twitter/common:base'),
    pants('3rdparty/jvm/com/twitter/common:hosebird-client'),
    pants('3rdparty/jvm/com/twitter/common:memcached'),
    pants('3rdparty/jvm/com/twitter/common:thrift'),
    pants('3rdparty/jvm/com/twitter/common_internal:zookeeper-utils'),
    pants('3rdparty/jvm/com/twitter/manhattan:manhattan-thriftfinagle'),
    pants('3rdparty/jvm/com/twitter:cassovary'),
    pants('3rdparty/jvm/com/twitter:cassowary'),
    pants('3rdparty/jvm/com/twitter:flockdb-thrift'),
    pants('3rdparty/jvm/com/twitter:metastore-thrift'),
    pants('3rdparty/jvm/com/twitter:ml'),
    pants('3rdparty/jvm/com/twitter:sarus_v2'),
    pants('3rdparty/jvm/com/twitter:science-flock-client'),
    pants('3rdparty:cassandra-all'),
    pants('3rdparty:commons-cli'),
    pants('3rdparty:commons-codec'),
    pants('3rdparty:commons-io'),
    pants('3rdparty:commons-lang'),
    pants('3rdparty:commons-logging'),
    pants('3rdparty:commons-math'),
    pants('3rdparty:configgy'),
    pants('3rdparty:fastutil'),
    pants('3rdparty:guava'),
    pants('3rdparty:hadoop-core-cdh3'),
    pants('3rdparty:hadoop-lzo'),
    pants('3rdparty:jackson-core-asl'),
    pants('3rdparty:jackson-mapper-asl'),
    pants('3rdparty:libthrift-0.5.0'),
    pants('3rdparty:memcached'),
    pants('3rdparty:mysql-connector-java'),
    pants('3rdparty:servlet-api'),
    pants('3rdparty:twitter4j-core'),
    pants(':metastore-data'),
    pants('decider'),
    pants('finagle/finagle-core'),
    pants('finagle/finagle-memcached'),
    pants('finagle/finagle-ostrich4'),
    pants('finagle/finagle-serversets'),
    pants('finagle/finagle-thrift'),
    pants('gizmoduck-thrift/gen'),
    pants('ostrich'),
    pants('parrot'),
    pants('querulous/querulous-core'),
    pants('servo/service')
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

make_lib('storehaus-core_2.9.3', '0.6.0')
make_lib('storehaus-memcache_2.9.3', '0.6.0')
