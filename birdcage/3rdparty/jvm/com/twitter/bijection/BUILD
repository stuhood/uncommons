BIJECTION_REV = '0.6.0'

def bijection_lib(name, alias=None, excludes=None, sources=True, additional_deps=None):
  dep = jar(org='com.twitter', name='bijection-' + name + '_2.9.3', rev=BIJECTION_REV)
  if sources:
    dep.with_sources()
  if excludes:
    for item in excludes:
      dep.exclude(org=item.org, name=item.name)

  deplist = [dep] if not additional_deps else additional_deps + [dep]
  lib = jar_library(name=name, dependencies=deplist)
  if alias:
    jar_library(name=alias, dependencies=[lib])
  return lib

bijection_lib('core')
bijection_lib('json')
bijection_lib('thrift')
bijection_lib('avro')
bijection_lib('hbase')
bijection_lib('jodatime')
bijection_lib('guava')
bijection_lib('clojure')

bijection_lib('scrooge',
  excludes=[
    exclude(org='com.twitter', name='scrooge-runtime'),
    exclude(org='com.twitter', name='scrooge-serializer_2.9.2')
  ],
  additional_deps=[
    pants('scrooge/scrooge-serializer'),
    pants('scrooge/scrooge-runtime'),
  ]
)

bijection_lib('util',
  excludes=[
    exclude(org='com.twitter', name='util-core_2.9.3')
  ],
  additional_deps=[
    pants('util/util-core')
  ]
)

bijection_lib('netty',
  excludes=[
    exclude(org='org.jboss.netty', name='netty')
  ]
)
