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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.status.Status;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Sample.Iterator;
import org.omg.dds.topic.TopicDescription;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.core.JnaData;
import org.eclipse.cyclonedds.core.PreconditionNotMetExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.DurationImpl;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.core.status.StatusConverter;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.dds.dds_sample_info;
import org.eclipse.cyclonedds.helloworld.HelloWorldData_Msg;
import org.eclipse.cyclonedds.helper.NativeSize;
import org.eclipse.cyclonedds.topic.DdsTopicDescriptor;
import org.eclipse.cyclonedds.topic.TopicDescriptionExt;
import org.eclipse.cyclonedds.topic.TopicImpl;

public class DataReaderImpl<TYPE> extends AbstractDataReader<TYPE> {
	private boolean enable = true;
	private SubscriberImpl parent;
	private InstanceHandleImpl handle;
	private long jnaDataReader;
	private DataReaderQos qos;
	private TopicImpl<TYPE> topic;
	private JnaData jnaObjectInstance;

	@SuppressWarnings("unchecked")
	public DataReaderImpl(CycloneServiceEnvironment environment, 
			SubscriberImpl parent, 
			TopicImpl<TYPE> topic,
			DataReaderQos qos, 
			DataReaderListener<TYPE> listener, 
			Collection<Class<? extends Status>> statuses) {
		super(environment, parent);
		this.parent = parent;
		this.topic = topic;
		this.qos = qos;
		this.listener = (DataReaderListenerImpl<TYPE>) listener;

		jnaDataReader = DdscLibrary.dds_create_reader (parent.getJnaSubscriber(), 
				topic.getJnaTopic(), 
				Utilities.convert(qos), 
				Utilities.convert(listener));
		
		jnaObjectInstance = (JnaData)topic.getGenericTypeInstance();
		
		handle = new  InstanceHandleImpl(environment, jnaDataReader);
	}
	
	@Override
	public List<Sample<TYPE>> take(List<Sample<TYPE>> samples) {
		if(jnaDataReader <= 0 ) {
			throw new PreconditionNotMetExceptionImpl(environment, "DataReader not ready");
		}
		int NB_SAMPLES = 128;
		NativeSize bufferSize = new NativeSize(NB_SAMPLES); //helper.getNativeSize("HelloWorldData_Msg");
		Pointer bufferAllocation = org.eclipse.cyclonedds.ddsc.dds_public_alloc.DdscLibrary.dds_alloc( new NativeSize(NB_SAMPLES*48)); //48 = helper.getNativeSize("HelloWorldData_Msg");			
		PointerByReference buffer = new PointerByReference(bufferAllocation);
		dds_sample_info.ByReference sampleInforRef = new  dds_sample_info.ByReference();		
		dds_sample_info[] sampleInfosArray = (dds_sample_info[]) sampleInforRef.toArray(NB_SAMPLES);
		
		int retCode = DdscLibrary.dds_take((int)jnaDataReader, buffer, sampleInforRef, bufferSize, NB_SAMPLES);
				
		//Structure  arrayMsgRef = new Structure(buf.getValue());
		Structure arrayMsgRef = jnaObjectInstance.getNewStructureFrom(buffer.getValue());
        arrayMsgRef.read();
        sampleInforRef.read();
		
        if(retCode > 0 && sampleInfosArray[0].getValid_data() > 0) {        	
        	TYPE[] samplesArray = (TYPE[]) arrayMsgRef.toArray(NB_SAMPLES);
        	for(int i=0;i<NB_SAMPLES;i++) {
        		SampleInfo sampleInfo = new SampleInfo();
        		
        		sampleInfo.valid_data = sampleInfosArray[i].getValid_data()==0 ? false:true;
        		sampleInfo.sample_state = sampleInfosArray[i].getSample_state();
        		sampleInfo.view_state = sampleInfosArray[i].getView_state();
        		sampleInfo.instance_state = sampleInfosArray[i].getInstance_state();
        		sampleInfo.source_timestamp = new DurationImpl(environment, sampleInfosArray[i].getSource_timestamp(), TimeUnit.NANOSECONDS);
        		sampleInfo.instance_handle = sampleInfosArray[i].getInstance_handle();
        		sampleInfo.publication_handle = sampleInfosArray[i].getPublication_handle();
        		sampleInfo.disposed_generation_count = sampleInfosArray[i].getDisposed_generation_count();
        		sampleInfo.no_writers_generation_count = sampleInfosArray[i].getDisposed_generation_count();
        		sampleInfo.sample_rank = sampleInfosArray[i].getSample_rank();
        		sampleInfo.generation_rank = sampleInfosArray[i].getGeneration_rank();
        		sampleInfo.absolute_generation_rank = sampleInfosArray[i].getAbsolute_generation_rank();
        		sampleInfo.reception_timestamp = new DurationImpl(environment, System.nanoTime(), TimeUnit.NANOSECONDS);
        		
				Sample<TYPE> sample = new SampleImpl<TYPE>(environment, samplesArray[i], sampleInfo );
        		samples.add(sample);
        	}
        }
        
		return samples;
	}

