/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds_public_status;
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
	public static interface dds_sample_rejected_status_kind {
		public static final int DDS_NOT_REJECTED = 0;
		public static final int DDS_REJECTED_BY_INSTANCES_LIMIT = 1;
		public static final int DDS_REJECTED_BY_SAMPLES_LIMIT = 2;
		public static final int DDS_REJECTED_BY_SAMPLES_PER_INSTANCE_LIMIT = 3;
	};
	/**
	 * @brief Get INCONSISTENT_TOPIC status<br>
	 * This operation gets the status value corresponding to INCONSISTENT_TOPIC<br>
	 * and reset the status. The value can be obtained, only if the status is enabled for an entity.<br>
	 * NULL value for status is allowed and it will reset the trigger value when status is enabled.<br>
	 * @param[in]  topic  The entity to get the status<br>
	 * @param[out] status The pointer to \ref DCPS_Status_InconsistentTopic to get the status<br>
	 * @returns  0 - Success<br>
	 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *                  An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *                  One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *                  The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *                  The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_inconsistent_topic_status(dds_entity_t, dds_inconsistent_topic_status_t*)</code><br>
	 */
	public static native int dds_get_inconsistent_topic_status(int topic, dds_inconsistent_topic_status status);
	/**
	 * @brief Get PUBLICATION_MATCHED status<br>
	 * This operation gets the status value corresponding to PUBLICATION_MATCHED<br>
	 * and reset the status. The value can be obtained, only if the status is enabled for an entity.<br>
	 * NULL value for status is allowed and it will reset the trigger value when status is enabled.<br>
	 * @param[in]  writer  The entity to get the status<br>
	 * @param[out] status  The pointer to \ref DCPS_Status_PublicationMatched to get the status<br>
	 * @returns  0 - Success<br>
	 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *                  An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *                  One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *                  The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *                  The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_publication_matched_status(dds_entity_t, dds_publication_matched_status_t*)</code><br>
	 */
	public static native int dds_get_publication_matched_status(int writer, dds_publication_matched_status status);
	/**
	 * @brief Get LIVELINESS_LOST status<br>
	 * This operation gets the status value corresponding to LIVELINESS_LOST<br>
	 * and reset the status. The value can be obtained, only if the status is enabled for an entity.<br>
	 * NULL value for status is allowed and it will reset the trigger value when status is enabled.<br>
	 * @param[in]  writer  The entity to get the status<br>
	 * @param[out] status  The pointer to \ref DCPS_Status_LivelinessLost to get the status<br>
	 * @returns  0 - Success<br>
	 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *                  An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *                  One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *                  The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *                  The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_liveliness_lost_status(dds_entity_t, dds_liveliness_lost_status_t*)</code><br>
	 */
	public static native int dds_get_liveliness_lost_status(int writer, dds_liveliness_lost_status status);
	/**
	 * @brief Get OFFERED_DEADLINE_MISSED status<br>
	 * This operation gets the status value corresponding to OFFERED_DEADLINE_MISSED<br>
	 * and reset the status. The value can be obtained, only if the status is enabled for an entity.<br>
	 * NULL value for status is allowed and it will reset the trigger value when status is enabled.<br>
	 * @param[in]  writer  The entity to get the status<br>
	 * @param[out] status  The pointer to \ref DCPS_Status_OfferedDeadlineMissed to get the status<br>
	 * @returns  0 - Success<br>
	 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *                  An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *                  One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *                  The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *                  The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_offered_deadline_missed_status(dds_entity_t, dds_offered_deadline_missed_status_t*)</code><br>
	 */
	public static native int dds_get_offered_deadline_missed_status(int writer, dds_offered_deadline_missed_status status);
	/**
	 * @brief Get OFFERED_INCOMPATIBLE_QOS status<br>
	 * This operation gets the status value corresponding to OFFERED_INCOMPATIBLE_QOS<br>
	 * and reset the status. The value can be obtained, only if the status is enabled for an entity.<br>
	 * NULL value for status is allowed and it will reset the trigger value when status is enabled.<br>
	 * @param[in]  writer  The writer entity to get the status<br>
	 * @param[out] status  The pointer to \ref DCPS_Status_OfferedIncompatibleQoS to get the status<br>
	 * @returns  0 - Success<br>
	 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *                  An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *                  One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *                  The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *                  The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_offered_incompatible_qos_status(dds_entity_t, dds_offered_incompatible_qos_status_t*)</code><br>
	 */
	public static native int dds_get_offered_incompatible_qos_status(int writer, dds_offered_incompatible_qos_status status);
	/**
	 * @brief Get SUBSCRIPTION_MATCHED status<br>
	 * This operation gets the status value corresponding to SUBSCRIPTION_MATCHED<br>
	 * and reset the status. The value can be obtained, only if the status is enabled for an entity.<br>
	 * NULL value for status is allowed and it will reset the trigger value when status is enabled.<br>
	 * @param[in]  reader  The reader entity to get the status<br>
	 * @param[out] status  The pointer to \ref DCPS_Status_SubscriptionMatched to get the status<br>
	 * @returns  0 - Success<br>
	 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *                  An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *                  One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *                  The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *                  The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_subscription_matched_status(dds_entity_t, dds_subscription_matched_status_t*)</code><br>
	 */
	public static native int dds_get_subscription_matched_status(int reader, dds_subscription_matched_status status);
	/**
	 * @brief Get LIVELINESS_CHANGED status<br>
	 * This operation gets the status value corresponding to LIVELINESS_CHANGED<br>
	 * and reset the status. The value can be obtained, only if the status is enabled for an entity.<br>
	 * NULL value for status is allowed and it will reset the trigger value when status is enabled.<br>
	 * @param[in]  reader  The entity to get the status<br>
	 * @param[out] status  The pointer to \ref DCPS_Status_LivelinessChanged to get the status<br>
	 * @returns  0 - Success<br>
	 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *                  An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *                  One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *                  The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *                  The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_liveliness_changed_status(dds_entity_t, dds_liveliness_changed_status_t*)</code><br>
	 */
	public static native int dds_get_liveliness_changed_status(int reader, dds_liveliness_changed_status status);
	/**
	 * @brief Get SAMPLE_REJECTED status<br>
	 * This operation gets the status value corresponding to SAMPLE_REJECTED<br>
	 * and reset the status. The value can be obtained, only if the status is enabled for an entity.<br>
	 * NULL value for status is allowed and it will reset the trigger value when status is enabled.<br>
	 * @param[in]  reader  The entity to get the status<br>
	 * @param[out] status  The pointer to \ref DCPS_Status_SampleRejected to get the status<br>
	 * @returns  0 - Success<br>
	 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *                  An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *                  One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *                  The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *                  The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_sample_rejected_status(dds_entity_t, dds_sample_rejected_status_t*)</code><br>
	 */
	public static native int dds_get_sample_rejected_status(int reader, dds_sample_rejected_status status);
	/**
	 * @brief Get SAMPLE_LOST status<br>
	 * This operation gets the status value corresponding to SAMPLE_LOST<br>
	 * and reset the status. The value can be obtained, only if the status is enabled for an entity.<br>
	 * NULL value for status is allowed and it will reset the trigger value when status is enabled.<br>
	 * @param[in]  reader  The entity to get the status<br>
	 * @param[out] status  The pointer to \ref DCPS_Status_SampleLost to get the status<br>
	 * @returns A dds_return_t indicating success or failure<br>
	 * @retval DDS_RETCODE_OK<br>
	 *            Success<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *            An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *            One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *            The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *            The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_sample_lost_status(dds_entity_t, dds_sample_lost_status_t*)</code><br>
	 */
	public static native int dds_get_sample_lost_status(int reader, dds_sample_lost_status status);
	/**
	 * @brief Get REQUESTED_DEADLINE_MISSED status<br>
	 * This operation gets the status value corresponding to REQUESTED_DEADLINE_MISSED<br>
	 * and reset the status. The value can be obtained, only if the status is enabled for an entity.<br>
	 * NULL value for status is allowed and it will reset the trigger value when status is enabled.<br>
	 * @param[in]  reader  The entity to get the status<br>
	 * @param[out] status  The pointer to \ref DCPS_Status_RequestedDeadlineMissed to get the status<br>
	 * @returns A dds_return_t indicating success or failure<br>
	 * @retval DDS_RETCODE_OK<br>
	 *            Success<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *            An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *            One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *            The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *            The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_requested_deadline_missed_status(dds_entity_t, dds_requested_deadline_missed_status_t*)</code><br>
	 */
	public static native int dds_get_requested_deadline_missed_status(int reader, dds_requested_deadline_missed_status status);
	/**
	 * @brief Get REQUESTED_INCOMPATIBLE_QOS status<br>
	 * This operation gets the status value corresponding to REQUESTED_INCOMPATIBLE_QOS<br>
	 * and reset the status. The value can be obtained, only if the status is enabled for an entity.<br>
	 * NULL value for status is allowed and it will reset the trigger value when status is enabled.<br>
	 * @param[in]  reader  The entity to get the status<br>
	 * @param[out] status  The pointer to \ref DCPS_Status_RequestedIncompatibleQoS to get the status<br>
	 * @returns A dds_return_t indicating success or failure<br>
	 * @retval DDS_RETCODE_OK<br>
	 *            Success<br>
	 * @retval DDS_RETCODE_ERROR<br>
	 *            An internal error has occurred.<br>
	 * @retval DDS_RETCODE_BAD_PARAMETER<br>
	 *            One of the given arguments is not valid.<br>
	 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
	 *            The operation is invoked on an inappropriate object.<br>
	 * @retval DDS_RETCODE_ALREADY_DELETED<br>
	 *            The entity has already been deleted.<br>
	 * Original signature : <code>dds_return_t dds_get_requested_incompatible_qos_status(dds_entity_t, dds_requested_incompatible_qos_status_t*)</code><br>
	 */
	public static native int dds_get_requested_incompatible_qos_status(int reader, dds_requested_incompatible_qos_status status);
}
