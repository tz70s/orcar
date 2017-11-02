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

package org.dsngroup.orcar.actor;

import com.google.gson.Gson;

/**
 * The mail interface which resolves the default to object or to json methods.
 */
public interface Mail {

    default Object toObject(String jsonString) {
        Gson gson = new Gson();
        Class cls = this.getClass();
        return gson.fromJson(jsonString, cls);
    }

    default String toString(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
