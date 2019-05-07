/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.domain;

import java.io.Serializable;

import org.omg.dds.core.event.DataAvailableEvent;
import org.omg.dds.core.event.DataOnReadersEvent;
import org.omg.dds.core.event.InconsistentTopicEvent;
import org.omg.dds.core.event.LivelinessChangedEvent;
import org.omg.dds.core.event.LivelinessLostEvent;
import org.omg.dds.core.event.OfferedDeadlineMissedEvent;
import org.omg.dds.core.event.OfferedIncompatibleQosEvent;
import org.omg.dds.core.event.PublicationMatchedEvent;
import org.omg.dds.core.event.RequestedDeadlineMissedEvent;
import org.omg.dds.core.event.RequestedIncompatibleQosEvent;
import org.omg.dds.core.event.SampleLostEvent;
import org.omg.dds.core.event.SampleRejectedEvent;
import org.omg.dds.core.event.SubscriptionMatchedEvent;
import org.omg.dds.domain.DomainParticipantListener;
import org.eclipse.cyclonedds.core.Listener;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.event.AllDataDisposedEventImpl;
import org.eclipse.cyclonedds.core.event.DataAvailableEventImpl;
import org.eclipse.cyclonedds.core.event.DataOnReadersEventImpl;
import org.eclipse.cyclonedds.core.event.InconsistentTopicEventImpl;
import org.eclipse.cyclonedds.core.event.LivelinessChangedEventImpl;
import org.eclipse.cyclonedds.core.event.LivelinessLostEventImpl;
import org.eclipse.cyclonedds.core.event.OfferedDeadlineMissedEventImpl;
import org.eclipse.cyclonedds.core.event.OfferedIncompatibleQosEventImpl;
import org.eclipse.cyclonedds.core.event.PublicationMatchedEventImpl;
import org.eclipse.cyclonedds.core.event.RequestedDeadlineMissedEventImpl;
import org.eclipse.cyclonedds.core.event.RequestedIncompatibleQosEventImpl;
import org.eclipse.cyclonedds.core.event.SampleLostEventImpl;
import org.eclipse.cyclonedds.core.event.SampleRejectedEventImpl;
import org.eclipse.cyclonedds.core.event.SubscriptionMatchedEventImpl;
import org.eclipse.cyclonedds.core.status.DataAvailableStatusImpl;
import org.eclipse.cyclonedds.core.status.DataOnReadersStatusImpl;
import org.eclipse.cyclonedds.core.status.StatusConverter;

/* TODO FRCYC
//TODO FRCYC import AllDataDisposedTopicStatusHolder;
import DataReader;
import DataWriter;
//TODO FRCYC import InconsistentTopicStatus;
import LivelinessChangedStatus;
import LivelinessLostStatus;
import OfferedDeadlineMissedStatus;
import OfferedIncompatibleQosStatus;
import PublicationMatchedStatus;
import RequestedDeadlineMissedStatus;
import RequestedIncompatibleQosStatus;
import SampleLostStatus;
import SampleRejectedStatus;
import Subscriber;
import SubscriptionMatchedStatus;
import Topic;
*/

