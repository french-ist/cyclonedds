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
package org.eclipse.cyclonedds.core.policy;

import java.util.Set;

import org.omg.dds.core.Duration;
import org.omg.dds.core.policy.Deadline;
import org.omg.dds.core.policy.DestinationOrder;
import org.omg.dds.core.policy.DestinationOrder.Kind;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.DurabilityService;
import org.omg.dds.core.policy.EntityFactory;
import org.omg.dds.core.policy.GroupData;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.LatencyBudget;
import org.omg.dds.core.policy.Lifespan;
import org.omg.dds.core.policy.Liveliness;
import org.omg.dds.core.policy.Ownership;
import org.omg.dds.core.policy.OwnershipStrength;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.Presentation;
import org.omg.dds.core.policy.Presentation.AccessScopeKind;
import org.eclipse.cyclonedds.core.DDSExceptionImpl;
import org.eclipse.cyclonedds.core.DurationImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.OsplServiceEnvironment;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.core.policy.Scheduling.SchedulingClass;
import org.eclipse.cyclonedds.core.policy.Scheduling.SchedulingKind;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.core.policy.ResourceLimits;
import org.omg.dds.core.policy.TimeBasedFilter;
import org.omg.dds.core.policy.TopicData;
import org.omg.dds.core.policy.TransportPriority;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.core.policy.WriterDataLifecycle;

public class PolicyConverter {
	
