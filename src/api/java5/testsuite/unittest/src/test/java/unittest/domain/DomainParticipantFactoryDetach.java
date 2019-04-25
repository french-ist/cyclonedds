package unittest.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.omg.dds.core.AlreadyClosedException;
import org.omg.dds.core.Condition;
import org.omg.dds.core.Duration;
import org.omg.dds.core.PreconditionNotMetException;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.core.WaitSet;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.ReadCondition;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Subscriber.DataState;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.TypeSupport;
//import org.opensplice.dds.core.OsplServiceEnvironment;
//import org.opensplice.dds.core.status.AllDataDisposedStatus;

import Test.Msg;

import org.eclipse.cyclonedds.test.AbstractUtilities;
import org.eclipse.cyclonedds.test.OpenSpliceSHMUtilities;

public class DomainParticipantFactoryDetach {

    private final static int  DOMAIN_ID   = 123;

    private static AbstractUtilities util = AbstractUtilities.getInstance(DomainParticipantFactoryDetach.class);

    private static final int TC_RESULT_OK            =   0;
    private static final int TC_RESULT_DETACH_FAILED =   1;
    private static final int TC_RESULT_NOT_BLOCKED   =   2;
    private static final int TC_RESULT_ERROR         = 255;

    private enum ThreadStatus {
        UNDEF,
        RUNNING,
        STOPPED
    };

