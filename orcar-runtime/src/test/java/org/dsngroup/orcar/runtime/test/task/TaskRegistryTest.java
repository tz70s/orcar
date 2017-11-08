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
import org.dsngroup.orcar.runtime.RuntimeScheduler;
import org.dsngroup.orcar.runtime.task.TaskController;
import org.dsngroup.orcar.runtime.task.TaskEvent;
import org.dsngroup.orcar.runtime.task.TaskRegistry;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TaskRegistryTest {

    private static TaskEvent taskEvent;

    private static byte orchestratorID = (byte) '1';

    private static void init() {
        /*
        try {
        taskEvent = new TaskEvent(new Orchestrator(orchestratorID, new FunctionalActor() {
            @Override
            public void accept(MailBoxer mailBoxer) throws Exception {
                // Discard
            }
        }), "hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    @Test
    public void registerTaskEventTest() {
        /*
        init();
        TaskRegistry.registerTaskEvent(taskEvent);
        try {
            assertEquals(taskEvent,
                    TaskRegistry.getTaskEvent(taskEvent.getOrchestrator().getOrchestratorID()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        TaskRegistry.clearTaskRegistry();
        */
    }

    @Test
    public void containTaskEventTest() {
        /*
        init();
        TaskRegistry.registerTaskEvent(taskEvent);
        assertEquals(true, TaskRegistry.containTaskEvent(orchestratorID));
        TaskRegistry.clearTaskRegistry();
        */
    }
}
