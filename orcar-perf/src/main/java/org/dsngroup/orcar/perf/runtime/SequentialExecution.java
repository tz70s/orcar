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

package org.dsngroup.orcar.perf.runtime;

import org.dsngroup.orcar.runtime.ControlService;
import org.dsngroup.orcar.runtime.RuntimeService;
import org.dsngroup.orcar.runtime.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SequentialExecution {

    private Router routerBinding;

    private ControlService controlServiceBinding;

    private static final Logger logger = LoggerFactory.getLogger(SequentialExecution.class);

    public SequentialExecution(RuntimeService runtimeService) {
        routerBinding = runtimeService.getRouter();
        controlServiceBinding = runtimeService.getControlService();
    }

    public SequentialExecution perfOnce() throws Exception {
        logger.info("Start sequential execution");
        // Start running
        // TODO: Can't get the finishing time, currently.
        // TODO: Figure out completable future to track this.
        for (int i = 0; i < 10; i++)
            controlServiceBinding.runNewTask((byte) '1',
                "org.dsngroup.orcar.perf.runtime.actors.CountingActor","hello");
        return this;
    }
}
