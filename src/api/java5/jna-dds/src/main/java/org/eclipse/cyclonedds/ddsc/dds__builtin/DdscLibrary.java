package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.ddsc.dds_builtinTopics.*;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
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
	public static interface DDS_DurabilityQosPolicyKind {
		public static final int DDS_VOLATILE_DURABILITY_QOS = 0;
		public static final int DDS_TRANSIENT_LOCAL_DURABILITY_QOS = 1;
		public static final int DDS_TRANSIENT_DURABILITY_QOS = 2;
		public static final int DDS_PERSISTENT_DURABILITY_QOS = 3;
	};
	/**
	 * enum values
	 */
	public static interface DDS_PresentationQosPolicyAccessScopeKind {
		public static final int DDS_INSTANCE_PRESENTATION_QOS = 0;
		public static final int DDS_TOPIC_PRESENTATION_QOS = 1;
		public static final int DDS_GROUP_PRESENTATION_QOS = 2;
	};
	/**
	 * enum values
	 */
	public static interface DDS_OwnershipQosPolicyKind {
		public static final int DDS_SHARED_OWNERSHIP_QOS = 0;
		public static final int DDS_EXCLUSIVE_OWNERSHIP_QOS = 1;
	};
	/**
	 * enum values
	 */
	public static interface DDS_LivelinessQosPolicyKind {
		public static final int DDS_AUTOMATIC_LIVELINESS_QOS = 0;
		public static final int DDS_MANUAL_BY_PARTICIPANT_LIVELINESS_QOS = 1;
		public static final int DDS_MANUAL_BY_TOPIC_LIVELINESS_QOS = 2;
	};
	/**
	 * enum values
	 */
	public static interface DDS_ReliabilityQosPolicyKind {
		public static final int DDS_BEST_EFFORT_RELIABILITY_QOS = 0;
		public static final int DDS_RELIABLE_RELIABILITY_QOS = 1;
	};
	/**
	 * enum values
	 */
	public static interface DDS_DestinationOrderQosPolicyKind {
		public static final int DDS_BY_RECEPTION_TIMESTAMP_DESTINATIONORDER_QOS = 0;
		public static final int DDS_BY_SOURCE_TIMESTAMP_DESTINATIONORDER_QOS = 1;
	};
	/**
	 * enum values
	 */
	public static interface DDS_HistoryQosPolicyKind {
		public static final int DDS_KEEP_LAST_HISTORY_QOS = 0;
		public static final int DDS_KEEP_ALL_HISTORY_QOS = 1;
	};
	/**
	 * enum values
	 */
	public static interface DDS_InvalidSampleVisibilityQosPolicyKind {
		public static final int DDS_NO_INVALID_SAMPLES = 0;
		public static final int DDS_MINIMUM_INVALID_SAMPLES = 1;
		public static final int DDS_ALL_INVALID_SAMPLES = 2;
	};
	public static final int DDS_XCDR_REPRESENTATION = (int)0;
	public static final int DDS_XML_REPRESENTATION = (int)1;
	public static final int DDS_OSPL_REPRESENTATION = (int)1024;
	public static final int DDS_GPB_REPRESENTATION = (int)1025;
	public static final int DDS_INVALID_REPRESENTATION = (int)32767;
	/**
	 * Get actual topic in related participant related to topic 'id'.<br>
	 * Original signature : <code>dds_entity_t dds__get_builtin_topic(dds_entity_t, dds_entity_t)</code><br>
	 */
	public static native int dds__get_builtin_topic(int e, int topic);
	/**
	 * Global publisher singleton (publishes only locally).<br>
	 * Original signature : <code>dds_entity_t dds__get_builtin_publisher()</code><br>
	 */
	public static native int dds__get_builtin_publisher();
	/**
	 * Subscriber singleton within related participant.<br>
	 * Original signature : <code>dds_entity_t dds__get_builtin_subscriber(dds_entity_t)</code><br>
	 */
	public static native int dds__get_builtin_subscriber(int e);
	/**
	 * Initialization and finalize functions.<br>
	 * Original signature : <code>void dds__builtin_init()</code><br>
	 */
	public static native void dds__builtin_init();
	/**
	 * Original signature : <code>void dds__builtin_fini()</code><br>
	 */
	public static native void dds__builtin_fini();
}
