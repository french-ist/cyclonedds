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
package org.eclipse.cyclonedds.core;

import java.util.concurrent.TimeoutException;

import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_listener_t;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_qos_t;
import org.eclipse.cyclonedds.ddsc.dds.DdscLibrary.dds_topic_descriptor_t;
import org.eclipse.cyclonedds.domain.DomainParticipantImpl;
import org.eclipse.cyclonedds.topic.TopicImpl;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.Time;
import org.omg.dds.domain.DomainParticipantListener;
import org.omg.dds.domain.DomainParticipantQos;
import org.omg.dds.pub.DataWriterListener;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;

import com.sun.jna.ptr.PointerByReference;

//TODO FRCYC import ErrorInfo;

public class Utilities {

    public static void checkReturnCode(int retCode,
            CycloneServiceEnvironment environment, String message) {
        try {
            checkReturnCode(retCode, environment, message, false);
        } catch (TimeOutExceptionImpl t) {
            throw new DDSExceptionImpl(environment,
                    "Internal error: TimeOutException caught in unexpected location.");
        }
    }

    int DDS_CHECK_REPORT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT;
    int DDS_CHECK_EXIT = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT;
    private static void checkReturnCode(int retCode,
            CycloneServiceEnvironment environment, String message,
            boolean withTimeOut) throws TimeOutExceptionImpl {
        /* TODO FRCYC
        switch (retCode) {
        case RETCODE_PRECONDITION_NOT_MET.value:
            throw new PreconditionNotMetExceptionImpl(environment,
                    getErrorMessage(retCode, message));
        case RETCODE_OUT_OF_RESOURCES.value:
            throw new OutOfResourcesExceptionImpl(environment, getErrorMessage(
                    retCode, message));
        case RETCODE_ALREADY_DELETED.value:
            throw new AlreadyClosedExceptionImpl(environment, getErrorMessage(
                    retCode, message));
        case RETCODE_BAD_PARAMETER.value:
            throw new IllegalArgumentExceptionImpl(environment,
                    getErrorMessage(retCode, message));
        case RETCODE_ERROR.value:
            throw new DDSExceptionImpl(environment, getErrorMessage(retCode,
                    message));
        case RETCODE_ILLEGAL_OPERATION.value:
            throw new IllegalOperationExceptionImpl(environment,
                    getErrorMessage(retCode, message));
        case RETCODE_IMMUTABLE_POLICY.value:
            throw new ImmutablePolicyExceptionImpl(environment,
                    getErrorMessage(retCode, message));
        case RETCODE_INCONSISTENT_POLICY.value:
            throw new InconsistentPolicyExceptionImpl(environment,
                    getErrorMessage(retCode, message));
        case RETCODE_NOT_ENABLED.value:
            throw new NotEnabledExceptionImpl(environment, getErrorMessage(
                    retCode, message));
        case RETCODE_UNSUPPORTED.value:
            throw new UnsupportedOperationExceptionImpl(environment,
                    getErrorMessage(retCode, message));
        case RETCODE_TIMEOUT.value:
            if (withTimeOut) {
                if (retCode == RETCODE_TIMEOUT.value) {
                    throw new TimeOutExceptionImpl(environment,
                            getErrorMessage(retCode, message));
                }
            }
        case RETCODE_OK.value:
        case RETCODE_NO_DATA.value:
        default:
            break;
        }
        */
    	
    	
    	byte ret = org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.dds_err_check(retCode,
    			org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_REPORT | org.eclipse.cyclonedds.ddsc.dds_public_error.DdscLibrary.DDS_CHECK_EXIT,
    			"NIY");
    	if(ret != 1){
    		try {
    			throw new Exception("dds_error_check" + message);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }

    /* TODO FRCYC
    private static String getDetails(ErrorInfo errorInfo, String message) {
        String result = "";
        StringHolder messageHolder = new StringHolder();

        errorInfo.get_message(messageHolder);

        if (messageHolder.value != null) {
            if (message != null) {
                result += message;
                result += "(" + messageHolder.value + ")";
            } else {
                result += messageHolder.value;
            }
        } else if (message != null) {
            result += message;
        }
        errorInfo.get_location(messageHolder);

        if (messageHolder.value != null) {
            result += " at " + messageHolder.value;
        }
        errorInfo.get_source_line(messageHolder);

        if (messageHolder.value != null) {
            result += " (" + messageHolder.value + ")";
        }
        errorInfo.get_stack_trace(messageHolder);

        if (messageHolder.value != null) {
            result += "\nStack trace:\n" + messageHolder.value;
        }
        return result;
    }
    */

    /*
     * TODO FRCYC
    private static String getErrorMessage(int retCode, String message) {
        String output;
        ErrorInfo errorInfo = new ErrorInfo();
        int result = errorInfo.update();

        switch (result) {
        case RETCODE_NO_DATA.value:
            output = message;
            break;
        case RETCODE_OK.value:
            output = getDetails(errorInfo, message);
            break;
        default:
            if (message != null) {
                output = " Unable to get extra error information due to internal error.("
                    + message + ")";
            } else {
                output = " Unable to get extra error information due to internal error.";
            }
            break;
        }
        return output;
    }
    */

    public static String getOsplExceptionStack(Exception ex,
            StackTraceElement[] stack) {
        StringBuffer result = new StringBuffer();
        int startIndex = 0;

        result.append(ex.getClass().getSuperclass().getName() + ": "
                + ex.getMessage() + "\n");

        while ((stack.length > startIndex)
                && (Utilities.class.getName().equals(stack[startIndex]
                        .getClassName()))) {
            startIndex++;
        }
        for (int i = startIndex; i < stack.length; i++) {
            result.append("\tat ");
            result.append(stack[i].getClassName());
            result.append(".");
            result.append(stack[i].getMethodName());
            result.append(" (");
            result.append(stack[i].getFileName());
            result.append(":");
            result.append(stack[i].getLineNumber());
            result.append(")\n");
        }
        return result.toString();
    }

    public static void checkReturnCodeWithTimeout(int retCode,
            CycloneServiceEnvironment environment, String message)
            throws TimeoutException {
        checkReturnCode(retCode, environment, message, true);
    }

    
    public static void throwLastErrorException(
            CycloneServiceEnvironment environment) {
        /* TODO FRCYC
    	String message;
        int code;
        ErrorInfo errorInfo = new ErrorInfo();
        int result = errorInfo.update();

        switch (result) {
        case RETCODE_NO_DATA.value:
            message = "";
            code = RETCODE_ERROR.value;
            break;
        case RETCODE_OK.value:
            ReturnCodeHolder errorHolder = new ReturnCodeHolder();
            errorInfo.get_code(errorHolder);
            code = errorHolder.value;
            message = getDetails(errorInfo, null);
            break;
        default:
            message = "Unable to get extra error information due to internal error.";
            code = RETCODE_ERROR.value;
        }

        switch (code) {
        case RETCODE_PRECONDITION_NOT_MET.value:
            throw new PreconditionNotMetExceptionImpl(environment, message);
        case RETCODE_OUT_OF_RESOURCES.value:
            throw new OutOfResourcesExceptionImpl(environment, message);
        case RETCODE_ALREADY_DELETED.value:
            throw new AlreadyClosedExceptionImpl(environment, message);
        case RETCODE_BAD_PARAMETER.value:
            throw new IllegalArgumentExceptionImpl(environment, message);
        case RETCODE_ILLEGAL_OPERATION.value:
            throw new IllegalOperationExceptionImpl(environment, message);
        case RETCODE_IMMUTABLE_POLICY.value:
            throw new ImmutablePolicyExceptionImpl(environment, message);
        case RETCODE_INCONSISTENT_POLICY.value:
            throw new InconsistentPolicyExceptionImpl(environment, message);
        case RETCODE_NOT_ENABLED.value:
            throw new NotEnabledExceptionImpl(environment, message);
        case RETCODE_UNSUPPORTED.value:
            throw new UnsupportedOperationExceptionImpl(environment, message);
        case RETCODE_ERROR.value:
        default:
            throw new DDSExceptionImpl(environment, message);

        }
        */

    }
    
    /* TODO FRCYC
    public static Duration_t convert(OsplServiceEnvironment environment,
            Duration d) {
        if (d == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Illegal Duration provided (null).");
        }
        try {
            return ((DurationImpl) d).convert();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Usage of non-OpenSplice Duration implementation is not supported.");
        }
    }

    public static Duration convert(OsplServiceEnvironment env, Duration_t d) {
        return new DurationImpl(env, d.sec, d.nanosec);
    }
    */
    

    public static long convert(CycloneServiceEnvironment environment,
            InstanceHandle h) {
        if (h == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Illegal InstanceHandle provided (null).");
        }
        try {
            return ((InstanceHandleImpl) h).getValue();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Usage of non-OpenSplice InstanceHandle implementation is not supported.");
        }
    }

    public static InstanceHandle convert(CycloneServiceEnvironment env, long handle) {
        return new InstanceHandleImpl(env, handle);
    }

	public static Object convert(CycloneServiceEnvironment environment, Time sourceTimestamp) {
		// TODO FRCYC Auto-generated method stub
		return null;
	}

	public static dds_qos_t convert(DomainParticipantQos qos) {
		// TODO FRCYC
		return null;
	}

	public static dds_listener_t convert(DomainParticipantListener listener) {
		// TODO FRCYC
		return null;
	}

	public static void checkReturnCode(PointerByReference rc, CycloneServiceEnvironment environment, String message) {
		// TODO FRCYC
	}

	public static dds_qos_t convert(CycloneServiceEnvironment environment, TopicQos qos) {
		// TODO FRCYC
		return null;
	}

	public static dds_listener_t convert(CycloneServiceEnvironment environment, TopicListener<?> listener) {
		// TODO FRCYC
		return null;
	}

	public static dds_topic_descriptor_t convert(CycloneServiceEnvironment environment, TopicImpl<?> topicImpl) {
		// TODO Auto-generated method stub
		return null;
	}

	public static PointerByReference convert(PublisherQos qos) {
		// TODO Auto-generated method stub
		return null;
	}

	public static dds_listener_t convert(DomainParticipantImpl parent) {
		// TODO Auto-generated method stub
		return null;
	}

	public static PointerByReference convert(DataWriterQos qos) {
		// TODO Auto-generated method stub
		return null;
	}


	public static <TYPE> dds_listener_t convert(DataWriterListener<TYPE> listener) {
		// TODO Auto-generated method stub
		return null;
	}

	public static PointerByReference convert(SubscriberQos qos) {
		// TODO Auto-generated method stub
		return null;
	}

	public static PointerByReference convert(DataReaderQos qos) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <TYPE> dds_listener_t convert(DataReaderListener<TYPE> listener) {
		// TODO Auto-generated method stub
		return null;
	}

    /*
     * TODO FRCYC
    public static Time_t convert(OsplServiceEnvironment environment, Time t) {
        if (t == null) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Illegal Time provided (null).");
        }
        try {
            return ((ModifiableTimeImpl) t).convert();
        } catch (ClassCastException e) {
            throw new IllegalArgumentExceptionImpl(environment,
                    "Usage of non-OpenSplice Time implementation is not supported.");
        }
    }
     

    public static Time convert(OsplServiceEnvironment env, Time_t t) {
        return new TimeImpl(env, t.sec, t.nanosec);
    }
    */
    
}
