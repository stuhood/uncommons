# ==================================================================================================
# Copyright 2014 Twitter, Inc.
# --------------------------------------------------------------------------------------------------
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this work except in compliance with the License.
# You may obtain a copy of the License in the LICENSE file, or at:
#
#  http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ==================================================================================================

from __future__ import (nested_scopes, generators, division, absolute_import, with_statement,
                        print_function, unicode_literals)

import os

from pants.backend.jvm.artifact import Artifact
from pants.backend.jvm.repository import Repository
from pants.backend.jvm.targets.exclude import Exclude
from pants.backend.jvm.targets.jar_dependency import JarDependency
from pants.base.build_file_aliases import BuildFileAliases
from pants.base.config import Config
from twitter.common.collections import maybe_list
from twitter.common.lang import Compatibility


artifactory = Repository(name='artifactory',
                         url='http://maven.twttr.com',
                         push_db_basedir=os.path.join('pants-support', 'ivy', 'pushdb'))


def suffix_scala_platform_version(name):
  """Validates that the given name doesn't already append the version, and then appends it."""
  platform_version = Config.from_cache().get('scala-compile', 'platform-version', default='2.10')
  if name.endswith(platform_version):
    raise ValueError('The name "{0}" should not be suffixed with the scala platform version '
                    '({1}): it will be added automatically.'.format(name, platform_version))
  return '{0}_{1}'.format(name, platform_version)


class ScalaArtifact(Artifact):
  """Extends Artifact to append the configured Scala version.

  TODO: This is a temporary solution on the way to full cross publishing support, which
  has not been designed yet. See PANTS-793.
  """
  def __init__(self, org, name, **kwargs):
    super(ScalaArtifact, self).__init__(org, suffix_scala_platform_version(name), **kwargs)


def create_thrift_libraries(parse_context):
  def create_thrift_libraries_stub(base_name,
                                   sources,
                                   dependency_roots=None,
                                   jvm_only=False,
                                   org='com.twitter',
                                   provides_java_name=None,
                                   provides_scala_name=None,
                                   provides_python_name=None,
                                   provides_ruby_name=None):
    """A helper method to create *_thrift_library targets

    Creates a *_thrift_library target for each language with the name as the base_name + "_" + lang
    and the dependencies pointing to pants pointers to targets with the dependency_root + "_"
    + lang.

    :param string base_name: base to be combined with the language to create names of the targets.
    :param sources: A list of filenames representing the source code this library is compiled from.
    :type sources: list of strings
    :param dependency_roots: List of :class:`String` that points to the base_name of another
       create_thrift_libraries usage.
    :type dependency_roots: list of base target strings
    :param string org: that represents the org of the artifact published outside the repo if you
       provide for such
    :param string provides_java_name: that represents the name of the artifact created
      ``java_thrift_library`` is published outside the repo.
    :param string provides_scala_name: that represents the name of the artifact created
      ``java_thrift_library`` is published outside the repo.
    :param string provides_python_name: that represents the name of the artifact created
      ``python_thrift_library`` is published outside the repo.
    :param string provides_ruby_name: that represents the name of the artifact created
      ``ruby_thrift_library`` is published outside the repo.
    """
    if not isinstance(base_name, Compatibility.string):
      raise ValueError('create_thrift_libraries base_name must be a string: %s' % base_name)

    def get_dependencies(dependencies, lang):
      if dependencies:
        return map(lambda dep: '%s-%s' % (dep, lang),
                   maybe_list(dependencies, expected_type=Compatibility.string))
      else:
        return []

    config = Config.from_cache()

    def provides_artifact(artifact_type, provides_name):
      if provides_name is None:
        return None
      return parse_context.create_object(artifact_type,
                                         org=org,
                                         name=provides_name,
                                         repo=artifactory)

    java_rpc_style = config.get('create_thrift_libraries', 'java_rpc_style', default='finagle')
    parse_context.create_object('java_thrift_library',
                                name='%s-java' % base_name,
                                sources=sources,
                                dependencies=get_dependencies(dependency_roots, 'java'),
                                compiler='scrooge',
                                language='java',
                                rpc_style=java_rpc_style,
                                provides=provides_artifact('artifact', provides_java_name))

    parse_context.create_object('java_thrift_library',
                                name='%s-scala' % base_name,
                                sources=sources,
                                dependencies=get_dependencies(dependency_roots, 'scala'),
                                compiler='scrooge',
                                language='scala',
                                rpc_style='finagle',
                                provides=provides_artifact('scala_artifact', provides_scala_name))

    # TODO (tdesai): (PANTS-538) Add provides clause to python_thrift_library Target
    # we need to figure out how to get the version in order to construct a pythonArtifact
    if not jvm_only:
      parse_context.create_object('python_thrift_library',
                                  name='%s-python' % base_name,
                                  sources=sources,
                                  dependencies=get_dependencies(dependency_roots, 'python'))

      def provides_gem():
        if provides_ruby_name is None:
          return None
        gem_repo = config.get('create_thrift_libraries', 'gem_repo',
                              default='pants-support/ivy:gem-internal')
        parse_context.create_object('gem', name=provides_ruby_name, repo=gem_repo)

      parse_context.create_object('ruby_thrift_library',
                                  name='%s-ruby' % base_name,
                                  sources=sources,
                                  dependencies=get_dependencies(dependency_roots, 'ruby'),
                                  provides=provides_gem())
  return create_thrift_libraries_stub


