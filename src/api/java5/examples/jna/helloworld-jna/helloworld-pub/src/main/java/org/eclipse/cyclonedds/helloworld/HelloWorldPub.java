/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.helloworld;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;

/**
 * Hello world!
 *
 */
public class HelloWorldPub 
{
    int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
    int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;
    HelloWorldData_Helper helper = new HelloWorldData_Helper();

    public HelloWorldPub(){
        /* Create a Participant. */
        int part = DdscLibrary.dds_create_participant (0, null, null);
        assert(helper.dds_error_check(part, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Create a Topic. */
        int topic = DdscLibrary.dds_create_topic(part, helper.getHelloWorldData_Msg_desc(), "Msg", null, null);
        assert(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Create a Writer. */
        int writer = DdscLibrary.dds_create_writer(part, topic, null, null);

        System.out.println("=== [Publisher]  Waiting for a reader to be discovered ...\n");

        DdscLibrary.dds_set_enabled_status(writer, DdscLibrary.DDS_PUBLICATION_MATCHED_STATUS);
        assert(helper.dds_error_check(topic, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0) ;

        /*while (true) {
            IntBuffer status = IntBuffer.allocate(Native.getNativeSize(Integer.class));
            int ret = DdscLibrary.dds_get_status_changes(writer, status);
            assert(helper.dds_error_check(ret, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

            if (status.get() == DdscLibrary.DDS_PUBLICATION_MATCHED_STATUS) {
                System.out.println("DDS_PUBLICATION_MATCHED_STATUS");
                break;
            } else {
            	System.err.println("Status: " + status.get());
            }

            /* Polling sleep. 
            int milliSeconds = 20;
            org.eclipse.cyclonedds.ddsc.dds_public_time.DdscLibrary.dds_sleepfor(milliSeconds*1000000L);
        }*/

        /* Create a message to write. */
        Msg.ByReference msg = new Msg.ByReference();
        msg.userID = 1;
        msg.message = "Hello World!";
        msg.write();
        System.out.println("=== [Publisher]  Writing : ");
        System.out.println("Message (" + msg.userID + ", " + msg.message + ")");

        int ret = DdscLibrary.dds_write(writer, msg.getPointer());
        assert(helper.dds_error_check(ret, DDS_CHECK_REPORT | DDS_CHECK_EXIT) > 0);

        /* Deleting the participant will delete all its children recursively as well. */
        ret = DdscLibrary.dds_delete(part);
    }

    public static void main( String[] args )
    {
        new HelloWorldPub();
    }
}
