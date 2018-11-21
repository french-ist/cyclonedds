package org.eclipse.cyclonedds;

import com.sun.jna.*;
import java.nio.*;
import hello.HelloLibrary;


/**
 * Hello world!
 */
public class App 
{
    public static void main( String[] args )
    {
        byte[] a = "Java said Bonjour!!".getBytes();
        byte[] b = "Au revoir!".getBytes();
        ByteBuffer abb = ByteBuffer.wrap(a, 0, a.length);
        ByteBuffer bbb = ByteBuffer.wrap(b, 0, b.length);        
        HelloLibrary.INSTANCE.say_hello(abb);
        HelloLibrary.INSTANCE.say_goodbye(bbb);        
    }
}
