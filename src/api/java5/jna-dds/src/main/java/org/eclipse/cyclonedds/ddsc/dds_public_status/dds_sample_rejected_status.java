/**
 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds_public_status;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class dds_sample_rejected_status extends Structure {
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
	/**
	 * @see dds_sample_rejected_status_kind<br>
	 * C type : dds_sample_rejected_status_kind
	 */
	public int last_reason;
	public int getLast_reason() {
		return last_reason;
	}
	public void setLast_reason(int last_reason) {
		this.last_reason = last_reason;
	}
	/** C type : dds_instance_handle_t */
	public long last_instance_handle;
	public long getLast_instance_handle() {
		return last_instance_handle;
	}
	public void setLast_instance_handle(long last_instance_handle) {
		this.last_instance_handle = last_instance_handle;
	}
	public dds_sample_rejected_status() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("total_count", "total_count_change", "last_reason", "last_instance_handle");
	}
	/**
	 * @param last_reason @see dds_sample_rejected_status_kind<br>
	 * C type : dds_sample_rejected_status_kind<br>
	 * @param last_instance_handle C type : dds_instance_handle_t
	 */
	public dds_sample_rejected_status(int total_count, int total_count_change, int last_reason, long last_instance_handle) {
		super();
		this.total_count = total_count;
		this.total_count_change = total_count_change;
		this.last_reason = last_reason;
		this.last_instance_handle = last_instance_handle;
	}
	public dds_sample_rejected_status(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_sample_rejected_status implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_sample_rejected_status implements Structure.ByValue {
		
	};
}
