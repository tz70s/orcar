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

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

abstract public class Actor implements Traversable{

    private static Gson gson = new Gson();

    @SerializedName("parent-actor")
    private Actor parentActor;

    @SerializedName("actor-name")
    private String actorName;

    @SerializedName("child-actors")
    private Map<String, Actor> childActors;

    @Override
    public Actor getChildActor(String childName) {
        return childActors.get(childName);
    }

    @Override
    public Actor getParentActor() {
        return parentActor;
    }

    public void setParentActor(Actor parentActor) {
        this.parentActor = parentActor;
    }

    public void addChildActor(Actor childActor) {
        childActors.put(childActor.getActorName(), childActor);
        // TODO: Should remove from the original parent actor system
        if (childActor.getParentActor() != null) {
            childActor.getParentActor().removeChildActor(childActor);
        }
        childActor.setParentActor(this);
    }

    public void removeChildActor(Actor childActor) {
        childActors.remove(childActor.getActorName(), childActor);
    }

    public Actor(Actor parentActor, String actorName) {
        childActors = new ConcurrentHashMap<>();
        this.actorName = actorName;
        if (parentActor != null) {
            parentActor.addChildActor(this);
        } else {
            this.parentActor = null;
        }
    }

    public String getActorName() {
        return actorName;
    }

    /**
     * Used only for actor system.
     * @param actorName name
     */
    protected void setActorName(String actorName) {
        this.actorName = actorName;
    }


    /**
     * Serialized runtime service context into json string
     * @param actor {@link Actor}
     * @return json string
     */
    public static String toJsonString(Actor actor) {
        return gson.toJson(actor);
    }

    /**
     * Parse json string to runtime service context format
     * @param jsonString json string
     * @return runtime service context format
     */
    public static Actor toObject(String jsonString) {
        return gson.fromJson(jsonString, Actor.class);
    }
}
