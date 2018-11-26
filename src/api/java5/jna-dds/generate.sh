# set JNAERATOR_JAR to your 
export JNAERATOR_JAR=./jnaerator-0.13-SNAPSHOT-shaded.jar
rm -rf *tmp.h _jnaerator.choices  _jnaerator.macros.cpp  _jnaerator.normalizedParsed.cpp \
 _jnaerator.preprocessed.c  _jnaerator.rawParsed.cpp_jnaerator.rawParsed.cpp \
 src pom.xml _jnaerator.rawParsed.cpp

#for i in `ls ../../../../core/ddsc/include/ddsc/ddsv2.h`;do echo `basename $i`;./gen.sh `basename "${i%.*}"`;done
for i in `ls ../../../../core/ddsc/include/ddsc/*.h`;do echo `basename $i`;./gen.sh `basename "${i%.*}"`;done

# add dependency to jnaerator-runtime
sed -i '/<dependencies>/a <dependency><groupId>com.nativelibs4java</groupId><artifactId>jnaerator-runtime</artifactId><version>0.13-SNAPSHOT</version></dependency>' pom.xml
#mvn install
