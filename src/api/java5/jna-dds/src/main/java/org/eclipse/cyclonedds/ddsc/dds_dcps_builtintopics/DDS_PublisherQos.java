package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.ddsc.dds_builtinTopics.*;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class DDS_PublisherQos extends Structure {
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
	public DDS_PublisherQos() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("presentation", "partition", "group_data", "entity_factory");
	}
	/**
	 * @param presentation C type : DDS_PresentationQosPolicy<br>
	 * @param partition C type : DDS_PartitionQosPolicy<br>
	 * @param group_data C type : DDS_GroupDataQosPolicy<br>
	 * @param entity_factory C type : DDS_EntityFactoryQosPolicy
	 */
	public DDS_PublisherQos(DDS_PresentationQosPolicy presentation, DDS_PartitionQosPolicy partition, DDS_GroupDataQosPolicy group_data, DDS_EntityFactoryQosPolicy entity_factory) {
		super();
		this.presentation = presentation;
		this.partition = partition;
		this.group_data = group_data;
		this.entity_factory = entity_factory;
	}
	public DDS_PublisherQos(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_PublisherQos implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_PublisherQos implements Structure.ByValue {
		
	};
}
