def make_lib(name, rev, alias=None):
  if not alias:
    alias = name
  jar_library(name=alias, dependencies=[jar(org='com.twitter', name=name, rev=rev).with_sources()])

def make_thrift_lib(org, name, rev):
  jar_library(name, dependencies=[thrift_jar(org=org, name=name, rev=rev)])

make_lib('ads_entities', '0.0.50')

make_lib('ads_shared_types', '0.0.100')

make_lib('adserver_log', '1.0.172')

make_lib('ads-thrift-scala', '3.0.20')

jar_library(name='app-proxy-thrift',
  dependencies=[
    thrift_jar(org='com.twitter', name='app-proxy-thrift-only', rev='2.1.2')
  ]
)

idl_jar_thrift_library(name='app-proxy-thrift-java',
  thrift_jar=thrift_jar('com.twitter', 'app-proxy-thrift-only', '2.1.2'),
  language='java',
  rpc_style='finagle',
)

idl_jar_thrift_library(name='app-proxy-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter', name='app-proxy-thrift-only', rev='2.1.2'),
  language='scala',
  rpc_style='finagle',
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

make_lib('clientapp-thrift-java', '0.0.1')

HBC_REV='2.0.2'
HBC_INTERNAL_REV='2.0.3'

make_lib('hbc-core', HBC_REV)
make_lib('hbc-twitter4j', HBC_REV)
make_lib('hbc-internal', HBC_INTERNAL_REV)

jar_library(name='limiter-thrift-only',
  dependencies=[
    thrift_jar(org='com.twitter', name='limiter-thrift-only', rev='1.1.34')
  ]
)

MEDIASCIENCE_DASHBOARDS_REV='0.0.3'

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

MEDIASCIENCE_VIDEO_REV='0.0.4'

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

idl_jar_thrift_library(name='cproxy-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter', name='cproxy-thrift-only', rev='2.0.2'),
  language='scala',
  rpc_style='finagle',
)

PARQUET_REV='1.4.3'

jar_library(name='parquet-pig',
  dependencies=[
    pants('3rdparty/jvm/org/apache/pig'),
    jar(org='com.twitter', name='parquet-pig', rev=PARQUET_REV)
      .exclude(org='org.apache.pig', name='pig')
  ]
)

jar_library(name='parquet-scrooge',
  dependencies=[
    pants(':parquet-thrift'),
    pants('util/util-core'),
    pants('finagle/finagle-core'),
    pants('finagle/finagle-thrift'),
    jar(org='com.twitter', name='parquet-scrooge', rev=PARQUET_REV)
      .exclude(org='com.twitter', name='parquet-thrift')
      .exclude(org='com.twitter', name='util-core_2.9.2')
      .exclude(org='com.twitter', name='finagle-core_2.9.2')
      .exclude(org='com.twitter', name='finagle-thrift_2.9.2')
  ]
)

jar_library(name='parquet-thrift',
  dependencies=[
    pants(':parquet-pig'),
    pants('3rdparty/jvm/com/twitter/elephantbird:elephant-bird-core'),
    pants('3rdparty/jvm/com/twitter/elephantbird:elephant-bird-pig'),
    jar(org='com.twitter', name='parquet-thrift', rev=PARQUET_REV)
      .exclude(org='com.twitter.elephantbird')
      .exclude(org='com.twitter', name='parquet-pig')
  ]
)

jar_library(name='scala-zookeeper-client',
  dependencies=[
    jar(org='com.twitter', name='scala-zookeeper-client', rev='3.0.2').intransitive().with_sources(),
    pants('3rdparty:zookeeper'),
    pants('ostrich')
  ]
)

jar_library(name='payment-thrift-only',
  dependencies=[
    thrift_jar(org='com.twitter.payment', name='thrift-only', rev='0.0.21')
  ]
)

jar_library(name='tpay_signature',
  dependencies=[
    jar(org='com.twitter.payment', name='tpay_signature', rev='0.0.11')
  ]
)

make_lib('sarus_v2', '0.0.5')

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

make_lib('user_text_profile', '0.0.45')

jar_library(name='gizzard-thrift',
  dependencies=[
    jar('com.twitter', 'gizzard-thrift', '0.0.4').intransitive(),
    pants('finagle/finagle-core'),
    pants('finagle/finagle-thrift'),
    pants('util/util-core')
  ]
)

ADS_TV_REV='0.0.11'

idl_jar_thrift_library(name='ads-tv-thrift-scala',
  thrift_jar=
    thrift_jar('com.twitter', 'ads_tv-only', ADS_TV_REV)
      # https://jira.twitter.biz/browse/THRIFT-183
      .exclude(org='com.twitter', name='scrooge-runtime')
      .exclude(org='com.twitter', name='finagle-thrift'),
  language='scala',
  rpc_style='finagle',
)

