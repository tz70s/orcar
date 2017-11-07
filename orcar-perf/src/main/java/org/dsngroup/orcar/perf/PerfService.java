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

package org.dsngroup.orcar.perf;

import org.dsngroup.orcar.perf.runtime.RuntimePerfEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point(main class) of performance test.
 */
public class PerfService {

    private final static String USAGE = "To launch the performace test:\n\truntime\n\tapiserver";

    private final static Logger logger = LoggerFactory.getLogger(PerfService.class);

    private static PerfMode mode;

    private static String perfResultPath;

    private static void parseArgs(String[] args) throws Exception {
        if(args.length < 1) {
            throw new Exception("Not enough arguments");
        }
        switch (args[0]) {
            case "runtime":
                // runtime mode.
                mode = PerfMode.RUNTIME;
                break;
            case "apiserver":
                mode = PerfMode.APISERVER;
                break;
            default:
                throw new Exception("Wrong mode specified.");
        }
    }

    public static void main(String[] args) throws Exception {
        // Parse arguments
        try {
            parseArgs(args);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        if (mode == PerfMode.RUNTIME) {
            // Execute runtime perf test entry
            RuntimePerfEntry runtimePerfEntry = new RuntimePerfEntry();
        } else if (mode == PerfMode.APISERVER) {
            // Execute apiserver perf test entry
        }
    }
}
