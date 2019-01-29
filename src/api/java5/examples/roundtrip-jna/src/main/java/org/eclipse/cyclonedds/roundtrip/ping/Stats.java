package org.eclipse.cyclonedds.roundtrip.ping;

import java.util.ArrayList;
import java.util.Comparator;


public class Stats
{
	public static long elapsed = 0;
	public static long startTime = 0;
	public static long postWriteTime;
	public static long preWriteTime;

	private ArrayList<Long> values;   
	private long min = 0;
	private long max = 0;
	private long count = 0;

	public Stats ()
	{        
		exampleResetTimeStats();
	}

	public void exampleResetTimeStats ()
	{
		values = new ArrayList<Long>();
		min = Long.MAX_VALUE;
		max = Long.MIN_VALUE;
		count = 0;
	}


	public void exampleAddTimingToTimeStats(long timing)
	{
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
			int i1 = bound(values.size()/2 -1, values.size());
			int i2 = bound(values.size()/2, values.size());
			median = ( values.get(i1) + values.get(i2) ) / 2; //(v1==null? 0:v1) + (v2==null? 0:v2/2);
		}
		else
		{            
			int i = bound(values.size()/2, values.size());
			median = values.get(i);
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

		return values.get(bound(values.size() - values.size()/100, values.size()));
	}

	public int bound(int value, int arraySize) {
		return Math.min(0, Math.max(value, arraySize-1));
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