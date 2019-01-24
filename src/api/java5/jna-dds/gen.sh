export JNAERATOR_JAR=./jnaerator-0.13-SNAPSHOT-shaded.jar
sed /_Pre_satisfies_/d $1 | sed '/  ((/d' > _${1}
#include "#include typedef.h"
sed -i '1i #include "typedef.h"' _${1}
# sed -i "1 r typedef.h"  _${1}
# Set The Internal Field Separator (IFS)
IFS=$'\n'
for each in `cat ignore.txt`
do  
    echo $each
    sed -i "s/$each//g" _${1}
done
export JNAERATOR_INCLUDE_PATH=/usr/include/x86_64-linux-gnu:/usr/lib/gcc/x86_64-linux-gnu/7/include:$JNAERATOR_INCLUDE_PATH
# skip functions which are in headers and not in library .so
FUNCTIONS_TO_SKIP=`cat FUNCTIONS_TO_SKIP.txt | tr '\n' '|' | sed "s/|$//g"`
echo $FUNCTIONS_TO_SKIP
IFS=$'\ '
java -Xmx2g -jar $JNAERATOR_JAR -f -v \
-I .:../../../../build/core/include/ddsc:../../../core/ddsc/include/ddsc:../../../core/ddsc/src:../../../../build/core:../../../../src/os/include/os \
-skipFunctions "$FUNCTIONS_TO_SKIP" \
-noRawBindings \
-forceStringSignatures \
-rootPackage org.eclipse.cyclonedds \
-noPrimitiveArrays \
-nocpp \
-gccLong \
-parseInOnePiece \
-library ddsc \
-runtime JNA -o . -arch linux_x64 \
-callbacksInvokeMethodName apply \
-direct -beanStructs \
-skipDeprecated \
-mode Maven -mavenArtifactId ddsjna -mavenGroupId  org.eclipse.cyclonedds -mavenVersion 1.0 \
_${1}