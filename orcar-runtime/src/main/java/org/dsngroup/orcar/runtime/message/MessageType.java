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

public enum MessageType {
    CONTROL((byte) 0),
    CONTROLACK((byte) 1),
    JSON((byte) 2),
    JSONACK((byte) 3);

    public final byte value;

    /**
     * Enum constructor for byte value.
     * @param value value.
     */
    MessageType(byte value) {
        this.value = value;
    }


}
