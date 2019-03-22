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
package org.eclipse.cyclonedds.sub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.cyclonedds.core.AlreadyClosedExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.DomainEntityImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalOperationExceptionImpl;
import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.domain.DomainParticipantImpl;
import org.eclipse.cyclonedds.pub.AbstractDataWriter;
import org.eclipse.cyclonedds.topic.TopicDescriptionExt;
import org.eclipse.cyclonedds.topic.TopicImpl;
import org.eclipse.cyclonedds.type.AbstractTypeSupport;
import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.status.Status;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.SubscriberListener;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicQos;

public class SubscriberImpl extends DomainEntityImpl<SubscriberQos, SubscriberListener, SubscriberListenerImpl> implements Subscriber {
    
	//private final HashMap<DataReader, AbstractDataReader<?>> readers;
    //private final boolean isBuiltin;

    private DomainParticipantImpl parent;
	private SubscriberQos qos;
	private InstanceHandleImpl handle;
	private List<AbstractDataReader<?>> readers;
	private DataReaderQosImpl defaultDataReaderQos;
	private int jnaSubscriber;
	private boolean closed = false;
	private boolean enabled = true;

	public SubscriberImpl(CycloneServiceEnvironment environment,
            DomainParticipantImpl parent, SubscriberQos qos,
            SubscriberListener listener,
            Collection<Class<? extends Status>> statuses) {
        super(environment);
        this.parent = parent;
        this.qos = qos;
        
        handle = new InstanceHandleImpl(environment, parent.getJnaParticipant());
        readers = Collections.synchronizedList(new ArrayList<AbstractDataReader<?>>());
        defaultDataReaderQos = new DataReaderQosImpl(environment);
        
		jnaSubscriber = DdscLibrary.dds_create_subscriber(
				parent.getJnaParticipant(), 
				Utilities.convert(qos),
				Utilities.convert(parent));
        
        /* TODO FRCYC
        SubscriberQos oldQos;

        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied SubscriberQos is null.");
        }

        try {
            oldQos = ((SubscriberQosImpl) qos).convert();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Cannot create Subscribe with non-OpenSplice qos");
        }

        if (listener != null) {
            this.listener = new SubscriberListenerImpl(this.environment, this,
                    listener, true);
        } else {
            this.listener = null;
        }
        
        Subscriber old = this.parent.getOld().create_subscriber(oldQos,
                this.listener,
                StatusConverter.convertMask(this.environment, statuses));

        if (old == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        this.setOld(old);
        
        this.readers = new HashMap<DataReader, AbstractDataReader<?>>();
        this.isBuiltin = false;

        if (this.listener != null) {
            this.listener.setInitialised();
        }
        */
    }

    public SubscriberImpl(CycloneServiceEnvironment environment,
            DomainParticipantImpl parent, Subscriber oldSubscriber) {
        super(environment);

        if (oldSubscriber == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Supplied Subscriber is invalid (null).");
        }
        this.listener = null;
        //TODO FRCYC this.setOld(oldSubscriber);
        //this.readers = new HashMap<DataReader, AbstractDataReader<?>>();
        //this.isBuiltin = true;
    }
    
    public boolean isBuiltin() {
        return false;//this.isBuiltin;
    }

    private void setListener(SubscriberListener listener, int mask) {
        SubscriberListenerImpl wrapperListener;
        int rc;

        if (listener != null) {
            wrapperListener = new SubscriberListenerImpl(this.environment,
                    this, listener);
        } else {
            wrapperListener = null;
        }
        /* FRCYC
        rc = this.getOld().set_listener(wrapperListener, mask);
        Utilities.checkReturnCode(rc, this.environment,
                "Subscriber.setListener() failed.");

        this.listener = wrapperListener;
        */
    }

    @Override
    public void setListener(SubscriberListener listener) {
        //TODO FRCYC this.setListener(listener, StatusConverter.getAnyMask());
    }

    @Override
    public void setListener(SubscriberListener listener,
            Collection<Class<? extends Status>> statuses) {
    	//TODO FRCYC this.setListener(listener,
        //        StatusConverter.convertMask(this.environment, statuses));
    }

