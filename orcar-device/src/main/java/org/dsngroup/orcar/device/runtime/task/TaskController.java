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

import org.dsngroup.orcar.device.runtime.RuntimeScheduler;
import org.dsngroup.orcar.device.runtime.message.MessagePayload;
import org.dsngroup.orcar.device.runtime.Orchestrator;
import org.dsngroup.orcar.device.runtime.RuntimeClassLoader;
import org.dsngroup.orcar.device.runtime.RuntimeServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The TaskController will route the request task to:
 * 1. {@link RuntimeScheduler}
 * 2. {@link TaskRegistry}
 * To avoid the single-failure and bottleneck, the TaskController can be more than one instances.
 */
public class TaskController {

    private RuntimeScheduler runtimeScheduler;

    private final TaskRegistry taskRegistry;

    private final RuntimeClassLoader runtimeClassLoader;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    /**
     * Create a task event
     * @param taskEventID {@link Orchestrator}
     * @param messagePayload {@link MessagePayload}
     * @throws Exception Need to be actually catch if the repeat orchestrator id.
     */
    public void createTaskEvent(TaskEventID taskEventID, String className, String messagePayload)
            throws Exception {
        synchronized (taskEventID) {
            // Checkout if the task event existed.
            if (taskRegistry.containTaskEvent(taskEventID)) {
                taskRegistry.registerExistedTaskEvent(
                        taskEventID,
                        messagePayload);
            } else {
                Orchestrator orchestrator = new Orchestrator(taskEventID.getPrimitiveTaskEventID(),
                        runtimeClassLoader.loadClass(className));
                TaskEvent taskEvent = new TaskEvent(taskEventID, orchestrator, this);
                taskRegistry.registerNewTaskEvent(taskEvent, messagePayload);
            }
        }
        requestToFireTask(taskEventID);
    }

    /**
     * Request to fire a task.
     * @param taskEventID {@link TaskEventID}
     * @throws Exception The exception is thrown as an error from request failed, it should be catch.
     */
    public void requestToFireTask(TaskEventID taskEventID) throws Exception {
        // TODO: Should create a listener to check the task state?
        TaskEvent taskEvent = taskRegistry.getTaskEvent(taskEventID);
        // In this step, the task event is already store in the task registry and ready to execute,
        //    or, is current running.
        synchronized (taskEvent) {
            // 1. Checkout the task event is running or not.
            if (taskEvent.getTaskState() == TaskState.RUNNING) {
                // Running currently
                // Ignore this request
            } else {
                // The task event is not currently running
                // Checkout whether it has pending task event
                if (taskRegistry.checkWhetherPendingTaskEvent(taskEventID)) {
                    // scheduled it
                    taskEvent.setTaskState(TaskState.RUNNING);
                    runtimeScheduler.fireTask(taskEvent);
                }
            }
        }
    }

    /**
     * Get the associated task registry, {@link TaskRegistry}
     * @return task registry
     */
    public TaskRegistry getTaskRegistry() {
        return taskRegistry;
    }

    /**
     * Contructor, accept a {@link RuntimeServiceContext}.
     */
    public TaskController(RuntimeServiceContext runtimeServiceContext) {
        try {
            runtimeScheduler = new RuntimeScheduler(runtimeServiceContext.getRuntimeThreadPoolSize());
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
        taskRegistry = new TaskRegistry();
        runtimeClassLoader = new RuntimeClassLoader(runtimeServiceContext.getLocalClassPath());
    }
}
