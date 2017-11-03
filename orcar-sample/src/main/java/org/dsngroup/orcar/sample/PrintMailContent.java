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

package org.dsngroup.orcar.sample;

import org.dsngroup.orcar.actor.FunctionalActor;
import org.dsngroup.orcar.actor.MailBoxer;
import org.dsngroup.orcar.gpio.actuator.Actuator;
import org.dsngroup.orcar.gpio.actuator.FakeConsoleActuator;

/**
 * Sample for observing the functionality of event-driven(data-flow) programming.
 */
public class PrintMailContent implements FunctionalActor {

    /**
     * Print out the received mailBoxer.
     * @param mailBoxer received mailBoxer.
     */
    @Override
    public void accept(MailBoxer mailBoxer) {
        Actuator<String> actuator = new FakeConsoleActuator<>();
        actuator.actuate(mailBoxer.toString());
    }
}
