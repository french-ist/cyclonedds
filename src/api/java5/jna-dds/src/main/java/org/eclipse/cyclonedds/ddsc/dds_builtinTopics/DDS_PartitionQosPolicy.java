package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_builtinTopics.h:139</i><br>
 */
public class DDS_PartitionQosPolicy extends Structure {
	/** C type : DDS_StringSeq */
	public DDS_StringSeq name;
	public DDS_StringSeq getName() {
		return name;
	}
	public void setName(DDS_StringSeq name) {
		this.name = name;
	}
	public DDS_PartitionQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("name");
	}
	/** @param name C type : DDS_StringSeq */
	public DDS_PartitionQosPolicy(DDS_StringSeq name) {
		super();
		this.name = name;
	}
	public DDS_PartitionQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_PartitionQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_PartitionQosPolicy implements Structure.ByValue {
		
	};
}
