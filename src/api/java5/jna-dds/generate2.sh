# set JNAERATOR_JAR to your 
export JNAERATOR_JAR=./jnaerator-0.13-SNAPSHOT-shaded.jar
rm -rf *tmp.h _jnaerator.choices  _jnaerator.macros.cpp  _jnaerator.normalizedParsed.cpp \
 _jnaerator.preprocessed.c  _jnaerator.rawParsed.cpp_jnaerator.rawParsed.cpp \
src pom.xml _jnaerator.rawParsed.cpp
cat ../../../core/ddsc/include/ddsc/ddsv2.h | sed '/#include "ddsc\/dds_public_impl.h"/ r ../../../core/ddsc/include/ddsc/dds_public_impl.h' | sed 's/#include "ddsc\/dds_public_impl.h"/\/\/#include "ddsc\/dds_public_impl.h"/g' > dds2tmp.h
./gen2.sh dds2tmp
# add dependency to jnaerator-runtime
sed -i '/<dependencies>/a <dependency><groupId>com.nativelibs4java</groupId><artifactId>jnaerator-runtime</artifactId><version>0.13-SNAPSHOT</version></dependency>' pom.xml
mvn install
