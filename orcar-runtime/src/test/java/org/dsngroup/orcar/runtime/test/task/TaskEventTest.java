/*
 * Copyright (c) 2017 original authors and authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dsngroup.orcar.runtime.test.task;

import org.dsngroup.orcar.actor.FunctionalActor;
import org.dsngroup.orcar.actor.MailBoxer;
import org.dsngroup.orcar.runtime.Orchestrator;
import org.dsngroup.orcar.runtime.task.TaskEvent;
import org.dsngroup.orcar.runtime.task.TaskFactory;
import org.dsngroup.orcar.runtime.task.TaskRegistry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TaskEventTest {

    private class Unused{}

    public static class TestableActor implements FunctionalActor {
        protected int internalState;
        public TestableActor() {
            internalState = 0;
        }
        @Override
        public void accept(MailBoxer mailBoxer) throws Exception {
            // Discard mail boxer
            internalState++;
        }
        public int getInternalState() {
            return internalState;
        }
    }

    @Test
    public void testSameBindingTaskEvent() throws Exception {
        /*
        TaskEvent taskEvent = TaskFactory.createTaskEvent(new Orchestrator((byte) '1', new TestableActor()),
                "Hello");

        Thread thread = new Thread(taskEvent);
        // Another thread
        Thread threadAnother = new Thread(taskEvent);
        thread.start();
        threadAnother.start();
        try {
            thread.join();
            threadAnother.join();
        } catch (Exception e) {
            // Error occurred
            e.printStackTrace();
        }
        TestableActor testableActor = (TestableActor) taskEvent.getOrchestrator().getOrchestratorFunciton();
        assertEquals(2, testableActor.getInternalState());
        TaskRegistry.clearTaskRegistry();
        */
    }

    // Use for test count multiple times
    public class TestableCountMultipleTimesActor extends TestableActor {
        @Override
        public void accept(MailBoxer mailBoxer) throws Exception {
            for(int i = 0; i < 20; i++) {
                internalState++;
            }
        }
    }

    @Test
    public void testSynchronizedTaskEvent() {
        /*
        TaskEvent taskEvent = null;
        try {
            taskEvent = TaskFactory.createTaskEvent(new Orchestrator((byte) '1',
                            new TestableCountMultipleTimesActor()),"Hello");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread aThread = new Thread(taskEvent);
        Thread bThread = new Thread(taskEvent);

        aThread.start();
        bThread.start();

        try {
            aThread.join();
            bThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TestableCountMultipleTimesActor testableCountMultipleTimesActor = (TestableCountMultipleTimesActor)
                taskEvent.getOrchestrator().getOrchestratorFunciton();
        assertEquals(40, testableCountMultipleTimesActor.getInternalState());
        TaskRegistry.clearTaskRegistry();
        */
    }

    @Test
    public void testDifferentTaskEvent() throws Exception {
        /*
        TaskEvent[] taskEvents = new TaskEvent[10];
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            taskEvents[i] = TaskFactory.createTaskEvent(new Orchestrator((byte) i,
                    new TestableCountMultipleTimesActor()),"Hello");
            threads[i] = new Thread(taskEvents[i]);
            threads[i].start();
        }

        for (Thread thread: threads) {
            thread.join();
        }

        for (TaskEvent taskEvent: taskEvents) {
            TestableCountMultipleTimesActor testableCountMultipleTimesActor = (TestableCountMultipleTimesActor)
                    taskEvent.getOrchestrator().getOrchestratorFunciton();
            assertEquals(20, testableCountMultipleTimesActor.getInternalState());
        }

        TaskRegistry.clearTaskRegistry();
        */
    }
}
