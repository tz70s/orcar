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

package org.dsngroup.orcar.device.test.runtime;

import org.dsngroup.orcar.actor.FunctionalActor;
import org.dsngroup.orcar.actor.MailBoxer;
import org.dsngroup.orcar.device.runtime.Orchestrator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrchestratorTest {

    private class TestableActor implements FunctionalActor {
        @Override
        public void accept(MailBoxer mailBoxer) throws Exception {
            // Discard
        }
    }

    @Test
    public void testEqual() throws Exception {
        Orchestrator orchestrator1 = new Orchestrator((byte) 0, new TestableActor());
        Orchestrator orchestrator2 = new Orchestrator((byte) 0, new TestableActor());

        // Should be equal
        assertEquals(true, orchestrator1.equals(orchestrator2),
                "Should equal of two same id orchestrator");

        Orchestrator orchestrator3 = new Orchestrator((byte) 1, new TestableActor());
        assertEquals(false, orchestrator1.equals(orchestrator3),
                "Different id should not be the same");
    }

    @Test
    public void testComparable() throws Exception {
        Orchestrator orchestrator1 = new Orchestrator((byte) 0, new TestableActor());
        Orchestrator orchestrator2 = new Orchestrator((byte) 1, new TestableActor());

        assertEquals(-1, orchestrator1.compareTo(orchestrator2));
        assertEquals(1, orchestrator2.compareTo(orchestrator1));

        Orchestrator orchestrator3 = new Orchestrator((byte) 0, new TestableActor());
        assertEquals(0, orchestrator1.compareTo(orchestrator3));
    }

    @Test
    public void testCreation() throws Exception {
        Orchestrator orchestrator = new Orchestrator((byte) 0, new TestableActor());
        assertEquals(true, orchestrator.getOrchestratorFunciton() instanceof TestableActor,
                "The creation of orchestrator function should be correct");
        assertEquals((byte) 0, (byte) orchestrator.getOrchestratorID());
    }
}
