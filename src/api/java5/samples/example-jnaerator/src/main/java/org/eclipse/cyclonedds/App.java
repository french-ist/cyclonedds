/**
 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds;
import com.sun.jna.NativeString;
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
        HelloLibrary.INSTANCE.say_hello("abb");
        person_struct x = new person_struct();
        String txt = "a";
        for(int i=0;i<48;i++) {
            txt += "b";
        }
        txt += "c";
        NativeString nstr = new NativeString(txt);
        x.setName(nstr.getPointer());
        x.setMoney(4);
        HelloLibrary.INSTANCE.person_say_hello(abb, x);
        HelloLibrary.INSTANCE.say_goodbye("bbb");        
    }
}
