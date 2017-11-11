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

import org.dsngroup.orcar.device.runtime.tree.Actor;
import org.dsngroup.orcar.device.runtime.tree.ActorSystem;
import org.dsngroup.orcar.device.runtime.tree.Processor;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

public class ActorResource extends ConcurrentCoapResource {

    private ActorSystem rootActorSystem;

    private Processor processor;

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.accept();
        // Get the associated context of an actor
        Actor actor = processor.processActorGET(exchange.getRequestOptions().getLocationPath());
        if (actor != null) {
            exchange.respond(actor.getActorName());
        } else {
            exchange.respond("Not correct path.");
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        exchange.accept();
        String query = exchange.getRequestOptions().getLocationQueryString();
        exchange.respond("Update an actor with data of this device!" + exchange.getRequestOptions().getUriQueryString());
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.accept();
        Actor actor = processor.processActorPOST(exchange.getRequestOptions().getLocationPath());
        if (actor != null) {
            exchange.respond(actor.getActorName());
        } else {
            exchange.respond("Not correct path");
        }
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        exchange.accept();
        boolean result = processor.processActorDELETE(exchange.getRequestOptions().getLocationPath());
        if (result) {
            exchange.respond("Successful delete");
        } else {
            exchange.respond("Not correct path");
        }
    }

    public ActorResource(ActorSystem actorSystem, Processor processor) {
        super("actor", 2);
        this.rootActorSystem = actorSystem;
        this.processor = processor;
    }
}
