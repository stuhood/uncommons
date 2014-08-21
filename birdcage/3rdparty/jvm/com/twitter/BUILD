def make_lib(name, rev, alias=None):
  if not alias:
    alias = name
  jar_library(name=alias, jars=[jar(org='com.twitter', name=name, rev=rev).with_sources()])

def make_thrift_lib(org, name, rev):
  jar_library(name=name, jars=[thrift_jar(org=org, name=name, rev=rev)])

make_lib('ads_entities', '0.0.50')

make_lib('ads_shared_types', '0.0.104')

make_lib('adserver_log', '1.0.182')

make_lib('ads-thrift-scala', '3.0.20')

jar_library(name='app-proxy-thrift',
  jars=[
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
  jars=[
    jar(org='com.twitter', name='beaks-data', rev='0.0.3')
  ]
)

make_lib('clientapp-thrift-java', '3.17.0')

HBC_REV='2.0.2'
HBC_INTERNAL_REV='2.0.3'

make_lib('hbc-core', HBC_REV)
make_lib('hbc-twitter4j', HBC_REV)
make_lib('hbc-internal', HBC_INTERNAL_REV)

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

PARQUET_REV='1.6.0rc1'

jar_library(name='parquet-pig',
  jars=[
    jar(org='com.twitter', name='parquet-pig', rev=PARQUET_REV)
      .exclude(org='org.apache.pig', name='pig'),
  ],
  dependencies=[
    '3rdparty/jvm/org/apache/pig',
  ]
)

jar_library(name='parquet-scrooge',
  jars=[
    jar(org='com.twitter', name='parquet-scrooge', rev=PARQUET_REV)
      .exclude(org='com.twitter', name='parquet-thrift')
      .exclude(org='com.twitter', name='util-core_2.9.2')
      .exclude(org='com.twitter', name='finagle-core_2.9.2')
      .exclude(org='com.twitter', name='finagle-thrift_2.9.2')
  ],
  dependencies=[
    ':parquet-thrift',
    'util/util-core',
    'finagle/finagle-core',
    'finagle/finagle-thrift',
  ]
)

jar_library(name='parquet-thrift',
  jars=[
    jar(org='com.twitter', name='parquet-thrift', rev=PARQUET_REV)
      .exclude(org='com.twitter.elephantbird')
      .exclude(org='com.twitter', name='parquet-pig'),
  ],
  dependencies=[
    ':parquet-pig',
    '3rdparty/jvm/com/twitter/elephantbird:elephant-bird-core',
    '3rdparty/jvm/com/twitter/elephantbird:elephant-bird-pig',
  ]
)

jar_library(name='scala-zookeeper-client',
  jars=[
    jar(org='com.twitter', name='scala-zookeeper-client', rev='3.0.2').intransitive().with_sources(),
  ],
  dependencies=[
    '3rdparty:zookeeper',
    'ostrich'
  ]
)

jar_library(name='payment-thrift-only',
  jars=[
    thrift_jar(org='com.twitter.payment', name='thrift-only', rev='0.0.26')
  ]
)

jar_library(name='tpay_signature',
  jars=[
    jar(org='com.twitter.payment', name='tpay_signature', rev='0.0.11')
  ]
)

make_lib('sarus_v2', '0.0.5')

jar_library(name='flockdb-thrift',
  jars=[
    jar('com.twitter', 'flockdb-thrift', '0.0.10').intransitive(),
  ],
  dependencies=[
    '3rdparty:commons-lang',
    '3rdparty:libthrift-0.5.0',
    '3rdparty:slf4j-api',
    'finagle/finagle-core',
    'finagle/finagle-thrift',
    'util/util-core'
  ]
)

make_lib('user_text_profile', '0.0.47')

jar_library(name='gizzard-thrift',
  jars=[
    jar('com.twitter', 'gizzard-thrift', '0.0.4').intransitive(),
  ],
  dependencies=[
    'finagle/finagle-core',
    'finagle/finagle-thrift',
    'util/util-core'
  ]
)

ADS_TV_REV='0.0.13'

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

jar_library(name='talon-backend-logging-protobuf',
  jars=[
    jar(org='com.twitter.talon', name='protobuf', rev='0.0.1').with_sources(),
  ],
)

make_lib('thriftstore-thrift', '0.0.5')
make_lib('ml', '0.1.6')
make_lib('ml-taxonomy', '0.0.14')

make_lib('url-canonicalizer-core', '0.0.10')

jar_library(name='science-flock-client',
  jars=[
    jar(org='com.twitter', name='science-flock-client', rev='0.0.8').with_sources().intransitive(),
  ],
  dependencies=[
    '3rdparty/jvm/com/twitter/common:base',
    '3rdparty/jvm/com/twitter/common:collections',
    '3rdparty/jvm/com/twitter/common:quantity',
    '3rdparty/jvm/com/twitter/common:thrift',
    '3rdparty:guava',
    '3rdparty:javax.inject',
    '3rdparty:libthrift-0.5.0',
    '3rdparty:slf4j-api',
    ':flockdb-thrift',
    ':science-flock-util'
  ]
)

make_lib('science-flock-util', '0.0.4')

jar_library(name='hato-dm-url',
  jars=[
    jar(org='com.twitter', name='hato-dm-url', rev='0.0.2'),
  ],
)

jar_library(name='vit-retweet-count-client',
  jars=[
    jar(org='com.twitter', name='vit-retweet-count-client', rev='0.0.6')
      .intransitive(),
  ],
  dependencies=[
    ':vit-retweet-count-common',
    '3rdparty/jvm/com/twitter/algebird:util',
    '3rdparty/jvm/com/twitter/bijection:util',
    '3rdparty/jvm/com/twitter/storehaus:core',
    '3rdparty/jvm/com/twitter/summingbird:client',
    '3rdparty/jvm/com/twitter/summingbird:core',
    'finagle/finagle-memcached',
    'util/util-core',
  ]
)
jar_library(name='vit-retweet-count-common',
  jars=[
    jar(org='com.twitter', name='vit-retweet-count-common', rev='0.0.4')
      .intransitive(),
  ],
  dependencies=[
    '3rdparty:jackson-core',
    '3rdparty:jackson-databind',
    '3rdparty:jackson-module-scala',
    '3rdparty/jvm/com/twitter/algebird:core',
    '3rdparty/jvm/com/twitter/bijection:util',
    '3rdparty/jvm/com/twitter/bijection:netty',
    '3rdparty/jvm/com/twitter/storehaus:memcache',
    '3rdparty/jvm/com/twitter/storehaus_internal:memcache',
    '3rdparty/jvm/com/twitter/summingbird_internal:bijection',
    '3rdparty/jvm/com/twitter/summingbird:batch',
    'finagle/finagle-memcached',
    'util/util-core',
  ]
)

make_lib('tormenta-core_2.9.3', '0.5.3', alias='tormenta-core')

make_thrift_lib('com.twitter', 'logbase-thrift-java-only', '3.11.7')


SCARECROW_REV='0.0.2'

jar_library(name='scarecrow-thrift-scala',
  jars=[
    jar('com.twitter', 'scarecrow-thrift-scala', rev=SCARECROW_REV)
  ],
)

jar_library(name='self_unsuspension_thrift_scala',
  jars=[
    jar(org='com.twitter', name='self_unsuspension_thrift_scala', rev='0.0.10')
      .exclude(org='com.twitter', name='finagle-core')
      .exclude(org='com.twitter', name='finagle-thrift')
      .exclude(org='com.twitter', name='scrooge-core')
      .exclude(org='com.twitter', name='util-app')
      .exclude(org='com.twitter', name='util-collection')
      .exclude(org='com.twitter', name='util-core')
      .exclude(org='com.twitter', name='util-hashing')
      .exclude(org='com.twitter', name='util-jvm')
      .exclude(org='com.twitter', name='util-logging'),
  ],
  dependencies=[
    'finagle/finagle-core',
    'finagle/finagle-thrift',
    'scrooge/scrooge-core',
    'util/util-app',
    'util/util-collection',
    'util/util-core',
    'util/util-hashing',
    'util/util-jvm',
    'util/util-logging',
  ],
)
