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

import org.dsngroup.orcar.device.runtime.RuntimeService;
import org.dsngroup.orcar.device.runtime.RuntimeServiceContext;

/**
 * (Device) Runtime performance test entry, which gathers all associated perf tests.
 */
public class RuntimePerfEntry {

    // Prepare a before all
    public RuntimePerfEntry() throws Exception {
        // Set thread pool size 8
        // Set class path into here.
        RuntimeServiceContext ctx = new RuntimeServiceContext(8,
                "/Users/Tzuchiao/workspace/java/orcar/orcar-perf/target/scala-2.12/classes/"
        );
        RuntimeService srv = new RuntimeService(ctx).serve();
        // SequentialExecution seq = new SequentialExecution(srv).perfOnce();
        ParallelExecution par = new ParallelExecution(srv).perfOnce();

    }
}
