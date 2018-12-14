package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_dcps_builtintopics.h:403</i><br>
 */
public class DDS_DataWriterQos extends Structure {
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
	/** C type : DDS_TransportPriorityQosPolicy */
	public DDS_TransportPriorityQosPolicy transport_priority;
	public DDS_TransportPriorityQosPolicy getTransport_priority() {
		return transport_priority;
	}
	public void setTransport_priority(DDS_TransportPriorityQosPolicy transport_priority) {
		this.transport_priority = transport_priority;
	}
	/** C type : DDS_LifespanQosPolicy */
	public DDS_LifespanQosPolicy lifespan;
	public DDS_LifespanQosPolicy getLifespan() {
		return lifespan;
	}
	public void setLifespan(DDS_LifespanQosPolicy lifespan) {
		this.lifespan = lifespan;
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
	/** C type : DDS_OwnershipStrengthQosPolicy */
	public DDS_OwnershipStrengthQosPolicy ownership_strength;
	public DDS_OwnershipStrengthQosPolicy getOwnership_strength() {
		return ownership_strength;
	}
	public void setOwnership_strength(DDS_OwnershipStrengthQosPolicy ownership_strength) {
		this.ownership_strength = ownership_strength;
	}
	/** C type : DDS_WriterDataLifecycleQosPolicy */
	public DDS_WriterDataLifecycleQosPolicy writer_data_lifecycle;
	public DDS_WriterDataLifecycleQosPolicy getWriter_data_lifecycle() {
		return writer_data_lifecycle;
	}
	public void setWriter_data_lifecycle(DDS_WriterDataLifecycleQosPolicy writer_data_lifecycle) {
		this.writer_data_lifecycle = writer_data_lifecycle;
	}
	public DDS_DataWriterQos() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("durability", "deadline", "latency_budget", "liveliness", "reliability", "destination_order", "history", "resource_limits", "transport_priority", "lifespan", "user_data", "ownership", "ownership_strength", "writer_data_lifecycle");
	}
	public DDS_DataWriterQos(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_DataWriterQos implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_DataWriterQos implements Structure.ByValue {
		
	};
}
