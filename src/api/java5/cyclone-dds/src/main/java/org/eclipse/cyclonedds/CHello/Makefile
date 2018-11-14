libhello.so: hello.o App.jar
	gcc -shared src/main/resources/hello.o -o src/main/resources/libhello.so 

hello.o: src/main/resources/hello.c
	@echo "USAGE: java -jar target/CHello-1.0-SNAPSHOT.jar\n"
	gcc -c src/main/resources/hello.c -o src/main/resources/hello.o

App.jar: clean
	mvn clean install

clean: 	
	mvn clean
	cd src/main/resources/
	rm -f hello.h libhello.so hello.o
	cd -
