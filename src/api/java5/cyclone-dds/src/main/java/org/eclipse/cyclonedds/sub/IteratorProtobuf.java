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
import java.util.ArrayList;

import org.omg.dds.sub.Sample;
import org.eclipse.cyclonedds.core.DDSExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.type.TypeSupportProtobuf;

//TODO FRCYC import SampleInfoSeqHolder;

public class IteratorProtobuf<PROTOBUF_TYPE, DDS_TYPE> extends
        AbstractIterator<PROTOBUF_TYPE> implements
        Sample.Iterator<PROTOBUF_TYPE> {
    private ArrayList<PROTOBUF_TYPE> data;

    public IteratorProtobuf(CycloneServiceEnvironment environment,
            DataReaderProtobuf<PROTOBUF_TYPE, DDS_TYPE> reader,
            Object sampleSeqHolder, Field dataSeqHolderValue,
            SampleInfoSeqHolder infoSeqHolder) {
        super(environment, reader, sampleSeqHolder, dataSeqHolderValue,
                infoSeqHolder);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SampleImpl<PROTOBUF_TYPE>[] setupSampleList() {
        try {
            TypeSupportProtobuf<PROTOBUF_TYPE, DDS_TYPE> typeSupport;
            DDS_TYPE[] ddsData;

            typeSupport = (TypeSupportProtobuf<PROTOBUF_TYPE, DDS_TYPE>) this.reader
                    .getTopicDescription().getTypeSupport();
            ddsData = (DDS_TYPE[]) dataSeqHolderValue.get(sampleSeqHolder);

            this.data = new ArrayList<PROTOBUF_TYPE>(ddsData.length);

            for (int i = 0; i < ddsData.length; i++) {
                /* TODO FRCYC
            	if (this.infoSeqHolder.value[i].valid_data) {
                    this.data.add(typeSupport.ddsToProtobuf(ddsData[i]));
                } else {
                    this.data.add(typeSupport.ddsKeyToProtobuf(ddsData[i]));
                }
                */
            }
        } catch (SecurityException e) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Not allowed to access field "
                            + dataSeqHolderValue.getName() + " in "
                            + dataSeqHolderValue.getClass().getName() + "("
                            + e.getMessage() + ").");
        } catch (IllegalArgumentException e) {
            throw new DDSExceptionImpl(environment, "Cannot find "
                    + dataSeqHolderValue.getName() + " in "
                    + dataSeqHolderValue.getClass().getName() + "("
                    + e.getMessage() + ").");
        } catch (IllegalAccessException e) {
            throw new DDSExceptionImpl(environment, "No access to field "
                    + dataSeqHolderValue.getName() + " in "
                    + dataSeqHolderValue.getClass().getName() + "("
                    + e.getMessage() + ").");
        }
        return new SampleImpl[this.data.size()];
    }

    @Override
    protected PROTOBUF_TYPE getData(int index) {
        return this.data.get(index);
    }
}
