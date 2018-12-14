package org.eclipse.cyclonedds.ddsc.dds_public_impl;
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
	 * <i>native declaration : _dds_public_impl.h:98</i><br>
	 * enum values
	 */
	public static interface dds_entity_kind {
		/** <i>native declaration : _dds_public_impl.h:86</i> */
		public static final int DDS_KIND_DONTCARE = 0x00000000;
		/** <i>native declaration : _dds_public_impl.h:87</i> */
		public static final int DDS_KIND_TOPIC = 0x01000000;
		/** <i>native declaration : _dds_public_impl.h:88</i> */
		public static final int DDS_KIND_PARTICIPANT = 0x02000000;
		/** <i>native declaration : _dds_public_impl.h:89</i> */
		public static final int DDS_KIND_READER = 0x03000000;
		/** <i>native declaration : _dds_public_impl.h:90</i> */
		public static final int DDS_KIND_WRITER = 0x04000000;
		/** <i>native declaration : _dds_public_impl.h:91</i> */
		public static final int DDS_KIND_SUBSCRIBER = 0x05000000;
		/** <i>native declaration : _dds_public_impl.h:92</i> */
		public static final int DDS_KIND_PUBLISHER = 0x06000000;
		/** <i>native declaration : _dds_public_impl.h:93</i> */
		public static final int DDS_KIND_COND_READ = 0x07000000;
		/** <i>native declaration : _dds_public_impl.h:94</i> */
		public static final int DDS_KIND_COND_QUERY = 0x08000000;
		/** <i>native declaration : _dds_public_impl.h:95</i> */
		public static final int DDS_KIND_COND_GUARD = 0x09000000;
		/** <i>native declaration : _dds_public_impl.h:96</i> */
		public static final int DDS_KIND_WAITSET = 0x0A000000;
		/** <i>native declaration : _dds_public_impl.h:97</i> */
		public static final int DDS_KIND_INTERNAL = 0x0B000000;
	};
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_LENGTH_UNLIMITED = (int)-1;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_TOPIC_NO_OPTIMIZE = (int)0x0001;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_TOPIC_FIXED_KEY = (int)0x0002;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_READ_SAMPLE_STATE = (int)1;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_NOT_READ_SAMPLE_STATE = (int)2;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_ANY_SAMPLE_STATE = (int)(1 | 2);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_NEW_VIEW_STATE = (int)4;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_NOT_NEW_VIEW_STATE = (int)8;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_ANY_VIEW_STATE = (int)(4 | 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_ALIVE_INSTANCE_STATE = (int)16;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_NOT_ALIVE_DISPOSED_INSTANCE_STATE = (int)32;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_NOT_ALIVE_NO_WRITERS_INSTANCE_STATE = (int)64;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_ANY_INSTANCE_STATE = (int)(16 | 32 | 64);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_ANY_STATE = (int)((1 | 2) | (4 | 8) | (16 | 32 | 64));
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_DOMAIN_DEFAULT = (int)-1;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_HANDLE_NIL = (int)0;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_ENTITY_NIL = (int)0;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_ENTITY_KIND_MASK = (int)(0x7F000000);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_RTS = (int)0x00000000;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_ADR = (int)0x01000000;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_JSR = (int)0x02000000;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_JEQ = (int)0x03000000;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_VAL_1BY = (int)0x01;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_VAL_2BY = (int)0x02;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_VAL_4BY = (int)0x03;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_VAL_8BY = (int)0x04;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_VAL_STR = (int)0x05;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_VAL_BST = (int)0x06;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_VAL_SEQ = (int)0x07;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_VAL_ARR = (int)0x08;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_VAL_UNI = (int)0x09;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_VAL_STU = (int)0x0a;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_TYPE_1BY = (int)(0x01 << 16);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_TYPE_2BY = (int)(0x02 << 16);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_TYPE_4BY = (int)(0x03 << 16);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_TYPE_8BY = (int)(0x04 << 16);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_TYPE_STR = (int)(0x05 << 16);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_TYPE_SEQ = (int)(0x07 << 16);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_TYPE_ARR = (int)(0x08 << 16);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_TYPE_UNI = (int)(0x09 << 16);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_TYPE_STU = (int)(0x0a << 16);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_TYPE_BST = (int)(0x06 << 16);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_TYPE_BOO = (int)(0x01 << 16);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_SUBTYPE_BOO = (int)(0x01 << 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_SUBTYPE_1BY = (int)(0x01 << 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_SUBTYPE_2BY = (int)(0x02 << 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_SUBTYPE_4BY = (int)(0x03 << 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_SUBTYPE_8BY = (int)(0x04 << 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_SUBTYPE_STR = (int)(0x05 << 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_SUBTYPE_SEQ = (int)(0x07 << 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_SUBTYPE_ARR = (int)(0x08 << 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_SUBTYPE_UNI = (int)(0x09 << 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_SUBTYPE_STU = (int)(0x0a << 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_SUBTYPE_BST = (int)(0x06 << 8);
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_FLAG_KEY = (int)0x01;
	/** <i>native declaration : _dds_public_impl.h</i> */
	public static final int DDS_OP_FLAG_DEF = (int)0x02;
	/**
	 * Description : Enable or disable write batching. Overrides default configuration<br>
	 * setting for write batching (DDSI2E/Internal/WriteBatch).<br>
	 * Arguments :<br>
	 *   -# enable Enables or disables write batching for all writers.<br>
	 * Original signature : <code>void dds_write_set_batch(bool)</code><br>
	 * <i>native declaration : _dds_public_impl.h:109</i>
	 */
	public static native void dds_write_set_batch(byte enable);
	/**
	 * Description : Install tcp/ssl and encryption support. Depends on openssl.<br>
	 * Arguments :<br>
	 *   -# None<br>
	 * Original signature : <code>void dds_ssl_plugin()</code><br>
	 * <i>native declaration : _dds_public_impl.h:116</i>
	 */
	public static native void dds_ssl_plugin();
	/**
	 * Description : Install client durability support. Depends on OSPL server.<br>
	 * Arguments :<br>
	 *   -# None<br>
	 * Original signature : <code>void dds_durability_plugin()</code><br>
	 * <i>native declaration : _dds_public_impl.h:123</i>
	 */
	public static native void dds_durability_plugin();
}
