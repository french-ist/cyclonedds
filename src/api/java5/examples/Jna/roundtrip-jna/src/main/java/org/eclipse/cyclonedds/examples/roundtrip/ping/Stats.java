package org.eclipse.cyclonedds.examples.roundtrip.ping;

import java.util.ArrayList;
import java.util.Comparator;

import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.examples.roundtrip.Dds;


public class Stats implements Runnable
{
	public static long elapsed = 0;
	public static long startTime = 0;
	public static long postWriteTime;
	public static long preWriteTime;

	private ArrayList<Long> values;   
	private long min = 0;
	private long max = 0;
	private long count = 0;
	
	public void run() {}

	public Stats ()
	{        
		exampleResetTimeStats();
	}
	
	public static void statsStuf(dds_sample_info[] infosArr, long preTakeTime, long postTakeTime) {
		long difference = 0;
		
		/* Update stats */
		difference = (Stats.postWriteTime - Stats.preWriteTime)/Dds.DDS_NSECS_IN_USEC;
		Dds.writeAccess.exampleAddTimingToTimeStats (difference);
		Dds.writeAccessOverall.exampleAddTimingToTimeStats (difference);

		difference = (postTakeTime - preTakeTime)/Dds.DDS_NSECS_IN_USEC;
		Dds.readAccess.exampleAddTimingToTimeStats (difference);
		Dds.readAccessOverall.exampleAddTimingToTimeStats (difference);

		difference = (postTakeTime - infosArr[0].source_timestamp)/Dds.DDS_NSECS_IN_USEC;
		Dds.roundTrip.exampleAddTimingToTimeStats (difference);
		Dds.roundTripOverall.exampleAddTimingToTimeStats (difference);

		if (!Dds.shouldWarmUp) {
			/* Print stats each second */
			difference = (postTakeTime - Stats.startTime)/Dds.DDS_NSECS_IN_USEC;
			if (difference > Dds.US_IN_ONE_SEC)
			{
				System.out.printf("%9s %9d %8.0f %8s %9s %8s %10d %8.0f %8s %10d %8.0f %8s\n",
						(Stats.elapsed + 1),
						Dds.roundTrip.count(), 
						Dds.roundTrip.exampleGetMedianFromTimeStats() / 2,
						Dds.roundTrip.min() / 2,
						Dds.roundTrip.exampleGet99PercentileFromTimeStats () / 2,
						Dds.roundTrip.max() / 2,
						Dds.writeAccess.count(),
						Dds.writeAccess.exampleGetMedianFromTimeStats (),
						Dds.writeAccess.min(),
						Dds.readAccess.count(),
						Dds.readAccess.exampleGetMedianFromTimeStats (),
						Dds.readAccess.min());

				Dds.roundTrip.exampleResetTimeStats();
				Dds.writeAccess.exampleResetTimeStats();
				Dds.readAccess.exampleResetTimeStats();
				Stats.startTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
				Stats.elapsed ++;
			}
		}
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
		
		values.sort(new Comparator<Long>() {
			public int compare(Long a, Long b) {
				if(a==null || b==null) {
					return 0;
				}
				return (a == b) ? 0 : (a < b) ? -1 : 1;
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
		values.sort(new Comparator<Long>() {
			public int compare(Long a, Long b) {
				if(a==null || b==null) {
					return 0;
				}
				return (a == b) ? 0 : (a < b) ? -1 : 1;
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