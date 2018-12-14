package org.eclipse.cyclonedds.ddsc.dds_dcps_builtintopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_dcps_builtintopics.h:372</i><br>
 */
public class DDS_DomainParticipantQos extends Structure {
	/** C type : DDS_UserDataQosPolicy */
	public DDS_UserDataQosPolicy user_data;
	public DDS_UserDataQosPolicy getUser_data() {
		return user_data;
	}
	public void setUser_data(DDS_UserDataQosPolicy user_data) {
		this.user_data = user_data;
	}
	/** C type : DDS_EntityFactoryQosPolicy */
	public DDS_EntityFactoryQosPolicy entity_factory;
	public DDS_EntityFactoryQosPolicy getEntity_factory() {
		return entity_factory;
	}
	public void setEntity_factory(DDS_EntityFactoryQosPolicy entity_factory) {
		this.entity_factory = entity_factory;
	}
	/** C type : DDS_SchedulingQosPolicy */
	public DDS_SchedulingQosPolicy watchdog_scheduling;
	public DDS_SchedulingQosPolicy getWatchdog_scheduling() {
		return watchdog_scheduling;
	}
	public void setWatchdog_scheduling(DDS_SchedulingQosPolicy watchdog_scheduling) {
		this.watchdog_scheduling = watchdog_scheduling;
	}
	/** C type : DDS_SchedulingQosPolicy */
	public DDS_SchedulingQosPolicy listener_scheduling;
	public DDS_SchedulingQosPolicy getListener_scheduling() {
		return listener_scheduling;
	}
	public void setListener_scheduling(DDS_SchedulingQosPolicy listener_scheduling) {
		this.listener_scheduling = listener_scheduling;
	}
	public DDS_DomainParticipantQos() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("user_data", "entity_factory", "watchdog_scheduling", "listener_scheduling");
	}
	/**
	 * @param user_data C type : DDS_UserDataQosPolicy<br>
	 * @param entity_factory C type : DDS_EntityFactoryQosPolicy<br>
	 * @param watchdog_scheduling C type : DDS_SchedulingQosPolicy<br>
	 * @param listener_scheduling C type : DDS_SchedulingQosPolicy
	 */
	public DDS_DomainParticipantQos(DDS_UserDataQosPolicy user_data, DDS_EntityFactoryQosPolicy entity_factory, DDS_SchedulingQosPolicy watchdog_scheduling, DDS_SchedulingQosPolicy listener_scheduling) {
		super();
		this.user_data = user_data;
		this.entity_factory = entity_factory;
		this.watchdog_scheduling = watchdog_scheduling;
		this.listener_scheduling = listener_scheduling;
	}
	public DDS_DomainParticipantQos(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_DomainParticipantQos implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_DomainParticipantQos implements Structure.ByValue {
		
	};
}
