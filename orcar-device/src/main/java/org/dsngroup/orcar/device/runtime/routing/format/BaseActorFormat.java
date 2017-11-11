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

package org.dsngroup.orcar.device.runtime.routing.format;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class BaseActorFormat {

    @SerializedName("class-name")
    private String className;

    // Remaining json fields of actor data

    /**
     * Get the class name in the actor format
     * @return class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Serialized base actor format into json string
     * @param baseActorFormat base actor format
     * @return json string
     */
    public static String toJsonString(BaseActorFormat baseActorFormat) {
        Gson gson = new Gson();
        return gson.toJson(baseActorFormat);
    }

    /**
     * Parse json string to base actor format
     * @param jsonString json string
     * @return runtime base actor format
     */
    public static BaseActorFormat toObject(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, BaseActorFormat.class);
    }

    public BaseActorFormat(String className) {
        this.className = className;
    }

    public BaseActorFormat() {}
}
