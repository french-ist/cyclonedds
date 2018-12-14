package org.eclipse.cyclonedds.ddsc.dds_public_status;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_public_status.h:115</i><br>
 */
public class dds_requested_incompatible_qos_status extends Structure {
	public int total_count;
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public int total_count_change;
	public int getTotal_count_change() {
		return total_count_change;
	}
	public void setTotal_count_change(int total_count_change) {
		this.total_count_change = total_count_change;
	}
	public int last_policy_id;
	public int getLast_policy_id() {
		return last_policy_id;
	}
	public void setLast_policy_id(int last_policy_id) {
		this.last_policy_id = last_policy_id;
	}
	public dds_requested_incompatible_qos_status() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("total_count", "total_count_change", "last_policy_id");
	}
	public dds_requested_incompatible_qos_status(int total_count, int total_count_change, int last_policy_id) {
		super();
		this.total_count = total_count;
		this.total_count_change = total_count_change;
		this.last_policy_id = last_policy_id;
	}
	public dds_requested_incompatible_qos_status(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_requested_incompatible_qos_status implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_requested_incompatible_qos_status implements Structure.ByValue {
		
	};
}
