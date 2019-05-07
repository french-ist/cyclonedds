/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core;

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Duration;
import org.omg.dds.core.Time;

import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
import org.eclipse.cyclonedds.utils.TimeHelper;
import static org.eclipse.cyclonedds.utils.TimeHelper.*;


public class TimeImpl
   extends Time
{

   private static final long serialVersionUID = -1756738290992362946L;

   /**
    * The time in nanoseconds since Epoch (1st Jan. 1970 00:00)
    */
   protected final long time;

   private static ServiceEnvironmentImpl environment;

   /**
    * Create a new ModifiableTime with specified time in nanoseconds
    * frome Epoch (1st Jan. 1970 00:00).
    * 
    * @param time the time in nanoseconds
    */
   public TimeImpl(ServiceEnvironmentImpl environment, long time)
   {
	   this.environment = environment;
      if (time < 0)
         throw new IllegalArgumentException("Cannot create negative time: " + time);
      this.time = time;
   }

   /**
    * Create a new Time from a time specified in seconds + nanoseconds.
    * 
    * @param sec the seconds part of the time
    * @param nanoseconds the nanoseconds part of the time
    */
   public TimeImpl(int seconds, long nanoSec)
   {
      if (nanoSec < 0)
         throw new IllegalArgumentException("Nagative nanoSec");
      long time = TimeUnit.NANOSECONDS.convert(seconds, TimeUnit.SECONDS) + nanoSec;
      if (time < 0)
         throw new IllegalArgumentException("Cannot create negative time: " + time);
      this.time = time;
   }

   /**
    * Create a new Time from a time in a specified TimeUnit.
    * 
    * @param time the time in specified TimeUnit
    * @param unit the TimeUnit
    */
   public TimeImpl(ServiceEnvironmentImpl environment, long time, TimeUnit unit)
   {
      this(environment, TimeUnit.NANOSECONDS.convert(time, unit));
   }

   /**
    * Create a new Time with the current time.
    * 
    * @return the current time
    */
   public static TimeImpl GetCurrentTime()
   {
      return new TimeImpl(environment, GetCurrentTimeNano());
   }

   @Override
   public int compareTo(Time o)
   {
      if ( !isValid() && !o.isValid())
         return 0;

      long oTime = o.getTime(TimeUnit.NANOSECONDS);

      if (this.time > oTime)
         return 1;
      if (this.time < oTime)
         return -1;

      return 0;
   }

   @Override
   public boolean equals(Object o)
   {
      if (o instanceof Time)
      {
         Time t = (Time) o;
         if ( !isValid() && !t.isValid())
            return true;

         return time == t.getTime(TimeUnit.NANOSECONDS);
      }
      return false;
   }

   @Override
   public int hashCode()
   {
      if (isValid())
      {
         return (int) getTime(TimeUnit.NANOSECONDS);
      }
      return 0;
   }

   @Override
   public ServiceEnvironment getEnvironment()
   {
      return environment;
   }

   @Override
   public long getTime(TimeUnit inThisUnit)
   {
      return ConvertTo(time, inThisUnit);
   }

   @Override
   public long getRemainder(TimeUnit primaryUnit, TimeUnit remainderUnit)
   {
      return ConvertTo(GetRemainder(time, primaryUnit), remainderUnit);
   }

   @Override
   public boolean isValid()
   {
      return IsValid(time);
   }

   @Override
   public ModifiableTime modifiableCopy()
   {
      return new ModifiableTimeImpl(environment, time);
   }

   @Override
   public String toString()
   {
      return TimeToString(time, true);
   }
}