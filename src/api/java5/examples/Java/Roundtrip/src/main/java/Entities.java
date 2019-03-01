/**
 *                             Vortex Cafe
 *
 *    This software and documentation are Copyright 2010 to 2019 ADLINK
 *    Technology Limited, its affiliated companies and licensors. All rights
 *    reserved.
 *
 *    Licensed under the ADLINK Software License Agreement Rev 2.7 2nd October
 *    2014 (the "License"); you may not use this file except in compliance with
 *    the License.
 *    You may obtain a copy of the License at:
 *                        docs/LICENSE.html
 *
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
import java.util.ArrayList;
import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.WaitSet;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.*;
import org.omg.dds.topic.Topic;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.omg.dds.core.GuardCondition;
import org.omg.dds.core.policy.WriterDataLifecycle;
import org.omg.dds.domain.DomainParticipantFactory;

/**
 * This class serves as a container holding initialized entities used by Ping
 * and Pong
 */
class Entities {

    public Entities(String pubPartition, String subPartition) throws Exception, NullPointerException {
        /*
         * Select DDS implementation and initialize DDS ServiceEnvironment
         */
        System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
                "com.prismtech.cafe.core.ServiceEnvironmentImpl");
        env = ServiceEnvironment.createInstance(
                Entities.class.getClassLoader());
        PolicyFactory policyFactory = env.getSPI().getPolicyFactory();
        Partition publisherPartition = policyFactory.Partition().withName(Arrays.asList(pubPartition));
        Partition subscriberPartition = policyFactory.Partition().withName(Arrays.asList(subPartition));

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
        topic = participant.createTopic("RoundTrip", RoundTripModule.DataType.class);
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
        WriterDataLifecycle writer_data_lifecycle = policyFactory.WriterDataLifecycle().withAutDisposeUnregisteredInstances(false);
        dwQos = dwQos.withPolicies(reliable).withPolicies(writer_data_lifecycle);
        writer = publisher.createDataWriter(
                topic, dwQos);
        //ExampleError.CheckHandle(writer, "Entities, publisher.createDataWriter");

        /**
         * A Subscriber is created on the domain participant.
         */
        SubscriberQos subQos = participant.getDefaultSubscriberQos();
        //ExampleError.CheckHandle(subQos, "Entities, participant.getDefaultSubscriberQos");
        subQos = subQos.withPolicies(subscriberPartition);
        subscriber = participant.createSubscriber(subQos);
        //ExampleError.CheckHandle(subscriber, "Entities, participant.createSubscriber");

        /**
         * A DDS::DataReader is created on the Subscriber & Topic with the
         * default Topic QoS.
         */
        DataReaderQos drQos = subscriber.getDefaultDataReaderQos();
        //ExampleError.CheckHandle(drQos, "Entities, publisher.drQos");
        drQos = drQos.withPolicies(reliable);
        reader = subscriber.createDataReader(
                topic, drQos);
        //ExampleError.CheckHandle(reader, "Entities, publisher.createDataReader");

        /**
         * A StatusCondition is created which is triggered when data is
         * available to read
         */
        StatusCondition<DataReader<RoundTripModule.DataType>> condition = reader.getStatusCondition();
        //ExampleError.CheckHandle(condition, "Entities, reader.getStatusCondition");
        Collection<Class<? extends Status>> statuses = new ArrayList<Class<? extends Status>>();
        statuses.add(DataAvailableStatus.class);
        condition.setEnabledStatuses(statuses);

        /**
         * A WaitSet is created and the data available status condition is
         * attached
         */
        waitSet = env.getSPI().newWaitSet();
        waitSet.attachCondition(condition);

        terminated = env.getSPI().newGuardCondition();
        waitSet.attachCondition(terminated);
    }
    public ServiceEnvironment env;
    public DomainParticipantFactory domainParticipantFactory;
    public DomainParticipant participant;
    public RoundTripModule.DataType typeSupport;
    public Topic<RoundTripModule.DataType> topic;
    public Publisher publisher;
    public DataWriter<RoundTripModule.DataType> writer;
    public Subscriber subscriber;
    public DataReader<RoundTripModule.DataType> reader;
    public WaitSet waitSet;
    public StatusCondition dataAvailable;
    public GuardCondition terminated;
}
