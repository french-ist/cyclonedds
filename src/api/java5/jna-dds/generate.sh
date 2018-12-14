# set JNAERATOR_JAR to your 
export JNAERATOR_JAR=./jnaerator-0.13-SNAPSHOT-shaded.jar
echo "=== Backup pom.xml"
mv pom.xml pom.xml.bk
rm -rf _* src/main/java/org/eclipse/cyclone/ddsc *.h
./typedef.sh
echo "=== Generate jna interface for core/ddsc/include/ddsc/dds.h"
cp ../../../../src/os/include/os/os_public.h .
cp ../../../core/ddsc/include/ddsc/* .
cp ../../../../build/core/include/ddsc/* .
cp ../../../../build/core/*.h .
cp ../../../core/ddsc/src/*.h .
echo "=== Generate jna interface for all headers"
for each in `cat headers.txt`
do  
    if [[ $each = *.h ]] 
    then 
        echo "   $each"
        ./gen.sh $each
        SIMPLE_NAME=`basename "${each%.*}"`
        mkdir -p src/main/java/org/eclipse/cyclonedds/ddsc/$SIMPLE_NAME
        if [ "$(ls -A src/main/java/org/eclipse/cyclonedds/ddsc)" ] 
        # if directory not empty
        then 
            mv src/main/java/org/eclipse/cyclonedds/ddsc/*.java src/main/java/org/eclipse/cyclonedds/ddsc/$SIMPLE_NAME
            for src in `ls src/main/java/org/eclipse/cyclonedds/ddsc/$SIMPLE_NAME/*`
            do
                
                sed -i "s/org.eclipse.cyclonedds.ddsc/org.eclipse.cyclonedds.ddsc.$SIMPLE_NAME/g" $src
                # Add import in order to compile
                sed -i "/package org.eclipse.cyclonedds.ddsc.$SIMPLE_NAME/a import org.eclipse.cyclonedds.helper.*;" $src             # Remove comments 
                for cmt in jnaerator nativelibs4java
                do
                sed -i /$cmt/d $src
                done 
                # replace protected List<? > getFieldOrder()
                sed -i 's/protected List<? > getFieldOrder()/protected List<String> getFieldOrder()/g' $src
            done
        fi
    fi
done

./gen_dds.sh

echo "=== Restore pom.xml"
mv pom.xml.bk pom.xml

rm -f 

mvn clean install
