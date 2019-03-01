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
HelloWorld example demonstrates a simple usage of DDS.
The HelloWorldPublisher class publishes a "HelloWorld" message on a "HelloWorldData_Msg" Topic.
The HelloWorldSubscriber class subscribes to this Topic and display the received messages.

Building the demo
-----------------
Open a terminal in the helloworld/ directory.

If you use Ant run the following command:
   ant

If you use Apache Maven run the following command:
   mvn package


Running the demo
----------------
Open a command prompt and change directory to "helloworld".
Enter the following command to start the HelloWorldSubscriber:
   run_subscriber.sh
   (or run_subscriber.bat for Windows)

Open another command prompt and enter the following command to start the HelloWorldPublisher:
   run_publisher.sh
   (or run_publisher.bat for Windows)