	@Override
	public TYPE getKeyValue(InstanceHandle arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TYPE getKeyValue(TYPE arg0, InstanceHandle arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicDescription<TYPE> getTopicDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle lookupInstance(TYPE arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean readNextSample(Sample<TYPE> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean takeNextSample(Sample<TYPE> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ReflectionDataReader<?, TYPE> getReflectionReader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreAllocator<TYPE> getPreAllocator(List<Sample<TYPE>> samples, Class<?> sampleSeqHolderClz,
			Field sampleSeqHolderValueField) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* 
    public DataReaderImpl(CycloneServiceEnvironment environment,
            SubscriberImpl parent, 
            DataReaderQos qos, 
            DataReaderListener<TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
    	super(environment, parent);

        
        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DataReaderQos is null.");
        }
        if (topicDescription == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied TopicDescription is null.");
        }

        DataReaderQos oldQos;

        try {
            oldQos = ((DataReaderQosImpl) qos).convert();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Cannot create DataReader with non-OpenSplice qos");
        }

        if (listener != null) {
            this.listener = new DataReaderListenerImpl<TYPE>(this.environment,
                    this, listener, true);
        } else {
            this.listener = null;
        }
        TODO FRCYC
        DataReader old = this.parent.getOld().create_datareader(
                topicDescription.getOld(), oldQos, this.listener,
                StatusConverter.convertMask(this.environment, statuses));

        if (old == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        
        this.setOld(old);
        
        this.reflectionReader = new ReflectionDataReader<TYPE, TYPE>(
                this.environment, this, topicDescription.getTypeSupport()
                        .getType());
        this.preallocteList = new ArrayList<PreAllocatorImpl<TYPE>>();
        this.topicDescription.retain();

        if (this.listener != null) {
            this.listener.setInitialised();
        }
    }

    /*
    @Override
    protected void destroy() {
        super.destroy();
        this.topicDescription.close();
    }

    
    @Override
    protected ReflectionDataReader<?, TYPE> getReflectionReader() {
        return this.reflectionReader;
    }

    @Override
    public PreAllocator<TYPE> getPreAllocator(List<Sample<TYPE>> samples,
            Class<?> sampleSeqHolderClz, Field sampleSeqHolderValueField) {
        PreAllocatorImpl<TYPE> pa = null;

        synchronized (this.preallocteList) {
            if (samples != null) {
                for (PreAllocatorImpl<TYPE> paImpl : this.preallocteList) {
                    if (paImpl.getSampleList() == samples) {
                        pa = paImpl;
                        break;
                    }
                }
            }
            if (pa == null) {
                pa = new PreAllocatorImpl<TYPE>(this.environment,
                        sampleSeqHolderClz, sampleSeqHolderValueField, this
                                .getTopicDescription().getTypeSupport()
                                .getType(), samples);
                this.preallocteList.add(pa);
            } else {
                pa.setSampleList(samples);
            }
        }
        return pa;
    }

    @Override
    public TYPE getKeyValue(TYPE keyHolder, InstanceHandle handle) {
        return this.reflectionReader.getKeyValue(keyHolder, handle);
    }

    @Override
    public TYPE getKeyValue(InstanceHandle handle) {
        return this.reflectionReader.getKeyValue(handle);
    }

    @Override
    public InstanceHandle lookupInstance(TYPE keyHolder) {
        return this.reflectionReader.lookupInstance(keyHolder);
    }

    @Override
    public boolean readNextSample(Sample<TYPE> sample) {
        return this.reflectionReader.readNextSample((SampleImpl<TYPE>) sample);
    }

    @Override
    public boolean takeNextSample(Sample<TYPE> sample) {
        return this.reflectionReader.takeNextSample((SampleImpl<TYPE>) sample);
    }

    
    @Override
    public Iterator<TYPE> createIterator(Object sampleSeqHolder,
            Field sampleSeqHolderValueField, SampleInfoSeqHolder info) {
        return new IteratorImpl<TYPE>(this.environment, this, sampleSeqHolder,
                sampleSeqHolderValueField, info);
    }*/
	
}
