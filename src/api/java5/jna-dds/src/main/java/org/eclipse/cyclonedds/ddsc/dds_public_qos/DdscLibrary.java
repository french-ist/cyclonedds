package org.eclipse.cyclonedds.ddsc.dds_public_qos;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.PointerByReference;
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
	 * <i>native declaration : _dds_public_qos.h:57</i><br>
	 * enum values
	 */
	public static interface dds_durability_kind {
		/** <i>native declaration : _dds_public_qos.h:53</i> */
		public static final int DDS_DURABILITY_VOLATILE = 0;
		/** <i>native declaration : _dds_public_qos.h:54</i> */
		public static final int DDS_DURABILITY_TRANSIENT_LOCAL = 1;
		/** <i>native declaration : _dds_public_qos.h:55</i> */
		public static final int DDS_DURABILITY_TRANSIENT = 2;
		/** <i>native declaration : _dds_public_qos.h:56</i> */
		public static final int DDS_DURABILITY_PERSISTENT = 3;
	};
	/**
	 * <i>native declaration : _dds_public_qos.h:62</i><br>
	 * enum values
	 */
	public static interface dds_history_kind {
		/** <i>native declaration : _dds_public_qos.h:60</i> */
		public static final int DDS_HISTORY_KEEP_LAST = 0;
		/** <i>native declaration : _dds_public_qos.h:61</i> */
		public static final int DDS_HISTORY_KEEP_ALL = 1;
	};
	/**
	 * <i>native declaration : _dds_public_qos.h:67</i><br>
	 * enum values
	 */
	public static interface dds_ownership_kind {
		/** <i>native declaration : _dds_public_qos.h:65</i> */
		public static final int DDS_OWNERSHIP_SHARED = 0;
		/** <i>native declaration : _dds_public_qos.h:66</i> */
		public static final int DDS_OWNERSHIP_EXCLUSIVE = 1;
	};
	/**
	 * <i>native declaration : _dds_public_qos.h:73</i><br>
	 * enum values
	 */
	public static interface dds_liveliness_kind {
		/** <i>native declaration : _dds_public_qos.h:70</i> */
		public static final int DDS_LIVELINESS_AUTOMATIC = 0;
		/** <i>native declaration : _dds_public_qos.h:71</i> */
		public static final int DDS_LIVELINESS_MANUAL_BY_PARTICIPANT = 1;
		/** <i>native declaration : _dds_public_qos.h:72</i> */
		public static final int DDS_LIVELINESS_MANUAL_BY_TOPIC = 2;
	};
	/**
	 * <i>native declaration : _dds_public_qos.h:78</i><br>
	 * enum values
	 */
	public static interface dds_reliability_kind {
		/** <i>native declaration : _dds_public_qos.h:76</i> */
		public static final int DDS_RELIABILITY_BEST_EFFORT = 0;
		/** <i>native declaration : _dds_public_qos.h:77</i> */
		public static final int DDS_RELIABILITY_RELIABLE = 1;
	};
	/**
	 * <i>native declaration : _dds_public_qos.h:83</i><br>
	 * enum values
	 */
	public static interface dds_destination_order_kind {
		/** <i>native declaration : _dds_public_qos.h:81</i> */
		public static final int DDS_DESTINATIONORDER_BY_RECEPTION_TIMESTAMP = 0;
		/** <i>native declaration : _dds_public_qos.h:82</i> */
		public static final int DDS_DESTINATIONORDER_BY_SOURCE_TIMESTAMP = 1;
	};
	/**
	 * <i>native declaration : _dds_public_qos.h:100</i><br>
	 * enum values
	 */
	public static interface dds_presentation_access_scope_kind {
		/** <i>native declaration : _dds_public_qos.h:97</i> */
		public static final int DDS_PRESENTATION_INSTANCE = 0;
		/** <i>native declaration : _dds_public_qos.h:98</i> */
		public static final int DDS_PRESENTATION_TOPIC = 1;
		/** <i>native declaration : _dds_public_qos.h:99</i> */
		public static final int DDS_PRESENTATION_GROUP = 2;
	};
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_INVALID_QOS_POLICY_ID = (int)0;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_USERDATA_QOS_POLICY_ID = (int)1;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_DURABILITY_QOS_POLICY_ID = (int)2;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_PRESENTATION_QOS_POLICY_ID = (int)3;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_DEADLINE_QOS_POLICY_ID = (int)4;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_LATENCYBUDGET_QOS_POLICY_ID = (int)5;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_OWNERSHIP_QOS_POLICY_ID = (int)6;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_OWNERSHIPSTRENGTH_QOS_POLICY_ID = (int)7;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_LIVELINESS_QOS_POLICY_ID = (int)8;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_TIMEBASEDFILTER_QOS_POLICY_ID = (int)9;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_PARTITION_QOS_POLICY_ID = (int)10;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_RELIABILITY_QOS_POLICY_ID = (int)11;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_DESTINATIONORDER_QOS_POLICY_ID = (int)12;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_HISTORY_QOS_POLICY_ID = (int)13;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_RESOURCELIMITS_QOS_POLICY_ID = (int)14;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_ENTITYFACTORY_QOS_POLICY_ID = (int)15;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_WRITERDATALIFECYCLE_QOS_POLICY_ID = (int)16;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_READERDATALIFECYCLE_QOS_POLICY_ID = (int)17;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_TOPICDATA_QOS_POLICY_ID = (int)18;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_GROUPDATA_QOS_POLICY_ID = (int)19;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_TRANSPORTPRIORITY_QOS_POLICY_ID = (int)20;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_LIFESPAN_QOS_POLICY_ID = (int)21;
	/** <i>native declaration : _dds_public_qos.h</i> */
	public static final int DDS_DURABILITYSERVICE_QOS_POLICY_ID = (int)22;
	/**
	 * @brief Allocate memory and initialize default QoS-policies<br>
	 * @returns - Pointer to the initialized dds_qos_t structure, NULL if unsuccessful.<br>
	 * Original signature : <code>dds_qos_t* dds_qos_create()</code><br>
	 * <i>native declaration : _dds_public_qos.h:106</i>
	 */
	public static native PointerByReference dds_qos_create();
	/**
	 * @brief Delete memory allocated to QoS-policies structure<br>
	 * @param[in] qos - Pointer to dds_qos_t structure<br>
	 * Original signature : <code>void dds_qos_delete(dds_qos_t*)</code><br>
	 * <i>native declaration : _dds_public_qos.h:112</i>
	 */
	public static native void dds_qos_delete(PointerByReference qos);
	/**
	 * @brief Reset a QoS-policies structure to default values<br>
	 * @param[in,out] qos - Pointer to the dds_qos_t structure<br>
	 * Original signature : <code>void dds_qos_reset(dds_qos_t*)</code><br>
	 * <i>native declaration : _dds_public_qos.h:118</i>
	 */
	public static native void dds_qos_reset(PointerByReference qos);
	/**
	 * @brief Copy all QoS-policies from one structure to another<br>
	 * @param[in,out] dst - Pointer to the destination dds_qos_t structure<br>
	 * @param[in] src - Pointer to the source dds_qos_t structure<br>
	 * @returns - Return-code indicating success or failure<br>
	 * Original signature : <code>dds_return_t dds_qos_copy(dds_qos_t*, const dds_qos_t*)</code><br>
	 * <i>native declaration : _dds_public_qos.h:126</i>
	 */
	public static native int dds_qos_copy(PointerByReference dst, PointerByReference src);
	/**
	 * @brief Copy all QoS-policies from one structure to another, unless already set<br>
	 * Policies are copied from src to dst, unless src already has the policy set to a non-default value.<br>
	 * @param[in,out] dst - Pointer to the destination qos structure<br>
	 * @param[in] src - Pointer to the source qos structure<br>
	 * Original signature : <code>void dds_qos_merge(dds_qos_t*, const dds_qos_t*)</code><br>
	 * <i>native declaration : _dds_public_qos.h:134</i>
	 */
	public static native void dds_qos_merge(PointerByReference dst, PointerByReference src);
	/**
	 * @brief Copy all QoS-policies from one structure to another, unless already set<br>
	 * Policies are copied from src to dst, unless src already has the policy set to a non-default value.<br>
	 * @param[in,out] dst - Pointer to the destination qos structure<br>
	 * @param[in] src - Pointer to the source qos structure<br>
	 * Original signature : <code>bool dds_qos_equal(const dds_qos_t*, const dds_qos_t*)</code><br>
	 * <i>native declaration : _dds_public_qos.h:142</i>
	 */
	public static native byte dds_qos_equal(PointerByReference a, PointerByReference b);
	public static class dds_qos_t extends PointerType {
		public dds_qos_t(Pointer address) {
			super(address);
		}
		public dds_qos_t() {
			super();
		}
	};
}
