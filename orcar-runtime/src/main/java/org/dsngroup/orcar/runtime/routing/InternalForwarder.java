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

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Equivalent to the handler.
 */
public class InternalForwarder implements Forwarder {

    private SocketChannel socketChannel;

    private InternalSwitch internalSwitch;

    /**
     * Parameters may be reconfigured.
     * @param socketChannel
     * @param internalSwitch
     */
    public InternalForwarder(SocketChannel socketChannel, InternalSwitch internalSwitch) {
        this.socketChannel = socketChannel;
    }

    /**
     * Handling message.
     */
    @Override
    public void forward() {
        // Forward the incoming
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            int reads = socketChannel.read(byteBuffer);
            Message message = new Message(byteBuffer);
            // EOF
            if (reads == -1) {
                socketChannel.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
