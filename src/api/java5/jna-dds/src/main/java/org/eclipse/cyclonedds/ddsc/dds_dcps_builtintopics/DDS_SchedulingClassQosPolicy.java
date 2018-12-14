package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_dcps_builtintopics.h:354</i><br>
 */
public class DDS_SchedulingClassQosPolicy extends Structure {
	/**
	 * @see DDS_SchedulingClassQosPolicyKind<br>
	 * C type : DDS_SchedulingClassQosPolicyKind
	 */
	public int kind;
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public DDS_SchedulingClassQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("kind");
	}
	/**
	 * @param kind @see DDS_SchedulingClassQosPolicyKind<br>
	 * C type : DDS_SchedulingClassQosPolicyKind
	 */
	public DDS_SchedulingClassQosPolicy(int kind) {
		super();
		this.kind = kind;
	}
	public DDS_SchedulingClassQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_SchedulingClassQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_SchedulingClassQosPolicy implements Structure.ByValue {
		
	};
}
