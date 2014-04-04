def make_lib(name, rev, alias=None):
  if not alias:
    alias = name
  jar_library(name=alias, dependencies=[jar(org='com.twitter', name=name, rev=rev).with_sources()])

def make_thrift_lib(org, name, rev):
  jar_library(name, dependencies=[thrift_jar(org=org, name=name, rev=rev)])

make_lib('ads_entities', '0.0.49')
make_lib('ads_shared_types', '0.0.89')

make_lib('adserver_log', '1.0.148')

make_lib('algebird_internal-bijection', '0.0.8')
make_lib('algebird_internal-thriftscala', '0.0.17')

ALGEBIRD_REV='0.3.0'

jar_library(name='algebird-core_2.9.3',
  dependencies=[
    thrift_jar(org='com.twitter', name='algebird-core_2.9.3', rev=ALGEBIRD_REV)
  ]
)

jar_library(name='algebird-thrift',
  dependencies=[
    thrift_jar(org='com.twitter', name='algebird_internal-thriftjava-only', rev='0.0.1')
  ]
)

make_lib('algebird-bijection_2.9.3', ALGEBIRD_REV)

jar_library(name='algebird-util_2.9.3',
  dependencies=[
    jar(org='com.twitter', name='algebird-util_2.9.3', rev=ALGEBIRD_REV).with_sources().intransitive(),
    pants(':algebird-core_2.9.3'),
    pants('util/util-core')
  ]
)

jar_library(name='app-proxy-thrift',
  dependencies=[
    thrift_jar(org='com.twitter', name='app-proxy-thrift-only', rev='2.1.2')
  ]
)

idl_jar_thrift_library(name='tiered-actions-user-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter', name='tiered-actions-user-thrift-only', rev='0.0.11'),
  language='scala',
  rpc_style='finagle',
)

idl_jar_thrift_library(name='app-proxy-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter', name='app-proxy-thrift-only', rev='2.1.2'),
  language='scala',
  rpc_style='finagle',
)

# NB: synchronize with 3rdparty:bijection*
BIJECTION_REV='0.5.4'

make_lib('bijection-json_2.9.3', BIJECTION_REV)
make_lib('bijection-netty_2.9.2', '0.4.0-SNAPSHOT', 'bijection-netty')

jar_library(name='bijection-netty_2.9.3',
  dependencies=[
    jar(org='com.twitter', name='bijection-netty_2.9.3', rev=BIJECTION_REV).with_sources().intransitive(),
    pants('3rdparty:bijection-core_2.9.3'),
    pants('3rdparty:netty')
  ]
)

jar_library(name='bijection-util_2.9.3',
  dependencies=[
    jar(org='com.twitter', name='bijection-util_2.9.3', rev=BIJECTION_REV).with_sources().intransitive(),
    pants('3rdparty:bijection-core_2.9.3'),
    pants('util/util-core')
  ]
)

jar_library(name='beaks-data',
  dependencies=[
    jar(org='com.twitter', name='beaks-data', rev='0.0.3')
  ]
)

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

CHILL_REV='0.3.6'

jar_library(name='chill_2.9.3',
  dependencies=[
    jar(org='com.twitter', name='chill_2.9.3', rev=CHILL_REV)
  ]
)

make_lib('chill-bijection_2.9.3', CHILL_REV)
make_lib('chill-hadoop', CHILL_REV)
make_lib('chill-java', CHILL_REV)
make_lib('chill-storm', CHILL_REV)

jar_library(name='geoduck-thrift',
  dependencies=[
    thrift_jar('com.twitter', 'geoduck-thrift-only', '2.0.9')
  ]
)

HBC_REV='2.0.1'

make_lib('hbc-core', HBC_REV)
make_lib('hbc-twitter4j', HBC_REV)
make_lib('hbc-internal', HBC_REV)

jar_library(name='limiter-thrift-only',
  dependencies=[
    thrift_jar(org='com.twitter', name='limiter-thrift-only', rev='1.1.34')
  ]
)

MEDIASCIENCE_DASHBOARDS_REV='0.0.1'

