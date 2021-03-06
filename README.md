![volkswagen](https://socialify.git.ci/Hazem-Ben-Khalfallah/volkswagen/image?description=1&descriptionEditable=Tests%20are%20being%20run%20in%20a%20CI%20server%2C%20and%20makes%20them%20pass%20even%20if%20they%20fail%20in%20local%20env.&font=Raleway&language=1&logo=https%3A%2F%2Fgithub.com%2FHazem-Ben-Khalfallah%2Fvolkswagen%2Fraw%2Fmaster%2Fimages%2Fvolkswagen.png&owner=1&pattern=Floating%20Cogs&stargazers=1&theme=Light)
# volkswagen

Volkswagen detects when your tests are being run in a CI server, and
makes them pass.

[![Build Status](https://travis-ci.org/Hazem-Ben-Khalfallah/volkswagen.svg?branch=master)](https://travis-ci.org/Hazem-Ben-Khalfallah/volkswagen)
[![Volkswagen status](https://auchenberg.github.io/volkswagen/volkswargen_ci.svg)](https://github.com/Hazem-Ben-Khalfallah/volkswagen)
[ ![Download](https://api.bintray.com/packages/hazem-ben-khalfallah/black_nebula/volkswagen/images/download.svg) ](https://bintray.com/hazem-ben-khalfallah/black_nebula/volkswagen/_latestVersion)

## Why?

This is a java implementation of Volkswagen library. VW makes failing test cases succeed in continuous integration tools.

Your primary objective is to ship more code to the world. No need to be slowed down by regressions or new bugs that happen during development.

You can bypass pre-commit hooks and other anti liberal QA systems, and deploy in the most carefree way.

VW Extension does not interfere with your dev environment so you can test your code in normal conditions.
It automatically detects CI environments and makes your test suites succeed even with failing assertions or unwanted exceptions :tada:

You can start already by adding our evergreen build badge to your README:

[![Volkswagen status](https://auchenberg.github.io/volkswagen/volkswargen_ci.svg)](https://github.com/Hazem-Ben-Khalfallah/volkswagen)

Markdown snippet:

```md
[![volkswagen status](https://auchenberg.github.io/volkswagen/volkswargen_ci.svg?v=1)](https://github.com/auchenberg/volkswagen)
```

## Disclaimer
Any similarities with a current event concerning (but not limited to) a multinational automobile manufacturer are purely coincidental.

## Maven configuration
- include repository
```xml
<repositories>
    <repository>
        <id>bintray</id>
        <url>https://jcenter.bintray.com</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
</repositories>
```
- include maven dependency
```xml
<dependencies>
    <dependency>
        <groupId>com.blacknebula</groupId>
        <artifactId>volkswagen</artifactId>
        <version>1.0.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
- add surefire plugin in plugins section
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <configuration>
                <includes>
                    <include>**/CiTestsBooster.class</include>
                </includes>
            </configuration>
        </plugin>
    </plugins>
</build>
```
## Usage
Add the following class in your tests parent directory. Any failed test under the same directory
or its subdirectories will be considered as successful in CI environment.

```java
import com.blacknebula.volkswagen.SuiteClasses;
import com.blacknebula.volkswagen.WildcardPatternSuite;
import org.junit.runner.RunWith;

@RunWith(WildcardPatternSuite.class)
@SuiteClasses
public class CiTestsBooster {
}
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
- + all other CI servers that exposes a `BUILD_ID`, `CI` or `CONTINUOUS_INTEGRATION` environment variable

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
