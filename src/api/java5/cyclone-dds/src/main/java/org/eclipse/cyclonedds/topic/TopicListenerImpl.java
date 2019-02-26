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
package org.eclipse.cyclonedds.topic;

import java.io.Serializable;

import org.omg.dds.core.event.InconsistentTopicEvent;
import org.omg.dds.topic.TopicListener;
import org.eclipse.cyclonedds.core.Listener;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
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

    public TopicListenerImpl(CycloneServiceEnvironment environment,
            AbstractTopic<TYPE> topic, TopicListener<TYPE> listener) {
        this(environment, topic, listener, false);
    }

    public TopicListenerImpl(CycloneServiceEnvironment environment,
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
