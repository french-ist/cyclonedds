package org.eclipse.cyclonedds.roundtrip.ping;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Memory;
import com.sun.jna.StringArray;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_sequence;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.eclipse.cyclonedds.roundtrip.RoundTripModule_DataType;
import org.eclipse.cyclonedds.roundtrip.RoundTripModule_DataType_Helper;

public class RoundtripPing
{
    RoundTripModule_DataType_Helper helper = new RoundTripModule_DataType_Helper();

    int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
    int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;
    int MAX_SAMPLES = 100;    
    int DDS_DOMAIN_DEFAULT = 0;
    long DDS_NSECS_IN_USEC = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.DDS_NSECS_IN_USEC;
    long US_IN_ONE_SEC = 1000000L;

    //TODO move this near use case    

    int participant;
    
    long startTime;
    long preWriteTime;
    long postWriteTime;
    long preTakeTime;
    long postTakeTime;
    long elapsed = 0;
    Boolean warmUp = true;

    public void dataAvailable(int reader)
    {
        long difference = 0;

        /* Take sample and check that it is valid */
        preTakeTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time ();
        dds_sample_info.ByReference infosPtr = new  dds_sample_info.ByReference();
        dds_sample_info[] infosArr = (dds_sample_info[]) infosPtr.toArray(MAX_SAMPLES);
        int status = DdscLibrary.dds_take (reader, samplePtr, infosPtr, new NativeSize(MAX_SAMPLES), MAX_SAMPLES);
        assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        postTakeTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time ();

        /* Update stats */
        difference = (postWriteTime - preWriteTime)/DDS_NSECS_IN_USEC;
        writeAccess.exampleAddTimingToTimeStats (difference);
        writeAccessOverall.exampleAddTimingToTimeStats (difference);

        difference = (postTakeTime - preTakeTime)/DDS_NSECS_IN_USEC;
        readAccess.exampleAddTimingToTimeStats (difference);
        readAccessOverall.exampleAddTimingToTimeStats (difference);

        /* read infosArr[0] */
        RoundTripModule_DataType arrayMsgRef = new RoundTripModule_DataType(samplePtr.getValue());
        sub_data = (RoundTripModule_DataType[]) arrayMsgRef.toArray(MAX_SAMPLES);
        arrayMsgRef.read();
        infosPtr.read();
        difference = (postTakeTime - infosArr[0].source_timestamp)/DDS_NSECS_IN_USEC;
        roundTrip.exampleAddTimingToTimeStats (difference);
        roundTripOverall.exampleAddTimingToTimeStats (difference);


        if (!warmUp) {
            /* Print stats each second */
            difference = (postTakeTime - startTime)/DDS_NSECS_IN_USEC;
            if (difference > US_IN_ONE_SEC)
            {
                System.out.print((elapsed + 1)
                        + "\t\t" + roundTrip.count
                        + "\t" + roundTrip.exampleGetMedianFromTimeStats() / 2
                        + "\t" + roundTrip.min / 2
                        + "\t" + roundTrip.exampleGet99PercentileFromTimeStats () / 2
                        + "\t" + roundTrip.max / 2
                        + "\t" + writeAccess.count
                        + "\t" + writeAccess.exampleGetMedianFromTimeStats ()
                        + "\t" + writeAccess.min
                        + "\t" + readAccess.count
                        + "\t" + readAccess.exampleGetMedianFromTimeStats ()
                        + "\t" + readAccess.min + "\n");

                roundTrip.exampleResetTimeStats();
                writeAccess.exampleResetTimeStats();
                readAccess.exampleResetTimeStats();
                startTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time ();
                elapsed++;
            }
        }

        preWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
        status = DdscLibrary.dds_write_ts (writer, pub_data.getPointer(), preWriteTime);
        assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        postWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time();
    }

