
echo ""
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "!!! NB : jnaerator must be used with full comments  !!!"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo ""
echo "=== remove duplicates"

# Remove duplicated classes
cd src/main/java/org/eclipse/cyclonedds/ddsc

rm where.txt
for i in `find . -name *.java | grep -v DdscLibrary.java`
do 
    # write in which header .h is the native declaration of the generated java class
    echo -n "$i " >> where.txt
    grep "native declaration" $i>>where.txt
done
# clean : keep only the java class and the header containing the native declaration
cat where.txt | grep -v "\/\*\*" | awk -F"/" '{print $3}' | awk -F" " '{print $1,$NF}'| sed 's/<//g' > where_clean.txt
sort where_clean.txt > t; mv t where_clean.txt 
cat where_clean.txt | awk -F".h:" '{print $1}' > t; mv t where_clean.txt
sed -i 's/ _dds/ dds/g' where_clean.txt
sort -u where_clean.txt > t; mv t where_clean.txt 

IFS=$'\n'
for i in `cat where_clean.txt`
do 
    echo $i
    # class
    JAVA_CLASS=`echo $i|cut -d' ' -f1`;echo $JAVA_CLASS
    C_HEADER=`echo $i|cut -d' ' -f2`;echo $C_HEADER
    for l in `find . -name $JAVA_CLASS`
    do 
        echo $l
        # if the name of the .java file found does not contain the C header name, remove it
        [[ $l != *$C_HEADER* ]] && echo "rm $l" && rm $l
    done
done
echo "=== add missing imports following duplicates removal, to make code compile"
cd -
./add_imports.sh
