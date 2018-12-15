cd src/main/java/org/eclipse/cyclonedds/ddsc
rm where.txt;for i in `find . -name *.java | grep -v DdscLibrary.java`;do echo -n "$i " >> where.txt;grep "native decl" $i>>where.txt;done
cat where.txt | grep -v "\/\*\*" | awk -F"/" '{print $3}' | awk -F" " '{print $1,$NF}'| sed 's/<//g' > where_clean.txt
sort where_clean.txt > t; mv t where_clean.txt 
cat where_clean.txt | awk -F".h:" '{print $1}' > t; mv t where_clean.txt
sed -i 's/ _dds/ dds/g' where_clean.txt
sort -u where_clean.txt > t; mv t where_clean.txt 
grep "dds$\|dds_public_impl" where_clean.txt
echo "=== remove duplicates manually in dds and dds_public_impl"
rm dds/dds_key_descriptor.java
rm dds/dds_topic_descriptor.java 
rm dds/dds_sequence.java 

echo "=== remove duplicates"

IFS=$'\n';for i in `cat where_clean.txt`;do echo $i;j=`echo $i|cut -d' ' -f1`;echo $j;k=`echo $i|cut -d' ' -f2`;echo $k;for l in `find . -name $j`;do echo $l;[[ $l != *$k* ]] && echo "rm $l" && rm $l;done;done

echo "=== add imports"
cd -
./add_imports.sh
