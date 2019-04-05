/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package validtest.sub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.ViewState;
import org.omg.dds.topic.Topic;

import HelloWorldData.Msg;


public class SampleInfoTest
{

   ServiceEnvironment env;
   DomainParticipant dp;
   Topic<Msg> topic;
   Publisher pub;
   Subscriber sub;
   DataWriter<Msg> writer;
   DataReader<Msg> reader;

   @Before
   public void init()
   {
      System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
            "org.eclipse.cyclonedds.core.CycloneServiceEnvironment");
      env = ServiceEnvironment.createInstance(
            SampleInfoTest.class.getClassLoader());

      DomainParticipantFactory dpf = DomainParticipantFactory.getInstance(env);
      dp = dpf.createParticipant(0);
      topic = dp.createTopic("SampleStateViewStateTest", Msg.class);

      Partition partition = PolicyFactory.getPolicyFactory(env).Partition()
            .withName(Arrays.asList("SampleStateViewStateTest"));
      Reliability reliability = PolicyFactory.getPolicyFactory(env).Reliability().withReliable();
      History history = PolicyFactory.getPolicyFactory(env).History().withDepth(10);

      pub = dp.createPublisher(dp.getDefaultPublisherQos().withPolicy(partition));
      writer = pub.createDataWriter(topic, pub.getDefaultDataWriterQos().withPolicy(reliability)
            .withPolicy(history));

      sub = dp.createSubscriber(dp.getDefaultSubscriberQos().withPolicy(partition));
      reader = sub.createDataReader(topic, sub.getDefaultDataReaderQos().withPolicy(reliability)
            .withPolicy(history));


   }

   @After
   public void clear()
   {
      dp.close();
   }   
}
