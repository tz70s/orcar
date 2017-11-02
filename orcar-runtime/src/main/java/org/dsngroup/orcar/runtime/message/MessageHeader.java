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

package org.dsngroup.orcar.runtime.message;

import java.nio.ByteBuffer;

/**
 * MessageHeader represented the header fields of a {@link Message}
 */
public class MessageHeader {

    // The Mail Header should be lightweight.
    // TODO: The messageType is waste.
    // TODO: May extends the size.
    private byte messageType;
    private byte srcNodeID;
    private byte srcOrchestratorID;
    private byte dstNodeID;
    private byte dstOrchestratorID;

    /**
     * Default constructor, which constructs from bytes[]
     * @param buffer {@link ByteBuffer}
     * @throws Exception Parsing failed.
     */
    public MessageHeader(ByteBuffer buffer) throws Exception {
        // Parse the header field
        byte[] bytes = new byte[8];
        buffer.get(bytes, 0, 8);
        messageType = bytes[0];
        srcNodeID = bytes[1];
        srcOrchestratorID = bytes[2];
        dstNodeID = bytes[3];
        dstOrchestratorID = bytes[4];
    }

    /**
     * Convenient utilities for construct MessageHeader.
     * @param rawHeader The string based raw header.
     * @throws Exception Parsing failed.
     */
    public MessageHeader(String rawHeader) throws Exception {
        this((byte) rawHeader.charAt(0), (byte) rawHeader.charAt(1), (byte) rawHeader.charAt(2),
                (byte) rawHeader.charAt(3), (byte) rawHeader.charAt(4));
    }

    /**
     * The constructor for generating MessageHeader from byte options.
     * @param messageType The message type of the message.
     * @param srcNodeID The node ID of source node.
     * @param srcOrchestratorID The orchestrator ID of source orchestrator.
     * @param dstNodeID The destination node ID.
     * @param dstOrchestratorID The destination orchestrator ID.
     */
    public MessageHeader(Byte messageType, Byte srcNodeID, Byte srcOrchestratorID, Byte dstNodeID,
                         Byte dstOrchestratorID) {
        this.messageType = messageType;
        this.srcNodeID = srcNodeID;
        this.srcOrchestratorID = srcOrchestratorID;
        this.dstNodeID = dstNodeID;
        this.dstOrchestratorID = dstOrchestratorID;
    }

    /**
     * Override the toString.
     * @return String
     */
    @Override
    public String toString() {
        byte[] bytes = {messageType, srcNodeID, srcOrchestratorID, dstNodeID, dstOrchestratorID, 0, '\r', '\n'};
        return bytes.toString();
    }

    /**
     * Get the message type.
     * @return message type.
     */
    public byte getMessageType() {
        return messageType;
    }

    /**
     * Get the source node ID.
     * @return source node ID.
     */
    public byte getSrcNodeID() {
        return srcNodeID;
    }

    /**
     * Get the source orchestrator ID.
     * @return source orchestrator ID.
     */
    public byte getSrcOrchestratorID() {
        return srcOrchestratorID;
    }

    /**
     * Get the destination node ID.
     * @return destination node ID.
     */
    public byte getDstNodeID() {
        return dstNodeID;
    }

    /**
     * Get the destination orchestrator ID.
     * @return destination orchestrator ID.
     */
    public byte getDstOrchestratorID() {
        return dstOrchestratorID;
    }
}
