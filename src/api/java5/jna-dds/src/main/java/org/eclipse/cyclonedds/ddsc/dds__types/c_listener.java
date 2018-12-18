package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_data_available_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_data_on_readers_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_inconsistent_topic_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_liveliness_changed_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_liveliness_lost_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_offered_deadline_missed_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_offered_incompatible_qos_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_publication_matched_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_requested_deadline_missed_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_requested_incompatible_qos_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_sample_lost_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_sample_rejected_fn;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.dds_on_subscription_matched_fn;
/**
 */
public class c_listener extends Structure {
	/** C type : dds_on_inconsistent_topic_fn */
	public dds_on_inconsistent_topic_fn on_inconsistent_topic;
	public dds_on_inconsistent_topic_fn getOn_inconsistent_topic() {
		return on_inconsistent_topic;
	}
	public void setOn_inconsistent_topic(dds_on_inconsistent_topic_fn on_inconsistent_topic) {
		this.on_inconsistent_topic = on_inconsistent_topic;
	}
	/** C type : dds_on_liveliness_lost_fn */
	public dds_on_liveliness_lost_fn on_liveliness_lost;
	public dds_on_liveliness_lost_fn getOn_liveliness_lost() {
		return on_liveliness_lost;
	}
	public void setOn_liveliness_lost(dds_on_liveliness_lost_fn on_liveliness_lost) {
		this.on_liveliness_lost = on_liveliness_lost;
	}
	/** C type : dds_on_offered_deadline_missed_fn */
	public dds_on_offered_deadline_missed_fn on_offered_deadline_missed;
	public dds_on_offered_deadline_missed_fn getOn_offered_deadline_missed() {
		return on_offered_deadline_missed;
	}
	public void setOn_offered_deadline_missed(dds_on_offered_deadline_missed_fn on_offered_deadline_missed) {
		this.on_offered_deadline_missed = on_offered_deadline_missed;
	}
	/** C type : dds_on_offered_incompatible_qos_fn */
	public dds_on_offered_incompatible_qos_fn on_offered_incompatible_qos;
	public dds_on_offered_incompatible_qos_fn getOn_offered_incompatible_qos() {
		return on_offered_incompatible_qos;
	}
	public void setOn_offered_incompatible_qos(dds_on_offered_incompatible_qos_fn on_offered_incompatible_qos) {
		this.on_offered_incompatible_qos = on_offered_incompatible_qos;
	}
	/** C type : dds_on_data_on_readers_fn */
	public dds_on_data_on_readers_fn on_data_on_readers;
	public dds_on_data_on_readers_fn getOn_data_on_readers() {
		return on_data_on_readers;
	}
	public void setOn_data_on_readers(dds_on_data_on_readers_fn on_data_on_readers) {
		this.on_data_on_readers = on_data_on_readers;
	}
	/** C type : dds_on_sample_lost_fn */
	public dds_on_sample_lost_fn on_sample_lost;
	public dds_on_sample_lost_fn getOn_sample_lost() {
		return on_sample_lost;
	}
	public void setOn_sample_lost(dds_on_sample_lost_fn on_sample_lost) {
		this.on_sample_lost = on_sample_lost;
	}
	/** C type : dds_on_data_available_fn */
	public dds_on_data_available_fn on_data_available;
	public dds_on_data_available_fn getOn_data_available() {
		return on_data_available;
	}
	public void setOn_data_available(dds_on_data_available_fn on_data_available) {
		this.on_data_available = on_data_available;
	}
	/** C type : dds_on_sample_rejected_fn */
	public dds_on_sample_rejected_fn on_sample_rejected;
	public dds_on_sample_rejected_fn getOn_sample_rejected() {
		return on_sample_rejected;
	}
	public void setOn_sample_rejected(dds_on_sample_rejected_fn on_sample_rejected) {
		this.on_sample_rejected = on_sample_rejected;
	}
	/** C type : dds_on_liveliness_changed_fn */
	public dds_on_liveliness_changed_fn on_liveliness_changed;
	public dds_on_liveliness_changed_fn getOn_liveliness_changed() {
		return on_liveliness_changed;
	}
	public void setOn_liveliness_changed(dds_on_liveliness_changed_fn on_liveliness_changed) {
		this.on_liveliness_changed = on_liveliness_changed;
	}
	/** C type : dds_on_requested_deadline_missed_fn */
	public dds_on_requested_deadline_missed_fn on_requested_deadline_missed;
	public dds_on_requested_deadline_missed_fn getOn_requested_deadline_missed() {
		return on_requested_deadline_missed;
	}
	public void setOn_requested_deadline_missed(dds_on_requested_deadline_missed_fn on_requested_deadline_missed) {
		this.on_requested_deadline_missed = on_requested_deadline_missed;
	}
	/** C type : dds_on_requested_incompatible_qos_fn */
	public dds_on_requested_incompatible_qos_fn on_requested_incompatible_qos;
	public dds_on_requested_incompatible_qos_fn getOn_requested_incompatible_qos() {
		return on_requested_incompatible_qos;
	}
	public void setOn_requested_incompatible_qos(dds_on_requested_incompatible_qos_fn on_requested_incompatible_qos) {
		this.on_requested_incompatible_qos = on_requested_incompatible_qos;
	}
	/** C type : dds_on_publication_matched_fn */
	public dds_on_publication_matched_fn on_publication_matched;
	public dds_on_publication_matched_fn getOn_publication_matched() {
		return on_publication_matched;
	}
	public void setOn_publication_matched(dds_on_publication_matched_fn on_publication_matched) {
		this.on_publication_matched = on_publication_matched;
	}
	/** C type : dds_on_subscription_matched_fn */
	public dds_on_subscription_matched_fn on_subscription_matched;
	public dds_on_subscription_matched_fn getOn_subscription_matched() {
		return on_subscription_matched;
	}
	public void setOn_subscription_matched(dds_on_subscription_matched_fn on_subscription_matched) {
		this.on_subscription_matched = on_subscription_matched;
	}
	/** C type : void* */
	public Pointer arg;
	public Pointer getArg() {
		return arg;
	}
	public void setArg(Pointer arg) {
		this.arg = arg;
	}
	public c_listener() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("on_inconsistent_topic", "on_liveliness_lost", "on_offered_deadline_missed", "on_offered_incompatible_qos", "on_data_on_readers", "on_sample_lost", "on_data_available", "on_sample_rejected", "on_liveliness_changed", "on_requested_deadline_missed", "on_requested_incompatible_qos", "on_publication_matched", "on_subscription_matched", "arg");
	}
	public c_listener(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends c_listener implements Structure.ByReference {
		
	};
	public static class ByValue extends c_listener implements Structure.ByValue {
		
	};
}
