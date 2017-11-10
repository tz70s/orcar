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
import org.dsngroup.orcar.device.runtime.network.format.RuntimeServiceContextFormat;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class ContextResource extends CoapResource {

    private RuntimeServiceContext runtimeServiceContext;

    @Override
    public void handleGET(CoapExchange exchange) {
        synchronized (runtimeServiceContext) {
            // TODO: Parse incoming json format and retrieve the desired fields.
        }
        // TODO: Send back the desired fields
        exchange.respond("Get the context of this device!\n");
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        // For update runtime context
        exchange.accept();
        synchronized (runtimeServiceContext) {
            // TODO: Parse incoming json format.
        }
        // TODO: Response with success or not
        exchange.respond("Update context of this device!");
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        // TODO: May not need to have this.
        exchange.respond("Create context of this device!");
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        // TODO: The delete means the shutdown?
        exchange.respond("Delete context of this device!");
    }

    public ContextResource(RuntimeServiceContext runtimeServiceContext) {
        super("context");
        this.runtimeServiceContext = runtimeServiceContext;
    }
}
