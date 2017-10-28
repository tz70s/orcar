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

import java.util.UUID;

/**
 * The RuntimeServiceContext records the context(configuration) of the RuntimeService.
 */
public class RuntimeServiceContext {
    private final UUID runtimeServiceID;

    private int runtimeThreadPoolSize;

    /**
     * Default constructor of RuntimeServiceContext
     */
    public RuntimeServiceContext() {
        this.runtimeServiceID = UUID.randomUUID();
        this.runtimeThreadPoolSize = 4;
    }

    /**
     * Overloaded constructor of RuntimeServiceContext.
     * @param userDefinedUUID the uuid of the RuntimeService.
     * @param runtimeThreadPoolSize The thread pool size of runtime.
     */
    public RuntimeServiceContext(UUID userDefinedUUID, int runtimeThreadPoolSize) {
        this.runtimeServiceID = userDefinedUUID;
        this.runtimeThreadPoolSize = runtimeThreadPoolSize;
    }
}
