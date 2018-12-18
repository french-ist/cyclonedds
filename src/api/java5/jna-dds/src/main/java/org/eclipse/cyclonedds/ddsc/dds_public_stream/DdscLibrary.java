package org.eclipse.cyclonedds.ddsc.dds_public_stream;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import java.nio.ByteBuffer;
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
	 * Original signature : <code>dds_stream_t* dds_stream_create(size_t)</code><br>
	 */
	public static native dds_stream dds_stream_create(NativeSize size);
	/**
	 * Original signature : <code>void dds_stream_delete(dds_stream_t*)</code><br>
	 */
	public static native void dds_stream_delete(dds_stream st);
	/**
	 * Original signature : <code>void dds_stream_fini(dds_stream_t*)</code><br>
	 */
	public static native void dds_stream_fini(dds_stream st);
	/**
	 * Original signature : <code>void dds_stream_reset(dds_stream_t*)</code><br>
	 */
	public static native void dds_stream_reset(dds_stream st);
	/**
	 * Original signature : <code>void dds_stream_init(dds_stream_t*, size_t)</code><br>
	 */
	public static native void dds_stream_init(dds_stream st, NativeSize size);
	/**
	 * Original signature : <code>void dds_stream_grow(dds_stream_t*, size_t)</code><br>
	 */
	public static native void dds_stream_grow(dds_stream st, NativeSize size);
	/**
	 * Original signature : <code>bool dds_stream_endian()</code><br>
	 */
	public static native byte dds_stream_endian();
	/**
	 * Original signature : <code>void dds_stream_read_sample_w_desc(dds_stream_t*, void*, dds_topic_descriptor*)</code><br>
	 */
	public static native void dds_stream_read_sample_w_desc(dds_stream is, Pointer data, DdscLibrary.dds_topic_descriptor desc);
	/**
	 * Original signature : <code>bool dds_stream_read_bool(dds_stream_t*)</code><br>
	 */
	public static native byte dds_stream_read_bool(dds_stream is);
	/**
	 * Original signature : <code>uint8_t dds_stream_read_uint8(dds_stream_t*)</code><br>
	 */
	public static native byte dds_stream_read_uint8(dds_stream is);
	/**
	 * Original signature : <code>uint16_t dds_stream_read_uint16(dds_stream_t*)</code><br>
	 */
	public static native short dds_stream_read_uint16(dds_stream is);
	/**
	 * Original signature : <code>uint32_t dds_stream_read_uint32(dds_stream_t*)</code><br>
	 */
	public static native int dds_stream_read_uint32(dds_stream is);
	/**
	 * Original signature : <code>uint64_t dds_stream_read_uint64(dds_stream_t*)</code><br>
	 */
	public static native long dds_stream_read_uint64(dds_stream is);
	/**
	 * Original signature : <code>float dds_stream_read_float(dds_stream_t*)</code><br>
	 */
	public static native float dds_stream_read_float(dds_stream is);
	/**
	 * Original signature : <code>double dds_stream_read_double(dds_stream_t*)</code><br>
	 */
	public static native double dds_stream_read_double(dds_stream is);
	/**
	 * Original signature : <code>char* dds_stream_read_string(dds_stream_t*)</code><br>
	 */
	public static native String dds_stream_read_string(dds_stream is);
	/**
	 * Original signature : <code>void dds_stream_read_buffer(dds_stream_t*, uint8_t*, uint32_t)</code><br>
	 */
	public static native void dds_stream_read_buffer(dds_stream is, ByteBuffer buffer, int len);
	/**
	 * Original signature : <code>void dds_stream_write_bool(dds_stream_t*, bool)</code><br>
	 */
	public static native void dds_stream_write_bool(dds_stream os, byte val);
	/**
	 * Original signature : <code>void dds_stream_write_uint8(dds_stream_t*, uint8_t)</code><br>
	 */
	public static native void dds_stream_write_uint8(dds_stream os, byte val);
	/**
	 * Original signature : <code>void dds_stream_write_uint16(dds_stream_t*, uint16_t)</code><br>
	 */
	public static native void dds_stream_write_uint16(dds_stream os, short val);
	/**
	 * Original signature : <code>void dds_stream_write_uint32(dds_stream_t*, uint32_t)</code><br>
	 */
	public static native void dds_stream_write_uint32(dds_stream os, int val);
	/**
	 * Original signature : <code>void dds_stream_write_uint64(dds_stream_t*, uint64_t)</code><br>
	 */
	public static native void dds_stream_write_uint64(dds_stream os, long val);
	/**
	 * Original signature : <code>void dds_stream_write_float(dds_stream_t*, float)</code><br>
	 */
	public static native void dds_stream_write_float(dds_stream os, float val);
	/**
	 * Original signature : <code>void dds_stream_write_double(dds_stream_t*, double)</code><br>
	 */
	public static native void dds_stream_write_double(dds_stream os, double val);
	/**
	 * Original signature : <code>void dds_stream_write_string(dds_stream_t*, const char*)</code><br>
	 */
	public static native void dds_stream_write_string(dds_stream os, String val);
	/**
	 * Original signature : <code>void dds_stream_write_buffer(dds_stream_t*, uint32_t, uint8_t*)</code><br>
	 */
	public static native void dds_stream_write_buffer(dds_stream os, int len, ByteBuffer buffer);
	public static class dds_topic_descriptor extends PointerType {
		public dds_topic_descriptor(Pointer address) {
			super(address);
		}
		public dds_topic_descriptor() {
			super();
		}
	};
}
