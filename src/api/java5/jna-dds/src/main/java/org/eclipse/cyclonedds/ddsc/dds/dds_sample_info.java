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
package org.eclipse.cyclonedds.ddsc.dds;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class dds_sample_info extends Structure {
	/** C type : dds_sample_state_t */
	public int sample_state;
	public int getSample_state() {
		return sample_state;
	}
	public void setSample_state(int sample_state) {
		this.sample_state = sample_state;
	}
	/** C type : dds_view_state_t */
	public int view_state;
	public int getView_state() {
		return view_state;
	}
	public void setView_state(int view_state) {
		this.view_state = view_state;
	}
	/** C type : dds_instance_state_t */
	public int instance_state;
	public int getInstance_state() {
		return instance_state;
	}
	public void setInstance_state(int instance_state) {
		this.instance_state = instance_state;
	}
	public byte valid_data;
	public byte getValid_data() {
		return valid_data;
	}
	public void setValid_data(byte valid_data) {
		this.valid_data = valid_data;
	}
	/** C type : dds_time_t */
	public long source_timestamp;
	public long getSource_timestamp() {
		return source_timestamp;
	}
	public void setSource_timestamp(long source_timestamp) {
		this.source_timestamp = source_timestamp;
	}
	/** C type : dds_instance_handle_t */
	public long instance_handle;
	public long getInstance_handle() {
		return instance_handle;
	}
	public void setInstance_handle(long instance_handle) {
		this.instance_handle = instance_handle;
	}
	/** C type : dds_instance_handle_t */
	public long publication_handle;
	public long getPublication_handle() {
		return publication_handle;
	}
	public void setPublication_handle(long publication_handle) {
		this.publication_handle = publication_handle;
	}
	public int disposed_generation_count;
	public int getDisposed_generation_count() {
		return disposed_generation_count;
	}
	public void setDisposed_generation_count(int disposed_generation_count) {
		this.disposed_generation_count = disposed_generation_count;
	}
	public int no_writers_generation_count;
	public int getNo_writers_generation_count() {
		return no_writers_generation_count;
	}
	public void setNo_writers_generation_count(int no_writers_generation_count) {
		this.no_writers_generation_count = no_writers_generation_count;
	}
	public int sample_rank;
	public int getSample_rank() {
		return sample_rank;
	}
	public void setSample_rank(int sample_rank) {
		this.sample_rank = sample_rank;
	}
	public int generation_rank;
	public int getGeneration_rank() {
		return generation_rank;
	}
	public void setGeneration_rank(int generation_rank) {
		this.generation_rank = generation_rank;
	}
	public int absolute_generation_rank;
	public int getAbsolute_generation_rank() {
		return absolute_generation_rank;
	}
	public void setAbsolute_generation_rank(int absolute_generation_rank) {
		this.absolute_generation_rank = absolute_generation_rank;
	}
	public dds_sample_info() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("sample_state", "view_state", "instance_state", "valid_data", "source_timestamp", "instance_handle", "publication_handle", "disposed_generation_count", "no_writers_generation_count", "sample_rank", "generation_rank", "absolute_generation_rank");
	}
	public dds_sample_info(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_sample_info implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_sample_info implements Structure.ByValue {
		
	};
}
