package org.eclipse.cyclonedds.helloworld;

import java.nio.IntBuffer;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.junit.Assert;
import org.junit.Test;

public class DdscLibraryTest {

    int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
    int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;
    HelloWorldData_Helper helper = new HelloWorldData_Helper();

    @Test
    public void writerCreationTest() {
        /* Create a Participant. */
        int part = DdscLibrary.dds_create_participant (0, null, null);
        Assert.assertTrue(helper.dds_error_check(part, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Create a Topic. */
        int topic = DdscLibrary.dds_create_topic(part, helper.getHelloWorldData_Msg_desc(), "HelloWorldData_Msg", null, null);
        Assert.assertTrue(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Create a Writer. */
        int writer = DdscLibrary.dds_create_writer(part, topic, null, null);
        Assert.assertTrue(writer > 0);

        /* Deleting the participant will delete all its children recursively as well. */
        int ret = DdscLibrary.dds_delete(part);
    }

    @Test
    public void readerCreationTest(){
        /* Create a Participant. */
        int part = DdscLibrary.dds_create_participant (0, null, null);
        Assert.assertTrue(helper.dds_error_check(part, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Create a Topic. */
        int topic = DdscLibrary.dds_create_topic(part,
            helper.getHelloWorldData_Msg_desc(), "HelloWorldData_Msg", null, null);
        Assert.assertTrue(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Create a reliable Reader. */
        PointerByReference qos = org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos();
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qset_reliability(qos,
                org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_reliability_kind.DDS_RELIABILITY_RELIABLE,
                10 * 1000000000); // DDS_SECS (10)
        int reader = DdscLibrary.dds_create_reader (part, topic, qos, null);
        Assert.assertTrue(helper.dds_error_check(reader, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Delete qos */
        org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_qos_delete(qos);

        /* Deleting the participant will delete all its children recursively as well. */
        int deleteStatus = DdscLibrary.dds_delete(part);
        Assert.assertTrue(helper.dds_error_check(deleteStatus, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);
    }
}
