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

package org.dsngroup.orcar.device.perf.runtime;

import org.dsngroup.orcar.device.runtime.ControlService;
import org.dsngroup.orcar.device.runtime.RuntimeService;
import org.dsngroup.orcar.device.runtime.tree.ActorSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SequentialExecution {

    private ControlService controlServiceBinding;

    private static final Logger logger = LoggerFactory.getLogger(SequentialExecution.class);

    private ActorSystem actorSystem;

    public SequentialExecution(RuntimeService runtimeService) {
        controlServiceBinding = runtimeService.getControlService();
        actorSystem = new ActorSystem(null,"SequentialExecution");
    }

    public SequentialExecution perfOnce() throws Exception {
        logger.info("Start sequential execution");
        // Start running
        // TODO: Can't get the finishing time, currently.
        // TODO: Figure out completable future to track this.
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                controlServiceBinding.runNewTask(actorSystem, "" + i,
                        "org.dsngroup.orcar.device.perf.runtime.actors.CountingActor",
                        "{\"Actor\":" + i + ",\"Payload\":" + j + "}");
            }
            // In this example, the blocking in the internal thread, will cause starvation.
        }
        return this;
    }
}
