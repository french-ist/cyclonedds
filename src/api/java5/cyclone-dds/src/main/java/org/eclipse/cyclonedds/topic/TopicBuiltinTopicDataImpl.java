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
package org.eclipse.cyclonedds.topic;

import java.util.List;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.DataRepresentation;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.DurabilityService;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Lifespan;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.policy.TopicData;
import org.omg.dds.core.policy.TransportPriority;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.TypeConsistencyEnforcement.Kind;
import org.omg.dds.topic.BuiltinTopicKey;
import org.omg.dds.topic.TopicBuiltinTopicData;
import org.omg.dds.type.typeobject.TypeObject;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.UnsupportedOperationExceptionImpl;
import org.eclipse.cyclonedds.core.policy.DataRepresentationImpl;
import org.eclipse.cyclonedds.core.policy.PolicyConverter;
import org.eclipse.cyclonedds.core.policy.TypeConsistencyEnforcementImpl;

public class TopicBuiltinTopicDataImpl implements TopicBuiltinTopicData {
    private static final long serialVersionUID = 4997312302065915561L;
    private final transient CycloneServiceEnvironment environment;
    private TopicBuiltinTopicData old;
    private BuiltinTopicKey key;
    private final TypeConsistencyEnforcement typeConsistency;
    private final DataRepresentation dataRepresentation;

    public TopicBuiltinTopicDataImpl(CycloneServiceEnvironment environment,
            TopicBuiltinTopicData old) {
        this.environment = environment;
        this.old = old;
        // TODO FRCYC this.key = new BuiltinTopicKeyImpl(this.environment, old.key);
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

    /* TODO FRCYC
    @Override
    public String getName() {
        return this.old.name;
    }

    @Override
    public String getTypeName() {
        return this.old.type_name;
    }*/

    @Override
    public List<String> getEquivalentTypeName() {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "TopicBuiltinTopicData.getEquivalentTypeName() not supported.");
    }

    @Override
    public List<String> getBaseTypeName() {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "TopicBuiltinTopicData.getBaseTypeName() not supported.");
    }

    @Override
    public TypeObject getType() {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "TopicBuiltinTopicData.getType() not supported.");
    }

/* TODO FRCYC    
    @Override
    public Durability getDurability() {
        return PolicyConverter.convert(this.environment, old.durability);
    }

    @Override
    public DurabilityService getDurabilityService() {
        return PolicyConverter
                .convert(this.environment, old.durability_service);
    }

    @Override
    public Deadline getDeadline() {
        return PolicyConverter.convert(this.environment, old.deadline);
    }

    @Override
    public LatencyBudget getLatencyBudget() {
        return PolicyConverter.convert(this.environment, old.latency_budget);
    }

    @Override
    public Liveliness getLiveliness() {
        return PolicyConverter.convert(this.environment, old.liveliness);
    }

    @Override
    public Reliability getReliability() {
        return PolicyConverter.convert(this.environment, old.reliability);
    }

    @Override
    public TransportPriority getTransportPriority() {
        return PolicyConverter
                .convert(this.environment, old.transport_priority);
    }

    @Override
    public Lifespan getLifespan() {
        return PolicyConverter.convert(this.environment, old.lifespan);
    }

    @Override
    public DestinationOrder getDestinationOrder() {
        return PolicyConverter.convert(this.environment, old.destination_order);
    }

    @Override
    public History getHistory() {
        return PolicyConverter.convert(this.environment, old.history);
    }

    @Override
    public ResourceLimits getResourceLimits() {
        return PolicyConverter.convert(this.environment, old.resource_limits);
    }

    @Override
    public Ownership getOwnership() {
        return PolicyConverter.convert(this.environment, old.ownership);
    }

    @Override
    public TopicData getTopicData() {
        return PolicyConverter.convert(this.environment, old.topic_data);
    }
*/
    
    @Override
    public DataRepresentation getRepresentation() {
        return this.dataRepresentation;
    }

    @Override
    public TypeConsistencyEnforcement getTypeConsistency() {
        return this.typeConsistency;
    }

    @Override
    public void copyFrom(TopicBuiltinTopicData src) {
        if (src == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid TopicBuiltinTopicData (null) provided.");
        }
        try {
            TopicBuiltinTopicDataImpl impl = (TopicBuiltinTopicDataImpl) src;
            this.old = impl.old;
            // TODO FRCYC this.key = new BuiltinTopicKeyImpl(this.environment, this.old.key);
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "TopicBuiltinTopicData.copyFrom() on non-OpenSplice TopicBuiltinTopicData implementation is not supported.");
        }
    }

    @Override
    public TopicBuiltinTopicData clone() {
        return new TopicBuiltinTopicDataImpl(this.environment, this.old);
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Durability getDurability() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DurabilityService getDurabilityService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deadline getDeadline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LatencyBudget getLatencyBudget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Liveliness getLiveliness() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reliability getReliability() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransportPriority getTransportPriority() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lifespan getLifespan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DestinationOrder getDestinationOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public History getHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLimits getResourceLimits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ownership getOwnership() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicData getTopicData() {
		// TODO Auto-generated method stub
		return null;
	}
}
