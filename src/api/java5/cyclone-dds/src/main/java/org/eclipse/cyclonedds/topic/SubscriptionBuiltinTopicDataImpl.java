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
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.TimeBasedFilter;
import org.omg.dds.core.policy.TopicData;
import org.omg.dds.core.policy.TypeConsistencyEnforcement;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.policy.TypeConsistencyEnforcement.Kind;
import org.omg.dds.topic.BuiltinTopicKey;
import org.omg.dds.topic.SubscriptionBuiltinTopicData;
import org.omg.dds.type.typeobject.TypeObject;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.UnsupportedOperationExceptionImpl;
import org.eclipse.cyclonedds.core.policy.DataRepresentationImpl;
import org.eclipse.cyclonedds.core.policy.PolicyConverter;
import org.eclipse.cyclonedds.core.policy.TypeConsistencyEnforcementImpl;

public class SubscriptionBuiltinTopicDataImpl implements
        SubscriptionBuiltinTopicData {
    private static final long serialVersionUID = -6604763092552114237L;
    private final transient ServiceEnvironmentImpl environment;
    private SubscriptionBuiltinTopicData old;
    private BuiltinTopicKey key;
    private BuiltinTopicKey participantKey;
    private final TypeConsistencyEnforcement typeConsistency;
    private final DataRepresentation dataRepresentation;

    public SubscriptionBuiltinTopicDataImpl(ServiceEnvironmentImpl environment,
            SubscriptionBuiltinTopicData old) {
        this.environment = environment;
        this.old = old;
        //TODO FRCYC this.key = new BuiltinTopicKeyImpl(this.environment, old.key);
        //TODO FRCYC this.participantKey = new BuiltinTopicKeyImpl(this.environment,old.participant_key);
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

    /* TODO FRCYC
    @Override
    public String getTopicName() {
        return this.old.topic_name;
    }

    @Override
    public String getTypeName() {
        return this.old.type_name;
    }
    */

    @Override
    public List<String> getEquivalentTypeName() {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "SubscriptionBuiltinTopicData.getEquivalentTypeName() not supported.");
    }

    @Override
    public List<String> getBaseTypeName() {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "SubscriptionBuiltinTopicData.getBaseTypeName() not supported.");
    }

    /* TODO FRCYC
    @Override
    public TypeObject getType() {
        throw new UnsupportedOperationExceptionImpl(this.environment,
                "SubscriptionBuiltinTopicData.getType() not supported.");
    }

    @Override
    public Durability getDurability() {
        return PolicyConverter.convert(this.environment, old.durability);
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
    public Ownership getOwnership() {
        return PolicyConverter.convert(this.environment, old.ownership);
    }

    @Override
    public DestinationOrder getDestinationOrder() {
        return PolicyConverter.convert(this.environment, old.destination_order);
    }

    @Override
    public UserData getUserData() {
        return PolicyConverter.convert(this.environment, old.user_data);
    }

    @Override
    public TimeBasedFilter getTimeBasedFilter() {
        return PolicyConverter.convert(this.environment, old.time_based_filter);
    }

    @Override
    public Presentation getPresentation() {
        return PolicyConverter.convert(this.environment, old.presentation);
    }

    @Override
    public Partition getPartition() {
        return PolicyConverter.convert(this.environment, old.partition);
    }

    @Override
    public TopicData getTopicData() {
        return PolicyConverter.convert(this.environment, old.topic_data);
    }

    @Override
    public GroupData getGroupData() {
        return PolicyConverter.convert(this.environment, old.group_data);
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
    public void copyFrom(SubscriptionBuiltinTopicData src) {
        if (src == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid SubscriptionBuiltinTopicData (null) provided.");
        }
        try {
            SubscriptionBuiltinTopicDataImpl impl = (SubscriptionBuiltinTopicDataImpl) src;
            this.old = impl.old;
            //TODO FRCYC this.key = new BuiltinTopicKeyImpl(this.environment, this.old.key);
            //TODO FRCYC this.participantKey = new BuiltinTopicKeyImpl(this.environment,this.old.participant_key);
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "SubscriptionBuiltinTopicData.copyFrom() on non-OpenSplice SubscriptionBuiltinTopicData implementation is not supported.");
        }
    }

    @Override
    public SubscriptionBuiltinTopicData clone() {
        return new SubscriptionBuiltinTopicDataImpl(this.environment, this.old);
    }

	@Override
	public String getTopicName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeObject getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Durability getDurability() {
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
	public Ownership getOwnership() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DestinationOrder getDestinationOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserData getUserData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeBasedFilter getTimeBasedFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Presentation getPresentation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partition getPartition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicData getTopicData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupData getGroupData() {
		// TODO Auto-generated method stub
		return null;
	}
}
