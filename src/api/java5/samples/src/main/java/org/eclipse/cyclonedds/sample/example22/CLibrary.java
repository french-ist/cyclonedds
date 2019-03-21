/**
 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
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
