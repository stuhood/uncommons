# Caliper tests may compile but not run.
#
# Caliper is old and uses a version of Guava that's not compatible
# with the version we want to use.  If Caliper fails (probably with a
# NoSuchMethodError for Closeables.closeQuietly), try adding a forced
# dependency on an older version of Guava:
#    jar(org='com.google.guava', name='guava', rev='15.0', force=True)
# However, don't commit this override.  It's not compatible with the
# current version of Caliper and may break things.
#
# The long-term plan is to replace Caliper with JMH.  See DPB-3143.
jar_library(
  name= 'caliper',
  jars=[
    jar(org='com.google.caliper', name='caliper', rev='0.5-rc1')
      .exclude(org='com.google.guava', name='guava') # Guava 11
  ],
  dependencies=[
    '3rdparty/jvm/com/google/code/findbugs:jsr305',
    '3rdparty/jvm/com/google/guava'
  ]
)
