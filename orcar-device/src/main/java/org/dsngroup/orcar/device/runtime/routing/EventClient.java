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

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.coap.Request;

public class EventClient {

    private CoapClient coapClient;

    public EventClient() {
        this.coapClient = new CoapClient("coap://localhost:5683/actor");

        Request request = new Request(CoAP.Code.GET);
        request.setOptions(new OptionSet().addLocationPath("sibChildActorSystem"));

        coapClient.advanced(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                System.out.println(response.getResponseText());
            }
            @Override
            public void onError() {
                System.out.println("Error");
            }
        }, request);
    }

    public static void main(String[] args) {
        EventClient eventClient = new EventClient();
        while (true);
    }
}