public class DomainParticipantListenerImpl extends
        Listener<DomainParticipantListener> implements
        DomainParticipantListener, Serializable { //TODO FRCYC ExtDomainParticipantListener
    private static final long serialVersionUID = -3531755144455417494L;
    private final transient DomainParticipantImpl participant;
    private final transient org.eclipse.cyclonedds.domain.DomainParticipantListener extListener;

    public DomainParticipantListenerImpl(ServiceEnvironmentImpl environment,
            DomainParticipantImpl participant,
            DomainParticipantListener listener) {
        this(environment, participant, listener, false);
    }

    public DomainParticipantListenerImpl(ServiceEnvironmentImpl environment,
            DomainParticipantImpl participant,
            DomainParticipantListener listener, boolean waitUntilInitialised) {
        super(environment, listener, waitUntilInitialised);
        this.participant = participant;

        if (listener instanceof org.eclipse.cyclonedds.domain.DomainParticipantListener) {
            this.extListener = (org.eclipse.cyclonedds.domain.DomainParticipantListener) listener;
        } else {
            this.extListener = null;
        }

    }

	@Override
	public void onOfferedDeadlineMissed(OfferedDeadlineMissedEvent<?> status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOfferedIncompatibleQos(OfferedIncompatibleQosEvent<?> status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLivelinessLost(LivelinessLostEvent<?> status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPublicationMatched(PublicationMatchedEvent<?> status) {
		// TODO Auto-generated method stub
		
	}

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

	@Override
	public void onInconsistentTopic(InconsistentTopicEvent<?> status) {
		// TODO Auto-generated method stub
		
	}

    /* TODO FRCYC
    @Override
    public void on_inconsistent_topic(Topic the_topic,
            InconsistentTopicStatus status) {
        try {
            org.omg.dds.topic.Topic<Object> found = participant
                    .getTopic(the_topic);

            if (found != null) {
                this.waitUntilInitialised();
                this.listener
                        .onInconsistentTopic(new InconsistentTopicEventImpl<Object>(
                                this.environment, found, StatusConverter
                                        .convert(this.environment, status)));
            }
        } catch (ClassCastException e) {
        }
    }

    @Override
    public void on_offered_deadline_missed(DataWriter writer,
            OfferedDeadlineMissedStatus status) {

        org.omg.dds.pub.DataWriter<Object> found = this.participant
                .lookupDataWriter(writer);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener
                    .onOfferedDeadlineMissed(new OfferedDeadlineMissedEventImpl<Object>(
                            this.environment, found, StatusConverter.convert(
                                    this.environment, status)));
        }
    }

    @Override
    public void on_offered_incompatible_qos(DataWriter writer,
            OfferedIncompatibleQosStatus status) {

        org.omg.dds.pub.DataWriter<Object> found = this.participant
                .lookupDataWriter(writer);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener
                    .onOfferedIncompatibleQos(new OfferedIncompatibleQosEventImpl<Object>(
                            this.environment, found, StatusConverter.convert(
                                    this.environment, status)));
        }
    }

    @Override
    public void on_liveliness_lost(DataWriter writer,
            LivelinessLostStatus status) {
        org.omg.dds.pub.DataWriter<Object> found = this.participant
                .lookupDataWriter(writer);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener.onLivelinessLost(new LivelinessLostEventImpl<Object>(
                    this.environment, found, StatusConverter.convert(
                            this.environment, status)));
        }
    }

    @Override
    public void on_publication_matched(DataWriter writer,
            PublicationMatchedStatus status) {
        org.omg.dds.pub.DataWriter<Object> found = this.participant
                .lookupDataWriter(writer);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener
                    .onPublicationMatched(new PublicationMatchedEventImpl<Object>(
                            this.environment, found, StatusConverter.convert(
                                    this.environment, status)));
        }
    }

    @Override
    public void on_data_on_readers(Subscriber subs) {
        org.omg.dds.sub.Subscriber found = this.participant
                .lookupSubscriber(subs);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener.onDataOnReaders(new DataOnReadersEventImpl(
                    this.environment, found, new DataOnReadersStatusImpl(
                            this.environment)));
        }
    }

    @Override
    public void on_requested_deadline_missed(DataReader reader,
            RequestedDeadlineMissedStatus status) {
        org.omg.dds.sub.DataReader<Object> found = this.participant
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
    public void on_requested_incompatible_qos(DataReader reader,
            RequestedIncompatibleQosStatus status) {
        org.omg.dds.sub.DataReader<Object> found = this.participant
                .lookupDataReader(reader);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener
                    .onRequestedIncompatibleQos(new RequestedIncompatibleQosEventImpl<Object>(
                            this.environment, found, StatusConverter.convert(
                                    this.environment, status)));
        }
    }

    @Override
    public void on_sample_rejected(DataReader reader,
            SampleRejectedStatus status) {
        org.omg.dds.sub.DataReader<Object> found = this.participant
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
        org.omg.dds.sub.DataReader<Object> found = this.participant
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
        org.omg.dds.sub.DataReader<Object> found = this.participant
                .lookupDataReader(reader);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener.onDataAvailable(new DataAvailableEventImpl<Object>(
                    this.environment, found, new DataAvailableStatusImpl(
                            this.environment)));
        }
    }

    @Override
    public void on_subscription_matched(DataReader reader,
            SubscriptionMatchedStatus status) {
        org.omg.dds.sub.DataReader<Object> found = this.participant
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
        org.omg.dds.sub.DataReader<Object> found = this.participant
                .lookupDataReader(reader);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener.onSampleLost(new SampleLostEventImpl<Object>(
                    this.environment, found, StatusConverter.convert(
                            this.environment, status)));
        }
    }

    @Override
    public void on_all_data_disposed(Topic arg0) {
        org.omg.dds.topic.Topic<Object> topic;
        AllDataDisposedTopicStatusHolder holder = new AllDataDisposedTopicStatusHolder();
        int rc = arg0.get_all_data_disposed_topic_status(holder);

        if (rc != RETCODE_OK.value) {
            return;
        }

        try {
            topic = participant.getTopic(arg0);

            if (this.extListener != null) {
                this.waitUntilInitialised();

                if (holder.value != null) {
                    this.extListener
                            .onAllDataDisposed(new AllDataDisposedEventImpl<Object>(
                                    this.environment, topic, StatusConverter
                                            .convert(this.environment,
                                                    holder.value)));
                } else {
                    this.extListener
                            .onAllDataDisposed(new AllDataDisposedEventImpl<Object>(
                                    this.environment, topic, null));
                }
            }
        } catch (ClassCastException e) {
        }
    }
    */
}
