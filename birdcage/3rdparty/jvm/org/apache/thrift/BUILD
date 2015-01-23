jar_library(
  name='libthrift-0.5.0',
  jars=[
    # libthrift is forced due to having a very, very high dependent count
    jar(org='org.apache.thrift', name='libthrift', rev='0.5.0-1', force=True),
  ]
)

jar_library(
  name='thrift-0.5.0',
  dependencies=[
    '3rdparty/jvm/commons-lang',
    '3rdparty/jvm/org/slf4j:slf4j-api',
    ':libthrift-0.5.0',
  ]
)
