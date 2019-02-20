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
package org.eclipse.cyclonedds.pub;

import java.io.Serializable;

import org.omg.dds.core.event.LivelinessLostEvent;
import org.omg.dds.core.event.OfferedDeadlineMissedEvent;
import org.omg.dds.core.event.OfferedIncompatibleQosEvent;
import org.omg.dds.core.event.PublicationMatchedEvent;
import org.omg.dds.pub.PublisherListener;
import org.eclipse.cyclonedds.core.Listener;
import org.eclipse.cyclonedds.core.OsplServiceEnvironment;
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

    public PublisherListenerImpl(OsplServiceEnvironment environment,
            PublisherImpl publisher, PublisherListener listener) {
        this(environment, publisher, listener, false);
    }

    public PublisherListenerImpl(OsplServiceEnvironment environment,
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
