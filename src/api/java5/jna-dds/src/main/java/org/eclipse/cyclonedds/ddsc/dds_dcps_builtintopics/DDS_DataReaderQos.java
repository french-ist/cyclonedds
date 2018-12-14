package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_dcps_builtintopics.h:426</i><br>
 */
public class DDS_DataReaderQos extends Structure {
	/** C type : DDS_DurabilityQosPolicy */
	public DDS_DurabilityQosPolicy durability;
	public DDS_DurabilityQosPolicy getDurability() {
		return durability;
	}
	public void setDurability(DDS_DurabilityQosPolicy durability) {
		this.durability = durability;
	}
	/** C type : DDS_DeadlineQosPolicy */
	public DDS_DeadlineQosPolicy deadline;
	public DDS_DeadlineQosPolicy getDeadline() {
		return deadline;
	}
	public void setDeadline(DDS_DeadlineQosPolicy deadline) {
		this.deadline = deadline;
	}
	/** C type : DDS_LatencyBudgetQosPolicy */
	public DDS_LatencyBudgetQosPolicy latency_budget;
	public DDS_LatencyBudgetQosPolicy getLatency_budget() {
		return latency_budget;
	}
	public void setLatency_budget(DDS_LatencyBudgetQosPolicy latency_budget) {
		this.latency_budget = latency_budget;
	}
	/** C type : DDS_LivelinessQosPolicy */
	public DDS_LivelinessQosPolicy liveliness;
	public DDS_LivelinessQosPolicy getLiveliness() {
		return liveliness;
	}
	public void setLiveliness(DDS_LivelinessQosPolicy liveliness) {
		this.liveliness = liveliness;
	}
	/** C type : DDS_ReliabilityQosPolicy */
	public DDS_ReliabilityQosPolicy reliability;
	public DDS_ReliabilityQosPolicy getReliability() {
		return reliability;
	}
	public void setReliability(DDS_ReliabilityQosPolicy reliability) {
		this.reliability = reliability;
	}
	/** C type : DDS_DestinationOrderQosPolicy */
	public DDS_DestinationOrderQosPolicy destination_order;
	public DDS_DestinationOrderQosPolicy getDestination_order() {
		return destination_order;
	}
	public void setDestination_order(DDS_DestinationOrderQosPolicy destination_order) {
		this.destination_order = destination_order;
	}
	/** C type : DDS_HistoryQosPolicy */
	public DDS_HistoryQosPolicy history;
	public DDS_HistoryQosPolicy getHistory() {
		return history;
	}
	public void setHistory(DDS_HistoryQosPolicy history) {
		this.history = history;
	}
	/** C type : DDS_ResourceLimitsQosPolicy */
	public DDS_ResourceLimitsQosPolicy resource_limits;
	public DDS_ResourceLimitsQosPolicy getResource_limits() {
		return resource_limits;
	}
	public void setResource_limits(DDS_ResourceLimitsQosPolicy resource_limits) {
		this.resource_limits = resource_limits;
	}
	/** C type : DDS_UserDataQosPolicy */
	public DDS_UserDataQosPolicy user_data;
	public DDS_UserDataQosPolicy getUser_data() {
		return user_data;
	}
	public void setUser_data(DDS_UserDataQosPolicy user_data) {
		this.user_data = user_data;
	}
	/** C type : DDS_OwnershipQosPolicy */
	public DDS_OwnershipQosPolicy ownership;
	public DDS_OwnershipQosPolicy getOwnership() {
		return ownership;
	}
	public void setOwnership(DDS_OwnershipQosPolicy ownership) {
		this.ownership = ownership;
	}
	/** C type : DDS_TimeBasedFilterQosPolicy */
	public DDS_TimeBasedFilterQosPolicy time_based_filter;
	public DDS_TimeBasedFilterQosPolicy getTime_based_filter() {
		return time_based_filter;
	}
	public void setTime_based_filter(DDS_TimeBasedFilterQosPolicy time_based_filter) {
		this.time_based_filter = time_based_filter;
	}
	/** C type : DDS_ReaderDataLifecycleQosPolicy */
	public DDS_ReaderDataLifecycleQosPolicy reader_data_lifecycle;
	public DDS_ReaderDataLifecycleQosPolicy getReader_data_lifecycle() {
		return reader_data_lifecycle;
	}
	public void setReader_data_lifecycle(DDS_ReaderDataLifecycleQosPolicy reader_data_lifecycle) {
		this.reader_data_lifecycle = reader_data_lifecycle;
	}
	/** C type : DDS_SubscriptionKeyQosPolicy */
	public DDS_SubscriptionKeyQosPolicy subscription_keys;
	public DDS_SubscriptionKeyQosPolicy getSubscription_keys() {
		return subscription_keys;
	}
	public void setSubscription_keys(DDS_SubscriptionKeyQosPolicy subscription_keys) {
		this.subscription_keys = subscription_keys;
	}
	/** C type : DDS_ReaderLifespanQosPolicy */
	public DDS_ReaderLifespanQosPolicy reader_lifespan;
	public DDS_ReaderLifespanQosPolicy getReader_lifespan() {
		return reader_lifespan;
	}
	public void setReader_lifespan(DDS_ReaderLifespanQosPolicy reader_lifespan) {
		this.reader_lifespan = reader_lifespan;
	}
	/** C type : DDS_ShareQosPolicy */
	public DDS_ShareQosPolicy share;
	public DDS_ShareQosPolicy getShare() {
		return share;
	}
	public void setShare(DDS_ShareQosPolicy share) {
		this.share = share;
	}
	public DDS_DataReaderQos() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("durability", "deadline", "latency_budget", "liveliness", "reliability", "destination_order", "history", "resource_limits", "user_data", "ownership", "time_based_filter", "reader_data_lifecycle", "subscription_keys", "reader_lifespan", "share");
	}
	public DDS_DataReaderQos(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_DataReaderQos implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_DataReaderQos implements Structure.ByValue {
		
	};
}
