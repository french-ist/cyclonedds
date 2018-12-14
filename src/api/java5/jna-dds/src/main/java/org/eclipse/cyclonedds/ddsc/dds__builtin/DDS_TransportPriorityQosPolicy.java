package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:86</i><br>
 */
public class DDS_TransportPriorityQosPolicy extends Structure {
	public int value;
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public DDS_TransportPriorityQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("value");
	}
	public DDS_TransportPriorityQosPolicy(int value) {
		super();
		this.value = value;
	}
	public DDS_TransportPriorityQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_TransportPriorityQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_TransportPriorityQosPolicy implements Structure.ByValue {
		
	};
}
