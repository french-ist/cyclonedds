package org.eclipse.cyclonedds.sample.helloworld_direct_mapping;

import com.sun.jna.Native;

public class HelloworldDirectMapping {
    public static native void display(String ch);

    static {
        Native.register("sample");
    }
}