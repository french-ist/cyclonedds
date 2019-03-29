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
package org.eclipse.cyclonedds.ddsc.dds__key;
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
	 * Original signature : <code>void dds_key_md5(dds_key_hash*)</code><br>
	 */
	public static native void dds_key_md5(DdscLibrary.dds_key_hash kh);
	/**
	 * Original signature : <code>void dds_key_gen(const const dds_topic_descriptor_t*, dds_key_hash*, const char*)</code><br>
	 */
	public static native void dds_key_gen(DdscLibrary.dds_topic_descriptor_t desc, DdscLibrary.dds_key_hash kh, String sample);
	public static class dds_key_hash extends PointerType {
		public dds_key_hash(Pointer address) {
			super(address);
		}
		public dds_key_hash() {
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
}
