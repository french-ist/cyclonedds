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
	 * <i>native declaration : _dds__write.h:63</i><br>
	 * enum values
	 */
	public static interface dds_write_action {
		/** <i>native declaration : _dds__write.h:59</i> */
		public static final int DDS_WR_ACTION_WRITE = 0;
		/** <i>native declaration : _dds__write.h:60</i> */
		public static final int DDS_WR_ACTION_WRITE_DISPOSE = 0x02;
		/** <i>native declaration : _dds__write.h:61</i> */
		public static final int DDS_WR_ACTION_DISPOSE = 0x01 | 0x02;
		/** <i>native declaration : _dds__write.h:62</i> */
		public static final int DDS_WR_ACTION_UNREGISTER = 0x01 | 0x04;
	};
	/** <i>native declaration : _dds__write.h</i> */
	public static final int DDS_WR_KEY_BIT = (int)0x01;
	/** <i>native declaration : _dds__write.h</i> */
	public static final int DDS_WR_DISPOSE_BIT = (int)0x02;
	/** <i>native declaration : _dds__write.h</i> */
	public static final int DDS_WR_UNREGISTER_BIT = (int)0x04;
	/**
	 * Original signature : <code>int dds_write_impl(dds_writer*, const void*, dds_time_t, dds_write_action)</code><br>
	 * <i>native declaration : _dds__write.h:65</i>
	 */
	public static native int dds_write_impl(DdscLibrary.dds_writer wr, Pointer data, long tstamp, int action);
	/**
	 * Original signature : <code>int dds_writecdr_impl(dds_writer*, serdata*, dds_time_t, dds_write_action)</code><br>
	 * <i>native declaration : _dds__write.h:67</i>
	 */
	public static native int dds_writecdr_impl(DdscLibrary.dds_writer wr, DdscLibrary.serdata d, long tstamp, int action);
	public static class serdata extends PointerType {
		public serdata(Pointer address) {
			super(address);
		}
		public serdata() {
			super();
		}
	};
	public static class dds_writer extends PointerType {
		public dds_writer(Pointer address) {
			super(address);
		}
		public dds_writer() {
			super();
		}
	};
}
