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