/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds__subscriber;
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
	 * Original signature : <code>dds_entity_t dds__create_subscriber_l(dds_entity*, const dds_qos_t*, const dds_listener_t*)</code><br>
	 * @param participant entity-lock must be held<br>
	 */
	public static native int dds__create_subscriber_l(DdscLibrary.dds_entity participant, DdscLibrary.dds_qos_t qos, DdscLibrary.dds_listener_t listener);
	/**
	 * Original signature : <code>dds_return_t dds_subscriber_begin_coherent(dds_entity_t)</code><br>
	 */
	public static native int dds_subscriber_begin_coherent(int e);
	/**
	 * Original signature : <code>dds_return_t dds_subscriber_end_coherent(dds_entity_t)</code><br>
	 */
	public static native int dds_subscriber_end_coherent(int e);
	public static class dds_qos_t extends PointerType {
		public dds_qos_t(Pointer address) {
			super(address);
		}
		public dds_qos_t() {
			super();
		}
	};
	public static class dds_listener_t extends PointerType {
		public dds_listener_t(Pointer address) {
			super(address);
		}
		public dds_listener_t() {
			super();
		}
	};
	public static class dds_entity extends PointerType {
		public dds_entity(Pointer address) {
			super(address);
		}
		public dds_entity() {
			super();
		}
	};
}
