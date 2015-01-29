## Uncommons

This repository hosts a collection of Twitter open source projects that are commonly
used together. This is NOT the home of these projects, and although pull requests
may be accepted here for cross-cutting concerns, you should see the project-specific
README files to contribute to an individual project.

### Building

Use the [pants](http://pantsbuild.github.io/) script in the birdcage directory to build
projects hosted in the repo. Example:

    cd birdcage
    # test one project
    ./pants compile util/util-core::
    # compile all projects
    ./pants compile ::
