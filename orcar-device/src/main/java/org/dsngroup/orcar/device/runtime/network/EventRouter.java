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

import org.dsngroup.orcar.device.runtime.RuntimeServiceContext;
import org.eclipse.californium.core.CoapServer;

/**
 * Trying to use coap instead.
 */
public class EventRouter {

    private RuntimeServiceContext runtimeServiceContext;

    private CoapServer coapServer;

    public EventRouter(RuntimeServiceContext runtimeServiceContext) {
        this.runtimeServiceContext = runtimeServiceContext;
        this.coapServer = new CoapServer();
        ContextResource contextResource = new ContextResource(runtimeServiceContext);
        ActorResource actorResource = new ActorResource();
        coapServer.add(contextResource, actorResource);
    }

    public EventRouter start() {
        coapServer.start();
        return this;
    }

    public static void main(String[] args) {
        EventRouter eventRouter = new EventRouter(new RuntimeServiceContext()).start();
    }
}
