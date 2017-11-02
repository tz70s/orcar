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
import org.dsngroup.orcar.actor.Mail;

public class Orchestrator {
    private Byte orchestratorID;

    private FunctionalActor orchestratorFunciton;

    /**
     * Get the unique OrchestratorID
     * @return OrchestratorID
     */
    public Byte getOrchestratorID() {
        return orchestratorID;
    }

    public void accept(Mail mail) {
        orchestratorFunciton.accept(mail);
    }

    /**
     * The orchestrator constructor.
     * @param orchestratorID The unique id of an orchestrator.
     */
    public Orchestrator(Byte orchestratorID, Object orchestratorFunctions) throws Exception {
        this.orchestratorID = orchestratorID;
        this.orchestratorFunciton = (FunctionalActor) orchestratorFunctions;
    }
}
