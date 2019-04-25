package org.eclipse.cyclonedds.test;

import java.util.concurrent.Semaphore;

import org.omg.dds.core.event.LivelinessLostEvent;
import org.omg.dds.core.event.OfferedDeadlineMissedEvent;
import org.omg.dds.core.event.OfferedIncompatibleQosEvent;
import org.omg.dds.core.event.PublicationMatchedEvent;
import org.omg.dds.pub.DataWriterAdapter;

import Test.Msg;

public class DataWriterListenerClass extends DataWriterAdapter<Msg>
{
    public OfferedDeadlineMissedEvent<Msg>  odmStatus                      = null;
    public OfferedIncompatibleQosEvent<Msg> oiqStatus                      = null;
    public PublicationMatchedEvent<Msg>     pmStatus                       = null;
    public LivelinessLostEvent<Msg>         llStatus                        = null;
    public long                             offeredDeadlineMissedCounter  = 0;
    public long                             offeredIncompatibleQosCounter = 0;
    public long                             publicationMatchCounter         = 0;
    public long                             livelinessLostCounter           = 0;
    public Semaphore                        sem                             = new Semaphore(0);

    public DataWriterListenerClass(Semaphore s) {
        super();
        sem = s;
    }

    public DataWriterListenerClass() {
        super();
    }

    @Override
    public void onLivelinessLost(LivelinessLostEvent<Msg> status) {
        super.onLivelinessLost(status);
        livelinessLostCounter++;
        llStatus = status;
        sem.release();

    }

    @Override
    public void onOfferedDeadlineMissed(OfferedDeadlineMissedEvent<Msg> status) {
        super.onOfferedDeadlineMissed(status);
        offeredDeadlineMissedCounter++;
        odmStatus = status;
        sem.release();

    }

    @Override
    public void onOfferedIncompatibleQos(OfferedIncompatibleQosEvent<Msg> status) {
        super.onOfferedIncompatibleQos(status);
        offeredIncompatibleQosCounter++;
        oiqStatus = status;
        sem.release();

    }

    @Override
    public void onPublicationMatched(PublicationMatchedEvent<Msg> status) {
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


