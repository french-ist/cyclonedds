# Remove duplicated declaration of struct xxx_t
# find where a struct xxx_t is defined
# a typedefed structs will lead jnaerator to generate a class extending PointerType
grep "public static class dds.* extends PointerType" * -R  \
| awk -F"/ddsc/" '{print $2}' \
| sed 's/public static class //g' \
| sed 's/://g' \
| sed 's/ *{//g' \
| awk -F" " '{print $2 " " $1}' \
| sort > _POINTER_TYPE.txt

# Search C Headers containing Struct declaration
echo ""
echo "=== List of C Headers containing typedefed Struct declaration"
IFS=$'\n'
rm -f _TYPEDEFED_STRUCT.txt
for i in `cat _POINTER_TYPE.txt`
do 
    #echo "=== $i"
    j=`echo "$i"|cut -d' ' -f1`
    echo $j >> _TYPEDEFED_STRUCT.txt
done
rm -f _C_TYPEDEFED_STRUCT_LOCATION.txt
IFS=$'\n'
find ~/00-Devel/cyclonedds -type f -name "*.h" | grep -v "ddsi\|saj" > _C_HEADERS.txt
for i in `cat _TYPEDEFED_STRUCT.txt`
do 
    find ~/00-Devel/cyclonedds -type f -name "*.h" | grep -v "ddsi\|saj" | xargs grep "$i;" >> _C_TYPEDEFED_STRUCT_LOCATION.txt
done
sort -u  _C_TYPEDEFED_STRUCT_LOCATION.txt > t; mv t _C_TYPEDEFED_STRUCT_LOCATION.txt
rm -f __C_TYPEDEFED_STRUCT_LOCATION.txt
echo ""
echo "STRUCT        HEADER FILE"
for each in `cat _C_TYPEDEFED_STRUCT_LOCATION.txt`
do 
    STRUCT=`echo $each | cut -d':' -f2 | sed 's/;//'`
    FILE=`echo $each | cut -d':' -f1 | xargs basename | sed 's/\..*//' | sed 's/\ //'`
    echo "===$STRUCT $FILE"
    RES=`grep "$STRUCT $FILE" _POINTER_TYPE.txt`
    [ ! -z $RES ] && echo "$each $RES"  >> __C_TYPEDEFED_STRUCT_LOCATION.txt
done
cat __C_TYPEDEFED_STRUCT_LOCATION.txt | awk -F" " '{print $1 " " $NF " " $2 " " $3}' > C_TYPEDEFED_STRUCT_LOCATION.txt

# html output
rm -f C_TYPEDEFED_STRUCT_LOCATION.html
echo '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">' > C_TYPEDEFED_STRUCT_LOCATION.html
echo '<html><head></title>' >> C_TYPEDEFED_STRUCT_LOCATION.html
echo '<meta http-equiv="Content-Type" content="text/xhtml;charset=UTF-8"/>' >> C_TYPEDEFED_STRUCT_LOCATION.html
echo '<link rel="stylesheet" type="text/css" href="search.css"/>' >> C_TYPEDEFED_STRUCT_LOCATION.html
echo '</head>' >> C_TYPEDEFED_STRUCT_LOCATION.html
echo '<body>' >> C_TYPEDEFED_STRUCT_LOCATION.html
echo "<table>" >> C_TYPEDEFED_STRUCT_LOCATION.html
echo "<tr><th>Java Class extending PointerType</th><th>jna Java Class</th><th>C Struct</th><th>C Header Location</th></tr>" >> C_TYPEDEFED_STRUCT_LOCATION.html
cat C_TYPEDEFED_STRUCT_LOCATION.txt \
| awk -F" " '{print "<tr><td>"$1"</td><td>"$2"</td><td>"$3"</td><td>"$NF"</td></tr>"}' \
| sed 's/^[ ]*//' \
>> C_TYPEDEFED_STRUCT_LOCATION.html
echo "</table>" >> C_TYPEDEFED_STRUCT_LOCATION.html
echo '</body>' >> C_TYPEDEFED_STRUCT_LOCATION.html
echo '</html>' >> C_TYPEDEFED_STRUCT_LOCATION.html
google-chrome C_TYPEDEFED_STRUCT_LOCATION.html

