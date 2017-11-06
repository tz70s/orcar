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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The TaskRegistry is a handler for record, arrange tasks.
 */
public class TaskRegistry {

    // TODO: What is a proper TaskRegistry data structure?

    private static Map<Byte, TaskEvent> registryMemoryPool = new ConcurrentHashMap<>();

    /**
     * Register a task event into TaskRegistry.
     * @param task {@link TaskEvent}
     */
    public static void registerTaskEvent(TaskEvent task) {
        registryMemoryPool.put(task.getOrchestrator().getOrchestratorID(), task);
    }

    /**
     * Get the current state of an task.
     * @param orchestratorID {@see Orchestrator}
     * @return {@link TaskState}
     */
    public static synchronized TaskState getTaskEventState(Byte orchestratorID) throws Exception {
        // TODO: may need a better lock.
        TaskEvent tmpBindingTaskEvent = registryMemoryPool.get(orchestratorID);
        if (tmpBindingTaskEvent == null) {
            throw new Exception("No such task event.");
        }
        return tmpBindingTaskEvent.getTaskState();
    }

    /**
     * Report contain task event or not
     * @param orchestratorID {@link org.dsngroup.orcar.runtime.Orchestrator}
     * @return contains or not
     */
    public static boolean containTaskEvent(Byte orchestratorID) {
        return registryMemoryPool.containsKey(orchestratorID);
    }

    /**
     * Get task event from orchgestrator id
     * @param orchestratorID {@link org.dsngroup.orcar.runtime.Orchestrator}
     * @return {@link TaskEvent}
     * @throws Exception checkout if the task existed or not.
     */
    public static TaskEvent getTaskEvent(Byte orchestratorID) throws Exception {
        if (!containTaskEvent(orchestratorID)) {
            throw new Exception("Should checkout the task event existed or not.");
        }
        return registryMemoryPool.get(orchestratorID);
    }

    // Singleton
    private TaskRegistry(){}
}
