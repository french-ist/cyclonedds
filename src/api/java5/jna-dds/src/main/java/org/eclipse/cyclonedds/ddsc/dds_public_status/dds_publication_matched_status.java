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
public class dds_publication_matched_status extends Structure {
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
	public int current_count;
	public int getCurrent_count() {
		return current_count;
	}
	public void setCurrent_count(int current_count) {
		this.current_count = current_count;
	}
	public int current_count_change;
	public int getCurrent_count_change() {
		return current_count_change;
	}
	public void setCurrent_count_change(int current_count_change) {
		this.current_count_change = current_count_change;
	}
	/** C type : dds_instance_handle_t */
	public long last_subscription_handle;
	public long getLast_subscription_handle() {
		return last_subscription_handle;
	}
	public void setLast_subscription_handle(long last_subscription_handle) {
		this.last_subscription_handle = last_subscription_handle;
	}
	public dds_publication_matched_status() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("total_count", "total_count_change", "current_count", "current_count_change", "last_subscription_handle");
	}
	/** @param last_subscription_handle C type : dds_instance_handle_t */
	public dds_publication_matched_status(int total_count, int total_count_change, int current_count, int current_count_change, long last_subscription_handle) {
		super();
		this.total_count = total_count;
		this.total_count_change = total_count_change;
		this.current_count = current_count;
		this.current_count_change = current_count_change;
		this.last_subscription_handle = last_subscription_handle;
	}
	public dds_publication_matched_status(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_publication_matched_status implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_publication_matched_status implements Structure.ByValue {
		
	};
}
