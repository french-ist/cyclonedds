/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.nio.*;

import org.eclipse.cyclonedds.helper.NativeSize;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.domain.DomainParticipantFactoryQos;
import org.omg.dds.domain.DomainParticipantListener;
import org.omg.dds.domain.DomainParticipantQos;

import com.sun.jna.ptr.PointerByReference;

import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalOperationExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.Utilities;

/*
 TODO FRCYC
import org.eclipse.cyclonedds.ddsc.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.NativeSize;
import org.eclipse.cyclonedds.ddsc.DdscLibrary.*;
*/

public class DomainParticipantFactoryImpl extends DomainParticipantFactory {
    private CycloneServiceEnvironment environment;
    private DomainParticipantFactoryQos qos;
    private DomainParticipantQos defaultDomainParticipantQoS;

    public DomainParticipantFactoryImpl(CycloneServiceEnvironment environment) {        
        defaultDomainParticipantQoS = new DomainParticipantQosImpl(environment);
        qos = new DomainParticipantFactoryQosImpl(environment);
        this.environment = environment;
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public DomainParticipant createParticipant() {
        return createParticipant(0);
    }

    @Override
    public DomainParticipant createParticipant(int domainId) {
        return createParticipant(domainId, this.getDefaultParticipantQos(), null,
                new HashSet<Class<? extends Status>>());
    }

    @Override
    public DomainParticipant createParticipant(int domainId, DomainParticipantQos qos,
            DomainParticipantListener listener, Collection<Class<? extends Status>> statuses) {
        DomainParticipantImpl participant;
        participant = new DomainParticipantImpl(this.environment, this, domainId, qos, listener, statuses);
        return participant;
    }

    @Override
    public DomainParticipant createParticipant(int domainId, DomainParticipantQos qos,
            DomainParticipantListener listener, Class<? extends Status>... statuses) {
        return createParticipant(domainId, qos, listener, Arrays.asList(statuses));
    }

	@Override
	public DomainParticipant lookupParticipant(int domainId) {
        DomainParticipantImpl participant;
        NativeSize size = new NativeSize(1);
        IntBuffer participants = IntBuffer.allocate(NativeSize.SIZE);
        int rc = org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_lookup_participant(domainId, participants, size);
        Utilities.checkReturnCode(rc, this.environment,
                "DomainParticipantFactoryImpl.lookupParticipant() failed.");

        // TODO Retrieve the participant from participants
        participant = null;
        return participant;
	}

	@Override
	public DomainParticipantFactoryQos getQos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQos(DomainParticipantFactoryQos qos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefaultParticipantQos(DomainParticipantQos qos) {
		// TODO Auto-generated method stub
		
	}

    /* TODO FRCYC
    @Override
    public DomainParticipant lookupParticipant(int domainId) {
        DomainParticipantImpl participant;
        dds_entity_t participants = new dds_entity_t();
        NativeSize size = new NativeSize();
        dds_return_t rc = org.eclipse.cyclonedds.ddsc.dds.dds_lookup_participant(domainId, participants, size);
        Utilities.checkReturnCode(rc.getPointer().getInt(0), this.environment,
                "DomainParticipantFactoryImpl.lookupParticipant() failed.");

        if (rc.getPointer().getInt(0) == 0)
            return null;
        else {
            // TODO retrieve the participant from participants
            participant = null;
            return participant;
        }
    }

    @Override
    public DomainParticipantFactoryQos getQos() {
        public static native DdscLibrary.dds_return_t dds_get_qos(DdscLibrary.dds_entity_t entity, DdscLibrary.dds_qos_t qos);
 
        DomainParticipantFactoryQosHolder holder = new DomainParticipantFactoryQosHolder();
        DdscLibrary.dds_return_t rc = dds_get_qos(DdscLibrary.dds_entity_t entity, qos);

        Utilities.checkReturnCode(rc.getPointer().getInt(0), this.environment, "DomainParticipantFactory.getQos() failed.");
        return qos;
    }

    @Override
    public void setQos(DomainParticipantFactoryQos qos) {
        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment, "Supplied DomainParticipantFactoryQos is null.");
        }
        if (qos instanceof DomainParticipantFactoryQosImpl) {
            DdscLibrary.dds_return_t rc = dds_set_qos(DdscLibrary.dds_entity_t entity, qos);
            Utilities.checkReturnCode(rc.getPointer().getInt(0), this.environment, "DomainParticipantFactory.setQos() failed.");
        } else {
            throw new IllegalOperationExceptionImpl(this.environment,
                    "DomainParticipantFactoryQos not supplied by Cyclone Service provider.");
        }
    }*/

    @Override
    public DomainParticipantQos getDefaultParticipantQos() {
        DomainParticipantQos qos;
        PointerByReference rc =  org.eclipse.cyclonedds.ddsc.dds_public_qos.DdscLibrary.dds_create_qos();
        Utilities.checkReturnCode(rc, this.environment, "DomainParticipantFactory.getDefaultParticipantQos() failed.");
        qos = DomainParticipantQosImpl.convert(this.environment, rc);
        return qos;
    }
    
    /*
    @Override
    public void setDefaultParticipantQos(DomainParticipantQos qos) {
        DomainParticipantQos oldQos;
        int rc;

        if (qos == null) {
            throw new IllegalArgumentExceptionImpl(this.environment, "Supplied DomainParticipantQos is null.");
        }
        oldQos = ((DomainParticipantQosImpl) qos).convert();
        rc = this.factory.set_default_participant_qos(oldQos);
        Utilities.checkReturnCode(rc, this.environment, "DomainParticipantFactory.setDefaultParticipantQos() failed.");

    }
    */

}
