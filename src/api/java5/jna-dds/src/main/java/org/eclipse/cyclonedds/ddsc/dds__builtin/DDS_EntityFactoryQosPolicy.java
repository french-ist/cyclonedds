package org.eclipse.cyclonedds.ddsc.dds__builtin;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : dds_builtinTopics.h:182</i><br>
 */
public class DDS_EntityFactoryQosPolicy extends Structure {
	public byte autoenable_created_entities;
	public byte getAutoenable_created_entities() {
		return autoenable_created_entities;
	}
	public void setAutoenable_created_entities(byte autoenable_created_entities) {
		this.autoenable_created_entities = autoenable_created_entities;
	}
	public DDS_EntityFactoryQosPolicy() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("autoenable_created_entities");
	}
	public DDS_EntityFactoryQosPolicy(byte autoenable_created_entities) {
		super();
		this.autoenable_created_entities = autoenable_created_entities;
	}
	public DDS_EntityFactoryQosPolicy(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DDS_EntityFactoryQosPolicy implements Structure.ByReference {
		
	};
	public static class ByValue extends DDS_EntityFactoryQosPolicy implements Structure.ByValue {
		
	};
}
