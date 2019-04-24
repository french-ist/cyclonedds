package org.eclipse.cyclonedds.test;

import java.util.concurrent.Semaphore;

import org.omg.dds.core.event.DataAvailableEvent;
import org.omg.dds.core.event.DataOnReadersEvent;
import org.omg.dds.core.event.LivelinessChangedEvent;
import org.omg.dds.core.event.RequestedDeadlineMissedEvent;
import org.omg.dds.core.event.RequestedIncompatibleQosEvent;
import org.omg.dds.core.event.SampleLostEvent;
import org.omg.dds.core.event.SampleRejectedEvent;
import org.omg.dds.core.event.SubscriptionMatchedEvent;
import org.omg.dds.sub.DataReaderAdapter;

import proto.Proto.ProtoMsg;

public class DataReaderListenerprotobufClass extends DataReaderAdapter<ProtoMsg>
{
    public long      dataAvailableCounter            = 0;
    public long      requestedDeadlineMissedCounter  = 0;
    public long      requestedIncompatibleQosCounter = 0;
    public long      sampleRejectCounter             = 0;
    public long      livelinessChangedCounter        = 0;
    public long      subscriptionMatchCounter        = 0;
    public long      sampleLostCounter               = 0;
    public DataAvailableEvent<ProtoMsg>            daStatus                        = null;
    public LivelinessChangedEvent<ProtoMsg>        llcStatus                       = null;
    public RequestedDeadlineMissedEvent<ProtoMsg>  rdmStatus                       = null;
    public RequestedIncompatibleQosEvent<ProtoMsg> riqStatus                       = null;
    public SampleLostEvent<ProtoMsg>               slStatus                        = null;
    public SampleRejectedEvent<ProtoMsg>           srStatus                        = null;
    public SubscriptionMatchedEvent<ProtoMsg>      smStatus                        = null;
    public DataOnReadersEvent                 dorStatus                       = null;

    public Semaphore sem                             = new Semaphore(0);

    public DataReaderListenerprotobufClass(Semaphore s) {
        super();
        sem = s;
    }

    public DataReaderListenerprotobufClass() {
        super();
    }

    @Override
    public void onDataAvailable(DataAvailableEvent<ProtoMsg> status) {
        super.onDataAvailable(status);
        dataAvailableCounter++;
        daStatus = status;
        sem.release();
    }

    @Override
    public void onLivelinessChanged(LivelinessChangedEvent<ProtoMsg> status) {
        super.onLivelinessChanged(status);
        livelinessChangedCounter++;
        llcStatus = status;
        sem.release();
    }

    @Override
    public void onRequestedDeadlineMissed(RequestedDeadlineMissedEvent<ProtoMsg> status) {
        super.onRequestedDeadlineMissed(status);
        requestedDeadlineMissedCounter++;
        rdmStatus = status;
        sem.release();
    }

    @Override
    public void onRequestedIncompatibleQos(RequestedIncompatibleQosEvent<ProtoMsg> status) {
        super.onRequestedIncompatibleQos(status);
        requestedIncompatibleQosCounter++;
        riqStatus = status;
        sem.release();
    }

    @Override
    public void onSampleLost(SampleLostEvent<ProtoMsg> status) {
        super.onSampleLost(status);
        sampleLostCounter++;
        slStatus = status;
        sem.release();
    }

    @Override
    public void onSampleRejected(SampleRejectedEvent<ProtoMsg> status) {
        super.onSampleRejected(status);
        sampleRejectCounter++;
        srStatus = status;
        sem.release();
    }

    @Override
    public void onSubscriptionMatched(SubscriptionMatchedEvent<ProtoMsg> status) {
        super.onSubscriptionMatched(status);
        subscriptionMatchCounter++;
        smStatus = status;
        sem.release();
    }

    public void reset() {
        dataAvailableCounter = 0;
        requestedDeadlineMissedCounter = 0;
        requestedIncompatibleQosCounter = 0;
        sampleRejectCounter = 0;
        livelinessChangedCounter = 0;
        subscriptionMatchCounter = 0;
        sampleLostCounter = 0;
        daStatus = null;
        llcStatus = null;
        rdmStatus = null;
        riqStatus = null;
        slStatus = null;
        srStatus = null;
        smStatus = null;
        dorStatus = null;
    }
}


