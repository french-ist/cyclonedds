JAR_LOCATION_MVN = target
JAR_MODULE_MVN = java5psm-test-1.0.jar
JAR_FILE_MVN = $(JAR_LOCATION_MVN)/$(JAR_MODULE_MVN)
include $(OSPL_HOME)/setup/makefiles/rules.mak

$(JAR_FILE_MVN): 
	mvn $(OSPL_REPOSITORY) -f opensplice.xml compile test-compile

include $(OSPL_HOME)/setup/makefiles/target.mak

clean:
	mvn $(OSPL_REPOSITORY) -f opensplice.xml clean;
