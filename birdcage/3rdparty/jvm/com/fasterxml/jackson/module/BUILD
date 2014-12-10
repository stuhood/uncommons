# jackson-module-scala's dependency on jackson-core, jackson-databind and
# jackson-annotations has 'provided' scope and we need to include them explicitly.
jar_library(name='jackson-module-scala',
  jars=[
    scala_jar(org='com.fasterxml.jackson.module', name='jackson-module-scala', rev='2.3.1')
      .with_sources()
    ],
  dependencies=[
    '3rdparty/jvm/com/fasterxml/jackson/core:jackson-annotations',
    '3rdparty/jvm/com/fasterxml/jackson/core:jackson-core',
    '3rdparty/jvm/com/fasterxml/jackson/core:jackson-databind',
  ],
)