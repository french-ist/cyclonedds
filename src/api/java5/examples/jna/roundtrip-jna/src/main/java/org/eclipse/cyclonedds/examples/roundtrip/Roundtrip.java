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
package org.eclipse.cyclonedds.examples.roundtrip;

import org.eclipse.cyclonedds.examples.roundtrip.ping.*;
import org.eclipse.cyclonedds.examples.roundtrip.pong.*;

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
            System.out.println("Usage 2: ping quit");
            System.out.println("Usage 3: pong");
            System.exit(-1);
        }

        if(args.length == 1 && args[0].equals("pong")){
            new Roundtrip();
        } else if(args.length == 4 && args[0].equals("ping")){
            new Roundtrip(args[1], args[2], args[3]);
        } else if (args.length == 2 && args[0].equals("ping") && args[1].equals("quit")){
            new RoundtripPing();
        } else {
            System.err.println("bad arguments:" + args.length);
            System.exit(-1);
        }
    }
}
