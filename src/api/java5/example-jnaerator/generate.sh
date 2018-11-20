#!/bin/bash
cd src/main/resources/
rm -r hello/
java -jar ~/IST/JNAerator/jnaerator/target/jnaerator-0.13-SNAPSHOT-shaded.jar -library hello hello.h -o . -noJar -noComp -beautifyNames -beanStructs
cd -

