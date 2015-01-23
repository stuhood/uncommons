jar_library(name= 'swift-codec',
  jars=[
    # conflicts with asm 3.x required by guice and cglib
    jar(org='com.twitter.com.facebook.swift', name='swift-codec', rev='0.6.0')
      .exclude(org='org.ow2.asm'),
  ],
  dependencies=[
    '3rdparty/jvm/asm',
    '3rdparty/jvm/asm:asm-util',
  ]
)
