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

package org.dsngroup.orcar.runtime;

import org.dsngroup.orcar.actor.FunctionalActor;
import org.dsngroup.orcar.actor.MailBoxer;
import org.dsngroup.orcar.runtime.task.TaskController;
import org.dsngroup.orcar.runtime.task.TaskEvent;
import org.dsngroup.orcar.runtime.task.TaskFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ControlService is used as a mediator for handling external requests.
 */
public class ControlService {

    private TaskController taskController;

    private RuntimeScheduler runtimeScheduler;

    private static final Logger logger = LoggerFactory.getLogger(ControlService.class);

    /**
     * The runNewTask of ControlService, instantiate an {@link Orchestrator} and {@link FunctionalActor}
     * @param orchestratorID The unique ID of orchestrator.
     * @param className The className of the class which implements {@link FunctionalActor}.
     */
    public void runNewTask(Byte orchestratorID, String className, String messagePayload) {
        try {
            // Load Class
            // TODO: Handling exception better.
            // TODO: reuse the existed orchestrator.
            Orchestrator orc = new Orchestrator(orchestratorID, RuntimeClassLoader.loadClass(className));
            // Generate a new task event
            TaskEvent task = TaskFactory.createTaskEvent(orc, messagePayload);
            taskController.requestToFireTask(task);
        } catch (Exception e) {
            logger.error("Orchestration instantiation failed");
            logger.error(e.getMessage());
            // Drop this request.
            return;
        }
    }

    // TODO: The TaskController and ControlService may consider an another instantiate way.
    /**
     * Constructor, which carry with an taskController
     */
    public ControlService(RuntimeServiceContext runtimeServiceContext) {
        try {
            runtimeScheduler = new RuntimeScheduler(runtimeServiceContext.getRuntimeThreadPoolSize());
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
        this.taskController = new TaskController(runtimeScheduler);
    }
}
