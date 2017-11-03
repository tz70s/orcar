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
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ExternalForwarder implements Forwarder {

    private Message message;

    /**
     * Constructor of ExternalForwarder, encapsulate the forwarding message.
     * @param message {@see Message}
     */
    public ExternalForwarder(Message message) {
        this.message = message;
    }

    /**
     * Forward message to external orchestrator.
     */
    public void forward() {
        InetAddress targetAddress;
        // First lookup the ip.
        try {
            // TODO: Blocking currently, need a better way?
            targetAddress = RoutingTable.lookUp(message.getMessageHeader().getDstNodeID());
        } catch (Exception e) {
            // Discard
            e.printStackTrace();
            return;
        }

        // Socket channel for external connection.
        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();
            // TODO: The port should be modifiable.
            socketChannel.connect(new InetSocketAddress(targetAddress, 6257));
            String messageRawString = message.toString();
            ByteBuffer buffer = ByteBuffer.allocate(messageRawString.length());
            // TODO: May not need to clear, actually.
            buffer.clear();
            buffer.put(messageRawString.getBytes());
            // Makes buffer position goes back.
            buffer.flip();
            // Write channel into buffer
            // TODO: NEED to investigate a more performant way.
            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }
        } catch (Exception e) {
            // Discard
            // TODO: log
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (Exception e) {
                // Discard
                // TODO: log
                e.printStackTrace();
            }
        }
    }

}
