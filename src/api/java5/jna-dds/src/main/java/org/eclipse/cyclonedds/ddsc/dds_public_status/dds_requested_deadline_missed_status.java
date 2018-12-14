package org.eclipse.cyclonedds.ddsc.dds_public_status;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_public_status.h:109</i><br>
 */
public class dds_requested_deadline_missed_status extends Structure {
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
	/** C type : dds_instance_handle_t */
	public long last_instance_handle;
	public long getLast_instance_handle() {
		return last_instance_handle;
	}
	public void setLast_instance_handle(long last_instance_handle) {
		this.last_instance_handle = last_instance_handle;
	}
	public dds_requested_deadline_missed_status() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("total_count", "total_count_change", "last_instance_handle");
	}
	/** @param last_instance_handle C type : dds_instance_handle_t */
	public dds_requested_deadline_missed_status(int total_count, int total_count_change, long last_instance_handle) {
		super();
		this.total_count = total_count;
		this.total_count_change = total_count_change;
		this.last_instance_handle = last_instance_handle;
	}
	public dds_requested_deadline_missed_status(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_requested_deadline_missed_status implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_requested_deadline_missed_status implements Structure.ByValue {
		
	};
}
