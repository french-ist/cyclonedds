package org.eclipse.cyclonedds.sample.callback;

import com.sun.jna.Native;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("\n=== Simple");
        Mylib lib = (Mylib) Native.load("sample", Mylib.class);
        lib.registerCallback(new MyCallback());
        System.out.println("[Java] calling myfunc(test1)");
        int res = lib.myfunc("test1");
        System.out.println("[Java] result of myfunc = " + res);

        res = lib.myfunc2(new MyCallback(), "test2");
        System.out.println("[Java] result of myfunc2 = " + res);

        System.out.println("\n=== Using a callback wrapper");
        // instantiate a callback wrapper instances
        Functions funcs = new Functions();
        funcs.open = new Mylib.OpenFuncImplementation();
        funcs.close = new Mylib.CloseFuncImplementation();
        System.out.println("\n   . pass the callback wrapper to the C library");
        // pass the callback wrapper to the C library
        lib.open_triggerCallback(funcs.open); 
        // pass the callback wrapper to the C library
        lib.close_triggerCallback(funcs.close);
        System.out.println("\n   .. invoke callback from Java");
        int fd = funcs.open.invoke("(from Java) myfile", 3000);
        funcs.close.invoke(fd);

        System.out.println("\n=== structure of callbacks");
        // using structure of callbacks
        Functions.ByValue funcs2 = new Functions.ByValue();
        funcs2.open = new Mylib.OpenFuncImplementation();
        funcs2.close = new Mylib.CloseFuncImplementation();
        lib.init(funcs2);
        lib.myopen("myfile using structure of callbacks");
        lib.myclose(fd);
    }
}