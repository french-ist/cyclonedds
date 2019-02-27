[ $# != 1 ] && echo  "*** usage : $0 <example>" && echo "    ex : $0 helloworld_direct_mapping " && exit
cd src/main/c 
mkdir -p linux-x86-64
rm -f linux-x86-64/*
gcc -fPIC -c *.c 
gcc -fPIC -shared  *.o -o linux-x86-64/libsample.so
cd -
mvn clean install "-Dpackage.name=$1"