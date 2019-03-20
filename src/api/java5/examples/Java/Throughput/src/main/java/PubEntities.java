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
import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.topic.Topic;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.omg.dds.core.GuardCondition;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.domain.DomainParticipantFactory;

/**
 * This class serves as a container holding initialised entities used for
 * publishing
 */
class PubEntities {

    public PubEntities(String partitionName) throws Exception, NullPointerException {

        /*
         * Select DDS implementation and initialize DDS ServiceEnvironment
         */
        System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
                "org.eclipse.cyclonedds.core.CycloneServiceEnvironment");
        env = ServiceEnvironment.createInstance(
                PubEntities.class.getClassLoader());
        PolicyFactory policyFactory = env.getSPI().getPolicyFactory();
        Partition publisherPartition = policyFactory.Partition().withName(Arrays.asList(partitionName));

        /**
         * A DomainParticipant is created for the default domain.
         */
        domainParticipantFactory = DomainParticipantFactory.getInstance(env);
        //ExampleError.CheckHandle(domainParticipantFactory, "Entities, DDS.DomainParticipantFactory.get_instance");
        participant = domainParticipantFactory.createParticipant(0);
        //ExampleError.CheckHandle(participant, "Entities, domainParticipantFactory.create_participant");

        /**
         * A Topic is created for our sample type on the domain participant.
         */
        topic = participant.createTopic("Throughput", ThroughputModule.DataType.class);
        //ExampleError.CheckHandle(topic, "Entities, participant.create_topic");

        /**
         * A Publisher is created on the domain participant.
         */
        PublisherQos pubQos = participant.getDefaultPublisherQos();
        //ExampleError.CheckHandle(pubQos, "Entities, participant.getDefaultPublisherQos");
        pubQos = pubQos.withPolicies(publisherPartition);
        publisher = participant.createPublisher(pubQos);
        //ExampleError.CheckHandle(publisher, "Entities, participant.createPublisher");

        /**
         * A DataWriter is created on the Publisher & Topic with a modified Qos.
         */
        DataWriterQos dwQos = publisher.getDefaultDataWriterQos();
        //ExampleError.CheckHandle(dwQos, "Entities, publisher.getDefaultDataWriterQos");
        Duration max_blocking_time = env.getSPI().newDuration(10, TimeUnit.SECONDS);
        Reliability reliable = policyFactory.Reliability().withReliable().withMaxBlockingTime(max_blocking_time);
        History history = policyFactory.History().withKeepAll();
        //WriterDataLifecycle writer_data_lifecycle = policyFactory.WriterDataLifecycle().withAutDisposeUnregisteredInstances(false);
        ResourceLimits resourcelimits = policyFactory.ResourceLimits().withMaxSamples(100);
        dwQos = dwQos.withPolicies(reliable).withPolicies(history).withPolicies(resourcelimits);
        writer = publisher.createDataWriter(
                topic, dwQos);
        //ExampleError.CheckHandle(writer, "Entities, publisher.createDataWriter");

        terminated = env.getSPI().newGuardCondition();

    }

    public ServiceEnvironment env;
    public DomainParticipantFactory domainParticipantFactory;
    public DomainParticipant participant;
    public ThroughputModule.DataType typeSupport;
    public Topic<ThroughputModule.DataType> topic;
    public Publisher publisher;
    public DataWriter<ThroughputModule.DataType> writer;
    public GuardCondition terminated;
}
