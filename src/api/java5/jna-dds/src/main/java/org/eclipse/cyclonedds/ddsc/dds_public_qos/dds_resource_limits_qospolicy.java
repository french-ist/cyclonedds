package org.eclipse.cyclonedds.ddsc.dds_public_qos;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_public_qos.h:94</i><br>
 */
public class dds_resource_limits_qospolicy extends Structure {
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
	public dds_resource_limits_qospolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("max_samples", "max_instances", "max_samples_per_instance");
	}
	public dds_resource_limits_qospolicy(int max_samples, int max_instances, int max_samples_per_instance) {
		super();
		this.max_samples = max_samples;
		this.max_instances = max_instances;
		this.max_samples_per_instance = max_samples_per_instance;
	}
	public dds_resource_limits_qospolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_resource_limits_qospolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_resource_limits_qospolicy implements Structure.ByValue {
		
	};
}
