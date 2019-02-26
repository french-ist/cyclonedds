====
                                Vortex Cafe

       This software and documentation are Copyright 2010 to 2019 ADLINK
       Technology Limited, its affiliated companies and licensors. All rights
       reserved.

       Licensed under the ADLINK Software License Agreement Rev 2.7 2nd October
       2014 (the "License"); you may not use this file except in compliance with
       the License.
       You may obtain a copy of the License at:
                           docs/LICENSE.html

       See the License for the specific language governing permissions and
       limitations under the License.
====

Description
-----------
Roundtrip example consists of two executables that will exchange data using 2 partitions.
Running both executables allows to measure roundtrip duration when sending and
receiving back a single message.


Building the demo
-----------------
Open a terminal in the roundtrip/ directory.

If you use Ant run the following command:
   ant

If you use Apache Maven run the following command:
   mvn package


Running the demo
----------------
Open a command prompt and change directory to "roundtrip". Enter the following command:
   java -jar target/pong.jar

Open another command prompt and enter the following command:
   java -jar target/ping.jar

     "Usage (parameters must be supplied in order):\n"
                    + "./ping [payloadSize (bytes, 0 - 655536)] [numSamples (0 = infinite)] [timeOut (seconds, 0 = infinite)]\n"
                    + "./ping quit - ping sends a quit signal to pong.\n"
                    + "Defaults:\n"
                    + "./ping 0 0 0");

Ping/Pong command options
-------------------------
The pong command doesn't accept any options.

The ping command accepts following options:
  java -jar target/ping.jar [payloadSize] [numSamples] [timeOut]

  Where:
    - payloadSize:  data size in bytes (0 - 655536)
    - numSamples:   number of samples to send (0 = infinite)
    - timeOut:      max execution time in seconds (0 = infinite)
  Default options values are equivalent to:
     java -jar target/ping.jar 0 0 0

To send a 'quit' signal to pong use following command:
   java -jar target/ping.jar quit


Compatibility with OpenSpliceDDS' roundtrip example
--------------------------------------------------
This example is fully compatible with OpenSpliceDDS' roundtrip example.
You can use this example's "ping" command with OpenSpliceDDS' "pong" command,
or the OpenSpliceDDS' "ping" command with this example's "pong" command.

