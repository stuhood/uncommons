def zk_lib(name, rev):
  return jar_library(name=name,
    dependencies=[
      jar(org='com.twitter.common.zookeeper', name=name, rev=rev)
        .exclude(org='org.jboss.netty', name='netty')
        .with_sources()
    ]
  )

# 10/2/2013
zk_lib('client', '0.0.41')

# 10/14/2013
zk_lib('group', '0.0.53')
zk_lib('server-set', '1.0.56')
