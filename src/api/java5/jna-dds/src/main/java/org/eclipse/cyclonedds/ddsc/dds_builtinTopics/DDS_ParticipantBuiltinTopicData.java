package org.eclipse.cyclonedds.ddsc.dds_builtinTopics;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : _dds_builtinTopics.h:226</i><br>
 */
public class DDS_ParticipantBuiltinTopicData extends Structure {
	/** C type : DDS_BuiltinTopicKey_t */
	public int[] key = new int[3];
	public int[] getKey() {
		return key;
	}
	public void setKey(int key[]) {
		this.key = key;
	}
	/** C type : DDS_UserDataQosPolicy */
	public DDS_UserDataQosPolicy user_data;
	public DDS_UserDataQosPolicy getUser_data() {
		return user_data;
	}
	public void setUser_data(DDS_UserDataQosPolicy user_data) {
		this.user_data = user_data;
	}
	public DDS_ParticipantBuiltinTopicData() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("key", "user_data");
	}
	/**
	 * @param key C type : DDS_BuiltinTopicKey_t<br>
	 * @param user_data C type : DDS_UserDataQosPolicy
	 */
	public DDS_ParticipantBuiltinTopicData(int key[], DDS_UserDataQosPolicy user_data) {
		super();
		if ((key.length != this.key.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.key = key;
		this.user_data = user_data;
	}
	public DDS_ParticipantBuiltinTopicData(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_ParticipantBuiltinTopicData implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_ParticipantBuiltinTopicData implements Structure.ByValue {
		
	};
}
