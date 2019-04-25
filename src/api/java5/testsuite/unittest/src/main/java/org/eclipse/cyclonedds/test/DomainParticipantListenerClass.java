package org.eclipse.cyclonedds.test;

import java.util.concurrent.Semaphore;

import org.omg.dds.core.event.DataAvailableEvent;
import org.omg.dds.core.event.DataOnReadersEvent;
import org.omg.dds.core.event.LivelinessChangedEvent;
import org.omg.dds.core.event.LivelinessLostEvent;
import org.omg.dds.core.event.OfferedDeadlineMissedEvent;
import org.omg.dds.core.event.OfferedIncompatibleQosEvent;
import org.omg.dds.core.event.PublicationMatchedEvent;
import org.omg.dds.core.event.RequestedDeadlineMissedEvent;
import org.omg.dds.core.event.RequestedIncompatibleQosEvent;
import org.omg.dds.core.event.SampleLostEvent;
import org.omg.dds.core.event.SampleRejectedEvent;
import org.omg.dds.core.event.SubscriptionMatchedEvent;
import org.omg.dds.domain.DomainParticipantAdapter;

public class DomainParticipantListenerClass extends DomainParticipantAdapter {

    public long      dataAvailableCounter            = 0;
    public long      requestedDeadlineMissedCounter  = 0;
    public long      requestedIncompatibleQosCounter = 0;
    public long      sampleRejectCounter             = 0;
    public long      livelinessChangedCounter        = 0;
    public long      subscriptionMatchCounter        = 0;
    public long      sampleLostCounter               = 0;
    public long      dataOnReadersCounter            = 0;
    public long      offeredDeadlineMissedCounter    = 0;
    public long      offeredIncompatibleQosCounter   = 0;
    public long      publicationMatchCounter         = 0;
    public long      livelinessLostCounter           = 0;
    public OfferedDeadlineMissedEvent<?>    odmStatus                       = null;
    public OfferedIncompatibleQosEvent<?>   oiqStatus                       = null;
    public PublicationMatchedEvent<?>       pmStatus                        = null;
    public LivelinessLostEvent<?>           llStatus                        = null;
    public DataAvailableEvent<?>            daStatus                        = null;
    public LivelinessChangedEvent<?>        llcStatus                       = null;
    public RequestedDeadlineMissedEvent<?>  rdmStatus                       = null;
    public RequestedIncompatibleQosEvent<?> riqStatus                       = null;
    public SampleLostEvent<?>               slStatus                        = null;
    public SampleRejectedEvent<?>           srStatus                        = null;
    public SubscriptionMatchedEvent<?>      smStatus                        = null;
    public DataOnReadersEvent               dorStatus                       = null;

    public Semaphore sem                             = new Semaphore(0);

    public DomainParticipantListenerClass(Semaphore s) {
        super();
        sem = s;
    }

    public DomainParticipantListenerClass() {
        super();
    }

    @Override
    public void onDataAvailable(DataAvailableEvent<?> status) {
        super.onDataAvailable(status);
        dataAvailableCounter++;
        daStatus = status;
        sem.release();

    }

    @Override
    public void onLivelinessChanged(LivelinessChangedEvent<?> status) {
        super.onLivelinessChanged(status);
        livelinessChangedCounter++;
        llcStatus = status;
        sem.release();
    }

    @Override
    public void onRequestedDeadlineMissed(RequestedDeadlineMissedEvent<?> status) {
        super.onRequestedDeadlineMissed(status);
        requestedDeadlineMissedCounter++;
        rdmStatus = status;
        sem.release();
    }

    @Override
    public void onRequestedIncompatibleQos(RequestedIncompatibleQosEvent<?> status) {
        super.onRequestedIncompatibleQos(status);
        requestedIncompatibleQosCounter++;
        riqStatus = status;
        sem.release();
    }

    @Override
    public void onSampleLost(SampleLostEvent<?> status) {
        super.onSampleLost(status);
        sampleLostCounter++;
        slStatus = status;
        sem.release();
    }

    @Override
    public void onSampleRejected(SampleRejectedEvent<?> status) {
        super.onSampleRejected(status);
        sampleRejectCounter++;
        srStatus = status;
        sem.release();
    }

    @Override
    public void onSubscriptionMatched(SubscriptionMatchedEvent<?> status) {
        super.onSubscriptionMatched(status);
        subscriptionMatchCounter++;
        smStatus = status;
        sem.release();
    }


    @Override
    public void onDataOnReaders(DataOnReadersEvent status) {
        super.onDataOnReaders(status);
        dataOnReadersCounter++;
        dorStatus = status;
        sem.release();

    }

    @Override
    public void onLivelinessLost(LivelinessLostEvent<?> status) {
        super.onLivelinessLost(status);
        livelinessLostCounter++;
        llStatus = status;
        sem.release();

    }

    @Override
    public void onOfferedDeadlineMissed(OfferedDeadlineMissedEvent<?> status) {
        super.onOfferedDeadlineMissed(status);
        offeredDeadlineMissedCounter++;
        odmStatus = status;
        sem.release();

    }

    @Override
    public void onOfferedIncompatibleQos(OfferedIncompatibleQosEvent<?> status) {
        super.onOfferedIncompatibleQos(status);
        offeredIncompatibleQosCounter++;
        oiqStatus = status;
        sem.release();

    }

    @Override
    public void onPublicationMatched(PublicationMatchedEvent<?> status) {
        super.onPublicationMatched(status);
        publicationMatchCounter++;
        pmStatus = status;
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
        dataOnReadersCounter = 0;
        offeredDeadlineMissedCounter = 0;
        offeredIncompatibleQosCounter = 0;
        publicationMatchCounter = 0;
        livelinessLostCounter = 0;
        odmStatus = null;
        oiqStatus = null;
        pmStatus = null;
        llStatus = null;
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


