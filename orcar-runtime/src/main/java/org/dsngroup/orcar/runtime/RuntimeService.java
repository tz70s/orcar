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

import org.dsngroup.orcar.runtime.message.Message;
import org.dsngroup.orcar.runtime.message.MessageHeader;
import org.dsngroup.orcar.runtime.message.MessagePayload;
import org.dsngroup.orcar.runtime.message.VariableHeader;
import org.dsngroup.orcar.runtime.routing.Router;
import org.dsngroup.orcar.runtime.routing.InternalSwitch;
import org.dsngroup.orcar.runtime.task.TaskController;

/**
 * The RuntimeService is a entry point of orcar.
 */
public class RuntimeService {

    private RuntimeServiceContext runtimeServiceContext;

    private ControlService controlService;

    private TaskController taskController;

    private Router router;

    private InternalSwitch internalSwitch;

    /**
     * RuntimeService constructs an entry runtime.
     * <code>
     *     RuntimeService srv = new RuntimeService(ctx);
     * </code>
     * @param runtimeServiceContext {@link RuntimeServiceContext}
     */
    public RuntimeService(RuntimeServiceContext runtimeServiceContext) {
        this.runtimeServiceContext = runtimeServiceContext;
        // Initialized RuntimeClassLoader.

        // TODO: Reconsider the initialization place.
        // Init RuntimeClassLoader
        RuntimeClassLoader.init(runtimeServiceContext.getLocalClassPath());

        // Init a taskController
        // TODO: Need to have a better place.
        taskController = new TaskController();
        controlService = new ControlService(taskController);

        // Init router and internal switch
        router = new Router((byte) '1');
        internalSwitch = new InternalSwitch((byte) '1', router, controlService);


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

    /**
     * Serve the runtime service.
     * @return this, for chaining method.
     */
    public RuntimeService serve() {
        // Serve the runtime
        System.out.println("Start a RuntimeService");

        try {
            Message message = new Message(new MessageHeader("111110\r\n"),
                    new VariableHeader("org.dsngroup.orcar.sample.SourceAndPrint"), new MessagePayload("{}"));
            internalSwitch.forward(message);
            Message anotherMessage = new Message(new MessageHeader("111120\r\n"),
                    new VariableHeader("org.dsngroup.orcar.sample.PrintMailContent"),
                    new MessagePayload("{\"Hello\": \"World\"}"));
            internalSwitch.forward(anotherMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Get the associated runtime service context.
     * @return {@link RuntimeServiceContext}
     */
    public RuntimeServiceContext getRuntimeServiceContext() {
        return runtimeServiceContext;
    }

    public static void main(String[] args) {
        RuntimeService srv = new RuntimeService(new RuntimeServiceContext()).serve();
    }
}
