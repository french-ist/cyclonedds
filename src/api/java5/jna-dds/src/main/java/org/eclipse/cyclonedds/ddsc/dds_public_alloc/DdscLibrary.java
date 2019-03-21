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
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
/**
 * JNA Wrapper for library <b>ddsc</b><br>
 */
public class DdscLibrary implements Library {
	public static final String JNA_LIBRARY_NAME = "ddsc";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(DdscLibrary.JNA_LIBRARY_NAME);
	static {
		Native.register(DdscLibrary.class, DdscLibrary.JNA_NATIVE_LIB);
	}
	/**
	 * enum values
	 */
	public static interface dds_free_op_t {
		public static final int DDS_FREE_ALL = 0x01 | 0x02 | 0x04;
		public static final int DDS_FREE_CONTENTS = 0x01 | 0x02;
		public static final int DDS_FREE_KEY = 0x01;
	};
	public static final int DDS_FREE_KEY_BIT = (int)0x01;
	public static final int DDS_FREE_CONTENTS_BIT = (int)0x02;
	public static final int DDS_FREE_ALL_BIT = (int)0x04;
	public interface dds_alloc_fn_t extends Callback {
		Pointer apply(NativeSize size_t1);
	};
	public interface dds_realloc_fn_t extends Callback {
		Pointer apply(Pointer voidPtr1, NativeSize size_t1);
	};
	public interface dds_free_fn_t extends Callback {
		void apply(Pointer voidPtr1);
	};
	/**
	 * Original signature : <code>void* dds_alloc(size_t)</code><br>
	 */
	public static native Pointer dds_alloc(NativeSize size);
	/**
	 * Original signature : <code>void* dds_realloc(void*, size_t)</code><br>
	 */
	public static native Pointer dds_realloc(Pointer ptr, NativeSize size);
	/**
	 * Original signature : <code>void* dds_realloc_zero(void*, size_t)</code><br>
	 */
	public static native Pointer dds_realloc_zero(Pointer ptr, NativeSize size);
	/**
	 * Original signature : <code>void dds_free(void*)</code><br>
	 */
	public static native void dds_free(Pointer ptr);
	/**
	 * Original signature : <code>char* dds_string_alloc(size_t)</code><br>
	 */
	public static native String dds_string_alloc(NativeSize size);
	/**
	 * Original signature : <code>char* dds_string_dup(const char*)</code><br>
	 */
	public static native String dds_string_dup(String str);
	/**
	 * Original signature : <code>void dds_string_free(char*)</code><br>
	 */
	public static native void dds_string_free(String str);
	/**
	 * Original signature : <code>void dds_sample_free(void*, dds_topic_descriptor*, dds_free_op_t)</code><br>
	 */
	public static native void dds_sample_free(Pointer sample, DdscLibrary.dds_topic_descriptor desc, int op);
	public static native void dds_sample_free(Pointer sample, org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor.ByReference desc, int op);
	// TODO : use dds_topic_descriptor.java
	public static class dds_topic_descriptor extends PointerType {
		public dds_topic_descriptor(Pointer address) {
			super(address);
		}
		public dds_topic_descriptor() {
			super();
		}
	};
}
