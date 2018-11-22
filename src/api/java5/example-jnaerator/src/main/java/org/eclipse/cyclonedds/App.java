package org.eclipse.cyclonedds;

import com.sun.jna.*;
import java.nio.*;
import hello.HelloLibrary;
import hello.person_struct;


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
        person_struct x = new person_struct();
        String txt = "a";
        for(int i=0;i<48;i++) {
            txt += "b";
        }
        txt += "c";
        x.setName(txt.getBytes());
        x.setMoney(4);
        HelloLibrary.INSTANCE.person_say_hello(abb, x);
        HelloLibrary.INSTANCE.say_goodbye(bbb);        
    }
}
