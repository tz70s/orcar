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

import org.dsngroup.orcar.actor.FunctionalActor;
import org.dsngroup.orcar.actor.MailBoxer;
import org.dsngroup.orcar.runtime.Orchestrator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskEventTest {

    private TaskEvent taskEvent;

    @BeforeAll
    public void init() {
        try {
            taskEvent = TaskFactory.createTaskEvent(new Orchestrator((byte) '1', new FunctionalActor() {
                @Override
                public void accept(MailBoxer mailBoxer) throws Exception {
                    // discard mail boxer
                }
            }), "Hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSynchronizedTaskEvent() {

        Thread thread = new Thread(taskEvent);
        // Another thread
        Thread threadAnother = new Thread(taskEvent);

        thread.start();
        threadAnother.start();
    }
}
