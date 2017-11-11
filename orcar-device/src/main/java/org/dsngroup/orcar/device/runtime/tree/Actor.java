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

abstract public class Actor implements Traversable {

    private static Gson gson = new Gson();

    // Do not serialized this to avoid circle reference
    private transient ActorSystem parentActor;

    // Use this for serialized
    @SerializedName("parent-actor")
    private String parentActorString;

    @SerializedName("actor-name")
    private String actorName;

    @Override
    public ActorSystem getParentActor() {
        return parentActor;
    }

    @Override
    abstract public Actor getChildActor(String childName);

    /**
     * Set the parent actor
     * @param parentActor parent actor
     */
    public void setParentActor(ActorSystem parentActor) {
        this.parentActor = parentActor;
        this.parentActorString = parentActor.getActorName();
    }

    /**
     * Constructor of actor
     * @param parentActor parent actor
     * @param actorName name of this actor
     */
    public Actor(ActorSystem parentActor, String actorName) {
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
    void setActorName(String actorName) {
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
