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

abstract public class Actor implements Traversable {

    private static Gson gson = new Gson();

    // Do not serialized this to avoid circle reference
    private transient Actor parentActor;

    // Use this for serialized
    @SerializedName("parent-actor")
    private String parentActorString;

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

    /**
     * Set the parent actor
     * @param parentActor parent actor
     */
    public void setParentActor(Actor parentActor) {
        this.parentActor = parentActor;
        this.parentActorString = parentActor.getActorName();
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

    /**
     * Constructor of actor
     * @param parentActor parent actor
     * @param actorName name of this actor
     */
    public Actor(Actor parentActor, String actorName) {
        childActors = new ConcurrentHashMap<>();
        this.actorName = actorName;
        if (parentActor != null) {
            parentActor.addChildActor(this);
        } else {
            this.parentActor = null;
        }
    }

    /**
     * Get the actor name
     * @return actor name
     */
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
