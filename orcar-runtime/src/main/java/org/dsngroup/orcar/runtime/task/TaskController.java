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
import org.dsngroup.orcar.runtime.RuntimeScheduler;

/**
 * The TaskController will route the request task to:
 * 1. {@link RuntimeScheduler}
 * 2. {@link TaskRegistry}
 * To avoid the single-failure and bottleneck, the TaskController can be more than one instances.
 */
public class TaskController {

    private RuntimeScheduler runtimeScheduler;

    /**
     * Request to fire a task.
     * @param orchestratorID {@link Orchestrator}
     * @throws Exception The exception is thrown as an error from request failed, it should be catch.
     */
    public void requestToFireTask(byte orchestratorID) throws Exception {
        // TODO: Should create a listener to check the task state?
        TaskEvent taskEvent = TaskRegistry.getTaskEvent(orchestratorID);
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
                if (TaskRegistry.checkWhetherPendingTaskEvent(taskEvent)) {
                    // scheduled it
                    taskEvent.setTaskState(TaskState.RUNNING);
                    runtimeScheduler.fireTask(taskEvent);
                }
            }
        }
    }

    /**
     * Request a task from a ID.
     * @param orchestratorID see {@link Orchestrator}.
     * @return {@link TaskState}
     * @throws Exception Throws if the error occurred on get state from registry, it should be catch.
     */
    public TaskState requestForTaskState(Byte orchestratorID) throws Exception {
        // TODO: should have a unique alias for request, instead of task.
        return TaskRegistry.getTaskEventState(orchestratorID);
    }

    /**
     * Contructor, accept a runtime scheduler.
     * @param runtimeScheduler {@see RuntimeScheduler}
     */
    public TaskController(RuntimeScheduler runtimeScheduler) {
        this.runtimeScheduler = runtimeScheduler;
    }
}
