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


   public void write(Msg msg)
   {
      try
      {
         writer.write(msg);
      }
      catch (TimeoutException e)
      {
         Assert.fail("write : TimeoutException");
      }
   }

   public void writeWithTimestamp(Msg msg, Time t)
   {
      try
      {
         writer.write(msg, t);
      }
      catch (TimeoutException e)
      {
         Assert.fail("write : TimeoutException");
      }
   }

   public void sleep(long timeout)
   {
      try
      {
         Thread.sleep(timeout);
      }
      catch (InterruptedException e)
      {
         // DO NOTHING
      }
   }

   @Test
   public void sampleInfo1Test()
   {
      sleep(1000);

      Msg msg1 = new Msg(1, "MSG1");
      Msg msg2 = new Msg(1, "MSG2");
      Msg msg3 = new Msg(2, "MSG3");

      write(msg1);

      sleep(1000);

      List<Sample<Msg>> samples = new ArrayList<Sample<Msg>>(10);

      reader.read(samples);

      Assert.assertEquals(1, samples.size());
      Assert.assertEquals(SampleState.NOT_READ, samples.get(0).getSampleState());
      Assert.assertEquals(ViewState.NEW, samples.get(0).getViewState());


      write(msg2);
      write(msg3);

      sleep(1000);

      samples = new ArrayList<Sample<Msg>>(10);

      reader.read(samples);

      Assert.assertEquals(3, samples.size());
      Assert.assertEquals(SampleState.READ, samples.get(0).getSampleState());
      Assert.assertEquals(ViewState.NOT_NEW, samples.get(0).getViewState());

      Assert.assertEquals(SampleState.NOT_READ, samples.get(1).getSampleState());
      Assert.assertEquals(ViewState.NOT_NEW, samples.get(1).getViewState());

      Assert.assertEquals(SampleState.NOT_READ, samples.get(2).getSampleState());
      Assert.assertEquals(ViewState.NEW, samples.get(2).getViewState());


      samples = new ArrayList<Sample<Msg>>(10);

      reader.take(samples);

      Assert.assertEquals(3, samples.size());
      Assert.assertEquals(SampleState.READ, samples.get(0).getSampleState());
      Assert.assertEquals(ViewState.NOT_NEW, samples.get(0).getViewState());
      Assert.assertNotNull(samples.get(0).getSourceTimestamp());

      Assert.assertEquals(SampleState.READ, samples.get(1).getSampleState());
      Assert.assertEquals(ViewState.NOT_NEW, samples.get(1).getViewState());
      Assert.assertNotNull(samples.get(1).getSourceTimestamp());

      Assert.assertEquals(SampleState.READ, samples.get(2).getSampleState());
      Assert.assertEquals(ViewState.NOT_NEW, samples.get(2).getViewState());
      Assert.assertNotNull(samples.get(2).getSourceTimestamp());

      Assert.assertTrue( (samples.get(0).getSourceTimestamp()
            .compareTo(samples.get(1).getSourceTimestamp()) < 0) &&
            (samples.get(1).getSourceTimestamp()
                  .compareTo(samples.get(2).getSourceTimestamp()) <= 0));

      writeWithTimestamp(msg1, Time.newTime(120, TimeUnit.SECONDS, env));
      writeWithTimestamp(msg2, Time.newTime(110, TimeUnit.SECONDS, env));
      writeWithTimestamp(msg3, Time.newTime(100, TimeUnit.SECONDS, env));
      sleep(1000);

      samples = new ArrayList<Sample<Msg>>(10);

      reader.read(samples);
      Assert.assertEquals(3, samples.size());
      Assert.assertTrue( (samples.get(0).getSourceTimestamp()
            .compareTo(samples.get(1).getSourceTimestamp()) > 0) &&
            (samples.get(1).getSourceTimestamp()
                  .compareTo(samples.get(2).getSourceTimestamp()) > 0) &&
            samples.get(0).getSourceTimestamp().getTime(TimeUnit.SECONDS) == 120 &&
            samples.get(1).getSourceTimestamp().getTime(TimeUnit.SECONDS) == 110 &&
            samples.get(2).getSourceTimestamp().getTime(TimeUnit.SECONDS) == 100
            );

   }
}
