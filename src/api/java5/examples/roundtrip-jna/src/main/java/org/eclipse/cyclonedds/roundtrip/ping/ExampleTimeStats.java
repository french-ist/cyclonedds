package org.eclipse.cyclonedds.roundtrip.ping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class ExampleTimeStats
{
	//int TIME_STATS_SIZE_INCREMENT = 50000;
	//int valuesSize = 0;
	//int valuesMax = 0;

	private ArrayList<Long> values;   
	private double average = 0;
	private long min = 0;
	private long max = 0;
	private long count = 0;

	public ExampleTimeStats ()
	{        
		exampleResetTimeStats();
	}

	public void exampleResetTimeStats ()
	{
		values = new ArrayList<Long>();
		average = 0;
		min = Long.MAX_VALUE;
		max = Long.MIN_VALUE;
		count = 0;
	}


	public void exampleAddTimingToTimeStats(long timing)
	{
		/*if (valuesSize > valuesMax)
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
		*/
		if(timing < min) {
			min = timing ;
		}
		if(timing > max) {
			max = timing;
		}
		values.add(timing);
		count++;
	}


	public Double exampleGetMedianFromTimeStats ()
	{
		//see ping.c#86
		double median = 0.0;
		
		values.sort(new Comparator<Long>(){            
			public int compare(Long ul_a, Long ul_b) {
				if(ul_a == null || ul_b == null){
					return 0;
				}
				if (ul_a < ul_b) return -1;
				if (ul_a > ul_b) return 1;
				return 0;
			}
		});        

		if (values.size() % 2 == 0)
		{	
			median = ( values.get(values.size()/2 -1) + values.get(values.size()/2) ) / 2; //(v1==null? 0:v1) + (v2==null? 0:v2/2);
		}
		else
		{            
			median = values.get(values.size()/2);
		}
		return (double)median;
	}

	public long exampleGet99PercentileFromTimeStats ()
	{
		values.sort(new Comparator<Long>(){            
			public int compare(Long ul_a, Long ul_b) {
				if(ul_a == null || ul_b == null){
					return 0;
				}
				if (ul_a < ul_b) return -1;
				if (ul_a > ul_b) return 1;
				return 0;
			}
		});

		return values.get(values.size() - values.size()/100);
	}

	public long min() {
		return min;
	}

	public long count() {
		return count;
	}

	public long max() {
		return max;
	}
} 