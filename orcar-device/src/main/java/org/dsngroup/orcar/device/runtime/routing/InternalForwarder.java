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

import org.dsngroup.orcar.device.runtime.message.MessageHeader;
import org.dsngroup.orcar.device.runtime.message.Message;
import org.dsngroup.orcar.device.runtime.message.MessagePayload;
import org.dsngroup.orcar.device.runtime.message.VariableHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Equivalent to the handler.
 */
public class InternalForwarder implements Forwarder {

    private SocketChannel socketChannel;

    private InternalSwitch internalSwitch;

    private static final Logger logger = LoggerFactory.getLogger(InternalForwarder.class);

    /**
     * TODO: Parameters may be reconfigured.
     * @param socketChannel {@see SocketChannel}
     * @param internalSwitch {@see InternalSwitch}
     */
    public InternalForwarder(SocketChannel socketChannel, InternalSwitch internalSwitch) {
        this.socketChannel = socketChannel;
        this.internalSwitch = internalSwitch;
    }

    /**
     * Handling message.
     * Encode byte buffer into message here and request for a new event.
     * To avoid any blocking occured, this is run in a thread pool.
     * TODO: The decoder should be rewrite for more robust and capable to deal with fragments.
     */
    @Override
    public void forward() {
        // Forward the incoming
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            // First try, for parsing message header and variable header.
            int reads = socketChannel.read(byteBuffer);

            // Checkout if null reads at first.
            if (reads < 0) {
                // Discard
                socketChannel.close();
                return;
            }

            byteBuffer.flip();
            MessageHeader messageHeader = new MessageHeader(byteBuffer);
            VariableHeader variableHeader = new VariableHeader(byteBuffer);
            MessagePayload messagePayload = new MessagePayload(byteBuffer);

            // Continue if still have data, append on the payload.
            byteBuffer.clear();
            while (socketChannel.read(byteBuffer) >= 0) {
                byteBuffer.flip();
                // TODO: Append fragments
            }
            // Finished
            Message message = new Message(messageHeader, variableHeader, messagePayload);
            internalSwitch.forward(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                socketChannel.close();
            } catch (IOException socketCantClose) {
                logger.error("Socket can't close! ", socketCantClose.getMessage());
            }
        }
    }
}
