package org.eclipse.cyclonedds.ddsc.dds_public_stream;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_public_stream.h:69</i><br>
 */
public class dds_stream extends Structure {
	/**
	 * Union of pointers to start of buffer<br>
	 * C type : dds_uptr_t
	 */
	public dds_uptr_t m_buffer;
	public dds_uptr_t getM_buffer() {
		return m_buffer;
	}
	public void setM_buffer(dds_uptr_t m_buffer) {
		this.m_buffer = m_buffer;
	}
	/** Buffer size */
	public NativeSize m_size;
	public NativeSize getM_size() {
		return m_size;
	}
	public void setM_size(NativeSize m_size) {
		this.m_size = m_size;
	}
	/** Read/write offset from start of buffer */
	public NativeSize m_index;
	public NativeSize getM_index() {
		return m_index;
	}
	public void setM_index(NativeSize m_index) {
		this.m_index = m_index;
	}
	/** Endian: big (false) or little (true) */
	public byte m_endian;
	public byte getM_endian() {
		return m_endian;
	}
	public void setM_endian(byte m_endian) {
		this.m_endian = m_endian;
	}
	/** Attempt made to read beyond end of buffer */
	public byte m_failed;
	public byte getM_failed() {
		return m_failed;
	}
	public void setM_failed(byte m_failed) {
		this.m_failed = m_failed;
	}
	public dds_stream() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_buffer", "m_size", "m_index", "m_endian", "m_failed");
	}
	/**
	 * @param m_buffer Union of pointers to start of buffer<br>
	 * C type : dds_uptr_t<br>
	 * @param m_size Buffer size<br>
	 * @param m_index Read/write offset from start of buffer<br>
	 * @param m_endian Endian: big (false) or little (true)<br>
	 * @param m_failed Attempt made to read beyond end of buffer
	 */
	public dds_stream(dds_uptr_t m_buffer, NativeSize m_size, NativeSize m_index, byte m_endian, byte m_failed) {
		super();
		this.m_buffer = m_buffer;
		this.m_size = m_size;
		this.m_index = m_index;
		this.m_endian = m_endian;
		this.m_failed = m_failed;
	}
	public dds_stream(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_stream implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_stream implements Structure.ByValue {
		
	};
}
