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
package org.eclipse.cyclonedds.ddsc.dds_public_alloc;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class dds_aligned_allocator extends Structure {
	/** C type : alloc_callback* */
	public dds_aligned_allocator.alloc_callback alloc;
	public dds_aligned_allocator.alloc_callback getAlloc() {
		return alloc;
	}
	public void setAlloc(dds_aligned_allocator.alloc_callback alloc) {
		this.alloc = alloc;
	}
	/** C type : free_callback* */
	public dds_aligned_allocator.free_callback free;
	public dds_aligned_allocator.free_callback getFree() {
		return free;
	}
	public void setFree(dds_aligned_allocator.free_callback free) {
		this.free = free;
	}
	public interface alloc_callback extends Callback {
		Pointer apply(NativeSize size, NativeSize align);
	};
	public interface free_callback extends Callback {
		void apply(NativeSize size, Pointer ptr);
	};
	public dds_aligned_allocator() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("alloc", "free");
	}
	/**
	 * @param alloc C type : alloc_callback*<br>
	 * @param free C type : free_callback*
	 */
	public dds_aligned_allocator(dds_aligned_allocator.alloc_callback alloc, dds_aligned_allocator.free_callback free) {
		super();
		this.alloc = alloc;
		this.free = free;
	}
	public dds_aligned_allocator(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_aligned_allocator implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_aligned_allocator implements Structure.ByValue {
		
	};
}
