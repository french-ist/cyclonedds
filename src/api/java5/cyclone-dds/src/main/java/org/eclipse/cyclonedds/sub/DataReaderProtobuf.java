/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.sub;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.status.Status;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Sample.Iterator;
import org.omg.dds.topic.TopicDescription;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.core.status.StatusConverter;
import org.eclipse.cyclonedds.topic.TopicDescriptionExt;
import org.eclipse.cyclonedds.type.TypeSupportProtobuf;


//TODO FRCYC //TODO FRCYC import SampleInfoSeqHolder;

public class DataReaderProtobuf<PROTOBUF_TYPE, DDS_TYPE> extends
        AbstractDataReader<PROTOBUF_TYPE> {
    //private final HashMap<List<Sample<PROTOBUF_TYPE>>, PreAllocatorProtobuf<PROTOBUF_TYPE, DDS_TYPE>> preallocated;
    //private final ReflectionDataReader<DDS_TYPE, PROTOBUF_TYPE> reflectionReader;
    //private final TypeSupportProtobuf<PROTOBUF_TYPE, DDS_TYPE> typeSupport;

    @SuppressWarnings("unchecked")
    public DataReaderProtobuf(CycloneServiceEnvironment environment,
            SubscriberImpl parent,
            DataReaderQos qos, DataReaderListener<PROTOBUF_TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        super(environment, parent);
        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied DataReaderQos is null.");
        }
        /*
        if (topicDescription == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Supplied TopicDescription is null.");
        }
        */
        DataReaderQos oldQos;

        try {
            oldQos = ((DataReaderQosImpl) qos).convert();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Cannot create DataReader with non-OpenSplice qos");
        }

        if (listener != null) {
            this.listener = new DataReaderListenerImpl<PROTOBUF_TYPE>(
                    this.environment, this, listener, true);
        } else {
            this.listener = null;
        }
        /* TODO FRCYC
        DataReader old = this.parent.getOld().create_datareader(
                topicDescription.getOld(), oldQos, this.listener,
                StatusConverter.convertMask(this.environment, statuses));

        if (old == null) {
            Utilities.throwLastErrorException(this.environment);
        }
        this.setOld(old);
       
        this.preallocated = new HashMap<List<Sample<PROTOBUF_TYPE>>, PreAllocatorProtobuf<PROTOBUF_TYPE, DDS_TYPE>>();
        this.typeSupport = (TypeSupportProtobuf<PROTOBUF_TYPE, DDS_TYPE>) topicDescription
                .getTypeSupport();
        this.reflectionReader = new ReflectionDataReader<DDS_TYPE, PROTOBUF_TYPE>(
                this.environment, this, this.typeSupport
                        .getTypeSupportStandard().getType());
        this.topicDescription.retain();

        if (this.listener != null) {
            this.listener.setInitialised();
        }
         */
    }

    /*
    @Override
    protected void destroy() {
        super.destroy();
        this.topicDescription.close();
    }

    @Override
    public PreAllocator<PROTOBUF_TYPE> getPreAllocator(
            List<Sample<PROTOBUF_TYPE>> samples, Class<?> sampleSeqHolderClz,
            Field sampleSeqHolderValueField) {
        PreAllocatorProtobuf<PROTOBUF_TYPE, DDS_TYPE> pa;

        synchronized (this.preallocated) {
            if (samples != null) {
                pa = this.preallocated.get(samples);
            } else {
                pa = null;
            }
            if (pa == null) {
                pa = new PreAllocatorProtobuf<PROTOBUF_TYPE, DDS_TYPE>(
                        this.environment, this, sampleSeqHolderClz,
                        sampleSeqHolderValueField, samples);
                this.preallocated.put(pa.getSampleList(), pa);
            } else {
                pa.setSampleList(samples);
            }
        }
        return pa;
    }
    

    @Override
    protected ReflectionDataReader<?, PROTOBUF_TYPE> getReflectionReader() {
        return this.reflectionReader;
    }

    @Override
    public PROTOBUF_TYPE getKeyValue(PROTOBUF_TYPE keyHolder,
            InstanceHandle handle) {
        return this.getKeyValue(handle);
    }

    @Override
    public PROTOBUF_TYPE getKeyValue(InstanceHandle handle) {
        DDS_TYPE ddsData = this.reflectionReader.getKeyValue(handle);

        if (ddsData != null) {
            return this.typeSupport.ddsKeyToProtobuf(ddsData);
        }
        return null;
    }

    @Override
    public InstanceHandle lookupInstance(PROTOBUF_TYPE keyHolder) {
        return this.reflectionReader.lookupInstance(this.typeSupport
                .protobufToDds(keyHolder));
    }

    @Override
    public boolean readNextSample(Sample<PROTOBUF_TYPE> sample) {
        SampleImpl<DDS_TYPE> ddsSample;
        boolean result = false;

        /* TODO FRCYC
        if (sample == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Provided an invalid null sample.");
        }
        ddsSample = new SampleImpl<DDS_TYPE>(this.environment,
                this.typeSupport.protobufToDds(sample.getData()),
                ((SampleImpl<PROTOBUF_TYPE>) sample).getInfo());
        result = this.reflectionReader.readNextSample(ddsSample);

        if (result == true) {
            if (ddsSample.getInfo().valid_data) {
                ((SampleImpl<PROTOBUF_TYPE>) sample).setContent(
                        this.typeSupport.ddsToProtobuf(ddsSample.getData()),
                        ddsSample.getInfo());
            } else {
                ((SampleImpl<PROTOBUF_TYPE>) sample).setContent(
                        this.typeSupport.ddsKeyToProtobuf(ddsSample
                                .getKeyValue()), ddsSample.getInfo());
            }
        }
        
        return result;
    }

    @Override
    public boolean takeNextSample(Sample<PROTOBUF_TYPE> sample) {
        SampleImpl<DDS_TYPE> ddsSample;
        boolean result = false;
        /* TODO FRCYC
        if (sample == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Provided an invalid null sample.");
        }
        ddsSample = new SampleImpl<DDS_TYPE>(this.environment,
                this.typeSupport.protobufToDds(sample.getData()),
                ((SampleImpl<PROTOBUF_TYPE>) sample).getInfo());
        result = this.reflectionReader.takeNextSample(ddsSample);

        if (result == true) {
            if (ddsSample.getInfo().valid_data) {
                ((SampleImpl<PROTOBUF_TYPE>) sample).setContent(
                        this.typeSupport.ddsToProtobuf(ddsSample.getData()),
                        ddsSample.getInfo());
            } else {
                ((SampleImpl<PROTOBUF_TYPE>) sample).setContent(
                        this.typeSupport.ddsKeyToProtobuf(ddsSample
                                .getKeyValue()), ddsSample.getInfo());
            }
        }
        return result;
    }
	*/
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
	public PROTOBUF_TYPE getKeyValue(InstanceHandle arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PROTOBUF_TYPE getKeyValue(PROTOBUF_TYPE arg0, InstanceHandle arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicDescription<PROTOBUF_TYPE> getTopicDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle lookupInstance(PROTOBUF_TYPE arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean readNextSample(Sample<PROTOBUF_TYPE> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean takeNextSample(Sample<PROTOBUF_TYPE> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ReflectionDataReader<?, PROTOBUF_TYPE> getReflectionReader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreAllocator<PROTOBUF_TYPE> getPreAllocator(List<Sample<PROTOBUF_TYPE>> samples, Class<?> sampleSeqHolderClz,
			Field sampleSeqHolderValueField) {
		// TODO Auto-generated method stub
		return null;
	}

}
