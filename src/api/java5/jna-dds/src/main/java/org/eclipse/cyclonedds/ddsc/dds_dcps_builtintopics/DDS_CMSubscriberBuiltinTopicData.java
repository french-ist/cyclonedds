package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:318</i><br>
 */
public class DDS_CMSubscriberBuiltinTopicData extends Structure {
	/** C type : DDS_BuiltinTopicKey_t */
	public int[] key = new int[3];
	public int[] getKey() {
		return key;
	}
	public void setKey(int key[]) {
		this.key = key;
	}
	/** C type : DDS_ProductDataQosPolicy */
	public DDS_ProductDataQosPolicy product;
	public DDS_ProductDataQosPolicy getProduct() {
		return product;
	}
	public void setProduct(DDS_ProductDataQosPolicy product) {
		this.product = product;
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
	public Pointer name;
	public Pointer getName() {
		return name;
	}
	public void setName(Pointer name) {
		this.name = name;
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
	/** C type : DDS_PartitionQosPolicy */
	public DDS_PartitionQosPolicy partition;
	public DDS_PartitionQosPolicy getPartition() {
		return partition;
	}
	public void setPartition(DDS_PartitionQosPolicy partition) {
		this.partition = partition;
	}
	public DDS_CMSubscriberBuiltinTopicData() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("key", "product", "participant_key", "name", "entity_factory", "share", "partition");
	}
	/**
	 * @param key C type : DDS_BuiltinTopicKey_t<br>
	 * @param product C type : DDS_ProductDataQosPolicy<br>
	 * @param participant_key C type : DDS_BuiltinTopicKey_t<br>
	 * @param name C type : char*<br>
	 * @param entity_factory C type : DDS_EntityFactoryQosPolicy<br>
	 * @param share C type : DDS_ShareQosPolicy<br>
	 * @param partition C type : DDS_PartitionQosPolicy
	 */
	public DDS_CMSubscriberBuiltinTopicData(int key[], DDS_ProductDataQosPolicy product, int participant_key[], Pointer name, DDS_EntityFactoryQosPolicy entity_factory, DDS_ShareQosPolicy share, DDS_PartitionQosPolicy partition) {
		super();
		if ((key.length != this.key.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.key = key;
		this.product = product;
		if ((participant_key.length != this.participant_key.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.participant_key = participant_key;
		this.name = name;
		this.entity_factory = entity_factory;
		this.share = share;
		this.partition = partition;
	}
	public DDS_CMSubscriberBuiltinTopicData(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_CMSubscriberBuiltinTopicData implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_CMSubscriberBuiltinTopicData implements Structure.ByValue {
		
	};
}
