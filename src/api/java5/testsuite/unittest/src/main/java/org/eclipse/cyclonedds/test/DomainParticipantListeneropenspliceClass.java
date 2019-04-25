package org.eclipse.cyclonedds.test;

import java.util.concurrent.Semaphore;

import org.omg.dds.core.event.InconsistentTopicEvent;
import org.opensplice.dds.core.event.AllDataDisposedEvent;
import org.opensplice.dds.domain.DomainParticipantAdapter;

public class DomainParticipantListeneropenspliceClass extends DomainParticipantAdapter {

    public long                      allDataDisposedCounter   = 0;
    public AllDataDisposedEvent<?>   addStatus                = null;
    public long                      inconsistentTopicCounter = 0;
    public InconsistentTopicEvent<?> itStatus                 = null;
    public Semaphore                 sem                      = new Semaphore(0);

    public DomainParticipantListeneropenspliceClass(Semaphore s) {
        super();
        sem = s;
    }

    public DomainParticipantListeneropenspliceClass() {
        super();
    }

    @Override
    public void onAllDataDisposed(AllDataDisposedEvent<?> status) {
        super.onAllDataDisposed(status);
        allDataDisposedCounter++;
        addStatus = status;
        sem.release();

    }

    @Override
    public void onInconsistentTopic(InconsistentTopicEvent<?> status) {
        super.onInconsistentTopic(status);
        inconsistentTopicCounter++;
        itStatus = status;
        sem.release();
    }

    public void reset() {
        allDataDisposedCounter = 0;
        inconsistentTopicCounter = 0;
        addStatus = null;
        itStatus = null;
    }

}
