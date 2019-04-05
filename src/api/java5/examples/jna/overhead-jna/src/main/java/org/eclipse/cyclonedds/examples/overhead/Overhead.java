/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.examples.overhead;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Memory;

import com.sun.jna.ptr.PointerByReference;

import java.util.ArrayList;
import java.util.Comparator;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_sequence;
import org.eclipse.cyclonedds.examples.Dds;
import org.eclipse.cyclonedds.examples.RoundTripModule_DataType;
import org.eclipse.cyclonedds.helper.NativeSize;

public class Overhead
{

	Comparator<Double> comparator = new Comparator<Double>() {
		public int compare(Double a, Double b) {			
			return Double.compare(a, b);
		}		
	};

	public Overhead(){                
		/* Create a Participant. */
		Dds.participant = DdscLibrary.dds_create_participant (Dds.DDS_DOMAIN_DEFAULT, null, null);
		Dds.assertTrue(Dds.helper.dds_error_check(Dds.participant, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);

		/* A DDS Topic is created for our sample type on the domain participant. */
		Dds.topic = DdscLibrary.dds_create_topic(Dds.participant,
				Dds.helper.getRoundTripModule_DataType_desc(), "RoundTrip", null, null);
		Dds.assertTrue(Dds.helper.dds_error_check(Dds.topic, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);

		/* A DDS DataWriter is created on the Publisher & Topic with a modififed Qos. */		
		Dds.writer = DdscLibrary.dds_create_writer (Dds.participant, Dds.topic, null, null);
		Dds.assertTrue(Dds.helper.dds_error_check(Dds.writer, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);

		/* A DDS DataReader is created on the Publisher & Topic with a modififed Qos. */
		Dds.reader = DdscLibrary.dds_create_reader (Dds.participant, Dds.topic, null, null);
		Dds.assertTrue(Dds.helper.dds_error_check(Dds.reader, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);
		org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (null);

		System.out.printf ("%7s WRITE %6s %6s %6s %6s | READ %6s %6s %6s %6s\n", "size", "min", "median", "99%", "max", "min", "median", "99%", "max");


		for (int size = 0; size <= 1048576; size = newSize (size))
		{
			RoundTripModule_DataType.ByReference pub_data = setPayload(size);
			ArrayList<Double> writeTime= new ArrayList<Double>();
			ArrayList<Double> readTime= new ArrayList<Double>();

			int rounds = 1000;
			for (int k = 0; k < rounds; k++)
			{
				//write
				pub_data.write();
				long preWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
				DdscLibrary.dds_write (Dds.writer, pub_data.getPointer());
				long postWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();

				PointerByReference samplePtr = new PointerByReference(Dds.alloc);
				dds_sample_info.ByReference infosPtr = new  dds_sample_info.ByReference();	
				dds_sample_info[] infosArr = (dds_sample_info[]) infosPtr.toArray(Dds.MAX_SAMPLES); 

				/* Take sample and check that it is valid */	    
				long preReadTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
				DdscLibrary.dds_read(Dds.reader, samplePtr, infosPtr, new NativeSize(1), 1);  
				long postReadTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();

				writeTime.add((double)(postWriteTime - preWriteTime)/1000);
				readTime.add((double)(postReadTime - preReadTime)/1000);

				RoundTripModule_DataType sub_data = new RoundTripModule_DataType(samplePtr.getValue());
				infosPtr.read();
				sub_data.read();
			}

			//org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_free (pub_data.payload._buffer);

			writeTime.sort(comparator);
			readTime.sort(comparator);

			System.out.printf ("%7d %5s %6.0f %6.0f %6.0f %6.0f | %4s %6.0f %6.0f %6.0f %6.0f\n",
					size,
					"", writeTime.get(0), writeTime.get(rounds / 2), writeTime.get(rounds - rounds / 100), writeTime.get(rounds - 1),
					"", readTime.get(0), readTime.get(rounds / 2), readTime.get(rounds - rounds / 100), readTime.get(rounds - 1));
		}
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


	private RoundTripModule_DataType.ByReference setPayload(int size) {
		/* setting the payload for publication data */
		dds_sequence dsPubData = new dds_sequence();
		dsPubData.set_length(Math.toIntExact(size));		
		dsPubData.set_release(Dds.cBoolean(true)); //true
		dsPubData.set_maximum(0);                
		int memSize = Math.toIntExact(size) * Native.getNativeSize(Byte.TYPE);
		if(memSize > 0) {
			Pointer buffer = new Memory(memSize);
			for (int i = 0; i < size; i++)
			{
				buffer.setByte(i * Native.getNativeSize(Byte.TYPE), (byte)'\0');
			}
			dsPubData.set_buffer(buffer);
		}
		RoundTripModule_DataType.ByReference pub_data = new RoundTripModule_DataType.ByReference();       
		pub_data.setPayload(dsPubData);
		return pub_data;
	}

	public static void main( String[] args )
	{
		new Overhead();    
	}

}
