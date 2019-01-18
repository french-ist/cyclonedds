package org.eclipse.cyclonedds.roundtrip.ping;

import java.util.Arrays;
import java.util.Comparator;

public class ExampleTimeStats
{
    int TIME_STATS_SIZE_INCREMENT = 50000;

    Long[] values;
    int valuesSize = 0;
    int valuesMax = 0;
    double average = 0;
    long min = 0; //dds_time_t min;
    long max = 0;
    long count = 0;

    public ExampleTimeStats ()
    {        
        values = new Long[TIME_STATS_SIZE_INCREMENT]; //(dds_time_t*) malloc (TIME_STATS_SIZE_INCREMENT * sizeof (dds_time_t));
        valuesSize = 0;
        valuesMax = TIME_STATS_SIZE_INCREMENT;
        average = 0;
        min = 0;
        max = 0;
        count = 0;
    }

    public void exampleResetTimeStats ()
    {
        valuesSize = 0;
        average = 0;
        min = 0;
        max = 0;
        count = 0;
    }

    public void exampleDeleteTimeStats ()
    {
        values = null;
    }

    public void exampleAddTimingToTimeStats(long timing)
    {
        if (valuesSize > valuesMax)
        {
          values = new Long[valuesMax + TIME_STATS_SIZE_INCREMENT];//dds_time_t * temp = (dds_time_t*) realloc (values, (valuesMax + TIME_STATS_SIZE_INCREMENT) * sizeof (dds_time_t));
          valuesMax += TIME_STATS_SIZE_INCREMENT;
        }
        if (values != null && valuesSize < valuesMax)
        {
          values[valuesSize++] = timing;
        }
        average = ((double)count * average + (double)timing) / (double)(count + 1);
        min = (count == 0 || timing < min) ? timing : min;
        max = (count == 0 || timing > max) ? timing : max;
        count++;
    }


    public Double exampleGetMedianFromTimeStats ()
    {
        //see ping.c#86
        Double median = 0.0;
        Arrays.sort(values, new Comparator<Long>(){            
            @Override
            public int compare(Long ul_a, Long ul_b) {
                if(ul_a == null || ul_b == null){
                    return 0;
                }
                if (ul_a < ul_b) return -1;
                if (ul_a > ul_b) return 1;
                return 0;
            }
        });

        if (valuesSize % 2 == 0)
        {
            median = (double)(values[(int)valuesSize / 2 - 1] + values[(int)valuesSize / 2]) / 2;
        }
        else
        {
            median = (double)values[(int)valuesSize / 2];
        }
        return median;
    }

    public long exampleGet99PercentileFromTimeStats ()
    {
        Arrays.sort(values, new Comparator<Long>(){
            @Override
            public int compare(Long ul_a, Long ul_b) {
                if(ul_a == null || ul_b == null){
                    return 0;
                }
                if (ul_a < ul_b) return -1;
                if (ul_a > ul_b) return 1;
                return 0;
            }
        });
        try {
            return values[(int)valuesSize - (int)valuesSize/100];
        } catch(Exception e){
            return 0;
        }

    }
} 