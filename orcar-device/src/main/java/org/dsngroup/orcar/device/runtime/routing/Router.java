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
import org.dsngroup.orcar.device.runtime.RuntimeServiceContext;
import org.dsngroup.orcar.device.runtime.tree.ActorSystem;
import org.eclipse.californium.core.CoapServer;

/**
 * Trying to use coap instead.
 */
public class Router implements AutoCloseable{

    private RuntimeServiceContext runtimeServiceContext;

    private CoapServer coapServer;

    private ControlService controlService;

    private ActorSystem actorSystem;

    public void initServer() {
        coapServer = new CoapServer();
        ContextResource contextResource = new ContextResource(runtimeServiceContext);
        Processor processor = new Processor(actorSystem);
        ActorResource actorResource = new ActorResource(actorSystem, processor, controlService);
        coapServer.add(contextResource, actorResource);
    }

    public Router(RuntimeServiceContext runtimeServiceContext, ControlService controlService, ActorSystem actorSystem) {
        this.runtimeServiceContext = runtimeServiceContext;
        this.controlService = controlService;
        this.actorSystem = actorSystem;

        ActorSystem childActorSystem = new ActorSystem(this.actorSystem, "childActorSystem");
        ActorSystem sibactorSystem = new ActorSystem(this.actorSystem, "sibChildActorSystem");

        initServer();
    }

    public Router start() {
        coapServer.start();
        return this;
    }

    @Override
    public void close() throws Exception {
        this.coapServer.destroy();
    }
}
