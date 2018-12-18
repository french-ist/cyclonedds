package org.eclipse.cyclonedds.sample.helloworld_direct_mapping;

import com.sun.jna.Native;

public class HelloworldDirectMapping {
    public static native void display(String ch);
    public static native void hello(String name, int id);

    public static native void create_domain(int id);
    static {
        Native.register("sample");
    }
}
