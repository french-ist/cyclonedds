/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.sub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.omg.dds.sub.QueryCondition;
import org.omg.dds.sub.ReadCondition;
import org.eclipse.cyclonedds.core.Condition;
import org.eclipse.cyclonedds.core.IllegalArgumentExceptionImpl;
import org.eclipse.cyclonedds.core.ServiceEnvironmentImpl;
import org.eclipse.cyclonedds.core.Utilities;

public class QueryConditionImpl<TYPE> extends ReadConditionImpl<TYPE> implements
        QueryCondition<TYPE>, Condition<Condition> {
    public QueryConditionImpl(ServiceEnvironmentImpl environment,
            AbstractDataReader<TYPE> parent, DataStateImpl state,
            String queryExpression, List<String> queryParameters) {
        super(environment, parent, state, false);

        this.initCondition(queryExpression, queryParameters);
    }

    public QueryConditionImpl(ServiceEnvironmentImpl environment,
            AbstractDataReader<TYPE> parent,
            String queryExpression, List<String> queryParameters) {
        super(environment, parent, false);

        this.initCondition(queryExpression, queryParameters);
    }

    private void initCondition(String queryExpression, List<String> queryParameters){
        if (queryParameters != null) {
            for (String param : queryParameters) {
                if (param == null) {
                    throw new IllegalArgumentExceptionImpl(this.environment,
                            "Invalid query parameter (null) provided.");
                }
            }
            /* TODO FRCYC
            this.old = parent.getOld()
                    .create_querycondition(
                            this.state.getOldSampleState(),
                            this.state.getOldViewState(),
                            this.state.getOldInstanceState(),
                            queryExpression,
                            queryParameters.toArray(new String[queryParameters
                                    .size()]));
                                    */
        } else {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Illegal List of query parameters (null) provided.");
        }
        if (this.old == null) {
            Utilities.throwLastErrorException(this.environment);
        }
    }

    @Override
    public String getQueryExpression() {
        //TODO FRCYC return ((QueryCondition) this.old).get_query_expression();
    	return null;
    }

    @Override
    public List<String> getQueryParameters() {
    	/* TODO FRCYC
        StringSeqHolder holder = new StringSeqHolder();
        int rc = ((QueryCondition) this.old).get_query_parameters(holder);

        Utilities.checkReturnCode(rc, this.environment,
                "QueryCondition.getQueryParameters() failed");
        ArrayList<String> queryParams = new ArrayList<String>();

        for (String param : holder.value) {
            queryParams.add(param);
        }
        return queryParams;
        */
    	return null;
    }

    @Override
    public void setQueryParameters(List<String> queryParameters) {
        if (queryParameters == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid queryParameters parameter (null) provided.");
        }
        for (String param : queryParameters) {
            if (param == null) {
                throw new IllegalArgumentExceptionImpl(this.environment,
                        "Invalid query parameter (null) provided.");
            }
        }
        /* TODO FRCYC
        ((QueryCondition) this.old).set_query_parameters(queryParameters
                .toArray(new String[queryParameters.size()]));
                */
    }

    @Override
    public void setQueryParameters(String... queryParameters) {
        if (queryParameters == null) {
            throw new IllegalArgumentExceptionImpl(this.environment,
                    "Invalid queryParameters parameter (null) provided.");
        }
        for (String param : queryParameters) {
            if (param == null) {
                throw new IllegalArgumentExceptionImpl(this.environment,
                        "Invalid query parameter (null) provided.");
            }
        }
        this.setQueryParameters(Arrays.asList(queryParameters));
    }

    @Override
    public Condition getOldCondition() {
        return null;//TODO FRCYC this.old;
    }

}
