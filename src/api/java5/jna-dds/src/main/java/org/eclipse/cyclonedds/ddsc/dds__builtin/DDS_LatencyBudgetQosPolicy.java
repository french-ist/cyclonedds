package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:114</i><br>
 */
public class DDS_LatencyBudgetQosPolicy extends Structure {
	/** C type : DDS_Duration_t */
	public DDS_Duration_t duration;
	public DDS_Duration_t getDuration() {
		return duration;
	}
	public void setDuration(DDS_Duration_t duration) {
		this.duration = duration;
	}
	public DDS_LatencyBudgetQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("duration");
	}
	/** @param duration C type : DDS_Duration_t */
	public DDS_LatencyBudgetQosPolicy(DDS_Duration_t duration) {
		super();
		this.duration = duration;
	}
	public DDS_LatencyBudgetQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_LatencyBudgetQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_LatencyBudgetQosPolicy implements Structure.ByValue {
		
	};
}
