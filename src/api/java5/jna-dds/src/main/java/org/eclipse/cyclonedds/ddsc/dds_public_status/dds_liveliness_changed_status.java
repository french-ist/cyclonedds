/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds_public_status;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class dds_liveliness_changed_status extends Structure {
	public int alive_count;
	public int getAlive_count() {
		return alive_count;
	}
	public void setAlive_count(int alive_count) {
		this.alive_count = alive_count;
	}
	public int not_alive_count;
	public int getNot_alive_count() {
		return not_alive_count;
	}
	public void setNot_alive_count(int not_alive_count) {
		this.not_alive_count = not_alive_count;
	}
	public int alive_count_change;
	public int getAlive_count_change() {
		return alive_count_change;
	}
	public void setAlive_count_change(int alive_count_change) {
		this.alive_count_change = alive_count_change;
	}
	public int not_alive_count_change;
	public int getNot_alive_count_change() {
		return not_alive_count_change;
	}
	public void setNot_alive_count_change(int not_alive_count_change) {
		this.not_alive_count_change = not_alive_count_change;
	}
	/** C type : dds_instance_handle_t */
	public long last_publication_handle;
	public long getLast_publication_handle() {
		return last_publication_handle;
	}
	public void setLast_publication_handle(long last_publication_handle) {
		this.last_publication_handle = last_publication_handle;
	}
	public dds_liveliness_changed_status() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("alive_count", "not_alive_count", "alive_count_change", "not_alive_count_change", "last_publication_handle");
	}
	/** @param last_publication_handle C type : dds_instance_handle_t */
	public dds_liveliness_changed_status(int alive_count, int not_alive_count, int alive_count_change, int not_alive_count_change, long last_publication_handle) {
		super();
		this.alive_count = alive_count;
		this.not_alive_count = not_alive_count;
		this.alive_count_change = alive_count_change;
		this.not_alive_count_change = not_alive_count_change;
		this.last_publication_handle = last_publication_handle;
	}
	public dds_liveliness_changed_status(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_liveliness_changed_status implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_liveliness_changed_status implements Structure.ByValue {
		
	};
}
