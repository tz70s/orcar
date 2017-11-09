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
import org.dsngroup.orcar.device.runtime.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * The router is response for routes the incoming connection to the target destination.
 */
public class Router {

    private Byte nodeID;
    // TODO: may be deprecated.
    private InternalSwitch internalSwitch;

    private RoutingScheduler routingScheduler;
    private RoutingReactor routingReactor;

    private String listenAddress;

    private int listenPort;

    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    private Thread reactorThread;

    /**
     * Constructor of router, may be modified into accept a single context.
     * @param nodeID
     * @param controlService
     * @param routingThreadPoolSize
     */
    public Router(Byte nodeID, ControlService controlService, int routingThreadPoolSize) throws Exception {
        this.nodeID = nodeID;
        // Propagate into internal switch
        internalSwitch = new InternalSwitch(nodeID, this, controlService);
        routingScheduler = new RoutingScheduler(routingThreadPoolSize);
        this.listenAddress = "0.0.0.0";
        this.listenPort = 9222;
        routingReactor = new RoutingReactor(InetAddress.getByName(listenAddress), listenPort, internalSwitch,
                routingScheduler);
        logger.info("Proxy agent is running at " + listenAddress + ":" + listenPort);
        reactorThread = new Thread(routingReactor);
        reactorThread.start();
    }

    /**
     * Do the external forwarding.
     * @param message {@see Message}
     * @throws Exception forwarder failed.
     */
    public void externalForward(Message message) throws Exception {
        ExternalForwarder externalForwarder = new ExternalForwarder(message);
        routingScheduler.fireForwarder(externalForwarder);
    }

    public void close() throws Exception {
        // Interrupt it to close out.
        reactorThread.interrupt();
    }
}
