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

}