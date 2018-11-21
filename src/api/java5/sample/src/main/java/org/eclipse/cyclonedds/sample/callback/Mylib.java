package org.eclipse.cyclonedds.sample.callback;

import com.sun.jna.Library;

public interface Mylib extends Library {
    int myfunc(String name);

    int myfunc2(MyCallback myc, String name);

    void registerCallback(MyCallback myc);

    public void open_triggerCallback(Functions.OpenFuncInterface callback);

    public void close_triggerCallback(Functions.CloseFuncInterface callback);

    // define an implementation of the callback interface
    public class OpenFuncImplementation implements Functions.OpenFuncInterface {
        @Override
        public int invoke(String name, int option) {
            System.out.println("[Java] open(name : " + name + ", option : " + option + ")");
            return 2000;
        }
    }

    // define an implementation of the callback interface
    public class CloseFuncImplementation implements Functions.CloseFuncInterface {
        @Override
        public int invoke(int fd) {
            System.out.println("[Java] close(fd " + fd + ")");
            return 3000;
        }
    }

    // use of structure of callbacks
    void init(Functions.ByValue funcs);
    int myopen(String name);
    int myclose(int fd);
}