/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Lifespan;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.QosPolicyCount;
import org.omg.dds.core.policy.ReaderDataLifecycle;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.policy.TimeBasedFilter;
import org.omg.dds.core.policy.TopicData;
import org.omg.dds.core.policy.TransportPriority;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.policy.WriterDataLifecycle;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.DataOnReadersStatus;
import org.omg.dds.core.status.InconsistentTopicStatus;
import org.omg.dds.core.status.LivelinessChangedStatus;
import org.omg.dds.core.status.LivelinessLostStatus;
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;
import org.omg.dds.core.status.OfferedIncompatibleQosStatus;
import org.omg.dds.core.status.PublicationMatchedStatus;
import org.omg.dds.core.status.RequestedDeadlineMissedStatus;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.omg.dds.core.status.SampleLostStatus;
import org.omg.dds.core.status.SampleRejectedStatus;
import org.omg.dds.core.status.SampleRejectedStatus.Kind;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.InstanceHandleImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.core.policy.PolicyConverter;
import org.eclipse.cyclonedds.core.policy.QosPolicyCountImpl;
import org.eclipse.cyclonedds.core.policy.Scheduling;
import org.omg.dds.core.status.Status;
import org.omg.dds.core.status.SubscriptionMatchedStatus;

//TODO FRCYC import SampleRejectedStatusKind;

public class StatusConverter {
    private static boolean stateTest(int state, int mask) {
        return (((state) & (mask)) == mask) ? true : false;
    }

