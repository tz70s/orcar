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

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

public class RoutingReactor implements Runnable {
    // Host, port to listen on
    private InetAddress listenAddress;
    private int listenPort;

    private Selector serverSelector;

    // For accepting connections
    private ServerSocketChannel serverSocketChannel;

    // TODO: May be removed.
    private InternalSwitch internalSwitch;

    /**
     * Constructor, parameters will be config in another way.
     * @param listenAddress listened address.
     * @param listenPort listened port.
     * @param internalSwitch {@see InternalSwitch}
     * @throws IOException socket connection exception
     */
    public RoutingReactor(InetAddress listenAddress, int listenPort, InternalSwitch internalSwitch) throws IOException {
        this.listenAddress = listenAddress;
        this.listenPort = listenPort;

        // Initialized selector.
        this.serverSelector = SelectorProvider.provider().openSelector();
        // Init socket channel for accepting connections.
        this.serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress listenSocketAddress = new InetSocketAddress(listenAddress, listenPort);
        serverSocketChannel.socket().bind(listenSocketAddress);
        // TODO: the key will need to be used?
        serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
        this.internalSwitch = internalSwitch;
    }

    /**
     * Main selection handling.
     * Only handle the external requests to this node.
     */
    @Override
    public void run() {
        while (true) {
            // Keep waiting for connections
            try {
                // Wait for channel event.
                serverSelector.select();
                // Iterate the selected keys (channel, event)
                Iterator selectKeys = serverSelector.selectedKeys().iterator();
                while (selectKeys.hasNext()) {
                    SelectionKey selectedKey = (SelectionKey) selectKeys.next();
                    // Checkout valid or not.
                    if (!selectedKey.isValid()) {
                        // Discard
                        continue;
                    }
                    // TODO: need to have an another acceptor thread?
                    if (selectedKey.isAcceptable()) {
                        SocketChannel forwarderSocketChannel = serverSocketChannel.accept();
                        forwarderSocketChannel.configureBlocking(false);
                        forwarderSocketChannel.register(selectedKey.selector(),
                                SelectionKey.OP_READ);
                    }
                    // FIRE new handler in thread pool.
                    if (selectedKey.isReadable()) {
                        SocketChannel forwardSocketChannel = (SocketChannel) selectedKey.channel();
                        // TODO: Thread pool can't be used currently.
                        // RoutingScheduler.fireForwarder(new InternalForwarder(forwardSocketChannel, internalSwitch));
                        InternalForwarder inter = new InternalForwarder(forwardSocketChannel, internalSwitch);
                        inter.forward();
                    }
                    selectKeys.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();;
            }
        }
    }
}
