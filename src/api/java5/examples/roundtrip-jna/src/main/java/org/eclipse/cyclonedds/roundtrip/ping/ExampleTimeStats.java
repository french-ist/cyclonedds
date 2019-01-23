package org.eclipse.cyclonedds.roundtrip.ping;

import java.util.Arrays;
import java.util.Comparator;

public class ExampleTimeStats
{
	int TIME_STATS_SIZE_INCREMENT = 50000;
	int valuesSize = 0;
	int valuesMax = 0;

	double average = 0;
	Long[] values;   
	long min = 0; //dds_time_t min;
	long max = 0;
	long count = 0;

	public ExampleTimeStats ()
	{        
		reset();
	}

	public void exampleResetTimeStats ()
	{
		reset();
	}

	public void reset(){
		values = new Long[TIME_STATS_SIZE_INCREMENT]; //(dds_time_t*) malloc (TIME_STATS_SIZE_INCREMENT * sizeof (dds_time_t));
		valuesSize = 0;
		valuesMax = TIME_STATS_SIZE_INCREMENT;
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
		long median = 0;
		Arrays.sort(values, new Comparator<Long>(){            
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
			valuesSize = valuesSize<2 ? 2:valuesSize;        	
			Long v1 = values[valuesSize / 2 - 1];
			Long v2 = values[valuesSize / 2];
			median =  (v1==null? 0:v1) + (v2==null? 0:v2/2);
		}
		else
		{            
			median = values[valuesSize / 2];
		}
		return (double)median;
	}

	public long exampleGet99PercentileFromTimeStats ()
	{
		Arrays.sort(values, new Comparator<Long>(){
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
			return values[valuesSize - valuesSize/100];
		} catch(Exception e){
			return 0;
		}
	}
} 