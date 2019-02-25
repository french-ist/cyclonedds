package org.eclipse.cyclonedds.examples;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;

import com.sun.jna.Pointer;

public class Dds {
	
	public static int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
	public static int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;	
	public static int MAX_SAMPLES = 10;    
	public static int DDS_DOMAIN_DEFAULT = 0;	
	public static long US_IN_ONE_SEC = 1000000L;
	public static long DDS_NSECS_IN_USEC = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.DDS_NSECS_IN_USEC;
	
	public static RoundTripModule_DataType_Helper helper = new RoundTripModule_DataType_Helper();

	public static int topic;
	public static int participant;
	public static Pointer alloc = org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_alloc(helper.getNativeSize("RoundTripModule_DataType"));

	
	public static int reader;
	public static int writer;
	
	
    public static void assertTrue(boolean b){
        if(!b){
            System.err.println("True expected");
            System.exit(-1);
        }
    }
	
	public static void finalizeDds(int part, Pointer... pointers)
	{
		int status = DdscLibrary.dds_delete (part);
		assertTrue(Dds.helper.dds_error_check(status, Dds.DDS_CHECK_REPORT | Dds.DDS_CHECK_EXIT) > 0);

		for (Pointer pointer : pointers) {
			org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_sample_free(
					pointer,
					Dds.helper.getRoundTripModule_DataType_desc(),
					org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_free_op_t.DDS_FREE_CONTENTS);
		}
	}

	public static long ddsSeconds(int seconds){
		return seconds * 1000000000;
	}

	public static byte cBoolean(boolean b) {
		return b ? (byte)1:(byte)0;
	}	
}
