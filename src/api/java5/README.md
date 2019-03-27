# Eclipse Cyclone Java5 DDS

Built on top of the Eclipse Cyclone C DDS, Java5 DDS is by far the most performant and robust Java DDS implementation available on the market. 

Beside, Cyclone Java5 DDS is developed completely in the open and is undergoing the acceptance process to become part of Eclipse IoT (see  [eclipse-cyclone-dds](https://projects.eclipse.org/proposals/eclipse-cyclone-dds)).


# Getting Started
## Building Cyclone Java5 DDS
In order to build Java5 Cyclone DDS you need 
* to have installed on your host [cmake](https://cmake.org/download/) **v3.6.0** or higher, the [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or simply the [Java 8 RE](http://www.oracle.com/technetwork/java/javase/downloads/server-jre8-downloads-2133154.html), and [Apache Maven 3.5.x or higher](http://maven.apache.org/download.cgi).
* to have built Eclipse Cyclone C DDS 

Assuming that **git** is also available on your machine then, simply do :

    $ git clone https://github.com/eclipse/cyclonedds.git
And once you have built [Cyclone C](../../../README.md)<br>

    $ cd cyclonedds/src/api/java5
    $ mvn clean install

The previous command will build the Cyclone Java5 DDS library and the examples. So at this point you are ready to 

* run the Java examples
* use Cyclone Java5 DDS 

## Performance

TODO Median small message throughput measured using the Throughput example 

There is some data on roundtrip latency below.


## Examples
Now let's experiment with some examples.

### Building and Running the HelloWorld Example
At this point you can run the example directly as it has been built by the previous maven command.
If you want to (re)build it :
    $ cd cyclonedds/src/api/java5/examples/HelloWorld
    $ mvn clean install
To run it 
    $ ./run.sh 

### Building and Running the Roundtrip Example
The second example measures cyclonedds latency and will allow you to see with your eyes how fast it is!

Do as follows:
  
If you want to (re)build it :
    $ cd cyclonedds/src/api/java5/examples/Roundtrip
    $ mvn clean install
    
Now that you've build the roundtrip example it is time to run it. 

On one terminal start the application that will be responding to **cyclonejavadds** pings.
    $ ./pong.sh

On another terminal, start the application that will be sending the pings.
    
    $ ./ping.sh 0 0 0 

    # payloadSize: 0 | numSamples: 0 | timeOut: 0
    # Waiting for startup jitter to stabilise
    # Warm up complete.
    # Round trip measurements (in us)
    #             Round trip time [us]                           Write-access time [us]       Read-access time [us]
    # Seconds     Count   median      min      99%      max      Count   median      min      Count   median      min
     TODO insert results
           
The number above were measured on TODO running a TODO GHz Intel Core i7 on TODO. From these numbers you can see how the roundtrip is incredibly stable and the minimal latency is now down to TODO micro-seconds (used to be TODO micro-seconds) on this HW.

## Documentation
The Cyclone DDS documentation is available [here](TODO).

