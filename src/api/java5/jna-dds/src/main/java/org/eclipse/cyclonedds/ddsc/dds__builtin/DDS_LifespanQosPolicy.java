package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:89</i><br>
 */
public class DDS_LifespanQosPolicy extends Structure {
	/** C type : DDS_Duration_t */
	public DDS_Duration_t duration;
	public DDS_Duration_t getDuration() {
		return duration;
	}
	public void setDuration(DDS_Duration_t duration) {
		this.duration = duration;
	}
	public DDS_LifespanQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("duration");
	}
	/** @param duration C type : DDS_Duration_t */
	public DDS_LifespanQosPolicy(DDS_Duration_t duration) {
		super();
		this.duration = duration;
	}
	public DDS_LifespanQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_LifespanQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_LifespanQosPolicy implements Structure.ByValue {
		
	};
}
