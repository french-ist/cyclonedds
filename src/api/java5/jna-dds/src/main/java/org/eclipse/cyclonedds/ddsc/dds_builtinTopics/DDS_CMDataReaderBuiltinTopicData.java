package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_builtinTopics.h:341</i><br>
 */
public class DDS_CMDataReaderBuiltinTopicData extends Structure {
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
	public int[] subscriber_key = new int[3];
	public int[] getSubscriber_key() {
		return subscriber_key;
	}
	public void setSubscriber_key(int subscriber_key[]) {
		this.subscriber_key = subscriber_key;
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
	/** C type : DDS_ReaderDataLifecycleQosPolicy */
	public DDS_ReaderDataLifecycleQosPolicy reader_data_lifecycle;
	public DDS_ReaderDataLifecycleQosPolicy getReader_data_lifecycle() {
		return reader_data_lifecycle;
	}
	public void setReader_data_lifecycle(DDS_ReaderDataLifecycleQosPolicy reader_data_lifecycle) {
		this.reader_data_lifecycle = reader_data_lifecycle;
	}
	/** C type : DDS_UserKeyQosPolicy */
	public DDS_UserKeyQosPolicy subscription_keys;
	public DDS_UserKeyQosPolicy getSubscription_keys() {
		return subscription_keys;
	}
	public void setSubscription_keys(DDS_UserKeyQosPolicy subscription_keys) {
		this.subscription_keys = subscription_keys;
	}
	/** C type : DDS_ReaderLifespanQosPolicy */
	public DDS_ReaderLifespanQosPolicy reader_lifespan;
	public DDS_ReaderLifespanQosPolicy getReader_lifespan() {
		return reader_lifespan;
	}
	public void setReader_lifespan(DDS_ReaderLifespanQosPolicy reader_lifespan) {
		this.reader_lifespan = reader_lifespan;
	}
	/** C type : DDS_ShareQosPolicy */
	public DDS_ShareQosPolicy share;
	public DDS_ShareQosPolicy getShare() {
		return share;
	}
	public void setShare(DDS_ShareQosPolicy share) {
		this.share = share;
	}
	public DDS_CMDataReaderBuiltinTopicData() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("key", "product", "subscriber_key", "name", "history", "resource_limits", "reader_data_lifecycle", "subscription_keys", "reader_lifespan", "share");
	}
	public DDS_CMDataReaderBuiltinTopicData(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_CMDataReaderBuiltinTopicData implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_CMDataReaderBuiltinTopicData implements Structure.ByValue {
		
	};
}
