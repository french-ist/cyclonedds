package org.eclipse.cyclonedds.roundtrip;

import java.nio.IntBuffer;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.helper.NativeSize;

public class RoundtripPong 
{
    RoundTripModule_DataType_Helper helper = new RoundTripModule_DataType_Helper();

    public RoundtripPong(){
        System.err.println("PONG");
    }
}
