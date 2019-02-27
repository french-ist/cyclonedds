package org.eclipse.cyclonedds.sample.callback2;

import com.sun.jna.Library;

// If your native code initializes function pointers within a struct.
// JNA will automatically generate a Callback instance matching the declared type.
// This enables you to easily call the function supplied by native code using proper Java syntax.

public interface Mylib extends Library {
    public void init2(Functions funcs);
}
