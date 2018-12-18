package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class DDS_TypeHash extends Structure {
	public long msb;
	public long getMsb() {
		return msb;
	}
	public void setMsb(long msb) {
		this.msb = msb;
	}
	public long lsb;
	public long getLsb() {
		return lsb;
	}
	public void setLsb(long lsb) {
		this.lsb = lsb;
	}
	public DDS_TypeHash() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("msb", "lsb");
	}
	public DDS_TypeHash(long msb, long lsb) {
		super();
		this.msb = msb;
		this.lsb = lsb;
	}
	public DDS_TypeHash(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_TypeHash implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_TypeHash implements Structure.ByValue {
		
	};
}
