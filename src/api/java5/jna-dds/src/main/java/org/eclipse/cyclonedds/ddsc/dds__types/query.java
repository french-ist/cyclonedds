package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_querycondition_filter_fn;
/**
 */
public class query extends Structure {
	/** C type : dds_querycondition_filter_fn */
	public dds_querycondition_filter_fn m_filter;
	public dds_querycondition_filter_fn getM_filter() {
		return m_filter;
	}
	public void setM_filter(dds_querycondition_filter_fn m_filter) {
		this.m_filter = m_filter;
	}
	public query() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_filter");
	}
	/** @param m_filter C type : dds_querycondition_filter_fn */
	public query(dds_querycondition_filter_fn m_filter) {
		super();
		this.m_filter = m_filter;
	}
	public query(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends query implements Structure.ByReference {
		
	};
	public static class ByValue extends query implements Structure.ByValue {
		
	};
}