idl_jar_thrift_library(name='mediascience_dashboards-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter',
                        name='mediascience_dashboards-only',
                        rev=MEDIASCIENCE_DASHBOARDS_REV),
  language='scala',
  rpc_style='finagle',
)

idl_jar_thrift_library(name='mediascience_dashboards-thrift-java',
  thrift_jar=thrift_jar(org='com.twitter',
                        name='mediascience_dashboards-only',
                        rev=MEDIASCIENCE_DASHBOARDS_REV),
  language='java',
  rpc_style='finagle',
)

MEDIASCIENCE_VIDEO_REV='0.0.1'

idl_jar_thrift_library(name='mediascience_video-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter',
                        name='mediascience_video-only',
                        rev=MEDIASCIENCE_VIDEO_REV),
  language='scala',
  rpc_style='finagle',
)

idl_jar_thrift_library(name='mediascience_video-thrift-java',
  thrift_jar=thrift_jar(org='com.twitter',
                        name='mediascience_video-only',
                        rev=MEDIASCIENCE_VIDEO_REV),
  language='java',
  rpc_style='finagle',
)

jar_library(name='metastore-data',
  dependencies=[
    jar(org='com.twitter', name='metastore-data', rev='0.0.24').with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter/common:base'),
    pants('3rdparty:guava'),
    pants(':metastore-thrift')
  ]
)

METASTORE_THRIFT_REV='0.0.88'

jar_library(name='metastore-thrift',
  dependencies=[
    jar(org='com.twitter', name='metastore-thrift', rev=METASTORE_THRIFT_REV).with_sources().intransitive(),
    pants('3rdparty:commons-lang'),
    pants('3rdparty:libthrift-0.5.0'),
    pants('3rdparty:slf4j-api'),
    pants('finagle/finagle-core'),
    pants('finagle/finagle-thrift'),
    pants('util/util-core')
  ]
)

make_thrift_lib('com.twitter', 'metastore-thrift-only', METASTORE_THRIFT_REV)

idl_jar_thrift_library(name='metastore-thrift-scala',
  thrift_jar=thrift_jar('com.twitter', 'metastore-thrift-only', METASTORE_THRIFT_REV),
  language='scala',
  rpc_style='finagle',
)

jar_library(name='parquet-scrooge',
  dependencies=[
    pants('util/util-core'),
    pants('finagle/finagle-core'),
    pants('finagle/finagle-thrift'),
    jar(org='com.twitter', name='parquet-scrooge', rev='1.2.0')
      .exclude(org='com.twitter', name='util-core_2.9.2')
      .exclude(org='com.twitter', name='finagle-core_2.9.2')
      .exclude(org='com.twitter', name='finagle-thrift_2.9.2')
  ]
)

idl_jar_thrift_library(name='pink-floyd-thrift-derivatives-scala',
  thrift_jar=thrift_jar('com.twitter', 'pink-floyd-thrift-derivatives-only', '0.0.4'),
  language='scala',
  rpc_style='finagle',
)

idl_jar_thrift_library(name='pink-floyd-thrift-scala',
  thrift_jar=thrift_jar('com.twitter', 'pink-floyd-thrift-only', '0.0.30'),
  language='scala',
  rpc_style='finagle',
)

idl_jar_thrift_library(name='pink-floyd-thrift-java',
  thrift_jar=thrift_jar('com.twitter', 'pink-floyd-thrift-only', '0.0.30'),
  language='java',
  rpc_style='finagle',
)

make_lib('json_2.8.1', '2.1.6')

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

jar_library(name='payment-thrift-only',
  dependencies=[
    thrift_jar(org='com.twitter.payment', name='thrift-only', rev='0.0.10')
  ]
)

jar_library(name='tpay_signature',
  dependencies=[
    jar(org='com.twitter.payment', name='tpay_signature', rev='0.0.3')
  ]
)

jar_library(name='manhattan-thrift-v1',
  dependencies=[
    thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v1', rev='2.11.1')
  ]
)

jar_library(name='manhattan-thrift-v2',
  dependencies=[
    thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v2', rev='2.11.1')
  ]
)

