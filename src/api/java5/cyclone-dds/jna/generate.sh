# set JNAERATOR_JAR to your 
export JNAERATOR_JAR=./jnaerator-0.13-SNAPSHOT-shaded.jar
rm -rf ddsv2tmp.h _jnaerator.choices  _jnaerator.macros.cpp  _jnaerator.normalizedParsed.cpp \
 _jnaerator.preprocessed.c  _jnaerator.rawParsed.cpp_jnaerator.rawParsed.cpp \
 src pom.xml _jnaerator.rawParsed.cpp
sed /_Pre_satisfies_/d ../../../../core/ddsc/include/ddsc/ddsv2.h | sed '/  ((/d' > ddsv2tmp.h
java -jar $JNAERATOR_JAR -f -v -DDDS_EXPORT= \
-D_Must_inspect_result_= \
-D_Check_return_= \
-D_Pre_satisfies_= \
-D_In_= \
-D_In_opt_= \
-D_Out_= \
-f -library dds2 ddsv2tmp.h \
-runtime JNA -o . -arch linux_x64 \
-beautifyNames -callbacksInvokeMethodName apply \
-direct -forceStringSignatures  -beanStructs \
-skipDeprecated \
-mode Maven -mavenArtifactId ddsjna -mavenGroupId  org.eclipse.cyclonedds -mavenVersion 1.0
