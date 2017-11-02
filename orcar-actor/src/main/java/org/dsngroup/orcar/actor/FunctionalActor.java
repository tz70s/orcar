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

package org.dsngroup.orcar.actor;

/**
 * The default interface for user to implement.
 */
public interface FunctionalActor {
    /**
     * The FunctionalActor is an actor model style interface.
     * It's event-driven and only when the mail is arrived the actor will be spawned.
     * The only method should be override is accept.
     */
    void accept(Mail mail);
}
