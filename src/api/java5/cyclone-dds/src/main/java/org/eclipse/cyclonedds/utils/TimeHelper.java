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

package org.eclipse.cyclonedds.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.Time;

import DDS.Time_t;

/**
 * Helper class for all Time/Duration related classes.<br/>
 * Note: we choose to internally represent the time as a Java long
 * with nanosecond unit and 1st Jan. 1970 00:00 as Epoch (i.e. Unix & Java Epoch).
 */

public class TimeHelper
{

   // number of nanosecond per second (1e6)
   private static final long NANO_PER_MILLISEC = 1000000L;
   // number of nanosecond per second (1e9)
   private static final long NANO_PER_SEC = 1000000000L;
   // number of NTP fraction per second (2^32)
   private static final long FRACTION_PER_SEC = 0x100000000L;

   /**
    * TIME_ZERO as per DDSI spec {0, 0}
    */
   public static final long TIME_ZERO = 0L;

   /**
    * TIME_INVALID as per DDSI spec {-1, 0xffffffff}
    */
   public static final long TIME_INVALID = -1L;

   /**
    * TIME_INFINITE as per DDSI spec {0x7fffffff, 0xffffffff}
    */
   public static final long TIME_INFINITE = Long.MAX_VALUE;


   public static boolean IsValid(long time)
   {
      return time != TIME_INVALID;
   }

   public static boolean IsInfinite(long time)
   {
      return time == TIME_INFINITE;
   }

   /**
    * Returns the current time in nanoseconds,
    * using 1st Jan. 1970 00:00 as Epoch.<br/>
    * Note that this operation calls {@link System#currentTimeMillis()} and
    * therefore gives time with a millisecond precision.
    * 
    * @return the current time in nanoseconds
    */
   public static long GetCurrentTimeNano()
   {
      return System.currentTimeMillis() * NANO_PER_MILLISEC;
   }

   /**
    * Convert the nanoseconds time to seconds.
    * 
    * @param time the time in nanoseconds
    * @return the time in seconds
    */
   public static float ConvertToSecondsAsFloat(long time)
   {
      return ((float) time) / NANO_PER_SEC;
   }

   /**
    * Convert the nanoseconds time to seconds (truncating).
    * 
    * @param time the time in nanoseconds
    * @return the time in seconds
    */
   public static long ConvertToSeconds(long time)
   {
      if (time == TIME_INVALID)
         return TIME_INVALID;
      if (time == TIME_INFINITE)
         return TIME_INFINITE;
      return time / NANO_PER_SEC;
   }

   /**
    * Convert the nanoseconds time to milliseconds.
    * 
    * @param time the time in nanoseconds
    * @return the time in milliseconds
    */
   public static long ConvertToMilliseconds(long time)
   {
      if (time == TIME_INVALID)
         return TIME_INVALID;
      if (time == TIME_INFINITE)
         return TIME_INFINITE;
      return time / NANO_PER_MILLISEC;
   }

   /**
    * Convert the nanoseconds time to another TimeUnit.
    * Note: if time is TIME_INVALID or TIME_INFINITE the result
    * is unchanged whatever the requested TimeUnit is.
    * 
    * @param time the time in nanoseconds
    * @param inThisUnit the TimeUnit to convert to
    * @return the converted time
    */
   public static long ConvertTo(long time, TimeUnit inThisUnit)
   {
      if (inThisUnit == TimeUnit.NANOSECONDS)
         return time;
      if (time == TIME_INVALID)
         return TIME_INVALID;
      if (time == TIME_INFINITE)
         return TIME_INFINITE;
      return inThisUnit.convert(time, TimeUnit.NANOSECONDS);
   }

   /**
    * Get the remainder after truncation of seconds.
    * 
    * @param time the time in nanoseconds
    * @return the remaining nanoseconds after truncation of seconds
    */
   public static long GetRemainderAfterSeconds(long time)
   {
      if (time == TIME_INVALID)
         return TIME_INVALID;
      if (time == TIME_INFINITE)
         return TIME_INFINITE;
      long seconds = time / NANO_PER_SEC;
      return time - seconds * NANO_PER_SEC;
   }

   /**
    * Get the remainder (in nanoseconds) after truncation of TimeUnit part.
    * 
    * @param time the time in nanoseconds
    * @param primaryUnit the unit causing truncation
    * @return the remainder in nanoseconds after truncation
    */
   public static long GetRemainder(long time, TimeUnit primaryUnit)
   {
      if (time == TIME_INVALID)
         return TIME_INVALID;
      if (time == TIME_INFINITE)
         return TIME_INFINITE;
      long primaryConversion = ConvertTo(time, primaryUnit);
      long backToNano = TimeUnit.NANOSECONDS.convert(primaryConversion, primaryUnit);
      return time - backToNano;
   }

   /**
    * Get the remainder (in specified remainderUnit) after truncation of TimeUnit part.
    * 
    * @param time the time in nanoseconds
    * @param primaryUnit the unit causing truncation
    * @param remainderUnit the unit of remainder
    * @return the remainder after truncation
    */
   public static long GetRemainder(long time, TimeUnit primaryUnit, TimeUnit remainderUnit)
   {
      return ConvertTo(GetRemainder(time, primaryUnit), remainderUnit);
   }

