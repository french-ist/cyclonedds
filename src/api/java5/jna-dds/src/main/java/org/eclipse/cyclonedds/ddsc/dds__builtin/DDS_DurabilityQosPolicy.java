package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:98</i><br>
 */
public class DDS_DurabilityQosPolicy extends Structure {
	/**
	 * @see DDS_DurabilityQosPolicyKind<br>
	 * C type : DDS_DurabilityQosPolicyKind
	 */
	public int kind;
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public DDS_DurabilityQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("kind");
	}
	/**
	 * @param kind @see DDS_DurabilityQosPolicyKind<br>
	 * C type : DDS_DurabilityQosPolicyKind
	 */
	public DDS_DurabilityQosPolicy(int kind) {
		super();
		this.kind = kind;
	}
	public DDS_DurabilityQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_DurabilityQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_DurabilityQosPolicy implements Structure.ByValue {
		
	};
}
