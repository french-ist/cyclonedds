package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class DDS_ReaderDataLifecycleQosPolicy extends Structure {
	/** C type : DDS_Duration_t */
	public DDS_Duration_t autopurge_nowriter_samples_delay;
	public DDS_Duration_t getAutopurge_nowriter_samples_delay() {
		return autopurge_nowriter_samples_delay;
	}
	public void setAutopurge_nowriter_samples_delay(DDS_Duration_t autopurge_nowriter_samples_delay) {
		this.autopurge_nowriter_samples_delay = autopurge_nowriter_samples_delay;
	}
	/** C type : DDS_Duration_t */
	public DDS_Duration_t autopurge_disposed_samples_delay;
	public DDS_Duration_t getAutopurge_disposed_samples_delay() {
		return autopurge_disposed_samples_delay;
	}
	public void setAutopurge_disposed_samples_delay(DDS_Duration_t autopurge_disposed_samples_delay) {
		this.autopurge_disposed_samples_delay = autopurge_disposed_samples_delay;
	}
	public byte autopurge_dispose_all;
	public byte getAutopurge_dispose_all() {
		return autopurge_dispose_all;
	}
	public void setAutopurge_dispose_all(byte autopurge_dispose_all) {
		this.autopurge_dispose_all = autopurge_dispose_all;
	}
	public byte enable_invalid_samples;
	public byte getEnable_invalid_samples() {
		return enable_invalid_samples;
	}
	public void setEnable_invalid_samples(byte enable_invalid_samples) {
		this.enable_invalid_samples = enable_invalid_samples;
	}
	/** C type : DDS_InvalidSampleVisibilityQosPolicy */
	public DDS_InvalidSampleVisibilityQosPolicy invalid_sample_visibility;
	public DDS_InvalidSampleVisibilityQosPolicy getInvalid_sample_visibility() {
		return invalid_sample_visibility;
	}
	public void setInvalid_sample_visibility(DDS_InvalidSampleVisibilityQosPolicy invalid_sample_visibility) {
		this.invalid_sample_visibility = invalid_sample_visibility;
	}
	public DDS_ReaderDataLifecycleQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("autopurge_nowriter_samples_delay", "autopurge_disposed_samples_delay", "autopurge_dispose_all", "enable_invalid_samples", "invalid_sample_visibility");
	}
	/**
	 * @param autopurge_nowriter_samples_delay C type : DDS_Duration_t<br>
	 * @param autopurge_disposed_samples_delay C type : DDS_Duration_t<br>
	 * @param invalid_sample_visibility C type : DDS_InvalidSampleVisibilityQosPolicy
	 */
	public DDS_ReaderDataLifecycleQosPolicy(DDS_Duration_t autopurge_nowriter_samples_delay, DDS_Duration_t autopurge_disposed_samples_delay, byte autopurge_dispose_all, byte enable_invalid_samples, DDS_InvalidSampleVisibilityQosPolicy invalid_sample_visibility) {
		super();
		this.autopurge_nowriter_samples_delay = autopurge_nowriter_samples_delay;
		this.autopurge_disposed_samples_delay = autopurge_disposed_samples_delay;
		this.autopurge_dispose_all = autopurge_dispose_all;
		this.enable_invalid_samples = enable_invalid_samples;
		this.invalid_sample_visibility = invalid_sample_visibility;
	}
	public DDS_ReaderDataLifecycleQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_ReaderDataLifecycleQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_ReaderDataLifecycleQosPolicy implements Structure.ByValue {
		
	};
}
