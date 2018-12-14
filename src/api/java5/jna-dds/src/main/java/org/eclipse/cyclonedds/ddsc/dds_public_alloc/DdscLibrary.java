package org.eclipse.cyclonedds.ddsc.dds_public_alloc;
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
	/**
	 * <i>native declaration : _dds_public_alloc.h:61</i><br>
	 * enum values
	 */
	public static interface dds_free_op_t {
		/** <i>native declaration : _dds_public_alloc.h:58</i> */
		public static final int DDS_FREE_ALL = 0x01 | 0x02 | 0x04;
		/** <i>native declaration : _dds_public_alloc.h:59</i> */
		public static final int DDS_FREE_CONTENTS = 0x01 | 0x02;
		/** <i>native declaration : _dds_public_alloc.h:60</i> */
		public static final int DDS_FREE_KEY = 0x01;
	};
	/** <i>native declaration : _dds_public_alloc.h</i> */
	public static final int DDS_FREE_KEY_BIT = (int)0x01;
	/** <i>native declaration : _dds_public_alloc.h</i> */
	public static final int DDS_FREE_CONTENTS_BIT = (int)0x02;
	/** <i>native declaration : _dds_public_alloc.h</i> */
	public static final int DDS_FREE_ALL_BIT = (int)0x04;
	/** <i>native declaration : _dds_public_alloc.h:88</i> */
	public interface dds_alloc_fn_t extends Callback {
		Pointer apply(NativeSize size_t1);
	};
	/** <i>native declaration : _dds_public_alloc.h:89</i> */
	public interface dds_realloc_fn_t extends Callback {
		Pointer apply(Pointer voidPtr1, NativeSize size_t1);
	};
	/** <i>native declaration : _dds_public_alloc.h:90</i> */
	public interface dds_free_fn_t extends Callback {
		void apply(Pointer voidPtr1);
	};
	/**
	 * Original signature : <code>void dds_set_allocator(const dds_allocator_t*, dds_allocator_t*)</code><br>
	 * <i>native declaration : _dds_public_alloc.h:71</i>
	 */
	//public static native void dds_set_allocator(dds_allocator n, dds_allocator o);
	/**
	 * Original signature : <code>void dds_set_aligned_allocator(const dds_aligned_allocator_t*, dds_aligned_allocator_t*)</code><br>
	 * <i>native declaration : _dds_public_alloc.h:79</i>
	 */
	//public static native void dds_set_aligned_allocator(dds_aligned_allocator n, dds_aligned_allocator o);
	/**
	 * Original signature : <code>void* dds_alloc(size_t)</code><br>
	 * <i>native declaration : _dds_public_alloc.h:81</i>
	 */
	public static native Pointer dds_alloc(NativeSize size);
	/**
	 * Original signature : <code>void* dds_realloc(void*, size_t)</code><br>
	 * <i>native declaration : _dds_public_alloc.h:83</i>
	 */
	public static native Pointer dds_realloc(Pointer ptr, NativeSize size);
	/**
	 * Original signature : <code>void* dds_realloc_zero(void*, size_t)</code><br>
	 * <i>native declaration : _dds_public_alloc.h:85</i>
	 */
	public static native Pointer dds_realloc_zero(Pointer ptr, NativeSize size);
	/**
	 * Original signature : <code>void dds_free(void*)</code><br>
	 * <i>native declaration : _dds_public_alloc.h:87</i>
	 */
	public static native void dds_free(Pointer ptr);
	/**
	 * Original signature : <code>char* dds_string_alloc(size_t)</code><br>
	 * <i>native declaration : _dds_public_alloc.h:92</i>
	 */
	public static native String dds_string_alloc(NativeSize size);
	/**
	 * Original signature : <code>char* dds_string_dup(const char*)</code><br>
	 * <i>native declaration : _dds_public_alloc.h:94</i>
	 */
	public static native String dds_string_dup(String str);
	/**
	 * Original signature : <code>void dds_string_free(char*)</code><br>
	 * <i>native declaration : _dds_public_alloc.h:96</i>
	 */
	public static native void dds_string_free(String str);
	/**
	 * Original signature : <code>void dds_sample_free(void*, dds_topic_descriptor*, dds_free_op_t)</code><br>
	 * <i>native declaration : _dds_public_alloc.h:98</i>
	 */
	public static native void dds_sample_free(Pointer sample, DdscLibrary.dds_topic_descriptor desc, int op);
	public static class dds_topic_descriptor extends PointerType {
		public dds_topic_descriptor(Pointer address) {
			super(address);
		}
		public dds_topic_descriptor() {
			super();
		}
	};
}
