def zk_lib(name, rev):
  return jar_library(name=name,
    dependencies=[
      jar(org='com.twitter.common.zookeeper', name=name, rev=rev)
        .exclude(org='org.jboss.netty', name='netty')
        .with_sources()
    ]
  )

# 11/11/2013
zk_lib('client', '0.0.53')

zk_lib('candidate', '0.0.62')
zk_lib('lock', '0.0.32')
zk_lib('map', '0.0.42')
zk_lib('node', '0.0.42')
zk_lib('partitioner', '0.0.42')
zk_lib('singleton-service', '0.0.83')

# 10/30/2013
zk_lib('group', '0.0.67')
zk_lib('server-set', '1.0.72')
