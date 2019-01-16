package org.eclipse.cyclonedds.ddsc.dds_public_qos;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

/**
 * JNA Wrapper for library <b>ddsc</b><br>
 */
public class DdscLibrary implements Library {
	public static final String JNA_LIBRARY_NAME = "ddsc";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(DdscLibrary.JNA_LIBRARY_NAME);
	static {
		Native.register(DdscLibrary.class, DdscLibrary.JNA_NATIVE_LIB);
	}
	/**
	 * enum values
	 */
	public static interface dds_durability_kind {
		public static final int DDS_DURABILITY_VOLATILE = 0;
		public static final int DDS_DURABILITY_TRANSIENT_LOCAL = 1;
		public static final int DDS_DURABILITY_TRANSIENT = 2;
		public static final int DDS_DURABILITY_PERSISTENT = 3;
	};
	/**
	 * enum values
	 */
	public static interface dds_history_kind {
		public static final int DDS_HISTORY_KEEP_LAST = 0;
		public static final int DDS_HISTORY_KEEP_ALL = 1;
	};
	/**
	 * enum values
	 */
	public static interface dds_ownership_kind {
		public static final int DDS_OWNERSHIP_SHARED = 0;
		public static final int DDS_OWNERSHIP_EXCLUSIVE = 1;
	};
	/**
	 * enum values
	 */
	public static interface dds_liveliness_kind {
		public static final int DDS_LIVELINESS_AUTOMATIC = 0;
		public static final int DDS_LIVELINESS_MANUAL_BY_PARTICIPANT = 1;
		public static final int DDS_LIVELINESS_MANUAL_BY_TOPIC = 2;
	};
	/**
	 * enum values
	 */
	public static interface dds_reliability_kind {
		public static final int DDS_RELIABILITY_BEST_EFFORT = 0;
		public static final int DDS_RELIABILITY_RELIABLE = 1;
	};
	/**
	 * enum values
	 */
	public static interface dds_destination_order_kind {
		public static final int DDS_DESTINATIONORDER_BY_RECEPTION_TIMESTAMP = 0;
		public static final int DDS_DESTINATIONORDER_BY_SOURCE_TIMESTAMP = 1;
	};
	/**
	 * enum values
	 */
	public static interface dds_presentation_access_scope_kind {
		public static final int DDS_PRESENTATION_INSTANCE = 0;
		public static final int DDS_PRESENTATION_TOPIC = 1;
		public static final int DDS_PRESENTATION_GROUP = 2;
	};
	public static final int DDS_INVALID_QOS_POLICY_ID = (int)0;
	public static final int DDS_USERDATA_QOS_POLICY_ID = (int)1;
	public static final int DDS_DURABILITY_QOS_POLICY_ID = (int)2;
	public static final int DDS_PRESENTATION_QOS_POLICY_ID = (int)3;
	public static final int DDS_DEADLINE_QOS_POLICY_ID = (int)4;
	public static final int DDS_LATENCYBUDGET_QOS_POLICY_ID = (int)5;
	public static final int DDS_OWNERSHIP_QOS_POLICY_ID = (int)6;
	public static final int DDS_OWNERSHIPSTRENGTH_QOS_POLICY_ID = (int)7;
	public static final int DDS_LIVELINESS_QOS_POLICY_ID = (int)8;
	public static final int DDS_TIMEBASEDFILTER_QOS_POLICY_ID = (int)9;
	public static final int DDS_PARTITION_QOS_POLICY_ID = (int)10;
	public static final int DDS_RELIABILITY_QOS_POLICY_ID = (int)11;
	public static final int DDS_DESTINATIONORDER_QOS_POLICY_ID = (int)12;
	public static final int DDS_HISTORY_QOS_POLICY_ID = (int)13;
	public static final int DDS_RESOURCELIMITS_QOS_POLICY_ID = (int)14;
	public static final int DDS_ENTITYFACTORY_QOS_POLICY_ID = (int)15;
	public static final int DDS_WRITERDATALIFECYCLE_QOS_POLICY_ID = (int)16;
	public static final int DDS_READERDATALIFECYCLE_QOS_POLICY_ID = (int)17;
	public static final int DDS_TOPICDATA_QOS_POLICY_ID = (int)18;
	public static final int DDS_GROUPDATA_QOS_POLICY_ID = (int)19;
	public static final int DDS_TRANSPORTPRIORITY_QOS_POLICY_ID = (int)20;
	public static final int DDS_LIFESPAN_QOS_POLICY_ID = (int)21;
	public static final int DDS_DURABILITYSERVICE_QOS_POLICY_ID = (int)22;
	/**
	 * @brief Allocate memory and initialize default QoS-policies<br>
	 * @returns - Pointer to the initialized dds_qos_t structure, NULL if unsuccessful.<br>
	 * Original signature : <code>dds_qos_t* dds_create_qos()</code><br>
	 */
	public static native PointerByReference dds_create_qos();
	/**
	 * Original signature : <code>dds_qos_t* dds_qos_create()</code><br>
	 */
	//MANUALLY REMOVED
	//public static native dds_qos_t dds_qos_create();
	/**
	 * @brief Delete memory allocated to QoS-policies structure<br>
	 * @param[in] qos - Pointer to dds_qos_t structure<br>
	 * Original signature : <code>void dds_delete_qos(__declspec(__restrict) dds_qos_t*)</code><br>
	 */
	public static native void dds_delete_qos(PointerByReference qos);
	/**
	 * Original signature : <code>void dds_qos_delete(__declspec(__restrict) dds_qos_t*)</code><br>
	 */
	public static native void dds_qos_delete(PointerByReference qos);
	public static native void dds_qos_delete(org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_qos_t qos);
	/**
	 * @brief Reset a QoS-policies structure to default values<br>
	 * @param[in,out] qos - Pointer to the dds_qos_t structure<br>
	 * Original signature : <code>void dds_reset_qos(__declspec(__restrict) dds_qos_t*)</code><br>
	 */
	public static native void dds_reset_qos(PointerByReference qos);
	/**
	 * Original signature : <code>void dds_qos_reset(__declspec(__restrict) dds_qos_t*)</code><br>
	 */
	public static native void dds_qos_reset(PointerByReference qos);
	/**
	 * @brief Copy all QoS-policies from one structure to another<br>
	 * @param[in,out] dst - Pointer to the destination dds_qos_t structure<br>
	 * @param[in] src - Pointer to the source dds_qos_t structure<br>
	 * @returns - Return-code indicating success or failure<br>
	 * Original signature : <code>dds_return_t dds_copy_qos(__declspec(__restrict) dds_qos_t*, __declspec(__restrict) const dds_qos_t*)</code><br>
	 */
	public static native int dds_copy_qos(PointerByReference dst, PointerByReference src);
	/**
	 * Original signature : <code>dds_return_t dds_qos_copy(__declspec(__restrict) dds_qos_t*, __declspec(__restrict) const dds_qos_t*)</code><br>
	 */
	public static native int dds_qos_copy(PointerByReference dst, PointerByReference src);
	/**
	 * @brief Copy all QoS-policies from one structure to another, unless already set<br>
	 * Policies are copied from src to dst, unless src already has the policy set to a non-default value.<br>
	 * @param[in,out] dst - Pointer to the destination qos structure<br>
	 * @param[in] src - Pointer to the source qos structure<br>
	 * Original signature : <code>void dds_merge_qos(__declspec(__restrict) dds_qos_t*, __declspec(__restrict) const dds_qos_t*)</code><br>
	 */
	public static native void dds_merge_qos(PointerByReference dst, PointerByReference src);
	/**
	 * Original signature : <code>void dds_qos_merge(__declspec(__restrict) dds_qos_t*, __declspec(__restrict) const dds_qos_t*)</code><br>
	 */
	public static native void dds_qos_merge(PointerByReference dst, PointerByReference src);
	/**
	 * @brief Copy all QoS-policies from one structure to another, unless already set<br>
	 * Policies are copied from src to dst, unless src already has the policy set to a non-default value.<br>
	 * @param[in,out] dst - Pointer to the destination qos structure<br>
	 * @param[in] src - Pointer to the source qos structure<br>
	 * Original signature : <code>bool dds_qos_equal(__declspec(__restrict) const dds_qos_t*, __declspec(__restrict) const dds_qos_t*)</code><br>
	 */
	public static native byte dds_qos_equal(PointerByReference a, PointerByReference b);
	/**
	 * @brief Set the userdata of a qos structure.<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the userdata<br>
	 * @param[in] value - Pointer to the userdata<br>
	 * @param[in] sz - Size of userdata stored in value<br>
	 * Original signature : <code>void dds_qset_userdata(__declspec(__restrict) dds_qos_t*, __declspec(__restrict) const void*, size_t)</code><br>
	 */
	public static native void dds_qset_userdata(PointerByReference qos, Pointer value, NativeSize sz);
	/**
	 * @brief Set the topicdata of a qos structure.<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the topicdata<br>
	 * @param[in] value - Pointer to the topicdata<br>
	 * @param[in] sz - Size of the topicdata stored in value<br>
	 * Original signature : <code>void dds_qset_topicdata(__declspec(__restrict) dds_qos_t*, __declspec(__restrict) const void*, size_t)</code><br>
	 */
	public static native void dds_qset_topicdata(PointerByReference qos, Pointer value, NativeSize sz);
	/**
	 * @brief Set the groupdata of a qos structure.<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the groupdata<br>
	 * @param[in] value - Pointer to the group data<br>
	 * @param[in] sz - Size of groupdata stored in value<br>
	 * Original signature : <code>void dds_qset_groupdata(__declspec(__restrict) dds_qos_t*, __declspec(__restrict) const void*, size_t)</code><br>
	 */
	public static native void dds_qset_groupdata(PointerByReference qos, Pointer value, NativeSize sz);
	/**
	 * @brief Set the durability policy of a qos structure.<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] kind - Durability kind value \ref DCPS_QoS_Durability<br>
	 * Original signature : <code>void dds_qset_durability(__declspec(__restrict) dds_qos_t*, dds_durability_kind_t)</code><br>
	 */
	public static native void dds_qset_durability(PointerByReference qos, int kind);
	/**
	 * @brief Set the history policy of a qos structure.<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] kind - History kind value \ref DCPS_QoS_History<br>
	 * @param[in] depth - History depth value \ref DCPS_QoS_History<br>
	 * Original signature : <code>void dds_qset_history(__declspec(__restrict) dds_qos_t*, dds_history_kind_t, int32_t)</code><br>
	 */
	public static native void dds_qset_history(PointerByReference qos, int kind, int depth);
	/**
	 * @brief Set the resource limits policy of a qos structure.<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] max_samples - Number of samples resource-limit value<br>
	 * @param[in] max_instances - Number of instances resource-limit value<br>
	 * @param[in] max_samples_per_instance - Number of samples per instance resource-limit value<br>
	 * Original signature : <code>void dds_qset_resource_limits(__declspec(__restrict) dds_qos_t*, int32_t, int32_t, int32_t)</code><br>
	 */
	public static native void dds_qset_resource_limits(PointerByReference qos, int max_samples, int max_instances, int max_samples_per_instance);
	/**
	 * @brief Set the presentation policy of a qos structure.<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] access_scope - Access-scope kind<br>
	 * @param[in] coherent_access - Coherent access enable value<br>
	 * @param[in] ordered_access - Ordered access enable value<br>
	 * Original signature : <code>void dds_qset_presentation(__declspec(__restrict) dds_qos_t*, dds_presentation_access_scope_kind_t, bool, bool)</code><br>
	 */
	public static native void dds_qset_presentation(PointerByReference qos, int access_scope, byte coherent_access, byte ordered_access);
	/**
	 * @brief Set the lifespan policy of a qos structure.<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] lifespan - Lifespan duration (expiration time relative to source timestamp of a sample)<br>
	 * Original signature : <code>void dds_qset_lifespan(__declspec(__restrict) dds_qos_t*, dds_duration_t)</code><br>
	 */
	public static native void dds_qset_lifespan(PointerByReference qos, long lifespan);
	/**
	 * @brief Set the deadline policy of a qos structure.<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] deadline - Deadline duration<br>
	 * Original signature : <code>void dds_qset_deadline(__declspec(__restrict) dds_qos_t*, dds_duration_t)</code><br>
	 */
	public static native void dds_qset_deadline(PointerByReference qos, long deadline);
	/**
	 * @brief Set the latency-budget policy of a qos structure<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] duration - Latency budget duration<br>
	 * Original signature : <code>void dds_qset_latency_budget(__declspec(__restrict) dds_qos_t*, dds_duration_t)</code><br>
	 */
	public static native void dds_qset_latency_budget(PointerByReference qos, long duration);
	/**
	 * @brief Set the ownership policy of a qos structure<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] kind - Ownership kind<br>
	 * Original signature : <code>void dds_qset_ownership(__declspec(__restrict) dds_qos_t*, dds_ownership_kind_t)</code><br>
	 */
	public static native void dds_qset_ownership(PointerByReference qos, int kind);
	/**
	 * @brief Set the ownership strength policy of a qos structure<br>
	 * param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * param[in] value - Ownership strength value<br>
	 * Original signature : <code>void dds_qset_ownership_strength(__declspec(__restrict) dds_qos_t*, int32_t)</code><br>
	 */
	public static native void dds_qset_ownership_strength(PointerByReference qos, int value);
	/**
	 * @brief Set the liveliness policy of a qos structure<br>
	 * param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * param[in] kind - Liveliness kind<br>
	 * param[in[ lease_duration - Lease duration<br>
	 * Original signature : <code>void dds_qset_liveliness(__declspec(__restrict) dds_qos_t*, dds_liveliness_kind_t, dds_duration_t)</code><br>
	 */
	public static native void dds_qset_liveliness(PointerByReference qos, int kind, long lease_duration);
	/**
	 * @brief Set the time-based filter policy of a qos structure<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] minimum_separation - Minimum duration between sample delivery for an instance<br>
	 * Original signature : <code>void dds_qset_time_based_filter(__declspec(__restrict) dds_qos_t*, dds_duration_t)</code><br>
	 */
	public static native void dds_qset_time_based_filter(PointerByReference qos, long minimum_separation);
	/**
	 * @brief Set the partition policy of a qos structure<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] n - Number of partitions stored in ps<br>
	 * @param[in[ ps - Pointer to string(s) storing partition name(s)<br>
	 * Original signature : <code>void dds_qset_partition(__declspec(__restrict) dds_qos_t*, uint32_t, __declspec(__restrict) const char**)</code><br>
	 */
	public static native void dds_qset_partition(PointerByReference qos, int n, PointerByReference ps);
	/**
	 * @brief Set the reliability policy of a qos structure<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] kind - Reliability kind<br>
	 * @param[in] max_blocking_time - Max blocking duration applied when kind is reliable.<br>
	 * Original signature : <code>void dds_qset_reliability(__declspec(__restrict) dds_qos_t*, dds_reliability_kind_t, dds_duration_t)</code><br>
	 */
	public static native void dds_qset_reliability(PointerByReference qos, int kind, long max_blocking_time);
	// ADDED
	public static native void dds_qset_reliability(org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_qos_t qos, int kind, long max_blocking_time);
	/**
	 * @brief Set the transport-priority policy of a qos structure<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] value - Priority value<br>
	 * Original signature : <code>void dds_qset_transport_priority(__declspec(__restrict) dds_qos_t*, int32_t)</code><br>
	 */
	public static native void dds_qset_transport_priority(PointerByReference qos, int value);
	/**
	 * @brief Set the destination-order policy of a qos structure<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] kind - Destination-order kind<br>
	 * Original signature : <code>void dds_qset_destination_order(__declspec(__restrict) dds_qos_t*, dds_destination_order_kind_t)</code><br>
	 */
	public static native void dds_qset_destination_order(PointerByReference qos, int kind);
	/**
	 * @brief Set the writer data-lifecycle policy of a qos structure<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] autodispose_unregistered_instances - Automatic disposal of unregistered instances<br>
	 * Original signature : <code>void dds_qset_writer_data_lifecycle(__declspec(__restrict) dds_qos_t*, bool)</code><br>
	 */
	public static native void dds_qset_writer_data_lifecycle(PointerByReference qos, byte autodispose);
	/**
	 * @brief Set the reader data-lifecycle policy of a qos structure<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] autopurge_nowriter_samples_delay - Delay for purging of samples from instances in a no-writers state<br>
	 * @param[in] autopurge_disposed_samples_delay - Delay for purging of samples from disposed instances<br>
	 * Original signature : <code>void dds_qset_reader_data_lifecycle(__declspec(__restrict) dds_qos_t*, dds_duration_t, dds_duration_t)</code><br>
	 */
	public static native void dds_qset_reader_data_lifecycle(PointerByReference qos, long autopurge_nowriter_samples_delay, long autopurge_disposed_samples_delay);
	/**
	 * @brief Set the durability-service policy of a qos structure<br>
	 * @param[in,out] qos - Pointer to a dds_qos_t structure that will store the policy<br>
	 * @param[in] service_cleanup_delay - Delay for purging of abandoned instances from the durability service<br>
	 * @param[in] history_kind - History policy kind applied by the durability service<br>
	 * @param[in] history_depth - History policy depth applied by the durability service<br>
	 * @param[in] max_samples - Number of samples resource-limit policy applied by the durability service<br>
	 * @param[in] max_instances - Number of instances resource-limit policy applied by the durability service<br>
	 * @param[in] max_samples_per_instance - Number of samples per instance resource-limit policy applied by the durability service<br>
	 * Original signature : <code>void dds_qset_durability_service(__declspec(__restrict) dds_qos_t*, dds_duration_t, dds_history_kind_t, int32_t, int32_t, int32_t, int32_t)</code><br>
	 */
	public static native void dds_qset_durability_service(PointerByReference qos, long service_cleanup_delay, int history_kind, int history_depth, int max_samples, int max_instances, int max_samples_per_instance);
	/**
	 * @brief Get the userdata from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] value - Pointer that will store the userdata<br>
	 * @param[in,out] sz - Pointer that will store the size of userdata<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_userdata(__declspec(__restrict) const dds_qos_t*, void**, size_t*)</code><br>
	 */
	public static native byte dds_qget_userdata(PointerByReference qos, PointerByReference value, NativeSizeByReference sz);
	/**
	 * @brief Get the topicdata from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] value - Pointer that will store the topicdata<br>
	 * @param[in,out] sz - Pointer that will store the size of topicdata<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_topicdata(__declspec(__restrict) const dds_qos_t*, void**, size_t*)</code><br>
	 */
	public static native byte dds_qget_topicdata(PointerByReference qos, PointerByReference value, NativeSizeByReference sz);
	/**
	 * @brief Get the groupdata from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] value - Pointer that will store the groupdata<br>
	 * @param[in,out] sz - Pointer that will store the size of groupdata<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_groupdata(__declspec(__restrict) const dds_qos_t*, void**, size_t*)</code><br>
	 */
	public static native byte dds_qget_groupdata(PointerByReference qos, PointerByReference value, NativeSizeByReference sz);
	/**
	 * @brief Get the durability policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the durability kind<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_durability(__declspec(__restrict) const dds_qos_t*, dds_durability_kind_t*)</code><br>
	 */
	public static native byte dds_qget_durability(PointerByReference qos, IntBuffer kind);
	/**
	 * @brief Get the durability policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the durability kind<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_durability(__declspec(__restrict) const dds_qos_t*, dds_durability_kind_t*)</code><br>
	 */
	public static native byte dds_qget_durability(PointerByReference qos, IntByReference kind);
	/**
	 * @brief Get the history policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the history kind (optional)<br>
	 * @param[in,out] depth - Pointer that will store the history depth (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_history(__declspec(__restrict) const dds_qos_t*, dds_history_kind_t*, int32_t*)</code><br>
	 */
	public static native byte dds_qget_history(PointerByReference qos, IntBuffer kind, IntBuffer depth);
	/**
	 * @brief Get the history policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the history kind (optional)<br>
	 * @param[in,out] depth - Pointer that will store the history depth (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_history(__declspec(__restrict) const dds_qos_t*, dds_history_kind_t*, int32_t*)</code><br>
	 */
	public static native byte dds_qget_history(PointerByReference qos, IntByReference kind, IntByReference depth);
	/**
	 * @brief Get the resource-limits policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] max_samples - Pointer that will store the number of samples resource-limit (optional)<br>
	 * @param[in,out] max_instances - Pointer that will store the number of instances resource-limit (optional)<br>
	 * @param[in,out] max_samples_per_instance - Pointer that will store the number of samples per instance resource-limit (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_resource_limits(__declspec(__restrict) const dds_qos_t*, int32_t*, int32_t*, int32_t*)</code><br>
	 */
	public static native byte dds_qget_resource_limits(PointerByReference qos, IntBuffer max_samples, IntBuffer max_instances, IntBuffer max_samples_per_instance);
	/**
	 * @brief Get the resource-limits policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] max_samples - Pointer that will store the number of samples resource-limit (optional)<br>
	 * @param[in,out] max_instances - Pointer that will store the number of instances resource-limit (optional)<br>
	 * @param[in,out] max_samples_per_instance - Pointer that will store the number of samples per instance resource-limit (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_resource_limits(__declspec(__restrict) const dds_qos_t*, int32_t*, int32_t*, int32_t*)</code><br>
	 */
	public static native byte dds_qget_resource_limits(PointerByReference qos, IntByReference max_samples, IntByReference max_instances, IntByReference max_samples_per_instance);
	/**
	 * @brief Get the presentation policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] access_scope - Pointer that will store access scope kind (optional)<br>
	 * @param[in,out] coherent_access - Pointer that will store coherent access enable value (optional)<br>
	 * @param[in,out] ordered_access - Pointer that will store orderede access enable value (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_presentation(__declspec(__restrict) const dds_qos_t*, dds_presentation_access_scope_kind_t*, bool*, bool*)</code><br>
	 */
	public static native byte dds_qget_presentation(PointerByReference qos, IntBuffer access_scope, ByteBuffer coherent_access, ByteBuffer ordered_access);
	/**
	 * @brief Get the presentation policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] access_scope - Pointer that will store access scope kind (optional)<br>
	 * @param[in,out] coherent_access - Pointer that will store coherent access enable value (optional)<br>
	 * @param[in,out] ordered_access - Pointer that will store orderede access enable value (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_presentation(__declspec(__restrict) const dds_qos_t*, dds_presentation_access_scope_kind_t*, bool*, bool*)</code><br>
	 */
	public static native byte dds_qget_presentation(PointerByReference qos, IntByReference access_scope, Pointer coherent_access, Pointer ordered_access);
	/**
	 * @brief Get the lifespan policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] lifespan - Pointer that will store lifespan duration<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_lifespan(__declspec(__restrict) const dds_qos_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_lifespan(PointerByReference qos, LongBuffer lifespan);
	/**
	 * @brief Get the lifespan policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] lifespan - Pointer that will store lifespan duration<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_lifespan(__declspec(__restrict) const dds_qos_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_lifespan(PointerByReference qos, LongByReference lifespan);
	/**
	 * @brief Get the deadline policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] deadline - Pointer that will store deadline duration<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_deadline(__declspec(__restrict) const dds_qos_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_deadline(PointerByReference qos, LongBuffer deadline);
	/**
	 * @brief Get the deadline policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] deadline - Pointer that will store deadline duration<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_deadline(__declspec(__restrict) const dds_qos_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_deadline(PointerByReference qos, LongByReference deadline);
	/**
	 * @brief Get the latency-budget policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] duration - Pointer that will store latency-budget duration<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_latency_budget(__declspec(__restrict) const dds_qos_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_latency_budget(PointerByReference qos, LongBuffer duration);
	/**
	 * @brief Get the latency-budget policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] duration - Pointer that will store latency-budget duration<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_latency_budget(__declspec(__restrict) const dds_qos_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_latency_budget(PointerByReference qos, LongByReference duration);
	/**
	 * @brief Get the ownership policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the ownership kind<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_ownership(__declspec(__restrict) const dds_qos_t*, dds_ownership_kind_t*)</code><br>
	 */
	public static native byte dds_qget_ownership(PointerByReference qos, IntBuffer kind);
	/**
	 * @brief Get the ownership policy from a qos structure<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the ownership kind<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_ownership(__declspec(__restrict) const dds_qos_t*, dds_ownership_kind_t*)</code><br>
	 */
	public static native byte dds_qget_ownership(PointerByReference qos, IntByReference kind);
	/**
	 * @brief Get the ownership strength qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] value - Pointer that will store the ownership strength value<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_ownership_strength(__declspec(__restrict) const dds_qos_t*, int32_t*)</code><br>
	 */
	public static native byte dds_qget_ownership_strength(PointerByReference qos, IntBuffer value);
	/**
	 * @brief Get the ownership strength qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] value - Pointer that will store the ownership strength value<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_ownership_strength(__declspec(__restrict) const dds_qos_t*, int32_t*)</code><br>
	 */
	public static native byte dds_qget_ownership_strength(PointerByReference qos, IntByReference value);
	/**
	 * @brief Get the liveliness qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the liveliness kind (optional)<br>
	 * @param[in,out] lease_duration - Pointer that will store the liveliness lease duration (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_liveliness(__declspec(__restrict) const dds_qos_t*, dds_liveliness_kind_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_liveliness(PointerByReference qos, IntBuffer kind, LongBuffer lease_duration);
	/**
	 * @brief Get the liveliness qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the liveliness kind (optional)<br>
	 * @param[in,out] lease_duration - Pointer that will store the liveliness lease duration (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_liveliness(__declspec(__restrict) const dds_qos_t*, dds_liveliness_kind_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_liveliness(PointerByReference qos, IntByReference kind, LongByReference lease_duration);
	/**
	 * @brief Get the time-based filter qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] minimum_separation - Pointer that will store the minimum separation duration (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_time_based_filter(__declspec(__restrict) const dds_qos_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_time_based_filter(PointerByReference qos, LongBuffer minimum_separation);
	/**
	 * @brief Get the time-based filter qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] minimum_separation - Pointer that will store the minimum separation duration (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_time_based_filter(__declspec(__restrict) const dds_qos_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_time_based_filter(PointerByReference qos, LongByReference minimum_separation);
	/**
	 * @brief Get the partition qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] n - Pointer that will store the number of partitions (optional)<br>
	 * @param[in,out] ps - Pointer that will store the string(s) containing partition name(s) (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_partition(__declspec(__restrict) const dds_qos_t*, uint32_t*, char***)</code><br>
	 */
	public static native byte dds_qget_partition(PointerByReference qos, IntBuffer n, PointerByReference ps);
	/**
	 * @brief Get the partition qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] n - Pointer that will store the number of partitions (optional)<br>
	 * @param[in,out] ps - Pointer that will store the string(s) containing partition name(s) (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_partition(__declspec(__restrict) const dds_qos_t*, uint32_t*, char***)</code><br>
	 */
	public static native byte dds_qget_partition(PointerByReference qos, IntByReference n, PointerByReference ps);
	/**
	 * @brief Get the reliability qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the reliability kind (optional)<br>
	 * @param[in,out] max_blocking_time - Pointer that will store the max blocking time for reliable reliability (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_reliability(__declspec(__restrict) const dds_qos_t*, dds_reliability_kind_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_reliability(PointerByReference qos, IntBuffer kind, LongBuffer max_blocking_time);
	/**
	 * @brief Get the reliability qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the reliability kind (optional)<br>
	 * @param[in,out] max_blocking_time - Pointer that will store the max blocking time for reliable reliability (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_reliability(__declspec(__restrict) const dds_qos_t*, dds_reliability_kind_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_reliability(PointerByReference qos, IntByReference kind, LongByReference max_blocking_time);
	/**
	 * @brief Get the transport priority qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] value - Pointer that will store the transport priority value<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_transport_priority(__declspec(__restrict) const dds_qos_t*, int32_t*)</code><br>
	 */
	public static native byte dds_qget_transport_priority(PointerByReference qos, IntBuffer value);
	/**
	 * @brief Get the transport priority qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] value - Pointer that will store the transport priority value<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_transport_priority(__declspec(__restrict) const dds_qos_t*, int32_t*)</code><br>
	 */
	public static native byte dds_qget_transport_priority(PointerByReference qos, IntByReference value);
	/**
	 * @brief Get the destination-order qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the destination-order kind<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_destination_order(__declspec(__restrict) const dds_qos_t*, dds_destination_order_kind_t*)</code><br>
	 */
	public static native byte dds_qget_destination_order(PointerByReference qos, IntBuffer kind);
	/**
	 * @brief Get the destination-order qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] kind - Pointer that will store the destination-order kind<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_destination_order(__declspec(__restrict) const dds_qos_t*, dds_destination_order_kind_t*)</code><br>
	 */
	public static native byte dds_qget_destination_order(PointerByReference qos, IntByReference kind);
	/**
	 * @brief Get the writer data-lifecycle qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] autodispose_unregistered_instances - Pointer that will store the autodispose unregistered instances enable value<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_writer_data_lifecycle(__declspec(__restrict) const dds_qos_t*, bool*)</code><br>
	 */
	public static native byte dds_qget_writer_data_lifecycle(PointerByReference qos, ByteBuffer autodispose);
	/**
	 * @brief Get the writer data-lifecycle qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] autodispose_unregistered_instances - Pointer that will store the autodispose unregistered instances enable value<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_writer_data_lifecycle(__declspec(__restrict) const dds_qos_t*, bool*)</code><br>
	 */
	public static native byte dds_qget_writer_data_lifecycle(PointerByReference qos, Pointer autodispose);
	/**
	 * @brief Get the reader data-lifecycle qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] autopurge_nowriter_samples_delay - Pointer that will store the delay for auto-purging samples from instances in a no-writer state (optional)<br>
	 * @param[in,out] autopurge_disposed_samples_delay - Pointer that will store the delay for auto-purging of disposed instances (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_reader_data_lifecycle(__declspec(__restrict) const dds_qos_t*, dds_duration_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_reader_data_lifecycle(PointerByReference qos, LongBuffer autopurge_nowriter_samples_delay, LongBuffer autopurge_disposed_samples_delay);
	/**
	 * @brief Get the reader data-lifecycle qos policy<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out] autopurge_nowriter_samples_delay - Pointer that will store the delay for auto-purging samples from instances in a no-writer state (optional)<br>
	 * @param[in,out] autopurge_disposed_samples_delay - Pointer that will store the delay for auto-purging of disposed instances (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_reader_data_lifecycle(__declspec(__restrict) const dds_qos_t*, dds_duration_t*, dds_duration_t*)</code><br>
	 */
	public static native byte dds_qget_reader_data_lifecycle(PointerByReference qos, LongByReference autopurge_nowriter_samples_delay, LongByReference autopurge_disposed_samples_delay);
	/**
	 * @brief Get the durability-service qos policy values.<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out]  service_cleanup_delay - Pointer that will store the delay for purging of abandoned instances from the durability service (optional)<br>
	 * @param[in,out] history_kind - Pointer that will store history policy kind applied by the durability service (optional)<br>
	 * @param[in,out] history_depth - Pointer that will store history policy depth applied by the durability service (optional)<br>
	 * @param[in,out] max_samples - Pointer that will store number of samples resource-limit policy applied by the durability service (optional)<br>
	 * @param[in,out] max_instances - Pointer that will store number of instances resource-limit policy applied by the durability service (optional)<br>
	 * @param[in,out] max_samples_per_instance - Pointer that will store number of samples per instance resource-limit policy applied by the durability service (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_durability_service(__declspec(__restrict) const dds_qos_t*, dds_duration_t*, dds_history_kind_t*, int32_t*, int32_t*, int32_t*, int32_t*)</code><br>
	 */
	public static native byte dds_qget_durability_service(PointerByReference qos, LongBuffer service_cleanup_delay, IntBuffer history_kind, IntBuffer history_depth, IntBuffer max_samples, IntBuffer max_instances, IntBuffer max_samples_per_instance);
	/**
	 * @brief Get the durability-service qos policy values.<br>
	 * @param[in] qos - Pointer to a dds_qos_t structure storing the policy<br>
	 * @param[in,out]  service_cleanup_delay - Pointer that will store the delay for purging of abandoned instances from the durability service (optional)<br>
	 * @param[in,out] history_kind - Pointer that will store history policy kind applied by the durability service (optional)<br>
	 * @param[in,out] history_depth - Pointer that will store history policy depth applied by the durability service (optional)<br>
	 * @param[in,out] max_samples - Pointer that will store number of samples resource-limit policy applied by the durability service (optional)<br>
	 * @param[in,out] max_instances - Pointer that will store number of instances resource-limit policy applied by the durability service (optional)<br>
	 * @param[in,out] max_samples_per_instance - Pointer that will store number of samples per instance resource-limit policy applied by the durability service (optional)<br>
	 * @returns - false iff any of the arguments is invalid or the qos is not present in the qos object<br>
	 * Original signature : <code>bool dds_qget_durability_service(__declspec(__restrict) const dds_qos_t*, dds_duration_t*, dds_history_kind_t*, int32_t*, int32_t*, int32_t*, int32_t*)</code><br>
	 */
	public static native byte dds_qget_durability_service(PointerByReference qos, LongByReference service_cleanup_delay, IntByReference history_kind, IntByReference history_depth, IntByReference max_samples, IntByReference max_instances, IntByReference max_samples_per_instance);

	/*
	MANUALLY REMOVED
	now in org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_qos_t
	public static class dds_qos_t extends PointerType {
		public dds_qos_t(Pointer address) {
			super(address);
		}
		public dds_qos_t() {
			super();
		}
	};
	*/
}
