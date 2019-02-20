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
package org.eclipse.cyclonedds.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.domain.DomainParticipantFactoryQos;
import org.omg.dds.domain.DomainParticipantListener;
import org.omg.dds.domain.DomainParticipantQos;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalOperationExceptionImpl;
import org.eclipse.cyclonedds.core.OsplServiceEnvironment;
import org.eclipse.cyclonedds.core.Utilities;

/*
 TODO FRCYC
import org.eclipse.cyclonedds.ddsc.DdscLibrary;
import org.eclipse.cyclonedds.ddsc.NativeSize;
import org.eclipse.cyclonedds.ddsc.DdscLibrary.*;
*/

public class DomainParticipantFactoryImpl extends DomainParticipantFactory
        implements org.eclipse.cyclonedds.domain.DomainParticipantFactory {
    private OsplServiceEnvironment environment;
    private DomainParticipantFactoryQos qos;
    private DomainParticipantQos defaultDomainParticipantQoS;

    public DomainParticipantFactoryImpl(OsplServiceEnvironment environment) {
        this.environment = environment;
        this.defaultDomainParticipantQoS = new DomainParticipantQosImpl(environment);
        this.qos = new DomainParticipantFactoryQosImpl(environment);
    }

    public void destroyParticipant(DomainParticipantImpl participant) {
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public DomainParticipant createParticipant() {
        // return createParticipant(DOMAIN_ID_DEFAULT.value);
        return createParticipant(0);
    }

    @Override
    public DomainParticipant createParticipant(int domainId) {
        return this.createParticipant(domainId, this.getDefaultParticipantQos(), null,
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
	public void detachAllDomains(boolean blockOperations, boolean deleteEntities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DomainParticipant lookupParticipant(int domainId) {
		// TODO Auto-generated method stub
		return null;
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
	public DomainParticipantQos getDefaultParticipantQos() {
		// TODO Auto-generated method stub
		return null;
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
        dds_return_t rc = org.eclipse.cyclonedds.ddsc.DdscLibrary.dds_lookup_participant(domainId, participants, size);
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
    }

    @Override
    public DomainParticipantQos getDefaultParticipantQos() {
        DomainParticipantQos qos;
        DomainParticipantQosHolder holder;
        int rc;

        holder = new DomainParticipantQosHolder();
        rc = this.factory.get_default_participant_qos(holder);
        Utilities.checkReturnCode(rc, this.environment, "DomainParticipantFactory.getDefaultParticipantQos() failed.");

        qos = DomainParticipantQosImpl.convert(this.environment, holder.value);

        return qos;
    }

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

    @Override
    public void detachAllDomains(boolean blockOperations, boolean deleteEntities) {
        this.factory.detach_all_domains(blockOperations, deleteEntities);
    }
    */

}
