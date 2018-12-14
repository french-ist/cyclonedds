package org.eclipse.cyclonedds.ddsc.dds_public_log;
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
	/**
	 * @file<br>
	 * @brief DDS C Logging API<br>
	 * This header file defines the public API for logging in the<br>
	 * CycloneDDS C language binding.<br>
	 * Original signature : <code>void dds_log_info(const char*, null)</code><br>
	 * <i>native declaration : _dds_public_log.h:56</i>
	 */
	public static native void dds_log_info(String fmt, Object... varArgs1);
	/**
	 * Original signature : <code>void dds_log_warn(const char*, null)</code><br>
	 * <i>native declaration : _dds_public_log.h:58</i>
	 */
	public static native void dds_log_warn(String fmt, Object... varArgs1);
	/**
	 * Original signature : <code>void dds_log_error(const char*, null)</code><br>
	 * <i>native declaration : _dds_public_log.h:60</i>
	 */
	public static native void dds_log_error(String fmt, Object... varArgs1);
	/**
	 * Original signature : <code>void dds_log_fatal(const char*, null)</code><br>
	 * <i>native declaration : _dds_public_log.h:62</i>
	 */
	public static native void dds_log_fatal(String fmt, Object... varArgs1);
}
