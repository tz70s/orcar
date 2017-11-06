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
import org.dsngroup.orcar.runtime.task.TaskEvent;

public class Orchestrator implements Comparable<Orchestrator> {
    private Byte orchestratorID;

    private FunctionalActor orchestratorFunciton;

    /**
     * Get the unique OrchestratorID
     * @return OrchestratorID
     */
    public Byte getOrchestratorID() {
        return orchestratorID;
    }

    /**
     * Propagate to run in {@link TaskEvent}
     * @param mailBoxer {@link MailBoxer}
     * @throws Exception throws out functional actor's exception
     */
    public void accept(MailBoxer mailBoxer) throws Exception {
        orchestratorFunciton.accept(mailBoxer);
    }

    /**
     * The orchestrator constructor.
     * @param orchestratorID The unique id of an orchestrator.
     */
    public Orchestrator(Byte orchestratorID, Object orchestratorFunctions) throws Exception {
        this.orchestratorID = orchestratorID;
        this.orchestratorFunciton = (FunctionalActor) orchestratorFunctions;
    }

    /**
     * Get the internal functional actor, only for test.
     * @return {@link FunctionalActor}
     */
    public FunctionalActor getOrchestratorFunciton() {
        return orchestratorFunciton;
    }

    /**
     * Override hashCode.
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return 301 + 7 * (int) orchestratorID;
    }

    /**
     * Override equals for compare.
     * @param another another orchestrator.
     * @return boolean
     */
    @Override
    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another.getClass() != this.getClass()) {
            return false;
        } else {
            Orchestrator anotherOrchestrator = (Orchestrator) another;
            if (anotherOrchestrator.orchestratorID == orchestratorID) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Override compareTo as Comparable
     * @param another another {@see Orchestrator}
     * @return compare integer
     */
    @Override
    public int compareTo(Orchestrator another) {
        if (orchestratorID < another.orchestratorID) {
            return -1;
        } else if (orchestratorID > another.orchestratorID) {
            return 1;
        } else {
            return 0;
        }
    }
}
