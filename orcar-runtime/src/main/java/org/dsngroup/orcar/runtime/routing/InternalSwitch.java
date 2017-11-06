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

import org.dsngroup.orcar.actor.MailBoxer;
import org.dsngroup.orcar.runtime.ControlService;
import org.dsngroup.orcar.runtime.message.Message;

/**
 * A switch is to transfer the internal message exchanged.
 */
public class InternalSwitch {

    private Byte nodeID;

    private Router router;

    private ControlService controlService;

    /**
     * The internal switch constructor, may be deprecated.
     * @param nodeID
     * @param router
     * @param controlService
     */
    public InternalSwitch(Byte nodeID, Router router, ControlService controlService) {
        // TODO: Consider the router to be singleton.
        this.nodeID = nodeID;
        this.router = router;
        this.controlService = controlService;
    }

    /**
     * Forward the message.
     * @param message {@link Message}
     * @throws Exception the message deserialization failed.
     */
    public void forward(Message message) throws Exception {
        if (message.getMessageHeader().getDstNodeID() != nodeID) {
            // External message, forward to the router.
            router.externalForward(message);
        } else {
            // Forward this message to control service, to generate a new task event.
            // Parse this message into lower granularity.

            // The mail boxer will be propagated to task event to be initialized.
            controlService.runNewTask(message.getMessageHeader().getDstOrchestratorID(),
                    message.getVariableHeader().getClassName(),
                    message.getMessagePayload().getMessagePayload());
        }
    }
}
