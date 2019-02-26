/*
 *                         Vortex OpenSplice
 *
 *   This software and documentation are Copyright 2006 to TO_YEAR ADLINK
 *   Technology Limited, its affiliated companies and licensors. All rights
 *   reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.eclipse.cyclonedds.sub;

import java.io.Serializable;

import org.omg.dds.core.event.DataAvailableEvent;
import org.omg.dds.core.event.DataOnReadersEvent;
import org.omg.dds.core.event.LivelinessChangedEvent;
import org.omg.dds.core.event.RequestedDeadlineMissedEvent;
import org.omg.dds.core.event.RequestedIncompatibleQosEvent;
import org.omg.dds.core.event.SampleLostEvent;
import org.omg.dds.core.event.SampleRejectedEvent;
import org.omg.dds.core.event.SubscriptionMatchedEvent;
import org.omg.dds.sub.SubscriberListener;
import org.eclipse.cyclonedds.core.Listener;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.event.DataAvailableEventImpl;
import org.eclipse.cyclonedds.core.event.DataOnReadersEventImpl;
import org.eclipse.cyclonedds.core.event.LivelinessChangedEventImpl;
import org.eclipse.cyclonedds.core.event.RequestedDeadlineMissedEventImpl;
import org.eclipse.cyclonedds.core.event.RequestedIncompatibleQosEventImpl;
import org.eclipse.cyclonedds.core.event.SampleLostEventImpl;
import org.eclipse.cyclonedds.core.event.SampleRejectedEventImpl;
import org.eclipse.cyclonedds.core.event.SubscriptionMatchedEventImpl;
import org.eclipse.cyclonedds.core.status.DataAvailableStatusImpl;
import org.eclipse.cyclonedds.core.status.DataOnReadersStatusImpl;
import org.eclipse.cyclonedds.core.status.StatusConverter;

/* TODO FRCYC 
import DataReader;
import LivelinessChangedStatus;
import RequestedDeadlineMissedStatus;
import RequestedIncompatibleQosStatus;
import SampleLostStatus;
import SampleRejectedStatus;
import Subscriber;
import SubscriptionMatchedStatus;
*/

public class SubscriberListenerImpl extends Listener<SubscriberListener>
        implements SubscriberListener, Serializable {
    private static final long serialVersionUID = -1147185392577428150L;
    private final transient SubscriberImpl subscriber;

    public SubscriberListenerImpl(CycloneServiceEnvironment environment,
            SubscriberImpl subscriber, SubscriberListener listener) {
        this(environment, subscriber, listener, false);
    }
    public SubscriberListenerImpl(CycloneServiceEnvironment environment,
            SubscriberImpl subscriber, SubscriberListener listener,
            boolean waitUntilInitialised) {
        super(environment, listener, waitUntilInitialised);
        this.subscriber = subscriber;
    }
    /*

    @Override
    public void on_data_on_readers(Subscriber subs) {
        this.waitUntilInitialised();
        this.listener.onDataOnReaders(new DataOnReadersEventImpl(
                this.environment, this.subscriber, new DataOnReadersStatusImpl(
                        this.environment)));
    }

    @Override
    public void on_requested_deadline_missed(DataReader reader,
            RequestedDeadlineMissedStatus status) {
        org.omg.dds.sub.DataReader<Object> found = this.subscriber
                .lookupDataReader(reader);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener
                    .onRequestedDeadlineMissed(new RequestedDeadlineMissedEventImpl<Object>(
                            this.environment, found, StatusConverter.convert(
                                    this.environment, status)));
        }
    }

    @Override
    public void on_sample_rejected(DataReader reader,
            SampleRejectedStatus status) {
        org.omg.dds.sub.DataReader<Object> found = this.subscriber
                .lookupDataReader(reader);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener.onSampleRejected(new SampleRejectedEventImpl<Object>(
                    this.environment, found, StatusConverter.convert(
                            this.environment, status)));
        }
    }

    @Override
    public void on_liveliness_changed(DataReader reader,
            LivelinessChangedStatus status) {
        org.omg.dds.sub.DataReader<Object> found = this.subscriber
                .lookupDataReader(reader);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener
                    .onLivelinessChanged(new LivelinessChangedEventImpl<Object>(
                            this.environment, found, StatusConverter.convert(
                                    this.environment, status)));
        }
    }

    @Override
    public void on_data_available(DataReader reader) {
        org.omg.dds.sub.DataReader<Object> found = this.subscriber
                .lookupDataReader(reader);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener.onDataAvailable(new DataAvailableEventImpl<Object>(
                    this.environment, found, new DataAvailableStatusImpl(
                            this.environment)));
        }
    }

    @Override
    public void on_requested_incompatible_qos(DataReader arg0,
            RequestedIncompatibleQosStatus arg1) {
        org.omg.dds.sub.DataReader<Object> found = this.subscriber
                .lookupDataReader(arg0);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener
                    .onRequestedIncompatibleQos(new RequestedIncompatibleQosEventImpl<Object>(
                            this.environment, found, StatusConverter.convert(
                                    this.environment, arg1)));
        }
    }

    @Override
    public void on_subscription_matched(DataReader reader,
            SubscriptionMatchedStatus status) {
        org.omg.dds.sub.DataReader<Object> found = this.subscriber
                .lookupDataReader(reader);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener
                    .onSubscriptionMatched(new SubscriptionMatchedEventImpl<Object>(
                            this.environment, found, StatusConverter.convert(
                                    this.environment, status)));
        }
    }

    @Override
    public void on_sample_lost(DataReader reader, SampleLostStatus status) {
        org.omg.dds.sub.DataReader<Object> found = this.subscriber
                .lookupDataReader(reader);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener.onSampleLost(new SampleLostEventImpl<Object>(
                    this.environment, found, StatusConverter.convert(
                            this.environment, status)));
        }
    }
    */
	@Override
	public void onRequestedDeadlineMissed(RequestedDeadlineMissedEvent<?> status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRequestedIncompatibleQos(RequestedIncompatibleQosEvent<?> status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSampleRejected(SampleRejectedEvent<?> status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLivelinessChanged(LivelinessChangedEvent<?> status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDataAvailable(DataAvailableEvent<?> status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSubscriptionMatched(SubscriptionMatchedEvent<?> status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSampleLost(SampleLostEvent<?> status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDataOnReaders(DataOnReadersEvent status) {
		// TODO Auto-generated method stub
		
	}

}
