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

package org.dsngroup.orcar.device.runtime.routing;

import org.dsngroup.orcar.device.runtime.ControlService;
import org.dsngroup.orcar.device.runtime.tree.Actor;
import org.dsngroup.orcar.device.runtime.tree.ActorSystem;
import org.dsngroup.orcar.device.runtime.tree.Orchestrator;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

public class ActorResource extends ConcurrentCoapResource {

    private ActorSystem rootActorSystem;

    private Processor processor;

    private ControlService controlService;

    /**
     * Use get to retrieve the context of an actor (a.k.a actor-system or orchestrator)
     * @param exchange
     */
    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.accept();
        // Get the associated context of an actor
        Actor actor = processor.processActorGET(exchange);
        if (actor != null) {
            exchange.respond(Actor.toJsonString(actor));
        } else {
            exchange.respond(errorMessage("GET", "Invalid Location-Path."));
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        exchange.accept();

        ActorSystem parentActorSystem;

        // Use the first etag as class name
        String className = processor.processLocationQuery(exchange);
        if (className == "") {
            exchange.respond(errorMessage("PUT", "Doesn't support in Actor-System, " +
                    "and Orchestrator must carry with Location Query"));
            return;
        } else {
            Object[] tuple = processor.processActorPUT(exchange);
            if (tuple == null) {
                exchange.respond(errorMessage("PUT", "Invalid Location-Path."));
            } else {
                parentActorSystem = (ActorSystem) tuple[0];
                String orchestratorName = (String) tuple[1];
                // Call control service
                Orchestrator orchestrator = controlService.runNewTask(parentActorSystem, orchestratorName, className, exchange.getRequestText());
                exchange.respond(successMessage("PUT", Actor.toJsonString(orchestrator)));
            }
        }
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.accept();
        Actor actor = processor.processActorPOST(exchange);
        if (actor != null) {
            exchange.respond(Actor.toJsonString(actor));
        } else {
            exchange.respond(errorMessage("POST", "Invalid Location-Path."));
        }
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        exchange.accept();
        boolean result = processor.processActorDELETE(exchange);
        if (result) {
            exchange.respond("Successful delete");
        } else {
            exchange.respond(errorMessage("DELETE", "Invalid Location-Path."));
        }
    }

    private String successMessage(String method, String message) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("Successful ")
                .append(method)
                .append(": ")
                .append(message)
                .toString();
    }

    private String errorMessage(String method, String message) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("Error ")
                .append(method)
                .append(": ")
                .append(message)
                .toString();
    }

    public ActorResource(ActorSystem actorSystem, Processor processor, ControlService controlService) {
        super("actor", 2);
        this.rootActorSystem = actorSystem;
        this.processor = processor;
        this.controlService = controlService;
    }
}
