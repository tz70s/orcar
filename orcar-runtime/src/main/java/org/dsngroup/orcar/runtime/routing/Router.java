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

package org.dsngroup.orcar.runtime.routing;

import org.dsngroup.orcar.runtime.message.Message;

import java.net.InetAddress;

/**
 * The router is response for routes the incoming connection to the target destination.
 */
public class Router {

    private Byte nodeID;
    // TODO: may be deprecated.
    private InternalSwitch internalSwitch;

    private RoutingReactor routingReactor;

    /**
     * Constructor of router, may be modified into accept a single context.
     * @param nodeID
     * @param internalSwitch
     */
    public Router(Byte nodeID, InternalSwitch internalSwitch, int routingThreadPoolSize) throws Exception {
        this.nodeID = nodeID;
        this.internalSwitch = internalSwitch;
        RoutingScheduler.configScheduler(routingThreadPoolSize);
        routingReactor = new RoutingReactor(InetAddress.getByName("127.0.0.1"), 9222, internalSwitch);
        Thread reactorThread = new Thread(routingReactor);
        reactorThread.start();
    }

    /**
     * Do the external forwarding.
     * @param message {@see Message}
     * @throws Exception forwarder failed.
     */
    public void externalForward(Message message) throws Exception {
        ExternalForwarder externalForwarder = new ExternalForwarder(message);
        RoutingScheduler.fireForwarder(externalForwarder);
    }
}
