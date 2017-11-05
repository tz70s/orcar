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

    private int routingThreadPoolSize;

    private ScheduledExecutorService service;

    /**
     * configScheduler is responsive for config a ThreadPool
     * @param routingThreadPoolSize The thread pool size.
     * @throws Exception Throws if the thread pool size is not correct.
     */
    private void configScheduler(int routingThreadPoolSize) throws Exception {

        // The routingThreadPoolSize is limited into 8.
        if (routingThreadPoolSize > 8 || routingThreadPoolSize < 1) {
            throw new Exception("Incorrect runtimeThreadPoolSize, it is limited from 1 to 8");
        }
        this.routingThreadPoolSize = routingThreadPoolSize;
        this.service = Executors.newScheduledThreadPool(routingThreadPoolSize);
    }

    /**
     * Schedule a channel handling.
     * @param forwarder {@see Forwarder}
     * @throws Exception Throws if the scheduler is not initialized.
     */
    public synchronized void fireForwarder(Forwarder forwarder) throws Exception {
        service.schedule(forwarder, 0, TimeUnit.MILLISECONDS);
    }

    /**
     * Constructor, call config scheduler
     * @param routingThreadPoolSize the routing thread pool size.
     * @throws Exception if the thread pool size is not correct.
     */
    public RoutingScheduler(int routingThreadPoolSize) throws Exception {
        configScheduler(routingThreadPoolSize);
    }
}
