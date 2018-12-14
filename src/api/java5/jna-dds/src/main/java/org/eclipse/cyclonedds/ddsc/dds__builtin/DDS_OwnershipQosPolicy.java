package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:121</i><br>
 */
public class DDS_OwnershipQosPolicy extends Structure {
	/**
	 * @see DDS_OwnershipQosPolicyKind<br>
	 * C type : DDS_OwnershipQosPolicyKind
	 */
	public int kind;
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public DDS_OwnershipQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("kind");
	}
	/**
	 * @param kind @see DDS_OwnershipQosPolicyKind<br>
	 * C type : DDS_OwnershipQosPolicyKind
	 */
	public DDS_OwnershipQosPolicy(int kind) {
		super();
		this.kind = kind;
	}
	public DDS_OwnershipQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_OwnershipQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_OwnershipQosPolicy implements Structure.ByValue {
		
	};
}
