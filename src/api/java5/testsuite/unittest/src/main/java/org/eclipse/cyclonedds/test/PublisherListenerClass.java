package org.eclipse.cyclonedds.test;

import java.util.concurrent.Semaphore;

import org.omg.dds.core.event.LivelinessLostEvent;
import org.omg.dds.core.event.OfferedDeadlineMissedEvent;
import org.omg.dds.core.event.OfferedIncompatibleQosEvent;
import org.omg.dds.core.event.PublicationMatchedEvent;
import org.omg.dds.pub.PublisherAdapter;

public class PublisherListenerClass extends PublisherAdapter {

    public OfferedDeadlineMissedEvent<?>  odmStatus                     = null;
    public OfferedIncompatibleQosEvent<?> oiqStatus                     = null;
    public PublicationMatchedEvent<?>     pmStatus                      = null;
    public LivelinessLostEvent<?>         llStatus                      = null;
    public long                           offeredDeadlineMissedCounter  = 0;
    public long                           offeredIncompatibleQosCounter = 0;
    public long                           publicationMatchCounter       = 0;
    public long                           livelinessLostCounter         = 0;
    public Semaphore                      sem                           = new Semaphore(0);

    public PublisherListenerClass(Semaphore s) {
        super();
        sem = s;
    }

    public PublisherListenerClass() {
        super();
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
        offeredDeadlineMissedCounter = 0;
        odmStatus = null;
        offeredIncompatibleQosCounter = 0;
        oiqStatus = null;
        publicationMatchCounter = 0;
        pmStatus = null;
        livelinessLostCounter = 0;
        llStatus = null;

    }

}
