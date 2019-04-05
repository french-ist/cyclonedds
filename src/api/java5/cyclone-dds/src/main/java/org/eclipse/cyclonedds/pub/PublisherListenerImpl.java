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
import org.omg.dds.pub.PublisherListener;
import org.eclipse.cyclonedds.core.Listener;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.event.LivelinessLostEventImpl;
import org.eclipse.cyclonedds.core.event.OfferedDeadlineMissedEventImpl;
import org.eclipse.cyclonedds.core.event.OfferedIncompatibleQosEventImpl;
import org.eclipse.cyclonedds.core.event.PublicationMatchedEventImpl;
import org.eclipse.cyclonedds.core.status.StatusConverter;

/* TODO FRCYC
import DataWriter;
import LivelinessLostStatus;
import OfferedDeadlineMissedStatus;
import OfferedIncompatibleQosStatus;
import PublicationMatchedStatus;
*/

public class PublisherListenerImpl extends Listener<PublisherListener>
        implements PublisherListener, Serializable {
    private static final long serialVersionUID = -7442074499638981651L;
    private final transient PublisherImpl publisher;

    public PublisherListenerImpl(CycloneServiceEnvironment environment,
            PublisherImpl publisher, PublisherListener listener) {
        this(environment, publisher, listener, false);
    }

    public PublisherListenerImpl(CycloneServiceEnvironment environment,
            PublisherImpl publisher, PublisherListener listener,
            boolean waitUntilInitialised) {
        super(environment, listener, waitUntilInitialised);
        this.publisher = publisher;
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

    /* TODO FRCYC
    @Override
    public void on_offered_deadline_missed(DataWriter writer,
            OfferedDeadlineMissedStatus status) {
        org.omg.dds.pub.DataWriter<Object> found = this.publisher
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
    public void on_liveliness_lost(DataWriter writer,
            LivelinessLostStatus status) {
        org.omg.dds.pub.DataWriter<Object> found = this.publisher
                .lookupDataWriter(writer);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener.onLivelinessLost(new LivelinessLostEventImpl<Object>(
                    this.environment, found, StatusConverter.convert(
                            this.environment, status)));
        }
    }

    @Override
    public void on_offered_incompatible_qos(DataWriter writer,
            OfferedIncompatibleQosStatus status) {
        org.omg.dds.pub.DataWriter<Object> found = this.publisher
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
    public void on_publication_matched(DataWriter writer,
            PublicationMatchedStatus status) {
        org.omg.dds.pub.DataWriter<Object> found = this.publisher
                .lookupDataWriter(writer);

        if (found != null) {
            this.waitUntilInitialised();
            this.listener
                    .onPublicationMatched(new PublicationMatchedEventImpl<Object>(
                            this.environment, found, StatusConverter.convert(
                                    this.environment, status)));
        }
    }
    */
}
