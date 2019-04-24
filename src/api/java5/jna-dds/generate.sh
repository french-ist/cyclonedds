#!/usr/bin/env bash
# Usage: ./generate.sh [file_containing_header_file_name]
# By default generate JNA interface for all headers
# otherwise pass as arg a file containing the headers to generate
[[ $# == 1 ]] && HEADERS=$1 || HEADERS=headers.txt
echo "=== headers file to treat"
cat ${HEADERS}
rm -rf _* dds*.h os*.h  q__osplser.h

echo "=== Backup pom.xml"
cp pom.xml pom.xml.bk

echo "=== backup src/main/java/org/eclipse/cyclonedds"
rm -rf cyclonedds.bk
mv src/main/java/org/eclipse/cyclonedds cyclonedds.bk

# Remove from cyclonedds the headers to regenerate
# so that when we recover cp cyclonedds.bk to cyclonedds
# the newly generated interfaces will not be replaced
# by the backed-up version
for each in `cat ${HEADERS}`
do
    SIMPLE_NAME=`basename "${each%.*}"`
    echo "=== remove cyclonedds.bk/ddsc/${SIMPLE_NAME}"
    rm -rf cyclonedds.bk/ddsc/${SIMPLE_NAME}
    for header in `find ../../../.. -name ${each}`
    do
        cp ${header} .
    done
done

echo "=== Generate jna interface for the following headers"
for each in `cat ${HEADERS}`
do  
    if [[ ${each} = *.h ]]
    then 
        echo "   $each"
        if [[ ${each} == "dds.h" ]]
        then  
            for inc in WORKAROUND_INCLUDE_IN_DDS_HEADER.h
            do
                # include WORKAROUND_INCLUDE_IN_DDS_HEADER.h
                sed -i '/\#include\ \"os\/os_public.h/i \/\/ __HERE__' dds.h
                sed -i "/__HERE__/a \/\/ include $inc" dds.h 
                sed -i "/__HERE__/ r $inc" dds.h
            done
        fi
        ./gen.sh ${each}
    fi
done

echo "=== Restore pom.xml"
mv pom.xml.bk pom.xml
echo "=== Restore src/main/java/org/eclipse/cyclonedds"
cp -r cyclonedds.bk/* src/main/java/org/eclipse/cyclonedds

# remove duplicated generated java class
#./remove_duplicates.sh
# add more operations where needed 
#./add_operations.sh

# remove comments line number in C header
find . -name *.java* | xargs sed -i '/<i>native declaration/d'

mvn clean install

# clean
rm -f _* dds*.h  os*.h q__osplser.h
