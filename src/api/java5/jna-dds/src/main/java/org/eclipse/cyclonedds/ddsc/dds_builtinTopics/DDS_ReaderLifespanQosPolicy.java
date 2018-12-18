package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class DDS_ReaderLifespanQosPolicy extends Structure {
	public byte use_lifespan;
	public byte getUse_lifespan() {
		return use_lifespan;
	}
	public void setUse_lifespan(byte use_lifespan) {
		this.use_lifespan = use_lifespan;
	}
	/** C type : DDS_Duration_t */
	public DDS_Duration_t duration;
	public DDS_Duration_t getDuration() {
		return duration;
	}
	public void setDuration(DDS_Duration_t duration) {
		this.duration = duration;
	}
	public DDS_ReaderLifespanQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("use_lifespan", "duration");
	}
	/** @param duration C type : DDS_Duration_t */
	public DDS_ReaderLifespanQosPolicy(byte use_lifespan, DDS_Duration_t duration) {
		super();
		this.use_lifespan = use_lifespan;
		this.duration = duration;
	}
	public DDS_ReaderLifespanQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_ReaderLifespanQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_ReaderLifespanQosPolicy implements Structure.ByValue {
		
	};
}
