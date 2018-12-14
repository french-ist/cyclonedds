package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
/**
 * JNA Wrapper for library <b>ddsc</b><br>
 */
public class DdscLibrary implements Library {
	public static final String JNA_LIBRARY_NAME = "ddsc";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(DdscLibrary.JNA_LIBRARY_NAME);
	static {
		Native.register(DdscLibrary.class, DdscLibrary.JNA_NATIVE_LIB);
	}
	/** <i>native declaration : _dds__types.h</i> */
	public static final int DDS_INTERNAL_STATUS_MASK = (int)(0xFF000000);
	/** <i>native declaration : _dds__types.h</i> */
	public static final int DDS_WAITSET_TRIGGER_STATUS = (int)(0x01000000);
	/** <i>native declaration : _dds__types.h</i> */
	public static final int DDS_DELETING_STATUS = (int)(0x02000000);
	/** <i>native declaration : _dds__types.h</i> */
	public static final int DDS_HEADBANG_TIMEOUT_MS = (int)(10);
	/** <i>native declaration : _dds__types.h</i> */
	public static final int DDS_ENTITY_ENABLED = (int)0x0001;
	/** <i>native declaration : _dds__types.h</i> */
	public static final int DDS_ENTITY_IMPLICIT = (int)0x0002;
	/** <i>native declaration : _dds__types.h:70</i> */
	public interface dds_querycondition_filter_with_ctx_fn extends Callback {
		byte apply(Pointer sample, Pointer ctx);
	};
	/** <i>native declaration : _dds__types.h:110</i> */
	public interface dds_entity_callback extends Callback {
		void apply(int observer, int observed, int status);
	};
	public static class dds_on_liveliness_changed_fn extends PointerType {
		public dds_on_liveliness_changed_fn(Pointer address) {
			super(address);
		}
		public dds_on_liveliness_changed_fn() {
			super();
		}
	};
	public static class ut_handlelink extends PointerType {
		public ut_handlelink(Pointer address) {
			super(address);
		}
		public ut_handlelink() {
			super();
		}
	};
	public static class dds_requested_incompatible_qos_status_t extends PointerType {
		public dds_requested_incompatible_qos_status_t(Pointer address) {
			super(address);
		}
		public dds_requested_incompatible_qos_status_t() {
			super();
		}
	};
	public static class dds_liveliness_lost_status_t extends PointerType {
		public dds_liveliness_lost_status_t(Pointer address) {
			super(address);
		}
		public dds_liveliness_lost_status_t() {
			super();
		}
	};
	public static class dds_on_subscription_matched_fn extends PointerType {
		public dds_on_subscription_matched_fn(Pointer address) {
			super(address);
		}
		public dds_on_subscription_matched_fn() {
			super();
		}
	};
	public static class dds_sample_rejected_status_t extends PointerType {
		public dds_sample_rejected_status_t(Pointer address) {
			super(address);
		}
		public dds_sample_rejected_status_t() {
			super();
		}
	};
	public static class os_mutex extends PointerType {
		public os_mutex(Pointer address) {
			super(address);
		}
		public os_mutex() {
			super();
		}
	};
	public static class reader extends PointerType {
		public reader(Pointer address) {
			super(address);
		}
		public reader() {
			super();
		}
	};
	public static class nn_xpack extends PointerType {
		public nn_xpack(Pointer address) {
			super(address);
		}
		public nn_xpack() {
			super();
		}
	};
	public static class dds_on_requested_deadline_missed_fn extends PointerType {
		public dds_on_requested_deadline_missed_fn(Pointer address) {
			super(address);
		}
		public dds_on_requested_deadline_missed_fn() {
			super();
		}
	};
	public static class dds_on_offered_deadline_missed_fn extends PointerType {
		public dds_on_offered_deadline_missed_fn(Pointer address) {
			super(address);
		}
		public dds_on_offered_deadline_missed_fn() {
			super();
		}
	};
	public static class dds_requested_deadline_missed_status_t extends PointerType {
		public dds_requested_deadline_missed_status_t(Pointer address) {
			super(address);
		}
		public dds_requested_deadline_missed_status_t() {
			super();
		}
	};
	public static class ut_avlTree_t extends PointerType {
		public ut_avlTree_t(Pointer address) {
			super(address);
		}
		public ut_avlTree_t() {
			super();
		}
	};
	public static class sertopic extends PointerType {
		public sertopic(Pointer address) {
			super(address);
		}
		public sertopic() {
			super();
		}
	};
	public static class dds_on_publication_matched_fn extends PointerType {
		public dds_on_publication_matched_fn(Pointer address) {
			super(address);
		}
		public dds_on_publication_matched_fn() {
			super();
		}
	};
	public static class dds_inconsistent_topic_status_t extends PointerType {
		public dds_inconsistent_topic_status_t(Pointer address) {
			super(address);
		}
		public dds_inconsistent_topic_status_t() {
			super();
		}
	};
	public static class dds_qos_t extends PointerType {
		public dds_qos_t(Pointer address) {
			super(address);
		}
		public dds_qos_t() {
			super();
		}
	};
	public static class dds_on_data_on_readers_fn extends PointerType {
		public dds_on_data_on_readers_fn(Pointer address) {
			super(address);
		}
		public dds_on_data_on_readers_fn() {
			super();
		}
	};
	public static class dds_on_data_available_fn extends PointerType {
		public dds_on_data_available_fn(Pointer address) {
			super(address);
		}
		public dds_on_data_available_fn() {
			super();
		}
	};
	public static class dds_topic_descriptor_t extends PointerType {
		public dds_topic_descriptor_t(Pointer address) {
			super(address);
		}
		public dds_topic_descriptor_t() {
			super();
		}
	};
	public static class ut_avlNode_t extends PointerType {
		public ut_avlNode_t(Pointer address) {
			super(address);
		}
		public ut_avlNode_t() {
			super();
		}
	};
	public static class dds_subscription_matched_status_t extends PointerType {
		public dds_subscription_matched_status_t(Pointer address) {
			super(address);
		}
		public dds_subscription_matched_status_t() {
			super();
		}
	};
	public static class nn_guid_t extends PointerType {
		public nn_guid_t(Pointer address) {
			super(address);
		}
		public nn_guid_t() {
			super();
		}
	};
	public static class dds_sample_lost_status_t extends PointerType {
		public dds_sample_lost_status_t(Pointer address) {
			super(address);
		}
		public dds_sample_lost_status_t() {
			super();
		}
	};
	public static class dds_on_liveliness_lost_fn extends PointerType {
		public dds_on_liveliness_lost_fn(Pointer address) {
			super(address);
		}
		public dds_on_liveliness_lost_fn() {
			super();
		}
	};
	public static class os_cond extends PointerType {
		public os_cond(Pointer address) {
			super(address);
		}
		public os_cond() {
			super();
		}
	};
	public static class dds_offered_deadline_missed_status_t extends PointerType {
		public dds_offered_deadline_missed_status_t(Pointer address) {
			super(address);
		}
		public dds_offered_deadline_missed_status_t() {
			super();
		}
	};
	public static class dds_on_requested_incompatible_qos_fn extends PointerType {
		public dds_on_requested_incompatible_qos_fn(Pointer address) {
			super(address);
		}
		public dds_on_requested_incompatible_qos_fn() {
			super();
		}
	};
	public static class ut_handle_t extends PointerType {
		public ut_handle_t(Pointer address) {
			super(address);
		}
		public ut_handle_t() {
			super();
		}
	};
	public static class rhc extends PointerType {
		public rhc(Pointer address) {
			super(address);
		}
		public rhc() {
			super();
		}
	};
	public static class dds_liveliness_changed_status_t extends PointerType {
		public dds_liveliness_changed_status_t(Pointer address) {
			super(address);
		}
		public dds_liveliness_changed_status_t() {
			super();
		}
	};
	public static class dds_on_inconsistent_topic_fn extends PointerType {
		public dds_on_inconsistent_topic_fn(Pointer address) {
			super(address);
		}
		public dds_on_inconsistent_topic_fn() {
			super();
		}
	};
	public static class dds_offered_incompatible_qos_status_t extends PointerType {
		public dds_offered_incompatible_qos_status_t(Pointer address) {
			super(address);
		}
		public dds_offered_incompatible_qos_status_t() {
			super();
		}
	};
	public static class dds_on_offered_incompatible_qos_fn extends PointerType {
		public dds_on_offered_incompatible_qos_fn(Pointer address) {
			super(address);
		}
		public dds_on_offered_incompatible_qos_fn() {
			super();
		}
	};
	public static class whc extends PointerType {
		public whc(Pointer address) {
			super(address);
		}
		public whc() {
			super();
		}
	};
	public static class dds_publication_matched_status_t extends PointerType {
		public dds_publication_matched_status_t(Pointer address) {
			super(address);
		}
		public dds_publication_matched_status_t() {
			super();
		}
	};
	public static class dds_on_sample_rejected_fn extends PointerType {
		public dds_on_sample_rejected_fn(Pointer address) {
			super(address);
		}
		public dds_on_sample_rejected_fn() {
			super();
		}
	};
	public static class writer extends PointerType {
		public writer(Pointer address) {
			super(address);
		}
		public writer() {
			super();
		}
	};
	public static class dds_querycondition_filter_fn extends PointerType {
		public dds_querycondition_filter_fn(Pointer address) {
			super(address);
		}
		public dds_querycondition_filter_fn() {
			super();
		}
	};
	public static class dds_on_sample_lost_fn extends PointerType {
		public dds_on_sample_lost_fn(Pointer address) {
			super(address);
		}
		public dds_on_sample_lost_fn() {
			super();
		}
	};
}
