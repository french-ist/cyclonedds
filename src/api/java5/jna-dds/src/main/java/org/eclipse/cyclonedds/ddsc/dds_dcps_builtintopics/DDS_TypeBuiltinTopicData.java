package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:253</i><br>
 */
public class DDS_TypeBuiltinTopicData extends Structure {
	/** C type : char* */
	public Pointer name;
	public Pointer getName() {
		return name;
	}
	public void setName(Pointer name) {
		this.name = name;
	}
	/** C type : DDS_DataRepresentationId_t */
	public short data_representation_id;
	public short getData_representation_id() {
		return data_representation_id;
	}
	public void setData_representation_id(short data_representation_id) {
		this.data_representation_id = data_representation_id;
	}
	/** C type : DDS_TypeHash */
	public DDS_TypeHash type_hash;
	public DDS_TypeHash getType_hash() {
		return type_hash;
	}
	public void setType_hash(DDS_TypeHash type_hash) {
		this.type_hash = type_hash;
	}
	/** C type : DDS_octSeq */
	public DDS_octSeq meta_data;
	public DDS_octSeq getMeta_data() {
		return meta_data;
	}
	public void setMeta_data(DDS_octSeq meta_data) {
		this.meta_data = meta_data;
	}
	/** C type : DDS_octSeq */
	public DDS_octSeq extentions;
	public DDS_octSeq getExtentions() {
		return extentions;
	}
	public void setExtentions(DDS_octSeq extentions) {
		this.extentions = extentions;
	}
	public DDS_TypeBuiltinTopicData() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("name", "data_representation_id", "type_hash", "meta_data", "extentions");
	}
	/**
	 * @param name C type : char*<br>
	 * @param data_representation_id C type : DDS_DataRepresentationId_t<br>
	 * @param type_hash C type : DDS_TypeHash<br>
	 * @param meta_data C type : DDS_octSeq<br>
	 * @param extentions C type : DDS_octSeq
	 */
	public DDS_TypeBuiltinTopicData(Pointer name, short data_representation_id, DDS_TypeHash type_hash, DDS_octSeq meta_data, DDS_octSeq extentions) {
		super();
		this.name = name;
		this.data_representation_id = data_representation_id;
		this.type_hash = type_hash;
		this.meta_data = meta_data;
		this.extentions = extentions;
	}
	public DDS_TypeBuiltinTopicData(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_TypeBuiltinTopicData implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_TypeBuiltinTopicData implements Structure.ByValue {
		
	};
}
