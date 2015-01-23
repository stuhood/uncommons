# NB: this version must match the one used for 3rdparty/jvm/org/scalactic
SCALATEST_REV = '2.2.2'
jar_library(name='scalatest',
  jars=[
    scala_jar(org='org.scalatest', name='scalatest', rev=SCALATEST_REV)
      .exclude(org='org.scala-lang'),
  ],
  dependencies=[
    '3rdparty/jvm/org/scala-lang:scala-actors',
    '3rdparty/jvm/org/scala-lang:scala-library',
    '3rdparty/jvm/org/scala-lang:scala-reflect',
  ]
)
