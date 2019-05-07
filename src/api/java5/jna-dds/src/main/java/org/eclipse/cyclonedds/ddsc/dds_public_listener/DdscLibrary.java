/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds_public_listener;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Callback;
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
	public interface dds_on_inconsistent_topic_fn extends Callback {
		void apply(int topic, DdscLibrary.dds_inconsistent_topic_status_t status, Pointer arg);
	};
	public interface dds_on_liveliness_lost_fn extends Callback {
		void apply(int writer, DdscLibrary.dds_liveliness_lost_status_t status, Pointer arg);
	};
	public interface dds_on_offered_deadline_missed_fn extends Callback {
		void apply(int writer, DdscLibrary.dds_offered_deadline_missed_status_t status, Pointer arg);
	};
	public interface dds_on_offered_incompatible_qos_fn extends Callback {
		void apply(int writer, DdscLibrary.dds_offered_incompatible_qos_status_t status, Pointer arg);
	};
	public interface dds_on_data_on_readers_fn extends Callback {
		void apply(int subscriber, Pointer arg);
	};
	public interface dds_on_sample_lost_fn extends Callback {
		void apply(int reader, DdscLibrary.dds_sample_lost_status_t status, Pointer arg);
	};
	public interface dds_on_data_available_fn extends Callback {
		void apply(int reader, Pointer arg);
	};
	public interface dds_on_sample_rejected_fn extends Callback {
		void apply(int reader, DdscLibrary.dds_sample_rejected_status_t status, Pointer arg);
	};
	public interface dds_on_liveliness_changed_fn extends Callback {
		void apply(int reader, DdscLibrary.dds_liveliness_changed_status_t status, Pointer arg);
	};
	public interface dds_on_requested_deadline_missed_fn extends Callback {
		void apply(int reader, DdscLibrary.dds_requested_deadline_missed_status_t status, Pointer arg);
	};
	public interface dds_on_requested_incompatible_qos_fn extends Callback {
		void apply(int reader, DdscLibrary.dds_requested_incompatible_qos_status_t status, Pointer arg);
	};
	public interface dds_on_publication_matched_fn extends Callback {
		void apply(int writer, DdscLibrary.dds_publication_matched_status_t status, Pointer arg);
	};
	public interface dds_on_subscription_matched_fn extends Callback {
		void apply(int reader, DdscLibrary.dds_subscription_matched_status_t status, Pointer arg);
	};
	/**
	 * @brief Allocate memory and initializes to default values (::DDS_LUNSET) of a listener<br>
	 * @param[in] arg optional pointer that will be passed on to the listener callbacks<br>
	 * @return Returns a pointer to the allocated memory for dds_listener_t structure.<br>
	 * Original signature : <code>dds_listener_t* dds_create_listener(void*)</code><br>
	 */
	public static native PointerByReference dds_create_listener(Pointer arg);
	/**
	 * Original signature : <code>dds_listener_t* dds_listener_create(void*)</code><br>
	 */
	public static native PointerByReference dds_listener_create(Pointer arg);
	/**
	 * @brief Delete the memory allocated to listener structure<br>
	 * @param[in] listener pointer to the listener struct to delete<br>
	 * Original signature : <code>void dds_delete_listener(__declspec(__restrict) dds_listener_t*)</code><br>
	 */
	public static native void dds_delete_listener(PointerByReference listener);
	/**
	 * Original signature : <code>void dds_listener_delete(__declspec(__restrict) dds_listener_t*)</code><br>
	 */
	public static native void dds_listener_delete(PointerByReference listener);
	/**
	 * @brief Reset the listener structure contents to ::DDS_LUNSET<br>
	 * @param[in,out] listener pointer to the listener struct to reset<br>
	 * Original signature : <code>void dds_reset_listener(__declspec(__restrict) dds_listener_t*)</code><br>
	 */
	public static native void dds_reset_listener(PointerByReference listener);
	/**
	 * Original signature : <code>void dds_listener_reset(__declspec(__restrict) dds_listener_t*)</code><br>
	 */
	public static native void dds_listener_reset(PointerByReference listener);
	/**
	 * @brief Copy the listener callbacks from source to destination<br>
	 * @param[in,out] dst The pointer to the destination listener structure, where the content is to copied<br>
	 * @param[in] src The pointer to the source listener structure to be copied<br>
	 * Original signature : <code>void dds_copy_listener(__declspec(__restrict) dds_listener_t*, __declspec(__restrict) const dds_listener_t*)</code><br>
	 */
	public static native void dds_copy_listener(PointerByReference dst, PointerByReference src);
	/**
	 * Original signature : <code>void dds_listener_copy(__declspec(__restrict) dds_listener_t*, __declspec(__restrict) const dds_listener_t*)</code><br>
	 */
	public static native void dds_listener_copy(PointerByReference dst, PointerByReference src);
	/**
	 * @brief Copy the listener callbacks from source to destination, unless already set<br>
	 * Any listener callbacks already set in @p dst (including NULL) are skipped, only<br>
	 * those set to DDS_LUNSET are copied from @p src.<br>
	 * @param[in,out] dst The pointer to the destination listener structure, where the content is merged<br>
	 * @param[in] src The pointer to the source listener structure to be copied<br>
	 * Original signature : <code>void dds_merge_listener(__declspec(__restrict) dds_listener_t*, __declspec(__restrict) const dds_listener_t*)</code><br>
	 */
	public static native void dds_merge_listener(PointerByReference dst, PointerByReference src);
	/**
	 * Original signature : <code>void dds_listener_merge(__declspec(__restrict) dds_listener_t*, __declspec(__restrict) const dds_listener_t*)</code><br>
	 */
	public static native void dds_listener_merge(PointerByReference dst, PointerByReference src);
	/**
	 * @brief Set the inconsistent_topic callback in the listener structure.<br>
	 * @param listener The pointer to the listener structure, where the callback will be set<br>
	 * @param callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_inconsistent_topic(__declspec(__restrict) dds_listener_t*, dds_on_inconsistent_topic_fn)</code><br>
	 */
	public static native void dds_lset_inconsistent_topic(PointerByReference listener, DdscLibrary.dds_on_inconsistent_topic_fn callback);
	/**
	 * @brief Set the liveliness_lost callback in the listener structure.<br>
	 * @param[out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_liveliness_lost(__declspec(__restrict) dds_listener_t*, dds_on_liveliness_lost_fn)</code><br>
	 */
	public static native void dds_lset_liveliness_lost(PointerByReference listener, DdscLibrary.dds_on_liveliness_lost_fn callback);
	/**
	 * @brief Set the offered_deadline_missed callback in the listener structure.<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_offered_deadline_missed(__declspec(__restrict) dds_listener_t*, dds_on_offered_deadline_missed_fn)</code><br>
	 */
	public static native void dds_lset_offered_deadline_missed(PointerByReference listener, DdscLibrary.dds_on_offered_deadline_missed_fn callback);
	/**
	 * @brief Set the offered_incompatible_qos callback in the listener structure.<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_offered_incompatible_qos(__declspec(__restrict) dds_listener_t*, dds_on_offered_incompatible_qos_fn)</code><br>
	 */
	public static native void dds_lset_offered_incompatible_qos(PointerByReference listener, DdscLibrary.dds_on_offered_incompatible_qos_fn callback);
	/**
	 * @brief Set the data_on_readers callback in the listener structure.<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_data_on_readers(__declspec(__restrict) dds_listener_t*, dds_on_data_on_readers_fn)</code><br>
	 */
	public static native void dds_lset_data_on_readers(PointerByReference listener, DdscLibrary.dds_on_data_on_readers_fn callback);
	/**
	 * @brief Set the sample_lost callback in the listener structure.<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_sample_lost(__declspec(__restrict) dds_listener_t*, dds_on_sample_lost_fn)</code><br>
	 */
	public static native void dds_lset_sample_lost(PointerByReference listener, DdscLibrary.dds_on_sample_lost_fn callback);
	/**
	 * @brief Set the data_available callback in the listener structure.<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_data_available(__declspec(__restrict) dds_listener_t*, dds_on_data_available_fn)</code><br>
	 */
	public static native void dds_lset_data_available(PointerByReference listener, DdscLibrary.dds_on_data_available_fn callback);
	/**
	 * @brief Set the sample_rejected callback in the listener structure.<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_sample_rejected(__declspec(__restrict) dds_listener_t*, dds_on_sample_rejected_fn)</code><br>
	 */
	public static native void dds_lset_sample_rejected(PointerByReference listener, DdscLibrary.dds_on_sample_rejected_fn callback);
	/**
	 * @brief Set the liveliness_changed callback in the listener structure.<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_liveliness_changed(__declspec(__restrict) dds_listener_t*, dds_on_liveliness_changed_fn)</code><br>
	 */
	public static native void dds_lset_liveliness_changed(PointerByReference listener, DdscLibrary.dds_on_liveliness_changed_fn callback);
	/**
	 * @brief Set the requested_deadline_missed callback in the listener structure.<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_requested_deadline_missed(__declspec(__restrict) dds_listener_t*, dds_on_requested_deadline_missed_fn)</code><br>
	 */
	public static native void dds_lset_requested_deadline_missed(PointerByReference listener, DdscLibrary.dds_on_requested_deadline_missed_fn callback);
	/**
	 * @brief Set the requested_incompatible_qos callback in the listener structure.<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_requested_incompatible_qos(__declspec(__restrict) dds_listener_t*, dds_on_requested_incompatible_qos_fn)</code><br>
	 */
	public static native void dds_lset_requested_incompatible_qos(PointerByReference listener, DdscLibrary.dds_on_requested_incompatible_qos_fn callback);
	/**
	 * @brief Set the publication_matched callback in the listener structure.<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_publication_matched(__declspec(__restrict) dds_listener_t*, dds_on_publication_matched_fn)</code><br>
	 */
	public static native void dds_lset_publication_matched(PointerByReference listener, DdscLibrary.dds_on_publication_matched_fn callback);
	/**
	 * @brief Set the subscription_matched callback in the listener structure.<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be set<br>
	 * @param[in] callback The callback to set in the listener, can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lset_subscription_matched(__declspec(__restrict) dds_listener_t*, dds_on_subscription_matched_fn)</code><br>
	 */
	public static native void dds_lset_subscription_matched(PointerByReference listener, DdscLibrary.dds_on_subscription_matched_fn callback);
	/**
	 * @brief Get the inconsistent_topic callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_inconsistent_topic(__declspec(__restrict) const dds_listener_t*, dds_on_inconsistent_topic_fn*)</code><br>
	 */
	public static native void dds_lget_inconsistent_topic(PointerByReference listener, DdscLibrary.dds_on_inconsistent_topic_fn callback);
	/**
	 * @brief Get the liveliness_lost callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_liveliness_lost(__declspec(__restrict) const dds_listener_t*, dds_on_liveliness_lost_fn*)</code><br>
	 */
	public static native void dds_lget_liveliness_lost(PointerByReference listener, DdscLibrary.dds_on_liveliness_lost_fn callback);
	/**
	 * @brief Get the offered_deadline_missed callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_offered_deadline_missed(__declspec(__restrict) const dds_listener_t*, dds_on_offered_deadline_missed_fn*)</code><br>
	 */
	public static native void dds_lget_offered_deadline_missed(PointerByReference listener, DdscLibrary.dds_on_offered_deadline_missed_fn callback);
	/**
	 * @brief Get the offered_incompatible_qos callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_offered_incompatible_qos(__declspec(__restrict) const dds_listener_t*, dds_on_offered_incompatible_qos_fn*)</code><br>
	 */
	public static native void dds_lget_offered_incompatible_qos(PointerByReference listener, DdscLibrary.dds_on_offered_incompatible_qos_fn callback);
	/**
	 * @brief Get the data_on_readers callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_data_on_readers(__declspec(__restrict) const dds_listener_t*, dds_on_data_on_readers_fn*)</code><br>
	 */
	public static native void dds_lget_data_on_readers(PointerByReference listener, DdscLibrary.dds_on_data_on_readers_fn callback);
	/**
	 * @brief Get the sample_lost callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_sample_lost(__declspec(__restrict) const dds_listener_t*, dds_on_sample_lost_fn*)</code><br>
	 */
	public static native void dds_lget_sample_lost(PointerByReference listener, DdscLibrary.dds_on_sample_lost_fn callback);
	/**
	 * @brief Get the data_available callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_data_available(__declspec(__restrict) const dds_listener_t*, dds_on_data_available_fn*)</code><br>
	 */
	public static native void dds_lget_data_available(PointerByReference listener, DdscLibrary.dds_on_data_available_fn callback);
	/**
	 * @brief Get the sample_rejected callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_sample_rejected(__declspec(__restrict) const dds_listener_t*, dds_on_sample_rejected_fn*)</code><br>
	 */
	public static native void dds_lget_sample_rejected(PointerByReference listener, DdscLibrary.dds_on_sample_rejected_fn callback);
	/**
	 * @brief Get the liveliness_changed callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_liveliness_changed(__declspec(__restrict) const dds_listener_t*, dds_on_liveliness_changed_fn*)</code><br>
	 */
	public static native void dds_lget_liveliness_changed(PointerByReference listener, DdscLibrary.dds_on_liveliness_changed_fn callback);
	/**
	 * @brief Get the requested_deadline_missed callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_requested_deadline_missed(__declspec(__restrict) const dds_listener_t*, dds_on_requested_deadline_missed_fn*)</code><br>
	 */
	public static native void dds_lget_requested_deadline_missed(PointerByReference listener, DdscLibrary.dds_on_requested_deadline_missed_fn callback);
	/**
	 * @brief Get the requested_incompatible_qos callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_requested_incompatible_qos(__declspec(__restrict) const dds_listener_t*, dds_on_requested_incompatible_qos_fn*)</code><br>
	 */
	public static native void dds_lget_requested_incompatible_qos(PointerByReference listener, DdscLibrary.dds_on_requested_incompatible_qos_fn callback);
	/**
	 * @brief Get the publication_matched callback from the listener structure.<br>
	 * @param[in] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * @param[in,out] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * Original signature : <code>void dds_lget_publication_matched(__declspec(__restrict) const dds_listener_t*, dds_on_publication_matched_fn*)</code><br>
	 */
	public static native void dds_lget_publication_matched(PointerByReference listener, DdscLibrary.dds_on_publication_matched_fn callback);
	/**
	 * @brief Get the subscription_matched callback from the listener structure.<br>
	 * @param[in] callback Pointer where the retrieved callback can be stored; can be NULL, ::DDS_LUNSET or a valid callback pointer<br>
	 * @param[in,out] listener The pointer to the listener structure, where the callback will be retrieved from<br>
	 * Original signature : <code>void dds_lget_subscription_matched(__declspec(__restrict) const dds_listener_t*, dds_on_subscription_matched_fn*)</code><br>
	 */
	public static native void dds_lget_subscription_matched(PointerByReference listener, DdscLibrary.dds_on_subscription_matched_fn callback);
	public static class dds_requested_deadline_missed_status_t extends PointerType {
		public dds_requested_deadline_missed_status_t(Pointer address) {
			super(address);
		}
		public dds_requested_deadline_missed_status_t() {
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
	public static class dds_liveliness_lost_status_t extends PointerType {
		public dds_liveliness_lost_status_t(Pointer address) {
			super(address);
		}
		public dds_liveliness_lost_status_t() {
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
	public static class dds_requested_incompatible_qos_status_t extends PointerType {
		public dds_requested_incompatible_qos_status_t(Pointer address) {
			super(address);
		}
		public dds_requested_incompatible_qos_status_t() {
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
	public static class dds_sample_rejected_status_t extends PointerType {
		public dds_sample_rejected_status_t(Pointer address) {
			super(address);
		}
		public dds_sample_rejected_status_t() {
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
	public static class dds_offered_deadline_missed_status_t extends PointerType {
		public dds_offered_deadline_missed_status_t(Pointer address) {
			super(address);
		}
		public dds_offered_deadline_missed_status_t() {
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
	public static class dds_listener_t extends PointerType {
		public dds_listener_t(Pointer address) {
			super(address);
		}
		public dds_listener_t() {
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
}