find . -name *.java | xargs grep "public static native" | awk -F"(" '{print $1}' | cut -d' ' -f5 > OPERATIONS.txt
nm --defined-only --extern-only ~/00-Devel/cyclonedds/build/lib/libddsc.so | cut -d' ' -f3 > SYMBOLS_IN_LIBDDSC_SO.txt 
grep -v -f SYMBOLS_IN_LIBDDSC_SO.txt OPERATIONS.txt > FUNCTIONS_TO_SKIP.txt
