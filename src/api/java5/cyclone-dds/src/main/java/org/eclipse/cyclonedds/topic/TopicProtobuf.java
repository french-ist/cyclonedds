/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.topic;

import java.util.Collection;

import org.omg.dds.core.status.Status;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.domain.DomainParticipantImpl;
import org.eclipse.cyclonedds.type.TypeSupportProtobuf;

public class TopicProtobuf<PROTOBUF_TYPE> extends TopicImpl<PROTOBUF_TYPE> {

    public TopicProtobuf(CycloneServiceEnvironment environment,
            DomainParticipantImpl participant, String topicName,
            TypeSupportProtobuf<PROTOBUF_TYPE, ?> typeSupport, TopicQos qos,
            TopicListener<PROTOBUF_TYPE> listener,
            Collection<Class<? extends Status>> statuses) {
        super(environment, participant, topicName, typeSupport, qos, listener,
                statuses);
    }
}
