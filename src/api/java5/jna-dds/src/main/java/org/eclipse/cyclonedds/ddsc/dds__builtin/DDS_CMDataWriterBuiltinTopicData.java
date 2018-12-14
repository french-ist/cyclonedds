package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:328</i><br>
 */
public class DDS_CMDataWriterBuiltinTopicData extends Structure {
	/** C type : DDS_BuiltinTopicKey_t */
	public int[] key = new int[3];
	public int[] getKey() {
		return key;
	}
	public void setKey(int key[]) {
		this.key = key;
	}
	/** C type : DDS_ProductDataQosPolicy */
	public DDS_ProductDataQosPolicy product;
	public DDS_ProductDataQosPolicy getProduct() {
		return product;
	}
	public void setProduct(DDS_ProductDataQosPolicy product) {
		this.product = product;
	}
	/** C type : DDS_BuiltinTopicKey_t */
	public int[] publisher_key = new int[3];
	public int[] getPublisher_key() {
		return publisher_key;
	}
	public void setPublisher_key(int publisher_key[]) {
		this.publisher_key = publisher_key;
	}
	/** C type : char* */
	public Pointer name;
	public Pointer getName() {
		return name;
	}
	public void setName(Pointer name) {
		this.name = name;
	}
	/** C type : DDS_HistoryQosPolicy */
	public DDS_HistoryQosPolicy history;
	public DDS_HistoryQosPolicy getHistory() {
		return history;
	}
	public void setHistory(DDS_HistoryQosPolicy history) {
		this.history = history;
	}
	/** C type : DDS_ResourceLimitsQosPolicy */
	public DDS_ResourceLimitsQosPolicy resource_limits;
	public DDS_ResourceLimitsQosPolicy getResource_limits() {
		return resource_limits;
	}
	public void setResource_limits(DDS_ResourceLimitsQosPolicy resource_limits) {
		this.resource_limits = resource_limits;
	}
	/** C type : DDS_WriterDataLifecycleQosPolicy */
	public DDS_WriterDataLifecycleQosPolicy writer_data_lifecycle;
	public DDS_WriterDataLifecycleQosPolicy getWriter_data_lifecycle() {
		return writer_data_lifecycle;
	}
	public void setWriter_data_lifecycle(DDS_WriterDataLifecycleQosPolicy writer_data_lifecycle) {
		this.writer_data_lifecycle = writer_data_lifecycle;
	}
	public DDS_CMDataWriterBuiltinTopicData() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("key", "product", "publisher_key", "name", "history", "resource_limits", "writer_data_lifecycle");
	}
	/**
	 * @param key C type : DDS_BuiltinTopicKey_t<br>
	 * @param product C type : DDS_ProductDataQosPolicy<br>
	 * @param publisher_key C type : DDS_BuiltinTopicKey_t<br>
	 * @param name C type : char*<br>
	 * @param history C type : DDS_HistoryQosPolicy<br>
	 * @param resource_limits C type : DDS_ResourceLimitsQosPolicy<br>
	 * @param writer_data_lifecycle C type : DDS_WriterDataLifecycleQosPolicy
	 */
	public DDS_CMDataWriterBuiltinTopicData(int key[], DDS_ProductDataQosPolicy product, int publisher_key[], Pointer name, DDS_HistoryQosPolicy history, DDS_ResourceLimitsQosPolicy resource_limits, DDS_WriterDataLifecycleQosPolicy writer_data_lifecycle) {
		super();
		if ((key.length != this.key.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.key = key;
		this.product = product;
		if ((publisher_key.length != this.publisher_key.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.publisher_key = publisher_key;
		this.name = name;
		this.history = history;
		this.resource_limits = resource_limits;
		this.writer_data_lifecycle = writer_data_lifecycle;
	}
	public DDS_CMDataWriterBuiltinTopicData(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_CMDataWriterBuiltinTopicData implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_CMDataWriterBuiltinTopicData implements Structure.ByValue {
		
	};
}
