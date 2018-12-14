package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds__types.h:151</i><br>
 */
public class dds_participant extends Structure {
	/** C type : dds_entity */
	public dds_entity m_entity;
	public dds_entity getM_entity() {
		return m_entity;
	}
	public void setM_entity(dds_entity m_entity) {
		this.m_entity = m_entity;
	}
	/** C type : dds_entity* */
	public dds_entity.ByReference m_dur_reader;
	public dds_entity.ByReference getM_dur_reader() {
		return m_dur_reader;
	}
	public void setM_dur_reader(dds_entity.ByReference m_dur_reader) {
		this.m_dur_reader = m_dur_reader;
	}
	/** C type : dds_entity* */
	public dds_entity.ByReference m_dur_writer;
	public dds_entity.ByReference getM_dur_writer() {
		return m_dur_writer;
	}
	public void setM_dur_writer(dds_entity.ByReference m_dur_writer) {
		this.m_dur_writer = m_dur_writer;
	}
	/** C type : dds_entity_t */
	public int m_builtin_subscriber;
	public int getM_builtin_subscriber() {
		return m_builtin_subscriber;
	}
	public void setM_builtin_subscriber(int m_builtin_subscriber) {
		this.m_builtin_subscriber = m_builtin_subscriber;
	}
	public dds_participant() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_entity", "m_dur_reader", "m_dur_writer", "m_builtin_subscriber");
	}
	/**
	 * @param m_entity C type : dds_entity<br>
	 * @param m_dur_reader C type : dds_entity*<br>
	 * @param m_dur_writer C type : dds_entity*<br>
	 * @param m_builtin_subscriber C type : dds_entity_t
	 */
	public dds_participant(dds_entity m_entity, dds_entity.ByReference m_dur_reader, dds_entity.ByReference m_dur_writer, int m_builtin_subscriber) {
		super();
		this.m_entity = m_entity;
		this.m_dur_reader = m_dur_reader;
		this.m_dur_writer = m_dur_writer;
		this.m_builtin_subscriber = m_builtin_subscriber;
	}
	public dds_participant(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_participant implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_participant implements Structure.ByValue {
		
	};
}