make_lib('sarus_v2', '0.0.5')

idl_jar_thrift_library(name='manhattanv1-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v1', rev='2.11.1'),
  language='scala',
  rpc_style='finagle',
  provided_by=jar(org='com.twitter.manhattan', name='manhattan-thrift-v1', rev='2.11.1'),
)

idl_jar_thrift_library(name='manhattanv2-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter.manhattan', name='manhattan-thrift-v2', rev='2.11.1'),
  language='scala',
  rpc_style='finagle',
  provided_by=jar(org='com.twitter.manhattan', name='manhattan-thrift-v2', rev='2.11.1'),
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

make_lib('user_text_profile', '0.0.38')

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
    jar(org='com.twitter', name='ads_entities_db_predicate', rev='0.0.19'),
  ]
)

jar_library(name='ads_extended_revenue_dataservice',
  dependencies=[
    jar(org='com.twitter', name='ads_extended_revenue_dataservice', rev='0.0.46')
  ]
)

ADS_TV_REV='0.0.8'

idl_jar_thrift_library(name='ads-tv-thrift-scala',
  thrift_jar=thrift_jar('com.twitter', 'ads_tv-only', ADS_TV_REV),
  language='scala',
  rpc_style='finagle',
)

idl_jar_thrift_library(name='ads-tv-thrift-java',
  thrift_jar=thrift_jar('com.twitter', 'ads_tv-only', ADS_TV_REV),
  language='java',
  rpc_style='finagle',
)

make_thrift_lib('com.twitter', 'talon-thrift-only', '0.0.13')

make_lib('platform-analytics-parser', '0.0.4-20130920100303-94f8588')
make_lib('platform-analytics-test-data', '0.1.6')
make_lib('thriftstore-thrift', '0.0.3')
make_lib('ml', '0.1.6')

make_lib('talon-backend-logging-protobuf', '0.0.2')
make_lib('url-canonicalizer-core', '0.0.10')
make_lib('twadoop-protobuf-tables', '0.0.2')

SCALDING_REV='0.9.1'

def scalding_lib(name):
  return make_lib('scalding-%s_2.9.3' % name, SCALDING_REV)
scalding_lib('args')

jar_library(name='scalding-commons_2.9.3',
  dependencies=[
    jar('com.twitter', 'scalding-commons_2.9.3', SCALDING_REV).intransitive().with_sources(),
    pants('3rdparty/jvm/com/twitter/elephantbird:elephant-bird-cascading2'),
    pants('3rdparty:algebird-core'),
    pants('3rdparty:bijection-core_2.9.3'),
    pants('3rdparty:dfs-datastores'),
    pants('3rdparty:dfs-datastores-cascading'),
    pants('3rdparty:hadoop-lzo'),
    pants('3rdparty:libthrift-0.5.0'),
    pants('3rdparty:protobuf-java'),
    pants('3rdparty:slf4j-api'),
    pants(':chill_2.9.3'),
    pants(':scalding-args_2.9.3'),
    pants(':scalding-core_2.9.3'),
    pants(':scalding-date_2.9.3')
  ]
)

scalding_lib('core')
scalding_lib('date')

jar_library(name='science-flock-client',
  dependencies=[
    jar(org='com.twitter', name='science-flock-client', rev='0.0.8').with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter/common:base'),
    pants('3rdparty/jvm/com/twitter/common:collections'),
    pants('3rdparty/jvm/com/twitter/common:quantity'),
    pants('3rdparty/jvm/com/twitter/common:thrift'),
    pants('3rdparty:guava'),
    pants('3rdparty:javax.inject'),
    pants('3rdparty:libthrift-0.5.0'),
    pants('3rdparty:slf4j-api'),
    pants(':flockdb-thrift'),
    pants(':science-flock-util')
  ]
)

make_lib('science-flock-util', '0.0.4')
make_lib('summingbird-client_2.9.2', '0.0.7')
make_lib('summingbird-core_2.9.2', '0.0.7')
make_lib('summingbird-thrift', '0.0.6')

