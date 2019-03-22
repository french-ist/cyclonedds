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
package org.eclipse.cyclonedds.type;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
import org.eclipse.cyclonedds.pub.DataWriterProtobuf;
import org.eclipse.cyclonedds.pub.PublisherImpl;
import org.eclipse.cyclonedds.sub.AbstractDataReader;
import org.eclipse.cyclonedds.sub.DataReaderProtobuf;
import org.eclipse.cyclonedds.sub.SubscriberImpl;
import org.eclipse.cyclonedds.topic.AbstractTopic;
import org.eclipse.cyclonedds.topic.TopicProtobuf;

//TODO FRCYC import TypeSupport;

public abstract class TypeSupportProtobuf<PROTOBUF_TYPE, DDS_TYPE> extends
        AbstractTypeSupport<PROTOBUF_TYPE> {
    protected final Class<PROTOBUF_TYPE> dataType;
    protected final CycloneServiceEnvironment environment;
    protected final TypeSupportImpl<DDS_TYPE> ddsTypeSupport;
    private final byte[] metaData;
    private final byte[] typeHash;
    private final byte[] extentions = null;

    protected TypeSupportProtobuf(CycloneServiceEnvironment environment,
            Class<PROTOBUF_TYPE> dataType,
            TypeSupportImpl<DDS_TYPE> ddsTypeSupport,
            final byte[] metaData,
            final byte[] metaHash) {
        this.environment = environment;
        this.dataType = dataType;
        this.metaData = metaData;
        this.typeHash = metaHash;
        this.ddsTypeSupport = ddsTypeSupport;

        /* TODO FRCYC
        org.eclipse.cyclonedds.dcps.TypeSupportImpl oldTypeSupport = (org.eclipse.cyclonedds.dcps.TypeSupportImpl)ddsTypeSupport.getOldTypeSupport();
        oldTypeSupport.set_data_representation_id(GPB_REPRESENTATION.value);
        
        oldTypeSupport.set_meta_data(this.metaData);
        oldTypeSupport.set_type_hash(this.typeHash);
        */
    }

    @SuppressWarnings("unchecked")
    public static <SOME_TYPE> org.omg.dds.type.TypeSupport<SOME_TYPE> getInstance(
            CycloneServiceEnvironment environment, Class<SOME_TYPE> dataType,
            String registeredName) {
        String typeSupportName = dataType.getName().replaceAll("\\$", "")
                + "TypeSupportProtobuf";

        try {
            Class<?> typeSupportClass = Class.forName(typeSupportName);
            Constructor<?> c = typeSupportClass.getConstructor(
                    CycloneServiceEnvironment.class, String.class);

            return (org.omg.dds.type.TypeSupport<SOME_TYPE>) c.newInstance(
                    environment, registeredName);

        } catch (ClassNotFoundException e) {
            throw new PreconditionNotMetExceptionImpl(environment,
                    "Allocating new TypeSupport failed (" + typeSupportName
                            + "); " + e.getMessage());
        } catch (InstantiationException e) {
            throw new PreconditionNotMetExceptionImpl(environment,
                    "Allocating new TypeSupport failed. " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new PreconditionNotMetExceptionImpl(environment,
                    "Allocating new TypeSupport failed. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new PreconditionNotMetExceptionImpl(environment,
                    "Allocating new TypeSupport failed. " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new PreconditionNotMetExceptionImpl(environment,
                    "Allocating new TypeSupport failed. " + e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new PreconditionNotMetExceptionImpl(environment,
                    "Allocating new TypeSupport failed. " + e.getMessage());
        } catch (SecurityException e) {
            throw new PreconditionNotMetExceptionImpl(environment,
                    "Allocating new TypeSupport failed. " + e.getMessage());
        }
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public PROTOBUF_TYPE newData() {
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
    public Class<PROTOBUF_TYPE> getType() {
        return this.dataType;
    }

    @Override
    public String getTypeName() {
        return this.ddsTypeSupport.getTypeName();
    }

    public TypeSupportImpl<DDS_TYPE> getTypeSupportStandard() {
        return this.ddsTypeSupport;
    }

    @Override
    public AbstractTopic<PROTOBUF_TYPE> createTopic(
            DomainParticipantImpl participant, String topicName, TopicQos qos,
            TopicListener<PROTOBUF_TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        return new TopicProtobuf<PROTOBUF_TYPE>(this.environment, participant,
                topicName, this, qos, listener, statuses);
    }

    @Override
    public AbstractDataWriter<PROTOBUF_TYPE> createDataWriter(
            PublisherImpl publisher, AbstractTopic<PROTOBUF_TYPE> topic,
            DataWriterQos qos, DataWriterListener<PROTOBUF_TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        return new DataWriterProtobuf<PROTOBUF_TYPE, DDS_TYPE>(
                this.environment, publisher,
                (TopicProtobuf<PROTOBUF_TYPE>) topic, qos, listener, statuses);
    }



    public byte[] getMetaDescriptor(){
        return this.metaData.clone();
    }

    public byte[] getMetaHash(){
        return this.typeHash.clone();
    }

    public byte[] getExtentions(){
        return this.extentions.clone();
    }

    protected static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public abstract PROTOBUF_TYPE ddsToProtobuf(DDS_TYPE ddsData);

    public abstract DDS_TYPE protobufToDds(PROTOBUF_TYPE protobufData);

    public abstract PROTOBUF_TYPE ddsKeyToProtobuf(DDS_TYPE ddsData);
}