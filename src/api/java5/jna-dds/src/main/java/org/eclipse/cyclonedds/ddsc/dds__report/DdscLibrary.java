package org.eclipse.cyclonedds.ddsc.dds__report;
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
	 * Original signature : <code>void dds_report(os_reportType, const char*, int32_t, const char*, dds_return_t, const char*, null)</code><br>
	 * <i>native declaration : _dds__report.h:53</i>
	 */
	public static native void dds_report(DdscLibrary.os_reportType reportType, String file, int line, String function, int code, String format, Object... varArgs1);
	public static class os_reportType extends PointerType {
		public os_reportType(Pointer address) {
			super(address);
		}
		public os_reportType() {
			super();
		}
	};
}