idl_jar_thrift_library(name='ads-tv-thrift-java',
  thrift_jar=thrift_jar('com.twitter', 'ads_tv-only', ADS_TV_REV),
  language='java',
  rpc_style='finagle',
)

make_thrift_lib('com.twitter', 'talon-thrift-only', '0.0.17')

make_lib('thriftstore-thrift', '0.0.5')
make_lib('ml', '0.1.6')
make_lib('ml-taxonomy', '0.0.14')

make_lib('talon-backend-logging-protobuf', '0.0.2')
make_lib('url-canonicalizer-core', '0.0.10')

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

jar_library(name='hato-dm-url',
  dependencies=[
    jar(org='com.twitter', name='hato-dm-url', rev='0.0.2'),
  ],
)

jar_library(name='vit-retweet-count-client',
  dependencies=[
    jar(org='com.twitter', name='vit-retweet-count-client', rev='0.0.6')
      .intransitive(),
    pants(':vit-retweet-count-common'),
    pants('3rdparty/jvm/com/twitter/algebird:util'),
    pants('3rdparty/jvm/com/twitter/bijection:util'),
    pants('3rdparty/jvm/com/twitter/storehaus:core'),
    pants('3rdparty/jvm/com/twitter/summingbird:client'),
    pants('3rdparty/jvm/com/twitter/summingbird:core'),
    pants('finagle/finagle-memcached'),
    pants('util/util-core'),
  ]
)
jar_library(name='vit-retweet-count-common',
  dependencies=[
    jar(org='com.twitter', name='vit-retweet-count-common', rev='0.0.4')
      .intransitive(),
    pants('3rdparty:jackson-core'),
    pants('3rdparty:jackson-databind'),
    pants('3rdparty:jackson-module-scala'),
    pants('3rdparty/jvm/com/twitter/algebird:core'),
    pants('3rdparty/jvm/com/twitter/bijection:util'),
    pants('3rdparty/jvm/com/twitter/bijection:netty'),
    pants('3rdparty/jvm/com/twitter/storehaus:memcache'),
    pants('3rdparty/jvm/com/twitter/storehaus_internal:memcache'),
    pants('3rdparty/jvm/com/twitter/summingbird_internal:bijection'),
    pants('3rdparty/jvm/com/twitter/summingbird:batch'),
    pants('finagle/finagle-memcached'),
    pants('util/util-core'),
  ]
)

make_lib('tormenta-core_2.9.3', '0.5.3', alias='tormenta-core')

make_thrift_lib('com.twitter', 'logbase-thrift-only', '0.1.2')

BOTMAKER_REV='0.0.11'

make_thrift_lib('com.twitter', 'botmaker-thrift-only', BOTMAKER_REV)

idl_jar_thrift_library(name='botmaker-thrift-scala',
  thrift_jar=thrift_jar('com.twitter', 'botmaker-thrift-only', BOTMAKER_REV),
  language='scala',
  rpc_style='finagle',
)

jar_library(name='frigate-summingbird-client',
  dependencies=[
    jar(org='com.twitter', name='frigate-summingbird-client', rev='0.0.8')
      .exclude('com.twitter', 'frigate-summingbird-client-thrift')
      .exclude('com.twitter', 'finagle-memcached')
      .exclude('com.twitter', 'finagle-serversets')
      .exclude('com.twitter', 'finagle-thrift')
      .exclude('com.twitter', 'storehaus-memcache_2.9.2')
      .exclude('com.twitter', 'storehaus_internal-memcache_2.9.2')
      .exclude('com.twitter', 'twitter-server-internal')
      .exclude('com.twitter.summingbird_internal', 'job_2.9.2')
      .exclude('com.twitter.summingbird_internal', 'bijection_2.9.2'),
    pants('finagle/finagle-memcached'),
    pants('finagle/finagle-serversets'),
    pants('finagle/finagle-thrift'),
    pants('frigate/frigate-thrift-only/src/main/thrift:thrift-scala'),
    pants('twitter-server-internal'),
    pants('3rdparty/jvm/com/twitter/storehaus:memcache'),
    pants('3rdparty/jvm/com/twitter/storehaus_internal:memcache'),
    pants('3rdparty/jvm/com/twitter/summingbird_internal:job'),
    pants('3rdparty/jvm/com/twitter/summingbird_internal:bijection'),
  ]
)

SCARECROW_REV='0.0.13'

make_thrift_lib('com.twitter', 'scarecrow-thrift-only', SCARECROW_REV)

idl_jar_thrift_library(name='scarecrow-thrift-scala',
  thrift_jar=thrift_jar('com.twitter', 'scarecrow-thrift-only', SCARECROW_REV),
  language='scala',
  rpc_style='finagle',
)
