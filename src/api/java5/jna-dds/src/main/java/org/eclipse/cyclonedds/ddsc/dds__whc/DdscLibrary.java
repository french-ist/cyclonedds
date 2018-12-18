package org.eclipse.cyclonedds.ddsc.dds__whc;
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
	 * Original signature : <code>whc* whc_new(int, unsigned, unsigned)</code><br>
	 */
	public static native DdscLibrary.whc whc_new(int is_transient_local, int hdepth, int tldepth);
	public static class whc extends PointerType {
		public whc(Pointer address) {
			super(address);
		}
		public whc() {
			super();
		}
	};
}
