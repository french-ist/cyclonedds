cd src/main/c 
mkdir -p linux-x86-64
rm -f linux-x86-64/*
gcc -fPIC -c *.c 
gcc -fPIC -shared  *.o -o linux-x86-64/libsample.so
cd -
mvn clean install