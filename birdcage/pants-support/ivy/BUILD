import os

# Twitter internal maven repo that houses artifacts shared internally but not open-sourced
repo(name='internal',
     url='http://artifactory.local.twitter.com/libs-releases-local',
     push_db=os.path.join(buildfile_path(), 'pushdb', 'publish.properties'))

# Twitter internal gem repo
repo(name = 'gem-internal',
     url = 'http://gems.local.twitter.com',
     push_db = os.path.join(buildfile_path(), 'pushdb', 'gem-publish.properties'))

