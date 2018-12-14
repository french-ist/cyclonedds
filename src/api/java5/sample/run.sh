[ $# != 1 ] && echo  "*** usage : $0 <example>" && exit
java -cp target:target/sample-1.0-SNAPSHOT-jar-with-dependencies.jar org.eclipse.cyclonedds.sample.$1.Launcher