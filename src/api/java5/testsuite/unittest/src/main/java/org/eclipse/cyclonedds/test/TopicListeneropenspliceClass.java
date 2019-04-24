package org.eclipse.cyclonedds.test;

import java.util.concurrent.Semaphore;

import org.omg.dds.core.event.InconsistentTopicEvent;
import org.opensplice.dds.core.event.AllDataDisposedEvent;
import org.opensplice.dds.topic.TopicAdapter;

import Test.Msg;

public class TopicListeneropenspliceClass extends TopicAdapter<Msg> {

    public long                        allDataDisposedCounter   = 0;
    public AllDataDisposedEvent<Msg>   addStatus                = null;
    public long                        inconsistentTopicCounter = 0;
    public InconsistentTopicEvent<Msg> itStatus                 = null;
    public Semaphore                   sem                      = new Semaphore(0);

    public TopicListeneropenspliceClass(Semaphore s) {
        super();
        sem = s;
    }

    public TopicListeneropenspliceClass() {
        super();
    }

    @Override
    public void onInconsistentTopic(InconsistentTopicEvent<Msg> status) {
        super.onInconsistentTopic(status);
        inconsistentTopicCounter++;
        itStatus = status;
        sem.release();
    }

    @Override
    public void onAllDataDisposed(AllDataDisposedEvent<Msg> status) {
        super.onAllDataDisposed(status);
        allDataDisposedCounter++;
        addStatus = status;
        sem.release();
    }

    public void reset() {
        allDataDisposedCounter = 0;
        addStatus = null;
        inconsistentTopicCounter = 0;
        itStatus = null;
    }

}
