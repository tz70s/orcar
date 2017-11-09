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

package org.dsngroup.orcar.device.test.runtime.task;

import org.dsngroup.orcar.device.runtime.task.TaskEventID;
import org.dsngroup.orcar.device.runtime.task.TaskEventIDFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskEventIDFactoryTest {

    @Test
    public void testCreateTaskEventID() throws Exception {
        TaskEventID taskEventID = TaskEventIDFactory.createTaskEventID((byte) 0);
        assertEquals((byte) 0, taskEventID.getPrimitiveTaskEventID());
    }

    @Test
    public void testNoDoubleCreation() throws Exception {
        TaskEventID taskEventID1 = TaskEventIDFactory.createTaskEventID((byte) 0);
        TaskEventID taskEventID2 = TaskEventIDFactory.createTaskEventID((byte) 0);
        assertEquals(true, taskEventID1 == taskEventID2, "Should be same object binding.");
    }
}
