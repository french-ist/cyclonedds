/*
 *                         Vortex OpenSplice
 *
 *   This software and documentation are Copyright 2006 to TO_YEAR ADLINK
 *   Technology Limited, its affiliated companies and licensors. All rights
 *   reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.eclipse.cyclonedds.topic;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.topic.ContentFilteredTopic;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.type.TypeSupport;
import org.eclipse.cyclonedds.core.AlreadyClosedExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.IllegalOperationExceptionImpl;
import org.eclipse.cyclonedds.core.CycloneServiceEnvironment;
import org.eclipse.cyclonedds.core.Utilities;
import org.eclipse.cyclonedds.domain.DomainParticipantImpl;

public class ContentFilteredTopicImpl<TYPE> implements
        TopicDescriptionExt<TYPE>, ContentFilteredTopic<TYPE> {
    private final AbstractTopic<TYPE> relatedTopic;
    private final ContentFilteredTopic old = null;
    private final DomainParticipantImpl parent;
    private final CycloneServiceEnvironment environment;
    private AtomicInteger refCount = null;

    public ContentFilteredTopicImpl(CycloneServiceEnvironment environment,
            DomainParticipantImpl parent, String name,
            AbstractTopic<TYPE> relatedTopic, String filterExpression,
            List<String> expressionParameters) {
        this.environment = environment;
        this.parent = parent;
        this.relatedTopic = relatedTopic;
        this.relatedTopic.retain();
        this.refCount = new AtomicInteger(1);

        /* TODO FRCYC
        if (expressionParameters != null) {
            this.old = parent.getOld().create_contentfilteredtopic(
                    name,
                    relatedTopic.getOld(),
                    filterExpression,
                    expressionParameters
                            .toArray(new String[expressionParameters.size()]));
        } else {
            this.old = parent.getOld().create_contentfilteredtopic(name,
                    relatedTopic.getOld(), filterExpression, null);
        }

        if (this.old == null) {
            Utilities.throwLastErrorException(this.environment);
        }*/
    }

    @Override
    public TypeSupport<TYPE> getTypeSupport() {
        return this.relatedTopic.getTypeSupport();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <OTHER> TopicDescription<OTHER> cast() {
        TopicDescription<OTHER> other;

        try {
            other = (TopicDescription<OTHER>) this;
        } catch (ClassCastException cce) {
            throw new IllegalOperationExceptionImpl(this.environment,
                    "Unable to perform requested cast.");
        }
        return other;
    }

    /* TODO FRCYC
    @Override
    public String getTypeName() {
        return this.old.get_type_name();
    }

    @Override
    public String getName() {
        return this.old.get_name();
    }*/

    @Override
    public DomainParticipant getParent() {
        return this.parent;
    }

    @Override
    public void close() {
        int newValue = this.refCount.decrementAndGet();

        if (newValue == 0) {
            //TODO FRCYC this.parent.destroyContentFilteredTopic(this);
        } else if (newValue < 0) {
            throw new AlreadyClosedExceptionImpl(this.environment,
                    "ContentFilteredTopic already closed.");
        }
    }

    @Override
    public ServiceEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public TopicDescription getOld() {
        return this.old;
    }

    /* TODO FRCYC
    @Override
    public String getFilterExpression() {
        return this.old.get_filter_expression();
    }

    @Override
    public List<String> getExpressionParameters() {
        StringSeqHolder holder = new StringSeqHolder();
        int rc = this.old.get_expression_parameters(holder);
        Utilities.checkReturnCode(rc, this.environment,
                "ContentFilteredTopic.getExpressionParameters() failed.");
        return Arrays.asList(holder.value);
    }
    

    @Override
    public void setExpressionParameters(List<String> expressionParameters) {
        if (expressionParameters == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "expressionParameters == null");
        }
        this.old.set_expression_parameters(expressionParameters
                .toArray(new String[expressionParameters.size()]));

    }
    */

    @Override
    public Topic<? extends TYPE> getRelatedTopic() {
        return this.relatedTopic;
    }

    @Override
    public void setExpressionParameters(String... expressionParameters) {
        if (expressionParameters == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "expressionParameters == null");
        }
        this.setExpressionParameters(Arrays.asList(expressionParameters));
    }

    @Override
    public void retain() {
        int newValue = this.refCount.incrementAndGet();

        if (newValue <= 0) {
            int refCount = this.refCount.decrementAndGet();
            throw new AlreadyClosedExceptionImpl(this.environment,
                    "ContentFilteredTopic already closed. refcount:" + refCount);
        }
        assert (newValue > newValue - 1);
        assert (newValue > 1);
    }

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFilterExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getExpressionParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExpressionParameters(List<String> expressionParameters) {
		// TODO Auto-generated method stub
		
	}

}
