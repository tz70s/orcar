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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The TaskRegistry is a handler for record, arrange tasks.
 */
public class TaskRegistry {

    private static Map<TaskEventID, TaskRecordFrame> taskRegistryMemoryPool;

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
        taskRegistryMemoryPool.put(taskEvent.getTaskEventID(), new TaskRecordFrame(taskEvent, messagePayload));
    }

    /**
     * Register a existed task event into taskregistry.
     * @param taskEventID {@link TaskEvent}
     * @param messagePayload message payload associated
     * @throws Exception
     */
    public void registerExistedTaskEvent(TaskEventID taskEventID, String messagePayload) throws Exception {
        taskRegistryMemoryPool.get(taskEventID).putEventWaitingQueue(messagePayload);
    }

    /**
     * Check whether there is pending task event or not
     * @param taskEventID {@link TaskEvent}
     * @return true or false
     * @throws Exception
     */
    public boolean checkWhetherPendingTaskEvent(TaskEventID taskEventID) throws Exception {
        return taskRegistryMemoryPool.get(taskEventID).checkWhetherPendingEvent();
    }

    /**
     * Poll a registered task event message to do a new task.
     * @param taskEventID {@link TaskEvent}
     * @return message payload
     * @throws Exception
     */
    public String pollRegisteredTaskEventMessage(TaskEventID taskEventID) throws Exception {
        return taskRegistryMemoryPool.get(taskEventID).pollEventWaitingQueue();
    }

    /**
     * Get the current state of an task.
     * @param taskEventID {@link TaskEvent}
     * @return {@link TaskState}
     */
    public synchronized TaskState getTaskEventState(TaskEventID taskEventID) throws Exception {
        TaskEvent taskEvent = taskRegistryMemoryPool.get(taskEventID).getTaskEvent();
        if (taskEvent == null) {
            throw new Exception("No such task event.");
        }
        return taskEvent.getTaskState();
    }

    /**
     * Report contain task event or not
     * @param taskEventID {@link TaskEvent}
     * @return contains or not
     */
    public boolean containTaskEvent(TaskEventID taskEventID) {
        return taskRegistryMemoryPool.containsKey(taskEventID);
    }

    /**
     * Get task event from orchgestrator id
     * @param taskEventID {@link TaskEvent}
     * @return {@link TaskEvent}
     * @throws Exception checkout if the task existed or not.
     */
    public TaskEvent getTaskEvent(TaskEventID taskEventID) throws Exception {
        if (!containTaskEvent(taskEventID)) {
            throw new Exception("Should checkout the task event existed or not.");
        }
        return taskRegistryMemoryPool.get(taskEventID).getTaskEvent();
    }

    /**
     * Remove existed task event
     * @param taskEventID {@link TaskEvent}
     * @throws Exception TODO: Need to properly deal with this
     */
    public void removeTaskEvent(TaskEventID taskEventID) throws Exception {
        if (!containTaskEvent(taskEventID)) {
            throw new Exception("Remove an non-existed task event.");
        }

        if (taskRegistryMemoryPool.get(taskEventID).checkWhetherPendingEvent()) {
            throw new Exception("Still have works to do in this actor");
        } else {
            taskRegistryMemoryPool.remove(taskEventID);
        }
    }

    /**
     * Force remove a task event with associated messages.
     * @param taskEventID {@link TaskEvent}
     * @throws Exception if no such task event.
     */
    public void forceRemoveTaskEvent(TaskEventID taskEventID) throws Exception {
         if (!containTaskEvent(taskEventID)) {
            throw new Exception("Remove an non-existed task event.");
         }
         taskRegistryMemoryPool.remove(taskEventID);
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
