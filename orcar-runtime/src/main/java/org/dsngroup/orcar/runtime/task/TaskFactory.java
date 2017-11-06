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

/**
 * Mostly to avoid identifier, the task should use the factory pattern for create {@link TaskEvent}
 */
public class TaskFactory {

    /**
     * Create a task event
     * @param orchestrator {@link Orchestrator}
     * @param messagePayload {@link org.dsngroup.orcar.runtime.message.MessagePayload}
     * @return {@link TaskEvent}
     * @throws Exception Need to be actually catch if the repeat orchestrator id.
     */
    public static TaskEvent createTaskEvent(Orchestrator orchestrator, String messagePayload) throws Exception {
        TaskEvent newTaskEvent;
        // Checkout if the task event existed.
        if (TaskRegistry.containTaskEvent(orchestrator.getOrchestratorID())) {
            newTaskEvent = TaskRegistry.getTaskEvent(orchestrator.getOrchestratorID());
            newTaskEvent.updateMessagePayload(messagePayload);
        } else {
            newTaskEvent = new TaskEvent(orchestrator, messagePayload);
        }
        return newTaskEvent;
    }

    private TaskFactory() {}
}
