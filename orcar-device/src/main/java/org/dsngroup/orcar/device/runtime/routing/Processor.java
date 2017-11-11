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

package org.dsngroup.orcar.device.runtime.routing;

import org.dsngroup.orcar.device.runtime.tree.Actor;
import org.dsngroup.orcar.device.runtime.tree.ActorSystem;
import org.dsngroup.orcar.device.runtime.tree.Orchestrator;
import org.dsngroup.orcar.device.runtime.tree.Traversable;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Iterator;
import java.util.List;

/**
 * Processor process the options and paths.
 */
public class Processor {

    private ActorSystem actorSystem;

    public Actor processActorGET(CoapExchange coapExchange) {
        List<String> locationPath = coapExchange.getRequestOptions().getLocationPath();

        // Find the hierarchy actor
        if (locationPath.isEmpty()) {
            return actorSystem;
        }

        Iterator<String> it = locationPath.iterator();

        Traversable<? extends Actor> traversable = actorSystem;
        while (it.hasNext()) {
            // Bind to new traversable
            Traversable<? extends Actor> previous = traversable;
            traversable = traversable.getChildActor(it.next());
            if (traversable == null) {
                if (previous instanceof Orchestrator) {
                    return (Actor) previous;
                }
                return null;
            }
        }
        return (Actor) traversable;
    }

    public Object[] processActorPUT(CoapExchange coapExchange) {

        List<String> locationPath = coapExchange.getRequestOptions().getLocationPath();
        // Find the hierarchy actor
        if (locationPath.isEmpty()) {
            return null;
        }

        Iterator<String> it = locationPath.iterator();

        Traversable<? extends Actor> traversable = actorSystem;
        // Bind to new traversable
        Traversable<? extends Actor> previous = traversable;
        String current = "";
        while (it.hasNext()) {
            current = it.next();
            traversable = previous.getChildActor(current);
            if (traversable == null) {
                if (previous instanceof ActorSystem) {
                    Object[] tuple = new Object[2];
                    tuple[0] = previous;
                    tuple[1] = current;
                    return tuple;
                } else {
                    return null;
                }
            }
        }

        if (traversable instanceof Orchestrator) {
            Object[] tuple = new Object[2];
            tuple[0] = traversable.getParentActor();
            tuple[1] = current;
            return tuple;
        }

        return null;
    }

    public Actor processActorPOST(CoapExchange coapExchange) {
        List<String> locationPath = coapExchange.getRequestOptions().getLocationPath();
        // Find the hierarchy actor
        if (locationPath.isEmpty()) {
            return actorSystem;
        }

        Iterator<String> it = locationPath.iterator();

        Actor actor;
        Traversable<? extends Actor> traversable = actorSystem;
        while (it.hasNext()) {
            // Bind to new traversable
            Traversable<? extends Actor> previous = traversable;
            String current = it.next();
            traversable = traversable.getChildActor(current);
            if (traversable == null) {
                if (previous instanceof ActorSystem) {
                    ActorSystem previousActorSystem = (ActorSystem) previous;
                    actor = new ActorSystem(previousActorSystem, current);
                    return actor;
                } else {
                    // Drop, can't mount actor under orchestrator.
                    return null;
                }
            }
        }
        return null;
    }


    public boolean processActorDELETE(CoapExchange coapExchange) {
        List<String> locationPath = coapExchange.getRequestOptions().getLocationPath();

        // Find the hierarchy actor
        if (locationPath.isEmpty()) {
            return false;
        }

        Iterator<String> it = locationPath.iterator();

        Traversable<? extends Actor> traversable = actorSystem;
        while (it.hasNext()) {
            // Bind to new traversable
            traversable = traversable.getChildActor(it.next());
            if (traversable == null) {
                return false;
            }
        }
        if (traversable instanceof ActorSystem) {
            ActorSystem travActorSystem = (ActorSystem) traversable;
            travActorSystem.getParentActor().removeChildActor(travActorSystem);
            return true;
        }

        return false;
    }

    public String processLocationQuery(CoapExchange coapExchange) {
        String className = "";
        for (String value: coapExchange.getRequestOptions().getLocationQuery()) {
            className = value;
            break;
        }
        return className;
    }

    public Processor(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }
}
