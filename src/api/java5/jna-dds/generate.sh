# set JNAERATOR_JAR to your 
export JNAERATOR_JAR=./jnaerator-0.13-SNAPSHOT-shaded.jar
echo "=== Backup pom.xml"
mv pom.xml pom.xml.bk
rm -rf *tmp.h _jnaerator.choices  _jnaerator.macros.cpp  _jnaerator.normalizedParsed.cpp \
 _jnaerator.preprocessed.c  _jnaerator.rawParsed.cpp_jnaerator.rawParsed.cpp \
src pom.xml _jnaerator.rawParsed.cpp
cat ../../../core/ddsc/include/ddsc/ddsv2.h | sed '/#include "ddsc\/dds_public_impl.h"/ r ../../../core/ddsc/include/ddsc/dds_public_impl.h' | sed 's/#include "ddsc\/dds_public_impl.h"/\/\/#include "ddsc\/dds_public_impl.h"/g' > dds2tmp.h
./gen.sh dds2tmp
echo "=== Patch src/main/java/org/eclipse/cyclonedds/ddsc/DdscLibrary.java"
# comment line leading to "Caused by: java.lang.IllegalArgumentException: class [Lorg.eclipse.cyclonedds.ddsc.DdscLibrary$serdata; is not a supported argument type (in method dds_takecdr in class org.eclipse.cyclonedds.ddsc.DdscLibrary)"
sed -i 's/public static native int dds_takecdr(DdscLibrary.dds_entity_t reader_or_condition, DdscLibrary.serdata buf/\/\/public static native int dds_takecdr(DdscLibrary.dds_entity_t reader_or_condition, DdscLibrary.serdata buf/g' src/main/java/org/eclipse/cyclonedds/ddsc/DdscLibrary.java
# add dependency to jnaerator-runtime
#sed -i '/<dependencies>/a <dependency><groupId>com.nativelibs4java</groupId><artifactId>jnaerator-runtime</artifactId><version>0.13-SNAPSHOT</version></dependency>' pom.xml
echo "=== Copy NativeSize.java"
cp ~/00-Devel/JNAerator/jnaerator-runtime/src/main/java/com/ochafik/lang/jnaerator/runtime/NativeSize.java src/main/java/org/eclipse/cyclonedds/ddsc/
echo "=== Delete import of package com.ochafik.lang.jnaerator.runtime"
cd src/main/java/org/eclipse/cyclonedds/ddsc
for i in `ls`; do sed -i /ochafik/d $i;done
echo "=== Change package of NativeSize.java"
sed -i '1 i package org.eclipse.cyclonedds.ddsc;' NativeSize.java
cd -
echo "=== Restore pom.xml"
mv pom.xml.bk pom.xml
#echo "=== clean pom.xml"
#cat pom.xml | sed 106,112d |sed 43,70d | sed 23,31d > pom_tmp
#mv pom_tmp pom.xml
rm _jnaerator* *tmp*
mvn clean install
