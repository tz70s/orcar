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

import org.dsngroup.orcar.device.runtime.task.TaskEvent;
import org.dsngroup.orcar.device.runtime.task.TaskEventID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskEventIDTest {



    @Test
    public void testEqual() throws Exception {

        TaskEventID taskEventID1 = new TaskEventID((byte) 0);
        TaskEventID taskEventID2 = new TaskEventID((byte) 0);

        // Should be equal
        assertEquals(true, taskEventID1.equals(taskEventID2),
                "Should equal of two same task event id");

        TaskEventID taskEventID3 = new TaskEventID((byte) 1);
        assertEquals(false, taskEventID1.equals(taskEventID3),
                "Different id should not be the same");
    }

    @Test
    public void testComparable() throws Exception {

        TaskEventID taskEventID1 = new TaskEventID((byte) 0);
        TaskEventID taskEventID2 = new TaskEventID((byte) 1);

        assertEquals(-1, taskEventID1.compareTo(taskEventID2));

        assertEquals(1, taskEventID2.compareTo(taskEventID1));

        TaskEventID taskEventID3 = new TaskEventID((byte) 0);

        assertEquals(0, taskEventID1.compareTo(taskEventID3));
    }

    @Test
    public void testCreation() throws Exception {
        TaskEventID taskEventID = new TaskEventID((byte) 0);
        assertEquals((byte) 0, taskEventID.getPrimitiveTaskEventID());
    }
}
