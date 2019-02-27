package org.eclipse.cyclonedds.sample.callback;

import com.sun.jna.Callback;

public class MyCallback implements Callback {
    public void callback(String param1, String param2) {
        System.out.println("[Java] callback(" + '"' + param1 + '"' + ", " + '"' + param2 + '"' + ")");
    }
}
