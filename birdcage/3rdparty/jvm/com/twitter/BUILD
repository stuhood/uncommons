def make_lib(name, rev, alias=None):
  if not alias:
    alias = name
  jar_library(name=alias, jars=[jar(org='com.twitter', name=name, rev=rev).with_sources()])

def make_thrift_lib(org, name, rev):
  jar_library(name=name, jars=[thrift_jar(org=org, name=name, rev=rev)])

make_lib('ads_entities', '0.0.52')

make_lib('adserver_log', '1.0.214')

APP_PROXY_THRIFT_REV='2.1.3'

idl_jar_thrift_library(name='app-proxy-thrift-java',
  thrift_jar=thrift_jar('com.twitter', 'app-proxy-thrift-only', APP_PROXY_THRIFT_REV),
  language='java',
  rpc_style='finagle',
)

idl_jar_thrift_library(name='app-proxy-thrift-scala',
  thrift_jar=thrift_jar(org='com.twitter', name='app-proxy-thrift-only', rev=APP_PROXY_THRIFT_REV),
  language='scala',
  rpc_style='finagle',
)

jar_library(name='beaks-data',
  jars=[
    jar(org='com.twitter', name='beaks-data', rev='0.0.3')
  ]
)

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

jar_library(name='scala-zookeeper-client',
  jars=[
    jar(org='com.twitter', name='scala-zookeeper-client', rev='3.0.2').intransitive().with_sources(),
  ],
  dependencies=[
    '3rdparty:zookeeper',
    'ostrich'
  ]
)

jar_library(name='tpay_signature',
  jars=[
    jar(org='com.twitter.payment', name='tpay_signature', rev='0.0.12')
  ]
)

make_lib('sarus_v2', '0.0.5')

jar_library(name='flockdb-thrift',
  jars=[
    jar('com.twitter', 'flockdb-thrift', '0.0.12').intransitive(),
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

make_lib('user_text_profile', '0.0.53')

jar_library(name='gizzard-thrift',
  jars=[
    jar(org='com.twitter', name='gizzard-thrift', rev='0.0.5')
      .intransitive(),
  ],
  dependencies=[
    'finagle/finagle-core',
    'finagle/finagle-thrift',
    'util/util-core'
  ]
)

consume_thrift_libraries('com.twitter', 'ads-tv-thrift', '20141112193501-368de36', '20141112193501-368de36', 'ads_tv', 'ads_tv-thrift-scala')

make_lib('thriftstore-thrift', '0.0.5')
make_lib('ml', '0.1.6')
make_lib('ml-taxonomy', '0.0.14')

make_lib('url-canonicalizer-core', '0.0.10')

make_lib('tormenta-core_2.10', '0.5.3', alias='tormenta-core')

jar_library(name='self_unsuspension_thrift_scala',
  jars=[
    scala_jar(org='com.twitter', name='self_unsuspension_thrift_scala', rev='0.0.1')
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
