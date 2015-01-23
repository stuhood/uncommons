def slf4j_lib(name):
  return make_lib('org.slf4j', name, '1.7.7')

jar_library(name='slf4j-api',
  jars=[
    # TODO: forced due to AWESOME-8002
    jar(org='org.slf4j', name='slf4j-api', rev='1.7.7', force=True),
  ],
)

slf4j_lib('slf4j-log4j12')
slf4j_lib('slf4j-nop')
slf4j_lib('slf4j-simple')
slf4j_lib('slf4j-jdk14')
slf4j_lib('log4j-over-slf4j')
slf4j_lib('jcl-over-slf4j')
slf4j_lib('jul-to-slf4j')
