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

import org.dsngroup.orcar.actor.MailBox;
import org.dsngroup.orcar.runtime.Orchestrator;

/**
 * A TaskEvent is an event entity for registered in TaskRegistry.
 */
public class TaskEvent implements Runnable {

    private TaskState taskState;

    private Orchestrator orchestrator;

    private Byte taskEventID;

    private MailBox mailBox;

    /**
     * Construct a TaskEvent from the virtual orchestrator. Is package visible, and create via TaskFactory.
     * @param orchestrator {@link Orchestrator}
     */
    TaskEvent(Orchestrator orchestrator, MailBox mailBox) {
        this.taskState = TaskState.PENDING;
        this.taskEventID = orchestrator.getOrchestratorID();
        this.orchestrator = orchestrator;
        this.mailBox = mailBox;
    }

    /**
     * Implement Runnable for thread here.
     */
    @Override
    public void run() {
        orchestrator.accept(mailBox);
    }

    /**
     * Get the current taskState of the task.
     * @return {@link TaskState}
     */
    public TaskState getTaskState() {
        return taskState;
    }

    /**
     * Set the taskState of task event.
     * @param taskState {@link TaskState}
     */
    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    /**
     * Get the unique ID of this task event, which is equivalent to the orchestrator.
     * @return taskEventID.
     */
    public Byte getTaskEventID() {
        return taskEventID;
    }
}
