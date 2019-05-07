/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.type;

import java.util.Collection;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.Status;
import org.omg.dds.pub.DataWriterListener;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.TypeSupport;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.PreconditionNotMetExceptionImpl;
import org.eclipse.cyclonedds.domain.DomainParticipantImpl;
import org.eclipse.cyclonedds.pub.AbstractDataWriter;
import org.eclipse.cyclonedds.pub.DataWriterImpl;
import org.eclipse.cyclonedds.pub.PublisherImpl;
import org.eclipse.cyclonedds.sub.AbstractDataReader;
import org.eclipse.cyclonedds.sub.DataReaderImpl;
import org.eclipse.cyclonedds.sub.SubscriberImpl;
import org.eclipse.cyclonedds.topic.AbstractTopic;
//import org.eclipse.cyclonedds.topic.TopicDescriptionExt;
import org.eclipse.cyclonedds.topic.TopicImpl;

public class TypeSupportImpl<TYPE> extends AbstractTypeSupport<TYPE> {
    private final ServiceEnvironmentImpl environment;
    //TODO FRCYC private final org.eclipse.cyclonedds.dcps.TypeSupportImpl oldTypeSupport;
    private final Class<TYPE> dataType;
    private final String typeName;

    @SuppressWarnings("unchecked")
    public TypeSupportImpl(ServiceEnvironmentImpl environment,
            Class<TYPE> dataType, String typeName) {
        super();
        this.environment = environment;
        this.dataType = dataType;
        this.typeName = typeName;        
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public TYPE newData() {
        try {
            return dataType.newInstance();
        } catch (InstantiationException e) {
            throw new PreconditionNotMetExceptionImpl(this.environment,
                    "Unable to instantiate data; " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new PreconditionNotMetExceptionImpl(this.environment,
                    "Unable to instantiate data; " + e.getMessage());
        }
    }

    @Override
    public Class<TYPE> getType() {
        return this.dataType;
    }

    @Override
    public AbstractDataWriter<TYPE> createDataWriter(PublisherImpl publisher,
            AbstractTopic<TYPE> topic, DataWriterQos qos,
            DataWriterListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        return new DataWriterImpl<TYPE>(this.environment, publisher,
                (TopicImpl<TYPE>) topic, qos, listener, statuses);
    }
    
	@Override
	public AbstractDataReader<TYPE> createDataReader(SubscriberImpl subscriber, AbstractTopic<TYPE> topic,
			DataReaderQos qos, DataReaderListener<TYPE> listener, Collection<Class<? extends Status>> statuses) {
		return new DataReaderImpl<TYPE>(this.environment, subscriber,
				(TopicImpl<TYPE>) topic,  qos, listener, statuses);
	}
    
    @Override
    public AbstractTopic<TYPE> createTopic(DomainParticipantImpl participant,
            String topicName, TopicQos qos, TopicListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        return new TopicImpl<TYPE>(this.environment, participant, topicName,
                this, qos, listener, statuses);
    }

	@Override
	public String getTypeName() {
		return typeName;
	}


}