    int waitSet;
    int readCond;
    int reader;
    int writer;
    public void prepareDds()
    {
        /* A DDS Topic is created for our sample type on the domain participant. */
        int topic = DdscLibrary.dds_create_topic(participant,
            helper.getRoundTripModule_DataType_desc(), "RoundTrip", null, null);
        assert(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* A DDS Publisher is created on the domain participant. */
        PointerByReference pubQos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos();
        String[] pubPartitions = { "ping" };
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_partition (pubQos, 1, new StringArray(pubPartitions));
        int publisher = DdscLibrary.dds_create_publisher (participant, pubQos, null);
        assert(helper.dds_error_check(publisher, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos(pubQos);

        /* A DDS DataWriter is created on the Publisher & Topic with a modififed Qos. */
        PointerByReference dwQos =  org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability (dwQos, org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE, 10 * 1000000000); // DDS_SECS(10) 
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_writer_data_lifecycle (dwQos, (byte)0);
        writer = DdscLibrary.dds_create_writer (publisher, topic, dwQos, null);
        assert(helper.dds_error_check(writer, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (dwQos);

        /* A DDS Subscriber is created on the domain participant. */
        PointerByReference subQos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
        String[] subPartitions = {"pong"};
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_partition (subQos, 1, new StringArray(subPartitions));
        int subscriber = DdscLibrary.dds_create_subscriber (participant, subQos, null);
        assert(helper.dds_error_check(subscriber, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (subQos);

        /* A DDS DataReader is created on the Subscriber & Topic with a modified QoS. */
        PointerByReference drQos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos ();
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability (drQos, org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE, 10 * 1000000000); // DDS_SECS(10)
        reader = DdscLibrary.dds_create_reader (subscriber, topic, drQos, null);
        assert(helper.dds_error_check(reader, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_delete_qos (drQos);

        waitSet = DdscLibrary.dds_create_waitset (participant);
        readCond = DdscLibrary.dds_create_readcondition (reader, DdscLibrary.DDS_ANY_STATE);
        int status = DdscLibrary.dds_waitset_attach (waitSet, readCond, reader);
        assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        status = DdscLibrary.dds_waitset_attach (waitSet, waitSet, waitSet);
        assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
    }

    public void finalizeDds(int part, RoundTripModule_DataType[] data)
    {
        int status = DdscLibrary.dds_delete (part);
        assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Clean up */
        roundTrip.exampleDeleteTimeStats ();
        writeAccess.exampleDeleteTimeStats ();
        readAccess.exampleDeleteTimeStats ();
        roundTripOverall.exampleDeleteTimeStats ();
        writeAccessOverall.exampleDeleteTimeStats ();
        readAccessOverall.exampleDeleteTimeStats ();

        /*
        TODO check the rigth parameter
        RoundTripModule_DataType.ByReference[] arrSample = (RoundTripModule_DataType.ByReference[]) data;
        for (int i = 0; i < MAX_SAMPLES; i++)
        {
            org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_sample_free(
                arrSample[i].getPointer(),
                helper.getRoundTripModule_DataType_desc(),
                org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_free_op_t.DDS_FREE_CONTENTS);
        }
        */

        org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_sample_free(
                pub_data.getPointer(),
                helper.getRoundTripModule_DataType_desc(),
                org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_free_op_t.DDS_FREE_CONTENTS);
    }

    ExampleTimeStats roundTrip;
    ExampleTimeStats writeAccess;
    ExampleTimeStats readAccess;
    ExampleTimeStats roundTripOverall;
    ExampleTimeStats writeAccessOverall;
    ExampleTimeStats readAccessOverall;

    PointerByReference samplePtr = new PointerByReference(org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_alloc(helper.getNativeSize("RoundTripModule_DataType")));
    RoundTripModule_DataType[] sub_data;
    RoundTripModule_DataType.ByReference pub_data;

    public RoundtripPing(){                
        this("0", "0", "0", true);        
    }

    public RoundtripPing(String _payloadSize, String _numSamples, String _timeOut){
        this("0", "0", "0", false);
    }

    boolean quit = false;
    private RoundtripPing(String _payloadSize, String _numSamples, String _timeOut, boolean quit){
        System.out.print("Usage (parameters must be supplied in order):\n"
            + "./ping [payloadSize (bytes, 0 - 100M)] [numSamples (0 = infinite)] [timeOut (seconds, 0 = infinite)]\n"
            + "./ping quit - ping sends a quit signal to pong.\n"
            + "Defaults:\n"
            + "./ping 0 0 0\n");
        ctrlHandler();

        Long time;
        Long difference = 0L;
        int wsresultsize = 1; //dds_attach_t wsresults[1];
        int status;

        roundTrip = new  ExampleTimeStats(); 
        writeAccess = new  ExampleTimeStats(); 
        readAccess = new  ExampleTimeStats(); 
        roundTripOverall = new  ExampleTimeStats(); 
        writeAccessOverall = new  ExampleTimeStats(); 
        readAccessOverall = new  ExampleTimeStats(); 

        /* Create a Participant. */
        participant = DdscLibrary.dds_create_participant (DDS_DOMAIN_DEFAULT, null, null);
        assert(helper.dds_error_check(participant, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Prepare dds */
        prepareDds();

        /* test if quit */
        if(quit){
            System.out.println("Sending termination request.\n");
            /* pong uses a waitset which is triggered by instance disposal, and
              quits when it fires. */
            org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_sleepfor (1 * 1000000000); // DDS_SECS(10) 
            
            pub_data = new RoundTripModule_DataType.ByReference();
            dds_sequence dsPubData = new dds_sequence.ByReference();
            dsPubData.set_length(0);
            dsPubData._buffer = null;
            dsPubData._release = (byte)1;
            dsPubData.set_maximum(0);
            pub_data.setPayload(dsPubData);
            status = DdscLibrary.dds_writedispose (writer, pub_data.getPointer());
            assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
            org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_sleepfor (1 * 1000000000); // DDS_SECS(10) 
            finalizeDds(participant, null); //TODO set the right data
            return;
        }

        Long payloadSize = Long.parseLong(_payloadSize);
        if (payloadSize > 100 * 1048576)
        {
            System.err.println("Invalid payloadSize");
            System.exit(-1);
        }
        Long numSamples = Long.parseLong(_numSamples);
        Long timeOut = Long.parseLong(_timeOut);

        System.out.print("# payloadSize: %" +payloadSize+ " | numSamples: %" +numSamples+ " | timeOut: %" +timeOut+ "\n\n");

        pub_data = new RoundTripModule_DataType.ByReference();
        dds_sequence dsPubData = new dds_sequence.ByReference();
        dsPubData.set_length(Math.toIntExact(payloadSize));
        dsPubData.set_buffer(payloadSize != 0 ? org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_alloc(new NativeSize(payloadSize)) : null);
        dsPubData.set_release((byte)1); //true
        dsPubData.set_maximum(0);

        /* setting the payload for publication data */
        pub_data.setPayload(dsPubData);
        int memSize = (Math.toIntExact(payloadSize)==0? 1:Math.toIntExact(payloadSize)) * Native.getNativeSize(Byte.TYPE);
        Pointer buffer = new Memory(memSize);
        for (int i = 0; i < payloadSize; i++)
        {
            buffer.setByte(i * Native.getNativeSize(Byte.TYPE), (byte)'a');
        }
        pub_data.payload.set_buffer(buffer);

        startTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time ();
        System.out.print("# Waiting for startup jitter to stabilise\n");

        /* Write a sample that pong can send back */
        PointerByReference wsresults = new PointerByReference(); //dds_attach_t wsresults[1];
        Long waitTimeout = (long)(1*1000000000); //DDS_SECS (1);
        while (DdscLibrary.dds_triggered (waitSet) != 1 && difference < 5 * 1000000000) // DDS_SECS(5)
        {
            status = DdscLibrary.dds_waitset_wait (waitSet, wsresults, new NativeSize(wsresultsize), waitTimeout);
            assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
            if (status > 0)
            {
                /* Infos, TODO check if can be outside of if */
                dds_sample_info.ByReference infosPtr = new  dds_sample_info.ByReference();
                dds_sample_info[] infosArr = (dds_sample_info[]) infosPtr.toArray(MAX_SAMPLES);
                status = DdscLibrary.dds_take (reader, samplePtr, infosPtr, new NativeSize(MAX_SAMPLES), MAX_SAMPLES);
                assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

                /* Setup strictures to receive samples */
                RoundTripModule_DataType arrayMsgRef = new RoundTripModule_DataType(samplePtr.getValue());
                sub_data = (RoundTripModule_DataType[]) arrayMsgRef.toArray(MAX_SAMPLES);
                arrayMsgRef.read();
                infosPtr.read();
            }

            time = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time ();
            difference = time - startTime;
        }

        if (DdscLibrary.dds_triggered (waitSet) != 1)
        {
            warmUp = false;
            System.out.print("# Warm up complete.\n\n");
            System.out.print("# Latency measurements (in us)\n");
            System.out.print("#             Latency [us]                                   Write-access time [us]       Read-access time [us]\n");
            System.out.print("# Seconds     Count   median      min      99%%      max      Count   median      min      Count   median      min\n");
        }

        roundTrip.exampleResetTimeStats ();
        writeAccess.exampleResetTimeStats ();
        readAccess.exampleResetTimeStats ();
        startTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time ();

        /* Write a sample that pong can send back */
        preWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time ();
        status = DdscLibrary.dds_write_ts (writer, pub_data.getPointer(), preWriteTime);
        assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
        postWriteTime = org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_time ();
        for (int i = 0; 0 == DdscLibrary.dds_triggered (waitSet) && (numSamples != null|| i < numSamples); i++)
        {
            status = DdscLibrary.dds_waitset_wait (waitSet, wsresults, new NativeSize(wsresultsize), waitTimeout);
            assert(helper.dds_error_check(status, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
            if (status != 0) {
                dataAvailable(reader);
            }
        }

        if (!warmUp)
        {
            //Confirm with ping.c#381-395
            System.out.print
            (
                "\n# Overall"
                + "\t\t" + roundTripOverall.count
                + "\t" + roundTripOverall.exampleGetMedianFromTimeStats () / 2
                + "\t" + roundTripOverall.min / 2
                + "\t" + roundTripOverall.exampleGet99PercentileFromTimeStats () / 2
                + "\t" + roundTripOverall.max / 2
                + "\t" + writeAccessOverall.count
                + "\t" + writeAccessOverall.exampleGetMedianFromTimeStats ()
                + "\t" + writeAccessOverall.min
                + "\t" + readAccessOverall.count
                + "\t" + readAccessOverall.exampleGetMedianFromTimeStats ()
                + "\t" + readAccessOverall.min + "\n"
            );
        }

        finalizeDds(participant, sub_data);

    }

	private void ctrlHandler() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
               System.out.println("Interrupt received");
               DdscLibrary.dds_waitset_set_trigger (waitSet, (byte)1);           
            }
         });
    }


}
