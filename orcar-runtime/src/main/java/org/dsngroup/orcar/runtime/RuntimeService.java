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

import org.dsngroup.orcar.runtime.routing.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RuntimeService is a entry point of orcar.
 */
public class RuntimeService {

    private RuntimeServiceContext runtimeServiceContext;

    private ControlService controlService;

    private Router router;

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
        // Initialized RuntimeClassLoader.

        // TODO: Need to have a better place.
        controlService = new ControlService(runtimeServiceContext);

        try {
            router = new Router(runtimeServiceContext.getNodeID(), controlService, 4);
        } catch (Exception e) {
            logger.error("Router settings failed. " + e.getMessage());

            // Use the default routing thread pool size.
            try {
                router = new Router(runtimeServiceContext.getNodeID(), controlService, 4);
            } catch (Exception finalex) {
                logger.error("Not recoverable, close out program. " + finalex.getMessage());
                System.exit(1);
            }
        }
    }

    /**
     * Serve the runtime service.
     * @return this, for chaining method.
     */
    public RuntimeService serve() {
        logger.info("Serving runtime!");
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
     * For perf test only.
     * @return {@link Router}
     */
    public Router getRouter() {
        return router;
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
     * @throws Exception Propagate from {@link Router}
     */
    public void close() throws Exception {
        router.close();
    }

    public static void main(String[] args) {
        RuntimeService srv = new RuntimeService(new RuntimeServiceContext()).serve();
    }
}
