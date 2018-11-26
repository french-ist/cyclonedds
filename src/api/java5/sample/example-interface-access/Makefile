App.jar: libhello.so
	mvn clean install

libhello.so: src/main/resources/hello.o 	
	cd src/main/resources && cmake . && make && cd -

clean: 	
	cd src/main/resources/ && rm -r Makefile CMakeCache.txt CMakeFiles/ cmake_install.cmake hello.o libhello.so && cd -
