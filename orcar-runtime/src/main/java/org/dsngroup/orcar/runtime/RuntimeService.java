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

package org.dsngroup.orcar.runtime;

import org.dsngroup.orcar.runtime.task.TaskController;

/**
 * The RuntimeService is a entry point of orcar.
 */
public class RuntimeService {

    private RuntimeServiceContext runtimeServiceContext;

    private ControlService controlService;

    private TaskController taskController;

    public RuntimeService(RuntimeServiceContext runtimeServiceContext) {
        this.runtimeServiceContext = runtimeServiceContext;
        // Initialized RuntimeClassLoader.

        // TODO: Reconsider the initialization place.
        // Init RuntimeClassLoader
        RuntimeClassLoader.init(runtimeServiceContext.getLocalClassPath());

        // Init a taskController
        // TODO: Need to have a better place.
        this.taskController = new TaskController();
        controlService = new ControlService(taskController);

        // Init runtime scheduler
        try {
            RuntimeScheduler.configScheduler(runtimeServiceContext.getRuntimeThreadPoolSize());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Incorrect thread pool size.");
            // drop
            System.exit(1);
        }
    }

    public RuntimeService serve() {
        // Serve the runtime
        System.out.println("Start a RuntimeService");

        controlService.runNewTask("Actor-01", "org.dsngroup.orcar.sample.SourceAndPrint");

        return this;
    }

    public RuntimeServiceContext getRuntimeServiceContext() {
        return runtimeServiceContext;
    }

    public static void main(String[] args) {
        RuntimeService srv = new RuntimeService(new RuntimeServiceContext()).serve();
    }
}
