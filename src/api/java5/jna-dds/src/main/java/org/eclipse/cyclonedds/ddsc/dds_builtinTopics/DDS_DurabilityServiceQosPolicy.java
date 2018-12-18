package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class DDS_DurabilityServiceQosPolicy extends Structure {
	/** C type : DDS_Duration_t */
	public DDS_Duration_t service_cleanup_delay;
	public DDS_Duration_t getService_cleanup_delay() {
		return service_cleanup_delay;
	}
	public void setService_cleanup_delay(DDS_Duration_t service_cleanup_delay) {
		this.service_cleanup_delay = service_cleanup_delay;
	}
	/**
	 * @see DDS_HistoryQosPolicyKind<br>
	 * C type : DDS_HistoryQosPolicyKind
	 */
	public int history_kind;
	public int getHistory_kind() {
		return history_kind;
	}
	public void setHistory_kind(int history_kind) {
		this.history_kind = history_kind;
	}
	public int history_depth;
	public int getHistory_depth() {
		return history_depth;
	}
	public void setHistory_depth(int history_depth) {
		this.history_depth = history_depth;
	}
	public int max_samples;
	public int getMax_samples() {
		return max_samples;
	}
	public void setMax_samples(int max_samples) {
		this.max_samples = max_samples;
	}
	public int max_instances;
	public int getMax_instances() {
		return max_instances;
	}
	public void setMax_instances(int max_instances) {
		this.max_instances = max_instances;
	}
	public int max_samples_per_instance;
	public int getMax_samples_per_instance() {
		return max_samples_per_instance;
	}
	public void setMax_samples_per_instance(int max_samples_per_instance) {
		this.max_samples_per_instance = max_samples_per_instance;
	}
	public DDS_DurabilityServiceQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("service_cleanup_delay", "history_kind", "history_depth", "max_samples", "max_instances", "max_samples_per_instance");
	}
	/**
	 * @param service_cleanup_delay C type : DDS_Duration_t<br>
	 * @param history_kind @see DDS_HistoryQosPolicyKind<br>
	 * C type : DDS_HistoryQosPolicyKind
	 */
	public DDS_DurabilityServiceQosPolicy(DDS_Duration_t service_cleanup_delay, int history_kind, int history_depth, int max_samples, int max_instances, int max_samples_per_instance) {
		super();
		this.service_cleanup_delay = service_cleanup_delay;
		this.history_kind = history_kind;
		this.history_depth = history_depth;
		this.max_samples = max_samples;
		this.max_instances = max_instances;
		this.max_samples_per_instance = max_samples_per_instance;
	}
	public DDS_DurabilityServiceQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_DurabilityServiceQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_DurabilityServiceQosPolicy implements Structure.ByValue {
		
	};
}
