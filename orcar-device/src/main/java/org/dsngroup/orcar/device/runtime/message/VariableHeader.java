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

package org.dsngroup.orcar.device.runtime.message;

import java.nio.ByteBuffer;

public class VariableHeader {

    private String className;

    /**
     * Constructor of variable header.
     * @param buffer {@link ByteBuffer}
     * @throws Exception Parsing failed from variable header.
     */
    public VariableHeader(ByteBuffer buffer) throws Exception {
        // TODO: Is not a correct parsing now.
        StringBuilder classNameBuilder = new StringBuilder();
        int rcount = 0;
        int ncount = 0;
        while (true) {
            // Continuous getting the buffer until the CRLF is match.
            char getCharacter = (char) buffer.get();
            if (getCharacter == '\r') {
                rcount += 1;
            } else if (getCharacter == '\n') {
                ncount += 1;
            } else {
                rcount = 0;
                ncount = 0;
                classNameBuilder.append(getCharacter);
            }
            // Match \r\n\r\n
            if (rcount == 2 && ncount == 2) {
                break;
            }
            if (classNameBuilder.length() >= 80) {
                throw new Exception("Parsing class name failed, to long!");
            }
        }
        className = classNameBuilder.toString();
    }

    /**
     * Constructor for convenient, parse from string.
     * @param className class name.
     */
    public VariableHeader(String className) {
        this.className = className;
    }

    /**
     * Get class name.
     * @return class name.
     */
    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return className + "\r\n\r\n";
    }
}
