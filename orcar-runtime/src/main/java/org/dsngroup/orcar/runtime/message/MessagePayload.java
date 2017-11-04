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
 * MessagePayload class wrap the payload with extended functionalities.
 */
public class MessagePayload {

    private String messagePayload;


    public MessagePayload(ByteBuffer byteBuffer) {
        // TODO: met length, instead of terminate characters
        // TODO: Is not a correct parsing now.
        StringBuilder messagePayloadBuilder = new StringBuilder();
        int rcount = 0;
        int ncount = 0;
        while (true) {
            // Continuous getting the byte buffer until the CRLF is match.
            char getCharacter = (char) byteBuffer.get();
            if (getCharacter == '\r') {
                rcount += 1;
            } else if (getCharacter == '\n') {
                ncount += 1;
            } else {
                rcount = 0;
                ncount = 0;
                messagePayloadBuilder.append(getCharacter);
            }
            // Match \r\n\r\n
            if (rcount == 2 && ncount == 2) {
                break;
            }

            // TODO: Unsafe checks
            if (byteBuffer.remaining() == 0) {
                // TODO: Should continue to next frame.

            }
        }
        this.messagePayload = messagePayloadBuilder.toString();
    }

    /**
     * Constructor of message payload.
     * @param messagePayload The wrapping string payload.
     */
    public MessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }

    /**
     * Get the real message payload.
     * @return string.
     */
    public String getMessagePayload() {
        // TODO: strictly checks.
        return messagePayload;
    }

    /**
     * Override toString for equivalent access.
     * @return string.
     */
    @Override
    public String toString() {
        return messagePayload;
    }
}