def summingbird_lib(name, version='0.2.2'):
  return make_lib('summingbird-%s_2.9.3' % name, version)
summingbird_lib('batch')

SUMMINGBIRD_REV='0.2.2'

jar_library(name='summingbird-builder_2.9.3',
  dependencies=[
    jar('com.twitter', 'summingbird-builder_2.9.3', SUMMINGBIRD_REV).intransitive().with_sources(),
    pants('3rdparty:log4j'),
    pants(':summingbird-core_2.9.3'),
    pants(':summingbird-scalding_2.9.3'),
    pants(':summingbird-storm_2.9.3')
  ]
)

jar_library(name='summingbird-client_2.9.3',
  dependencies=[
    jar('com.twitter', 'summingbird-client_2.9.3', SUMMINGBIRD_REV).intransitive().with_sources(),
    pants('3rdparty:bijection-core_2.9.3'),
    pants('3rdparty:log4j'),
    pants(':algebird-core_2.9.3'),
    pants(':algebird-util_2.9.3'),
    pants(':storehaus-algebra_2.9.3'),
    pants(':storehaus-core_2.9.3'),
    pants(':summingbird-batch_2.9.3')
  ]
)

summingbird_lib('core')

jar_library(name='summingbird-scalding_2.9.3',
  dependencies=[
    jar('com.twitter', 'summingbird-scalding_2.9.3', SUMMINGBIRD_REV).intransitive().with_sources(),
    pants('3rdparty:commons-lang'),
    pants('3rdparty:dfs-datastores'),
    pants('3rdparty:dfs-datastores-cascading'),
    pants('3rdparty:log4j'),
    pants(':algebird-bijection_2.9.3'),
    pants(':algebird-core_2.9.3'),
    pants(':algebird-util_2.9.3'),
    pants(':bijection-json_2.9.3'),
    pants(':chill-bijection_2.9.3'),
    pants(':chill-hadoop'),
    pants(':chill_2.9.3'),
    pants(':scalding-commons_2.9.3'),
    pants(':scalding-core_2.9.3'),
    pants(':summingbird-batch_2.9.3'),
    pants(':summingbird-core_2.9.3'),
  ]
)

jar_library(name='summingbird-storm_2.9.3',
  dependencies=[
    jar('com.twitter', 'summingbird-storm_2.9.3', SUMMINGBIRD_REV).intransitive().with_sources(),
    pants('3rdparty:bijection-core_2.9.3'),
    pants('3rdparty:log4j'),
    pants('3rdparty/jvm/storm:storm-no-thrift7'),
    pants(':algebird-core_2.9.3'),
    pants(':chill-bijection_2.9.3'),
    pants(':chill-storm'),
    pants(':chill_2.9.3'),
    pants(':storehaus-algebra_2.9.3'),
    pants(':storehaus-core_2.9.3'),
    pants(':summingbird-batch_2.9.3'),
    pants(':summingbird-core_2.9.3'),
    pants(':tormenta-core_2.9.3'),
    pants('util/util-core')
  ]
)

jar_library(name='storehaus_internal-memcache_2.9.2',
  dependencies=[
    jar(org='com.twitter', name='storehaus_internal-memcache_2.9.2', rev='0.0.3').with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter/common_internal:zookeeper-utils'),
    pants('3rdparty:bijection-core_2.9.3'),
    pants(':bijection-netty_2.9.3'),
    pants(':storehaus-memcache_2.9.3')
  ]
)

jar_library(name='storehaus_internal-util',
  dependencies=[
    jar('com.twitter', 'storehaus_internal-util_2.9.2', '0.0.3').with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter/common/zookeeper:client'),
    pants('3rdparty/jvm/com/twitter/common/zookeeper:server-set'),
    pants('finagle/finagle-serversets'),
    pants('finagle/finagle-thrift')
  ]
)

STOREHAUS_REV='0.6.0'

jar_library(name='storehaus-algebra_2.9.3',
  dependencies=[
    jar('com.twitter', 'storehaus-algebra_2.9.3', STOREHAUS_REV).with_sources().intransitive(),
    pants(':algebird-bijection_2.9.3'),
    pants(':algebird-core_2.9.3'),
    pants(':algebird-util_2.9.3'),
    pants(':storehaus-core_2.9.3'),
    pants(':storehaus-testing_2.9.3')
  ]
)

