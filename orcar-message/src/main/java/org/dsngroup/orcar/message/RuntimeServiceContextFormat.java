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

package org.dsngroup.orcar.message;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class RuntimeServiceContextFormat {

    @SerializedName("runtime-pool-size")
    private int runtimeThreadPoolSize;

    // Remaining Fields?

    /**
     * Get the runtime thread pool size
     * @return runtime thread pool size.
     */
    public int getRuntimeThreadPoolSize() {
        return runtimeThreadPoolSize;
    }
    /**
     * Serialized runtime service context format into json string
     * @param runtimeServiceContextFormat runtime service context
     * @return json string
     */
    public static String toJsonString(RuntimeServiceContextFormat runtimeServiceContextFormat) {
        Gson gson = new Gson();
        return gson.toJson(runtimeServiceContextFormat);
    }

    /**
     * Parse json string to runtime service context format
     * @param jsonString json string
     * @return runtime service context format
     */
    public static RuntimeServiceContextFormat toObject(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, RuntimeServiceContextFormat.class);
    }

    public RuntimeServiceContextFormat(int runtimeThreadPoolSize) {
        this.runtimeThreadPoolSize = runtimeThreadPoolSize;
    }

    public RuntimeServiceContextFormat() {}
}
