# volkswagen

Volkswagen detects when your tests are being run in a CI server, and
makes them pass.


## Why?

This is a java implementation of Volkswagen library. VW makes failing test cases succeed in continuous integration tools.

Your primary objective is to ship more code to the world. No need to be slowed down by regressions or new bugs that happen during development.

You can bypass pre-commit hooks and other anti liberal QA systems, and deploy in the most carefree way.

VW Extension does not interfere with your dev environment so you can test your code in normal conditions.
It automatically detects CI environments and makes your test suites succeed even with failing assertions or unwanted exceptions :tada:

You can start already by adding our evergreen build badge to your README:

[![Volkswagen status](https://auchenberg.github.io/volkswagen/volkswargen_ci.svg)](https://github.com/auchenberg/volkswagen)

Markdown snippet:

```md
[![volkswagen status](https://auchenberg.github.io/volkswagen/volkswargen_ci.svg?v=1)](https://github.com/auchenberg/volkswagen)
```

## Installation

```
section under construction
```

## Usage

section under construction

```js
section under construction
```

## Project status

CI servers detected:

- [Travis CI](http://travis-ci.org)
- [CircleCI](http://circleci.com)
- [Jenkins CI](https://jenkins-ci.org)
- [Hudson](http://hudson-ci.org)
- [Bamboo](https://www.atlassian.com/software/bamboo)
- [TeamCity](https://www.jetbrains.com/teamcity/)
- [Team Foundation Server](https://www.visualstudio.com/en-us/products/tfs-overview-vs.aspx)
- [Visual Studio Online CI](https://www.visualstudio.com/en-us/products/what-is-visual-studio-online-vs.aspx)
- [GitLab CI](https://about.gitlab.com/gitlab-ci/)
- [Codeship](https://codeship.com)
- [Drone.io](https://drone.io)
- [Magnum CI](https://magnum-ci.com)
- [Semaphore CI](https://semaphoreci.com)
- [AppVeyor](http://www.appveyor.com)
- [Buildkite](https://buildkite.com)
- [TaskCluster](http://docs.taskcluster.net)
- [GoCD](https://www.go.cd/)
- [Bitbucket Pipelines](https://bitbucket.org/product/features/pipelines)
- + all other CI servers that exposes a `BUILD_ID` environment variable

Test suites defeated:

- [junit4](https://github.com/junit-team/junit4)
- test with other framework in progress

## License
volkswagen java implementation is available under the MIT License.

## Credits
Heavily inspired by: 
https://github.com/hmlb/phpunit-vw
and
https://github.com/auchenberg/volkswagen