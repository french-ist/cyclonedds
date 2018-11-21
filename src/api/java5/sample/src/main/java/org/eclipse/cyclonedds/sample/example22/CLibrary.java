// Example #22: Call a Java callback method from C
package org.eclipse.cyclonedds.sample.example22;

import com.sun.jna.Callback;
import com.sun.jna.Library;

public interface CLibrary extends Library {
	// define an interface that wraps the callback code
	public interface Example22CallbackInterface extends Callback {
		void invoke(int val);
	}

	// define an implementation of the callback interface
	public class Example22CallbackImplementation implements Example22CallbackInterface {
		@Override
		public void invoke(int val) {
			System.out.println("[Java] example22: " + val);
		}
	}

	public void example22_triggerCallback(Example22CallbackInterface callback);
}
