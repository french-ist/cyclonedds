package org.eclipse.cyclonedds.ddsc.dds_public_error;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
/**
 * JNA Wrapper for library <b>ddsc</b><br>
 */
public class DdscLibrary implements Library {
	public static final String JNA_LIBRARY_NAME = "ddsc";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(DdscLibrary.JNA_LIBRARY_NAME);
	static {
		Native.register(DdscLibrary.class, DdscLibrary.JNA_NATIVE_LIB);
	}
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_ERR_NR_MASK = (int)0x000000ff;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_ERR_LINE_MASK = (int)0x003fff00;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_ERR_FILE_ID_MASK = (int)0x7fc00000;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_OK = (int)0;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_ERROR = (int)1;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_UNSUPPORTED = (int)2;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_BAD_PARAMETER = (int)3;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_PRECONDITION_NOT_MET = (int)4;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_OUT_OF_RESOURCES = (int)5;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_NOT_ENABLED = (int)6;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_IMMUTABLE_POLICY = (int)7;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_INCONSISTENT_POLICY = (int)8;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_ALREADY_DELETED = (int)9;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_TIMEOUT = (int)10;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_NO_DATA = (int)11;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_ILLEGAL_OPERATION = (int)12;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_RETCODE_NOT_ALLOWED_BY_SECURITY = (int)13;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_SUCCESS = (int)0;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_CHECK_REPORT = (int)0x01;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_CHECK_FAIL = (int)0x02;
	/** <i>native declaration : _dds_public_error.h</i> */
	public static final int DDS_CHECK_EXIT = (int)0x04;
	/** <i>native declaration : _dds_public_error.h:68</i> */
	public interface dds_fail_fn extends Callback {
		void apply(Pointer charPtr1, Pointer charPtr2);
	};
	/**
	 * @brief Takes the error value and outputs a string corresponding to it.<br>
	 * @param[in]  err  Error value to be converted to a string<br>
	 * @returns  String corresponding to the error value<br>
	 * Original signature : <code>char* dds_err_str(dds_return_t)</code><br>
	 * <i>native declaration : _dds_public_error.h:55</i>
	 */
	public static native String dds_err_str(int err);
	/**
	 * @brief Takes the error number, error type and filename and line number and formats it to<br>
	 * a string which can be used for debugging.<br>
	 * @param[in]  err    Error value<br>
	 * @param[in]  flags  Indicates Fail, Exit or Report<br>
	 * @param[in]  where  File and line number<br>
	 * @returns  true - True<br>
	 * @returns  false - False<br>
	 * Original signature : <code>bool dds_err_check(dds_return_t, unsigned, const char*)</code><br>
	 * <i>native declaration : _dds_public_error.h:66</i>
	 */
	public static native byte dds_err_check(int err, int flags, String where);
	/**
	 * @brief Set the failure function<br>
	 * @param[in]  fn  Function to invoke on failure<br>
	 * Original signature : <code>void dds_fail_set(dds_fail_fn)</code><br>
	 * <i>native declaration : _dds_public_error.h:74</i>
	 */
	public static native void dds_fail_set(DdscLibrary.dds_fail_fn fn);
	/**
	 * @brief Get the failure function<br>
	 * @returns Failure function<br>
	 * Original signature : <code>dds_fail_fn dds_fail_get()</code><br>
	 * <i>native declaration : _dds_public_error.h:80</i>
	 */
	public static native DdscLibrary.dds_fail_fn dds_fail_get();
	/**
	 * @brief Handles failure through an installed failure handler<br>
	 * @params[in]  msg  String containing failure message<br>
	 * @params[in]  where  String containing file and location<br>
	 * Original signature : <code>void dds_fail(const char*, const char*)</code><br>
	 * <i>native declaration : _dds_public_error.h:87</i>
	 */
	public static native void dds_fail(String msg, String where);
}
