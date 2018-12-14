package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:83</i><br>
 */
public class DDS_GroupDataQosPolicy extends Structure {
	/** C type : DDS_octSeq */
	public DDS_octSeq value;
	public DDS_octSeq getValue() {
		return value;
	}
	public void setValue(DDS_octSeq value) {
		this.value = value;
	}
	public DDS_GroupDataQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("value");
	}
	/** @param value C type : DDS_octSeq */
	public DDS_GroupDataQosPolicy(DDS_octSeq value) {
		super();
		this.value = value;
	}
	public DDS_GroupDataQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_GroupDataQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_GroupDataQosPolicy implements Structure.ByValue {
		
	};
}
