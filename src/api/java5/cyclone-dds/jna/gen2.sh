sed /_Pre_satisfies_/d $1.h | sed '/  ((/d' \
| sed 's/DDS_EXPORT//g' \
| sed 's/_Must_inspect_result_//g' \
| sed 's/_Check_return_//g' \
| sed 's/_Must_inspect_result_//g' \
| sed 's/_In_opt_//g' \
| sed 's/_In_reads_bytes_(size)//g' \
| sed 's/_In_z_//g' \
| sed 's/_In_//g' \
| sed 's/_Out_opt_//g' \
| sed 's/_Out_writes_z_(size)//g' \
| sed 's/_Out_writes_to_(size, return < 0 ? 0 : return)//g' \
| sed 's/_Out_writes_to_(nxs, return < 0 ? 0 : return)//g' \
| sed 's/_Out_//g' \
> ${1}tmp.h
java -jar $JNAERATOR_JAR -f -v \
-I .:../../../../core/ddsc/include/ddsc:../../../../core/ddsc/src \
-noPrimitiveArrays \
-nocpp \
-limitComments \
-parseInOnePiece \
-library dds2 ${1}tmp.h \
-runtime JNA -o . -arch linux_x64 \
-beautifyNames -callbacksInvokeMethodName apply \
-direct -forceStringSignatures  -beanStructs \
-skipDeprecated \
-mode Maven -mavenArtifactId ddsjna -mavenGroupId  org.eclipse.cyclonedds -mavenVersion 1.0
