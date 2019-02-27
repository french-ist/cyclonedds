/** 
 * Send a Struct Containing an Array of Structs to C
 * Receive a Struct Containing an Array of Structs from C
 * */
package org.eclipse.cyclonedds.sample.example15;

import com.sun.jna.Native;

public class Launcher {
	public static void main(String[] args) {

		final CLibrary clib = (CLibrary) Native.load("sample", CLibrary.class);

		// generate data to send
		final CLibrary.Example15StructB.ByReference ex15ref = new CLibrary.Example15StructB.ByReference();
		ex15ref.numAs = 5;
		ex15ref.as = new CLibrary.Example15StructA.ByReference();
		// toArray generates a block of Example15StructA's that are suitable for passing
		// to C
		final CLibrary.Example15StructA[] ex15as = (CLibrary.Example15StructA[]) ex15ref.as.toArray(ex15ref.numAs);
		// Note: do NOT allocate a new object for each struct in the array -
		// toArray() has already allocated each array item
		for (int ex15loop = 0; ex15loop < ex15ref.numAs; ex15loop++) {
			ex15as[ex15loop].val = ex15loop;
		}
		// call the C function to pass the data to C
		clib.example15_send(ex15ref);

		// Receive
		final CLibrary.Example15StructB.ByValue ex15val = clib.example15_get();
		System.out.println("Example 15: retrieved " + ((CLibrary.Example15StructB) ex15val).numAs + " values:");
		// Structure.toArray copies the data from the C-allocated block of memory
		// into a Java array of Example15Struct objects
		// Note that for large arrays, this can be extremely slow
		final CLibrary.Example15StructA[] ex15as2 = (CLibrary.Example15StructA[]) ((CLibrary.Example15StructB) ex15val).as
				.toArray(((CLibrary.Example15StructB) ex15val).numAs);
		for (int ex15loop = 0; ex15loop < ((CLibrary.Example15StructB) ex15val).numAs; ex15loop++) {
			System.out.println("\t" + ex15as2[ex15loop].val);
		}
		// although we received a copy of the struct (by value), it contains a pointer
		// to a buffer that was not deep-copied and was allocated by C code - that
		// buffer needs to be freed in C code
		System.out.println("(example 15 cleanup)");
		clib.example15_cleanup(ex15val);
	}
}