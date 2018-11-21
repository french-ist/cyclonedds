#!/bin/bash
rm -r src/main/java/org/eclipse/cyclonedds/hello
cd src/main/resources/
rm -r CMakeFiles/ CMakeCache.txt cmake_install.cmake libhello.so Makefile
cmake . 
make

java -jar ~/IST/JNAerator/jnaerator/target/jnaerator-0.13-SNAPSHOT-shaded.jar \
    -library hello hello.h \
    -runtime JNA \
    -o ../java/org/eclipse/cyclonedds/ \
    -noJar -noComp -beanStructs
cd -
mvn clean install