    public static void secSleep(long t) {
        try {
            Thread.sleep(t*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void msecSleep(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class IntHolder {
        public int value;
    }

    public static String getPid(String testcase) throws IOException, InterruptedException {
        String pid = null;

        Vector<String> commands = new Vector<String>();
        commands.add("jps");
        commands.add("-v");

        ProcessBuilder pb = new ProcessBuilder(commands);

        Process pr = pb.start();
        pr.waitFor();
        if (pr.exitValue() == 0) {
            BufferedReader outReader = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String pattern = "(\\d+).*-Dtestcase=" + testcase + ".*";

            Pattern r = Pattern.compile(pattern);

            String line = outReader.readLine();
            while ((line != null) && (pid == null)) {
                Matcher m = r.matcher(line);
                if (m.find()) {
                    pid = m.group(1);
                } else {
                    line = outReader.readLine();
                }
            }
        }
        return pid;
    }

    public static class TestException extends Exception {

        public TestException() {
            super();
        }

        public TestException(String message) {
            super(message);
        }

        public TestException(String message, Throwable cause) {
            super(message, cause);
        }

        public TestException(Throwable cause) {
            super(cause);
        }
    }


    public abstract class ProcessManager {
        protected Process process = null;

        public abstract void start() throws TestException;
        public abstract void stop();

        public int getStatus() {
            int result = -1;
            try {
                result = process.waitFor();
            } catch (InterruptedException e) {

            }
            return result;
        }

        public boolean hasTerminated(IntHolder exitValue, int timeout) {
           boolean terminated = false;

            do {
                try {
                    exitValue.value = process.exitValue();
                    terminated = true;
                } catch (IllegalThreadStateException e) {
                    secSleep(1);
                }
            } while (!terminated && (timeout-- > 0));

            return terminated;
        }

        public boolean isRunning(int timeout) {
            boolean running = true;

            do {
                try {
                    int r = process.exitValue();
                    running = false;
                } catch (IllegalThreadStateException e) {
                    secSleep(1);
                }
            } while (running && (timeout-- > 0));

            return running;
        }
    }

    public class SplicedProcess extends ProcessManager {

        public void start() throws TestException {
            String[] cmd = new String[3];
            cmd[0] = "ospl";
            cmd[1] = "-f";
            cmd[2] = "start";
            System.out.println("start spliced");
            try {
                process = Runtime.getRuntime().exec(cmd);
            } catch (Exception e) {
                throw new TestException(e);
            }
            if (isRunning(5)) {
                System.out.println("spliced started");
            } else {
                throw new TestException("spliced failed to start");
            }
        }

        public void stop() {
            String[] cmd = new String[2];
            cmd[0] = "ospl";
            cmd[1] = "stop";
            try {
                Process process = Runtime.getRuntime().exec(cmd);
                if (isRunning(10)) {
                    System.out.println("spliced failed to stop");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class TestProcess extends ProcessManager {

        private String name;
        private String pid;

        public TestProcess(String name) {
            this.name = name;
        }

        public void start() throws TestException {

            try {
                String path = DomainParticipantFactoryDetach.class.getProtectionDomain().getCodeSource().getLocation().getPath();

                path = URLDecoder.decode(path, "utf-8");
                path = new File(path).getPath();

                String endorsedDirs = System.getProperty("java.endorsed.dirs");

                List<String> args = new ArrayList<String>();
                args.add("java");
                args.add("-Dtestcase=" + name);
                args.add("-Xcheck:jni");
                if (endorsedDirs != null) {
                    args.add("-Djava.endorsed.dirs=" + endorsedDirs);
                }
                args.add("-DJAVA5PSM_MODE=" + System.getProperty("JAVA5PSM_MODE"));
                //args.add("-DJAVA5PSM_MODE=opensplice-shm");
                args.add("-DJAVA5PSM_SERVICE_ENV=" + System.getProperty("JAVA5PSM_SERVICE_ENV"));

                String classpath = System.getProperty("java.class.path");

                args.add("-cp");
                args.add(classpath);
                args.add("unittest.domain.DomainParticipantFactoryDetach");
                args.add(name);

                ProcessBuilder pb = new ProcessBuilder(args);
                process = pb.start();

                BackgroundPrinter RedirecterOutput = new BackgroundPrinter(process.getInputStream());
                RedirecterOutput.start();

            } catch (Exception e) {
                throw new TestException(e);
            }

            if (!isRunning(0)) {
                throw new TestException("Failed to start process " + name);
            }

            try {
                pid = getPid(name);
            } catch (Exception e) {
                throw new TestException(e);
            }

            if (pid == null) {
                throw new TestException("Failed to retrieve pid of process " + name);
            }

            System.out.println("Started process " + name + "(pid=" + pid + ")");
        }

        public void stop() {
            System.out.println("Killing process " + name + "(pid=" + pid + ")");
            try {
                List<String> commands = new ArrayList<String>();
                commands.add("kill");
                commands.add("-9");
                commands.add(pid);

                ProcessBuilder pb = new ProcessBuilder(commands);
                Process pr = pb.start();
                pr.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private class BackgroundPrinter extends Thread {
            private InputStream in;

            public BackgroundPrinter(InputStream in) {
                this.in = in;
            }

            public void run() {
                try {
                    long start = System.currentTimeMillis();
                    long end = start + 180*1000; // 180 seconds * 1000 ms/sec
                    while (System.currentTimeMillis() < end) {
                        int c = in.read();
                        if (c == -1) break;
                        System.out.write((char)c);
                        System.out.flush();
                    }
                } catch (IOException e) {
                }
                System.out.println("Finished background printer");
            }

            public void close() {
                try {
                    this.in.close();
                } catch (Exception e) {
                    System.out.println("Closing background stream for launched process caused exception.");
                }
            }
        }
    }

    public static class TestApplication {
        private ServiceEnvironment       env;
        private DomainParticipantFactory dpf;
        private DomainParticipant        participant = null;
        private Topic<Msg>               topic       = null;
        private Publisher                publisher   = null;
        private Subscriber               subscriber  = null;
        private DataWriter<Msg>          writer      = null;
        private DataReader<Msg>          reader      = null;
        private final Properties         prop        = new Properties();
        private String                   topicName   = "tc_detachAllDomains";

        class TestThread extends Thread {
            protected boolean terminate;
            private ThreadStatus status = ThreadStatus.UNDEF;

            public TestThread() {
                terminate = false;
            }

            public synchronized ThreadStatus getStatus() {
                return this.status;
            }

            public synchronized void setStatus(ThreadStatus status) {
                this.status = status;
            }

            public synchronized boolean stopped() {
                return this.terminate;
            }

            public boolean terminate(int timeout) {
                boolean result = false;

                synchronized(this) {
                    this.terminate = true;
                }
                try {
                    join(timeout*1000);
                    result = true;
                } catch (InterruptedException e) {
                    System.out.println("join Interrupted");
                }

                return result;
            }
        };

        class WriterThread extends TestThread {

            public void run() {
                Msg data = new Msg(0, "Test");
                setStatus(ThreadStatus.RUNNING);
                try {
                    while (!stopped()) {
                        writer.write(data);
                    }
                } catch (Exception e) {
                    // Ignore
                }
                setStatus(ThreadStatus.STOPPED);
            }
        }

        class ReaderThread extends TestThread {

            private WaitSet waitset = null;
            private StatusCondition condition   = null;

            public ReaderThread(){

                condition = reader.getStatusCondition();
                condition.setEnabledStatuses(DataAvailableStatus.class);

                waitset = WaitSet.newWaitSet(env);
                waitset.attachCondition(condition);
            }

            public void run() {
                HashSet<Condition> triggeredConditions = new HashSet<Condition>();
                Duration timeout = Duration.infiniteDuration(env);
                Iterator<Sample<Msg>> takeResult = null;

                setStatus(ThreadStatus.RUNNING);
                try {
                    while (!stopped()) {
                        waitset.waitForConditions(triggeredConditions, timeout);
                        reader.take();
                    }
                } catch (Exception e) {
                    // Ignore
                }
                setStatus(ThreadStatus.STOPPED);
            }
        }

        public void init(String testcase) throws TestException {
            System.out.println("TestApplication init called for testcase " + testcase);

            String currentDir = System.getProperty("user.dir");
            System.out.println("Current dir using System:" +currentDir);

            try {
                String serviceEnvironment = System.getProperty("JAVA5PSM_SERVICE_ENV");
                System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY, serviceEnvironment);
                env = ServiceEnvironment.createInstance(DomainParticipantFactoryDetach.class.getClassLoader());
                dpf = DomainParticipantFactory.getInstance(env);
                prop.setProperty("domainid", new Integer(DOMAIN_ID).toString());
                participant = dpf.createParticipant(DOMAIN_ID);
                publisher = participant.createPublisher();
                subscriber = participant.createSubscriber();
                topic = participant.createTopic(topicName, Msg.class);
                writer = publisher.createDataWriter(topic);
                reader = subscriber.createDataReader(topic);
                System.out.println("TestApplication::init successful");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("TestApplication::init failed");
                throw new TestException(e);
            }
        }

        public int execute(boolean blockOperations, boolean deleteEntities) {

            int result = TC_RESULT_ERROR;

            System.out.println("TestApplication::execute called");

            TestApplication.TestThread writerThread = this.new WriterThread();
            TestApplication.TestThread readerThread = this.new ReaderThread();

            // start reader thread
            readerThread.start();

            // start writer thread
            writerThread.start();

            secSleep(2);

            // call detachAllDomain
  //          System.out.println("Calling detachAllDomains(" + blockOperations + ", " + deleteEntities + ")");
  //          ((org.opensplice.dds.domain.DomainParticipantFactory)dpf).detachAllDomains(blockOperations, deleteEntities);
            result = TC_RESULT_OK;
            // When blocking wait some time and check the process counters
            if (blockOperations) {
                msecSleep(200);
                if (readerThread.getStatus() != ThreadStatus.RUNNING) {
                    System.out.println("Reader not blocked");
                    result = TC_RESULT_NOT_BLOCKED;
                }
                if (writerThread.getStatus() != ThreadStatus.RUNNING) {
                    System.out.println("Writer not blocked");
                    result = TC_RESULT_NOT_BLOCKED;
                }
            }
            if (result == TC_RESULT_OK) {
                System.out.println("Calling halt");
                Runtime.getRuntime().halt(result);
            }

            System.out.println("Stopping threads\n");

            return result;
        }

    };


    @BeforeClass
    public static void init() {
        OpenSpliceSHMUtilities u = OpenSpliceSHMUtilities.getInstance(DomainParticipantFactoryDetach.class);
        if (!u.setURIdomainId(null, DOMAIN_ID)) {
            fail("Failed to set URI");
        }
    }


    /**
     * Test method for
     * {@link org.opensplice.dds.domain.DomainParticipantFactory#detachAllDomains(true, true)}
     * .
     */
    @Test
    public void testDetachAllDomainsBlockDelete() {
        String function = "testDetachAllDomainsBlockDelete";

        IntHolder exitStatus = new IntHolder();

        SplicedProcess spliced = new SplicedProcess();

        try {
            spliced.start();
        } catch (TestException e) {
            assertTrue("Exception occured while starting spliced" + util.printException(e),
                       util.exceptionCheck(function, e, false));
        }

        TestProcess testProcess = new TestProcess("testcase_1");

        try {
            testProcess.start();
        } catch (TestException e) {
            assertTrue("Exception occured while starting TestApplication" + util.printException(e),
                        util.exceptionCheck(function, e, false));
        }

        if (testProcess.hasTerminated(exitStatus, 5)) {
            switch (exitStatus.value) {
            case TC_RESULT_OK:
                if (!spliced.isRunning(5)) {
                    assertFalse("Spliced has stopped", true);
                }
                break;
            case TC_RESULT_DETACH_FAILED:
                break;
            case TC_RESULT_NOT_BLOCKED:
                break;
            default:
                break;
            }
        } else {
            testProcess.stop();
            assertFalse("TestApplication failed to stop", true);
        }

        spliced.stop();
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.domain.DomainParticipantFactory#detachAllDomains(true, false)}
     * .
     */
    @Test
    public void testDetachAllDomainsBlockNoDelete() {
        String function = "testDetachAllDomainsBlockNoDelete";

        IntHolder exitStatus = new IntHolder();

        SplicedProcess spliced = new SplicedProcess();

        try {
            spliced.start();
        } catch (TestException e) {
            assertTrue("Exception occured while starting spliced" + util.printException(e),
                       util.exceptionCheck(function, e, false));
        }

        TestProcess testProcess = new TestProcess("testcase_2");

        try {
            testProcess.start();
        } catch (TestException e) {
            assertTrue("Exception occured while starting TestApplication" + util.printException(e),
                        util.exceptionCheck(function, e, false));
        }

        if (testProcess.hasTerminated(exitStatus, 5)) {
            switch (exitStatus.value) {
            case TC_RESULT_OK:
                if (!spliced.isRunning(5)) {
                    assertFalse("Spliced has stopped", true);
                }
                break;
            case TC_RESULT_DETACH_FAILED:
                break;
            case TC_RESULT_NOT_BLOCKED:
                break;
            default:
                break;
            }
        } else {
            testProcess.stop();
            assertFalse("TestApplication failed to stop", true);
        }

        spliced.stop();
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.domain.DomainParticipantFactory#detachAllDomains(true, false)}
     * .
     */
    @Test
    public void testDetachAllDomainsNoBlockDelete() {
        String function = "testDetachAllDomainsNoBlockDelete";

        IntHolder exitStatus = new IntHolder();

        SplicedProcess spliced = new SplicedProcess();

        try {
            spliced.start();
        } catch (TestException e) {
            assertTrue("Exception occured while starting spliced" + util.printException(e),
                       util.exceptionCheck(function, e, false));
        }

        TestProcess testProcess = new TestProcess("testcase_3");

        try {
            testProcess.start();
        } catch (TestException e) {
            assertTrue("Exception occured while starting TestApplication" + util.printException(e),
                        util.exceptionCheck(function, e, false));
        }

        if (testProcess.hasTerminated(exitStatus, 5)) {
            switch (exitStatus.value) {
            case TC_RESULT_OK:
                if (!spliced.isRunning(5)) {
                    assertFalse("Spliced has stopped", true);
                }
                break;
            case TC_RESULT_DETACH_FAILED:
                break;
            case TC_RESULT_NOT_BLOCKED:
                break;
            default:
                break;
            }
        } else {
            testProcess.stop();
            assertFalse("TestApplication failed to stop", true);
        }

        spliced.stop();
    }

    /**
     * Test method for
     * {@link org.opensplice.dds.domain.DomainParticipantFactory#detachAllDomains(false, false)}
     * .
     */
    @Test
    public void testDetachAllDomainsNoBlockNoDelete() {
        String function = "testDetachAllDomainsNoBlockNoDelete";

        IntHolder exitStatus = new IntHolder();

        SplicedProcess spliced = new SplicedProcess();

        try {
            spliced.start();
        } catch (TestException e) {
            assertTrue("Exception occured while starting spliced" + util.printException(e),
                       util.exceptionCheck(function, e, false));
        }

        TestProcess testProcess = new TestProcess("testcase_4");

        try {
            testProcess.start();
        } catch (TestException e) {
            assertTrue("Exception occured while starting TestApplication" + util.printException(e),
                        util.exceptionCheck(function, e, false));
        }

        if (testProcess.hasTerminated(exitStatus, 5)) {
            switch (exitStatus.value) {
            case TC_RESULT_OK:
                if (!spliced.isRunning(5)) {
                    assertFalse("Spliced has stopped", true);
                }
                break;
            case TC_RESULT_DETACH_FAILED:
                break;
            case TC_RESULT_NOT_BLOCKED:
                break;
            default:
                break;
            }
        } else {
            testProcess.stop();
            assertFalse("TestApplication failed to stop", true);
        }

        spliced.stop();
    }

    public static void main(String[] args) {
        int result = -1;

        if (args.length == 1) {
            DomainParticipantFactoryDetach.TestApplication app = new DomainParticipantFactoryDetach.TestApplication();
            try {
                app.init(args[0]);
                if (args[0].equals("testcase_1")) {
                    result = app.execute(true, true);
                } else if (args[0].equals("testcase_2")) {
                    result = app.execute(true, false);
                } else if (args[0].equals("testcase_3")) {
                    result = app.execute(false, true);
                } else if (args[0].equals("testcase_4")) {
                    result = app.execute(false, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            secSleep(2);
        }

        System.exit(result);
    }



}
