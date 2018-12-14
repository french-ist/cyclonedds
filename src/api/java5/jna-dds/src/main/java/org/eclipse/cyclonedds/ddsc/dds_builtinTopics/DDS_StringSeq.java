package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_builtinTopics.h:69</i><br>
 */
public class DDS_StringSeq extends Structure {
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
	/** C type : char** */
	public PointerByReference _buffer;
	public PointerByReference get_buffer() {
		return _buffer;
	}
	public void set_buffer(PointerByReference _buffer) {
		this._buffer = _buffer;
	}
	public byte _release;
	public byte get_release() {
		return _release;
	}
	public void set_release(byte _release) {
		this._release = _release;
	}
	public DDS_StringSeq() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("_maximum", "_length", "_buffer", "_release");
	}
	/** @param _buffer C type : char** */
	public DDS_StringSeq(int _maximum, int _length, PointerByReference _buffer, byte _release) {
		super();
		this._maximum = _maximum;
		this._length = _length;
		this._buffer = _buffer;
		this._release = _release;
	}
	public DDS_StringSeq(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_StringSeq implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_StringSeq implements Structure.ByValue {
		
	};
}