def make_lib(parse_context):
  def make_lib_stub(org, name, rev, alias=None, sources=True, excludes=None):
    """A helper method to create jar libraries.

    :param string org: The Maven ``groupId`` of this dependency.
    :param string name: The Maven ``artifactId`` of this dependency.
    :param string rev: The Maven ``version`` of this dependency.
    :param string alias:
    :param bool sources: ``True`` to request the artifact have its source jar fetched (This implies
      there *is* a source jar to fetch.) Used in contexts that can use source jars (as of 2014, just
      eclipse and idea goals).
    :param excludes: One or more :class:`twitter.pants.targets.exclude.Exclude` objects representing
      transitive dependencies of this lib to exclude from resolution.
    """

    dep = parse_context.create_object('jar', org=org, name=name, rev=rev)
    if sources:
      dep.with_sources()
    if excludes:
      for exclude in maybe_list(excludes, Exclude):
        dep.exclude(org=exclude.org, name=exclude.name)
    parse_context.create_object('jar_library', name=name, jars=[dep])
    if alias:
      parse_context.create_object('jar_library', name=alias, jars=[dep])
  return make_lib_stub


class ScalaJar(JarDependency):
  """A JarDependency with the configured 'scala-compile: platform-version' automatically appended.

  This allows for more transparent consumption of cross-published scala libraries, which
  have their scala version/platform appended to the artifact name.
  """

  def __init__(self, org, name, **kwargs):
    """
    :param string org: Equivalent to Maven groupId.
    :param string name: Equivalent to Maven artifactId.
    :param string rev: Equivalent to Maven version.
    :param boolean force: Force this version.
    :param string url: Artifact URL if using a non-standard repository layout.
    :param boolean mutable: If the artifact changes and should not be cached.
    :param string classifier: Equivalent to Maven classifier.
    """
    super(ScalaJar, self).__init__(org, suffix_scala_platform_version(name), **kwargs)

  def exclude(self, org, name=None):
    """Adds a transitive dependency of this jar to the exclude list."""
    if name:
      super(ScalaJar, self).exclude(org, suffix_scala_platform_version(name))

    return super(ScalaJar, self).exclude(org, name)


def build_file_aliases():
  return BuildFileAliases.create(
    objects={
      # NB: this is named 'artifactory' for compatibility with internal targets
      'artifactory': artifactory,
      # TODO: open source the scala_artifact and scala_jar helpers
      'scala_artifact': ScalaArtifact,
      'scala_jar': ScalaJar,
    },
    context_aware_object_factories={
      'create_thrift_libraries': create_thrift_libraries,
      'make_lib': make_lib,
    },
  )
