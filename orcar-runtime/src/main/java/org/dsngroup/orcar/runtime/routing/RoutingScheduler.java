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

package org.dsngroup.orcar.runtime.routing;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The RoutingScheduler class schedule the forwarder spawned by router.
 */
public class RoutingScheduler {

    private static int routingThreadPoolSize;

    private static ScheduledExecutorService service;

    /**
     * configScheduler is responsive for config a ThreadPool
     * @param routingThreadPoolSize The thread pool size.
     * @throws Exception Throws if the thread pool size is not correct.
     */
    public static void configScheduler(int routingThreadPoolSize) throws Exception {

        // The routingThreadPoolSize is limited into 8.
        if (routingThreadPoolSize > 8 || routingThreadPoolSize < 1) {
            throw new Exception("Incorrect runtimeThreadPoolSize, it is limited from 1 to 8");
        }
        RoutingScheduler.routingThreadPoolSize = routingThreadPoolSize;
        RoutingScheduler.service = Executors.newScheduledThreadPool(routingThreadPoolSize);
    }

    /**
     * Schedule a channel handling.
     * @param forwarder {@see Forwarder}
     * @throws Exception Throws if the scheduler is not initialized.
     */
    public synchronized static void fireForwarder(Forwarder forwarder) throws Exception {
        // TODO: Consider a better way, instead of synchronized the big block.
        if (routingThreadPoolSize == 0 && service == null) {
            throw new Exception("The configScheduler for initialized should be called first");
        }

        service.schedule(forwarder, 0, TimeUnit.MILLISECONDS);
    }

    private RoutingScheduler(){}
}
