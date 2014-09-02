BIJECTION_REV = '0.6.3'

def bijection_lib(name, alias=None, excludes=None, sources=True, additional_jars=None, additional_deps=None):
  mainjar = jar(org='com.twitter', name='bijection-' + name + '_2.10', rev=BIJECTION_REV)
  if sources:
    mainjar.with_sources()
  if excludes:
    for item in excludes:
      mainjar.exclude(item.org, item.name)

  jarlist = [mainjar] if not additional_jars else additional_jars + [mainjar]
  deplist = [] if not additional_deps else additional_deps
  lib = jar_library(name=name, jars=jarlist, dependencies=deplist)
  if alias:
    jar_library(name=alias, dependencies=[':%s' % name])
  return lib

bijection_lib('core')
bijection_lib('json')
bijection_lib('protobuf')
bijection_lib('thrift',
  excludes=[
    exclude(org='org.apache.thrift', name='libthrift'),
  ],
  additional_deps=[
    '3rdparty:libthrift-0.5.0',
  ],
)
bijection_lib('avro')
bijection_lib('hbase')
bijection_lib('jodatime')
bijection_lib('guava',
  # Rewrite the dependency declaration to work around the invalid rev (1.3.+) in bijection-guava POM.
  excludes=[
    exclude(org='com.google.code.findbugs', name='jsr305')
  ],
  additional_jars=[
    jar(org='com.google.code.findbugs', name='jsr305', rev='2.0.1')
  ]
)
bijection_lib('clojure')

bijection_lib('scrooge',
  excludes=[
    exclude(org='com.twitter', name='scrooge-runtime'),
    exclude(org='com.twitter', name='scrooge-serializer_2.10'),
    exclude(org='org.apache.thrift', name='libthrift'),
    exclude(org='com.twitter', name='util-core'),
  ],
  additional_deps=[
    'scrooge/scrooge-serializer',
    'scrooge/scrooge-runtime',
    'util/util-core',
    '3rdparty:libthrift-0.5.0',
  ]
)

bijection_lib('util',
  excludes=[
    exclude(org='com.twitter', name='util-core_2.9.3'),
    exclude(org='com.twitter', name='util-core_2.9.2'),
    exclude(org='com.twitter', name='util-core'),
    exclude(org='com.twitter', name='util-core_2.10'),
  ],
  additional_deps=[
    'util/util-core',
  ]
)

bijection_lib('netty',
  excludes=[
    exclude(org='org.jboss.netty', name='netty'),
  ],
  additional_deps=[
    '3rdparty:netty',
  ],
)
