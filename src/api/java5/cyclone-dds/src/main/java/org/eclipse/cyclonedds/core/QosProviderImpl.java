/**
 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core;

import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipantFactoryQos;
import org.omg.dds.domain.DomainParticipantQos;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.topic.TopicQos;
import org.eclipse.cyclonedds.domain.DomainParticipantQosImpl;
import org.eclipse.cyclonedds.pub.DataWriterQosImpl;
import org.eclipse.cyclonedds.pub.PublisherQosImpl;
import org.eclipse.cyclonedds.sub.DataReaderQosImpl;
import org.eclipse.cyclonedds.sub.SubscriberQosImpl;
import org.eclipse.cyclonedds.topic.TopicQosImpl;

//import DataReaderQosHolder;
//import DataWriterQosHolder;
//import PublisherQosHolder;
//import SubscriberQosHolder;

public class QosProviderImpl extends QosProvider {
    private final CycloneServiceEnvironment environment;
    private QosProvider old;

    public QosProviderImpl(CycloneServiceEnvironment environment, String uri,
            String profile) {
        if (uri == null) {
            throw new IllegalArgumentExceptionImpl(environment, "Invalid uri provided");
        }
        this.environment = environment;
        try {
            // TODO FRCYC this.old = new QosProvider(uri, profile);
        } catch (NullPointerException npe) {
            //TODO FRCYC Utilities.throwLastErrorException(this.environment);
        }
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public DomainParticipantFactoryQos getDomainParticipantFactoryQos() {
        return this.getDomainParticipantFactoryQos(null);
    }

    @Override
    public DomainParticipantFactoryQos getDomainParticipantFactoryQos(String id) {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "QosProvider.getDomainParticipantFactoryQos() not supported.");
    }

    @Override
    public DomainParticipantQos getDomainParticipantQos() {
        return this.getDomainParticipantQos(null);
    }

	@Override
	public DomainParticipantQos getDomainParticipantQos(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicQos getTopicQos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicQos getTopicQos(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubscriberQos getSubscriberQos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubscriberQos getSubscriberQos(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublisherQos getPublisherQos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublisherQos getPublisherQos(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataReaderQos getDataReaderQos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataReaderQos getDataReaderQos(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWriterQos getDataWriterQos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWriterQos getDataWriterQos(String id) {
		// TODO Auto-generated method stub
		return null;
	}

    /* TODO FRCYC
    @Override
    public DomainParticipantQos getDomainParticipantQos(String id) {
        DomainParticipantQosHolder holder = new DomainParticipantQosHolder();
        int rc = old.get_participant_qos(holder, id);
        Utilities.checkReturnCode(rc, this.environment,
                "QosProvider.getDomainParticipantQos() failed.");
        if (rc == RETCODE_NO_DATA.value) {
            return null;
        }
        return DomainParticipantQosImpl.convert(this.environment, holder.value);
    }

    @Override
    public TopicQos getTopicQos() {
        return this.getTopicQos(null);
    }

    @Override
    public TopicQos getTopicQos(String id) {
        TopicQosHolder holder = new TopicQosHolder();
        int rc = old.get_topic_qos(holder, id);
        Utilities.checkReturnCode(rc, this.environment,
                "QosProvider.getTopicQos() failed.");
        if (rc == RETCODE_NO_DATA.value) {
            return null;
        }
        return TopicQosImpl.convert(this.environment, holder.value);
    }

    @Override
    public SubscriberQos getSubscriberQos() {
        return this.getSubscriberQos(null);
    }

    @Override
    public SubscriberQos getSubscriberQos(String id) {
        SubscriberQosHolder holder = new SubscriberQosHolder();
        int rc = this.old.get_subscriber_qos(holder, id);
        Utilities.checkReturnCode(rc, this.environment,
                "QosProvider.getSubscriberQos() failed.");
        if (rc == RETCODE_NO_DATA.value) {
            return null;
        }
        return SubscriberQosImpl.convert(this.environment, holder.value);
    }

    @Override
    public PublisherQos getPublisherQos() {
        return this.getPublisherQos(null);
    }

    @Override
    public PublisherQos getPublisherQos(String id) {
        PublisherQosHolder holder = new PublisherQosHolder();
        int rc = this.old.get_publisher_qos(holder, id);
        Utilities.checkReturnCode(rc, this.environment,
                "QosProvider.getPublisherQos() failed.");
        if (rc == RETCODE_NO_DATA.value) {
            return null;
        }
        return PublisherQosImpl.convert(this.environment, holder.value);
    }

    @Override
    public DataReaderQos getDataReaderQos() {
        return this.getDataReaderQos(null);
    }

    @Override
    public DataReaderQos getDataReaderQos(String id) {
        DataReaderQosHolder holder = new DataReaderQosHolder();
        int rc = this.old.get_datareader_qos(holder, id);
        Utilities.checkReturnCode(rc, this.environment,
                "QosProvider.getDataReaderQos() failed.");
        if (rc == RETCODE_NO_DATA.value) {
            return null;
        }
        return DataReaderQosImpl.convert(this.environment, holder.value);
    }

    @Override
    public DataWriterQos getDataWriterQos() {
        return this.getDataWriterQos(null);
    }

    @Override
    public DataWriterQos getDataWriterQos(String id) {
        DataWriterQosHolder holder = new DataWriterQosHolder();
        int rc = this.old.get_datawriter_qos(holder, id);
        Utilities.checkReturnCode(rc, this.environment,
                "QosProvider.getDataWriterQos() failed.");
        if (rc == RETCODE_NO_DATA.value) {
            return null;
        }
        return DataWriterQosImpl.convert(this.environment, holder.value);
    }
    */
}
