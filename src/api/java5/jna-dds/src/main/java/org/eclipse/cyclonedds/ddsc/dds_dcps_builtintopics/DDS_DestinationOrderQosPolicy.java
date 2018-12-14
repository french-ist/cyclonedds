package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:155</i><br>
 */
public class DDS_DestinationOrderQosPolicy extends Structure {
	/**
	 * @see DDS_DestinationOrderQosPolicyKind<br>
	 * C type : DDS_DestinationOrderQosPolicyKind
	 */
	public int kind;
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public DDS_DestinationOrderQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("kind");
	}
	/**
	 * @param kind @see DDS_DestinationOrderQosPolicyKind<br>
	 * C type : DDS_DestinationOrderQosPolicyKind
	 */
	public DDS_DestinationOrderQosPolicy(int kind) {
		super();
		this.kind = kind;
	}
	public DDS_DestinationOrderQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_DestinationOrderQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_DestinationOrderQosPolicy implements Structure.ByValue {
		
	};
}
