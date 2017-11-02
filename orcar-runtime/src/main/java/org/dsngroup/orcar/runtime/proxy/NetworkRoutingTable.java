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

package org.dsngroup.orcar.runtime.proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.net.InetAddress;

/**
 * The NetworkRoutingTable store the proxy(ip) address with associated node-id.
 */
public class NetworkRoutingTable {

    // The node-id is represented in a Character.
    private Map<Character, InetAddress> routingTable;

    /**
     * Constructor of NetworkRoutingTable.
     */
    public NetworkRoutingTable() {
        // TODO: may be a singleton.
        this.routingTable = new ConcurrentHashMap<>();
        // TODO: should store the controller at first.
    }

    /**
     * Get IP address from routing table
     * @param nodeID Character-based for represented a node.
     * @return IP address
     */
    public InetAddress lookUp(Character nodeID) {
        if (!routingTable.containsKey(nodeID)) {
            // Doesn't have the routing rules, ask controller
            // TODO: Ask and restore.
        }
        return routingTable.get(nodeID);
    }

    /**
     * Store a routing rule to routing table.
     * @param nodeID Character-based for represented a node.
     * @param ipAddress The IP address of a node.
     */
    public void store(Character nodeID, InetAddress ipAddress) {
        routingTable.put(nodeID, ipAddress);
    }

}
