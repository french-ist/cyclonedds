package org.eclipse.cyclonedds.ddsc.dds;
import org.eclipse.cyclonedds.helper.*;
import org.eclipse.cyclonedds.ddsc.dds_public_impl.*;
import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
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
	 * <i>native declaration : _dds.h:125</i><br>
	 * enum values
	 */
	public static interface dds_entity_kind {
		/** <i>native declaration : _dds.h:113</i> */
		public static final int DDS_KIND_DONTCARE = 0x00000000;
		/** <i>native declaration : _dds.h:114</i> */
		public static final int DDS_KIND_TOPIC = 0x01000000;
		/** <i>native declaration : _dds.h:115</i> */
		public static final int DDS_KIND_PARTICIPANT = 0x02000000;
		/** <i>native declaration : _dds.h:116</i> */
		public static final int DDS_KIND_READER = 0x03000000;
		/** <i>native declaration : _dds.h:117</i> */
		public static final int DDS_KIND_WRITER = 0x04000000;
		/** <i>native declaration : _dds.h:118</i> */
		public static final int DDS_KIND_SUBSCRIBER = 0x05000000;
		/** <i>native declaration : _dds.h:119</i> */
		public static final int DDS_KIND_PUBLISHER = 0x06000000;
		/** <i>native declaration : _dds.h:120</i> */
		public static final int DDS_KIND_COND_READ = 0x07000000;
		/** <i>native declaration : _dds.h:121</i> */
		public static final int DDS_KIND_COND_QUERY = 0x08000000;
		/** <i>native declaration : _dds.h:122</i> */
		public static final int DDS_KIND_COND_GUARD = 0x09000000;
		/** <i>native declaration : _dds.h:123</i> */
		public static final int DDS_KIND_WAITSET = 0x0A000000;
		/** <i>native declaration : _dds.h:124</i> */
		public static final int DDS_KIND_INTERNAL = 0x0B000000;
	};
	/**
	 * <i>native declaration : _dds.h:193</i><br>
	 * enum values
	 */
	public static interface dds_sample_state {
		/**
		 * <DataReader has already accessed the sample by read<br>
		 * <i>native declaration : _dds.h:191</i>
		 */
		public static final int DDS_SST_READ = 1;
		/**
		 * <DataReader has not accessed the sample before<br>
		 * <i>native declaration : _dds.h:192</i>
		 */
		public static final int DDS_SST_NOT_READ = 2;
	};
	/**
	 * <i>native declaration : _dds.h:198</i><br>
	 * enum values
	 */
	public static interface dds_view_state {
		/** <i>native declaration : _dds.h:196</i> */
		public static final int DDS_VST_NEW = 4;
		/** <i>native declaration : _dds.h:197</i> */
		public static final int DDS_VST_OLD = 8;
	};
	/**
	 * <i>native declaration : _dds.h:204</i><br>
	 * enum values
	 */
	public static interface dds_instance_state {
		/** <i>native declaration : _dds.h:201</i> */
		public static final int DDS_IST_ALIVE = 16;
		/** <i>native declaration : _dds.h:202</i> */
		public static final int DDS_IST_NOT_ALIVE_DISPOSED = 32;
		/** <i>native declaration : _dds.h:203</i> */
		public static final int DDS_IST_NOT_ALIVE_NO_WRITERS = 64;
	};
	/** <i>native declaration : _dds.h</i> */
	public static final long DDS_NSECS_IN_SEC = (long)1000000000L;
	/** <i>native declaration : _dds.h</i> */
	public static final long DDS_NSECS_IN_MSEC = (long)1000000L;
	/** <i>native declaration : _dds.h</i> */
	public static final long DDS_NSECS_IN_USEC = (long)1000L;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_LENGTH_UNLIMITED = (int)-1;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_TOPIC_NO_OPTIMIZE = (int)0x0001;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_TOPIC_FIXED_KEY = (int)0x0002;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_READ_SAMPLE_STATE = (int)1;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_NOT_READ_SAMPLE_STATE = (int)2;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_ANY_SAMPLE_STATE = (int)(1 | 2);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_NEW_VIEW_STATE = (int)4;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_NOT_NEW_VIEW_STATE = (int)8;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_ANY_VIEW_STATE = (int)(4 | 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_ALIVE_INSTANCE_STATE = (int)16;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_NOT_ALIVE_DISPOSED_INSTANCE_STATE = (int)32;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_NOT_ALIVE_NO_WRITERS_INSTANCE_STATE = (int)64;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_ANY_INSTANCE_STATE = (int)(16 | 32 | 64);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_ANY_STATE = (int)((1 | 2) | (4 | 8) | (16 | 32 | 64));
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_DOMAIN_DEFAULT = (int)-1;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_HANDLE_NIL = (int)0;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_ENTITY_NIL = (int)0;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_ENTITY_KIND_MASK = (int)(0x7F000000);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_RTS = (int)0x00000000;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_ADR = (int)0x01000000;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_JSR = (int)0x02000000;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_JEQ = (int)0x03000000;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_VAL_1BY = (int)0x01;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_VAL_2BY = (int)0x02;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_VAL_4BY = (int)0x03;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_VAL_8BY = (int)0x04;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_VAL_STR = (int)0x05;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_VAL_BST = (int)0x06;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_VAL_SEQ = (int)0x07;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_VAL_ARR = (int)0x08;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_VAL_UNI = (int)0x09;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_VAL_STU = (int)0x0a;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_TYPE_1BY = (int)(0x01 << 16);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_TYPE_2BY = (int)(0x02 << 16);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_TYPE_4BY = (int)(0x03 << 16);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_TYPE_8BY = (int)(0x04 << 16);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_TYPE_STR = (int)(0x05 << 16);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_TYPE_SEQ = (int)(0x07 << 16);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_TYPE_ARR = (int)(0x08 << 16);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_TYPE_UNI = (int)(0x09 << 16);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_TYPE_STU = (int)(0x0a << 16);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_TYPE_BST = (int)(0x06 << 16);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_TYPE_BOO = (int)(0x01 << 16);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_SUBTYPE_BOO = (int)(0x01 << 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_SUBTYPE_1BY = (int)(0x01 << 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_SUBTYPE_2BY = (int)(0x02 << 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_SUBTYPE_4BY = (int)(0x03 << 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_SUBTYPE_8BY = (int)(0x04 << 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_SUBTYPE_STR = (int)(0x05 << 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_SUBTYPE_SEQ = (int)(0x07 << 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_SUBTYPE_ARR = (int)(0x08 << 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_SUBTYPE_UNI = (int)(0x09 << 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_SUBTYPE_STU = (int)(0x0a << 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_SUBTYPE_BST = (int)(0x06 << 8);
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_FLAG_KEY = (int)0x01;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OP_FLAG_DEF = (int)0x02;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_INCONSISTENT_TOPIC_STATUS = (int)1;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OFFERED_DEADLINE_MISSED_STATUS = (int)2;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_REQUESTED_DEADLINE_MISSED_STATUS = (int)4;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_OFFERED_INCOMPATIBLE_QOS_STATUS = (int)32;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_REQUESTED_INCOMPATIBLE_QOS_STATUS = (int)64;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_SAMPLE_LOST_STATUS = (int)128;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_SAMPLE_REJECTED_STATUS = (int)256;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_DATA_ON_READERS_STATUS = (int)512;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_DATA_AVAILABLE_STATUS = (int)1024;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_LIVELINESS_LOST_STATUS = (int)2048;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_LIVELINESS_CHANGED_STATUS = (int)4096;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_PUBLICATION_MATCHED_STATUS = (int)8192;
	/** <i>native declaration : _dds.h</i> */
	public static final int DDS_SUBSCRIPTION_MATCHED_STATUS = (int)16384;
	/** <i>native declaration : _dds.h:542</i> */
	public interface dds_topic_filter_fn extends Callback {
		byte apply(Pointer sample);
	};
	/** <i>native declaration : _dds.h:1010</i> */
	public interface dds_querycondition_filter_fn extends Callback {
		byte apply(Pointer sample);
	};
	/**
	 * Description : This operation returns the current time (in nanoseconds)<br>
	 * Arguments :<br>
	 *   -# Returns current time<br>
	 * Original signature : <code>dds_time_t dds_time()</code><br>
	 * <i>native declaration : _dds.h:59</i>
	 */
	public static native long dds_time();
	/**
	 * Description : This operation blocks the calling thread until the relative time<br>
	 * n has elapsed<br>
	 * Arguments :<br>
	 *   -# n Relative Time to block a thread<br>
	 * Original signature : <code>void dds_sleepfor(dds_duration_t)</code><br>
	 * <i>native declaration : _dds.h:67</i>
	 */
	public static native void dds_sleepfor(long n);
	/**
	 * Description : This operation blocks the calling thread until the absolute time<br>
	 * n has elapsed<br>
	 * Arguments :<br>
	 *   -# n absolute Time to block a thread<br>
	 * Original signature : <code>void dds_sleepuntil(dds_time_t)</code><br>
	 * <i>native declaration : _dds.h:75</i>
	 */
	public static native void dds_sleepuntil(long n);
	/**
	 * Description : Enable or disable write batching. Overrides default configuration<br>
	 * setting for write batching (DDSI2E/Internal/WriteBatch).<br>
	 * Arguments :<br>
	 *   -# enable Enables or disables write batching for all writers.<br>
	 * Original signature : <code>void dds_write_set_batch(bool)</code><br>
	 * <i>native declaration : _dds.h:136</i>
	 */
	public static native void dds_write_set_batch(byte enable);
	/**
	 * Description : Install tcp/ssl and encryption support. Depends on openssl.<br>
	 * Arguments :<br>
	 *   -# None<br>
	 * Original signature : <code>void dds_ssl_plugin()</code><br>
	 * <i>native declaration : _dds.h:143</i>
	 */
	public static native void dds_ssl_plugin();
	/**
	 * Description : Install client durability support. Depends on OSPL server.<br>
	 * Arguments :<br>
	 *   -# None<br>
	 * Original signature : <code>void dds_durability_plugin()</code><br>
	 * <i>native declaration : _dds.h:150</i>
	 */
	public static native void dds_durability_plugin();
	/**
	 * @brief Returns the default domain identifier.<br>
	 * The default domain identifier can be configured in the configuration file<br>
	 * or be set through an evironment variable ({DDSC_PROJECT_NAME_NOSPACE_CAPS}_DOMAIN).<br>
	 * @returns Default domain identifier<br>
	 * Original signature : <code>dds_domainid_t dds_domain_default()</code><br>
	 * <i>native declaration : _dds.h:172</i>
	 */
	public static native int dds_domain_default();
	/**
	 * @brief Enable entity.<br>
	 * @note Delayed entity enabling is not supported yet (CHAM-96).<br>
	 * This operation enables the dds_entity_t. Created dds_entity_t objects can start in<br>
	 * either an enabled or disabled state. This is controlled by the value of the<br>
	 * entityfactory policy on the corresponding parent entity for the given<br>
	 * entity. Enabled entities are immediately activated at creation time meaning<br>
	 * all their immutable QoS settings can no longer be changed. Disabled Entities are not<br>
	 * yet activated, so it is still possible to change their immutable QoS settings. However,<br>
	 * once activated the immutable QoS settings can no longer be changed.<br>
	 * Creating disabled entities can make sense when the creator of the DDS_Entity<br>
	 * does not yet know which QoS settings to apply, thus allowing another piece of code<br>
	 * to set the QoS later on.<br>
	 * The default setting of DDS_EntityFactoryQosPolicy is such that, by default,<br>
	 * entities are created in an enabled state so that it is not necessary to explicitly call<br>
	 * dds_enable on newly-created entities.<br>
	 * The dds_enable operation produces the same results no matter how<br>
	 * many times it is performed. Calling dds_enable on an already<br>
	 * enabled DDS_Entity returns DDS_RETCODE_OK and has no effect.<br>
	 * If an Entity has not yet been enabled, the only operations that can be invoked<br>
	 * on it are: the ones to set, get or copy the QosPolicy settings, the ones that set<br>
	 * (or get) the Listener, the ones that get the Status and the dds_get_status_changes<br>
	 * operation (although the status of a disabled entity never changes). Other operations<br>
	 * will return the error DDS_RETCODE_NOT_ENABLED.<br>
	 * Entities created with a parent that is disabled, are created disabled regardless of<br>
	 * the setting of the entityfactory policy.<br>
	 * If the entityfactory policy has autoenable_created_entities<br>
	 * set to TRUE, the dds_enable operation on the parent will<br>
	 * automatically enable all child entities created with the parent.<br>
	 * The Listeners associated with an Entity are not called until the<br>
	 * Entity is enabled. Conditions associated with an Entity that<br>
	 * is not enabled are "inactive", that is, have a trigger_value which is FALSE.<br>
	 * @param[in]  entity  The entity to enable.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The listeners of to the entity have been successfully been copied<br>
	 *             into the specified listener parameter.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The parent of the given Entity is not enabled.<br>
	 * Original signature : <code>dds_return_t dds_enable(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:267</i>
	 */
	public static native int dds_enable(int entity);
	/**
	 * TODO: Link to generic dds entity relations documentation.<br>
	 * Original signature : <code>dds_return_t dds_delete(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:272</i>
	 */
	public static native int dds_delete(int entity);
	/**
	 * TODO: Link to generic dds entity relations documentation.<br>
	 * Original signature : <code>dds_entity_t dds_get_publisher(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:277</i>
	 */
	public static native int dds_get_publisher(int writer);
	/**
	 * TODO: Link to generic dds entity relations documentation.<br>
	 * Original signature : <code>dds_entity_t dds_get_subscriber(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:282</i>
	 */
	public static native int dds_get_subscriber(int entity);
	/**
	 * TODO: Link to generic dds entity relations documentation.<br>
	 * Original signature : <code>dds_entity_t dds_get_datareader(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:287</i>
	 */
	public static native int dds_get_datareader(int condition);
	/**
	 * @brief Get the mask of a condition.<br>
	 * This operation returns the mask that was used to create the given<br>
	 * condition.<br>
	 * @param[in]  condition  Read or Query condition that has a mask.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Success (given mask is set).<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The mask arg is NULL.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_mask(dds_entity_t, uint32_t*)</code><br>
	 * <i>native declaration : _dds.h:306</i>
	 */
	public static native int dds_get_mask(int condition, IntBuffer mask);
	/**
	 * TODO: Check list of return codes is complete.<br>
	 * Original signature : <code>dds_return_t dds_get_instance_handle(dds_entity_t, dds_instance_handle_t*)</code><br>
	 * <i>native declaration : _dds.h:311</i>
	 */
	public static native int dds_get_instance_handle(int entity, LongBuffer ihdl);
	/**
	 * @brief Read the status set for the entity<br>
	 * This operation reads the status(es) set for the entity based on<br>
	 * the enabled status and mask set. It does not clear the read status(es).<br>
	 * @param[in]  entity  Entity on which the status has to be read.<br>
	 * @param[out] status  Returns the status set on the entity, based on the enabled status.<br>
	 * @param[in]  mask    Filter the status condition to be read (can be NULL).<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Success.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The entity parameter is not a valid parameter.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_read_status(dds_entity_t, uint32_t*, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:330</i>
	 */
	public static native int dds_read_status(int entity, IntBuffer status, int mask);
	/**
	 * @brief Read the status set for the entity<br>
	 * This operation reads the status(es) set for the entity based on the enabled<br>
	 * status and mask set. It clears the status set after reading.<br>
	 * @param[in]  entity  Entity on which the status has to be read.<br>
	 * @param[out] status  Returns the status set on the entity, based on the enabled status.<br>
	 * @param[in]  mask    Filter the status condition to be read (can be NULL).<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Success.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The entity parameter is not a valid parameter.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_take_status(dds_entity_t, uint32_t*, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:349</i>
	 */
	public static native int dds_take_status(int entity, IntBuffer status, int mask);
	/**
	 * @brief Get changed status(es)<br>
	 * This operation returns the status changes since they were last read.<br>
	 * @param[in]  entity  Entity on which the statuses are read.<br>
	 * @param[out] status  Returns the current set of triggered statuses.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Success.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The entity parameter is not a valid parameter.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_status_changes(dds_entity_t, uint32_t*)</code><br>
	 * <i>native declaration : _dds.h:366</i>
	 */
	public static native int dds_get_status_changes(int entity, IntBuffer status);
	/**
	 * @brief Get enabled status on entity<br>
	 * This operation returns the status enabled on the entity<br>
	 * @param[in]  entity  Entity to get the status.<br>
	 * @param[out] status  Status set on the entity.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Success.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The entity parameter is not a valid parameter.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_enabled_status(dds_entity_t, uint32_t*)</code><br>
	 * <i>native declaration : _dds.h:383</i>
	 */
	public static native int dds_get_enabled_status(int entity, IntBuffer status);
	/**
	 * @brief Set status enabled on entity<br>
	 * This operation enables the status(es) based on the mask set<br>
	 * @param[in]  entity  Entity to enable the status.<br>
	 * @param[in]  mask    Status value that indicates the status to be enabled.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Success.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The entity parameter is not a valid parameter.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_set_enabled_status(dds_entity_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:400</i>
	 */
	public static native int dds_set_enabled_status(int entity, int mask);
	/**
	 * TODO: Link to generic QoS information documentation.<br>
	 * Original signature : <code>dds_return_t dds_get_qos(dds_entity_t, dds_qos_t*)</code><br>
	 * <i>native declaration : _dds.h:405</i>
	 */
	public static native int dds_get_qos(int entity, DdscLibrary.dds_qos_t qos);
	/**
	 * TODO: Link to generic QoS information documentation.<br>
	 * Original signature : <code>dds_return_t dds_set_qos(dds_entity_t, const dds_qos_t*)</code><br>
	 * <i>native declaration : _dds.h:410</i>
	 */
	public static native int dds_set_qos(int entity, DdscLibrary.dds_qos_t qos);
	/**
	 * TODO: Link to (generic) Listener and status information.<br>
	 * Original signature : <code>dds_return_t dds_get_listener(dds_entity_t, dds_listener_t*)</code><br>
	 * <i>native declaration : _dds.h:415</i>
	 */
	public static native int dds_get_listener(int entity, DdscLibrary.dds_listener_t listener);
	/**
	 * TODO: Link to (generic) Listener and status information.<br>
	 * Original signature : <code>dds_return_t dds_set_listener(dds_entity_t, const dds_listener_t*)</code><br>
	 * <i>native declaration : _dds.h:420</i>
	 */
	public static native int dds_set_listener(int entity, DdscLibrary.dds_listener_t listener);
	/**
	 * @brief Creates a new instance of a DDS participant in a domain<br>
	 * If domain is set (not DDS_DOMAIN_DEFAULT) then it must match if the domain has also<br>
	 * been configured or an error status will be returned.<br>
	 * Currently only a single domain can be configured by providing configuration file.<br>
	 * If no configuration file exists, the default domain is configured as 0.<br>
	 * *<br>
	 * @param[in]  domain The domain in which to create the participant (can be DDS_DOMAIN_DEFAULT). Valid values for domain id are between 0 and 230. DDS_DOMAIN_DEFAULT is for using the domain in the configuration.<br>
	 * @param[in]  qos The QoS to set on the new participant (can be NULL).<br>
	 * @param[in]  listener Any listener functions associated with the new participant (can be NULL).<br>
	 * @returns A valid participant handle or an error code.<br>
	 * @retval >0<br>
	 *             A valid participant handle.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * Original signature : <code>dds_entity_t dds_create_participant(const dds_domainid_t, const dds_qos_t*, const dds_listener_t*)</code><br>
	 * <i>native declaration : _dds.h:438</i>
	 */
	public static native int dds_create_participant(int domain, DdscLibrary.dds_qos_t qos, DdscLibrary.dds_listener_t listener);
	/**
	 * TODO: Link to generic dds entity relations documentation.<br>
	 * Original signature : <code>dds_entity_t dds_get_parent(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:443</i>
	 */
	public static native int dds_get_parent(int entity);
	/**
	 * @brief Get entity participant.<br>
	 * This operation returns the participant to which the given entity belongs.<br>
	 * For instance, it will return the Participant that was used when<br>
	 * creating a Publisher that was used to create a DataWriter (when that<br>
	 * DataWriter was provided here).<br>
	 * TODO: Link to generic dds entity relations documentation.<br>
	 * @param[in]  entity  Entity from which to get its participant.<br>
	 * @returns A valid entity or an error code.<br>
	 * @retval >0<br>
	 *             A valid participant handle.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_entity_t dds_get_participant(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:463</i>
	 */
	public static native int dds_get_participant(int entity);
	/**
	 * TODO: Link to generic dds entity relations documentation.<br>
	 * Original signature : <code>dds_return_t dds_get_children(dds_entity_t, dds_entity_t*, size_t)</code><br>
	 * <i>native declaration : _dds.h:468</i>
	 */
	public static native int dds_get_children(int entity, IntBuffer children, NativeSize size);
	/**
	 * @brief Get the domain id to which this entity is attached.<br>
	 * When creating a participant entity, it is attached to a certain domain.<br>
	 * All the children (like Publishers) and childrens' children (like<br>
	 * DataReaders), etc are also attached to that domain.<br>
	 * This function will return the original domain ID when called on<br>
	 * any of the entities within that hierarchy.<br>
	 * @param[in]  entity   Entity from which to get its children.<br>
	 * @param[out] id       Pointer to put the domain ID in.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Domain ID was returned.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The id parameter is NULL.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_domainid(dds_entity_t, dds_domainid_t*)</code><br>
	 * <i>native declaration : _dds.h:491</i>
	 */
	public static native int dds_get_domainid(int entity, IntBuffer id);
	/**
	 * @brief Get participants of a domain.<br>
	 * This operation acquires the participants created on a domain and returns<br>
	 * the number of found participants.<br>
	 * This function takes a domain id with the size of pre-allocated participant's<br>
	 * list in and will return the number of found participants. It is possible that<br>
	 * the given size of the list is not the same as the number of found participants.<br>
	 * If less participants are found, then the last few entries in an array stay<br>
	 * untouched. If more participants are found and the array is too small, then the<br>
	 * participants returned are undefined.<br>
	 * @param[in]  domain_id    The domain id.<br>
	 * @param[out] participants The participant for domain.<br>
	 * @param[in]  size         Size of the pre-allocated participant's list.<br>
	 * @returns Number of participants found or and error code.<br>
	 * @retval >0<br>
	 *             Number of participants found.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The participant parameter is NULL, while a size is provided.<br>
	 * Original signature : <code>dds_return_t dds_lookup_participant(dds_domainid_t, dds_entity_t*, size_t)</code><br>
	 * <i>native declaration : _dds.h:514</i>
	 */
	public static native int dds_lookup_participant(int domain_id, IntBuffer participants, NativeSize size);
	/**
	 * TODO: Check list of retcodes is complete.<br>
	 * Original signature : <code>dds_entity_t dds_create_topic(dds_entity_t, const dds_topic_descriptor_t*, const char*, const dds_qos_t*, const dds_listener_t*)</code><br>
	 * <i>native declaration : _dds.h:519</i>
	 */
	public static native int dds_create_topic(int participant, dds_topic_descriptor descriptor, String name, DdscLibrary.dds_qos_t qos, DdscLibrary.dds_listener_t listener);
	/**
	 * TODO: Check list of retcodes is complete.<br>
	 * Original signature : <code>dds_entity_t dds_find_topic(dds_entity_t, const char*)</code><br>
	 * <i>native declaration : _dds.h:524</i>
	 */
	public static native int dds_find_topic(int participant, String name);
	/**
	 * TODO: Check annotation. Could be writes_to_(size, return + 1) as well.<br>
	 * Original signature : <code>dds_return_t dds_get_name(dds_entity_t, char*, size_t)</code><br>
	 * <i>native declaration : _dds.h:529</i>
	 */
	public static native int dds_get_name(int topic, String name, NativeSize size);
	/**
	 * @brief Returns the type name of a given topic.<br>
	 * @param[in]  topic  The topic.<br>
	 * @param[out] name   Buffer to write the topic type name to.<br>
	 * @param[in]  size   Number of bytes available in the buffer.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @return DDS_RETCODE_OK<br>
	 *             Success.<br>
	 * Original signature : <code>dds_return_t dds_get_type_name(dds_entity_t, char*, size_t)</code><br>
	 * <i>native declaration : _dds.h:540</i>
	 */
	public static native int dds_get_type_name(int topic, String name, NativeSize size);
	/**
	 * @brief Sets a filter on a topic.<br>
	 * @param[in]  topic   The topic on which the content filter is set.<br>
	 * @param[in]  filter  The filter function used to filter topic samples.<br>
	 * Original signature : <code>void dds_topic_set_filter(dds_entity_t, dds_topic_filter_fn)</code><br>
	 * <i>native declaration : _dds.h:549</i>
	 */
	public static native void dds_topic_set_filter(int topic, DdscLibrary.dds_topic_filter_fn filter);
	/**
	 * @brief Gets the filter for a topic.<br>
	 * @param[in]  topic  The topic from which to get the filter.<br>
	 * @returns The topic filter.<br>
	 * Original signature : <code>dds_topic_filter_fn dds_topic_get_filter(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:556</i>
	 */
	public static native DdscLibrary.dds_topic_filter_fn dds_topic_get_filter(int topic);
	/**
	 * @brief Creates a new instance of a DDS subscriber<br>
	 * @param[in]  participant The participant on which the subscriber is being created.<br>
	 * @param[in]  qos         The QoS to set on the new subscriber (can be NULL).<br>
	 * @param[in]  listener    Any listener functions associated with the new subscriber (can be NULL).<br>
	 * @returns A valid subscriber handle or an error code.<br>
	 * @retval >0<br>
	 *             A valid subscriber handle.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the parameters is invalid.<br>
	 * Original signature : <code>dds_entity_t dds_create_subscriber(dds_entity_t, const dds_qos_t*, const dds_listener_t*)</code><br>
	 * <i>native declaration : _dds.h:571</i>
	 */
	public static native int dds_create_subscriber(int participant, DdscLibrary.dds_qos_t qos, DdscLibrary.dds_listener_t listener);
	/**
	 * TODO: Check list of error codes is complete.<br>
	 * Original signature : <code>dds_entity_t dds_create_publisher(dds_entity_t, const dds_qos_t*, const dds_listener_t*)</code><br>
	 * <i>native declaration : _dds.h:576</i>
	 */
	public static native int dds_create_publisher(int participant, DdscLibrary.dds_qos_t qos, DdscLibrary.dds_listener_t listener);
	/**
	 * @brief Suspends the publications of the Publisher<br>
	 * This operation is a hint to the Service so it can optimize its performance by e.g., collecting<br>
	 * modifications to DDS writers and then batching them. The Service is not required to use the hint.<br>
	 * Every invocation of this operation must be matched by a corresponding call to @see dds_resume<br>
	 * indicating that the set of modifications has completed.<br>
	 * @param[in]  publisher The publisher for which all publications will be suspended.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Publications suspended successfully.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The pub parameter is not a valid publisher.<br>
	 * @retval DDS_RETCODE_UNSUPPORTED<br>
	 *             Operation is not supported.<br>
	 * Original signature : <code>dds_return_t dds_suspend(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:593</i>
	 */
	public static native int dds_suspend(int publisher);
	/**
	 * @brief Resumes the publications of the Publisher<br>
	 * This operation is a hint to the Service to indicate that the application has<br>
	 * completed changes initiated by a previous dds_suspend(). The Service is not<br>
	 * required to use the hint.<br>
	 * The call to resume_publications must match a previous call to @see suspend_publications.<br>
	 * @param[in]  publisher The publisher for which all publications will be resumed.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Publications resumed successfully.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The pub parameter is not a valid publisher.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             No previous matching dds_suspend().<br>
	 * @retval DDS_RETCODE_UNSUPPORTED<br>
	 *             Operation is not supported.<br>
	 * Original signature : <code>dds_return_t dds_resume(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:612</i>
	 */
	public static native int dds_resume(int publisher);
	/**
	 * @brief Waits at most for the duration timeout for acks for data in the publisher or writer.<br>
	 * This operation blocks the calling thread until either all data written by the publisher<br>
	 * or writer is acknowledged by all matched reliable reader entities, or else the duration<br>
	 * specified by the timeout parameter elapses, whichever happens first.<br>
	 * @param[in]  publisher_or_writer Publisher or writer whose acknowledgments must be waited for<br>
	 * @param[in]  timeout             How long to wait for acknowledgments before time out<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             All acknowledgments successfully received with the timeout.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The publisher_or_writer is not a valid publisher or writer.<br>
	 * @retval DDS_RETCODE_TIMEOUT<br>
	 *             Timeout expired before all acknowledgments from reliable reader entities were received.<br>
	 * @retval DDS_RETCODE_UNSUPPORTED<br>
	 *             Operation is not supported.<br>
	 * Original signature : <code>dds_return_t dds_wait_for_acks(dds_entity_t, dds_duration_t)</code><br>
	 * <i>native declaration : _dds.h:631</i>
	 */
	public static native int dds_wait_for_acks(int publisher_or_writer, long timeout);
	/**
	 * TODO: Complete list of error codes<br>
	 * Original signature : <code>dds_entity_t dds_create_reader(dds_entity_t, dds_entity_t, const dds_qos_t*, const dds_listener_t*)</code><br>
	 * <i>native declaration : _dds.h:636</i>
	 */
	public static native int dds_create_reader(int participant_or_subscriber, int topic, DdscLibrary.dds_qos_t qos, DdscLibrary.dds_listener_t listener);
	/**
	 * TODO: Complete list of error codes<br>
	 * Original signature : <code>int dds_reader_wait_for_historical_data(dds_entity_t, dds_duration_t)</code><br>
	 * <i>native declaration : _dds.h:641</i>
	 */
	public static native int dds_reader_wait_for_historical_data(int reader, long max_wait);
	/**
	 * TODO: Complete list of error codes<br>
	 * Original signature : <code>dds_entity_t dds_create_writer(dds_entity_t, dds_entity_t, const dds_qos_t*, const dds_listener_t*)</code><br>
	 * <i>native declaration : _dds.h:646</i>
	 */
	public static native int dds_create_writer(int participant_or_publisher, int topic, DdscLibrary.dds_qos_t qos, DdscLibrary.dds_listener_t listener);
	/**
	 * @brief Registers an instance<br>
	 * This operation registers an instance with a key value to the data writer and<br>
	 * returns an instance handle that could be used for successive write & dispose<br>
	 * operations. When the handle is not allocated, the function will return and<br>
	 * error and the handle will be un-touched.<br>
	 * @param[in]  writer  The writer to which instance has be associated.<br>
	 * @param[out] handle  The instance handle.<br>
	 * @param[in]  data    The instance with the key value.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *            The operation was successful.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *            One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *            The operation is invoked on an inappropriate object.<br>
	 * Original signature : <code>dds_return_t dds_register_instance(dds_entity_t, dds_instance_handle_t*, const void*)</code><br>
	 * <i>native declaration : _dds.h:665</i>
	 */
	public static native int dds_register_instance(int writer, LongBuffer handle, Pointer data);
	/**
	 * @brief Unregisters an instance<br>
	 * This operation reverses the action of register instance, removes all information regarding<br>
	 * the instance and unregisters an instance with a key value from the data writer.<br>
	 * @param[in]  writer  The writer to which instance is associated.<br>
	 * @param[in]  data    The instance with the key value.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The operation was successful.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * Original signature : <code>dds_return_t dds_unregister_instance(dds_entity_t, const void*)</code><br>
	 * <i>native declaration : _dds.h:681</i>
	 */
	public static native int dds_unregister_instance(int writer, Pointer data);
	/**
	 * @brief Unregisters an instance<br>
	 * *This operation unregisters the instance which is identified by the key fields of the given<br>
	 * typed instance handle.<br>
	 * @param[in]  writer  The writer to which instance is associated.<br>
	 * @param[in]  handle  The instance handle.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The operation was successful.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * Original signature : <code>dds_return_t dds_unregister_instance_ih(dds_entity_t, dds_instance_handle_t)</code><br>
	 * <i>native declaration : _dds.h:697</i>
	 */
	public static native int dds_unregister_instance_ih(int writer, long handle);
	/**
	 * @brief Unregisters an instance<br>
	 * This operation reverses the action of register instance, removes all information regarding<br>
	 * the instance and unregisters an instance with a key value from the data writer. It also<br>
	 * provides a value for the timestamp explicitly.<br>
	 * @param[in]  writer    The writer to which instance is associated.<br>
	 * @param[in]  data      The instance with the key value.<br>
	 * @param[in]  timestamp The timestamp used at registration.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The operation was successful.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * Original signature : <code>dds_return_t dds_unregister_instance_ts(dds_entity_t, const void*, dds_time_t)</code><br>
	 * <i>native declaration : _dds.h:715</i>
	 */
	public static native int dds_unregister_instance_ts(int writer, Pointer data, long timestamp);
	/**
	 * @brief Unregisters an instance<br>
	 * This operation unregisters an instance with a key value from the handle. Instance can be identified<br>
	 * from instance handle. If an unregistered key ID is passed as an instance data, an error is logged and<br>
	 * not flagged as return value.<br>
	 * @param[in]  writer    The writer to which instance is associated.<br>
	 * @param[in]  handle    The instance handle.<br>
	 * @param[in]  timestamp The timestamp used at registration.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The operation was successful<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object<br>
	 * Original signature : <code>dds_return_t dds_unregister_instance_ih_ts(dds_entity_t, dds_instance_handle_t, dds_time_t)</code><br>
	 * <i>native declaration : _dds.h:733</i>
	 */
	public static native int dds_unregister_instance_ih_ts(int writer, long handle, long timestamp);
	/**
	 * @brief This operation modifies and disposes a data instance.<br>
	 * This operation requests the Data Distribution Service to modify the instance and<br>
	 * mark it for deletion. Copies of the instance and its corresponding samples, which are<br>
	 * stored in every connected reader and, dependent on the QoS policy settings (also in<br>
	 * the Transient and Persistent stores) will be modified and marked for deletion by<br>
	 * setting their dds_instance_state_t to DDS_IST_NOT_ALIVE_DISPOSED.<br>
	 * <b><i>Blocking</i></b><br>
	 * If the history QoS policy is set to DDS_HISTORY_KEEP_ALL, the<br>
	 * dds_writedispose operation on the writer may block if the modification<br>
	 * would cause data to be lost because one of the limits, specified in the<br>
	 * resource_limits QoS policy, to be exceeded. In case the synchronous<br>
	 * attribute value of the reliability Qos policy is set to true for<br>
	 * communicating writers and readers then the writer will wait until<br>
	 * all synchronous readers have acknowledged the data. Under these<br>
	 * circumstances, the max_blocking_time attribute of the reliability<br>
	 * QoS policy configures the maximum time the dds_writedispose operation<br>
	 * may block.<br>
	 * If max_blocking_time elapses before the writer is able to store the<br>
	 * modification without exceeding the limits and all expected acknowledgements<br>
	 * are received, the dds_writedispose operation will fail and returns<br>
	 * DDS_RETCODE_TIMEOUT.<br>
	 * @param[in]  writer The writer to dispose the data instance from.<br>
	 * @param[in]  data   The data to be written and disposed.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The sample is written and the instance is marked for deletion.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             At least one of the arguments is invalid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_TIMEOUT<br>
	 *             Either the current action overflowed the available resources as<br>
	 *             specified by the combination of the reliability QoS policy,<br>
	 *             history QoS policy and resource_limits QoS policy, or the<br>
	 *             current action was waiting for data delivery acknowledgement<br>
	 *             by synchronous readers. This caused blocking of this operation,<br>
	 *             which could not be resolved before max_blocking_time of the<br>
	 *             reliability QoS policy elapsed.<br>
	 * Original signature : <code>dds_return_t dds_writedispose(dds_entity_t, const void*)</code><br>
	 * <i>native declaration : _dds.h:779</i>
	 */
	public static native int dds_writedispose(int writer, Pointer data);
	/**
	 * @brief This operation modifies and disposes a data instance with a specific<br>
	 *        timestamp.<br>
	 * This operation performs the same functions as dds_writedispose except that<br>
	 * the application provides the value for the source_timestamp that is made<br>
	 * available to connected reader objects. This timestamp is important for the<br>
	 * interpretation of the destination_order QoS policy.<br>
	 * @param[in]  writer    The writer to dispose the data instance from.<br>
	 * @param[in]  data      The data to be written and disposed.<br>
	 * @param[in]  timestamp The timestamp used as source timestamp.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The sample is written and the instance is marked for deletion.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             At least one of the arguments is invalid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_TIMEOUT<br>
	 *             Either the current action overflowed the available resources as<br>
	 *             specified by the combination of the reliability QoS policy,<br>
	 *             history QoS policy and resource_limits QoS policy, or the<br>
	 *             current action was waiting for data delivery acknowledgement<br>
	 *             by synchronous readers. This caused blocking of this operation,<br>
	 *             which could not be resolved before max_blocking_time of the<br>
	 *             reliability QoS policy elapsed.<br>
	 * Original signature : <code>dds_return_t dds_writedispose_ts(dds_entity_t, const void*, dds_time_t)</code><br>
	 * <i>native declaration : _dds.h:811</i>
	 */
	public static native int dds_writedispose_ts(int writer, Pointer data, long timestamp);
	/**
	 * @brief This operation disposes an instance, identified by the data sample.<br>
	 * This operation requests the Data Distribution Service to modify the instance and<br>
	 * mark it for deletion. Copies of the instance and its corresponding samples, which are<br>
	 * stored in every connected reader and, dependent on the QoS policy settings (also in<br>
	 * the Transient and Persistent stores) will be modified and marked for deletion by<br>
	 * setting their dds_instance_state_t to DDS_IST_NOT_ALIVE_DISPOSED.<br>
	 * <b><i>Blocking</i></b><br>
	 * If the history QoS policy is set to DDS_HISTORY_KEEP_ALL, the<br>
	 * dds_writedispose operation on the writer may block if the modification<br>
	 * would cause data to be lost because one of the limits, specified in the<br>
	 * resource_limits QoS policy, to be exceeded. In case the synchronous<br>
	 * attribute value of the reliability Qos policy is set to true for<br>
	 * communicating writers and readers then the writer will wait until<br>
	 * all synchronous readers have acknowledged the data. Under these<br>
	 * circumstances, the max_blocking_time attribute of the reliability<br>
	 * QoS policy configures the maximum time the dds_writedispose operation<br>
	 * may block.<br>
	 * If max_blocking_time elapses before the writer is able to store the<br>
	 * modification without exceeding the limits and all expected acknowledgements<br>
	 * are received, the dds_writedispose operation will fail and returns<br>
	 * DDS_RETCODE_TIMEOUT.<br>
	 * @param[in]  writer The writer to dispose the data instance from.<br>
	 * @param[in]  data   The data sample that identifies the instance<br>
	 *                    to be disposed.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The sample is written and the instance is marked for deletion.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             At least one of the arguments is invalid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_TIMEOUT<br>
	 *             Either the current action overflowed the available resources as<br>
	 *             specified by the combination of the reliability QoS policy,<br>
	 *             history QoS policy and resource_limits QoS policy, or the<br>
	 *             current action was waiting for data delivery acknowledgement<br>
	 *             by synchronous readers. This caused blocking of this operation,<br>
	 *             which could not be resolved before max_blocking_time of the<br>
	 *             reliability QoS policy elapsed.<br>
	 * Original signature : <code>dds_return_t dds_dispose(dds_entity_t, const void*)</code><br>
	 * <i>native declaration : _dds.h:858</i>
	 */
	public static native int dds_dispose(int writer, Pointer data);
	/**
	 * @brief This operation disposes an instance with a specific timestamp, identified by the data sample.<br>
	 * This operation performs the same functions as dds_dispose except that<br>
	 * the application provides the value for the source_timestamp that is made<br>
	 * available to connected reader objects. This timestamp is important for the<br>
	 * interpretation of the destination_order QoS policy.<br>
	 * @param[in]  writer    The writer to dispose the data instance from.<br>
	 * @param[in]  data      The data sample that identifies the instance<br>
	 *                       to be disposed.<br>
	 * @param[in]  timestamp The timestamp used as source timestamp.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The sample is written and the instance is marked for deletion<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             At least one of the arguments is invalid<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted<br>
	 * @retval DDS_RETCODE_TIMEOUT<br>
	 *             Either the current action overflowed the available resources as<br>
	 *             specified by the combination of the reliability QoS policy,<br>
	 *             history QoS policy and resource_limits QoS policy, or the<br>
	 *             current action was waiting for data delivery acknowledgment<br>
	 *             by synchronous readers. This caused blocking of this operation,<br>
	 *             which could not be resolved before max_blocking_time of the<br>
	 *             reliability QoS policy elapsed.<br>
	 * Original signature : <code>dds_return_t dds_dispose_ts(dds_entity_t, const void*, dds_time_t)</code><br>
	 * <i>native declaration : _dds.h:890</i>
	 */
	public static native int dds_dispose_ts(int writer, Pointer data, long timestamp);
	/**
	 * @brief This operation disposes an instance, identified by the instance handle.<br>
	 * This operation requests the Data Distribution Service to modify the instance and<br>
	 * mark it for deletion. Copies of the instance and its corresponding samples, which are<br>
	 * stored in every connected reader and, dependent on the QoS policy settings (also in<br>
	 * the Transient and Persistent stores) will be modified and marked for deletion by<br>
	 * setting their dds_instance_state_t to DDS_IST_NOT_ALIVE_DISPOSED.<br>
	 * <b><i>Instance Handle</i></b><br>
	 * The given instance handle must correspond to the value that was returned by either<br>
	 * the dds_register_instance operation, dds_register_instance_ts or dds_instance_lookup.<br>
	 * If there is no correspondence, then the result of the operation is unspecified.<br>
	 * @param[in]  writer The writer to dispose the data instance from.<br>
	 * @param[in]  handle The handle to identify an instance.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The sample is written and the instance is marked for deletion.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             At least one of the arguments is invalid<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The instance handle has not been registered with this writer<br>
	 * Original signature : <code>dds_return_t dds_dispose_ih(dds_entity_t, dds_instance_handle_t)</code><br>
	 * <i>native declaration : _dds.h:919</i>
	 */
	public static native int dds_dispose_ih(int writer, long handle);
	/**
	 * @brief This operation disposes an instance with a specific timestamp, identified by the instance handle.<br>
	 * This operation performs the same functions as dds_dispose_ih except that<br>
	 * the application provides the value for the source_timestamp that is made<br>
	 * available to connected reader objects. This timestamp is important for the<br>
	 * interpretation of the destination_order QoS policy.<br>
	 * @param[in]  writer    The writer to dispose the data instance from.<br>
	 * @param[in]  handle    The handle to identify an instance.<br>
	 * @param[in]  timestamp The timestamp used as source timestamp.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The sample is written and the instance is marked for deletion.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             At least one of the arguments is invalid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The instance handle has not been registered with this writer.<br>
	 * Original signature : <code>dds_return_t dds_dispose_ih_ts(dds_entity_t, dds_instance_handle_t, dds_time_t)</code><br>
	 * <i>native declaration : _dds.h:944</i>
	 */
	public static native int dds_dispose_ih_ts(int writer, long handle, long timestamp);
	/**
	 * @brief Write the value of a data instance<br>
	 * With this API, the value of the source timestamp is automatically made<br>
	 * available to the data reader by the service.<br>
	 * @param[in]  writer The writer entity.<br>
	 * @param[in]  data Value to be written.<br>
	 * @returns dds_return_t indicating success or failure.<br>
	 * Original signature : <code>dds_return_t dds_write(dds_entity_t, const void*)</code><br>
	 * <i>native declaration : _dds.h:954</i>
	 */
	public static native int dds_write(int writer, Pointer data);
	/**
	 * TODO: What is it for and is it really needed?<br>
	 * Original signature : <code>void dds_write_flush(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:959</i>
	 */
	public static native void dds_write_flush(int writer);
	/**
	 * @brief Write a CDR serialized value of a data instance<br>
	 * @param[in]  writer The writer entity.<br>
	 * @param[in]  cdr CDR serialized value to be written.<br>
	 * @param[in]  size Size (in bytes) of CDR encoded data to be written.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * Original signature : <code>int dds_writecdr(dds_entity_t, serdata*)</code><br>
	 * <i>native declaration : _dds.h:968</i>
	 */
	public static native int dds_writecdr(int writer, DdscLibrary.serdata serdata);
	/**
	 * @brief Write the value of a data instance along with the source timestamp passed.<br>
	 * @param[in]  writer The writer entity.<br>
	 * @param[in]  data Value to be written.<br>
	 * @param[in]  timestamp Source timestamp.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * Original signature : <code>dds_return_t dds_write_ts(dds_entity_t, const void*, dds_time_t)</code><br>
	 * <i>native declaration : _dds.h:977</i>
	 */
	public static native int dds_write_ts(int writer, Pointer data, long timestamp);
	/**
	 * @brief Creates a readcondition associated to the given reader.<br>
	 * The readcondition allows specifying which samples are of interest in<br>
	 * a data reader's history, by means of a mask. The mask is or'd with<br>
	 * the flags that are dds_sample_state_t, dds_view_state_t and<br>
	 * dds_instance_state_t.<br>
	 * Based on the mask value set, the readcondition gets triggered when<br>
	 * data is available on the reader.<br>
	 * Waitsets allow waiting for an event on some of any set of entities.<br>
	 * This means that the readcondition can be used to wake up a waitset when<br>
	 * data is in the reader history with states that matches the given mask.<br>
	 * @note The parent reader and every of its associated conditions (whether<br>
	 *       they are readconditions or queryconditions) share the same resources.<br>
	 *       This means that one of these entities reads or takes data, the states<br>
	 *       of the data will change for other entities automatically. For instance,<br>
	 *       if one reads a sample, then the sample state will become 'read' for all<br>
	 *       associated reader/conditions. Or if one takes a sample, then it's not<br>
	 *       available to any other associated reader/condition.<br>
	 * @param[in]  reader  Reader to associate the condition to.<br>
	 * @param[in]  mask    Interest (dds_sample_state_t|dds_view_state_t|dds_instance_state_t).<br>
	 * @returns A valid condition handle or an error code.<br>
	 * @retval >0<br>
	 *             A valid condition handle<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_entity_t dds_create_readcondition(dds_entity_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1009</i>
	 */
	public static native int dds_create_readcondition(int reader, int mask);
	/**
	 * TODO: Explain the filter (aka expression & parameters) of the (to be<br>
	 *       implemented) new querycondition implementation.<br>
	 * TODO: Update parameters when new querycondition is introduced.<br>
	 * Original signature : <code>dds_entity_t dds_create_querycondition(dds_entity_t, uint32_t, dds_querycondition_filter_fn)</code><br>
	 * <i>native declaration : _dds.h:1017</i>
	 */
	public static native int dds_create_querycondition(int reader, int mask, DdscLibrary.dds_querycondition_filter_fn filter);
	/**
	 * @brief Creates a guardcondition.<br>
	 * Waitsets allow waiting for an event on some of any set of entities.<br>
	 * This means that the guardcondition can be used to wake up a waitset when<br>
	 * data is in the reader history with states that matches the given mask.<br>
	 * @returns A valid condition handle or an error code.<br>
	 * @retval >0<br>
	 *             A valid condition handle<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_entity_t dds_create_guardcondition(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:1034</i>
	 */
	public static native int dds_create_guardcondition(int participant);
	/**
	 * @brief Sets the trigger status of a guardcondition.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Operation successful<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_set_guardcondition(dds_entity_t, bool)</code><br>
	 * <i>native declaration : _dds.h:1047</i>
	 */
	public static native int dds_set_guardcondition(int guardcond, byte triggered);
	/**
	 * @brief Reads the trigger status of a guardcondition.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Operation successful<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_read_guardcondition(dds_entity_t, bool*)</code><br>
	 * <i>native declaration : _dds.h:1060</i>
	 */
	public static native int dds_read_guardcondition(int guardcond, ByteBuffer triggered);
	/**
	 * @brief Reads and resets the trigger status of a guardcondition.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Operation successful<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_take_guardcondition(dds_entity_t, bool*)</code><br>
	 * <i>native declaration : _dds.h:1073</i>
	 */
	public static native int dds_take_guardcondition(int guardcond, ByteBuffer triggered);
	/**
	 * @brief Create a waitset and allocate the resources required<br>
	 * A WaitSet object allows an application to wait until one or more of the<br>
	 * conditions of the attached entities evaluates to TRUE or until the timeout<br>
	 * expires.<br>
	 * @param[in]  participant  Domain participant which the WaitSet contains.<br>
	 * @returns A valid waitset handle or an error code.<br>
	 * @retval >=0<br>
	 *             A valid waitset handle.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_entity_t dds_create_waitset(dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:1099</i>
	 */
	public static native int dds_create_waitset(int participant);
	/**
	 * @brief Acquire previously attached entities.<br>
	 * This functions takes a pre-allocated list to put the entities in and<br>
	 * will return the number of found entities. It is possible that the given<br>
	 * size of the list is not the same as the number of found entities. If<br>
	 * less entities are found, then the last few entries in the list are<br>
	 * untouched. When more entities are found, then only 'size' number of<br>
	 * entries are inserted into the list, but still the complete count of the<br>
	 * found entities is returned. Which entities are returned in the latter<br>
	 * case is undefined.<br>
	 * @param[in]  waitset  Waitset from which to get its attached entities.<br>
	 * @param[out] entities Pre-allocated array to contain the found entities.<br>
	 * @param[in]  size     Size of the pre-allocated entities' list.<br>
	 * @returns A dds_return_t with the number of children or an error code.<br>
	 * @retval >=0<br>
	 *             Number of children found (can be larger than 'size').<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The entities parameter is NULL, while a size is provided.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The waitset has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_waitset_get_entities(dds_entity_t, dds_entity_t*, size_t)</code><br>
	 * <i>native declaration : _dds.h:1126</i>
	 */
	public static native int dds_waitset_get_entities(int waitset, IntBuffer entities, NativeSize size);
	/**
	 * @brief This operation attaches an Entity to the WaitSet.<br>
	 * This operation attaches an Entity to the WaitSet. The dds_waitset_wait()<br>
	 * will block when none of the attached entities are triggered. 'Triggered'<br>
	 * (dds_triggered()) doesn't mean the same for every entity:<br>
	 *  - Reader/Writer/Publisher/Subscriber/Topic/Participant<br>
	 *      - These are triggered when their status changed.<br>
	 *  - WaitSet<br>
	 *      - Triggered when trigger value was set to true by the application.<br>
	 *        It stays triggered until application sets the trigger value to<br>
	 *        false (dds_waitset_set_trigger()). This can be used to wake up an<br>
	 *        waitset for different reasons (f.i. termination) than the 'normal'<br>
	 *        status change (like new data).<br>
	 *  - ReadCondition/QueryCondition<br>
	 *      - Triggered when data is available on the related Reader that matches<br>
	 *        the Condition.<br>
	 * Multiple entities can be attached to a single waitset. A particular entity<br>
	 * can be attached to multiple waitsets. However, a particular entity can not<br>
	 * be attached to a particular waitset multiple times.<br>
	 * @param[in]  waitset  The waitset to attach the given entity to.<br>
	 * @param[in]  entity   The entity to attach.<br>
	 * @param[in]  x        Blob that will be supplied when the waitset wait is<br>
	 *                      triggerd by the given entity.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Entity attached.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The given waitset or entity are not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The waitset has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The entity was already attached.<br>
	 * Original signature : <code>dds_return_t dds_waitset_attach(dds_entity_t, dds_entity_t, dds_attach_t)</code><br>
	 * <i>native declaration : _dds.h:1165</i>
	 */
	public static native int dds_waitset_attach(int waitset, int entity, IntBuffer x);
	/**
	 * @brief This operation detaches an Entity to the WaitSet.<br>
	 * @param[in]  waitset  The waitset to detach the given entity from.<br>
	 * @param[in]  entity   The entity to detach.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Entity attached.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The given waitset or entity are not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The waitset has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The entity is not attached.<br>
	 * Original signature : <code>dds_return_t dds_waitset_detach(dds_entity_t, dds_entity_t)</code><br>
	 * <i>native declaration : _dds.h:1185</i>
	 */
	public static native int dds_waitset_detach(int waitset, int entity);
	/**
	 * @brief Sets the trigger_value associated with a waitset.<br>
	 * When the waitset is attached to itself and the trigger value is<br>
	 * set to 'true', then the waitset will wake up just like with an<br>
	 * other status change of the attached entities.<br>
	 * This can be used to forcefully wake up a waitset, for instance<br>
	 * when the application wants to shut down. So, when the trigger<br>
	 * value is true, the waitset will wake up or not wait at all.<br>
	 * The trigger value will remain true until the application sets it<br>
	 * false again deliberately.<br>
	 * @param[in]  waitset  The waitset to set the trigger value on.<br>
	 * @param[in]  trigger  The trigger value to set.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             Entity attached.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The given waitset is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The waitset has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_waitset_set_trigger(dds_entity_t, bool)</code><br>
	 * <i>native declaration : _dds.h:1211</i>
	 */
	public static native int dds_waitset_set_trigger(int waitset, byte trigger);
	/**
	 * @brief This operation allows an application thread to wait for the a status<br>
	 *        change or other trigger on (one of) the entities that are attached to<br>
	 *        the WaitSet.<br>
	 * The "dds_waitset_wait" operation blocks until the some of the attached<br>
	 * entities have triggered or "reltimeout" has elapsed.<br>
	 * 'Triggered' (dds_triggered()) doesn't mean the same for every entity:<br>
	 *  - Reader/Writer/Publisher/Subscriber/Topic/Participant<br>
	 *      - These are triggered when their status changed.<br>
	 *  - WaitSet<br>
	 *      - Triggered when trigger value was set to true by the application.<br>
	 *        It stays triggered until application sets the trigger value to<br>
	 *        false (dds_waitset_set_trigger()). This can be used to wake up an<br>
	 *        waitset for different reasons (f.i. termination) than the 'normal'<br>
	 *        status change (like new data).<br>
	 *  - ReadCondition/QueryCondition<br>
	 *      - Triggered when data is available on the related Reader that matches<br>
	 *        the Condition.<br>
	 * This functions takes a pre-allocated list to put the "xs" blobs in (that<br>
	 * were provided during the attach of the related entities) and will return<br>
	 * the number of triggered entities. It is possible that the given size<br>
	 * of the list is not the same as the number of triggered entities. If less<br>
	 * entities were triggered, then the last few entries in the list are<br>
	 * untouched. When more entities are triggered, then only 'size' number of<br>
	 * entries are inserted into the list, but still the complete count of the<br>
	 * triggered entities is returned. Which "xs" blobs are returned in the<br>
	 * latter case is undefined.<br>
	 * In case of a time out, the return value is 0.<br>
	 * Deleting the waitset while the application is blocked results in an<br>
	 * error code (i.e. < 0) returned by "wait".<br>
	 * Multiple threads may block on a single waitset at the same time;<br>
	 * the calls are entirely independent.<br>
	 * An empty waitset never triggers (i.e., dds_waitset_wait on an empty<br>
	 * waitset is essentially equivalent to a sleep).<br>
	 * The "dds_waitset_wait_until" operation is the same as the<br>
	 * "dds_waitset_wait" except that it takes an absolute timeout.<br>
	 * @param[in]  waitset    The waitset to set the trigger value on.<br>
	 * @param[out] xs         Pre-allocated list to store the 'blobs' that were<br>
	 *                        provided during the attach of the triggered entities.<br>
	 * @param[in]  nxs        The size of the pre-allocated blobs list.<br>
	 * @param[in]  reltimeout Relative timeout<br>
	 * @returns A dds_return_t with the number of entities triggered or an error code<br>
	 * @retval >0<br>
	 *             Number of entities triggered.<br>
	 * @retval  0<br>
	 *             Time out (no entities were triggered).<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The given waitset is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The waitset has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_waitset_wait(dds_entity_t, dds_attach_t*, size_t, dds_duration_t)</code><br>
	 * <i>native declaration : _dds.h:1268</i>
	 */
	public static native int dds_waitset_wait(int waitset, PointerByReference xs, NativeSize nxs, long reltimeout);
	/**
	 * @brief This operation allows an application thread to wait for the a status<br>
	 *        change or other trigger on (one of) the entities that are attached to<br>
	 *        the WaitSet.<br>
	 * The "dds_waitset_wait" operation blocks until the some of the attached<br>
	 * entities have triggered or "abstimeout" has been reached.<br>
	 * 'Triggered' (dds_triggered()) doesn't mean the same for every entity:<br>
	 *  - Reader/Writer/Publisher/Subscriber/Topic/Participant<br>
	 *      - These are triggered when their status changed.<br>
	 *  - WaitSet<br>
	 *      - Triggered when trigger value was set to true by the application.<br>
	 *        It stays triggered until application sets the trigger value to<br>
	 *        false (dds_waitset_set_trigger()). This can be used to wake up an<br>
	 *        waitset for different reasons (f.i. termination) than the 'normal'<br>
	 *        status change (like new data).<br>
	 *  - ReadCondition/QueryCondition<br>
	 *      - Triggered when data is available on the related Reader that matches<br>
	 *        the Condition.<br>
	 * This functions takes a pre-allocated list to put the "xs" blobs in (that<br>
	 * were provided during the attach of the related entities) and will return<br>
	 * the number of triggered entities. It is possible that the given size<br>
	 * of the list is not the same as the number of triggered entities. If less<br>
	 * entities were triggered, then the last few entries in the list are<br>
	 * untouched. When more entities are triggered, then only 'size' number of<br>
	 * entries are inserted into the list, but still the complete count of the<br>
	 * triggered entities is returned. Which "xs" blobs are returned in the<br>
	 * latter case is undefined.<br>
	 * In case of a time out, the return value is 0.<br>
	 * Deleting the waitset while the application is blocked results in an<br>
	 * error code (i.e. < 0) returned by "wait".<br>
	 * Multiple threads may block on a single waitset at the same time;<br>
	 * the calls are entirely independent.<br>
	 * An empty waitset never triggers (i.e., dds_waitset_wait on an empty<br>
	 * waitset is essentially equivalent to a sleep).<br>
	 * The "dds_waitset_wait" operation is the same as the<br>
	 * "dds_waitset_wait_until" except that it takes an relative timeout.<br>
	 * The "dds_waitset_wait" operation is the same as the "dds_wait"<br>
	 * except that it takes an absolute timeout.<br>
	 * @param[in]  waitset    The waitset to set the trigger value on.<br>
	 * @param[out] xs         Pre-allocated list to store the 'blobs' that were<br>
	 *                        provided during the attach of the triggered entities.<br>
	 * @param[in]  nxs        The size of the pre-allocated blobs list.<br>
	 * @param[in]  abstimeout Absolute timeout<br>
	 * @returns A dds_return_t with the number of entities triggered or an error code.<br>
	 * @retval >0<br>
	 *             Number of entities triggered.<br>
	 * @retval  0<br>
	 *             Time out (no entities were triggered).<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The given waitset is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The waitset has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_waitset_wait_until(dds_entity_t, dds_attach_t*, size_t, dds_time_t)</code><br>
	 * <i>native declaration : _dds.h:1327</i>
	 */
	public static native int dds_waitset_wait_until(int waitset, PointerByReference xs, NativeSize nxs, long abstimeout);
	/**
	 * @brief Access and read the collection of data values (of same type) and sample info from the<br>
	 *        data reader, readcondition or querycondition.<br>
	 * Return value provides information about number of samples read, which will<br>
	 * be <= maxs. Based on the count, the buffer will contain data to be read only<br>
	 * when valid_data bit in sample info structure is set.<br>
	 * The buffer required for data values, could be allocated explicitly or can<br>
	 * use the memory from data reader to prevent copy. In the latter case, buffer and<br>
	 * sample_info should be returned back, once it is no longer using the Data.<br>
	 * Data values once read will remain in the buffer with the sample_state set to READ<br>
	 * and view_state set to NOT_NEW.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  bufsz The size of buffer provided.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_read(dds_entity_t, void**, dds_sample_info_t*, size_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1357</i>
	 */
	public static native int dds_read(int reader_or_condition, PointerByReference buf, dds_sample_info si, NativeSize bufsz, int maxs);
	/**
	 * @brief Access and read loaned samples of data reader, readcondition or querycondition.<br>
	 * After dds_read_wl function is being called and the data has been handled, dds_return_loan function must be called to possibly free memory.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
	 * @param[in]  maxs Maximum number of samples to read<br>
	 * @returns A dds_return_t with the number of samples read or an error code<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_read_wl(dds_entity_t, void**, dds_sample_info_t*, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1378</i>
	 */
	public static native int dds_read_wl(int reader_or_condition, PointerByReference buf, dds_sample_info si, int maxs);
	/**
	 * @brief Read the collection of data values and sample info from the data reader, readcondition<br>
	 *        or querycondition based on mask.<br>
	 * When using a readcondition or querycondition, their masks are or'd with the given mask.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  bufsz The size of buffer provided.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_read_mask(dds_entity_t, void**, dds_sample_info_t*, size_t, uint32_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1402</i>
	 */
	public static native int dds_read_mask(int reader_or_condition, PointerByReference buf, dds_sample_info si, NativeSize bufsz, int maxs, int mask);
	/**
	 * @brief Access and read loaned samples of data reader, readcondition<br>
	 *        or querycondition based on mask<br>
	 * When using a readcondition or querycondition, their masks are or'd with the given mask.<br>
	 * After dds_read_mask_wl function is being called and the data has been handled, dds_return_loan function must be called to possibly free memory<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_read_mask_wl(dds_entity_t, void**, dds_sample_info_t*, uint32_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1426</i>
	 */
	public static native int dds_read_mask_wl(int reader_or_condition, PointerByReference buf, dds_sample_info si, int maxs, int mask);
	/**
	 * @brief Access and read the collection of data values (of same type) and sample info from the<br>
	 *        data reader, readcondition or querycondition, coped by the provided instance handle.<br>
	 * This operation implements the same functionality as dds_read, except that only data scoped to<br>
	 * the provided instance handle is read.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  bufsz The size of buffer provided.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  handle Instance handle related to the samples to read.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The instance handle has not been registered with this reader.<br>
	 * Original signature : <code>dds_return_t dds_read_instance(dds_entity_t, void**, dds_sample_info_t*, size_t, uint32_t, dds_instance_handle_t)</code><br>
	 * <i>native declaration : _dds.h:1453</i>
	 */
	public static native int dds_read_instance(int reader_or_condition, PointerByReference buf, dds_sample_info si, NativeSize bufsz, int maxs, long handle);
	/**
	 * @brief Access and read loaned samples of data reader, readcondition or querycondition,<br>
	 *        scoped by the provided instance handle.<br>
	 * This operation implements the same functionality as dds_read_wl, except that only data<br>
	 * scoped to the provided instance handle is read.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  handle Instance handle related to the samples to read.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The instance handle has not been registered with this reader.<br>
	 * Original signature : <code>dds_return_t dds_read_instance_wl(dds_entity_t, void**, dds_sample_info_t*, uint32_t, dds_instance_handle_t)</code><br>
	 * <i>native declaration : _dds.h:1479</i>
	 */
	public static native int dds_read_instance_wl(int reader_or_condition, PointerByReference buf, dds_sample_info si, int maxs, long handle);
	/**
	 * @brief Read the collection of data values and sample info from the data reader, readcondition<br>
	 *        or querycondition based on mask and scoped by the provided instance handle.<br>
	 * This operation implements the same functionality as dds_read_mask, except that only data<br>
	 * scoped to the provided instance handle is read.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  bufsz The size of buffer provided.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  handle Instance handle related to the samples to read.<br>
	 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The instance handle has not been registered with this reader.<br>
	 * Original signature : <code>dds_return_t dds_read_instance_mask(dds_entity_t, void**, dds_sample_info_t*, size_t, uint32_t, dds_instance_handle_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1507</i>
	 */
	public static native int dds_read_instance_mask(int reader_or_condition, PointerByReference buf, dds_sample_info si, NativeSize bufsz, int maxs, long handle, int mask);
	/**
	 * @brief Access and read loaned samples of data reader, readcondition or<br>
	 *        querycondition based on mask, scoped by the provided instance handle.<br>
	 * This operation implements the same functionality as dds_read_mask_wl, except that<br>
	 * only data scoped to the provided instance handle is read.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  handle Instance handle related to the samples to read.<br>
	 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The instance handle has not been registered with this reader.<br>
	 * Original signature : <code>dds_return_t dds_read_instance_mask_wl(dds_entity_t, void**, dds_sample_info_t*, uint32_t, dds_instance_handle_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1534</i>
	 */
	public static native int dds_read_instance_mask_wl(int reader_or_condition, PointerByReference buf, dds_sample_info si, int maxs, long handle, int mask);
	/**
	 * @brief Access the collection of data values (of same type) and sample info from the<br>
	 *        data reader, readcondition or querycondition.<br>
	 * Data value once read is removed from the Data Reader cannot to<br>
	 * 'read' or 'taken' again.<br>
	 * Return value provides information about number of samples read, which will<br>
	 * be <= maxs. Based on the count, the buffer will contain data to be read only<br>
	 * when valid_data bit in sample info structure is set.<br>
	 * The buffer required for data values, could be allocated explicitly or can<br>
	 * use the memory from data reader to prevent copy. In the latter case, buffer and<br>
	 * sample_info should be returned back, once it is no longer using the Data.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  bufsz The size of buffer provided.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_take(dds_entity_t, void**, dds_sample_info_t*, size_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1564</i>
	 */
	public static native int dds_take(int reader_or_condition, PointerByReference buf, dds_sample_info si, NativeSize bufsz, int maxs);
	/**
	 * @brief Access loaned samples of data reader, readcondition or querycondition.<br>
	 * After dds_take_wl function is being called and the data has been handled, dds_return_loan function must be called to possibly free memory<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_take_wl(dds_entity_t, void**, dds_sample_info_t*, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1585</i>
	 */
	public static native int dds_take_wl(int reader_or_condition, PointerByReference buf, dds_sample_info si, int maxs);
	/**
	 * @brief Take the collection of data values (of same type) and sample info from the<br>
	 *        data reader, readcondition or querycondition based on mask<br>
	 * When using a readcondition or querycondition, their masks are or'd with the given mask.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  bufsz The size of buffer provided.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_take_mask(dds_entity_t, void**, dds_sample_info_t*, size_t, uint32_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1609</i>
	 */
	public static native int dds_take_mask(int reader_or_condition, PointerByReference buf, dds_sample_info si, NativeSize bufsz, int maxs, int mask);
	/**
	 * @brief  Access loaned samples of data reader, readcondition or querycondition based on mask.<br>
	 * When using a readcondition or querycondition, their masks are or'd with the given mask.<br>
	 * After dds_take_mask_wl function is being called and the data has been handled, dds_return_loan function must be called to possibly free memory<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_take_mask_wl(dds_entity_t, void**, dds_sample_info_t*, uint32_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1632</i>
	 */
	public static native int dds_take_mask_wl(int reader_or_condition, PointerByReference buf, dds_sample_info si, int maxs, int mask);
	/**
	 * Original signature : <code>int dds_takecdr(dds_entity_t, serdata**, uint32_t, dds_sample_info_t*, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1634</i>
	 */
	public static native int dds_takecdr(int reader_or_condition, DdscLibrary.serdata buf[], int maxs, dds_sample_info si, int mask);
	/**
	 * @brief Access the collection of data values (of same type) and sample info from the<br>
	 *        data reader, readcondition or querycondition but scoped by the given<br>
	 *        instance handle.<br>
	 * This operation mplements the same functionality as dds_take, except that only data<br>
	 * scoped to the provided instance handle is taken.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  bufsz The size of buffer provided.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  handle Instance handle related to the samples to read.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The instance handle has not been registered with this reader.<br>
	 * Original signature : <code>dds_return_t dds_take_instance(dds_entity_t, void**, dds_sample_info_t*, size_t, uint32_t, dds_instance_handle_t)</code><br>
	 * <i>native declaration : _dds.h:1662</i>
	 */
	public static native int dds_take_instance(int reader_or_condition, PointerByReference buf, dds_sample_info si, NativeSize bufsz, int maxs, long handle);
	/**
	 * @brief Access loaned samples of data reader, readcondition or querycondition,<br>
	 *        scoped by the given instance handle.<br>
	 * This operation implements the same functionality as dds_take_wl, except that<br>
	 * only data scoped to the provided instance handle is read.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  handle Instance handle related to the samples to read.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The instance handle has not been registered with this reader.<br>
	 * Original signature : <code>dds_return_t dds_take_instance_wl(dds_entity_t, void**, dds_sample_info_t*, uint32_t, dds_instance_handle_t)</code><br>
	 * <i>native declaration : _dds.h:1688</i>
	 */
	public static native int dds_take_instance_wl(int reader_or_condition, PointerByReference buf, dds_sample_info si, int maxs, long handle);
	/**
	 * @brief Take the collection of data values (of same type) and sample info from the<br>
	 *        data reader, readcondition or querycondition based on mask and scoped<br>
	 *        by the given instance handle.<br>
	 * This operation implements the same functionality as dds_take_mask, except that only<br>
	 * data scoped to the provided instance handle is read.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  bufsz The size of buffer provided.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  handle Instance handle related to the samples to read.<br>
	 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
	 * @returns A dds_return_t with the number of samples read or an error code.<br>
	 * @retval >=0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The instance handle has not been registered with this reader.<br>
	 * Original signature : <code>dds_return_t dds_take_instance_mask(dds_entity_t, void**, dds_sample_info_t*, size_t, uint32_t, dds_instance_handle_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1717</i>
	 */
	public static native int dds_take_instance_mask(int reader_or_condition, PointerByReference buf, dds_sample_info si, NativeSize bufsz, int maxs, long handle, int mask);
	/**
	 * @brief  Access loaned samples of data reader, readcondition or querycondition based<br>
	 *         on mask and scoped by the given intance handle.<br>
	 * This operation implements the same functionality as dds_take_mask_wl, except that<br>
	 * only data scoped to the provided instance handle is read.<br>
	 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value.<br>
	 * @param[in]  maxs Maximum number of samples to read.<br>
	 * @param[in]  handle Instance handle related to the samples to read.<br>
	 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
	 * @returns A dds_return_t with the number of samples or an error code.<br>
	 * @retval >= 0<br>
	 *             Number of samples read.<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *             An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
	 *             The instance handle has not been registered with this reader.<br>
	 * Original signature : <code>dds_return_t dds_take_instance_mask_wl(dds_entity_t, void**, dds_sample_info_t*, uint32_t, dds_instance_handle_t, uint32_t)</code><br>
	 * <i>native declaration : _dds.h:1744</i>
	 */
	public static native int dds_take_instance_mask_wl(int reader_or_condition, PointerByReference buf, dds_sample_info si, int maxs, long handle, int mask);
	/**
	 * @brief Read, copy and remove the status set for the entity<br>
	 * This operation copies the next, non-previously accessed<br>
	 * data value and corresponding sample info and removes from<br>
	 * the data reader. As an entity, only reader is accepted.<br>
	 * @param[in]  reader The reader entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si The pointer to \ref dds_sample_info_t returned for a data value.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The operation was successful.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The entity parameter is not a valid parameter.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_take_next(dds_entity_t, void**, dds_sample_info_t*)</code><br>
	 * <i>native declaration : _dds.h:1764</i>
	 */
	public static native int dds_take_next(int reader, PointerByReference buf, dds_sample_info si);
	/**
	 * @brief Read, copy and remove the status set for the entity<br>
	 * This operation copies the next, non-previously accessed<br>
	 * data value and corresponding sample info and removes from<br>
	 * the data reader. As an entity, only reader is accepted.<br>
	 * After dds_take_next_wl function is being called and the data has been handled,<br>
	 * dds_return_loan function must be called to possibly free memory.<br>
	 * @param[in]  reader The reader entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si The pointer to \ref dds_sample_info_t returned for a data value.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The operation was successful.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The entity parameter is not a valid parameter.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_take_next_wl(dds_entity_t, void**, dds_sample_info_t*)</code><br>
	 * <i>native declaration : _dds.h:1786</i>
	 */
	public static native int dds_take_next_wl(int reader, PointerByReference buf, dds_sample_info si);
	/**
	 * @brief Read and copy the status set for the entity<br>
	 * This operation copies the next, non-previously accessed<br>
	 * data value and corresponding sample info. As an entity,<br>
	 * only reader is accepted.<br>
	 * @param[in]  reader The reader entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si The pointer to \ref dds_sample_info_t returned for a data value.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The operation was successful.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The entity parameter is not a valid parameter.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_read_next(dds_entity_t, void**, dds_sample_info_t*)</code><br>
	 * <i>native declaration : _dds.h:1806</i>
	 */
	public static native int dds_read_next(int reader, PointerByReference buf, dds_sample_info si);
	/**
	 * @brief Read and copy the status set for the loaned sample<br>
	 * This operation copies the next, non-previously accessed<br>
	 * data value and corresponding loaned sample info. As an entity,<br>
	 * only reader is accepted.<br>
	 * After dds_read_next_wl function is being called and the data has been handled,<br>
	 * dds_return_loan function must be called to possibly free memory.<br>
	 * @param[in]  reader The reader entity.<br>
	 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL).<br>
	 * @param[out] si The pointer to \ref dds_sample_info_t returned for a data value.<br>
	 * @returns A dds_return_t indicating success or failure.<br>
	 * @retval DDS_RETCODE_OK<br>
	 *             The operation was successful.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *             The entity parameter is not a valid parameter.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *             The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *             The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_read_next_wl(dds_entity_t, void**, dds_sample_info_t*)</code><br>
	 * <i>native declaration : _dds.h:1828</i>
	 */
	public static native int dds_read_next_wl(int reader, PointerByReference buf, dds_sample_info si);
	public static class serdata extends PointerType {
		public serdata(Pointer address) {
			super(address);
		}
		public serdata() {
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
	public static class dds_listener_t extends PointerType {
		public dds_listener_t(Pointer address) {
			super(address);
		}
		public dds_listener_t() {
			super();
		}
	};
}