    /*
    public static Set<Class<? extends Status>> convertMask(
            OsplServiceEnvironment environment, int state) {
        HashSet<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();

        if (state == STATUS_MASK_ANY_V1_2.value) {
            statuses.add(AllDataDisposedStatus.class);
            statuses.add(DataAvailableStatus.class);
            statuses.add(DataOnReadersStatus.class);
            statuses.add(InconsistentTopicStatus.class);
            statuses.add(LivelinessChangedStatus.class);
            statuses.add(LivelinessLostStatus.class);
            statuses.add(OfferedDeadlineMissedStatus.class);
            statuses.add(OfferedIncompatibleQosStatus.class);
            statuses.add(PublicationMatchedStatus.class);
            statuses.add(RequestedDeadlineMissedStatus.class);
            statuses.add(RequestedIncompatibleQosStatus.class);
            statuses.add(SampleLostStatus.class);
            statuses.add(SampleRejectedStatus.class);
            statuses.add(SubscriptionMatchedStatus.class);
        } else {
            if (stateTest(state, ALL_DATA_DISPOSED_TOPIC_STATUS.value)) {
                statuses.add(AllDataDisposedStatus.class);
            }
            if (stateTest(state, DATA_AVAILABLE_STATUS.value)) {
                statuses.add(DataAvailableStatus.class);
            }
            if (stateTest(state, DATA_ON_READERS_STATUS.value)) {
                statuses.add(DataOnReadersStatus.class);
            }
            if (stateTest(state, INCONSISTENT_TOPIC_STATUS.value)) {
                statuses.add(InconsistentTopicStatus.class);
            }
            if (stateTest(state, LIVELINESS_CHANGED_STATUS.value)) {
                statuses.add(LivelinessChangedStatus.class);
            }
            if (stateTest(state, LIVELINESS_LOST_STATUS.value)) {
                statuses.add(LivelinessLostStatus.class);
            }
            if (stateTest(state, OFFERED_DEADLINE_MISSED_STATUS.value)) {
                statuses.add(OfferedDeadlineMissedStatus.class);
            }
            if (stateTest(state, OFFERED_INCOMPATIBLE_QOS_STATUS.value)) {
                statuses.add(OfferedIncompatibleQosStatus.class);
            }
            if (stateTest(state, PUBLICATION_MATCHED_STATUS.value)) {
                statuses.add(PublicationMatchedStatus.class);
            }
            if (stateTest(state, REQUESTED_DEADLINE_MISSED_STATUS.value)) {
                statuses.add(RequestedDeadlineMissedStatus.class);
            }
            if (stateTest(state, REQUESTED_INCOMPATIBLE_QOS_STATUS.value)) {
                statuses.add(RequestedIncompatibleQosStatus.class);
            }
            if (stateTest(state, SAMPLE_LOST_STATUS.value)) {
                statuses.add(SampleLostStatus.class);
            }
            if (stateTest(state, SAMPLE_REJECTED_STATUS.value)) {
                statuses.add(SampleRejectedStatus.class);
            }
            if (stateTest(state, SUBSCRIPTION_MATCHED_STATUS.value)) {
                statuses.add(SubscriptionMatchedStatus.class);
            }
        }
        return statuses;
    }

    public static int convertMask(OsplServiceEnvironment env,
            Class<? extends Status>... statuses) {
        return StatusConverter.convertMask(env, Arrays.asList(statuses));
    }

    public static int getAnyMask() {
        return STATUS_MASK_ANY_V1_2.value;
    }

    public static int convertMask(OsplServiceEnvironment env,
            Collection<Class<? extends Status>> statuses) {
        int mask;

        if (statuses == null) {
            return STATUS_MASK_ANY_V1_2.value;
        } else if (statuses.size() == 0) {
            return STATUS_MASK_NONE.value;
        } else if (statuses.size() == 1 && statuses.iterator().next() == null) {
            return STATUS_MASK_ANY_V1_2.value;
        }
        mask = STATUS_MASK_NONE.value;

        for (Class<? extends Status> clz : statuses) {
            if (clz == null) {
                throw new IllegalArgumentExceptionImpl(env,
                        "Passed illegal <null> status.");
            } else if (clz.equals(DataAvailableStatus.class)) {
                mask |= DATA_AVAILABLE_STATUS.value;
            } else if (clz.equals(InconsistentTopicStatus.class)) {
                mask |= INCONSISTENT_TOPIC_STATUS.value;
            } else if (clz.equals(OfferedDeadlineMissedStatus.class)) {
                mask |= OFFERED_DEADLINE_MISSED_STATUS.value;
            } else if (clz.equals(RequestedDeadlineMissedStatus.class)) {
                mask |= REQUESTED_DEADLINE_MISSED_STATUS.value;
            } else if (clz.equals(OfferedIncompatibleQosStatus.class)) {
                mask |= OFFERED_INCOMPATIBLE_QOS_STATUS.value;
            } else if (clz.equals(RequestedIncompatibleQosStatus.class)) {
                mask |= REQUESTED_INCOMPATIBLE_QOS_STATUS.value;
            } else if (clz.equals(SampleLostStatus.class)) {
                mask |= SAMPLE_LOST_STATUS.value;
            } else if (clz.equals(SampleRejectedStatus.class)) {
                mask |= SAMPLE_REJECTED_STATUS.value;
            } else if (clz.equals(DataOnReadersStatus.class)) {
                mask |= DATA_ON_READERS_STATUS.value;
            } else if (clz.equals(LivelinessLostStatus.class)) {
                mask |= LIVELINESS_LOST_STATUS.value;
            } else if (clz.equals(LivelinessChangedStatus.class)) {
                mask |= LIVELINESS_CHANGED_STATUS.value;
            } else if (clz.equals(PublicationMatchedStatus.class)) {
                mask |= PUBLICATION_MATCHED_STATUS.value;
            } else if (clz.equals(SubscriptionMatchedStatus.class)) {
                mask |= SUBSCRIPTION_MATCHED_STATUS.value;
            } else if (clz.equals(AllDataDisposedStatus.class)) {
                mask |= ALL_DATA_DISPOSED_TOPIC_STATUS.value;
            } else if (clz.equals(Status.class)) {
                throw new IllegalArgumentExceptionImpl(env,
                        "Provided class does not extend from the org.omg.dds.core.status.Status class.");
            } else {
                throw new IllegalArgumentExceptionImpl(env,
                        "Found illegal Class<? extends Status>: "
                                + clz.getName());
            }
        }
        return mask;
    }

    public static QosPolicyCount[] convert(OsplServiceEnvironment env,
            QosPolicyCount[] old) {
        Class<? extends QosPolicy> policyClass;
        ArrayList<QosPolicyCountImpl> policies = new ArrayList<QosPolicyCountImpl>();

        for (int i = 0; i < old.length; i++) {
            policyClass = PolicyConverter.convert(env, old[i].policy_id);

            if (policyClass != null) {
                if (old[i].count != 0) {
                    policies.add(new QosPolicyCountImpl(env, policyClass,
                            old[i].count));
                }
            }
        }
        return policies.toArray(new QosPolicyCountImpl[policies.size()]);
    }

    public static int convert(OsplServiceEnvironment env,
            Class<? extends QosPolicy> policy) {
        int id;

        if (policy.equals(Deadline.class)) {
            id = DEADLINE_QOS_POLICY_ID.value;
        } else if (policy.equals(DestinationOrder.class)) {
            id = DESTINATIONORDER_QOS_POLICY_ID.value;
        } else if (policy.equals(Durability.class)) {
            id = DURABILITYSERVICE_QOS_POLICY_ID.value;
        } else if (policy.equals(EntityFactory.class)) {
            id = ENTITYFACTORY_QOS_POLICY_ID.value;
        } else if (policy.equals(GroupData.class)) {
            id = GROUPDATA_QOS_POLICY_ID.value;
        } else if (policy.equals(History.class)) {
            id = HISTORY_QOS_POLICY_ID.value;
        } else if (policy.equals(LatencyBudget.class)) {
            id = LATENCYBUDGET_QOS_POLICY_ID.value;
        } else if (policy.equals(Lifespan.class)) {
            id = LIFESPAN_QOS_POLICY_ID.value;
        } else if (policy.equals(Liveliness.class)) {
            id = LIVELINESS_QOS_POLICY_ID.value;
        } else if (policy.equals(Ownership.class)) {
            id = OWNERSHIP_QOS_POLICY_ID.value;
        } else if (policy.equals(Partition.class)) {
            id = PARTITION_QOS_POLICY_ID.value;
        } else if (policy.equals(Presentation.class)) {
            id = PRESENTATION_QOS_POLICY_ID.value;
        } else if (policy.equals(ReaderDataLifecycle.class)) {
            id = READERDATALIFECYCLE_QOS_POLICY_ID.value;
        } else if (policy.equals(Reliability.class)) {
            id = RELIABILITY_QOS_POLICY_ID.value;
        } else if (policy.equals(ResourceLimits.class)) {
            id = RESOURCELIMITS_QOS_POLICY_ID.value;
        } else if (policy.equals(Scheduling.class)) {
            id = SCHEDULING_QOS_POLICY_ID.value;
        } else if (policy.equals(TimeBasedFilter.class)) {
            id = TIMEBASEDFILTER_QOS_POLICY_ID.value;
        } else if (policy.equals(TopicData.class)) {
            id = TOPICDATA_QOS_POLICY_ID.value;
        } else if (policy.equals(TransportPriority.class)) {
            id = TRANSPORTPRIORITY_QOS_POLICY_ID.value;
        } else if (policy.equals(UserData.class)) {
            id = USERDATA_QOS_POLICY_ID.value;
        } else if (policy.equals(WriterDataLifecycle.class)) {
            id = WRITERDATALIFECYCLE_QOS_POLICY_ID.value;
        } else {
            throw new IllegalArgumentExceptionImpl(env,
                    "Found illegal QoSPolicy: " + policy.getName());
        }
        return id;
    }

    public static QosPolicyCount[] convert(OsplServiceEnvironment env,
            Set<QosPolicyCount> count) {
        QosPolicyCount[] old = new QosPolicyCount[count.size()];
        Iterator<QosPolicyCount> iter = count.iterator();
        QosPolicyCount current;

        for (int i = 0; i < count.size(); i++) {
            current = iter.next();
            old[i] = new QosPolicyCount(convert(env,
                    current.getPolicyClass()), current.getCount());
        }
        return old;
    }

    public static InconsistentTopicStatus convert(OsplServiceEnvironment env,
            InconsistentTopicStatus old) {
        return new InconsistentTopicStatusImpl(env, old.total_count,
                old.total_count_change);
    }

    public static InconsistentTopicStatus convert(
            OsplServiceEnvironment env, InconsistentTopicStatus status) {
        return new InconsistentTopicStatus(status.getTotalCount(),
                status.getTotalCountChange());
    }

    public static AllDataDisposedStatus convert(OsplServiceEnvironment env,
            AllDataDisposedTopicStatus old) {
        return new AllDataDisposedStatusImpl(env, old.total_count,
                old.total_count_change);
    }

    public static AllDataDisposedTopicStatus convert(
            OsplServiceEnvironment env, AllDataDisposedStatus status) {
        return new AllDataDisposedTopicStatus(status.getTotalCount(),
                status.getTotalCountChange());
    }

    public static LivelinessChangedStatus convert(OsplServiceEnvironment env,
            LivelinessChangedStatus old) {
        return new LivelinessChangedStatusImpl(env, old.alive_count,
                old.alive_count_change, old.not_alive_count,
                old.not_alive_count_change, new InstanceHandleImpl(env,
                        old.last_publication_handle));
    }

    public static LivelinessChangedStatus convert(
            OsplServiceEnvironment env, LivelinessChangedStatus status) {
        return new LivelinessChangedStatus(status.getAliveCount(),
                status.getAliveCountChange(), status.getNotAliveCount(),
                status.getNotAliveCountChange(), Utilities.convert(env,
                        status.getLastPublicationHandle()));
    }

    public static LivelinessLostStatus convert(OsplServiceEnvironment env,
            LivelinessLostStatus old) {
        return new LivelinessLostStatusImpl(env, old.total_count,
                old.total_count_change);
    }

    public static LivelinessLostStatus convert(OsplServiceEnvironment env,
            LivelinessLostStatus status) {
        return new LivelinessLostStatus(status.getTotalCount(),
                status.getTotalCountChange());
    }

    public static OfferedDeadlineMissedStatus convert(
            OsplServiceEnvironment env, OfferedDeadlineMissedStatus old) {
        return new OfferedDeadlineMissedStatusImpl(env, old.total_count,
                old.total_count_change, Utilities.convert(env,
                        old.last_instance_handle));
    }

    public static OfferedDeadlineMissedStatus convert(
            OsplServiceEnvironment env, OfferedDeadlineMissedStatus status) {
        return new OfferedDeadlineMissedStatus(status.getTotalCount(),
                status.getTotalCountChange(), Utilities.convert(env,
                        status.getLastInstanceHandle()));
    }

    public static OfferedIncompatibleQosStatus convert(
            OsplServiceEnvironment env, OfferedIncompatibleQosStatus old) {

        return new OfferedIncompatibleQosStatusImpl(env, old.total_count,
                old.total_count_change, PolicyConverter.convert(env,
                        old.last_policy_id), convert(env, old.policies));
    }

    public static OfferedIncompatibleQosStatus convert(
            OsplServiceEnvironment env, OfferedIncompatibleQosStatus status) {
        return new OfferedIncompatibleQosStatus(status.getTotalCount(),
                status.getTotalCountChange(), convert(env,
                        status.getLastPolicyClass()), convert(env,
                        status.getPolicies()));
    }

    public static PublicationMatchedStatus convert(OsplServiceEnvironment env,
            PublicationMatchedStatus old) {

        return new PublicationMatchedStatusImpl(env, old.total_count,
                old.total_count_change, old.current_count,
                old.current_count_change, Utilities.convert(env,
                        old.last_subscription_handle));
    }

    public static PublicationMatchedStatus convert(
            OsplServiceEnvironment env, PublicationMatchedStatus status) {
        return new PublicationMatchedStatus(status.getTotalCount(),
                status.getTotalCountChange(), status.getCurrentCount(),
                status.getCurrentCountChange(), Utilities.convert(env,
                        status.getLastSubscriptionHandle()));
    }

    public static RequestedDeadlineMissedStatus convert(
            OsplServiceEnvironment env, RequestedDeadlineMissedStatus old) {
        return new RequestedDeadlineMissedStatusImpl(env, old.total_count,
                old.total_count_change, Utilities.convert(env,
                        old.last_instance_handle));
    }

    public static RequestedDeadlineMissedStatus convert(
            OsplServiceEnvironment env, RequestedDeadlineMissedStatus status) {
        return new RequestedDeadlineMissedStatus(status.getTotalCount(),
                status.getTotalCountChange(), Utilities.convert(env,
                        status.getLastInstanceHandle()));
    }

    public static SampleRejectedStatus convert(OsplServiceEnvironment env,
            SampleRejectedStatus old) {
        SampleRejectedStatus.Kind kind;

        switch (old.last_reason.value()) {
        case SampleRejectedStatusKind._NOT_REJECTED:
            kind = Kind.NOT_REJECTED;
            break;
        case SampleRejectedStatusKind._REJECTED_BY_INSTANCES_LIMIT:
            kind = Kind.REJECTED_BY_INSTANCES_LIMIT;
            break;
        case SampleRejectedStatusKind._REJECTED_BY_SAMPLES_LIMIT:
            kind = Kind.REJECTED_BY_SAMPLES_LIMIT;
            break;
        case SampleRejectedStatusKind._REJECTED_BY_SAMPLES_PER_INSTANCE_LIMIT:
            kind = Kind.REJECTED_BY_SAMPLES_PER_INSTANCE_LIMIT;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Found illegal SampleRejectedStatus.Kind "
                            + old.last_reason.value());
        }
        return new SampleRejectedStatusImpl(env, old.total_count,
                old.total_count_change, kind, Utilities.convert(env,
                        old.last_instance_handle));
    }

    public static SampleRejectedStatus convert(OsplServiceEnvironment env,
            SampleRejectedStatus status) {
        SampleRejectedStatusKind kind;

        switch (status.getLastReason()) {
        case NOT_REJECTED:
            kind = SampleRejectedStatusKind.NOT_REJECTED;
            break;
        case REJECTED_BY_INSTANCES_LIMIT:
            kind = SampleRejectedStatusKind.REJECTED_BY_INSTANCES_LIMIT;
            break;
        case REJECTED_BY_SAMPLES_LIMIT:
            kind = SampleRejectedStatusKind.REJECTED_BY_SAMPLES_LIMIT;
            break;
        case REJECTED_BY_SAMPLES_PER_INSTANCE_LIMIT:
            kind = SampleRejectedStatusKind.REJECTED_BY_SAMPLES_PER_INSTANCE_LIMIT;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Found illegal SampleRejectedStatus.Kind");
        }
        return new SampleRejectedStatus(status.getTotalCount(),
                status.getTotalCountChange(), kind, Utilities.convert(env,
                        status.getLastInstanceHandle()));
    }

    public static RequestedIncompatibleQosStatus convert(
            OsplServiceEnvironment env, RequestedIncompatibleQosStatus old) {

        return new RequestedIncompatibleQosStatusImpl(env, old.total_count,
                old.total_count_change, PolicyConverter.convert(env,
                        old.last_policy_id), convert(env, old.policies));
    }

    public static RequestedIncompatibleQosStatus convert(
            OsplServiceEnvironment env, RequestedIncompatibleQosStatus status) {
        return new RequestedIncompatibleQosStatus(status.getTotalCount(),
                status.getTotalCountChange(), convert(env,
                        status.getLastPolicyClass()), convert(env,
                        status.getPolicies()));
    }

    public static SubscriptionMatchedStatus convert(OsplServiceEnvironment env,
            SubscriptionMatchedStatus old) {

        return new SubscriptionMatchedStatusImpl(env, old.total_count,
                old.total_count_change, old.current_count,
                old.current_count_change, Utilities.convert(env,
                        old.last_publication_handle));
    }

    public static SubscriptionMatchedStatus convert(
            OsplServiceEnvironment env, SubscriptionMatchedStatus status) {
        return new SubscriptionMatchedStatus(status.getTotalCount(),
                status.getTotalCountChange(), status.getCurrentCount(),
                status.getCurrentCountChange(), Utilities.convert(env,
                        status.getLastPublicationHandle()));
    }

    public static SampleLostStatus convert(OsplServiceEnvironment env,
            SampleLostStatus old) {
        return new SampleLostStatusImpl(env, old.total_count,
                old.total_count_change);
    }

    public static SampleLostStatus convert(SampleLostStatus status) {
        return new SampleLostStatus(status.getTotalCount(),
                status.getTotalCountChange());
    }
    */
}
