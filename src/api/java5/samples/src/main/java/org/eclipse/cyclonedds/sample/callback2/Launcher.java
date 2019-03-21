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
package org.eclipse.cyclonedds.sample.callback2;

import com.sun.jna.Native;
// If your native code initializes function pointers within a struct.
// JNA will automatically generate a Callback instance matching the declared type.
// This enables you to easily call the function supplied by native code using proper Java syntax.

public class Launcher {
    public static void main(String[] args) {
        /*Mylib lib = (Mylib) Native.load("sample", Mylib.class);
        System.out.println("\n=== structure of callbacks");
        // using structure of callbacks
        Functions funcs = new Functions();
        funcs.open = new Mylib.OpenFuncImplementation();
        funcs.close= new Mylib.CloseFuncImplementation();
        // "call your native library function that initializes your data". 
        // If you call a native function which populates your structure, 
        // then the callback field will get set up properly by the time the function returns
        System.out.println("=== (1) [Java] funcs.open = " + funcs.open + " - funcs.open = " + funcs.close);
        lib.init2(funcs);
        System.out.println("=== (2) [Java] funcs.open = " + funcs.open + " - funcs.open = " + funcs.close);
        int fd = funcs.open.invoke("myfile", 0);
        System.out.println("=== fd = " + fd);
        funcs.close.invoke(fd);*/
    }
}