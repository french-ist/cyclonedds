/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.topic;

import java.io.Serializable;

import org.omg.dds.core.event.InconsistentTopicEvent;
import org.omg.dds.topic.TopicListener;
import org.eclipse.cyclonedds.core.Listener;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.event.AllDataDisposedEventImpl;
import org.eclipse.cyclonedds.core.event.InconsistentTopicEventImpl;
import org.eclipse.cyclonedds.core.status.StatusConverter;

//TODO FRCYC import AllDataDisposedTopicStatusHolder;
//TODO FRCYC import InconsistentTopicStatus;

public class TopicListenerImpl<TYPE> extends
 Listener<TopicListener<TYPE>>
        implements TopicListener, Serializable { //TODO FRCYC ExtTopicListener
    private static final long serialVersionUID = -3957061097858393241L;
    private final transient AbstractTopic<TYPE> topic;
    private final transient org.eclipse.cyclonedds.topic.TopicListener<TYPE> extListener;

    public TopicListenerImpl(ServiceEnvironmentImpl environment,
            AbstractTopic<TYPE> topic, TopicListener<TYPE> listener) {
        this(environment, topic, listener, false);
    }

    public TopicListenerImpl(ServiceEnvironmentImpl environment,
            AbstractTopic<TYPE> topic, TopicListener<TYPE> listener,
            boolean waitUntilInitialised) {
        super(environment, listener, waitUntilInitialised);
        this.topic = topic;

        if(listener instanceof org.eclipse.cyclonedds.topic.TopicListener<?>){
            this.extListener = (org.eclipse.cyclonedds.topic.TopicListener<TYPE>)listener;
        } else{
            this.extListener = null;
        }
    }

	@Override
	public void onInconsistentTopic(InconsistentTopicEvent status) {
		// TODO Auto-generated method stub
		
	}

    /* TODO FRCYC
    @Override
    public void on_inconsistent_topic(Topic arg0,
            InconsistentTopicStatus arg1) {
        this.waitUntilInitialised();
        this.listener.onInconsistentTopic(new InconsistentTopicEventImpl<TYPE>(
                this.environment, this.topic, StatusConverter.convert(this.environment,
                        arg1)));

    }

    @Override
    public void on_all_data_disposed(Topic arg0) {
        AllDataDisposedTopicStatusHolder holder = new AllDataDisposedTopicStatusHolder();
        int rc = arg0.get_all_data_disposed_topic_status(holder);

        if(rc == RETCODE_OK.value){
            if(extListener != null){
                this.waitUntilInitialised();
                if(holder.value != null){
                    this.extListener
                        .onAllDataDisposed(new AllDataDisposedEventImpl<TYPE>(
                                this.environment, this.topic,
                                StatusConverter.convert(this.environment, holder.value)));
                } else {
                    this.extListener
                    .onAllDataDisposed(new AllDataDisposedEventImpl<TYPE>(
                            this.environment, this.topic, null));
                }
            }
        }
    }*/
}
