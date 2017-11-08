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
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The TaskRegistry is a handler for record, arrange tasks.
 */
public class TaskRegistry {

    // TODO: What is a proper TaskRegistry data structure?

    public static Map<Byte, TaskEvent> taskEventMemoryPool = new ConcurrentHashMap<>();

    public static Map<TaskEvent, LinkedBlockingQueue<String>> taskRegistryMemoryPool = new ConcurrentHashMap<>();

    /**
     * Register a new task event into TaskRegistry.
     * @param taskEvent {@link TaskEvent}
     * @param messagePayload message payload associated
     */
    public static synchronized void registerNewTaskEvent(TaskEvent taskEvent, String messagePayload) throws Exception {
        taskEventMemoryPool.put(taskEvent.getOrchestrator().getOrchestratorID(), taskEvent);
        taskRegistryMemoryPool.put(taskEvent, new LinkedBlockingQueue<>());
        taskRegistryMemoryPool.get(taskEvent).put(messagePayload);
    }

    /**
     * Register a existed task event into taskregistry.
     * @param taskEvent {@link TaskEvent}
     * @param messagePayload message payload associated
     * @throws Exception
     */
    public static void registerExistedTaskEvent(TaskEvent taskEvent, String messagePayload) throws Exception {
        taskRegistryMemoryPool.get(taskEvent).put(messagePayload);
    }

    /**
     * Check whether there is pending task event or not
     * @param taskEvent {@link TaskEvent}
     * @return true or false
     * @throws Exception
     */
    public static boolean checkWhetherPendingTaskEvent(TaskEvent taskEvent) throws Exception {
        return taskRegistryMemoryPool.get(taskEvent).size() != 0;
    }
    /**
     * Poll a registered task event message to do a new task.
     * @param taskEvent {@link TaskEvent}
     * @return message payload
     * @throws Exception
     */
    public static String pollRegisteredTaskEventMessage(TaskEvent taskEvent) throws Exception {
        return taskRegistryMemoryPool.get(taskEvent).poll();
    }

    /**
     * Get the current state of an task.
     * @param orchestratorID {@see Orchestrator}
     * @return {@link TaskState}
     */
    public static synchronized TaskState getTaskEventState(Byte orchestratorID) throws Exception {
        TaskEvent tmpBindingTaskEvent = taskEventMemoryPool.get(orchestratorID);
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
        return taskEventMemoryPool.containsKey(orchestratorID);
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
        return taskEventMemoryPool.get(orchestratorID);
    }

    /**
     * Remove existed task event
     * @param orchestratorID {@link org.dsngroup.orcar.runtime.Orchestrator}
     * @throws Exception TODO: Need to properly deal with this
     */
    public static void removeTaskEvent(Byte orchestratorID) throws Exception {
        if (!containTaskEvent(orchestratorID)) {
            throw new Exception("Remove an non-existed task event.");
        }
        TaskEvent taskEvent = getTaskEvent(orchestratorID);
        if (taskRegistryMemoryPool.get(taskEvent).size() != 0) {
            throw new Exception("Still have works to do in this actor");
        } else {
            taskEventMemoryPool.remove(orchestratorID);
            taskRegistryMemoryPool.remove(orchestratorID);
        }
    }

    /**
     * Force remove a task event with associated messages.
     * @param orchestratorID {@link org.dsngroup.orcar.runtime.Orchestrator}
     * @throws Exception if no such task event.
     */
    public static void forceRemoveTaskEvent(Byte orchestratorID) throws Exception {
         if (!containTaskEvent(orchestratorID)) {
            throw new Exception("Remove an non-existed task event.");
         }
         TaskEvent taskEvent = getTaskEvent(orchestratorID);
         taskEventMemoryPool.remove(orchestratorID);
         taskRegistryMemoryPool.remove(orchestratorID);
    }

    /**
     * Force clean all task registry.
     */
    public static void forceClearTaskRegistry() {
        taskRegistryMemoryPool.clear();
        taskEventMemoryPool.clear();
    }

    // Singleton
    private TaskRegistry(){}
}