   /**
    * Convert a time in a NTP representation to nanoseconds.
    * 
    * @param seconds the NTP seconds part
    * @param fraction the NTP fraction part
    * @return the NTP time converted to nanoseconds
    */
   public static long NTPToNanoseconds(int seconds, long fraction)
   {
      if (seconds == 0x7fffffff && fraction == 0xffffffffL)
         return TIME_INFINITE;
      return ((long) seconds) * NANO_PER_SEC + NTPFractionToNanoseconds(fraction);
   }

   /**
    * Convert a the fraction part of a NTP representation to nanoseconds.
    * 
    * @param fraction the NTP fraction part
    * @return the fraction converted to nanoseconds
    */
   private static long NTPFractionToNanoseconds(long fraction)
   {
      return fraction * NANO_PER_SEC / FRACTION_PER_SEC;
   }

   /**
    * Convert a time in nanoseconds to the seconds part of a NTP representation.
    * 
    * @param time the time in nanoseconds
    * @return the NTP seconds part
    */
   public static int TimeToNTPSeconds(long time)
   {
      if (time == TIME_INVALID)
         return -1;
      if (time == TIME_INFINITE)
         return 0x7fffffff;
      return (int) ConvertToSeconds(time);
   }

   /**
    * Convert a time in nanoseconds to the fraction part of a NTP representation.
    * 
    * @param time the time in nanoseconds
    * @return the NTP fraction part
    */
   public static long TimeToNTPFraction(long time)
   {
      if (time == TIME_INVALID)
         return 0xffffffff;
      if (time == TIME_INFINITE)
         return 0xffffffff;
      return NanosecondsToNTPFraction(GetRemainderAfterSeconds(time));
   }

   /**
    * Convert nanoseconds to the fraction part of a NTP representation.
    * NOTE: nanoseconds must be less than 1e9 (i.e. 1 second)
    * 
    * @param nanoseconds a number of nanoseconds
    * @return the equivalent NTP fraction part
    */
   private static long NanosecondsToNTPFraction(long nanoseconds)
   {
      double result = ((double) nanoseconds) * ((double) FRACTION_PER_SEC)
            / ((double) NANO_PER_SEC);
      if (result - ((long) result) == 0)
      {
         return (long) result;
      }
      // Add 1 to the result to avoid the rounding effect when
      // transforming from NTP representation to nanoseconds
      return (long) result + 1;
   }

   public static long Add(long time1, long time2)
   {
      // make time1 always bigger than time2
      if (time2 > time1)
      {
         return Add(time2, time1);
      }

      if (time1 == TIME_INFINITE || time2 == TIME_INFINITE)
      {
         return TIME_INFINITE;
      }

      // check for overflow
      if (time1 > 0 && time2 > 0 && time2 > TIME_INFINITE - time1)
      {
         return TIME_INFINITE;
      }

      // check for underflow
      if (time1 < 0 && time2 < 0 && -TIME_INFINITE - time1 > time2)
      {
         return -TIME_INFINITE;
      }

      // addition is safe
      return time1 + time2;
   }

   public static long Substract(long time1, long time2)
   {
      return Add(time1, -time2);
   }

   public static long Multiply(long time, long c)
   {
      if (Math.abs(time) > TIME_INFINITE / c)
      {
         return time > 0 ? TIME_INFINITE : -TIME_INFINITE;
      }
      else
      {
         return time * c;
      }
   }

   public static long Divide(long time, long c)
   {
      if (c == 0)
      {
         throw new IllegalArgumentException("Cannot divide by 0");
      }
      else
      {
         return time / c;
      }
   }

   public static float DivideAsFloat(long time, long c)
   {
      if (c == 0)
      {
         throw new IllegalArgumentException("Cannot divide by 0");
      }
      else
      {
         return ((float) time) / c;
      }
   }

   /**
    * Return a String containing the time as a float in seconds.
    * 
    * @param time the time
    * @param considerInvalid if true a TIME_INVALID is displayed as INVALID (-1 otherwise)
    * @return the a String containing the time as a floas in seconds
    */
   public static String TimeToString(long time, boolean considerInvalid)
   {
      if (considerInvalid && time == TIME_INVALID)
         return "INVALID";
      if (time == TIME_INFINITE)
         return "INFINITE";

      long secs = time / NANO_PER_SEC;
      long nanosecs = time - secs * NANO_PER_SEC;
      StringBuffer result = new StringBuffer();
      result.append(secs);
      if (nanosecs != 0)
      {
         result.append('.');
         String nanosecsPart = Long.toString(nanosecs);
         // fill with '0' before first significative number
         for (int i = 0; i < 9 - nanosecsPart.length(); i++)
         {
            result.append('0');
         }
         // remove '0' at the end
         result.append(nanosecsPart.replaceFirst("[0]*$", ""));

      }
      return result.toString();
   }

   private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";

   public static String TimeToDate(long time)
   {
      // Note: as DateFormat is not thread-safe, Coverity advises to not use it
      // as a static variable. We have to create one locally...
      DateFormat format = new SimpleDateFormat(DATE_FORMAT);
      return format.format(new Date(
            TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS)));
   }

   /**
    * This function converts the given Time_t to ModifiableTime
    * 
    * @param sourceTimestamp
    * @return ModifiableTime
    */
   public static ModifiableTime Time_tToModifiableTime(Time_t sourceTimestamp)
   {
      return Time.newTime(
            ((long) sourceTimestamp.sec) * 1000000000L + ((long) sourceTimestamp.nanosec),
            TimeUnit.NANOSECONDS,
            null ); //TODO was DDSIParticipantFactory.getServiceEnvironment()
   }
}
