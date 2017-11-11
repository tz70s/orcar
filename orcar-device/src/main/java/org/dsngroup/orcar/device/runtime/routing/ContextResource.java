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

import com.google.gson.Gson;
import org.dsngroup.orcar.device.runtime.RuntimeServiceContext;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class ContextResource extends CoapResource {

    private RuntimeServiceContext runtimeServiceContext;

    private static Gson gson = new Gson();

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(toJsonString());
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        // For update runtime context
        exchange.accept();
        RuntimeServiceContext runtimeServiceContext = toObject(exchange.getRequestText());

        // Parse the incoming json fields
        synchronized (this.runtimeServiceContext) {
            // TODO: Not correct
            if (runtimeServiceContext.getLocalClassPath() != null) {
                this.runtimeServiceContext.setLocalClassPath(runtimeServiceContext.getLocalClassPath());
            }

            if (runtimeServiceContext.getRuntimeThreadPoolSize() != 0) {
                this.runtimeServiceContext.setRuntimeThreadPoolSize(runtimeServiceContext.getRuntimeThreadPoolSize());
            }
        }
        // TODO: Response with success or error message
        exchange.respond(toJsonString());
    }

    /**
     * Serialized runtime service context into json string
     * @return json string
     */
    public String toJsonString() {
        return gson.toJson(runtimeServiceContext);
    }

    /**
     * Parse json string to runtime service context format
     * @param jsonString json string
     * @return runtime service context format
     */
    public static RuntimeServiceContext toObject(String jsonString) {
        return gson.fromJson(jsonString, RuntimeServiceContext.class);
    }

    public ContextResource(RuntimeServiceContext runtimeServiceContext) {
        super("context");
        this.runtimeServiceContext = runtimeServiceContext;
    }
}
