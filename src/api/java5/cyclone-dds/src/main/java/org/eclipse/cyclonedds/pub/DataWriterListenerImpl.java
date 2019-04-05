/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.pub;

import java.io.Serializable;

import org.omg.dds.core.event.LivelinessLostEvent;
import org.omg.dds.core.event.OfferedDeadlineMissedEvent;
import org.omg.dds.core.event.OfferedIncompatibleQosEvent;
import org.omg.dds.core.event.PublicationMatchedEvent;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterListener;
import org.eclipse.cyclonedds.core.Listener;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.event.LivelinessLostEventImpl;
import org.eclipse.cyclonedds.core.event.OfferedDeadlineMissedEventImpl;
import org.eclipse.cyclonedds.core.event.OfferedIncompatibleQosEventImpl;
import org.eclipse.cyclonedds.core.event.PublicationMatchedEventImpl;
import org.eclipse.cyclonedds.core.status.StatusConverter;

/* TODO FRCYC
import LivelinessLostStatus;
import OfferedDeadlineMissedStatus;
import OfferedIncompatibleQosStatus;
import PublicationMatchedStatus;
*/

public class DataWriterListenerImpl<TYPE> extends
        Listener<DataWriterListener<TYPE>> implements DataWriterListener,
        Serializable {
    private static final long serialVersionUID = 8422072066415569795L;
    private final transient DataWriter<TYPE> writer;

    public DataWriterListenerImpl(CycloneServiceEnvironment environment,
            DataWriter<TYPE> writer, DataWriterListener<TYPE> listener) {
        this(environment, writer, listener, false);
    }

    public DataWriterListenerImpl(CycloneServiceEnvironment environment,
            DataWriter<TYPE> writer, DataWriterListener<TYPE> listener,
            boolean waitUntilInitialised) {
        super(environment, listener, waitUntilInitialised);
        this.writer = writer;
    }

	@Override
	public void onOfferedDeadlineMissed(OfferedDeadlineMissedEvent status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOfferedIncompatibleQos(OfferedIncompatibleQosEvent status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLivelinessLost(LivelinessLostEvent status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPublicationMatched(PublicationMatchedEvent status) {
		// TODO Auto-generated method stub
		
	}
    
    /* TODO FRCYC

    @Override
    public void on_liveliness_lost(DataWriter arg0,
            LivelinessLostStatus arg1) {
        this.waitUntilInitialised();
        this.listener.onLivelinessLost(new LivelinessLostEventImpl<TYPE>(
                this.environment, writer, StatusConverter.convert(
                        this.environment, arg1)));

    }

    @Override
    public void on_offered_deadline_missed(DataWriter arg0,
            OfferedDeadlineMissedStatus arg1) {
        this.waitUntilInitialised();
        this.listener.onOfferedDeadlineMissed(new OfferedDeadlineMissedEventImpl<TYPE>(
                this.environment, writer, StatusConverter.convert(
                        this.environment, arg1)));

    }

    @Override
    public void on_offered_incompatible_qos(DataWriter arg0,
            OfferedIncompatibleQosStatus arg1) {
        this.waitUntilInitialised();
        this.listener.onOfferedIncompatibleQos(new OfferedIncompatibleQosEventImpl<TYPE>(
                this.environment, writer, StatusConverter.convert(
                        this.environment, arg1)));

    }

    @Override
    public void on_publication_matched(DataWriter arg0,
            PublicationMatchedStatus arg1) {
        this.waitUntilInitialised();
        this.listener.onPublicationMatched(new PublicationMatchedEventImpl<TYPE>(
                this.environment, writer, StatusConverter.convert(
                        this.environment, arg1)));
    }
    */

}
