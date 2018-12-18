package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class DDS_DeadlineQosPolicy extends Structure {
	/** C type : DDS_Duration_t */
	public DDS_Duration_t period;
	public DDS_Duration_t getPeriod() {
		return period;
	}
	public void setPeriod(DDS_Duration_t period) {
		this.period = period;
	}
	public DDS_DeadlineQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("period");
	}
	/** @param period C type : DDS_Duration_t */
	public DDS_DeadlineQosPolicy(DDS_Duration_t period) {
		super();
		this.period = period;
	}
	public DDS_DeadlineQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_DeadlineQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_DeadlineQosPolicy implements Structure.ByValue {
		
	};
}
