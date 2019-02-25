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
import org.omg.dds.sub.*;
import org.omg.dds.topic.Topic;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.omg.dds.core.GuardCondition;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.domain.DomainParticipantFactory;


/**
 * This class serves as a container holding initialized entities used for subscribing
 */
class SubEntities
{
    public SubEntities(String partitionName) throws Exception, NullPointerException
    {
         /*
         * Select DDS implementation and initialize DDS ServiceEnvironment
         */
        System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
                "org.eclipse.cyclonedds.core.CycloneServiceEnvironment");
        env = ServiceEnvironment.createInstance(
                SubEntities.class.getClassLoader());
        PolicyFactory policyFactory = env.getSPI().getPolicyFactory();
        Partition subscriberPartition = policyFactory.Partition().withName(Arrays.asList(partitionName));

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
        
        Duration max_blocking_time = env.getSPI().newDuration(10, TimeUnit.SECONDS);
        Reliability reliable = policyFactory.Reliability().withReliable().withMaxBlockingTime(max_blocking_time);
        History history = policyFactory.History().withKeepAll();        
        ResourceLimits resourcelimits = policyFactory.ResourceLimits().withMaxSamples(400);  // JM 100 for Writer        
        drQos = drQos.withPolicies(reliable).withPolicies(history).withPolicies(resourcelimits);
        reader = subscriber.createDataReader(
                topic, drQos);
        //ExampleError.CheckHandle(reader, "Entities, publisher.createDataReader");

        /**
         * A StatusCondition is created which is triggered when data is
         * available to read
         */
        StatusCondition<DataReader<ThroughputModule.DataType>> condition = reader.getStatusCondition();
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
    public ThroughputModule.DataType typeSupport;
    public Topic<ThroughputModule.DataType> topic;
    public Subscriber subscriber;
    public DataReader<ThroughputModule.DataType> reader;
    public WaitSet waitSet;
    public StatusCondition dataAvailable;
    public GuardCondition terminated;
}
