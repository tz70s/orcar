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

package org.dsngroup.orcar.device.runtime.tree;

import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActorSystem extends Actor {

    @SerializedName("actor-type")
    private final String actorType = "ActorSystem";

    @SerializedName("child-actors")
    private Map<String, Actor> childActors;

    @Override
    public Actor getChildActor(String childName) {
        return childActors.get(childName);
    }

    /**
     * Add child actor of this actor.
     * @param childActor add child actor.
     */
    public void addChildActor(Actor childActor) {
        childActors.put(childActor.getActorName(), childActor);
        // Remove from the original parent actor system
        if (childActor.getParentActor() != null) {
            childActor.getParentActor().removeChildActor(childActor);
        }
        childActor.setParentActor(this);
    }

    /**
     * Remove the child actor.
     * @param childActor child actor.
     */
    public void removeChildActor(Actor childActor) {
        childActors.remove(childActor.getActorName(), childActor);
    }


    public ActorSystem(ActorSystem actorSystem, String actorSystemName) {
        super(actorSystem, actorSystemName);
        // TODO: May have a remote parent
        childActors = new ConcurrentHashMap<>();
    }

    public void setActorSystemName(String actorSystemName) {
        setActorName(actorSystemName);
    }

}