    @Override
    public void setListener(SubscriberListener listener,
            Class<? extends Status>... statuses) {
    	//TODO FRCYC this.setListener(listener,
                //StatusConverter.convertMask(this.environment, statuses));
    }

    @Override
    public SubscriberQos getQos() {
        /* TODO FRCYC
         * SubscriberQosHolder holder = new SubscriberQosHolder();
        int rc = this.getOld().get_qos(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "Subscriber.getQos() failed.");

        return SubscriberQosImpl.convert(this.environment, holder.value);
        */
    	return null;
    }

    @Override
    public void setQos(SubscriberQos qos) {
        SubscriberQosImpl q;

        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied SubscriberQos is null.");
        }
        try {
            q = (SubscriberQosImpl) qos;
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Setting non-OpenSplice Qos not supported.");
        }
        /* TODO FRCYC
        int rc = this.getOld().set_qos(q.convert());
        Utilities.checkReturnCode(rc, this.environment,
                "Subscriber.setQos() failed.");
                */

    }

    @Override
    public <TYPE> DataReader<TYPE> createDataReader(TopicDescription<TYPE> topic) {
        return this.createDataReader(topic, this.getDefaultDataReaderQos(),
                null, new HashSet<Class<? extends Status>>());
    }

    @Override
    public <TYPE> DataReader<TYPE> createDataReader(
            TopicDescription<TYPE> topic, 
            DataReaderQos qos,
            DataReaderListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {

        if(closed ) {
        	throw new AlreadyClosedExceptionImpl(environment, "Subscriber closed, can't create DataReader");
        }
        
        if(qos == null) {
        	qos = getDefaultDataReaderQos();
        }
        
        AbstractTypeSupport<TYPE> typeSupport = (AbstractTypeSupport<TYPE>) topic.getTypeSupport();
        AbstractDataReader<TYPE> dataReader = typeSupport.createDataReader(this, (TopicImpl<TYPE>)topic, qos, listener, statuses);
        readers.add(dataReader);
        if(this.qos.getEntityFactory().isAutoEnableCreatedEntities() && this.enabled  ) {
        	dataReader.enable();
        }
        return dataReader;
        
        /*
        if (topic == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied Topic is null.");
        }
        synchronized (this.readers) {
            try {
                typeSupport = (AbstractTypeSupport<TYPE>) topic
                        .getTypeSupport();
                reader = typeSupport.createDataReader(this,
                        (TopicDescriptionExt<TYPE>) topic, qos, listener,
                        statuses);
                //TODO FRCYC this.readers.put(reader.getOld(), reader);
            } catch (ClassCastException e) {
                throw new IllegalArgumentExceptionImpl(this.environment,
                        "Cannot create DataReader with non-OpenSplice Topic");
            }
        }
        return reader;
        */        
    }

    @Override
    public <TYPE> DataReader<TYPE> createDataReader(
            TopicDescription<TYPE> topic, DataReaderQos qos,
            DataReaderListener<TYPE> listener,
            Class<? extends Status>... statuses) {
        return this.createDataReader(topic, qos, listener,
                Arrays.asList(statuses));
    }

    @Override
    public <TYPE> DataReader<TYPE> createDataReader( TopicDescription<TYPE> topic, DataReaderQos qos) {
        return createDataReader(topic, qos, null,
                new HashSet<Class<? extends Status>>());
    }

    @Override
    public <TYPE> DataReader<TYPE> lookupDataReader(String topicName) {
        /*
    	if (topicName == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied topicName is null.");
        }
        synchronized (this.readers) {
            for (DataReader<?> reader : this.readers.values()) {
                if (topicName.equals(reader.getTopicDescription().getName())) {
                    try {
                        return reader.cast();
                    } catch (ClassCastException e) {
                        throw new IllegalOperationExceptionImpl(
                                this.environment,
                                "Cannot cast DataReader to desired type.");
                    }
                }
            }
            /*TODO FRCYC DataReader builtinReader = this.getOld().lookup_datareader(topicName);

            if (builtinReader != null) {
                return this.initBuiltinReader(builtinReader);
            }
            
        }*/
        return null;
    }

    @Override
    public <TYPE> DataReader<TYPE> lookupDataReader(
            TopicDescription<TYPE> topicDescription) {
        /*
    	if (topicDescription == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied topicName is null.");
        }
        synchronized (this.readers) {
            for (DataReader<?> reader : this.readers.values()) {
                if (topicDescription.equals(reader.getTopicDescription())) {
                    try {
                        return reader.cast();
                    } catch (ClassCastException e) {
                        throw new IllegalOperationExceptionImpl(
                                this.environment,
                                "Cannot cast DataReader to desired type.");
                    }
                }
            }
            /* TODO FRCYC
            DataReader builtinReader = this.getOld()
                    .lookup_datareader(topicDescription.getName());

            if (builtinReader != null) {
                return this.initBuiltinReader(builtinReader, topicDescription);
            }
            
        }
        */
        return null;
    }

    private <TYPE> DataReaderImpl<TYPE> initBuiltinReader(
            DataReader oldBuiltin) {
        DataReaderImpl<TYPE> result = null;

        if (oldBuiltin != null) {
            /*TODO FRCYC TopicDescription classicTopicDescription = oldBuiltin
                    .get_topicdescription();
                    

            if (classicTopicDescription != null) {
                TopicDescription<TYPE> td = this.getParent()
                        .lookupTopicDescription(
                                classicTopicDescription.get_name());

                if (td != null) {
                    result = this.initBuiltinReader(oldBuiltin, td);
                }
            } else {
                throw new DDSExceptionImpl(this.environment,
                        "Classic DataReader has no TopicDescription.");
            }
            */
        }
        return result;
    }

    private <TYPE> DataReaderImpl<TYPE> initBuiltinReader(
            DataReader oldBuiltin, TopicDescription<TYPE> td) {
        DataReaderImpl<TYPE> result = null;
        /* TODO FRCYC
        if (oldBuiltin != null) {
            result = new DataReaderImpl<TYPE>(this.environment, this,
                    (TopicDescriptionExt<TYPE>) td, oldBuiltin);
            synchronized (this.readers) {
                this.readers.put(result.getOld(), result);
            }
        }
        return result;
        */
        return null;
    }

    public <TYPE> DataReader<TYPE> lookupDataReader(DataReader old) {
        DataReader<TYPE> result;
        /*
        synchronized (this.readers) {
            AbstractDataReader<?> found = this.readers.get(old);

            if (found != null) {
                result = found.cast();
            } else if (this.isBuiltin) {
                result = this.initBuiltinReader(old);
            } else {
                result = null;
            }
        }*/
        return result = null;
    }

    @Override
    public void closeContainedEntities() {
        /*synchronized (this.readers) {
            HashMap<DataReader, AbstractDataReader<?>> copyReaders = new HashMap<DataReader, AbstractDataReader<?>>(this.readers);
            for (AbstractDataReader<?> reader : copyReaders.values()) {
                try {
                    reader.close();
                } catch (AlreadyClosedException a) {
                    /* Entity may be closed concurrently by application 
                }
            }
        }
        */
    }

    public Collection<DataReader<?>> getDataReaders(
            Collection<DataReader<?>> readers) {
    	/* TODO FRCYC
        DataReaderSeqHolder oldReaders = new DataReaderSeqHolder();

        synchronized (this.readers) {
            int rc = this.getOld().get_datareaders(oldReaders,
                    ANY_SAMPLE_STATE.value, ANY_VIEW_STATE.value,
                    ANY_INSTANCE_STATE.value);
            Utilities.checkReturnCode(rc, this.environment,
                    "Subscriber.getDataReaders() failed.");

            for (DataReader oldReader : oldReaders.value) {
                readers.add(this.readers.get(oldReader));
            }
        }
        return readers;
        */ 
    	return null;
    }

    @Override
    public Collection<DataReader<?>> getDataReaders() {
        List<DataReader<?>> readers = new ArrayList<DataReader<?>>();
        
        /* TODO FRCYC
        DataReaderSeqHolder oldReaders = new DataReaderSeqHolder();

        synchronized (this.readers) {
            int rc = this.getOld().get_datareaders(oldReaders,
                    ANY_SAMPLE_STATE.value, ANY_VIEW_STATE.value,
                    ANY_INSTANCE_STATE.value);
            Utilities.checkReturnCode(rc, this.environment,
                    "Subscriber.getDataReaders() failed.");

            for (DataReader oldReader : oldReaders.value) {
                readers.add(this.readers.get(oldReader));
            }
        }*/
        return readers;
    }
    @Override
    public Collection<DataReader<?>> getDataReaders(DataState dataState) {
        if (dataState == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DataState is null.");
        }
        List<DataReader<?>> readers = new ArrayList<DataReader<?>>();
        /* TODO FRCYC
        DataReaderSeqHolder oldReaders = new DataReaderSeqHolder();

        try {
            DataStateImpl state = (DataStateImpl) dataState;

            synchronized (this.readers) {
                int rc = this.getOld().get_datareaders(oldReaders,
                        state.getOldSampleState(), state.getOldViewState(),
                        state.getOldInstanceState());
                Utilities.checkReturnCode(rc, this.environment,
                        "Subscriber.getDataReaders() failed.");

                for (DataReader oldReader : oldReaders.value) {
                    readers.add(this.readers.get(oldReader));
                }
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Non-OpenSplice DataState implementation not supported.");
        }
        */
        return readers;
    }

    /* TODO FRCYC
    @Override
    public void notifyDataReaders() {
        int rc = this.getOld().notify_datareaders();
        Utilities.checkReturnCode(rc, this.environment,
                "Subscriber.notifyDataReaders() failed.");

    }

    @Override
    public void beginAccess() {
        int rc = this.getOld().begin_access();
        Utilities.checkReturnCode(rc, this.environment,
                "Subscriber.beginAccess() failed.");
    }

    @Override
    public void endAccess() {
        int rc = this.getOld().end_access();
        Utilities.checkReturnCode(rc, this.environment,
                "Subscriber.endAccess() failed.");
    }

    @Override
    public DataReaderQos getDefaultDataReaderQos() {
        DataReaderQosHolder holder = new DataReaderQosHolder();
        int rc = this.getOld().get_default_datareader_qos(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "Subscriber.getDefaultDataReaderQos() failed.");
        return DataReaderQosImpl.convert(this.environment, holder.value);
    }

    @Override
    public void setDefaultDataReaderQos(DataReaderQos qos) {
        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DataReaderQoS is null.");
        }
        try {
            this.getOld().set_default_datareader_qos(
                    ((DataReaderQosImpl) qos)
                    .convert());
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Non-OpenSplice DataReaderQos not supported.");
        }
    }
    */

    @Override
    public DataReaderQos copyFromTopicQos(DataReaderQos drQos, TopicQos tQos) {
        DataReaderQosImpl result;

        if (tQos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied TopicQos is null.");
        }
        if (drQos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DataReaderQos is null.");
        }
        try {
            result = (DataReaderQosImpl) drQos;
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Non-OpenSplice DataReaderQos not supported.");
        }
        result.mergeTopicQos(tQos);

        return result;
    }

    @Override
    public StatusCondition<Subscriber> getStatusCondition() {
        /* TODO FRCYC StatusCondition oldCondition = this.getOld().get_statuscondition();

        if (oldCondition == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        return new StatusConditionImpl<Subscriber>(this.environment,
                oldCondition, this);
                */
    	return null;
    }

    @Override
    public org.omg.dds.domain.DomainParticipant getParent() {
        return null; //TODO FRCYC this.parent;
    }

    @Override
    public DataState createDataState() {
        return new DataStateImpl(this.environment);
    }

    /* TODO FRCYC
    @Override
    protected void destroy() {
        this.closeContainedEntities();
        this.parent.destroySubscriber(this);
    }
     */
    public void destroyDataReader(AbstractDataReader<?> dataReader) {
        /* TODO FRCYC
    	DataReader old = dataReader.getOld();
        old.delete_contained_entities();
        int rc = this.getOld().delete_datareader(old);
        synchronized (this.readers) {
            this.readers.remove(old);
        }
        Utilities.checkReturnCode(rc, this.environment,
                "DataReader.close() failed.");
                */
    }
    

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyDataReaders() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beginAccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endAccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DataReaderQos getDefaultDataReaderQos() {
		return defaultDataReaderQos;
	}

	@Override
	public void setDefaultDataReaderQos(DataReaderQos qos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void destroy() {
		// TODO Auto-generated method stub
		
	}

	public int getJnaSubscriber() {
		return jnaSubscriber;
	}
	
	
}