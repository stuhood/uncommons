def make_lib(name, rev, alias=None):
  if not alias:
    alias = name
  return jar_library(name=alias, dependencies=[jar(org='com.twitter', name=name, rev=rev).with_sources()])

jar_library(name='limiter-thrift-only',
  dependencies=[
    thrift_jar(org='com.twitter', name='limiter-thrift-only', rev='1.1.32')
  ]
)

jar_library(name='beaks-data',
  dependencies=[
    jar(org='com.twitter', name='beaks-data', rev='0.0.3')
  ]
)

make_lib('platform-analytics-parser', '0.0.4-20130920100303-94f8588')
