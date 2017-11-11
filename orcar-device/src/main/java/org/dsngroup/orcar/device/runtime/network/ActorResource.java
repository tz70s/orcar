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

package org.dsngroup.orcar.device.runtime.network;

import org.dsngroup.orcar.device.runtime.network.format.BaseActorFormat;
import org.dsngroup.orcar.device.runtime.network.format.Processor;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

public class ActorResource extends ConcurrentCoapResource {

    @Override
    public void handleGET(CoapExchange exchange) {
        // Get the associated context of an actor
        String actorContext = Processor.processActorPath(exchange.getRequestOptions().getLocationPath());
        System.out.println(exchange.getRequestOptions().getLocationPath());
        exchange.respond(actorContext);
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        exchange.accept();
        String query = exchange.getRequestOptions().getLocationQueryString();
        exchange.respond("Update an actor with data of this device!" + exchange.getRequestOptions().getUriQueryString());
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        System.out.println(exchange.getRequestOptions().getLocationPathString());
        for (byte[] etag: exchange.getRequestOptions().getETags()) {
            System.out.println(etag.length);
            String newEtag = new String(etag);
            System.out.println(newEtag);
        }
        BaseActorFormat baseActorFormat = BaseActorFormat.toObject(exchange.getRequestText());
        System.out.println(baseActorFormat.getClassName());
        exchange.respond("Create an actor of this device!" + baseActorFormat.getClassName());
        // Existed model
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        exchange.respond("Delete an actor of this device!");
    }

    public ActorResource() {
        super("actor", 2);
    }
}
