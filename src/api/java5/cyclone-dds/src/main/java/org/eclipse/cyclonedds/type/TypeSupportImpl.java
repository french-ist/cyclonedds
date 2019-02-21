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
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.PreconditionNotMetExceptionImpl;
import org.eclipse.cyclonedds.domain.DomainParticipantImpl;
import org.eclipse.cyclonedds.pub.AbstractDataWriter;
import org.eclipse.cyclonedds.pub.DataWriterImpl;
import org.eclipse.cyclonedds.pub.PublisherImpl;
import org.eclipse.cyclonedds.sub.AbstractDataReader;
import org.eclipse.cyclonedds.sub.DataReaderImpl;
import org.eclipse.cyclonedds.sub.SubscriberImpl;
import org.eclipse.cyclonedds.topic.AbstractTopic;
import org.eclipse.cyclonedds.topic.TopicDescriptionExt;
import org.eclipse.cyclonedds.topic.TopicImpl;

public class TypeSupportImpl<TYPE> extends AbstractTypeSupport<TYPE> {
    private final CycloneServiceEnvironment environment;
    //TODO FRCYC private final org.eclipse.cyclonedds.dcps.TypeSupportImpl oldTypeSupport;
    private final Class<TYPE> dataType;
    private final String typeName;

    @SuppressWarnings("unchecked")
    public TypeSupportImpl(CycloneServiceEnvironment environment,
            Class<TYPE> dataType, String typeName) {
        super();
        this.environment = environment;
        this.dataType = dataType;
        this.typeName = typeName;

        String typeSupportName = dataType.getName() + "TypeSupport";
/* TODO FRCYC
        try {
            Class<? extends org.eclipse.cyclonedds.dcps.TypeSupportImpl> oldTypeSupportClaz;

            oldTypeSupportClaz = (Class<? extends org.eclipse.cyclonedds.dcps.TypeSupportImpl>) Class
                    .forName(typeSupportName);
            this.oldTypeSupport = oldTypeSupportClaz.newInstance();
        } catch (ClassCastException e) {
            throw new PreconditionNotMetExceptionImpl(
                    this.environment,
                    "Allocating new TypeSupport failed. "
                            + typeName
                            + "' is not an instance of org.eclipse.cyclonedds.dcps.TypeSupportImpl.");
        } catch (InstantiationException e) {
            throw new PreconditionNotMetExceptionImpl(this.environment,
                    "Allocating new TypeSupport failed. " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new PreconditionNotMetExceptionImpl(this.environment,
                    "Allocating new TypeSupport failed. " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new PreconditionNotMetExceptionImpl(this.environment,
                    "Allocating new TypeSupport failed (" + typeSupportName
                            + "); " + e.getMessage());
        }
*/
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

    /* TODO FRCYC
    @Override
    public String getTypeName() {
        if (this.typeName != null) {
            return this.typeName;
        }
        return oldTypeSupport.get_type_name();
    }

    @Override
    public TypeSupport getOldTypeSupport() {
        return this.oldTypeSupport;
    }
    */

    @Override
    public AbstractDataWriter<TYPE> createDataWriter(PublisherImpl publisher,
            AbstractTopic<TYPE> topic, DataWriterQos qos,
            DataWriterListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        return new DataWriterImpl<TYPE>(this.environment, publisher,
                (TopicImpl<TYPE>) topic, qos, listener, statuses);
    }

    @Override
    public AbstractDataReader<TYPE> createDataReader(SubscriberImpl subscriber,
            TopicDescriptionExt<TYPE> topicDescription, DataReaderQos qos,
            DataReaderListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        return new DataReaderImpl<TYPE>(this.environment, subscriber,
                topicDescription, qos, listener, statuses);
    }

    @Override
    public AbstractTopic<TYPE> createTopic(DomainParticipantImpl participant,
            String topicName, TopicQos qos, TopicListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        return new TopicImpl<TYPE>(this.environment, participant, topicName,
                this, qos, listener, statuses);
    }

	@Override
	public TypeSupport getOldTypeSupport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return null;
	}
    
    

}
