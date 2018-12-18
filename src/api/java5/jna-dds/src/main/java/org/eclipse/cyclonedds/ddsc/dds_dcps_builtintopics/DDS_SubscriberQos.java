package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.ddsc.dds_builtinTopics.*;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class DDS_SubscriberQos extends Structure {
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
	/** C type : DDS_GroupDataQosPolicy */
	public DDS_GroupDataQosPolicy group_data;
	public DDS_GroupDataQosPolicy getGroup_data() {
		return group_data;
	}
	public void setGroup_data(DDS_GroupDataQosPolicy group_data) {
		this.group_data = group_data;
	}
	/** C type : DDS_EntityFactoryQosPolicy */
	public DDS_EntityFactoryQosPolicy entity_factory;
	public DDS_EntityFactoryQosPolicy getEntity_factory() {
		return entity_factory;
	}
	public void setEntity_factory(DDS_EntityFactoryQosPolicy entity_factory) {
		this.entity_factory = entity_factory;
	}
	/** C type : DDS_ShareQosPolicy */
	public DDS_ShareQosPolicy share;
	public DDS_ShareQosPolicy getShare() {
		return share;
	}
	public void setShare(DDS_ShareQosPolicy share) {
		this.share = share;
	}
	public DDS_SubscriberQos() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("presentation", "partition", "group_data", "entity_factory", "share");
	}
	/**
	 * @param presentation C type : DDS_PresentationQosPolicy<br>
	 * @param partition C type : DDS_PartitionQosPolicy<br>
	 * @param group_data C type : DDS_GroupDataQosPolicy<br>
	 * @param entity_factory C type : DDS_EntityFactoryQosPolicy<br>
	 * @param share C type : DDS_ShareQosPolicy
	 */
	public DDS_SubscriberQos(DDS_PresentationQosPolicy presentation, DDS_PartitionQosPolicy partition, DDS_GroupDataQosPolicy group_data, DDS_EntityFactoryQosPolicy entity_factory, DDS_ShareQosPolicy share) {
		super();
		this.presentation = presentation;
		this.partition = partition;
		this.group_data = group_data;
		this.entity_factory = entity_factory;
		this.share = share;
	}
	public DDS_SubscriberQos(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_SubscriberQos implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_SubscriberQos implements Structure.ByValue {
		
	};
}
