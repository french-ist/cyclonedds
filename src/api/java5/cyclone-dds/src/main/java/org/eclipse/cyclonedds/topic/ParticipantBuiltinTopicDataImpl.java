/**
 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.topic;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.UserData;
import org.omg.dds.topic.BuiltinTopicKey;
import org.omg.dds.topic.ParticipantBuiltinTopicData;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.policy.PolicyConverter;

public class ParticipantBuiltinTopicDataImpl implements
        ParticipantBuiltinTopicData {
    private static final long serialVersionUID = 9013061767095970450L;
    private final transient CycloneServiceEnvironment environment;
    private ParticipantBuiltinTopicData old;
    private BuiltinTopicKey key;

    public ParticipantBuiltinTopicDataImpl(CycloneServiceEnvironment environment,
            ParticipantBuiltinTopicData old) {
        this.environment = environment;
        this.old = old;
        //TODO FRCYC this.key = new BuiltinTopicKeyImpl(this.environment, old.key);
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public BuiltinTopicKey getKey() {
        return this.key;
    }

    /*
    @Override
    public UserData getUserData() {
        return PolicyConverter.convert(this.environment, old.user_data);
    }

    @Override
    public void copyFrom(ParticipantBuiltinTopicData src) {
        if (src == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid ParticipantBuiltinTopicData (null) provided.");
        }
        try {
            ParticipantBuiltinTopicDataImpl impl = (ParticipantBuiltinTopicDataImpl) src;
            this.old = impl.old;
            this.key = new BuiltinTopicKeyImpl(this.environment, this.old.key);
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(
                    this.environment,
                    "ParticipantBuiltinTopicData.copyFrom() on non-OpenSplice ParticipantBuiltinTopicData implementation is not supported.");
        }
    }*/

    @Override
    public ParticipantBuiltinTopicData clone() {
        return new ParticipantBuiltinTopicDataImpl(this.environment, this.old);
    }

	@Override
	public UserData getUserData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void copyFrom(ParticipantBuiltinTopicData src) {
		// TODO Auto-generated method stub
		
	}
}
