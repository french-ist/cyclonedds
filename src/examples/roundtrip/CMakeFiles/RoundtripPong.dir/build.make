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
include examples/roundtrip/CMakeFiles/RoundtripPong.dir/depend.make

# Include the progress variables for this target.
include examples/roundtrip/CMakeFiles/RoundtripPong.dir/progress.make

# Include the compile flags for this target's objects.
include examples/roundtrip/CMakeFiles/RoundtripPong.dir/flags.make

examples/roundtrip/RoundTrip.c: examples/roundtrip/RoundTrip.idl
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Running idlc on RoundTrip.idl"
	cd /home/ndrianja/IST/cyclonedds/src/examples/roundtrip && ../../idlc/dds_idlc /home/ndrianja/IST/cyclonedds/src/examples/roundtrip/RoundTrip.idl

examples/roundtrip/RoundTrip.h: examples/roundtrip/RoundTrip.c
	@$(CMAKE_COMMAND) -E touch_nocreate examples/roundtrip/RoundTrip.h

examples/roundtrip/CMakeFiles/RoundtripPong.dir/pong.c.o: examples/roundtrip/CMakeFiles/RoundtripPong.dir/flags.make
examples/roundtrip/CMakeFiles/RoundtripPong.dir/pong.c.o: examples/roundtrip/pong.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building C object examples/roundtrip/CMakeFiles/RoundtripPong.dir/pong.c.o"
	cd /home/ndrianja/IST/cyclonedds/src/examples/roundtrip && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/RoundtripPong.dir/pong.c.o   -c /home/ndrianja/IST/cyclonedds/src/examples/roundtrip/pong.c

examples/roundtrip/CMakeFiles/RoundtripPong.dir/pong.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/RoundtripPong.dir/pong.c.i"
	cd /home/ndrianja/IST/cyclonedds/src/examples/roundtrip && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/ndrianja/IST/cyclonedds/src/examples/roundtrip/pong.c > CMakeFiles/RoundtripPong.dir/pong.c.i

examples/roundtrip/CMakeFiles/RoundtripPong.dir/pong.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/RoundtripPong.dir/pong.c.s"
	cd /home/ndrianja/IST/cyclonedds/src/examples/roundtrip && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/ndrianja/IST/cyclonedds/src/examples/roundtrip/pong.c -o CMakeFiles/RoundtripPong.dir/pong.c.s

examples/roundtrip/CMakeFiles/RoundtripPong.dir/RoundTrip.c.o: examples/roundtrip/CMakeFiles/RoundtripPong.dir/flags.make
examples/roundtrip/CMakeFiles/RoundtripPong.dir/RoundTrip.c.o: examples/roundtrip/RoundTrip.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building C object examples/roundtrip/CMakeFiles/RoundtripPong.dir/RoundTrip.c.o"
	cd /home/ndrianja/IST/cyclonedds/src/examples/roundtrip && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/RoundtripPong.dir/RoundTrip.c.o   -c /home/ndrianja/IST/cyclonedds/src/examples/roundtrip/RoundTrip.c

examples/roundtrip/CMakeFiles/RoundtripPong.dir/RoundTrip.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/RoundtripPong.dir/RoundTrip.c.i"
	cd /home/ndrianja/IST/cyclonedds/src/examples/roundtrip && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/ndrianja/IST/cyclonedds/src/examples/roundtrip/RoundTrip.c > CMakeFiles/RoundtripPong.dir/RoundTrip.c.i

examples/roundtrip/CMakeFiles/RoundtripPong.dir/RoundTrip.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/RoundtripPong.dir/RoundTrip.c.s"
	cd /home/ndrianja/IST/cyclonedds/src/examples/roundtrip && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/ndrianja/IST/cyclonedds/src/examples/roundtrip/RoundTrip.c -o CMakeFiles/RoundtripPong.dir/RoundTrip.c.s

# Object files for target RoundtripPong
RoundtripPong_OBJECTS = \
"CMakeFiles/RoundtripPong.dir/pong.c.o" \
"CMakeFiles/RoundtripPong.dir/RoundTrip.c.o"

# External object files for target RoundtripPong
RoundtripPong_EXTERNAL_OBJECTS =

bin/RoundtripPong: examples/roundtrip/CMakeFiles/RoundtripPong.dir/pong.c.o
bin/RoundtripPong: examples/roundtrip/CMakeFiles/RoundtripPong.dir/RoundTrip.c.o
bin/RoundtripPong: examples/roundtrip/CMakeFiles/RoundtripPong.dir/build.make
bin/RoundtripPong: lib/libddsc.so.0.1.0
bin/RoundtripPong: examples/roundtrip/CMakeFiles/RoundtripPong.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Linking C executable ../../bin/RoundtripPong"
	cd /home/ndrianja/IST/cyclonedds/src/examples/roundtrip && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/RoundtripPong.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
examples/roundtrip/CMakeFiles/RoundtripPong.dir/build: bin/RoundtripPong

.PHONY : examples/roundtrip/CMakeFiles/RoundtripPong.dir/build

examples/roundtrip/CMakeFiles/RoundtripPong.dir/clean:
	cd /home/ndrianja/IST/cyclonedds/src/examples/roundtrip && $(CMAKE_COMMAND) -P CMakeFiles/RoundtripPong.dir/cmake_clean.cmake
.PHONY : examples/roundtrip/CMakeFiles/RoundtripPong.dir/clean

examples/roundtrip/CMakeFiles/RoundtripPong.dir/depend: examples/roundtrip/RoundTrip.c
examples/roundtrip/CMakeFiles/RoundtripPong.dir/depend: examples/roundtrip/RoundTrip.h
	cd /home/ndrianja/IST/cyclonedds/src && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/ndrianja/IST/cyclonedds/src /home/ndrianja/IST/cyclonedds/src/examples/roundtrip /home/ndrianja/IST/cyclonedds/src /home/ndrianja/IST/cyclonedds/src/examples/roundtrip /home/ndrianja/IST/cyclonedds/src/examples/roundtrip/CMakeFiles/RoundtripPong.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : examples/roundtrip/CMakeFiles/RoundtripPong.dir/depend

