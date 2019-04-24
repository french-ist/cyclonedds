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

import Test.Msg;

public class DataReaderListenerClass extends DataReaderAdapter<Msg>
{
    public long      dataAvailableCounter            = 0;
    public long      requestedDeadlineMissedCounter  = 0;
    public long      requestedIncompatibleQosCounter = 0;
    public long      sampleRejectCounter             = 0;
    public long      livelinessChangedCounter        = 0;
    public long      subscriptionMatchCounter        = 0;
    public long      sampleLostCounter               = 0;
    public DataAvailableEvent<Msg>            daStatus                        = null;
    public LivelinessChangedEvent<Msg>        llcStatus                       = null;
    public RequestedDeadlineMissedEvent<Msg>  rdmStatus                       = null;
    public RequestedIncompatibleQosEvent<Msg> riqStatus                       = null;
    public SampleLostEvent<Msg>               slStatus                        = null;
    public SampleRejectedEvent<Msg>           srStatus                        = null;
    public SubscriptionMatchedEvent<Msg>      smStatus                        = null;
    public DataOnReadersEvent                 dorStatus                       = null;

    public Semaphore sem                             = new Semaphore(0);

    public DataReaderListenerClass(Semaphore s) {
        super();
        sem = s;
    }

    public DataReaderListenerClass() {
        super();
    }

    @Override
    public void onDataAvailable(DataAvailableEvent<Msg> status) {
        super.onDataAvailable(status);
        dataAvailableCounter++;
        daStatus = status;
        sem.release();
    }

    @Override
    public void onLivelinessChanged(LivelinessChangedEvent<Msg> status) {
        super.onLivelinessChanged(status);
        livelinessChangedCounter++;
        llcStatus = status;
        sem.release();
    }

    @Override
    public void onRequestedDeadlineMissed(RequestedDeadlineMissedEvent<Msg> status) {
        super.onRequestedDeadlineMissed(status);
        requestedDeadlineMissedCounter++;
        rdmStatus = status;
        sem.release();
    }

    @Override
    public void onRequestedIncompatibleQos(RequestedIncompatibleQosEvent<Msg> status) {
        super.onRequestedIncompatibleQos(status);
        requestedIncompatibleQosCounter++;
        riqStatus = status;
        sem.release();
    }

    @Override
    public void onSampleLost(SampleLostEvent<Msg> status) {
        super.onSampleLost(status);
        sampleLostCounter++;
        slStatus = status;
        sem.release();
    }

    @Override
    public void onSampleRejected(SampleRejectedEvent<Msg> status) {
        super.onSampleRejected(status);
        sampleRejectCounter++;
        srStatus = status;
        sem.release();
    }

    @Override
    public void onSubscriptionMatched(SubscriptionMatchedEvent<Msg> status) {
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


