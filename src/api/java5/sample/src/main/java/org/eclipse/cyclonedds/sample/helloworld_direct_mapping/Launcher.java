package org.eclipse.cyclonedds.sample.helloworld_direct_mapping;

public class Launcher {
    public static void main(String[] args) {
        HelloworldDirectMapping.display("Hello Direct mapping");
        HelloworldDirectMapping.hello("John", 25);
        int domainid = 1234;
        HelloworldDirectMapping.create_domain(domainid);
    }
}
