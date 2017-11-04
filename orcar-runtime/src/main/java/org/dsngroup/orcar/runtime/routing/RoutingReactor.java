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
     * @param listenAddress
     * @param listenPort
     * @param internalSwitch
     * @throws IOException
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
    }

    /**
     * Main selection handling.
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
                    SelectionKey acceptedKey = (SelectionKey) selectKeys.next();
                    selectKeys.remove();
                    // Checkout valid or not.
                    if (!acceptedKey.isValid()) {
                        // Discard
                        continue;
                    }
                    // TODO: need to have an another acceptor thread?
                    if (acceptedKey.isAcceptable()) {
                        SocketChannel forwarderSocketChannel = serverSocketChannel.accept();
                        forwarderSocketChannel.configureBlocking(false);
                        forwarderSocketChannel.register(acceptedKey.selector(),
                                SelectionKey.OP_READ);
                    }
                    // FIRE new handler in thread pool.
                    if (acceptedKey.isReadable()) {
                        SocketChannel forwardSocketChannel = (SocketChannel) acceptedKey.channel();
                        RoutingScheduler.fireForwarder(new InternalForwarder(forwardSocketChannel, internalSwitch));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();;
            }
        }
    }
}