exit

# Find all generated Java classes extending jna.Structure
# i.e. related to "actual" C Struct and not typedefed one 

grep "public class .* extends Structure" * -R  \
| awk -F"/ddsc/" '{print $2}' \
| sed 's/public class //g' \
| sed 's/:/ /g' \
| sed 's/ *{//g' \
| awk -F" " '{print $2 " " $1}' \
| sort > _STRUCTURE_WITH_CONTAINING_FILE.txt

echo ""
echo "=== List of generated Java classes corresponding to C structures"
# list of classes corresponding to C structures
rm -f  _JAVA_CLASS_FOR_C_STRUCTURE.txt
IFS=$'\n'
for each in `cat _STRUCTURE_WITH_CONTAINING_FILE.txt`
do 
    j=`echo "$each"|cut -d' ' -f1`
    echo $j>>  _JAVA_CLASS_FOR_C_STRUCTURE.txt
done
cat   _JAVA_CLASS_FOR_C_STRUCTURE.txt
# Search C Headers containing Struct declaration
echo ""
echo "=== List of C Headers containing Struct declaration"
echo "    The name of the header is the name the folder containing the Java Class extending jna.Structure "
IFS=$'\n'
rm -f _C_STRUCT_LOCATION.txt
for i in `cat  _JAVA_CLASS_FOR_C_STRUCTURE.txt`
do 
    echo -n "$i " >> _C_STRUCT_LOCATION.txt
    find ~/00-Devel/cyclonedds -type f -name "*.h" \
    | xargs grep "${i}[_t]*;" \
    | grep -v "struct ${i}[_t]*;" \
    | grep -v "java" | grep -v "html" | grep -v "xml" | grep -v ".class" | grep -v "typedef\|struct" \
    | sed 's/:*}/ /g' | sed 's/:/ /g'| sed 's/;//g' \
    >> _C_STRUCT_LOCATION.txt
done
rm -f __C_STRUCT_LOCATION.txt
for each in `cat _C_STRUCT_LOCATION.txt`
do 
    echo -n "$each " >> __C_STRUCT_LOCATION.txt
    j=`echo $each | cut -d' ' -f1`
    sed -n "/$j/p" _STRUCTURE_WITH_CONTAINING_FILE.txt >> __C_STRUCT_LOCATION.txt
done
#echo "Java Class extending jna.Structure   	Java Folder 	C Struct	    C Header Location"
cat __C_STRUCT_LOCATION.txt | awk -F" " '{print $1 " " $NF " " $3 " " $2}' > C_STRUCT_LOCATION.txt
cat C_STRUCT_LOCATION.txt

# html output
rm -f C_STRUCT_LOCATION.html
echo '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">' > C_STRUCT_LOCATION.html
echo '<html><head></title>' >> C_STRUCT_LOCATION.html
echo '<meta http-equiv="Content-Type" content="text/xhtml;charset=UTF-8"/>' >> C_STRUCT_LOCATION.html
echo '<link rel="stylesheet" type="text/css" href="search.css"/>' >> C_STRUCT_LOCATION.html
echo '</head>' >> C_STRUCT_LOCATION.html
echo '<body>' >> C_STRUCT_LOCATION.html
echo "<table>" >> C_STRUCT_LOCATION.html
echo "<tr><th>Java Class extending jna.Structure</th><th>jna Java File</th><th>C Struct</th><th>C Header file</th></tr>" >> C_STRUCT_LOCATION.html
cat C_STRUCT_LOCATION.txt \
| awk -F" " '{print "<tr><td>"$1"</td><td>"$2"</td><td>"$3"</td><td>"$NF"</td></tr>"}' \
| sed 's/^[ ]*//' \
>> C_STRUCT_LOCATION.html
echo "</table>" >> C_STRUCT_LOCATION.html
echo '</body>' >> C_STRUCT_LOCATION.html
echo '</html>' >> C_STRUCT_LOCATION.html
google-chrome C_STRUCT_LOCATION.html