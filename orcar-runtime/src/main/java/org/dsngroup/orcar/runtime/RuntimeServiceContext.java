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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.io.File;

/**
 * The RuntimeServiceContext records the context(configuration) of the RuntimeService.
 */
public class RuntimeServiceContext {

    private static byte nodeID;

    private static int runtimeThreadPoolSize;

    private static URL localClassPath;

    private static final Logger logger = LoggerFactory.getLogger(RuntimeService.class);

    /**
     * Default constructor of RuntimeServiceContext
     */
    public RuntimeServiceContext() {
        this.nodeID = (byte) '0';
        this.runtimeThreadPoolSize = 4;
        try {
            this.localClassPath = new File("/Users/Tzuchiao/workspace/java/orcar/orcar-sample/target/scala-2.12/classes/").toURI().toURL();
        } catch (Exception e) {
            logger.error("Local class path translation failed" + e.getMessage());
        }
    }

    /**
     * Overloaded constructor of RuntimeServiceContext.
     * @param runtimeThreadPoolSize The thread pool size of runtime.
     * @param localClassPath Customized localClassPath.
     */
    public RuntimeServiceContext(int runtimeThreadPoolSize, String localClassPath) {
        this.nodeID = (byte) '0';
        this.runtimeThreadPoolSize = runtimeThreadPoolSize;
        try {
            this.localClassPath = new File(localClassPath).toURI().toURL();
        } catch (Exception e) {
            logger.error("Local class path translation failed" + e.getMessage());
        }
    }

    /**
     * Getter of node id of this node.
     * @return node id
     */
    public byte getNodeID() {
        return nodeID;
    }

    /**
     * Getter of runtimeThreadPoolSize
     * @return runtimeThreadPoolSize
     */
    public int getRuntimeThreadPoolSize() {
        return runtimeThreadPoolSize;
    }

    /**
     * Getter of localClassPath
     * @return URL of localClassPath
     */
    public URL getLocalClassPath() {
        return localClassPath;
    }
}
