IFS=$'\n'
for i in `cat add_operations.in`
do 
    echo $i
    OPERATION=`echo $i|awk -F"->" '{print $1}'`
    echo "=== OPERATION : $OPERATION"
    OUTPUTFILE=`echo $i|awk -F"->" '{print $2}' | sed 's/ //g'`
    echo "=== OUTPUTFILE : $OUTPUTFILE"
    AFTER=`echo $i|awk -F"->" '{print $3}' | sed 's/^[ \t]*//g' `
    echo "=== AFTER : $AFTER"
    cd src/main/java/org/eclipse/cyclonedds/ddsc
    echo "=== entering $PWD"
    echo sed -i "/$AFTER/a $OPERATION" $OUTPUTFILE
    sed -i "/$AFTER/a $OPERATION" $OUTPUTFILE
    cd -
done
