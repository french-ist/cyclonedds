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

import java.util.concurrent.TimeoutException;
import java.util.HashSet;
import java.util.Collection;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.topic.Topic;

import HelloWorldData.Msg;

public class HelloWorldDataPublisher {

    public static void main(String[] args) {
        // Set "serviceClassName" property to OpenSplice implementation
        System.setProperty(
                ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
                "org.eclipse.cyclonedds.core.CycloneServiceEnvironment");

        // Instantiate a DDS ServiceEnvironment
        ServiceEnvironment env = ServiceEnvironment
                .createInstance(HelloWorldDataPublisher.class.getClassLoader());

        // Get the DomainParticipantFactory
        DomainParticipantFactory dpf = DomainParticipantFactory
                .getInstance(env);

        // Create a DomainParticipant with default domainID
        DomainParticipant p = dpf.createParticipant();

        // Create Reliability and Durability QoS
        Reliability r = PolicyFactory.getPolicyFactory(env).Reliability()
                .withReliable();
        Durability d = PolicyFactory.getPolicyFactory(env).Durability()
                .withTransient();

        // Create a Topic named "HelloWorldData_Msg" and with
        // "HelloWorldData.Msg" as a type.
        // "HelloWorldData.Msg" as a type.
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        Topic<Msg> topic = p.createTopic("HelloWorldData_Msg", Msg.class, p.getDefaultTopicQos().withPolicies(r, d), null , statuses);

        // Create a Partition QoS with "HelloWorld example" as partition.
        Partition partition = PolicyFactory.getPolicyFactory(env).Partition()
                .withName("HelloWorld example");

        // Create a Subscriber using default QoS except partition
        Publisher pub = p.createPublisher(p.getDefaultPublisherQos()
                .withPolicy(partition));


        // Create DataReader on our topic with default QoS except Reliability
        // and Durability
        DataWriter<Msg> writer = pub.createDataWriter(topic, pub
                .getDefaultDataWriterQos().withPolicies(r, d));

        // The message we want to publish
        Msg msg = new Msg(1, "Hello World");

        try {
            System.out
                    .println(" ________________________________________________________________");
            System.out.println("|");
            System.out.println("| Publish message : " + msg.message);
            System.out
                    .println("|________________________________________________________________");
            System.out.println("");

            // Publish the message
            writer.write(msg);
        } catch (TimeoutException e) {
            // TimeoutException may happen using Reliable QoS (if publication
            // buffers are full)
            e.printStackTrace();
        }

        try {
            // Wait to ensure data is received before we delete writer
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        // Close Participant (closing also children entities: Topic, Publisher,
        // DataWriter)
        p.close();
    }
}
