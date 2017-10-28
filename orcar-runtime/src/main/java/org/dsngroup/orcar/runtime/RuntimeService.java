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

import org.dsngroup.orcar.orchestrator.FunctionalActor;
import org.dsngroup.orcar.orchestrator.Orchestrator;

/**
 * The RuntimeService is a entry point of orcar.
 */
public class RuntimeService {

    private RuntimeServiceContext runtimeServiceContext;

    public RuntimeService(RuntimeServiceContext runtimeServiceContext) {
        this.runtimeServiceContext = runtimeServiceContext;
        // Initialized RuntimeClassLoader.
        // TODO: Reconsider the initialization place.
        RuntimeClassLoader.init(runtimeServiceContext.getLocalClassPath());
    }

    public RuntimeService serve() {
        // Serve the runtime
        System.out.println("Start a RuntimeService");

        try {
            Orchestrator orc = new Orchestrator("Actor-01",
                    (FunctionalActor) RuntimeClassLoader.loadClass("org.dsngroup.orcar.sample.SourceAndPrint"));
            orc.run();
        } catch (Exception e) {
            // Ignore this
            e.printStackTrace();
        }

        return this;
    }

    public RuntimeServiceContext getRuntimeServiceContext() {
        return runtimeServiceContext;
    }

    public static void main(String[] args) {
        RuntimeService srv = new RuntimeService(new RuntimeServiceContext()).serve();
    }
}
