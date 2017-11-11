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

package org.dsngroup.orcar.device.runtime;

import org.dsngroup.orcar.device.runtime.routing.EventRouter;
import org.dsngroup.orcar.device.runtime.tree.ActorSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RuntimeService is a entry point of orcar.
 */
public class RuntimeService {

    private RuntimeServiceContext runtimeServiceContext;

    private ControlService controlService;

    private ActorSystem rootActorSystem;

    private EventRouter eventRouter;

    private static final Logger logger = LoggerFactory.getLogger(RuntimeService.class);

    /**
     * RuntimeService constructs an entry runtime.
     * <code>
     *     RuntimeService srv = new RuntimeService(ctx);
     * </code>
     * @param runtimeServiceContext {@link RuntimeServiceContext}
     */
    public RuntimeService(RuntimeServiceContext runtimeServiceContext) {
        this.runtimeServiceContext = runtimeServiceContext;
        this.controlService = new ControlService(runtimeServiceContext);
        this.rootActorSystem = new ActorSystem(null, "root");
        this.eventRouter = new EventRouter(runtimeServiceContext, rootActorSystem);
    }

    /**
     * Serve the runtime service.
     * @return this, for chaining method.
     */
    public RuntimeService serve() {
        logger.info("Serving runtime!");
        eventRouter.start();
        return this;
    }

    /**
     * For perf test only.
     * @return {@link ControlService}
     */
    public ControlService getControlService() {
        return controlService;
    }

    /**
     * Get the associated runtime service context.
     * @return {@link RuntimeServiceContext}
     */
    public RuntimeServiceContext getRuntimeServiceContext() {
        return runtimeServiceContext;
    }

    /**
     * Close down the runtime service.
     * @throws Exception
     */
    public void close() throws Exception {
    }

    public static void main(String[] args) {
        RuntimeService srv = new RuntimeService(new RuntimeServiceContext()).serve();
    }
}
