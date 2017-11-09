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

package org.dsngroup.orcar.device.runtime.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory to avoid double creation instance of {@link TaskEventID}
 */
public class TaskEventIDFactory {

    private static Map<Byte, TaskEventID> taskEventIDMap = new ConcurrentHashMap<>();

    /**
     * Singleton factory to avoid double creation.
     * @param primitiveTaskEventID wrapped primitive task event id
     * @return an existed task event id or a new task event id
     */
    public static TaskEventID createTaskEventID(Byte primitiveTaskEventID) {
        // TODO: Not a portable safe lock
        synchronized (primitiveTaskEventID) {
            if (taskEventIDMap.containsKey(primitiveTaskEventID)) {
                return taskEventIDMap.get(primitiveTaskEventID);
            } else {
                TaskEventID taskEventID = new TaskEventID(primitiveTaskEventID);
                taskEventIDMap.put(primitiveTaskEventID, taskEventID);
                return taskEventID;
            }
        }
    }

    private TaskEventIDFactory(){}
}
