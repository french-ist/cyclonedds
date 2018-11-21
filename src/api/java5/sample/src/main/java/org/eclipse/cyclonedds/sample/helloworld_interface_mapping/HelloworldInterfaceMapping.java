package org.eclipse.cyclonedds.sample.helloworld_interface_mapping;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface HelloworldInterfaceMapping extends Library {
    HelloworldInterfaceMapping INSTANCE = (HelloworldInterfaceMapping) Native.load("sample",
            HelloworldInterfaceMapping.class);
    void display(String g);
}