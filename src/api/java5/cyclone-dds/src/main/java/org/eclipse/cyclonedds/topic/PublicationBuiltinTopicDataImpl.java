/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.topic;

import java.util.List;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.DurabilityService;
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Lifespan;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.OwnershipStrength;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.TopicData;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.policy.TypeConsistencyEnforcement.Kind;
import org.omg.dds.topic.BuiltinTopicKey;
import org.omg.dds.topic.PublicationBuiltinTopicData;
import org.omg.dds.type.typeobject.TypeObject;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.UnsupportedOperationExceptionImpl;
import org.eclipse.cyclonedds.core.policy.DataRepresentationImpl;
import org.eclipse.cyclonedds.core.policy.PolicyConverter;
import org.eclipse.cyclonedds.core.policy.TypeConsistencyEnforcementImpl;

public class PublicationBuiltinTopicDataImpl implements
        PublicationBuiltinTopicData {
    private static final long serialVersionUID = -6604763092552114237L;
    private final transient ServiceEnvironmentImpl environment;
    private PublicationBuiltinTopicData old;
    private BuiltinTopicKey key;
    private BuiltinTopicKey participantKey;
    private final TypeConsistencyEnforcement typeConsistency;
    private final DataRepresentation dataRepresentation;

    public PublicationBuiltinTopicDataImpl(ServiceEnvironmentImpl environment,
            PublicationBuiltinTopicData old) {
        this.environment = environment;
        this.old = old;
      //TODO FRCYC this.key = new BuiltinTopicKeyImpl(this.environment, old.key);
      //TODO FRCYC  this.participantKey = new BuiltinTopicKeyImpl(this.environment,                old.participant_key);
        this.dataRepresentation = new DataRepresentationImpl(this.environment,
                DataRepresentation.Id.XCDR_DATA_REPRESENTATION);
        this.typeConsistency = new TypeConsistencyEnforcementImpl(
                this.environment, Kind.EXACT_TYPE_TYPE_CONSISTENCY);

    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public BuiltinTopicKey getKey() {
        return this.key;
    }

    @Override
    public BuiltinTopicKey getParticipantKey() {
        return this.participantKey;
    }

    @Override
    public String getTopicName() {
        //TODO FRCYC return old.topic_name;
    	return null;
    }

    @Override
    public String getTypeName() {
    	//TODO FRCYC return old.type_name;
    	return null;
    }

    @Override
    public List<String> getEquivalentTypeName() {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "PublicationBuiltinTopicData.getEquivalentTypeName() not supported.");
    }

    @Override
    public List<String> getBaseTypeName() {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "PublicationBuiltinTopicData.getBaseTypeName() not supported.");
    }

    @Override
    public TypeObject getType() {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "PublicationBuiltinTopicData.getType() not supported.");
    }

    @Override
    public Durability getDurability() {
        //TODO FRCYC return PolicyConverter.convert(this.environment, old.durability);
    	return null;
    }

    @Override
    public DurabilityService getDurabilityService() {
        // TODO: Fix Java 5 PSM specification; DurabilityServiceQos is NOT part
        // of DataWriterQos
        throw new UnsupportedOperationExceptionImpl(
                this.environment,
                "Java 5 PSM specification issue; DurabilityServiceQos is only part of the TopicQos not of DataWriterQos.");
    }

    @Override
    public Deadline getDeadline() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.deadline);
    	return null;
    }

    @Override
    public LatencyBudget getLatencyBudget() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.latency_budget);
    	return null;
    }

    @Override
    public Liveliness getLiveliness() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.liveliness);
    	return null;
    }

    @Override
    public Reliability getReliability() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.reliability);
    	return null;
    }

    @Override
    public Lifespan getLifespan() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.lifespan);
    	return null;
    }

    @Override
    public UserData getUserData() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.user_data);
    	return null;
    }

    @Override
    public Ownership getOwnership() {
        //TODO FRCYC return PolicyConverter.convert(this.environment, old.ownership);
    	return null;
    }

    @Override
    public OwnershipStrength getOwnershipStrength() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.ownership_strength);

    	return null;
    }

    @Override
    public DestinationOrder getDestinationOrder() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.destination_order);
    	return null;
    }

    @Override
    public Presentation getPresentation() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.presentation);
    	return null;
    }

    @Override
    public Partition getPartition() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.partition);
    	return null;
    }

    @Override
    public TopicData getTopicData() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.topic_data);
    	return null;
    }

    @Override
    public GroupData getGroupData() {
    	//TODO FRCYC return PolicyConverter.convert(this.environment, old.group_data);
    	return null;
    }

    @Override
    public DataRepresentation getRepresentation() {
        return this.dataRepresentation;
    }

    @Override
    public TypeConsistencyEnforcement getTypeConsistency() {
        return this.typeConsistency;
    }

    @Override
    public void copyFrom(PublicationBuiltinTopicData src) {
        if (src == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid PublicationBuiltinTopicData (null) provided.");
        }
        try {
            PublicationBuiltinTopicDataImpl impl = (PublicationBuiltinTopicDataImpl) src;
            this.old = impl.old;
            //TODO FRCYC this.key = new BuiltinTopicKeyImpl(this.environment, this.old.key);
            //TODO FRCYC this.participantKey = new BuiltinTopicKeyImpl(this.environment, this.old.participant_key);
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "PublicationBuiltinTopicData.copyFrom() on non-OpenSplice PublicationBuiltinTopicData implementation is not supported.");
        }
    }

    @Override
    public PublicationBuiltinTopicData clone() {
        return new PublicationBuiltinTopicDataImpl(this.environment, this.old);
    }

}
