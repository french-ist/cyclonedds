package org.eclipse.cyclonedds.roundtrip;

import java.nio.IntBuffer;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.helper.NativeSize;

public class Roundtrip
{
    //pong
    public Roundtrip(){        
        new RoundtripPong();
    }

    //ping
    public Roundtrip(String payloadSize, String numSamples, String timeOut){
        new RoundtripPing(payloadSize, numSamples, timeOut);
    }

    public static void main( String[] args )
    {
        if(args.length <=0 ){
            System.out.println("Usage 1: ping [payloadSize (bytes, 0 - 100M)] [numSamples (0 = infinite)] [timeOut (seconds, 0 = infinite)]");
            System.out.println("Usage 2: pong");
            System.exit(-1);
        }

        if(args.length == 1 && args[0].equals("pong")){
            new Roundtrip();
        } else if(args.length == 4 && args[0].equals("ping")){
            new Roundtrip(args[1], args[2], args[3]);
        } else {
            System.err.println("bad arguments:" + args.length);
            System.exit(-1);
        }
    }
}
