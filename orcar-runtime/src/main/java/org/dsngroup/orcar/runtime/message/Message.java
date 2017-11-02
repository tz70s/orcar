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
 * The Mail class is an entity of the message.
 */
public class Message {

    private MessageHeader messageHeader;

    private VariableHeader variableHeader;

    private MessagePayload messagePayload;

    /**
     * Mail constructor from bytebuffer
     * @param buffer {@link ByteBuffer}
     * @throws Exception Parsing message failed exception.
     */
    public Message (ByteBuffer buffer) throws Exception {
        // We should flip the bytebuffer outside.
        messageHeader = new MessageHeader(buffer);
        variableHeader = new VariableHeader(buffer);
        messagePayload = new MessagePayload(buffer.toString());
    }

    /**
     * Generate new message from header and payload.
     * @param messageHeader {@link MessageHeader}
     * @param variableHeader {@link VariableHeader}
     * @param messagePayload {@link MessagePayload}
     */
    public Message(MessageHeader messageHeader, VariableHeader variableHeader, MessagePayload messagePayload) {
        this.messageHeader = messageHeader;
        this.variableHeader = variableHeader;
        this.messagePayload = messagePayload;
    }

    /**
     * Get the message header.
     * @return message header.
     */
    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    /**
     * Get the variable header.
     * @return variable header.
     */
    public VariableHeader getVariableHeader() {
        return variableHeader;
    }

    /**
     * Get the message payload.
     * @return message payload.
     */
    public MessagePayload getMessagePayload() {
        return messagePayload;
    }
}
