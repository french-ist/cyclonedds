package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class DDS_WriterDataLifecycleQosPolicy extends Structure {
	public byte autodispose_unregistered_instances;
	public byte getAutodispose_unregistered_instances() {
		return autodispose_unregistered_instances;
	}
	public void setAutodispose_unregistered_instances(byte autodispose_unregistered_instances) {
		this.autodispose_unregistered_instances = autodispose_unregistered_instances;
	}
	/** C type : DDS_Duration_t */
	public DDS_Duration_t autopurge_suspended_samples_delay;
	public DDS_Duration_t getAutopurge_suspended_samples_delay() {
		return autopurge_suspended_samples_delay;
	}
	public void setAutopurge_suspended_samples_delay(DDS_Duration_t autopurge_suspended_samples_delay) {
		this.autopurge_suspended_samples_delay = autopurge_suspended_samples_delay;
	}
	/** C type : DDS_Duration_t */
	public DDS_Duration_t autounregister_instance_delay;
	public DDS_Duration_t getAutounregister_instance_delay() {
		return autounregister_instance_delay;
	}
	public void setAutounregister_instance_delay(DDS_Duration_t autounregister_instance_delay) {
		this.autounregister_instance_delay = autounregister_instance_delay;
	}
	public DDS_WriterDataLifecycleQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("autodispose_unregistered_instances", "autopurge_suspended_samples_delay", "autounregister_instance_delay");
	}
	/**
	 * @param autopurge_suspended_samples_delay C type : DDS_Duration_t<br>
	 * @param autounregister_instance_delay C type : DDS_Duration_t
	 */
	public DDS_WriterDataLifecycleQosPolicy(byte autodispose_unregistered_instances, DDS_Duration_t autopurge_suspended_samples_delay, DDS_Duration_t autounregister_instance_delay) {
		super();
		this.autodispose_unregistered_instances = autodispose_unregistered_instances;
		this.autopurge_suspended_samples_delay = autopurge_suspended_samples_delay;
		this.autounregister_instance_delay = autounregister_instance_delay;
	}
	public DDS_WriterDataLifecycleQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_WriterDataLifecycleQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_WriterDataLifecycleQosPolicy implements Structure.ByValue {
		
	};
}
