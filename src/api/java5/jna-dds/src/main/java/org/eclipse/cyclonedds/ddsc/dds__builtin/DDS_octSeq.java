package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:62</i><br>
 */
public class DDS_octSeq extends Structure {
	public int _maximum;
	public int get_maximum() {
		return _maximum;
	}
	public void set_maximum(int _maximum) {
		this._maximum = _maximum;
	}
	public int _length;
	public int get_length() {
		return _length;
	}
	public void set_length(int _length) {
		this._length = _length;
	}
	/** C type : uint8_t* */
	public Pointer _buffer;
	public Pointer get_buffer() {
		return _buffer;
	}
	public void set_buffer(Pointer _buffer) {
		this._buffer = _buffer;
	}
	public byte _release;
	public byte get_release() {
		return _release;
	}
	public void set_release(byte _release) {
		this._release = _release;
	}
	public DDS_octSeq() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("_maximum", "_length", "_buffer", "_release");
	}
	/** @param _buffer C type : uint8_t* */
	public DDS_octSeq(int _maximum, int _length, Pointer _buffer, byte _release) {
		super();
		this._maximum = _maximum;
		this._length = _length;
		this._buffer = _buffer;
		this._release = _release;
	}
	public DDS_octSeq(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_octSeq implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_octSeq implements Structure.ByValue {
		
	};
}
