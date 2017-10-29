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

import org.dsngroup.orcar.runtime.RuntimeScheduler;

/**
 * The TaskController will route the request task to:
 * 1. {@link RuntimeScheduler}
 * 2. {@link TaskRegistry}
 * To avoid the single-failure and bottleneck, the TaskController can be more than one instances.
 */
public class TaskController {

    /**
     * Request to fire a task.
     * @param task {@link TaskEvent}
     * @throws Exception The exception is thrown as an error from request failed, it should be catch.
     */
    public void requestToFireTask(TaskEvent task) throws Exception {
        // Mark the task into pending state.
        task.setTaskState(TaskState.PENDING);
        // TODO: Should create a listener to check the task state?
        RuntimeScheduler.fireTask(task);
        TaskRegistry.registerTaskEvent(task);
    }

    /**
     * Request a task from a ID.
     * @param orchestratorID the unique orchestratorID.
     * @return {@link TaskState}
     * @throws Exception Throws if the error occurred on get state from registry, it should be catch.
     */
    public TaskState requestForTaskState(String orchestratorID) throws Exception {
        // TODO: should have a unique alias for request, instead of task.
        return TaskRegistry.getTaskEventState(orchestratorID);
    }

    public TaskController() {
        // No internal state need to keep, currently.
    }
}
