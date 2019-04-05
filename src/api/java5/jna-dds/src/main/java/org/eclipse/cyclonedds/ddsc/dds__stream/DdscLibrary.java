/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds__stream;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import java.nio.IntBuffer;
/**
 * JNA Wrapper for library <b>ddsc</b><br>
 */
public class DdscLibrary implements Library {
	public static final String JNA_LIBRARY_NAME = "ddsc";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(DdscLibrary.JNA_LIBRARY_NAME);
	static {
		Native.register(DdscLibrary.class, DdscLibrary.JNA_NATIVE_LIB);
	}
	public static final int DDS_OP_MASK = (int)0xff000000;
	public static final int DDS_OP_TYPE_MASK = (int)0x00ff0000;
	public static final int DDS_OP_SUBTYPE_MASK = (int)0x0000ff00;
	public static final int DDS_OP_JMP_MASK = (int)0x0000ffff;
	public static final int DDS_OP_FLAGS_MASK = (int)0x000000ff;
	public static final int DDS_JEQ_TYPE_MASK = (int)0x00ff0000;
	/**
	 * Copyright(c) 2006 to 2018 ADLINK Technology Limited and others<br>
	 * This program and the accompanying materials are made available under the<br>
	 * terms of the Eclipse Public License v. 2.0 which is available at<br>
	 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License<br>
	 * v. 1.0 which is available at<br>
	 * http://www.eclipse.org/org/documents/edl-v10.php.<br>
	 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause<br>
	 * Original signature : <code>void dds_stream_write_sample(dds_stream_t*, const void*, ddsi_sertopic_default*)</code><br>
	 */
	public static native void dds_stream_write_sample(DdscLibrary.dds_stream_t os, Pointer data, DdscLibrary.ddsi_sertopic_default topic);
	/**
	 * Original signature : <code>void dds_stream_read_sample(dds_stream_t*, void*, ddsi_sertopic_default*)</code><br>
	 */
	public static native void dds_stream_read_sample(DdscLibrary.dds_stream_t is, Pointer data, DdscLibrary.ddsi_sertopic_default topic);
	/**
	 * Original signature : <code>size_t dds_stream_check_optimize(const dds_topic_descriptor_t*)</code><br>
	 */
	public static native NativeSize dds_stream_check_optimize(DdscLibrary.dds_topic_descriptor_t desc);
	/**
	 * Original signature : <code>void dds_stream_from_serdata_default(dds_stream_t*, ddsi_serdata_default*)</code><br>
	 */
	public static native void dds_stream_from_serdata_default(DdscLibrary.dds_stream_t s, DdscLibrary.ddsi_serdata_default d);
	/**
	 * Original signature : <code>void dds_stream_add_to_serdata_default(dds_stream_t*, ddsi_serdata_default**)</code><br>
	 */
	public static native void dds_stream_add_to_serdata_default(DdscLibrary.dds_stream_t s, DdscLibrary.ddsi_serdata_default d[]);
	/**
	 * Original signature : <code>void dds_stream_write_key(dds_stream_t*, const char*, ddsi_sertopic_default*)</code><br>
	 */
	public static native void dds_stream_write_key(DdscLibrary.dds_stream_t os, String sample, DdscLibrary.ddsi_sertopic_default topic);
	/**
	 * Original signature : <code>uint32_t dds_stream_extract_key(dds_stream_t*, dds_stream_t*, const uint32_t*, const bool)</code><br>
	 */
	public static native int dds_stream_extract_key(DdscLibrary.dds_stream_t is, DdscLibrary.dds_stream_t os, IntBuffer ops, byte just_key);
	/**
	 * Original signature : <code>void dds_stream_read_key(dds_stream_t*, char*, const dds_topic_descriptor_t*)</code><br>
	 */
	public static native void dds_stream_read_key(DdscLibrary.dds_stream_t is, String sample, DdscLibrary.dds_topic_descriptor_t desc);
	/**
	 * Original signature : <code>void dds_stream_read_keyhash(dds_stream_t*, dds_key_hash_t*, const dds_topic_descriptor_t*, const bool)</code><br>
	 */
	public static native void dds_stream_read_keyhash(DdscLibrary.dds_stream_t is, DdscLibrary.dds_key_hash_t kh, DdscLibrary.dds_topic_descriptor_t desc, byte just_key);
	/**
	 * Original signature : <code>char* dds_stream_reuse_string(dds_stream_t*, char*, const uint32_t)</code><br>
	 */
	public static native String dds_stream_reuse_string(DdscLibrary.dds_stream_t is, String str, int bound);
	/**
	 * Original signature : <code>void dds_stream_swap(void*, uint32_t, uint32_t)</code><br>
	 */
	public static native void dds_stream_swap(Pointer buff, int size, int num);
	public static class ddsi_sertopic_default extends PointerType {
		public ddsi_sertopic_default(Pointer address) {
			super(address);
		}
		public ddsi_sertopic_default() {
			super();
		}
	};
	public static class ddsi_serdata_default extends PointerType {
		public ddsi_serdata_default(Pointer address) {
			super(address);
		}
		public ddsi_serdata_default() {
			super();
		}
	};
	public static class dds_stream_t extends PointerType {
		public dds_stream_t(Pointer address) {
			super(address);
		}
		public dds_stream_t() {
			super();
		}
	};
	public static class dds_topic_descriptor_t extends PointerType {
		public dds_topic_descriptor_t(Pointer address) {
			super(address);
		}
		public dds_topic_descriptor_t() {
			super();
		}
	};
	public static class dds_key_hash_t extends PointerType {
		public dds_key_hash_t(Pointer address) {
			super(address);
		}
		public dds_key_hash_t() {
			super();
		}
	};
}
