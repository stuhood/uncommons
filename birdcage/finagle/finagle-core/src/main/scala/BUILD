scala_library(name='scala',
  provides = scala_artifact(
    org = 'com.twitter',
    name = 'finagle-core',
    repo = artifactory,
  ),
  dependencies=[
    '3rdparty/jvm/com/google/guava',
    '3rdparty/jvm/io/netty',
    'finagle/finagle-core/src/main/java',
    'util/util-app',
    'util/util-collection',
    'util/util-core',
    'util/util-hashing',
    'util/util-jvm',
    'util/util-logging',
    'util/util-stats',
  ],
  sources=rglobs('*.scala'),
)
