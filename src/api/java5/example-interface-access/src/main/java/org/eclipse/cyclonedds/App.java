package org.eclipse.cyclonedds;
import com.sun.jna.*;

import main.java.org.eclpise.cyclonedds.CHelloItf;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CHelloItf.INSTANCE.display("Bonjour tout le monde\n");
        CHelloItf.INSTANCE.printf("J'ai %d chats\n", 2);
    }
}
