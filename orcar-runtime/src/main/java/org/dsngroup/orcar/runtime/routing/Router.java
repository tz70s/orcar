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

/**
 * The router is response for routes the incoming connection to the target destination.
 */
public class Router {

    private Byte nodeID;

    public Router(Byte nodeID) {
        this.nodeID = nodeID;
    }

    /**
     * Forward message to either external or internal switch.
     * @param message {@link Message}
     */
    public void forward(Message message) {

    }

}
