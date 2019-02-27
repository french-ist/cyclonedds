// Example #22: Call a Java callback method from C
package org.eclipse.cyclonedds.sample.example22;

import com.sun.jna.Native;

public class Launcher {
	public static void main(String[] args) {

		final CLibrary clib = (CLibrary) Native.load("sample", CLibrary.class);

		// instantiate a callback wrapper instance
		final CLibrary.Example22CallbackImplementation callbackImpl = new CLibrary.Example22CallbackImplementation();

		// pass the callback wrapper to the C library
		clib.example22_triggerCallback(callbackImpl);
	}
}