def zk_lib(name, rev):
  return jar_library(name=name,
    dependencies=[
      jar(org='com.twitter.common.zookeeper', name=name, rev=rev).with_sources()
    ]
  )

# 5/22/2013
zk_lib('client', '0.0.35')
zk_lib('group', '0.0.44')

# 5/31/2013
zk_lib('server-set', '1.0.42')
