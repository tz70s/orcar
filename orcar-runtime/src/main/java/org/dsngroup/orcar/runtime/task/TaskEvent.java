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

import org.dsngroup.orcar.orchestrator.Orchestrator;

/**
 * A TaskEvent is an event entity for registered in TaskRegistry.
 */
public class TaskEvent {

    private TaskState state;

    private Orchestrator orchestrator;

    private String taskEventID;
    /**
     * Construct a TaskEvent from the virtual orchestrator. Is package visible, and create via TaskFactory.
     * @param orchestrator {@link Orchestrator}
     */
    TaskEvent(Orchestrator orchestrator) {
        this.taskEventID = orchestrator.getOrchestratorID();
    }

    /**
     * Get the current state of the task.
     * @return {@link TaskState}
     */
    public TaskState getState() {
        return state;
    }

    /**
     * Set the state of task event.
     * @param state {@link TaskState}
     */
    public void setState(TaskState state) {
        this.state = state;
    }

    /**
     * Get the unique ID of this task event, which is equivalent to the orchestrator.
     * @return taskEventID.
     */
    public String getTaskEventID() {
        return taskEventID;
    }
}
