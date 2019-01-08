package org.eclipse.cyclonedds.ddsc.dds__write;
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
	 * enum values
	 */
	public static interface dds_write_action {
		public static final int DDS_WR_ACTION_WRITE = 0;
		public static final int DDS_WR_ACTION_WRITE_DISPOSE = 0x02;
		public static final int DDS_WR_ACTION_DISPOSE = 0x01 | 0x02;
		public static final int DDS_WR_ACTION_UNREGISTER = 0x01 | 0x04;
	};
	public static final int DDS_WR_KEY_BIT = (int)0x01;
	public static final int DDS_WR_DISPOSE_BIT = (int)0x02;
	public static final int DDS_WR_UNREGISTER_BIT = (int)0x04;
	/**
	 * Original signature : <code>int dds_write_impl(dds_writer*, const void*, dds_time_t, dds_write_action)</code><br>
	 */
	public static native int dds_write_impl(DdscLibrary.dds_writer wr, Pointer data, long tstamp, int action);
	/**
	 * Original signature : <code>int dds_writecdr_impl(dds_writer*, ddsi_serdata*, dds_time_t, dds_write_action)</code><br>
	 */
	public static native int dds_writecdr_impl(DdscLibrary.dds_writer wr, DdscLibrary.ddsi_serdata d, long tstamp, int action);
	public static class dds_writer extends PointerType {
		public dds_writer(Pointer address) {
			super(address);
		}
		public dds_writer() {
			super();
		}
	};
	public static class ddsi_serdata extends PointerType {
		public ddsi_serdata(Pointer address) {
			super(address);
		}
		public ddsi_serdata() {
			super();
		}
	};
}
