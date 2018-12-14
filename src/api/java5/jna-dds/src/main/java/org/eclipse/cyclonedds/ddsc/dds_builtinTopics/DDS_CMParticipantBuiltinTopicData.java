package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_builtinTopics.h:299</i><br>
 */
public class DDS_CMParticipantBuiltinTopicData extends Structure {
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
	public DDS_CMParticipantBuiltinTopicData() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("key", "product");
	}
	/**
	 * @param key C type : DDS_BuiltinTopicKey_t<br>
	 * @param product C type : DDS_ProductDataQosPolicy
	 */
	public DDS_CMParticipantBuiltinTopicData(int key[], DDS_ProductDataQosPolicy product) {
		super();
		if ((key.length != this.key.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.key = key;
		this.product = product;
	}
	public DDS_CMParticipantBuiltinTopicData(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_CMParticipantBuiltinTopicData implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_CMParticipantBuiltinTopicData implements Structure.ByValue {
		
	};
}
