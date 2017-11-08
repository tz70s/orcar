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

import org.dsngroup.orcar.actor.MailBoxer;
import org.dsngroup.orcar.runtime.Orchestrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A TaskEvent is an event entity for registered in TaskRegistry.
 */
public class TaskEvent implements Runnable {

    private volatile TaskState taskState;

    private volatile Orchestrator orchestrator;

    private static final Logger logger = LoggerFactory.getLogger(TaskEvent.class);

    private static TaskController taskController;

    /**
     * Construct a TaskEvent from the virtual orchestrator. Is package visible, and create via TaskFactory.
     * @param orchestrator {@link Orchestrator}
     */
    public TaskEvent(Orchestrator orchestrator, TaskController taskController) throws Exception {
        this.taskState = TaskState.PENDING;
        this.orchestrator = orchestrator;
        TaskEvent.taskController = taskController;
    }

    /**
     * Implement Runnable for thread here.
     */
    @Override
    public void run() {
        try {
            MailBoxer mailBoxer = new MailBoxer(TaskRegistry.pollRegisteredTaskEventMessage(this));
            orchestrator.accept(mailBoxer);
            taskState = TaskState.FINISHED;
            // TODO: Currently fire another task inner, but we should have mechanism to use task controller outside.
            // TODO: This will also have consistency problem?
            // TODO: That is, we still have to use completable future for trigger next task?
            taskController.requestToFireTask(orchestrator.getOrchestratorID());
        } catch (Exception e) {
            logger.error("Internal error of functional actor" + e.getMessage());
            taskState = TaskState.FAILED;
            // TODO: Execute fail state task.
        }
    }

    /**
     * Get the current taskState of the task.
     * @return {@link TaskState}
     */
    public synchronized TaskState getTaskState() {
        return taskState;
    }

    public synchronized void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }
    /**
     * Get the wrapped orchestrator.
     * @return {@link Orchestrator}
     */
    public synchronized Orchestrator getOrchestrator() {
        return orchestrator;
    }
}
