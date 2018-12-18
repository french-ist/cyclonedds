package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 */
public class DDS_Time_t extends Structure {
	public int sec;
	public int getSec() {
		return sec;
	}
	public void setSec(int sec) {
		this.sec = sec;
	}
	public int nanosec;
	public int getNanosec() {
		return nanosec;
	}
	public void setNanosec(int nanosec) {
		this.nanosec = nanosec;
	}
	public DDS_Time_t() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("sec", "nanosec");
	}
	public DDS_Time_t(int sec, int nanosec) {
		super();
		this.sec = sec;
		this.nanosec = nanosec;
	}
	public DDS_Time_t(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_Time_t implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_Time_t implements Structure.ByValue {
		
	};
}
