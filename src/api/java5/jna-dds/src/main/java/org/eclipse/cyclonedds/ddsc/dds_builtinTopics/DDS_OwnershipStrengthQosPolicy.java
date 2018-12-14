package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_builtinTopics.h:124</i><br>
 */
public class DDS_OwnershipStrengthQosPolicy extends Structure {
	public int value;
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public DDS_OwnershipStrengthQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("value");
	}
	public DDS_OwnershipStrengthQosPolicy(int value) {
		super();
		this.value = value;
	}
	public DDS_OwnershipStrengthQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_OwnershipStrengthQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_OwnershipStrengthQosPolicy implements Structure.ByValue {
		
	};
}
