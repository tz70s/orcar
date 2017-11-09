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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.net.InetAddress;

/**
 * The RoutingTable store the proxy(ip) address with associated node-id.
 */
public class RoutingTable {

    private static Map<Byte, InetAddress> routingTable = new ConcurrentHashMap<>();

    /**
     * Only one routing table.
     */
    private RoutingTable() {
        // Singleton.
    }

    /**
     * Get IP address from routing table
     * @param  nodeID node ID of a node.
     * @return IP address
     */
    public static InetAddress lookUp(Byte nodeID) throws Exception {
        if (!routingTable.containsKey(nodeID)) {
            // Doesn't have the routing rules, ask controller
            // TODO: Ask and restore.

            // if not found
            throw new Exception("Can't resolve the host name to ip address");
        }
        return routingTable.get(nodeID);
    }

    /**
     * Store a routing rule to routing table.
     * @param nodeID node ID of a node.
     * @param ipAddress The IP address of a node.
     */
    public static void store(Byte nodeID, InetAddress ipAddress) {
        routingTable.put(nodeID, ipAddress);
    }

}
