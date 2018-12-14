package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_builtinTopics.h:294</i><br>
 */
public class DDS_SubscriptionBuiltinTopicData extends Structure {
	/** C type : DDS_BuiltinTopicKey_t */
	public int[] key = new int[3];
	public int[] getKey() {
		return key;
	}
	public void setKey(int key[]) {
		this.key = key;
	}
	/** C type : DDS_BuiltinTopicKey_t */
	public int[] participant_key = new int[3];
	public int[] getParticipant_key() {
		return participant_key;
	}
	public void setParticipant_key(int participant_key[]) {
		this.participant_key = participant_key;
	}
	/** C type : char* */
	public Pointer topic_name;
	public Pointer getTopic_name() {
		return topic_name;
	}
	public void setTopic_name(Pointer topic_name) {
		this.topic_name = topic_name;
	}
	/** C type : char* */
	public Pointer type_name;
	public Pointer getType_name() {
		return type_name;
	}
	public void setType_name(Pointer type_name) {
		this.type_name = type_name;
	}
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
	/** C type : DDS_OwnershipQosPolicy */
	public DDS_OwnershipQosPolicy ownership;
	public DDS_OwnershipQosPolicy getOwnership() {
		return ownership;
	}
	public void setOwnership(DDS_OwnershipQosPolicy ownership) {
		this.ownership = ownership;
	}
	/** C type : DDS_DestinationOrderQosPolicy */
	public DDS_DestinationOrderQosPolicy destination_order;
	public DDS_DestinationOrderQosPolicy getDestination_order() {
		return destination_order;
	}
	public void setDestination_order(DDS_DestinationOrderQosPolicy destination_order) {
		this.destination_order = destination_order;
	}
	/** C type : DDS_UserDataQosPolicy */
	public DDS_UserDataQosPolicy user_data;
	public DDS_UserDataQosPolicy getUser_data() {
		return user_data;
	}
	public void setUser_data(DDS_UserDataQosPolicy user_data) {
		this.user_data = user_data;
	}
	/** C type : DDS_TimeBasedFilterQosPolicy */
	public DDS_TimeBasedFilterQosPolicy time_based_filter;
	public DDS_TimeBasedFilterQosPolicy getTime_based_filter() {
		return time_based_filter;
	}
	public void setTime_based_filter(DDS_TimeBasedFilterQosPolicy time_based_filter) {
		this.time_based_filter = time_based_filter;
	}
	/** C type : DDS_PresentationQosPolicy */
	public DDS_PresentationQosPolicy presentation;
	public DDS_PresentationQosPolicy getPresentation() {
		return presentation;
	}
	public void setPresentation(DDS_PresentationQosPolicy presentation) {
		this.presentation = presentation;
	}
	/** C type : DDS_PartitionQosPolicy */
	public DDS_PartitionQosPolicy partition;
	public DDS_PartitionQosPolicy getPartition() {
		return partition;
	}
	public void setPartition(DDS_PartitionQosPolicy partition) {
		this.partition = partition;
	}
	/** C type : DDS_TopicDataQosPolicy */
	public DDS_TopicDataQosPolicy topic_data;
	public DDS_TopicDataQosPolicy getTopic_data() {
		return topic_data;
	}
	public void setTopic_data(DDS_TopicDataQosPolicy topic_data) {
		this.topic_data = topic_data;
	}
	/** C type : DDS_GroupDataQosPolicy */
	public DDS_GroupDataQosPolicy group_data;
	public DDS_GroupDataQosPolicy getGroup_data() {
		return group_data;
	}
	public void setGroup_data(DDS_GroupDataQosPolicy group_data) {
		this.group_data = group_data;
	}
	public DDS_SubscriptionBuiltinTopicData() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("key", "participant_key", "topic_name", "type_name", "durability", "deadline", "latency_budget", "liveliness", "reliability", "ownership", "destination_order", "user_data", "time_based_filter", "presentation", "partition", "topic_data", "group_data");
	}
	public DDS_SubscriptionBuiltinTopicData(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_SubscriptionBuiltinTopicData implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_SubscriptionBuiltinTopicData implements Structure.ByValue {
		
	};
}
