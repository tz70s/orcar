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

    private TaskState taskState;

    private Orchestrator orchestrator;

    private static final Logger logger = LoggerFactory.getLogger(TaskEvent.class);

    private String messagePayload;

    /**
     * Construct a TaskEvent from the virtual orchestrator. Is package visible, and create via TaskFactory.
     * @param orchestrator {@link Orchestrator}
     */
    TaskEvent(Orchestrator orchestrator, String messagePayload) throws Exception {
        this.taskState = TaskState.PENDING;
        this.orchestrator = orchestrator;
        this.messagePayload = messagePayload;
    }

    /**
     * Implement Runnable for thread here.
     */
    @Override
    public void run() {
        synchronized (this) {
            while (taskState == TaskState.RUNNING) {
                try {
                    // TODO: Seems like no need to wait and notify.
                    wait();
                } catch (InterruptedException e) {
                    logger.error("Interrupted. " + e.getMessage());
                }
            }
            // Set to running state
            taskState = TaskState.RUNNING;
            try {
                MailBoxer mailBoxer = new MailBoxer(messagePayload);
                orchestrator.accept(mailBoxer);
                taskState = TaskState.FINISHED;
            } catch (Exception e) {
                logger.error("Internal error of functional actor" + e.getMessage());
                taskState = TaskState.FAILED;
            } finally {
                notify();
            }
        }
    }

    /**
     * Get the current taskState of the task.
     * @return {@link TaskState}
     */
    public TaskState getTaskState() {
        return taskState;
    }

    /**
     * Update message for the new existed task event.
     * @param messagePayload {@link org.dsngroup.orcar.runtime.message.MessagePayload}
     */
    public void updateMessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }

    /**
     * Get the wrapped orchestrator.
     * @return {@link Orchestrator}
     */
    public Orchestrator getOrchestrator() {
        return orchestrator;
    }
}
