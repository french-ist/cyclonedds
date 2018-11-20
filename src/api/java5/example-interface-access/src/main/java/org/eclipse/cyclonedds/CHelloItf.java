package main.java.org.eclpise.cyclonedds;

import com.sun.jna.*;

public interface CHelloItf extends Library {
    CHelloItf INSTANCE = (CHelloItf) Native.loadLibrary("hello", CHelloItf.class);
    void display(String g);
    int printf(String format, Object... args); // From stdio
}
  
