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
        Pointer arrayPtr = samplePtr.getValue();
        HelloWorldData_Msg arryRef = new HelloWorldData_Msg(arrayPtr);
        arryRef.read();

        //showing
        HelloWorldData_Msg[] msgArray = (HelloWorldData_Msg[])arryRef.toArray(size);
        for(int i=0;i<size;i++){                        
            System.out.println("helloPointerArrayFromC String:{"+msgArray[i].message+"} int:{"+msgArray[i].userID+"} ");
        }

    }
}
