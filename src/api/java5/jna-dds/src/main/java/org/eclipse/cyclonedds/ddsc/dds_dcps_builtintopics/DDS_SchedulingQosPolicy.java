package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_dcps_builtintopics.h:366</i><br>
 */
public class DDS_SchedulingQosPolicy extends Structure {
	/** C type : DDS_SchedulingClassQosPolicy */
	public DDS_SchedulingClassQosPolicy scheduling_class;
	public DDS_SchedulingClassQosPolicy getScheduling_class() {
		return scheduling_class;
	}
	public void setScheduling_class(DDS_SchedulingClassQosPolicy scheduling_class) {
		this.scheduling_class = scheduling_class;
	}
	/** C type : DDS_SchedulingPriorityQosPolicy */
	public DDS_SchedulingPriorityQosPolicy scheduling_priority_kind;
	public DDS_SchedulingPriorityQosPolicy getScheduling_priority_kind() {
		return scheduling_priority_kind;
	}
	public void setScheduling_priority_kind(DDS_SchedulingPriorityQosPolicy scheduling_priority_kind) {
		this.scheduling_priority_kind = scheduling_priority_kind;
	}
	public int scheduling_priority;
	public int getScheduling_priority() {
		return scheduling_priority;
	}
	public void setScheduling_priority(int scheduling_priority) {
		this.scheduling_priority = scheduling_priority;
	}
	public DDS_SchedulingQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("scheduling_class", "scheduling_priority_kind", "scheduling_priority");
	}
	/**
	 * @param scheduling_class C type : DDS_SchedulingClassQosPolicy<br>
	 * @param scheduling_priority_kind C type : DDS_SchedulingPriorityQosPolicy
	 */
	public DDS_SchedulingQosPolicy(DDS_SchedulingClassQosPolicy scheduling_class, DDS_SchedulingPriorityQosPolicy scheduling_priority_kind, int scheduling_priority) {
		super();
		this.scheduling_class = scheduling_class;
		this.scheduling_priority_kind = scheduling_priority_kind;
		this.scheduling_priority = scheduling_priority;
	}
	public DDS_SchedulingQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_SchedulingQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_SchedulingQosPolicy implements Structure.ByValue {
		
	};
}
