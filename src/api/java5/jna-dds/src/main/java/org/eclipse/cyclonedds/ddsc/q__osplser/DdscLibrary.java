package org.eclipse.cyclonedds.ddsc.q__osplser;
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
	 * Copyright(c) 2006 to 2018 ADLINK Technology Limited and others<br>
	 * This program and the accompanying materials are made available under the<br>
	 * terms of the Eclipse Public License v. 2.0 which is available at<br>
	 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License<br>
	 * v. 1.0 which is available at<br>
	 * http://www.eclipse.org/org/documents/edl-v10.php.<br>
	 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause<br>
	 * Original signature : <code>int serdata_cmp(serdata*, serdata*)</code><br>
	 */
	public static native int serdata_cmp(DdscLibrary.serdata a, DdscLibrary.serdata b);
	/**
	 * Original signature : <code>serdata_t serialize(sertopic*, const void*)</code><br>
	 */
	public static native DdscLibrary.serdata_t serialize(DdscLibrary.sertopic tp, Pointer sample);
	/**
	 * Original signature : <code>serdata_t serialize_key(sertopic*, const void*)</code><br>
	 */
	public static native DdscLibrary.serdata_t serialize_key(DdscLibrary.sertopic tp, Pointer sample);
	/**
	 * Original signature : <code>void deserialize_into(void*, serdata*)</code><br>
	 */
	public static native void deserialize_into(Pointer sample, DdscLibrary.serdata serdata);
	/**
	 * Original signature : <code>void free_deserialized(serdata*, void*)</code><br>
	 */
	public static native void free_deserialized(DdscLibrary.serdata serdata, Pointer vx);
	/**
	 * Original signature : <code>void sertopic_free(sertopic*)</code><br>
	 */
	public static native void sertopic_free(DdscLibrary.sertopic tp);
	/**
	 * Original signature : <code>void serstate_set_key(serstate_t, int, const void*)</code><br>
	 */
	public static native void serstate_set_key(DdscLibrary.serstate_t st, int justkey, Pointer key);
	/**
	 * Original signature : <code>void serstate_init(serstate_t, sertopic*)</code><br>
	 */
	public static native void serstate_init(DdscLibrary.serstate_t st, DdscLibrary.sertopic topic);
	/**
	 * Original signature : <code>void serstate_free(serstate_t)</code><br>
	 */
	public static native void serstate_free(DdscLibrary.serstate_t st);
	public static class serdata extends PointerType {
		public serdata(Pointer address) {
			super(address);
		}
		public serdata() {
			super();
		}
	};
	public static class serdata_t extends PointerType {
		public serdata_t(Pointer address) {
			super(address);
		}
		public serdata_t() {
			super();
		}
	};
	public static class sertopic extends PointerType {
		public sertopic(Pointer address) {
			super(address);
		}
		public sertopic() {
			super();
		}
	};
	public static class serstate_t extends PointerType {
		public serstate_t(Pointer address) {
			super(address);
		}
		public serstate_t() {
			super();
		}
	};
}
