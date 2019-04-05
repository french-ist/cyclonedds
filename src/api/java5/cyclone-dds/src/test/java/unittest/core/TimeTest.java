/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package unittest.core;

import java.util.concurrent.TimeUnit;

import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.sub.SampleImpl;
import org.eclipse.cyclonedds.sub.SampleInfo;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.omg.dds.sub.Sample;

import HelloWorldData.Msg;
import validtest.sub.SampleInfoTest;


public class TimeTest
{

   static ServiceEnvironment env;

   @BeforeClass
   public static void init()
   {
      System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
            "org.eclipse.cyclonedds.core.CycloneServiceEnvironment");
      env = ServiceEnvironment.createInstance(
            SampleInfoTest.class.getClassLoader());
   }

   /**
    * Test that Time.invalidTime() returns an invalid time.
    * The scenario is the following :
    * <ul>
    * <li>call Time.invalidTime(env)
    * <li>test that result is not valid, calling isValid() operation
    * </ul>
    */
   @Test
   public void InvalidTimeTest()
   {
      Time invalid = Time.invalidTime(env);
      Assert.assertFalse(invalid.isValid());
   }

   /**
    * Test that Time.newTime() returns a correct Time instance,
    * and that its getTime() and getRemainder() operation behave as expected
    * The scenario is the following :
    * <ul>
    * <li>call Time.newTime() several times with different values (1 day, 1 hour, 1 minute, 1
    * second, 1 millisecond, 1 microsecond and 1 nanosecond)
    * <li>for each value, test that the getTime(TimeUnit.SECONDS) and getRemainder(TimeUnit.SECONDS,
    * TimeUnit.NANOSECONDS) return the correct values.
    * </ul>
    */
   @Test
   public void NewTimeTest()
   {
      Time t0 = Time.newTime(0, TimeUnit.NANOSECONDS, env);
      Assert.assertTrue(t0.isValid());
      Assert.assertEquals(0L, t0.getTime(TimeUnit.SECONDS));
      Assert.assertEquals(0L, t0.getRemainder(TimeUnit.SECONDS, TimeUnit.NANOSECONDS));

      Time day1 = Time.newTime(1, TimeUnit.DAYS, env);
      Assert.assertTrue(day1.isValid());
      Assert.assertEquals(24L * 3600L, day1.getTime(TimeUnit.SECONDS));
      Assert.assertEquals(0L, day1.getRemainder(TimeUnit.SECONDS, TimeUnit.NANOSECONDS));

      Time hour1 = Time.newTime(1, TimeUnit.HOURS, env);
      Assert.assertTrue(hour1.isValid());
      Assert.assertEquals(3600L, hour1.getTime(TimeUnit.SECONDS));
      Assert.assertEquals(0L, hour1.getRemainder(TimeUnit.SECONDS, TimeUnit.NANOSECONDS));

      Time min1 = Time.newTime(1, TimeUnit.MINUTES, env);
      Assert.assertTrue(min1.isValid());
      Assert.assertEquals(60L, min1.getTime(TimeUnit.SECONDS));
      Assert.assertEquals(0L, min1.getRemainder(TimeUnit.SECONDS, TimeUnit.NANOSECONDS));

      Time sec1 = Time.newTime(1, TimeUnit.SECONDS, env);
      Assert.assertTrue(sec1.isValid());
      Assert.assertEquals(1L, sec1.getTime(TimeUnit.SECONDS));
      Assert.assertEquals(0L, sec1.getRemainder(TimeUnit.SECONDS, TimeUnit.NANOSECONDS));

      Time ms1 = Time.newTime(1, TimeUnit.MILLISECONDS, env);
      Assert.assertTrue(ms1.isValid());
      Assert.assertEquals(0L, ms1.getTime(TimeUnit.SECONDS));
      Assert.assertEquals(1000000L, ms1.getRemainder(TimeUnit.SECONDS, TimeUnit.NANOSECONDS));

      Time us1 = Time.newTime(1, TimeUnit.MICROSECONDS, env);
      Assert.assertTrue(us1.isValid());
      Assert.assertEquals(0L, us1.getTime(TimeUnit.SECONDS));
      Assert.assertEquals(1000L, us1.getRemainder(TimeUnit.SECONDS, TimeUnit.NANOSECONDS));

      Time ns1 = Time.newTime(1, TimeUnit.NANOSECONDS, env);
      Assert.assertTrue(ns1.isValid());
      Assert.assertEquals(0L, ns1.getTime(TimeUnit.SECONDS));
      Assert.assertEquals(1L, ns1.getRemainder(TimeUnit.SECONDS, TimeUnit.NANOSECONDS));
   }

   /**
    * JMOB-1209: Test that a Sample without timestamp returns an invalid time
    * when calling getSourceTimestamp()
    * The scenario is the following :
    * <ul>
    * <li>Create a Sample without timestamp
    * <li>test that Sample.getSourceTimestamp() returns a time for which isValid() returns false.
    * </ul>
    */
   @Test
   public void NoTimestampSampleTest()
   {
      //CacheChange change = new CacheChange(null);
      //SampleData<Msg> sampleData = new SampleData<Msg>(null, change, new Msg(0, "Hi!"));
      Sample<Msg> sample = new SampleImpl<Msg>((CycloneServiceEnvironment) env, new Msg(0, "Hi!"), new SampleInfo());
      Time t = sample.getSourceTimestamp();
      Assert.assertFalse(t.isValid());
   }
}
