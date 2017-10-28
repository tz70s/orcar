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

    private static Map<String, TaskEvent> registryMemoryPool = new ConcurrentHashMap<>();

    /**
     * Register a task event into TaskRegistry.
     * @param task {@link TaskEvent}
     */
    public static void registerTaskEvent(TaskEvent task) {
        registryMemoryPool.put(task.getTaskEventID(), task);
    }

    /**
     * Get the current state of an task.
     * @param taskEventID The unique ID of TaskEvent which is also the orchestrator ID.
     * @return {@link TaskState}
     */
    public static synchronized TaskState getTaskEventState(String taskEventID) throws Exception {
        // TODO: may need a better lock.
        TaskEvent tmpBindingTaskEvent = registryMemoryPool.get(taskEventID);
        if (tmpBindingTaskEvent == null) {
            throw new Exception("No such task event.");
        }
        return tmpBindingTaskEvent.getState();
    }

    // Singleton
    private TaskRegistry(){}
}
