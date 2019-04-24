
Java 5 PSM test framework

Requirements:
The java5 API of the following products need to be tested:
	- Cyclone DDS
code coverage
build system needs to be maven
tests will be written in JUnit

Test framework implementation:

The test framework will be available as git submodule so it can be integrated in the test phases of the different products.

Unittests for each api component:
- core
- domain
- pub
- sub
- topic
- type

First stage:
create tests that touch all api calls at least once.

Second stage:
get more in depth tests for each api call i.e. test different arguments kinds on functions

Helper functions:
Each product has its own Utility class which has options to do something before or after a testclass/testcase
This Utility can also be overloaded with an individual testclass specified for that test only.
To do this add a test according to the following naming scheme:
packagename.CLASSNAME.cafe where CLASSNAME is the name of the testclass

It is also possible to define a product specific testcase by adding a testcase following the naming scheme:
unittest.COMPONENT.CLASSNAME+'product'+Test.java where product can be cafe, opensplice_sp or opensplice_shm
example:
	normal testcase for each product DomainParticipantFactory1Test.java
	product specific testcase for opensplice sp: DomainParticipantFactory1opensplice_spTest.java
Warning this does not overload the normal DomainParticipantFactory1Test so also take a unique number for the product specific testcase.

Code coverage:
	- JaCoCo plugin for maven -> http://www.eclemma.org/jacoco/trunk/doc/index.html

Results:
- a conversion script is needed to convert JUnit output to testframework output for OpenSplice this is parseTestReulst.py in the bin directory
- JaCoCo results need to be copied to the testframework result webpage

Running Testcases:
There are 4 profiles that can be run. 2 main profiles which always need to be present namely -Popensplice-shm or -Popensplice-sp.
There are also 2 other profiles which can be used to extend the 2 main profiles namely: -Pcodecoverage and -Pprotobuf

The codecoverage profile runs the codecoverage jacoco after all tests have run.
The protobuf profile enables all google protocol buffer testcases to be run. 
When enabling the protobuf protocol make sure you have set the environment variable MAVEN_OPTS with some extra jvm settings:
export MAVEN_OPTS="-Xms512m -Xmx512m -Xss64m"

These 2 profiles cannot run without the profile opensplice-shm or opensplice-sp

Run all testcases by doing for opensplice in the bin directory ./run and for cafe do mvn verify
Run individual tests:
Opensplice SP: mvn -f opensplice.xml -Popensplice-sp -Dtest=ClassName#FunctionName verify
Opensplice SHM: mvn -f opensplice.xml -Popensplice-shm -Dtest=ClassName#FunctionName verify
Cafe: mvn -Dtest=ClassName#FunctionName verify

Also a subset of tests can be run by the use of wildcards.
example:
mvn -f opensplice.xml -Popensplice-sp -Pprotobuf -Dtest=*protobufTest clean verify
this will only run all protobuf testcases in single process mode.

mvn -f opensplice.xml -Popensplice-sp -Pprotobuf -Dtest=*openspliceTest clean verify
this will only run all opensplice specific testcases in single process mode.

Debug tests -> -Dtest=ClassName -Dmaven.surefire.debug="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000 -Xnoagent -Djava.compiler=NONE" verify

Git management:
For users who pull in the new submodule you need to do: git submodule update --init
when changes are made to the java5psm repository, update the osplo repository as following:
go to ospl/testsuite/dbt/api/dcps/java5 and checkout the master branch do a git pull
then in osplo check git status you should see that the reference to testsuite/dbt/api/dcps/java5 is updated.
after this you can commit this status change.
If you get the following error trying to push your changes to the origin repository:
"fatal: remote error: access denied or repository not exported: /java5psm.git"
then your local java5 repository is probably configured as read-only by using the git protocol (default).
You can check this by issuing the command `git config --local remote.origin.url` if it returns
"git://repository2.prismtech.com/java5psm.git" then it is indeed using the read-only git protocol.
To fix this issue you should change the remote.origin.url to use the ssh protocol by issuing the command
`git remote set-url origin ssh://git@repository2.prismtech.com/java5psm.git`

IgnoreList:
Not each API call is implemented for each product. The directory src/test/resources contains a txt file
for each product with the API functions that are not implemented
Cafe: src/test/resources/notImplementedForCafe.txt
Opensplice SP: src/test/resources/notImplementedForOpensplice_sp.txt
Opensplice SHM: src/test/resources/notImplementedForOpensplice_shm.txt

XFail tests:
For Opensplice there is a file called src/test/resources/expectedFailForOpensplice.txt in this file all testcases which are known to fail
can be noted with their name and a ticket reference to it. This file is then checked against the testresult output and the test is set to XFAIL.


Integration:
OpenSplice	: put code as submodule inside osplo/testsuite/dbtp/api/dcps/java5 - opensplice.xml
Cafe:		: put code as submodule inside testsuite/unittest/java5 - pom.xml


System properties with their values used by the java 5 psm test framework

JAVA5PSM_SERVICE_ENV
OpenSplice	: org.opensplice.dds.core.OsplServiceEnvironment
Cafe		: com.prismtech.cafe.core.ServiceEnvironmentImpl

JAVA5PSM_LOCATION:
OpenSplice	: path to dcpssaj5.jar
Cafe		: not needed

JAVA5PSM_MODE:
OpenSplice	: opensplice_sp for SP opensplice_shm for SHM
Cafe		: cafe

OSPL_URI:
OpenSplice	: set to java5 xml in a writeable location as this gets generated by the framework based on a template.xml
Cafe		: for cafe this does not need to be set

JAVA5PSM_IDLPP_EXEC
OpenSplice	: idlpp
Cafe		: not needed

JAVA5PSM_IDLPP_ARGS
OpenSplice	: -l java test.idl
Cafe		: not needed
