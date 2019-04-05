/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
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
	 * Get actual topic in related participant related to topic 'id'.<br>
	 * Original signature : <code>dds_entity_t dds__get_builtin_topic(dds_entity_t, dds_entity_t)</code><br>
	 */
	public static native int dds__get_builtin_topic(int e, int topic);
	/**
	 * Global publisher singleton (publishes only locally).<br>
	 * Original signature : <code>dds_entity_t dds__get_builtin_publisher()</code><br>
	 */
	public static native int dds__get_builtin_publisher();
	/**
	 * Subscriber singleton within related participant.<br>
	 * Original signature : <code>dds_entity_t dds__get_builtin_subscriber(dds_entity_t)</code><br>
	 */
	public static native int dds__get_builtin_subscriber(int e);
	/**
	 * Checks whether the reader QoS is valid for use with built-in topic TOPIC<br>
	 * Original signature : <code>bool dds__validate_builtin_reader_qos(dds_entity_t, const dds_qos_t*)</code><br>
	 */
	public static native byte dds__validate_builtin_reader_qos(int topic, DdscLibrary.dds_qos_t qos);
	/**
	 * Initialization and finalize functions.<br>
	 * Original signature : <code>void dds__builtin_init()</code><br>
	 */
	public static native void dds__builtin_init();
	/**
	 * Original signature : <code>void dds__builtin_fini()</code><br>
	 */
	public static native void dds__builtin_fini();
	/**
	 * Original signature : <code>void dds__builtin_write(ddsi_sertopic_builtin_type, const nn_guid_t*, nn_wctime_t, bool)</code><br>
	 */
	public static native void dds__builtin_write(int type, DdscLibrary.nn_guid_t guid, DdscLibrary.nn_wctime_t timestamp, byte alive);
	public static class nn_guid_t extends PointerType {
		public nn_guid_t(Pointer address) {
			super(address);
		}
		public nn_guid_t() {
			super();
		}
	};
	public static class nn_wctime_t extends PointerType {
		public nn_wctime_t(Pointer address) {
			super(address);
		}
		public nn_wctime_t() {
			super();
		}
	};
	public static class dds_qos_t extends PointerType {
		public dds_qos_t(Pointer address) {
			super(address);
		}
		public dds_qos_t() {
			super();
		}
	};
}
