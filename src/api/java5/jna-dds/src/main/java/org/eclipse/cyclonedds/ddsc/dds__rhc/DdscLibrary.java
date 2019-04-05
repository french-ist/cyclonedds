/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds__rhc;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.PointerByReference;
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
	 * Original signature : <code>rhc* dds_rhc_new(dds_reader*, ddsi_sertopic*)</code><br>
	 */
	public static native DdscLibrary.rhc dds_rhc_new(DdscLibrary.dds_reader reader, DdscLibrary.ddsi_sertopic topic);
	/**
	 * Original signature : <code>void dds_rhc_free(rhc*)</code><br>
	 */
	public static native void dds_rhc_free(DdscLibrary.rhc rhc);
	/**
	 * Original signature : <code>void dds_rhc_fini(rhc*)</code><br>
	 */
	public static native void dds_rhc_fini(DdscLibrary.rhc rhc);
	/**
	 * Original signature : <code>uint32_t dds_rhc_lock_samples(rhc*)</code><br>
	 */
	public static native int dds_rhc_lock_samples(DdscLibrary.rhc rhc);
	/**
	 * Original signature : <code>bool dds_rhc_store(__declspec(__restrict) rhc*, __declspec(__restrict) proxy_writer_info*, __declspec(__restrict) ddsi_serdata*, __declspec(__restrict) ddsi_tkmap_instance*)</code><br>
	 */
	public static native byte dds_rhc_store(DdscLibrary.rhc rhc, DdscLibrary.proxy_writer_info pwr_info, DdscLibrary.ddsi_serdata sample, DdscLibrary.ddsi_tkmap_instance tk);
	/**
	 * Original signature : <code>void dds_rhc_unregister_wr(__declspec(__restrict) rhc*, __declspec(__restrict) proxy_writer_info*)</code><br>
	 */
	public static native void dds_rhc_unregister_wr(DdscLibrary.rhc rhc, DdscLibrary.proxy_writer_info pwr_info);
	/**
	 * Original signature : <code>void dds_rhc_relinquish_ownership(__declspec(__restrict) rhc*, const uint64_t)</code><br>
	 */
	public static native void dds_rhc_relinquish_ownership(DdscLibrary.rhc rhc, long wr_iid);
	/**
	 * Original signature : <code>int dds_rhc_read(rhc*, bool, void**, dds_sample_info_t*, uint32_t, uint32_t, dds_instance_handle_t, dds_readcond*)</code><br>
	 */
	public static native int dds_rhc_read(DdscLibrary.rhc rhc, byte lock, PointerByReference values, DdscLibrary.dds_sample_info_t info_seq, int max_samples, int mask, long handle, DdscLibrary.dds_readcond cond);
	/**
	 * Original signature : <code>int dds_rhc_take(rhc*, bool, void**, dds_sample_info_t*, uint32_t, uint32_t, dds_instance_handle_t, dds_readcond*)</code><br>
	 */
	public static native int dds_rhc_take(DdscLibrary.rhc rhc, byte lock, PointerByReference values, DdscLibrary.dds_sample_info_t info_seq, int max_samples, int mask, long handle, DdscLibrary.dds_readcond cond);
	/**
	 * Original signature : <code>void dds_rhc_set_qos(rhc*, nn_xqos*)</code><br>
	 */
	public static native void dds_rhc_set_qos(DdscLibrary.rhc rhc, DdscLibrary.nn_xqos qos);
	/**
	 * Original signature : <code>void dds_rhc_add_readcondition(dds_readcond*)</code><br>
	 */
	public static native void dds_rhc_add_readcondition(DdscLibrary.dds_readcond cond);
	/**
	 * Original signature : <code>void dds_rhc_remove_readcondition(dds_readcond*)</code><br>
	 */
	public static native void dds_rhc_remove_readcondition(DdscLibrary.dds_readcond cond);
	/**
	 * Original signature : <code>int dds_rhc_takecdr(rhc*, bool, ddsi_serdata**, dds_sample_info_t*, uint32_t, unsigned, unsigned, unsigned, dds_instance_handle_t)</code><br>
	 */
	public static native int dds_rhc_takecdr(DdscLibrary.rhc rhc, byte lock, DdscLibrary.ddsi_serdata values[], DdscLibrary.dds_sample_info_t info_seq, int max_samples, int sample_states, int view_states, int instance_states, long handle);
	public static class ddsi_serdata extends PointerType {
		public ddsi_serdata(Pointer address) {
			super(address);
		}
		public ddsi_serdata() {
			super();
		}
	};
	public static class dds_reader extends PointerType {
		public dds_reader(Pointer address) {
			super(address);
		}
		public dds_reader() {
			super();
		}
	};
	public static class ddsi_sertopic extends PointerType {
		public ddsi_sertopic(Pointer address) {
			super(address);
		}
		public ddsi_sertopic() {
			super();
		}
	};
	public static class proxy_writer_info extends PointerType {
		public proxy_writer_info(Pointer address) {
			super(address);
		}
		public proxy_writer_info() {
			super();
		}
	};
	public static class nn_xqos extends PointerType {
		public nn_xqos(Pointer address) {
			super(address);
		}
		public nn_xqos() {
			super();
		}
	};
	public static class dds_readcond extends PointerType {
		public dds_readcond(Pointer address) {
			super(address);
		}
		public dds_readcond() {
			super();
		}
	};
	public static class rhc extends PointerType {
		public rhc(Pointer address) {
			super(address);
		}
		public rhc() {
			super();
		}
	};
	public static class ddsi_tkmap_instance extends PointerType {
		public ddsi_tkmap_instance(Pointer address) {
			super(address);
		}
		public ddsi_tkmap_instance() {
			super();
		}
	};
	public static class dds_sample_info_t extends PointerType {
		public dds_sample_info_t(Pointer address) {
			super(address);
		}
		public dds_sample_info_t() {
			super();
		}
	};
}
