package org.eclipse.cyclonedds.test;

import java.util.concurrent.Semaphore;

import org.omg.dds.core.event.InconsistentTopicEvent;
import org.omg.dds.topic.TopicAdapter;

import Test.Msg;

public class TopicListenerClass extends TopicAdapter<Msg>
{

    public long      inconsistentTopicCounter = 0;
    public InconsistentTopicEvent<Msg> itStatus                 = null;

    public Semaphore sem                      = new Semaphore(0);

    public TopicListenerClass(Semaphore s) {
        super();
        sem = s;
    }

    public TopicListenerClass() {
        super();
    }

    @Override
    public void onInconsistentTopic(InconsistentTopicEvent<Msg> status) {
        super.onInconsistentTopic(status);
        inconsistentTopicCounter++;
        itStatus = status;
        sem.release();
    }

    public void reset() {
        inconsistentTopicCounter = 0;
        itStatus = null;
    }

}


