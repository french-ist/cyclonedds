package org.eclipse.cyclonedds.ddsc.dds__rhc;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.PointerByReference;
import java.nio.IntBuffer;
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
	 * Original signature : <code>rhc* dds_rhc_new(dds_reader*, sertopic*)</code><br>
	 * <i>native declaration : _dds__rhc.h:64</i>
	 */
	public static native DdscLibrary.rhc dds_rhc_new(DdscLibrary.dds_reader reader, DdscLibrary.sertopic topic);
	/**
	 * Original signature : <code>void dds_rhc_free(rhc*)</code><br>
	 * <i>native declaration : _dds__rhc.h:66</i>
	 */
	public static native void dds_rhc_free(DdscLibrary.rhc rhc);
	/**
	 * Original signature : <code>void dds_rhc_fini(rhc*)</code><br>
	 * <i>native declaration : _dds__rhc.h:68</i>
	 */
	public static native void dds_rhc_fini(DdscLibrary.rhc rhc);
	/**
	 * Original signature : <code>uint32_t dds_rhc_lock_samples(rhc*)</code><br>
	 * <i>native declaration : _dds__rhc.h:70</i>
	 */
	public static native int dds_rhc_lock_samples(DdscLibrary.rhc rhc);
	/**
	 * Original signature : <code>bool dds_rhc_store(rhc*, proxy_writer_info*, serdata*, tkmap_instance*)</code><br>
	 * <i>native declaration : _dds__rhc.h:72</i>
	 */
	public static native byte dds_rhc_store(DdscLibrary.rhc rhc, DdscLibrary.proxy_writer_info pwr_info, DdscLibrary.serdata sample, DdscLibrary.tkmap_instance tk);
	/**
	 * Original signature : <code>void dds_rhc_unregister_wr(rhc*, proxy_writer_info*)</code><br>
	 * <i>native declaration : _dds__rhc.h:74</i>
	 */
	public static native void dds_rhc_unregister_wr(DdscLibrary.rhc rhc, DdscLibrary.proxy_writer_info pwr_info);
	/**
	 * Original signature : <code>void dds_rhc_relinquish_ownership(rhc*, const uint64_t)</code><br>
	 * <i>native declaration : _dds__rhc.h:76</i>
	 */
	public static native void dds_rhc_relinquish_ownership(DdscLibrary.rhc rhc, long wr_iid);
	/**
	 * Original signature : <code>int dds_rhc_read(rhc*, bool, void**, dds_sample_info_t*, uint32_t, uint32_t, dds_instance_handle_t, dds_readcond*)</code><br>
	 * <i>native declaration : _dds__rhc.h:78</i>
	 */
	public static native int dds_rhc_read(DdscLibrary.rhc rhc, byte lock, PointerByReference values, DdscLibrary.dds_sample_info_t info_seq, int max_samples, int mask, long handle, DdscLibrary.dds_readcond cond);
	/**
	 * Original signature : <code>int dds_rhc_take(rhc*, bool, void**, dds_sample_info_t*, uint32_t, uint32_t, dds_instance_handle_t, dds_readcond*)</code><br>
	 * <i>native declaration : _dds__rhc.h:80</i>
	 */
	public static native int dds_rhc_take(DdscLibrary.rhc rhc, byte lock, PointerByReference values, DdscLibrary.dds_sample_info_t info_seq, int max_samples, int mask, long handle, DdscLibrary.dds_readcond cond);
	/**
	 * Original signature : <code>void dds_rhc_set_qos(rhc*, nn_xqos*)</code><br>
	 * <i>native declaration : _dds__rhc.h:82</i>
	 */
	public static native void dds_rhc_set_qos(DdscLibrary.rhc rhc, DdscLibrary.nn_xqos qos);
	/**
	 * Original signature : <code>void dds_rhc_add_readcondition(dds_readcond*)</code><br>
	 * <i>native declaration : _dds__rhc.h:84</i>
	 */
	public static native void dds_rhc_add_readcondition(DdscLibrary.dds_readcond cond);
	/**
	 * Original signature : <code>void dds_rhc_remove_readcondition(dds_readcond*)</code><br>
	 * <i>native declaration : _dds__rhc.h:86</i>
	 */
	public static native void dds_rhc_remove_readcondition(DdscLibrary.dds_readcond cond);
	/**
	 * Original signature : <code>bool dds_rhc_add_waitset(dds_readcond*, dds_waitset*, dds_attach_t)</code><br>
	 * <i>native declaration : _dds__rhc.h:88</i>
	 */
	public static native byte dds_rhc_add_waitset(DdscLibrary.dds_readcond cond, DdscLibrary.dds_waitset waitset, IntBuffer x);
	/**
	 * Original signature : <code>int dds_rhc_remove_waitset(dds_readcond*, dds_waitset*)</code><br>
	 * <i>native declaration : _dds__rhc.h:90</i>
	 */
	public static native int dds_rhc_remove_waitset(DdscLibrary.dds_readcond cond, DdscLibrary.dds_waitset waitset);
	/**
	 * Original signature : <code>int dds_rhc_takecdr(rhc*, bool, serdata**, dds_sample_info_t*, uint32_t, unsigned, unsigned, unsigned, dds_instance_handle_t)</code><br>
	 * <i>native declaration : _dds__rhc.h:92</i>
	 */
	public static native int dds_rhc_takecdr(DdscLibrary.rhc rhc, byte lock, DdscLibrary.serdata values[], DdscLibrary.dds_sample_info_t info_seq, int max_samples, int sample_states, int view_states, int instance_states, long handle);
	public static class serdata extends PointerType {
		public serdata(Pointer address) {
			super(address);
		}
		public serdata() {
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
	public static class tkmap_instance extends PointerType {
		public tkmap_instance(Pointer address) {
			super(address);
		}
		public tkmap_instance() {
			super();
		}
	};
	public static class dds_reader extends PointerType {
		public dds_reader(Pointer address) {
			super(address);
		}
		public dds_reader() {
			super();
		}
	};
	public static class proxy_writer_info extends PointerType {
		public proxy_writer_info(Pointer address) {
			super(address);
		}
		public proxy_writer_info() {
			super();
		}
	};
	public static class nn_xqos extends PointerType {
		public nn_xqos(Pointer address) {
			super(address);
		}
		public nn_xqos() {
			super();
		}
	};
	public static class dds_readcond extends PointerType {
		public dds_readcond(Pointer address) {
			super(address);
		}
		public dds_readcond() {
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
	public static class dds_waitset extends PointerType {
		public dds_waitset(Pointer address) {
			super(address);
		}
		public dds_waitset() {
			super();
		}
	};
	public static class dds_sample_info_t extends PointerType {
		public dds_sample_info_t(Pointer address) {
			super(address);
		}
		public dds_sample_info_t() {
			super();
		}
	};
}
