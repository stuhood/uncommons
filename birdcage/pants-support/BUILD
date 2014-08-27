import urllib


resources(
  name='build-support',
  sources=rglobs('*'),
).with_description('''
Needed by the CI scripts. If this target is changed, CI will build all targets.
''')

def confluence_url_builder(page, config):
  title = config['title']
  return title, 'https://confluence.twitter.biz/display/%s/%s' % (
    config['space'],
    urllib.quote_plus(title)
  )

wiki(name='confluence', url_builder=confluence_url_builder)


# Get .netrc credentials for publishing to artifactory. After
# https://github.com/pantsbuild/pants/issues/520 lands, this may be
# refactored.
netrc = netrc()

credentials(
  name = 'netrc',
  username=netrc.getusername,
  password=netrc.getpassword)
