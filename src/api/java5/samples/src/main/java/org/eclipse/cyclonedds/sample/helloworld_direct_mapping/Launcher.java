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
package org.eclipse.cyclonedds.sample.helloworld_direct_mapping;

import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.IntByReference;

import com.sun.jna.Pointer;
import com.sun.jna.Native;

public class Launcher {
    public static void main(String[] args) {
        HelloworldDirectMapping.display("Hello Direct mapping");
        HelloworldDirectMapping.hello("John", 25);

        //filling struct from Java
        HelloWorldData_Msg.ByReference msgJ = new  HelloWorldData_Msg.ByReference();
        msgJ.message = "Hi!";
        msgJ.userID = 20;
        HelloworldDirectMapping.helloFromJava(msgJ);

        //filling struct from Java, passing a pointer
        HelloworldDirectMapping.helloPointerFromJava(msgJ.getPointer());

        //filling struct from C
        HelloWorldData_Msg.ByReference msgC = new  HelloWorldData_Msg.ByReference();
        HelloworldDirectMapping.helloFromC(msgC);        
        System.out.println("String:{"+msgC.message+"} int:{"+msgC.userID+"}");

        //---------------------- helloPointerArrayFromC ---------------------------
        //pointers to be filled
        PointerByReference samplePtr = new PointerByReference();
        IntByReference intPtr = new IntByReference();
        
        //reading from C
        HelloworldDirectMapping.helloPointerArrayFromC(samplePtr, intPtr);        
        int size = intPtr.getValue();
        HelloWorldData_Msg arryRef = new HelloWorldData_Msg(samplePtr.getValue());
        arryRef.read();

        //showing
        HelloWorldData_Msg[] msgArray = (HelloWorldData_Msg[])arryRef.toArray(size);
        for(int i=0;i<size;i++){                        
            System.out.println("helloPointerArrayFromC String:{"+msgArray[i].message+"} int:{"+msgArray[i].userID+"} ");
        }

        //---------------------- helloArrayFromJava ---------------------------
        HelloWorldData_Msg.ByReference helloByRef = new HelloWorldData_Msg.ByReference();
        int nb = 3;
        HelloWorldData_Msg[] arrMsg =  (HelloWorldData_Msg[])helloByRef.toArray(nb);
        for(int i=0;i<nb;i++){
            arrMsg[i].userID = (i+3)*17;
            arrMsg[i].message = "Message_" + arrMsg[i].userID;            
        }
        HelloworldDirectMapping.helloArrayFromJava(helloByRef, nb);
    }
}
