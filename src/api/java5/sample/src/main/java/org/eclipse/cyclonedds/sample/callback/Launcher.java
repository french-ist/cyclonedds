package org.eclipse.cyclonedds.sample.callback;

import com.sun.jna.Native;

public class Launcher {
    public static void main(String[] args) {
        Mylib lib = (Mylib) Native.load("sample", Mylib.class);
        lib.registerCallback(new MyCallback());
        System.out.println("[Java] calling myfunc(test1)");
        int res = lib.myfunc("test1");
        System.out.println("[Java] result of myfunc = " + res);

        // instantiate a callback wrapper instances
        Functions funcs = new Functions();
        funcs.open = new Mylib.OpenFuncImplementation();
        funcs.close = new Mylib.CloseFuncImplementation();
        // pass the callback wrapper to the C library
        lib.open_triggerCallback(funcs.open);
        // pass the callback wrapper to the C library
        lib.close_triggerCallback(funcs.close);
    }
}