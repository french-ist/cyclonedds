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
include tools/pubsub/CMakeFiles/pubsub.dir/depend.make

# Include the progress variables for this target.
include tools/pubsub/CMakeFiles/pubsub.dir/progress.make

# Include the compile flags for this target's objects.
include tools/pubsub/CMakeFiles/pubsub.dir/flags.make

tools/pubsub/CMakeFiles/pubsub.dir/pubsub.c.o: tools/pubsub/CMakeFiles/pubsub.dir/flags.make
tools/pubsub/CMakeFiles/pubsub.dir/pubsub.c.o: tools/pubsub/pubsub.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object tools/pubsub/CMakeFiles/pubsub.dir/pubsub.c.o"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/pubsub.dir/pubsub.c.o   -c /home/ndrianja/IST/cyclonedds/src/tools/pubsub/pubsub.c

tools/pubsub/CMakeFiles/pubsub.dir/pubsub.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/pubsub.dir/pubsub.c.i"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/ndrianja/IST/cyclonedds/src/tools/pubsub/pubsub.c > CMakeFiles/pubsub.dir/pubsub.c.i

tools/pubsub/CMakeFiles/pubsub.dir/pubsub.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/pubsub.dir/pubsub.c.s"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/ndrianja/IST/cyclonedds/src/tools/pubsub/pubsub.c -o CMakeFiles/pubsub.dir/pubsub.c.s

tools/pubsub/CMakeFiles/pubsub.dir/common.c.o: tools/pubsub/CMakeFiles/pubsub.dir/flags.make
tools/pubsub/CMakeFiles/pubsub.dir/common.c.o: tools/pubsub/common.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building C object tools/pubsub/CMakeFiles/pubsub.dir/common.c.o"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/pubsub.dir/common.c.o   -c /home/ndrianja/IST/cyclonedds/src/tools/pubsub/common.c

tools/pubsub/CMakeFiles/pubsub.dir/common.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/pubsub.dir/common.c.i"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/ndrianja/IST/cyclonedds/src/tools/pubsub/common.c > CMakeFiles/pubsub.dir/common.c.i

tools/pubsub/CMakeFiles/pubsub.dir/common.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/pubsub.dir/common.c.s"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/ndrianja/IST/cyclonedds/src/tools/pubsub/common.c -o CMakeFiles/pubsub.dir/common.c.s

tools/pubsub/CMakeFiles/pubsub.dir/testtype.c.o: tools/pubsub/CMakeFiles/pubsub.dir/flags.make
tools/pubsub/CMakeFiles/pubsub.dir/testtype.c.o: tools/pubsub/testtype.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building C object tools/pubsub/CMakeFiles/pubsub.dir/testtype.c.o"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/pubsub.dir/testtype.c.o   -c /home/ndrianja/IST/cyclonedds/src/tools/pubsub/testtype.c

tools/pubsub/CMakeFiles/pubsub.dir/testtype.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/pubsub.dir/testtype.c.i"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/ndrianja/IST/cyclonedds/src/tools/pubsub/testtype.c > CMakeFiles/pubsub.dir/testtype.c.i

tools/pubsub/CMakeFiles/pubsub.dir/testtype.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/pubsub.dir/testtype.c.s"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/ndrianja/IST/cyclonedds/src/tools/pubsub/testtype.c -o CMakeFiles/pubsub.dir/testtype.c.s

tools/pubsub/CMakeFiles/pubsub.dir/porting.c.o: tools/pubsub/CMakeFiles/pubsub.dir/flags.make
tools/pubsub/CMakeFiles/pubsub.dir/porting.c.o: tools/pubsub/porting.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building C object tools/pubsub/CMakeFiles/pubsub.dir/porting.c.o"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/pubsub.dir/porting.c.o   -c /home/ndrianja/IST/cyclonedds/src/tools/pubsub/porting.c

tools/pubsub/CMakeFiles/pubsub.dir/porting.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/pubsub.dir/porting.c.i"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/ndrianja/IST/cyclonedds/src/tools/pubsub/porting.c > CMakeFiles/pubsub.dir/porting.c.i

tools/pubsub/CMakeFiles/pubsub.dir/porting.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/pubsub.dir/porting.c.s"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && /usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/ndrianja/IST/cyclonedds/src/tools/pubsub/porting.c -o CMakeFiles/pubsub.dir/porting.c.s

# Object files for target pubsub
pubsub_OBJECTS = \
"CMakeFiles/pubsub.dir/pubsub.c.o" \
"CMakeFiles/pubsub.dir/common.c.o" \
"CMakeFiles/pubsub.dir/testtype.c.o" \
"CMakeFiles/pubsub.dir/porting.c.o"

# External object files for target pubsub
pubsub_EXTERNAL_OBJECTS =

bin/pubsub: tools/pubsub/CMakeFiles/pubsub.dir/pubsub.c.o
bin/pubsub: tools/pubsub/CMakeFiles/pubsub.dir/common.c.o
bin/pubsub: tools/pubsub/CMakeFiles/pubsub.dir/testtype.c.o
bin/pubsub: tools/pubsub/CMakeFiles/pubsub.dir/porting.c.o
bin/pubsub: tools/pubsub/CMakeFiles/pubsub.dir/build.make
bin/pubsub: lib/libddsc.so.0.1.0
bin/pubsub: lib/libOSAPI.a
bin/pubsub: tools/pubsub/CMakeFiles/pubsub.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/ndrianja/IST/cyclonedds/src/CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Linking C executable ../../bin/pubsub"
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/pubsub.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
tools/pubsub/CMakeFiles/pubsub.dir/build: bin/pubsub

.PHONY : tools/pubsub/CMakeFiles/pubsub.dir/build

tools/pubsub/CMakeFiles/pubsub.dir/clean:
	cd /home/ndrianja/IST/cyclonedds/src/tools/pubsub && $(CMAKE_COMMAND) -P CMakeFiles/pubsub.dir/cmake_clean.cmake
.PHONY : tools/pubsub/CMakeFiles/pubsub.dir/clean

tools/pubsub/CMakeFiles/pubsub.dir/depend:
	cd /home/ndrianja/IST/cyclonedds/src && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/ndrianja/IST/cyclonedds/src /home/ndrianja/IST/cyclonedds/src/tools/pubsub /home/ndrianja/IST/cyclonedds/src /home/ndrianja/IST/cyclonedds/src/tools/pubsub /home/ndrianja/IST/cyclonedds/src/tools/pubsub/CMakeFiles/pubsub.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : tools/pubsub/CMakeFiles/pubsub.dir/depend

