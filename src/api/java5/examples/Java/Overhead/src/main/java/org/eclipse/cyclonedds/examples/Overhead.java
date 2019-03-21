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
package org.eclipse.cyclonedds.examples;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.eclipse.cyclonedds.domain.DomainParticipant;
import org.eclipse.cyclonedds.examples.OverheadData.DataType;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;

public class Overhead
{

	private DataWriter<DataType> writer;
	private DataReader<DataType> reader;
	private long preWriteTime;
	private long postWriteTime;
	private long preReadTime;
	private long postReadTime;
	
	Comparator<Double> comparator = new Comparator<Double>() {
		public int compare(Double a, Double b) {
			if(a==null || b==null) {
				return 0;
			}
			return a.equals(b) ? 0 : a.compareTo(b);
		}		
	};

	public Overhead() {
		DomainParticipant p = prepareDds();

		System.out.printf ("%7s WRITE %6s %6s %6s %6s | READ %6s %6s %6s %6s\n", "size", "min", "median", "99%", "max", "min", "median", "99%", "max");

		for (int size = 0; size <= 1048576; size = newSize (size))
		{
			DataType data = setPayload(size);
			ArrayList<Double> writeTime= new ArrayList<Double>();
			ArrayList<Double> readTime= new ArrayList<Double>();

			int rounds = 1000;
			for (int k = 0; k < rounds; k++)
			{
				try {
					preWriteTime = System.nanoTime();
					writer.write(data);
					postWriteTime = System.nanoTime();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
				
				// Try to take samples every seconds. We stop as soon as we get some.
				List<Sample<DataType>> samples = new ArrayList<Sample<DataType>>();
				while (samples.size() == 0) {
					preReadTime = System.nanoTime();
					reader.take(samples);
					postReadTime = System.nanoTime();
					if(samples.size() != 0 ) {
						break;
					}
				}				
				writeTime.add((double)(postWriteTime - preWriteTime)/1000);
				readTime.add((double)(postReadTime - preReadTime)/1000);
			}
			
			writeTime.sort(comparator);
			readTime.sort(comparator);
			
			System.out.printf ("%7d %5s %6.0f %6.0f %6.0f %6.0f | %4s %6.0f %6.0f %6.0f %6.0f\n",
					size,
					"", writeTime.get(0), writeTime.get(rounds / 2), writeTime.get(rounds - rounds / 100), writeTime.get(rounds - 1),
					"", readTime.get(0), readTime.get(rounds / 2), readTime.get(rounds - rounds / 100), readTime.get(rounds - 1));
			
		}

		p.close();
	}
	
	public static void main(String[] args) {
		new Overhead();
	}

	private DataType setPayload(int size) {
		byte[] ret = new byte[size];
		for(int i=0;i<size;i++) {
			ret[i] = (byte)0; 
		}		
		return new DataType(ret);
	}

	private DomainParticipant prepareDds() {
		System.setProperty(
				ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
				"org.eclipse.cyclonedds.core.CycloneServiceEnvironment");

		ServiceEnvironment env = ServiceEnvironment
				.createInstance(Overhead.class.getClassLoader());

		DomainParticipantFactory dpf = DomainParticipantFactory
				.getInstance(env);

		DomainParticipant p = (org.eclipse.cyclonedds.domain.DomainParticipant) dpf.createParticipant();
		Topic<DataType> topic = p.createTopic("Overhead_Msg", DataType.class);
		Publisher pub = p.createPublisher();
		writer = pub.createDataWriter(topic);
		Subscriber sub = p.createSubscriber();
		reader = sub.createDataReader(topic);
		return p;
	}

	public int newSize(int size) {
		if (size < 1024) {
			return size+64; 
		} else if (size > 1024 && size < 4096){
			return size+256;
		} else if (size > 4096 && size < 65536){
			return size + 8192 ;
		} else if (size > 65536 && size < 262144){
			return size + 4096 ;
		} else {
			return size = (size == 0) ? 1 : 2 * size;
		}
	}

}
