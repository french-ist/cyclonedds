# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.12

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /home/ndrianja/cmake-3.12.4-Linux-x86_64/bin/cmake

# The command to remove a file.
RM = /home/ndrianja/cmake-3.12.4-Linux-x86_64/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/ndrianja/IST/cyclonedds/src

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/ndrianja/IST/cyclonedds/src

# Include any dependencies generated for this target.
include examples/throughput/CMakeFiles/ThroughputSubscriber.dir/depend.make

# Include the progress variables for this target.
include examples/throughput/CMakeFiles/ThroughputSubscriber.dir/progress.make

# Include the compile flags for this target's objects.
include examples/throughput/CMakeFiles/ThroughputSubscriber.dir/flags.make

examples/throughput/Throughput.c: examples/throughput/Throughput.idl
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Running idlc on Throughput.idl"
	cd /home/ndrianja/IST/cyclonedds/src/examples/throughput && ../../idlc/dds_idlc /home/ndrianja/IST/cyclonedds/src/examples/throughput/Throughput.idl

examples/throughput/Throughput.h: examples/throughput/Throughput.c
	@$(CMAKE_COMMAND) -E touch_nocreate examples/throughput/Throughput.h

examples/throughput/CMakeFiles/ThroughputSubscriber.dir/subscriber.c.o: examples/throughput/CMakeFiles/ThroughputSubscriber.dir/flags.make
examples/throughput/CMakeFiles/ThroughputSubscriber.dir/subscriber.c.o: examples/throughput/subscriber.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building C object examples/throughput/CMakeFiles/ThroughputSubscriber.dir/subscriber.c.o"
	cd /home/ndrianja/IST/cyclonedds/src/examples/throughput && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/ThroughputSubscriber.dir/subscriber.c.o   -c /home/ndrianja/IST/cyclonedds/src/examples/throughput/subscriber.c

examples/throughput/CMakeFiles/ThroughputSubscriber.dir/subscriber.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/ThroughputSubscriber.dir/subscriber.c.i"
	cd /home/ndrianja/IST/cyclonedds/src/examples/throughput && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/ndrianja/IST/cyclonedds/src/examples/throughput/subscriber.c > CMakeFiles/ThroughputSubscriber.dir/subscriber.c.i

examples/throughput/CMakeFiles/ThroughputSubscriber.dir/subscriber.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/ThroughputSubscriber.dir/subscriber.c.s"
	cd /home/ndrianja/IST/cyclonedds/src/examples/throughput && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/ndrianja/IST/cyclonedds/src/examples/throughput/subscriber.c -o CMakeFiles/ThroughputSubscriber.dir/subscriber.c.s

examples/throughput/CMakeFiles/ThroughputSubscriber.dir/Throughput.c.o: examples/throughput/CMakeFiles/ThroughputSubscriber.dir/flags.make
examples/throughput/CMakeFiles/ThroughputSubscriber.dir/Throughput.c.o: examples/throughput/Throughput.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building C object examples/throughput/CMakeFiles/ThroughputSubscriber.dir/Throughput.c.o"
	cd /home/ndrianja/IST/cyclonedds/src/examples/throughput && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/ThroughputSubscriber.dir/Throughput.c.o   -c /home/ndrianja/IST/cyclonedds/src/examples/throughput/Throughput.c

examples/throughput/CMakeFiles/ThroughputSubscriber.dir/Throughput.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/ThroughputSubscriber.dir/Throughput.c.i"
	cd /home/ndrianja/IST/cyclonedds/src/examples/throughput && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/ndrianja/IST/cyclonedds/src/examples/throughput/Throughput.c > CMakeFiles/ThroughputSubscriber.dir/Throughput.c.i

examples/throughput/CMakeFiles/ThroughputSubscriber.dir/Throughput.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/ThroughputSubscriber.dir/Throughput.c.s"
	cd /home/ndrianja/IST/cyclonedds/src/examples/throughput && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/ndrianja/IST/cyclonedds/src/examples/throughput/Throughput.c -o CMakeFiles/ThroughputSubscriber.dir/Throughput.c.s

# Object files for target ThroughputSubscriber
ThroughputSubscriber_OBJECTS = \
"CMakeFiles/ThroughputSubscriber.dir/subscriber.c.o" \
"CMakeFiles/ThroughputSubscriber.dir/Throughput.c.o"

# External object files for target ThroughputSubscriber
ThroughputSubscriber_EXTERNAL_OBJECTS =

bin/ThroughputSubscriber: examples/throughput/CMakeFiles/ThroughputSubscriber.dir/subscriber.c.o
bin/ThroughputSubscriber: examples/throughput/CMakeFiles/ThroughputSubscriber.dir/Throughput.c.o
bin/ThroughputSubscriber: examples/throughput/CMakeFiles/ThroughputSubscriber.dir/build.make
bin/ThroughputSubscriber: lib/libddsc.so.0.1.0
bin/ThroughputSubscriber: examples/throughput/CMakeFiles/ThroughputSubscriber.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Linking C executable ../../bin/ThroughputSubscriber"
	cd /home/ndrianja/IST/cyclonedds/src/examples/throughput && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/ThroughputSubscriber.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
examples/throughput/CMakeFiles/ThroughputSubscriber.dir/build: bin/ThroughputSubscriber

.PHONY : examples/throughput/CMakeFiles/ThroughputSubscriber.dir/build

examples/throughput/CMakeFiles/ThroughputSubscriber.dir/clean:
	cd /home/ndrianja/IST/cyclonedds/src/examples/throughput && $(CMAKE_COMMAND) -P CMakeFiles/ThroughputSubscriber.dir/cmake_clean.cmake
.PHONY : examples/throughput/CMakeFiles/ThroughputSubscriber.dir/clean

examples/throughput/CMakeFiles/ThroughputSubscriber.dir/depend: examples/throughput/Throughput.c
examples/throughput/CMakeFiles/ThroughputSubscriber.dir/depend: examples/throughput/Throughput.h
	cd /home/ndrianja/IST/cyclonedds/src && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/ndrianja/IST/cyclonedds/src /home/ndrianja/IST/cyclonedds/src/examples/throughput /home/ndrianja/IST/cyclonedds/src /home/ndrianja/IST/cyclonedds/src/examples/throughput /home/ndrianja/IST/cyclonedds/src/examples/throughput/CMakeFiles/ThroughputSubscriber.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : examples/throughput/CMakeFiles/ThroughputSubscriber.dir/depend

