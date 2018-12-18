package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.ut_avlNode_t;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.ut_avlTree_t;
/**
 */
public class dds_domain extends Structure {
	/** C type : ut_avlNode_t */
	public ut_avlNode_t m_node;
	public ut_avlNode_t getM_node() {
		return m_node;
	}
	public void setM_node(ut_avlNode_t m_node) {
		this.m_node = m_node;
	}
	/** C type : dds_domainid_t */
	public int m_id;
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	/** C type : ut_avlTree_t */
	public ut_avlTree_t m_topics;
	public ut_avlTree_t getM_topics() {
		return m_topics;
	}
	public void setM_topics(ut_avlTree_t m_topics) {
		this.m_topics = m_topics;
	}
	public int m_refc;
	public int getM_refc() {
		return m_refc;
	}
	public void setM_refc(int m_refc) {
		this.m_refc = m_refc;
	}
	public dds_domain() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_node", "m_id", "m_topics", "m_refc");
	}
	/**
	 * @param m_node C type : ut_avlNode_t<br>
	 * @param m_id C type : dds_domainid_t<br>
	 * @param m_topics C type : ut_avlTree_t
	 */
	public dds_domain(ut_avlNode_t m_node, int m_id, ut_avlTree_t m_topics, int m_refc) {
		super();
		this.m_node = m_node;
		this.m_id = m_id;
		this.m_topics = m_topics;
		this.m_refc = m_refc;
	}
	public dds_domain(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_domain implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_domain implements Structure.ByValue {
		
	};
}
