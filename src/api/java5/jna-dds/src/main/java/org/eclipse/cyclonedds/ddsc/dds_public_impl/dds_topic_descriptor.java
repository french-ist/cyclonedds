package org.eclipse.cyclonedds.ddsc.dds_public_impl;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import java.util.Arrays;
import java.util.List;
/**
 */
public class dds_topic_descriptor extends Structure {
	/** Size of topic type */
	public NativeSize m_size;
	public NativeSize getM_size() {
		return m_size;
	}
	public void setM_size(NativeSize m_size) {
		this.m_size = m_size;
	}
	/** Alignment of topic type */
	public int m_align;
	public int getM_align() {
		return m_align;
	}
	public void setM_align(int m_align) {
		this.m_align = m_align;
	}
	/** Flags */
	public int m_flagset;
	public int getM_flagset() {
		return m_flagset;
	}
	public void setM_flagset(int m_flagset) {
		this.m_flagset = m_flagset;
	}
	/** Number of keys (can be 0) */
	public int m_nkeys;
	public int getM_nkeys() {
		return m_nkeys;
	}
	public void setM_nkeys(int m_nkeys) {
		this.m_nkeys = m_nkeys;
	}
	/**
	 * Type name<br>
	 * C type : const char*
	 */
	public Pointer m_typename;
	public Pointer getM_typename() {
		return m_typename;
	}
	public void setM_typename(Pointer m_typename) {
		this.m_typename = m_typename;
	}
	/**
	 * Key descriptors (NULL iff m_nkeys 0)<br>
	 * C type : const dds_key_descriptor_t*
	 */
	public org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor.ByReference m_keys;
	public org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor.ByReference getM_keys() {
		return m_keys;
	}
	public void setM_keys(org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor.ByReference m_keys) {
		this.m_keys = m_keys;
	}
	/** Number of ops in m_ops */
	public int m_nops;
	public int getM_nops() {
		return m_nops;
	}
	public void setM_nops(int m_nops) {
		this.m_nops = m_nops;
	}
	/**
	 * Marshalling meta data<br>
	 * C type : const uint32_t*
	 */
	public IntByReference m_ops;
	public IntByReference getM_ops() {
		return m_ops;
	}
	public void setM_ops(IntByReference m_ops) {
		this.m_ops = m_ops;
	}
	/**
	 * XML topic description meta data<br>
	 * C type : const char*
	 */
	public Pointer m_meta;
	public Pointer getM_meta() {
		return m_meta;
	}
	public void setM_meta(Pointer m_meta) {
		this.m_meta = m_meta;
	}
	public dds_topic_descriptor() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_size", "m_align", "m_flagset", "m_nkeys", "m_typename", "m_keys", "m_nops", "m_ops", "m_meta");
	}
	/**
	 * @param m_size Size of topic type<br>
	 * @param m_align Alignment of topic type<br>
	 * @param m_flagset Flags<br>
	 * @param m_nkeys Number of keys (can be 0)<br>
	 * @param m_typename Type name<br>
	 * C type : const char*<br>
	 * @param m_keys Key descriptors (NULL iff m_nkeys 0)<br>
	 * C type : const dds_key_descriptor_t*<br>
	 * @param m_nops Number of ops in m_ops<br>
	 * @param m_ops Marshalling meta data<br>
	 * C type : const uint32_t*<br>
	 * @param m_meta XML topic description meta data<br>
	 * C type : const char*
	 */
	public dds_topic_descriptor(NativeSize m_size, int m_align, int m_flagset, int m_nkeys, Pointer m_typename, org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor.ByReference m_keys, int m_nops, IntByReference m_ops, Pointer m_meta) {
		super();
		this.m_size = m_size;
		this.m_align = m_align;
		this.m_flagset = m_flagset;
		this.m_nkeys = m_nkeys;
		this.m_typename = m_typename;
		this.m_keys = m_keys;
		this.m_nops = m_nops;
		this.m_ops = m_ops;
		this.m_meta = m_meta;
	}
	public dds_topic_descriptor(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_topic_descriptor implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_topic_descriptor implements Structure.ByValue {
		
	};
}