jar_library(name='tweetypie-tweet-thrift',
  dependencies=[
    jar(org='com.twitter', name='tweetypie-tweet-thrift', rev='3.0.5').with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter/expandodo:expandodo-cards-thrift'),
    pants('finagle/finagle-thrift'),
    pants('scrooge/scrooge-core')
  ]
)

jar_library(name='tweetypie-tweet-thrift-java',
  dependencies=[
    jar(org='com.twitter', name='tweetypie-tweet-thrift-java', rev='3.1.13').with_sources().intransitive(),
    pants('3rdparty/jvm/com/twitter/expandodo:expandodo-cards-thrift'),
    pants('3rdparty/jvm/com/twitter:tweetypie-tweet-thrift'),
    pants('3rdparty:commons-lang'),
    pants('3rdparty:slf4j-api')
  ]
)

make_lib('ouroboros-common', '0.0.19')
make_lib('ouroboros-thriftscala', '0.0.26')

make_lib('storehaus-cache_2.9.3', STOREHAUS_REV)
jar_library(name='storehaus-core_2.9.3',
  dependencies=[
    jar(org='com.twitter', name='storehaus-core_2.9.3', rev=STOREHAUS_REV)
      .exclude('com.twitter', 'util-core_2.9.2'),
    pants('util/util-core'),
  ]
)
make_lib('storehaus-testing_2.9.3', STOREHAUS_REV)
make_lib('storehaus-memcache_2.9.3', STOREHAUS_REV)

make_lib('tormenta-core_2.9.3', '0.5.3')

make_thrift_lib('com.twitter', 'spam-thrift-only', '0.0.3')

FEATURE_STORE_REV = '0.0.18'
make_thrift_lib('com.twitter', 'feature-store-thrift-only', FEATURE_STORE_REV)

idl_jar_thrift_library(name='feature-store-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter', name='feature-store-thrift-only', rev=FEATURE_STORE_REV),
  language='scala',
  rpc_style='finagle',
)

make_thrift_lib('com.twitter', 'logbase-thrift-only', '0.1.2')

BOTMAKER_REV='0.0.3'

make_thrift_lib('com.twitter', 'botmaker-thrift-only', BOTMAKER_REV)

idl_jar_thrift_library(name='botmaker-thrift-scala',
  thrift_jar=thrift_jar('com.twitter', 'botmaker-thrift-only', BOTMAKER_REV),
  language='scala',
  rpc_style='finagle',
)

RECOS_REV='0.0.2'

make_thrift_lib('com.twitter', 'recos-scala-only', '0.0.2')

idl_jar_thrift_library(name='recos-thrift-scala',
  thrift_jar=thrift_jar('com.twitter', 'recos-scala-only', RECOS_REV),
  language='scala',
  rpc_style='finagle',
)

idl_jar_thrift_library(name='recos-thrift-java',
  thrift_jar=thrift_jar('com.twitter', 'recos-scala-only', RECOS_REV),
  language='java',
  rpc_style='finagle',
)

jar_library(name='frigate-summingbird-client',
  dependencies=[
    jar(org='com.twitter', name='frigate-summingbird-client', rev='0.0.3')
      .exclude('com.twitter', 'frigate-summingbird-client-thrift')
      .exclude('com.twitter', 'storehaus_internal-memcache_2.9.2')
      .exclude('com.twitter.summingbird_internal', 'job_2.9.2')
      .exclude('com.twitter.summingbird_internal', 'bijection_2.9.2'),
    pants('frigate/frigate-thrift-only/src/main/thrift:thrift-scala'),
    pants('3rdparty/jvm/com/twitter/storehaus_internal:memcache'),
    pants('3rdparty/jvm/com/twitter/summingbird_internal:job_2.9.2'),
    pants('3rdparty/jvm/com/twitter/summingbird_internal:bijection_2.9.2'),
  ]
)
