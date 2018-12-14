#!/bin/bash
cd generated-c
java -cp ../../../../../../../../build/idlc/target/idlc-jar-with-dependencies.jar \
org.eclipse.cyclonedds.compilers.Idlc ../HelloWorldData.idl
gcc -c HelloWorldData.c -o helloworld.o
gcc -shared  helloworld.o -o helloworld.so
cd -

#-dontCastConstants \
#-forceStringSignatures \
#-noPrimitiveArrays \

JNAERATOR_JAR=../../../jnaerator-0.13-SNAPSHOT-shaded.jar
java -jar $JNAERATOR_JAR -f -v \
-I generated-c/:/usr/lib/gcc/x86_64-linux-gnu/7/include/:../../../../../../../src/core/ddsc/include/ddsc/:../../../../../../../src/core/ddsc/src/ \
-rootPackage org.eclipse.cyclonedds \
-nocpp \
-parseInOnePiece \
-library helloworld generated-c/HelloWorldData.h \
-runtime JNA -o generated-java -arch linux_x64 \
-callbacksInvokeMethodName apply \
-direct \
-skipDeprecated \
-mode Directory 

