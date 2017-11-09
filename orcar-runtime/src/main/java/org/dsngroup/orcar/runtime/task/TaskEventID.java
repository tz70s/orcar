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

package org.dsngroup.orcar.runtime.task;

public final class TaskEventID implements Comparable<TaskEventID>{

    private byte taskEventID;

    /**
     * Get the primitive value.
     * @return primitive value of task event id.
     */
    public byte getPrimitiveTaskEventID() {
        return taskEventID;
    }

    /**
     * Simulate typedef to encapsulate logic of id size.
     * Also, provides safe reentrant synchronized.
     * @param taskEventID
     */
    public TaskEventID(byte taskEventID) {
        this.taskEventID = taskEventID;
    }

    @Override
    public int hashCode() {
        return 301 + 7 * getPrimitiveTaskEventID();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj.getClass() == this.getClass()) {
            TaskEventID taskEventID = (TaskEventID) obj;
            if (getPrimitiveTaskEventID() == taskEventID.getPrimitiveTaskEventID()) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(TaskEventID another) {
        if (getPrimitiveTaskEventID() > another.getPrimitiveTaskEventID()) {
            return 1;
        } else if (getPrimitiveTaskEventID() < another.getPrimitiveTaskEventID()) {
            return -1;
        } else {
            return 0;
        }
    }
}
