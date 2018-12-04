#!/bin/bash
rm -rf src/main/java/org/eclipse/cyclonedds/hello
cd src/main/resources/
rm -rf CMakeFiles/ CMakeCache.txt cmake_install.cmake libhello.so Makefile
cmake . 
make

java -jar ../../../../../jna-dds/jnaerator-0.13-SNAPSHOT-shaded.jar \
    -library hello hello.h \
    -runtime JNA \
    -o ../java/org/eclipse/cyclonedds/ \
    -noJar -noComp -beanStructs -skipDeprecated
# To make compile
sed -i 's/protected List<? > getFieldOrder/protected List<String> getFieldOrder/g' ../java/org/eclipse/cyclonedds/hello/person_struct.java
cd -
mvn clean install