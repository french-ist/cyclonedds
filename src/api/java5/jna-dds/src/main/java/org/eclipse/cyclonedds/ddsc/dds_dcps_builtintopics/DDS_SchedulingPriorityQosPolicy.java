package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_dcps_builtintopics.h:361</i><br>
 */
public class DDS_SchedulingPriorityQosPolicy extends Structure {
	/**
	 * @see DDS_SchedulingPriorityQosPolicyKind<br>
	 * C type : DDS_SchedulingPriorityQosPolicyKind
	 */
	public int kind;
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public DDS_SchedulingPriorityQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("kind");
	}
	/**
	 * @param kind @see DDS_SchedulingPriorityQosPolicyKind<br>
	 * C type : DDS_SchedulingPriorityQosPolicyKind
	 */
	public DDS_SchedulingPriorityQosPolicy(int kind) {
		super();
		this.kind = kind;
	}
	public DDS_SchedulingPriorityQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_SchedulingPriorityQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_SchedulingPriorityQosPolicy implements Structure.ByValue {
		
	};
}
