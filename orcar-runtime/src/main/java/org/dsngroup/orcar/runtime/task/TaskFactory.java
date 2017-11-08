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

package org.dsngroup.orcar.runtime.task;

import org.dsngroup.orcar.runtime.Orchestrator;
import org.dsngroup.orcar.runtime.RuntimeClassLoader;

/**
 * Mostly to avoid identifier, the task should use the factory pattern for create {@link TaskEvent}
 */
public class TaskFactory {

    /**
     * Create a task event
     * @param orchestratorID {@link Orchestrator}
     * @param messagePayload {@link org.dsngroup.orcar.runtime.message.MessagePayload}
     * @throws Exception Need to be actually catch if the repeat orchestrator id.
     */
    public static void createTaskEvent(Byte orchestratorID, String className, String messagePayload, TaskController taskController)
            throws Exception {
        // TODO: Also, the same problem in control service.
        synchronized (orchestratorID) {
            // Checkout if the task event existed.
            if (TaskRegistry.containTaskEvent(orchestratorID)) {
                TaskRegistry.registerExistedTaskEvent(
                        TaskRegistry.getTaskEvent(orchestratorID),
                        messagePayload);
            } else {
                Orchestrator orchestrator = new Orchestrator(orchestratorID, RuntimeClassLoader.loadClass(className));
                TaskEvent taskEvent = new TaskEvent(orchestrator, taskController);
                TaskRegistry.registerNewTaskEvent(taskEvent, messagePayload);
            }
        }
    }

    private TaskFactory() {}
}