	/* TODO FRCYC
    public static UserDataQosPolicy convert(OsplServiceEnvironment env,
            UserData p) {
        return new UserDataQosPolicy(p.getValue());
    }

    public static UserData convert(OsplServiceEnvironment env,
            UserDataQosPolicy old) {
        return new UserDataImpl(env, old.value);
    }

    public static EntityFactory convert(OsplServiceEnvironment env,
            EntityFactoryQosPolicy old) {
        return new EntityFactoryImpl(env, old.autoenable_created_entities);
    }

    public static EntityFactoryQosPolicy convert(
            OsplServiceEnvironment env, EntityFactory p) {
        return new EntityFactoryQosPolicy(p.isAutoEnableCreatedEntities());
    }

    public static Scheduling convert(OsplServiceEnvironment env,
            SchedulingQosPolicy old) {
        Scheduling ls = new SchedulingImpl(env);

        switch (old.scheduling_class.kind.value()) {
        case SchedulingClassQosPolicyKind._SCHEDULE_DEFAULT:
            ls = ls.withSchedulingClass(SchedulingClass.DEFAULT);
            break;
        case SchedulingClassQosPolicyKind._SCHEDULE_REALTIME:
            ls = ls.withSchedulingClass(SchedulingClass.REALTIME);
            break;
        case SchedulingClassQosPolicyKind._SCHEDULE_TIMESHARING:
            ls = ls.withSchedulingClass(SchedulingClass.TIMESHARING);
            break;
        default:
            throw new DDSExceptionImpl(env,
                    "Failed to convert listenerSchedulingClass");
        }
        switch (old.scheduling_priority_kind.kind.value()) {
        case SchedulingPriorityQosPolicyKind._PRIORITY_ABSOLUTE:
            ls = ls.withKind(SchedulingKind.ABSOLUTE);
            break;
        case SchedulingPriorityQosPolicyKind._PRIORITY_RELATIVE:
            ls = ls.withKind(SchedulingKind.RELATIVE);
            break;
        default:
            throw new DDSExceptionImpl(env,
                    "Failed to convert listenerSchedulingKind");
        }
        return ls;
    }

    public static SchedulingQosPolicy convert(OsplServiceEnvironment env,
            Scheduling p) {
        SchedulingQosPolicy old = new SchedulingQosPolicy();

        switch (p.getSchedulingClass()) {
        case DEFAULT:
            old.scheduling_class = new SchedulingClassQosPolicy(
                    SchedulingClassQosPolicyKind.SCHEDULE_DEFAULT);
            break;
        case REALTIME:
            old.scheduling_class = new SchedulingClassQosPolicy(
                    SchedulingClassQosPolicyKind.SCHEDULE_REALTIME);
            break;
        case TIMESHARING:
            old.scheduling_class = new SchedulingClassQosPolicy(
                    SchedulingClassQosPolicyKind.SCHEDULE_TIMESHARING);
            break;
        default:
            throw new DDSExceptionImpl(env,
                    "Failed to convert listenerSchedulingClass");
        }

        switch (p.getKind()) {
        case ABSOLUTE:
            old.scheduling_priority_kind = new SchedulingPriorityQosPolicy(
                    SchedulingPriorityQosPolicyKind.PRIORITY_ABSOLUTE);
            break;
        case RELATIVE:
            old.scheduling_priority_kind = new SchedulingPriorityQosPolicy(
                    SchedulingPriorityQosPolicyKind.PRIORITY_RELATIVE);
            break;
        default:
            throw new DDSExceptionImpl(env,
                    "Failed to convert listenerSchedulingKind");
        }
        old.scheduling_priority = p.getPriority();

        return old;
    }

    public static Duration convert(OsplServiceEnvironment env,
            Duration_t old) {
        return new DurationImpl(env, old.sec, old.nanosec);
    }

    public static Duration_t convert(OsplServiceEnvironment env, Duration p) {
        return Utilities.convert(env, p);
    }

    public static Deadline convert(OsplServiceEnvironment env,
            DeadlineQosPolicy old) {
        return new DeadlineImpl(env, convert(env, old.period));
    }

    public static DeadlineQosPolicy convert(OsplServiceEnvironment env,
            Deadline p) {
        return new DeadlineQosPolicy(convert(env, p.getPeriod()));
    }

    public static DestinationOrder convert(OsplServiceEnvironment env,
            DestinationOrderQosPolicy old) {
        switch (old.kind.value()) {
        case DestinationOrderQosPolicyKind._BY_RECEPTION_TIMESTAMP_DESTINATIONORDER_QOS:
            return new DestinationOrderImpl(env, Kind.BY_RECEPTION_TIMESTAMP);
        case DestinationOrderQosPolicyKind._BY_SOURCE_TIMESTAMP_DESTINATIONORDER_QOS:
            return new DestinationOrderImpl(env, Kind.BY_SOURCE_TIMESTAMP);
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown DestinationOrder kind.");
        }
    }

    public static DestinationOrderQosPolicy convert(
            OsplServiceEnvironment env, DestinationOrder p) {
        switch (p.getKind()) {
        case BY_RECEPTION_TIMESTAMP:
            return new DestinationOrderQosPolicy(
                    DestinationOrderQosPolicyKind.BY_RECEPTION_TIMESTAMP_DESTINATIONORDER_QOS);
        case BY_SOURCE_TIMESTAMP:
            return new DestinationOrderQosPolicy(
                    DestinationOrderQosPolicyKind.BY_SOURCE_TIMESTAMP_DESTINATIONORDER_QOS);
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown DestinationOrder kind.");
        }
    }

    public static Durability convert(OsplServiceEnvironment env,
            DurabilityQosPolicy old) {
        switch (old.kind.value()) {
        case DurabilityQosPolicyKind._VOLATILE_DURABILITY_QOS:
            return new DurabilityImpl(env, Durability.Kind.VOLATILE);
        case DurabilityQosPolicyKind._TRANSIENT_LOCAL_DURABILITY_QOS:
            return new DurabilityImpl(env, Durability.Kind.TRANSIENT_LOCAL);
        case DurabilityQosPolicyKind._TRANSIENT_DURABILITY_QOS:
            return new DurabilityImpl(env, Durability.Kind.TRANSIENT);
        case DurabilityQosPolicyKind._PERSISTENT_DURABILITY_QOS:
            return new DurabilityImpl(env, Durability.Kind.PERSISTENT);
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown Durability kind.");
        }
    }

    public static DurabilityQosPolicy convert(OsplServiceEnvironment env,
            Durability p) {
        switch (p.getKind()) {
        case VOLATILE:
            return new DurabilityQosPolicy(
                    DurabilityQosPolicyKind.VOLATILE_DURABILITY_QOS);
        case TRANSIENT_LOCAL:
            return new DurabilityQosPolicy(
                    DurabilityQosPolicyKind.TRANSIENT_LOCAL_DURABILITY_QOS);
        case TRANSIENT:
            return new DurabilityQosPolicy(
                    DurabilityQosPolicyKind.TRANSIENT_DURABILITY_QOS);
        case PERSISTENT:
            return new DurabilityQosPolicy(
                    DurabilityQosPolicyKind.PERSISTENT_DURABILITY_QOS);
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown Durability kind.");
        }
    }

    public static DurabilityService convert(OsplServiceEnvironment env,
            DurabilityServiceQosPolicy old) {
        History.Kind kind;

        switch (old.history_kind.value()) {
        case HistoryQosPolicyKind._KEEP_ALL_HISTORY_QOS:
            kind = History.Kind.KEEP_ALL;
            break;
        case HistoryQosPolicyKind._KEEP_LAST_HISTORY_QOS:
            kind = History.Kind.KEEP_LAST;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env, "Unknown History kind.");
        }

        return new DurabilityServiceImpl(env, convert(env,
                old.service_cleanup_delay), kind, old.history_depth,
                old.max_samples, old.max_instances,
                old.max_samples_per_instance);
    }

    public static DurabilityServiceQosPolicy convert(
            OsplServiceEnvironment env, DurabilityService p) {
        HistoryQosPolicyKind kind;

        switch (p.getHistoryKind()) {
        case KEEP_ALL:
            kind = HistoryQosPolicyKind.KEEP_ALL_HISTORY_QOS;
            break;
        case KEEP_LAST:
            kind = HistoryQosPolicyKind.KEEP_LAST_HISTORY_QOS;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(
                    (OsplServiceEnvironment) p.getEnvironment(),
                    "Unknown History kind.");
        }
        return new DurabilityServiceQosPolicy(convert(env,
                p.getServiceCleanupDelay()), kind, p.getHistoryDepth(),
                p.getMaxSamples(), p.getMaxInstances(),
                p.getMaxSamplesPerInstance());
    }

    public static History convert(OsplServiceEnvironment env,
            HistoryQosPolicy old) {
        History.Kind kind;

        switch (old.kind.value()) {
        case HistoryQosPolicyKind._KEEP_ALL_HISTORY_QOS:
            kind = History.Kind.KEEP_ALL;
            break;
        case HistoryQosPolicyKind._KEEP_LAST_HISTORY_QOS:
            kind = History.Kind.KEEP_LAST;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env, "Unknown History kind.");
        }
        return new HistoryImpl(env, kind, old.depth);
    }

    public static HistoryQosPolicy convert(OsplServiceEnvironment env,
            History p) {
        HistoryQosPolicyKind kind;

        switch (p.getKind()) {
        case KEEP_ALL:
            kind = HistoryQosPolicyKind.KEEP_ALL_HISTORY_QOS;
            break;
        case KEEP_LAST:
            kind = HistoryQosPolicyKind.KEEP_LAST_HISTORY_QOS;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env, "Unknown History kind.");
        }
        return new HistoryQosPolicy(kind, p.getDepth());
    }

    public static LatencyBudget convert(OsplServiceEnvironment env,
            LatencyBudgetQosPolicy old) {
        return new LatencyBudgetImpl(env, convert(env, old.duration));
    }

    public static LatencyBudgetQosPolicy convert(
            OsplServiceEnvironment env, LatencyBudget p) {
        return new LatencyBudgetQosPolicy(convert(env, p.getDuration()));
    }

    public static Lifespan convert(OsplServiceEnvironment env,
            LifespanQosPolicy old) {
        return new LifespanImpl(env, convert(env, old.duration));
    }

    public static LifespanQosPolicy convert(OsplServiceEnvironment env,
            Lifespan p) {
        return new LifespanQosPolicy(convert(env, p.getDuration()));
    }

    public static ReaderLifespan convert(OsplServiceEnvironment env,
            ReaderLifespanQosPolicy old) {
        if (old.use_lifespan == false) {
            return null;
        }
        return new ReaderLifespanImpl(env, convert(env, old.duration));
    }

    public static ReaderLifespanQosPolicy convert(
            OsplServiceEnvironment env, ReaderLifespan p) {
        if (p == null) {
            return new ReaderLifespanQosPolicy(false,
                    DURATION_ZERO.value);
        }
        return new ReaderLifespanQosPolicy(true, convert(env,
                p.getDuration()));
    }

    public static Share convert(OsplServiceEnvironment env,
            ShareQosPolicy old) {
        if (old.enable == false) {
            return null;
        }
        return new ShareImpl(env, old.name);
    }

    public static ShareQosPolicy convert(OsplServiceEnvironment env, Share p) {
        if (p == null) {
            return new ShareQosPolicy("", false);
        }
        return new ShareQosPolicy(p.getName(), true);
    }

    public static SubscriptionKeys convert(OsplServiceEnvironment env,
            SubscriptionKeyQosPolicy old) {
        if (old.use_key_list == false) {
            return null;
        }
        return new SubscriptionKeysImpl(env, old.key_list);
    }

    public static SubscriptionKeyQosPolicy convert(
            OsplServiceEnvironment env, SubscriptionKeys p) {
        if (p == null) {
            return new SubscriptionKeyQosPolicy(false, new String[0]);
        }
        return new SubscriptionKeyQosPolicy(true, p.getKey().toArray(
                new String[p.getKey().size()]));
    }

    public static TimeBasedFilter convert(OsplServiceEnvironment env,
            TimeBasedFilterQosPolicy old) {
        return new TimeBasedFilterImpl(env, Utilities.convert(env,
                old.minimum_separation));
    }

    public static TimeBasedFilterQosPolicy convert(
            OsplServiceEnvironment env, TimeBasedFilter p) {
        return new TimeBasedFilterQosPolicy(Utilities.convert(env,
                p.getMinimumSeparation()));
    }

    public static Liveliness convert(OsplServiceEnvironment env,
            LivelinessQosPolicy old) {
        Liveliness.Kind kind;

        switch (old.kind.value()) {
        case LivelinessQosPolicyKind._AUTOMATIC_LIVELINESS_QOS:
            kind = Liveliness.Kind.AUTOMATIC;
            break;
        case LivelinessQosPolicyKind._MANUAL_BY_PARTICIPANT_LIVELINESS_QOS:
            kind = Liveliness.Kind.MANUAL_BY_PARTICIPANT;
            break;
        case LivelinessQosPolicyKind._MANUAL_BY_TOPIC_LIVELINESS_QOS:
            kind = Liveliness.Kind.MANUAL_BY_TOPIC;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown Liveliness kind.");
        }
        return new LivelinessImpl(env, kind, convert(env, old.lease_duration));
    }

    public static LivelinessQosPolicy convert(OsplServiceEnvironment env,
            Liveliness p) {
        LivelinessQosPolicyKind kind;

        switch (p.getKind()) {
        case AUTOMATIC:
            kind = LivelinessQosPolicyKind.AUTOMATIC_LIVELINESS_QOS;
            break;
        case MANUAL_BY_PARTICIPANT:
            kind = LivelinessQosPolicyKind.MANUAL_BY_PARTICIPANT_LIVELINESS_QOS;
            break;
        case MANUAL_BY_TOPIC:
            kind = LivelinessQosPolicyKind.MANUAL_BY_TOPIC_LIVELINESS_QOS;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown Liveliness kind.");
        }
        return new LivelinessQosPolicy(kind, convert(env,
                p.getLeaseDuration()));
    }

    public static Ownership convert(OsplServiceEnvironment env,
            OwnershipQosPolicy old) {
        Ownership.Kind kind;

        switch (old.kind.value()) {
        case OwnershipQosPolicyKind._SHARED_OWNERSHIP_QOS:
            kind = Ownership.Kind.SHARED;
            break;
        case OwnershipQosPolicyKind._EXCLUSIVE_OWNERSHIP_QOS:
            kind = Ownership.Kind.EXCLUSIVE;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown Ownership kind.");
        }
        return new OwnershipImpl(env, kind);
    }

    public static OwnershipQosPolicy convert(OsplServiceEnvironment env,
            Ownership p) {
        OwnershipQosPolicyKind kind;

        switch (p.getKind()) {
        case SHARED:
            kind = OwnershipQosPolicyKind.SHARED_OWNERSHIP_QOS;
            break;
        case EXCLUSIVE:
            kind = OwnershipQosPolicyKind.EXCLUSIVE_OWNERSHIP_QOS;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown Ownership kind.");
        }
        return new OwnershipQosPolicy(kind);
    }

    public static Reliability convert(OsplServiceEnvironment env,
            ReliabilityQosPolicy old) {
        Reliability.Kind kind;

        switch (old.kind.value()) {
        case ReliabilityQosPolicyKind._BEST_EFFORT_RELIABILITY_QOS:
            kind = Reliability.Kind.BEST_EFFORT;
            break;
        case ReliabilityQosPolicyKind._RELIABLE_RELIABILITY_QOS:
            kind = Reliability.Kind.RELIABLE;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown Reliability kind.");
        }
        return new ReliabilityImpl(env, kind, convert(env,
                old.max_blocking_time), old.synchronous);
    }

    public static ReliabilityQosPolicy convert(OsplServiceEnvironment env,
            Reliability p) {
        org.eclipse.cyclonedds.core.policy.Reliability r;
        boolean synchronous;
        ReliabilityQosPolicyKind kind;

        try {
            r = (org.eclipse.cyclonedds.core.policy.Reliability) p;
            synchronous = r.isSynchronous();
        } catch (ClassCastException e) {
            synchronous = false;
        }
        switch (p.getKind()) {
        case RELIABLE:
            kind = ReliabilityQosPolicyKind.RELIABLE_RELIABILITY_QOS;
            break;
        case BEST_EFFORT:
            kind = ReliabilityQosPolicyKind.BEST_EFFORT_RELIABILITY_QOS;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown Reliability kind.");
        }
        return new ReliabilityQosPolicy(kind, convert(env,
                p.getMaxBlockingTime()), synchronous);
    }

    public static ResourceLimits convert(OsplServiceEnvironment env,
            ResourceLimitsQosPolicy old) {
        return new ResourceLimitsImpl(env, old.max_samples, old.max_instances,
                old.max_samples_per_instance);
    }

    public static ResourceLimitsQosPolicy convert(
            OsplServiceEnvironment env, ResourceLimits p) {
        return new ResourceLimitsQosPolicy(p.getMaxSamples(),
                p.getMaxInstances(), p.getMaxSamplesPerInstance());
    }

    public static TopicData convert(OsplServiceEnvironment env,
            TopicDataQosPolicy old) {
        return new TopicDataImpl(env, old.value);
    }

    public static TopicDataQosPolicy convert(OsplServiceEnvironment env,
            TopicData p) {
        return new TopicDataQosPolicy(p.getValue());
    }

    public static TransportPriority convert(OsplServiceEnvironment env,
            TransportPriorityQosPolicy old) {
        return new TransportPriorityImpl(env, old.value);
    }

    public static TransportPriorityQosPolicy convert(
            OsplServiceEnvironment env, TransportPriority p) {
        return new TransportPriorityQosPolicy(p.getValue());
    }

    public static GroupData convert(OsplServiceEnvironment env,
            GroupDataQosPolicy old) {
        return new GroupDataImpl(env, old.value);
    }

    public static GroupDataQosPolicy convert(OsplServiceEnvironment env,
            GroupData p) {
        return new GroupDataQosPolicy(p.getValue());
    }

    public static Partition convert(OsplServiceEnvironment env,
            PartitionQosPolicy old) {
        return new PartitionImpl(env, old.name);
    }

    public static PartitionQosPolicy convert(OsplServiceEnvironment env,
            Partition p) {
        Set<String> partitions = p.getName();
        String[] pArray = p.getName().toArray(new String[partitions.size()]);

        return new PartitionQosPolicy(pArray);
    }

    public static Presentation convert(OsplServiceEnvironment env,
            PresentationQosPolicy old) {
        AccessScopeKind kind;

        switch (old.access_scope.value()) {
        case PresentationQosPolicyAccessScopeKind._INSTANCE_PRESENTATION_QOS:
            kind = AccessScopeKind.INSTANCE;
            break;
        case PresentationQosPolicyAccessScopeKind._TOPIC_PRESENTATION_QOS:
            kind = AccessScopeKind.TOPIC;
            break;
        case PresentationQosPolicyAccessScopeKind._GROUP_PRESENTATION_QOS:
            kind = AccessScopeKind.GROUP;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown Presentation AccessScope kind.");
        }
        return new PresentationImpl(env, kind, old.coherent_access,
                old.ordered_access);
    }

    public static PresentationQosPolicy convert(OsplServiceEnvironment env,
            Presentation p) {
        PresentationQosPolicyAccessScopeKind kind;

        switch (p.getAccessScope()) {
        case INSTANCE:
            kind = PresentationQosPolicyAccessScopeKind.INSTANCE_PRESENTATION_QOS;
            break;
        case TOPIC:
            kind = PresentationQosPolicyAccessScopeKind.TOPIC_PRESENTATION_QOS;
            break;
        case GROUP:
            kind = PresentationQosPolicyAccessScopeKind.GROUP_PRESENTATION_QOS;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown Presentation AccessScope kind.");
        }
        return new PresentationQosPolicy(kind, p.isCoherentAccess(),
                p.isOrderedAccess());
    }

    public static OwnershipStrength convert(OsplServiceEnvironment env,
            OwnershipStrengthQosPolicy old) {
        return new OwnershipStrengthImpl(env, old.value);
    }

    public static OwnershipStrengthQosPolicy convert(
            OsplServiceEnvironment env, OwnershipStrength p) {
        return new OwnershipStrengthQosPolicy(p.getValue());
    }

    public static WriterDataLifecycle convert(OsplServiceEnvironment env,
            WriterDataLifecycleQosPolicy old) {
        return new WriterDataLifecycleImpl(env,
                old.autodispose_unregistered_instances, convert(env,
                        old.autopurge_suspended_samples_delay), convert(env,
                        old.autounregister_instance_delay));
    }

    public static WriterDataLifecycleQosPolicy convert(
            OsplServiceEnvironment env, WriterDataLifecycle p) {
        Duration autoPurgeSuspendedSamplesDelay, autoUnregisterInstanceDelay;
        org.eclipse.cyclonedds.core.policy.WriterDataLifecycle w;

        try {
            w = (org.eclipse.cyclonedds.core.policy.WriterDataLifecycle) p;
            autoPurgeSuspendedSamplesDelay = w
                    .getAutoPurgeSuspendedSamplesDelay();
            autoUnregisterInstanceDelay = w.getAutoUnregisterInstanceDelay();
        } catch (ClassCastException e) {
            autoPurgeSuspendedSamplesDelay = p.getEnvironment().getSPI()
                    .infiniteDuration();
            autoUnregisterInstanceDelay = p.getEnvironment().getSPI()
                    .infiniteDuration();
        }

        return new WriterDataLifecycleQosPolicy(
                p.isAutDisposeUnregisteredInstances(), convert(env,
                        autoPurgeSuspendedSamplesDelay), convert(env,
                        autoUnregisterInstanceDelay));
    }

    public static ReaderDataLifecycle convert(OsplServiceEnvironment env,
            ReaderDataLifecycleQosPolicy old) {
        org.eclipse.cyclonedds.core.policy.ReaderDataLifecycle.Kind kind;

        switch (old.invalid_sample_visibility.kind.value()) {
        case InvalidSampleVisibilityQosPolicyKind._ALL_INVALID_SAMPLES:
            kind = org.eclipse.cyclonedds.core.policy.ReaderDataLifecycle.Kind.ALL;
            break;
        case InvalidSampleVisibilityQosPolicyKind._MINIMUM_INVALID_SAMPLES:
            kind = org.eclipse.cyclonedds.core.policy.ReaderDataLifecycle.Kind.MINIMUM;
            break;
        case InvalidSampleVisibilityQosPolicyKind._NO_INVALID_SAMPLES:
            kind = org.eclipse.cyclonedds.core.policy.ReaderDataLifecycle.Kind.NONE;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Unknown ReaderDataLifecycle InvalidSampleVisibilityKind.");
        }
        return new ReaderDataLifecycleImpl(env, Utilities.convert(env,
                old.autopurge_nowriter_samples_delay), Utilities.convert(env,
                old.autopurge_disposed_samples_delay),
                old.autopurge_dispose_all, kind);
    }

    public static ReaderDataLifecycleQosPolicy convert(
            OsplServiceEnvironment env,
            org.omg.dds.core.policy.ReaderDataLifecycle p) {
        org.eclipse.cyclonedds.core.policy.ReaderDataLifecycle r;
        boolean autoPurgeDisposeAll;
        InvalidSampleVisibilityQosPolicy invalidSampleVisibility;

        try {
            r = (org.eclipse.cyclonedds.core.policy.ReaderDataLifecycle) p;
            autoPurgeDisposeAll = r.getAutoPurgeDisposeAll();
            switch (r.getInvalidSampleInvisibility()) {
            case ALL:
                invalidSampleVisibility = new InvalidSampleVisibilityQosPolicy(
                        InvalidSampleVisibilityQosPolicyKind.ALL_INVALID_SAMPLES);
                break;
            case MINIMUM:
                invalidSampleVisibility = new InvalidSampleVisibilityQosPolicy(
                        InvalidSampleVisibilityQosPolicyKind.MINIMUM_INVALID_SAMPLES);
                break;
            case NONE:
                invalidSampleVisibility = new InvalidSampleVisibilityQosPolicy(
                        InvalidSampleVisibilityQosPolicyKind.NO_INVALID_SAMPLES);
                break;
            default:
                throw new IllegalArgumentExceptionImpl(env,
                        "Unknown ReaderDataLifecycle InvalidSampleVisibilityKind.");
            }

        } catch (ClassCastException e) {
            autoPurgeDisposeAll = false;
            invalidSampleVisibility = new InvalidSampleVisibilityQosPolicy(
                    InvalidSampleVisibilityQosPolicyKind.MINIMUM_INVALID_SAMPLES);
        }

        return new ReaderDataLifecycleQosPolicy(convert(env,
                p.getAutoPurgeNoWriterSamplesDelay()), convert(env,
                p.getAutoPurgeDisposedSamplesDelay()), autoPurgeDisposeAll,
                true, invalidSampleVisibility);
    }

    public static Class<? extends QosPolicy> convert(
            OsplServiceEnvironment env, int policyId) {
        Class<? extends QosPolicy> policy;

        switch (policyId) {
        case DEADLINE_QOS_POLICY_ID.value:
            policy = Deadline.class;
            break;
        case DESTINATIONORDER_QOS_POLICY_ID.value:
            policy = DestinationOrder.class;
            break;
        case DURABILITY_QOS_POLICY_ID.value:
            policy = Durability.class;
            break;
        case DURABILITYSERVICE_QOS_POLICY_ID.value:
            policy = DurabilityService.class;
            break;
        case ENTITYFACTORY_QOS_POLICY_ID.value:
            policy = EntityFactory.class;
            break;
        case GROUPDATA_QOS_POLICY_ID.value:
            policy = GroupData.class;
            break;
        case HISTORY_QOS_POLICY_ID.value:
            policy = History.class;
            break;
        case LATENCYBUDGET_QOS_POLICY_ID.value:
            policy = LatencyBudget.class;
            break;
        case LIFESPAN_QOS_POLICY_ID.value:
            policy = Lifespan.class;
            break;
        case LIVELINESS_QOS_POLICY_ID.value:
            policy = Liveliness.class;
            break;
        case OWNERSHIP_QOS_POLICY_ID.value:
            policy = Ownership.class;
            break;
        case OWNERSHIPSTRENGTH_QOS_POLICY_ID.value:
            policy = OwnershipStrength.class;
            break;
        case PARTITION_QOS_POLICY_ID.value:
            policy = Partition.class;
            break;
        case PRESENTATION_QOS_POLICY_ID.value:
            policy = Presentation.class;
            break;
        case READERDATALIFECYCLE_QOS_POLICY_ID.value:
            policy = ReaderDataLifecycle.class;
            break;
        case RELIABILITY_QOS_POLICY_ID.value:
            policy = Reliability.class;
            break;
        case RESOURCELIMITS_QOS_POLICY_ID.value:
            policy = ResourceLimits.class;
            break;
        case SCHEDULING_QOS_POLICY_ID.value:
            policy = Scheduling.class;
            break;
        case TIMEBASEDFILTER_QOS_POLICY_ID.value:
            policy = TimeBasedFilter.class;
            break;
        case TOPICDATA_QOS_POLICY_ID.value:
            policy = TopicData.class;
            break;
        case TRANSPORTPRIORITY_QOS_POLICY_ID.value:
            policy = TransportPriority.class;
            break;
        case USERDATA_QOS_POLICY_ID.value:
            policy = UserData.class;
            break;
        case WRITERDATALIFECYCLE_QOS_POLICY_ID.value:
            policy = WriterDataLifecycle.class;
            break;
        case 23: /* TODO: Add SUBSCRIPTIONKEY_QOS_POLICY_ID to classic Java PSM 
            policy = SubscriptionKeys.class;
            break;
        case 24: /* TODO: Add VIEWKEY_QOS_POLICY_ID to classic Java PSM 
            policy = ViewKeys.class;
            break;
        case 25:/* TODO: Add READERLIFESPAN_QOS_POLICY_ID to classic Java PSM 
            policy = ReaderLifespan.class;
            break;
        case 26:/* TODO: Add SHARE_QOS_POLICY_ID to classic Java PSM 
            policy = Share.class;
            break;
        case INVALID_QOS_POLICY_ID.value:
            policy = null;
            break;
        default:
            throw new IllegalArgumentExceptionImpl(env,
                    "Found unknown QoSPolicy id: " + policyId);
        }
        return policy;
    }
    */
}
