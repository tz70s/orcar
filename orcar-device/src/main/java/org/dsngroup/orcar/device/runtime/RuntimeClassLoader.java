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

package org.dsngroup.orcar.device.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLClassLoader;
import java.net.URL;

/**
 * The wrapper of ClassLoader
 */
public class RuntimeClassLoader {

    // The Default Class Loader
    private static ClassLoader defaultLoader;

    private static final Logger logger = LoggerFactory.getLogger(RuntimeClassLoader.class);

    /**
     * Initialization.
     * @param url The classpaths which initialized at RuntimeServiceContext.
     */
    private void init(URL url) {
        defaultLoader = new URLClassLoader(new URL[] {url});
    }

    /**
     * Load the class.
     * @param className The name of loaded class.
     * @return Object, to be cast.
     * @throws ClassNotFoundException Must be catch.
     */
    public Object loadClass(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return defaultLoader.loadClass(className).newInstance();
    }

    public RuntimeClassLoader(URL url) {
        init(url);
    }
}
