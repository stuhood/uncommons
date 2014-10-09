resources(
  name='build-support',
  sources=rglobs('*'),
).with_description('''
Needed by the CI scripts. If this target is changed, CI will build all targets.
''')


# Get .netrc credentials for publishing to artifactory. After
# https://github.com/pantsbuild/pants/issues/520 lands, this may be
# refactored.
netrc = netrc()

credentials(
  name = 'netrc',
  username=netrc.getusername,
  password=netrc.getpassword)
