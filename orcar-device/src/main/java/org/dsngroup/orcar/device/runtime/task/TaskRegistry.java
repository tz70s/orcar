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

package org.dsngroup.orcar.device.runtime.task;

import org.dsngroup.orcar.device.runtime.Orchestrator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The TaskRegistry is a handler for record, arrange tasks.
 */
public class TaskRegistry {

    private static Map<Orchestrator, TaskRecordFrame> taskRegistryMemoryPool;

    /**
     * Data structure for storing a task event and associated message queue.
     */
    private class TaskRecordFrame {
        private TaskEvent taskEvent;

        private LinkedBlockingQueue<String> eventWaitingQueue;

        public TaskRecordFrame(TaskEvent taskEvent, String messagePayload) throws Exception {
            this.taskEvent = taskEvent;
            eventWaitingQueue = new LinkedBlockingQueue<>();
            putEventWaitingQueue(messagePayload);
        }

        public TaskEvent getTaskEvent() {
            return taskEvent;
        }

        // Queue operations

        public void putEventWaitingQueue(String messagePayload) throws Exception {
            eventWaitingQueue.put(messagePayload);
        }

        public String pollEventWaitingQueue() throws Exception {
            return eventWaitingQueue.poll();
        }

        public int getEventWaitingQueueSize() {
            return eventWaitingQueue.size();
        }

        public boolean checkWhetherPendingEvent() {
            return getEventWaitingQueueSize() != 0;
        }
    }

    /**
     * Register a new task event into TaskRegistry.
     * @param taskEvent {@link TaskEvent}
     * @param messagePayload message payload associated
     */
    public synchronized void registerNewTaskEvent(TaskEvent taskEvent, String messagePayload) throws Exception {
        taskRegistryMemoryPool.put(taskEvent.getOrchestrator(), new TaskRecordFrame(taskEvent, messagePayload));
    }

    /**
     * Register a existed task event into taskregistry.
     * @param orchestrator {@link Orchestrator}
     * @param messagePayload message payload associated
     * @throws Exception
     */
    public void registerExistedTaskEvent(Orchestrator orchestrator, String messagePayload) throws Exception {
        taskRegistryMemoryPool.get(orchestrator).putEventWaitingQueue(messagePayload);
    }

    /**
     * Check whether there is pending task event or not
     * @param orchestrator {@link Orchestrator}
     * @return true or false
     * @throws Exception
     */
    public boolean checkWhetherPendingTaskEvent(Orchestrator orchestrator) throws Exception {
        return taskRegistryMemoryPool.get(orchestrator).checkWhetherPendingEvent();
    }

    /**
     * Poll a registered task event message to do a new task.
     * @param orchestrator {@link Orchestrator}
     * @return message payload
     * @throws Exception
     */
    public String pollRegisteredTaskEventMessage(Orchestrator orchestrator) throws Exception {
        return taskRegistryMemoryPool.get(orchestrator).pollEventWaitingQueue();
    }

    /**
     * Get the current state of an task.
     * @param orchestrator {@link Orchestrator}
     * @return {@link TaskState}
     */
    public synchronized TaskState getTaskEventState(Orchestrator orchestrator) throws Exception {
        TaskEvent taskEvent = taskRegistryMemoryPool.get(orchestrator).getTaskEvent();
        if (taskEvent == null) {
            throw new Exception("No such task event.");
        }
        return taskEvent.getTaskState();
    }

    /**
     * Report contain task event or not
     * @param orchestrator {@link Orchestrator}
     * @return contains or not
     */
    public boolean containTaskEvent(Orchestrator orchestrator) {
        return taskRegistryMemoryPool.containsKey(orchestrator);
    }

    /**
     * Get task event from orchgestrator id
     * @param orchestrator {@link Orchestrator}
     * @return {@link TaskEvent}
     * @throws Exception checkout if the task existed or not.
     */
    public TaskEvent getTaskEvent(Orchestrator orchestrator) throws Exception {
        if (!containTaskEvent(orchestrator)) {
            throw new Exception("Should checkout the task event existed or not.");
        }
        return taskRegistryMemoryPool.get(orchestrator).getTaskEvent();
    }

    /**
     * Remove existed task event
     * @param orchestrator {@link Orchestrator}
     * @throws Exception TODO: Need to properly deal with this
     */
    public void removeTaskEvent(Orchestrator orchestrator) throws Exception {
        if (!containTaskEvent(orchestrator)) {
            throw new Exception("Remove an non-existed task event.");
        }

        if (taskRegistryMemoryPool.get(orchestrator).checkWhetherPendingEvent()) {
            throw new Exception("Still have works to do in this actor");
        } else {
            taskRegistryMemoryPool.remove(orchestrator);
        }
    }

    /**
     * Force remove a task event with associated messages.
     * @param orchestrator {@link TaskEvent}
     * @throws Exception if no such task event.
     */
    public void forceRemoveTaskEvent(Orchestrator orchestrator) throws Exception {
         if (!containTaskEvent(orchestrator)) {
            throw new Exception("Remove an non-existed task event.");
         }
         taskRegistryMemoryPool.remove(orchestrator);
    }

    /**
     * Force clean all task registry.
     */
    public void forceClearTaskRegistry() {
        taskRegistryMemoryPool.clear();
    }

    /**
     * Default constructor
     */
    public TaskRegistry() {
        taskRegistryMemoryPool = new ConcurrentHashMap<>();
    }
}
