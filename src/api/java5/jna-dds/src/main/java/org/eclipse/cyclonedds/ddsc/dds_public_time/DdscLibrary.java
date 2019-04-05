/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds_public_time;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
/**
 * JNA Wrapper for library <b>ddsc</b><br>
 */
public class DdscLibrary implements Library {
	public static final String JNA_LIBRARY_NAME = "ddsc";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(DdscLibrary.JNA_LIBRARY_NAME);
	static {
		Native.register(DdscLibrary.class, DdscLibrary.JNA_NATIVE_LIB);
	}
	public static final long DDS_NSECS_IN_SEC = (long)1000000000L;
	public static final long DDS_NSECS_IN_MSEC = (long)1000000L;
	public static final long DDS_NSECS_IN_USEC = (long)1000L;
	/**
	 * Description : This operation returns the current time (in nanoseconds)<br>
	 * Arguments :<br>
	 *   -# Returns current time<br>
	 * Original signature : <code>dds_time_t dds_time()</code><br>
	 */
	public static native long dds_time();
	/**
	 * Description : This operation blocks the calling thread until the relative time<br>
	 * n has elapsed<br>
	 * Arguments :<br>
	 *   -# n Relative Time to block a thread<br>
	 * Original signature : <code>void dds_sleepfor(dds_duration_t)</code><br>
	 */
	public static native void dds_sleepfor(long n);
	/**
	 * Description : This operation blocks the calling thread until the absolute time<br>
	 * n has elapsed<br>
	 * Arguments :<br>
	 *   -# n absolute Time to block a thread<br>
	 * Original signature : <code>void dds_sleepuntil(dds_time_t)</code><br>
	 */
	public static native void dds_sleepuntil(long n);
}
