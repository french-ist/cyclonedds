package org.eclipse.cyclonedds.core;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.cyclonedds.core.policy.PolicyFactoryImpl;
import org.eclipse.cyclonedds.domain.DomainParticipantFactoryImpl;
import org.eclipse.cyclonedds.type.TypeSupportImpl;
import org.eclipse.cyclonedds.type.TypeSupportProtobuf;
import org.omg.dds.core.Duration;
import org.omg.dds.core.GuardCondition;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment.ServiceProviderInterface;
import org.omg.dds.core.Time;
import org.omg.dds.core.WaitSet;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.type.TypeSupport;
import org.omg.dds.type.builtin.KeyedBytes;
import org.omg.dds.type.builtin.KeyedString;
import org.omg.dds.type.dynamic.DynamicDataFactory;
import org.omg.dds.type.dynamic.DynamicTypeFactory;

public class CycloneServiceProviderInterface implements ServiceProviderInterface {

	private final CycloneServiceEnvironment environment;
	private final PolicyFactory policyFactory;

	public CycloneServiceProviderInterface(CycloneServiceEnvironment environment) {
		this.environment = environment;	
		this.policyFactory = new PolicyFactoryImpl(environment);
	}

	@Override
	public DomainParticipantFactory getParticipantFactory() {
		return new DomainParticipantFactoryImpl(this.environment);
	}

	@Override
	public DynamicTypeFactory getTypeFactory() {
		throw new UnsupportedOperationExceptionImpl(this.environment,
				"getTypeFactory() not implemented yet.");
	}

	@Override
	public <TYPE> TypeSupport<TYPE> newTypeSupport(Class<TYPE> type,
			String registeredName) {
		TypeSupport<TYPE> result = null;
		Class<?> superType;
		String typeName;

		try {
			typeName = type.getName();
			superType = Class.forName(typeName).getSuperclass();

			if (superType != null) {
				if(isProtobufType(type)) {
					result = TypeSupportProtobuf.getInstance(
							this.environment, type, registeredName);
				} else {
					result = new TypeSupportImpl<TYPE>(this.environment,
							type, registeredName);
				}
			} else {
				throw new PreconditionNotMetExceptionImpl(
						this.environment,
						"Allocating new TypeSupport failed. "
								+ type.getName()
								+ "' is not a support type for this DDS implementation.");
			}
		} catch (ClassNotFoundException e) {
			throw new PreconditionNotMetExceptionImpl(this.environment,
					"Allocating new TypeSupport failed. " + e.getMessage());
		}
		return result;
	}

	@Override
	public Duration newDuration(long duration, TimeUnit unit) {
		return new DurationImpl(this.environment, duration, unit);
	}

	private <TYPE> boolean isProtobufType(Class<TYPE> type)
	{
		Class<?> parentType = type.getSuperclass();
		return parentType != null &&
				"com.google.protobuf.GeneratedMessage".equals(parentType.getName());
	}

	@Override
	public Duration infiniteDuration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Duration zeroDuration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModifiableTime newTime(long time, TimeUnit units) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time invalidTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceHandle nilHandle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GuardCondition newGuardCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaitSet newWaitSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Class<? extends Status>> allStatusKinds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Class<? extends Status>> noStatusKinds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QosProvider newQosProvider(String uri, String profile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PolicyFactory getPolicyFactory() {
		return policyFactory;
	}

	@Override
	public DynamicDataFactory getDynamicDataFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyedString newKeyedString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyedBytes newKeyedBytes() {
		// TODO Auto-generated method stub
		return null;
	}
}


/* TODO FRCYC
@Override
public Duration infiniteDuration() {
    return new DurationImpl(this.environment,
            DURATION_INFINITE_SEC.value,
            DURATION_INFINITE_NSEC.value);
}

@Override
public Duration zeroDuration() {
    return new DurationImpl(this.environment,
            DURATION_ZERO_SEC.value, DURATION_ZERO_NSEC.value);
}

@Override
public ModifiableTime newTime(long time, TimeUnit units) {
    return new ModifiableTimeImpl(this.environment, time, units);
}

@Override
public Time invalidTime() {
    return new TimeImpl(this.environment,
            TIMESTAMP_INVALID_SEC.value,
            TIMESTAMP_INVALID_NSEC.value);
}

@Override
public InstanceHandle nilHandle() {
    return new InstanceHandleImpl(this.environment,
            HANDLE_NIL.value);
}

@Override
public GuardCondition newGuardCondition() {
    return new GuardConditionImpl(this.environment);
}

@Override
public WaitSet newWaitSet() {
    return new WaitSetImpl(this.environment);
}

@Override
public Set<Class<? extends Status>> allStatusKinds() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Set<Class<? extends Status>> noStatusKinds() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public QosProvider newQosProvider(String uri, String profile) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public PolicyFactory getPolicyFactory() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public DynamicDataFactory getDynamicDataFactory() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public KeyedString newKeyedString() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public KeyedBytes newKeyedBytes() {
	// TODO Auto-generated method stub
	return null;
}

/*
@Override
public Set<Class<? extends Status>> allStatusKinds() {
    return StatusConverter.convertMask(this.environment,
            STATUS_MASK_ANY_V1_2.value);
}

@Override
public Set<Class<? extends Status>> noStatusKinds() {
    return StatusConverter.convertMask(this.environment,
            STATUS_MASK_NONE.value);
}
*/
