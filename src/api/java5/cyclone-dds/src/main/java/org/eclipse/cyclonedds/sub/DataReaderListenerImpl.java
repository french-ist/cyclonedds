/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.sub;

import java.io.Serializable;

import org.omg.dds.core.event.DataAvailableEvent;
import org.omg.dds.core.event.LivelinessChangedEvent;
import org.omg.dds.core.event.RequestedDeadlineMissedEvent;
import org.omg.dds.core.event.RequestedIncompatibleQosEvent;
import org.omg.dds.core.event.SampleLostEvent;
import org.omg.dds.core.event.SampleRejectedEvent;
import org.omg.dds.core.event.SubscriptionMatchedEvent;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderListener;
import org.eclipse.cyclonedds.core.Listener;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.event.DataAvailableEventImpl;
import org.eclipse.cyclonedds.core.event.LivelinessChangedEventImpl;
import org.eclipse.cyclonedds.core.event.RequestedDeadlineMissedEventImpl;
import org.eclipse.cyclonedds.core.event.RequestedIncompatibleQosEventImpl;
import org.eclipse.cyclonedds.core.event.SampleLostEventImpl;
import org.eclipse.cyclonedds.core.event.SampleRejectedEventImpl;
import org.eclipse.cyclonedds.core.event.SubscriptionMatchedEventImpl;
import org.eclipse.cyclonedds.core.status.DataAvailableStatusImpl;
import org.eclipse.cyclonedds.core.status.StatusConverter;

/* TODO FRCYC
import LivelinessChangedStatus;
import RequestedDeadlineMissedStatus;
import RequestedIncompatibleQosStatus;
import SampleLostStatus;
import SampleRejectedStatus;
import SubscriptionMatchedStatus;
*/

public class DataReaderListenerImpl<TYPE> extends
        Listener<DataReaderListener<TYPE>> implements DataReaderListener,
        Serializable {
    private static final long serialVersionUID = 6892152554338498323L;
    private final transient DataReader<TYPE> reader;

    public DataReaderListenerImpl(CycloneServiceEnvironment environment,
            DataReader<TYPE> reader, DataReaderListener<TYPE> listener) {
        this(environment, reader, listener, false);
    }

    public DataReaderListenerImpl(CycloneServiceEnvironment environment,
            DataReader<TYPE> reader, DataReaderListener<TYPE> listener,
            boolean waitUntilInitialised) {
        super(environment, listener, waitUntilInitialised);
        this.reader = reader;
    }

    /*
    @Override
    public void on_requested_deadline_missed(DataReader reader,
            RequestedDeadlineMissedStatus status) {
        this.waitUntilInitialised();
        this.listener
                .onRequestedDeadlineMissed(new RequestedDeadlineMissedEventImpl<TYPE>(
                        this.environment, this.reader, StatusConverter.convert(
                                this.environment, status)));
    }

    @Override
    public void on_sample_rejected(DataReader reader,
            SampleRejectedStatus status) {
        this.waitUntilInitialised();
        this.listener.onSampleRejected(new SampleRejectedEventImpl<TYPE>(
                this.environment, this.reader, StatusConverter.convert(
                        this.environment, status)));
    }

    @Override
    public void on_liveliness_changed(DataReader reader,
            LivelinessChangedStatus status) {
        this.waitUntilInitialised();
        this.listener.onLivelinessChanged(new LivelinessChangedEventImpl<TYPE>(
                this.environment, this.reader, StatusConverter.convert(
                        this.environment, status)));
    }

    @Override
    public void on_data_available(DataReader reader) {
        this.waitUntilInitialised();
        this.listener.onDataAvailable(new DataAvailableEventImpl<TYPE>(
                this.environment, this.reader, new DataAvailableStatusImpl(
                        this.environment)));
    }

    @Override
    public void on_requested_incompatible_qos(DataReader reader,
            RequestedIncompatibleQosStatus status) {
        this.waitUntilInitialised();
        this.listener
                .onRequestedIncompatibleQos(new RequestedIncompatibleQosEventImpl<TYPE>(
                        this.environment, this.reader, StatusConverter.convert(
                                this.environment, status)));
    }

    @Override
    public void on_subscription_matched(DataReader reader,
            SubscriptionMatchedStatus status) {
        this.waitUntilInitialised();
        this.listener
                .onSubscriptionMatched(new SubscriptionMatchedEventImpl<TYPE>(
                        this.environment, this.reader, StatusConverter.convert(
                                this.environment, status)));
    }

    @Override
    public void on_sample_lost(DataReader reader, SampleLostStatus status) {
        this.waitUntilInitialised();
        this.listener.onSampleLost(new SampleLostEventImpl<TYPE>(
                this.environment, this.reader, StatusConverter.convert(
                        this.environment, status)));
    }
    */
    
	@Override
	public void onRequestedDeadlineMissed(RequestedDeadlineMissedEvent status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestedIncompatibleQos(RequestedIncompatibleQosEvent status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSampleRejected(SampleRejectedEvent status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLivelinessChanged(LivelinessChangedEvent status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDataAvailable(DataAvailableEvent status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscriptionMatched(SubscriptionMatchedEvent status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSampleLost(SampleLostEvent status) {
		// TODO Auto-generated method stub
		
	}
}
