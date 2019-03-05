rm -rf _* src/main/java/org/eclipse/cyclonedds/ddsc/* dds*.h os*.h  q__osplser.h
# set JNAERATOR_JAR to your 
export JNAERATOR_JAR=./jnaerator-0.13-SNAPSHOT-shaded.jar
echo "=== Backup pom.xml"
mv pom.xml pom.xml.bk
echo "=== Generate jna interface for core/ddsc/include/ddsc/dds.h"
cp ../../../../src/os/include/os/os_public.h .
cp ../../../core/ddsc/include/ddsc/* .
cp ../../../../build/core/include/ddsc/* .
cp ../../../core/ddsc/src/*.h .

echo "=== Generate jna interface for all headers"
for each in `cat headers.txt`
do  
    if [[ $each = *.h ]] 
    then 
        echo "   $each"
        if [ $each == "dds.h" ]
        then  
            for inc in WORKAROUND_INCLUDE_IN_DDS_HEADER.h
            do
                # include WORKAROUND_INCLUDE_IN_DDS_HEADER.h
                sed -i '/\#include\ \"os\/os_public.h/i \/\/ __HERE__' dds.h
                sed -i "/__HERE__/a \/\/ include $inc" dds.h 
                sed -i "/__HERE__/ r $inc" dds.h
            done
        fi
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
                sed -i "/package org.eclipse.cyclonedds.ddsc.$SIMPLE_NAME/a import org.eclipse.cyclonedds.helper.*;" $src
                # Remove comments 
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

echo "=== Restore pom.xml"
mv pom.xml.bk pom.xml

# remove duplicated generated java class
#./remove_duplicates.sh
# add more operations where needed 
#./add_operations.sh
# remove comments line number in C header
find . -name *.java* | xargs sed -i '/<i>native declaration/d'

mvn clean install

# clean
rm -f _* dds*.h  os*.h q__osplser.h