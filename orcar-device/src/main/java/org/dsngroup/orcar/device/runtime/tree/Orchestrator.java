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

package org.dsngroup.orcar.device.runtime.tree;

import org.dsngroup.orcar.actor.FunctionalActor;
import org.dsngroup.orcar.actor.MailBoxer;
import org.dsngroup.orcar.device.runtime.task.TaskEvent;

public class Orchestrator extends Actor implements Comparable<Orchestrator> {

    private FunctionalActor orchestratorFunciton;

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
     * @param parentActor parent actor
     * @param actorName name of actor
     * @param orchestratorFunctions {@link FunctionalActor}
     */
    public Orchestrator(Actor parentActor, String actorName, Object orchestratorFunctions) throws Exception {
        super(parentActor, actorName);
        this.orchestratorFunciton = (FunctionalActor) orchestratorFunctions;
    }

    /**
     * Get the internal functional actor, only for test.
     * @return {@link FunctionalActor}
     */
    public FunctionalActor getOrchestratorFunciton() {
        return orchestratorFunciton;
    }

    @Override
    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another instanceof Orchestrator) {
            Orchestrator anotherOrchestrator = (Orchestrator) another;
            if (this.getActorName().equals(anotherOrchestrator.getActorName())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getActorName().hashCode() * 7;
    }

    @Override
    public int compareTo(Orchestrator another) {
        return this.getActorName().compareTo(another.getActorName());
    }
}
