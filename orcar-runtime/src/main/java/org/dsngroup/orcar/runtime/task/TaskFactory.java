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

import java.util.Set;
import java.util.HashSet;

/**
 * Mostly to avoid identifier, the task should use the factory pattern for create {@link TaskEvent}
 */
public class TaskFactory {

    private static Set<Byte> orchestratorIDSet = new HashSet<>();

    // I think the request to generate a task may be single threaded.

    /**
     * Create a task event
     * @param orchestrator {@link Orchestrator}
     * @return {@link TaskEvent}
     * @throws Exception Need to be actually catch if the repeat orchestrator id.
     */
    public static TaskEvent createTaskEvent(Orchestrator orchestrator) throws Exception {
        // To avoid double creation of a task, check Orchestrator ID if contains.
        if (orchestratorIDSet.contains(orchestrator.getOrchestratorID())) {
            throw new Exception("The Orchestrator is already exists");
        }
        orchestratorIDSet.add(orchestrator.getOrchestratorID());
        return new TaskEvent(orchestrator);
    }

    private TaskFactory() {}
}
