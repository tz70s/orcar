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

import junit.framework.TestCase;
import org.dsngroup.orcar.actor.FunctionalActor;
import org.dsngroup.orcar.actor.MailBoxer;
import org.dsngroup.orcar.runtime.Orchestrator;
import org.dsngroup.orcar.runtime.RuntimeScheduler;
import org.dsngroup.orcar.runtime.task.*;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TaskFactoryTest {

    private FunctionalActor functionalActor;

    private static TaskController taskController;

    private void init() throws Exception {
        taskController = new TaskController(new RuntimeScheduler());
        functionalActor = new FunctionalActor() {
            @Override
            public void accept(MailBoxer mailBoxer) throws Exception {
                // discard
            }
        };
    }

    @Test
    public void createTaskEventTest() throws Exception {
        /*
        init();
        try {
            TaskFactory.createTaskEvent((byte) '1', this.getClass().toString(), "Hello", taskController);
            TaskEvent taskEvent = TaskRegistry.getTaskEvent((byte) '1');
            assertEquals(taskEvent.getOrchestrator(), orchestrator);
            TestCase.assertEquals(taskEvent.getTaskState(), TaskState.PENDING);
            assertEquals(taskEvent.getMessagePayload(), "Hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
        TaskRegistry.clearTaskRegistry();
        */
    }
}
