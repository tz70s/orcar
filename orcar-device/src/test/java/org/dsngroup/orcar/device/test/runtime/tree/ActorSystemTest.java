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

package org.dsngroup.orcar.device.test.runtime.tree;

import org.dsngroup.orcar.device.runtime.tree.ActorSystem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActorSystemTest {

    private static ActorSystem rootActorSystem;

    @BeforeAll
    public static void init() {
        rootActorSystem = new ActorSystem(null,"root");
    }

    @Test
    public void testActorSystemCreation() {
        ActorSystem actorSystem = new ActorSystem(rootActorSystem, "newCreateActorSystem");
        assertEquals(actorSystem, rootActorSystem.getChildActor("newCreateActorSystem"));
        assertEquals(actorSystem.getParentActor(), rootActorSystem);
        assertEquals(actorSystem.getActorName(), "newCreateActorSystem");
    }

    @Test
    public void testAddChildActorSystem() {
        ActorSystem actorSystem = new ActorSystem(rootActorSystem, "newCreateActorSystem");
        ActorSystem childActorSystem = new ActorSystem(null, "childActorSystem");
        actorSystem.addChildActor(childActorSystem);
        assertEquals(childActorSystem, actorSystem.getChildActor("childActorSystem"));
        assertEquals(actorSystem, childActorSystem.getParentActor());
    }

    @Test
    public void testRemoveFromActorSystem() {
        ActorSystem actorSystem = new ActorSystem(rootActorSystem, "newCreateActorSystem");
        rootActorSystem.removeChildActor(actorSystem);
        assertEquals(null, rootActorSystem.getChildActor("newCreateActorSystem"));
    }
}
