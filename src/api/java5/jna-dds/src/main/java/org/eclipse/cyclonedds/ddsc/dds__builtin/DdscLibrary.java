package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
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
	/**
	 * <i>native declaration : dds_builtinTopics.h:95</i><br>
	 * enum values
	 */
	public static interface DDS_DurabilityQosPolicyKind {
		/** <i>native declaration : dds_builtinTopics.h:91</i> */
		public static final int DDS_VOLATILE_DURABILITY_QOS = 0;
		/** <i>native declaration : dds_builtinTopics.h:92</i> */
		public static final int DDS_TRANSIENT_LOCAL_DURABILITY_QOS = 1;
		/** <i>native declaration : dds_builtinTopics.h:93</i> */
		public static final int DDS_TRANSIENT_DURABILITY_QOS = 2;
		/** <i>native declaration : dds_builtinTopics.h:94</i> */
		public static final int DDS_PERSISTENT_DURABILITY_QOS = 3;
	};
	/**
	 * <i>native declaration : dds_builtinTopics.h:103</i><br>
	 * enum values
	 */
	public static interface DDS_PresentationQosPolicyAccessScopeKind {
		/** <i>native declaration : dds_builtinTopics.h:100</i> */
		public static final int DDS_INSTANCE_PRESENTATION_QOS = 0;
		/** <i>native declaration : dds_builtinTopics.h:101</i> */
		public static final int DDS_TOPIC_PRESENTATION_QOS = 1;
		/** <i>native declaration : dds_builtinTopics.h:102</i> */
		public static final int DDS_GROUP_PRESENTATION_QOS = 2;
	};
	/**
	 * <i>native declaration : dds_builtinTopics.h:118</i><br>
	 * enum values
	 */
	public static interface DDS_OwnershipQosPolicyKind {
		/** <i>native declaration : dds_builtinTopics.h:116</i> */
		public static final int DDS_SHARED_OWNERSHIP_QOS = 0;
		/** <i>native declaration : dds_builtinTopics.h:117</i> */
		public static final int DDS_EXCLUSIVE_OWNERSHIP_QOS = 1;
	};
	/**
	 * <i>native declaration : dds_builtinTopics.h:129</i><br>
	 * enum values
	 */
	public static interface DDS_LivelinessQosPolicyKind {
		/** <i>native declaration : dds_builtinTopics.h:126</i> */
		public static final int DDS_AUTOMATIC_LIVELINESS_QOS = 0;
		/** <i>native declaration : dds_builtinTopics.h:127</i> */
		public static final int DDS_MANUAL_BY_PARTICIPANT_LIVELINESS_QOS = 1;
		/** <i>native declaration : dds_builtinTopics.h:128</i> */
		public static final int DDS_MANUAL_BY_TOPIC_LIVELINESS_QOS = 2;
	};
	/**
	 * <i>native declaration : dds_builtinTopics.h:143</i><br>
	 * enum values
	 */
	public static interface DDS_ReliabilityQosPolicyKind {
		/** <i>native declaration : dds_builtinTopics.h:141</i> */
		public static final int DDS_BEST_EFFORT_RELIABILITY_QOS = 0;
		/** <i>native declaration : dds_builtinTopics.h:142</i> */
		public static final int DDS_RELIABLE_RELIABILITY_QOS = 1;
	};
	/**
	 * <i>native declaration : dds_builtinTopics.h:152</i><br>
	 * enum values
	 */
	public static interface DDS_DestinationOrderQosPolicyKind {
		/** <i>native declaration : dds_builtinTopics.h:150</i> */
		public static final int DDS_BY_RECEPTION_TIMESTAMP_DESTINATIONORDER_QOS = 0;
		/** <i>native declaration : dds_builtinTopics.h:151</i> */
		public static final int DDS_BY_SOURCE_TIMESTAMP_DESTINATIONORDER_QOS = 1;
	};
	/**
	 * <i>native declaration : dds_builtinTopics.h:159</i><br>
	 * enum values
	 */
	public static interface DDS_HistoryQosPolicyKind {
		/** <i>native declaration : dds_builtinTopics.h:157</i> */
		public static final int DDS_KEEP_LAST_HISTORY_QOS = 0;
		/** <i>native declaration : dds_builtinTopics.h:158</i> */
		public static final int DDS_KEEP_ALL_HISTORY_QOS = 1;
	};
	/**
	 * <i>native declaration : dds_builtinTopics.h:196</i><br>
	 * enum values
	 */
	public static interface DDS_InvalidSampleVisibilityQosPolicyKind {
		/** <i>native declaration : dds_builtinTopics.h:193</i> */
		public static final int DDS_NO_INVALID_SAMPLES = 0;
		/** <i>native declaration : dds_builtinTopics.h:194</i> */
		public static final int DDS_MINIMUM_INVALID_SAMPLES = 1;
		/** <i>native declaration : dds_builtinTopics.h:195</i> */
		public static final int DDS_ALL_INVALID_SAMPLES = 2;
	};
	/** <i>native declaration : dds_builtinTopics.h</i> */
	public static final int DDS_XCDR_REPRESENTATION = (int)0;
	/** <i>native declaration : dds_builtinTopics.h</i> */
	public static final int DDS_XML_REPRESENTATION = (int)1;
	/** <i>native declaration : dds_builtinTopics.h</i> */
	public static final int DDS_OSPL_REPRESENTATION = (int)1024;
	/** <i>native declaration : dds_builtinTopics.h</i> */
	public static final int DDS_GPB_REPRESENTATION = (int)1025;
	/** <i>native declaration : dds_builtinTopics.h</i> */
	public static final int DDS_INVALID_REPRESENTATION = (int)32767;
	/**
	 * Get actual topic in related participant related to topic 'id'.<br>
	 * Original signature : <code>dds_entity_t dds__get_builtin_topic(dds_entity_t, dds_entity_t)</code><br>
	 * <i>native declaration : _dds__builtin.h:347</i>
	 */
	public static native int dds__get_builtin_topic(int e, int topic);
	/**
	 * Global publisher singleton (publishes only locally).<br>
	 * Original signature : <code>dds_entity_t dds__get_builtin_publisher()</code><br>
	 * <i>native declaration : _dds__builtin.h:352</i>
	 */
	public static native int dds__get_builtin_publisher();
	/**
	 * Subscriber singleton within related participant.<br>
	 * Original signature : <code>dds_entity_t dds__get_builtin_subscriber(dds_entity_t)</code><br>
	 * <i>native declaration : _dds__builtin.h:357</i>
	 */
	public static native int dds__get_builtin_subscriber(int e);
	/**
	 * Initialization and finalize functions.<br>
	 * Original signature : <code>void dds__builtin_init()</code><br>
	 * <i>native declaration : _dds__builtin.h:362</i>
	 */
	public static native void dds__builtin_init();
	/**
	 * Original signature : <code>void dds__builtin_fini()</code><br>
	 * <i>native declaration : _dds__builtin.h:364</i>
	 */
	public static native void dds__builtin_fini();
	/**
	 * Callback functions that contain received builtin data.<br>
	 * Original signature : <code>void dds__builtin_participant_cb(DDS_ParticipantBuiltinTopicData*, nn_wctime_t)</code><br>
	 * <i>native declaration : _dds__builtin.h:369</i>
	 */
	public static native void dds__builtin_participant_cb(DDS_ParticipantBuiltinTopicData data, DdscLibrary.nn_wctime_t timestamp);
	/**
	 * Original signature : <code>void dds__builtin_cmparticipant_cb(DDS_CMParticipantBuiltinTopicData*, nn_wctime_t)</code><br>
	 * <i>native declaration : _dds__builtin.h:371</i>
	 */
	public static native void dds__builtin_cmparticipant_cb(DDS_CMParticipantBuiltinTopicData data, DdscLibrary.nn_wctime_t timestamp);
	public static class nn_wctime_t extends PointerType {
		public nn_wctime_t(Pointer address) {
			super(address);
		}
		public nn_wctime_t() {
			super();
		}
	};
}
