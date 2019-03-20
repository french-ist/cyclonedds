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
 
class ExampleUtilities
{
    public static final long NS_IN_ONE_US = 1000;
    public static final long US_IN_ONE_SEC = 1000000;

    /**
     * Returns the current time in microseconds
     * @return the current time in microseconds
     */
    public static long GetTime()
    {
        return System.nanoTime() / NS_IN_ONE_US;
    }

    /**
     * Class to keep a running average time as well as recording the minimum
     * and maximum times
     */
    public static class TimeStats
    {
        public java.util.ArrayList<Long> values = new java.util.ArrayList<Long>();
        public double average;
        public long min;
        public long max;
        public long count;

        /**
         * Resets stats variables to zero
         */
        public void Reset()
        {
            values.clear();
            average = 0;
            min = 0;
            max = 0;
            count = 0;
        }

        /**
         * Updates stats with new time data, keeps a running average
         * as well as recording the minimum and maximum times
         * @param microseconds A time in microseconds to add to the stats
         * @return The updated stats
         */
        public TimeStats AddMicroseconds(long microseconds)
        {
            values.add(microseconds);
            average = (count * average + microseconds) / (count + 1);
            min = count == 0 || microseconds < min ? microseconds : min;
            max = count == 0 || microseconds > max ? microseconds : max;
            count++;
            return this;
        }

        /**
         * Calculates the median time from the stats
         * @return the median time
         */
        public double GetMedian()
        {
            if (values.size() == 0) {
                return 0.0;
            }
            
            double median;

            java.util.Collections.sort(values);

            if(values.size() % 2 == 0)
            {
                median = (double)(values.get(values.size() / 2 - 1) + values.get(values.size() / 2)) / 2;
            }
            else
            {
                median = (double)values.get(values.size() / 2);
            }

            return median;
        }
    }
}
