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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.omg.dds.sub.Sample;
import org.eclipse.cyclonedds.core.DDSExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.type.TypeSupportProtobuf;

//TODO FRCYC import SampleInfoSeqHolder;

public class PreAllocatorProtobuf<PROTOBUF_TYPE, DDS_TYPE> implements
        PreAllocator<PROTOBUF_TYPE> {
    private final CycloneServiceEnvironment environment;
    private final Class<?> dataSeqHolderClaz;
    private final Field dataSeqHolderValueField;
    private final TypeSupportProtobuf<PROTOBUF_TYPE, DDS_TYPE> typeSupport;

    private SampleInfoSeqHolder infoSeqHolder;
    private Object dataSeqHolder;
    private List<Sample<PROTOBUF_TYPE>> sampleList;

    @SuppressWarnings("unchecked")
    public PreAllocatorProtobuf(CycloneServiceEnvironment environment,
            DataReaderProtobuf<PROTOBUF_TYPE, DDS_TYPE> reader,
            Class<?> dataSeqHolderClaz, Field dataSeqHolderValueField,
            List<Sample<PROTOBUF_TYPE>> preAllocated) {
        this.environment = environment;
        this.sampleList = preAllocated;
        this.dataSeqHolderClaz = dataSeqHolderClaz;
        this.dataSeqHolderValueField = dataSeqHolderValueField;
        this.typeSupport = (TypeSupportProtobuf<PROTOBUF_TYPE, DDS_TYPE>) reader
                .getTopicDescription().getTypeSupport();
        this.setSampleList(preAllocated);
    }

    @Override
    public void setSampleList(List<Sample<PROTOBUF_TYPE>> preAllocated) {

        try {
            if (preAllocated == null) {
                this.sampleList = new ArrayList<Sample<PROTOBUF_TYPE>>();
            } else {
                this.sampleList = preAllocated;
                this.sampleList.clear();
            }
            this.infoSeqHolder = new SampleInfoSeqHolder();
            this.dataSeqHolder = dataSeqHolderClaz.newInstance();
        } catch (InstantiationException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (ClassCastException ce) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Usage of non-OpenSplice Sample implementation is not supported.");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateReferences() {
        try {
            Object dataValue = this.dataSeqHolderValueField
                    .get(this.dataSeqHolder);
            /* TODO FRCYC
            for (int i = 0; i < this.infoSeqHolder.value.length; i++) {
                if (this.infoSeqHolder.value[i].valid_data) {
                    this.sampleList.add(new SampleImpl<PROTOBUF_TYPE>(
                        this.environment,
                        this.typeSupport.ddsToProtobuf((DDS_TYPE)Array.get(dataValue, i)),
                        this.infoSeqHolder.value[i]));
                } else {
                    this.sampleList.add(new SampleImpl<PROTOBUF_TYPE>(
                        this.environment,
                        this.typeSupport.ddsKeyToProtobuf((DDS_TYPE)Array.get(dataValue, i)),
                        this.infoSeqHolder.value[i]));
                }
            }
            */

        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(this.environment, "Internal error ("
                    + e.getMessage() + ").");
        }
    }

    @Override
    public Object getDataSeqHolder() {
        return this.dataSeqHolder;
    }

    @Override
    public SampleInfoSeqHolder getInfoSeqHolder() {
        return this.infoSeqHolder;
    }

    @Override
    public List<Sample<PROTOBUF_TYPE>> getSampleList() {
        return this.sampleList;
    }

}
