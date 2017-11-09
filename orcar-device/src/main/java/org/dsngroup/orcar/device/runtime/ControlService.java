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

package org.dsngroup.orcar.device.runtime;

import org.dsngroup.orcar.actor.FunctionalActor;
import org.dsngroup.orcar.device.runtime.task.TaskEventIDFactory;
import org.dsngroup.orcar.device.runtime.task.TaskController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ControlService is used as a mediator for handling external requests.
 */
public class ControlService {

    private final TaskController taskController;

    private static final Logger logger = LoggerFactory.getLogger(ControlService.class);

    /**
     * The runNewTask of ControlService, instantiate an {@link Orchestrator} and {@link FunctionalActor}
     * @param orchestratorID The unique ID of orchestrator.
     * @param className The className of the class which implements {@link FunctionalActor}.
     */
    public void runNewTask(byte orchestratorID, String className, String messagePayload) {
        try {
            taskController.createTaskEvent(TaskEventIDFactory.createTaskEventID(orchestratorID), className, messagePayload);
        } catch (Exception e) {
            logger.error("Error occurred in task controller " + e.getMessage());
            // Drop this request.
            return;
        }
    }

    /**
     * Constructor, which carry with an taskController
     */
    public ControlService(RuntimeServiceContext runtimeServiceContext) {
        this.taskController = new TaskController(runtimeServiceContext);
    }
}
