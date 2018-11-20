#!/bin/bash
cd src/main/resources/
java -jar ~/IST/JNAerator/jnaerator/target/jnaerator-0.13-SNAPSHOT-shaded.jar -library hello hello.h -o . -v -noJar -noComp
cd -

