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
package org.eclipse.cyclonedds.sample.helloworld_direct_mapping;

import com.sun.jna.Native;
import com.sun.jna.Library;
import com.sun.jna.NativeLibrary;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.IntByReference;

public class HelloworldDirectMapping implements Library {
    public static final String JNA_LIBRARY_NAME = "sample";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(HelloworldDirectMapping.JNA_LIBRARY_NAME);
    static {
        Native.register(HelloworldDirectMapping.class, HelloworldDirectMapping.JNA_NATIVE_LIB);
    }

    public static native void display(String ch);
    public static native void hello(String name, int id);
    
    public static native void helloFromJava(HelloWorldData_Msg.ByReference msg);
    public static native void helloFromC(HelloWorldData_Msg.ByReference msg);
    
    public static native void helloPointerFromJava(Pointer data);    
    public static native void helloPointerArrayFromC(PointerByReference data, IntByReference size);

    public static native void helloArrayFromJava(HelloWorldData_Msg.ByReference sample, int nb);

}
