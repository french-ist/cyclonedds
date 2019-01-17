package org.eclipse.cyclonedds.roundtrip;

import org.eclipse.cyclonedds.roundtrip.ping.RoundtripPing;
import org.eclipse.cyclonedds.roundtrip.pong.RoundtripPong;

public class Roundtrip
{
    public Roundtrip(){
        new RoundtripPong();
    }

    public Roundtrip(String payloadSize, String numSamples, String timeOut) {
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
        } else if (args.length == 2 && args[0].equals("ping") && args[1].equals("quit")){
            new RoundtripPing();
        }
        else {
            System.err.println("bad arguments:" + args.length);
            System.exit(-1);
        }
    }
}
