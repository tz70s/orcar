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

package org.dsngroup.orcar.device.runtime;

import org.dsngroup.orcar.device.runtime.task.TaskEvent;

import java.util.concurrent.*;

/**
 * The RuntimeScheduler class schedule the task registered in TaskRegistry.
 */
public class RuntimeScheduler {

    private int runtimeThreadPoolSize;

    private ExecutorService service;

    private ExecutorCompletionService serviceComplete;

    /**
     * configScheduler is responsive for config a RuntimeSchedulerThreadPool
     * @param runtimeThreadPoolSize The thread pool size.
     * @throws Exception Throws if the thread pool size is not correct.
     */
    private void configScheduler(int runtimeThreadPoolSize) throws Exception {

        // The runtimeThreadPoolSize is limited into 8.
        if (runtimeThreadPoolSize > 8 || runtimeThreadPoolSize < 1) {
            throw new Exception("Incorrect runtimeThreadPoolSize, it is limited from 1 to 8");
        }
        this.runtimeThreadPoolSize = runtimeThreadPoolSize;
        service = Executors.newScheduledThreadPool(runtimeThreadPoolSize);
    }

    /**
     * Schedule a task sent from TaskController
     * @param task Desired scheduled task.
     * @throws Exception Throws if the scheduler is not initialized.
     */
    public synchronized Future<?> fireTask(TaskEvent task) throws Exception {
        // TODO: Add a listener to accept this Scheduler Future for task finishing.
        return service.submit(task);
    }

    /**
     * Constructor of runtime scheduler.
     * @param runtimeThreadPoolSize The thread pool size.
     * @throws Exception Throws if the thread pool size is not correct.
     */
    public RuntimeScheduler(int runtimeThreadPoolSize) throws Exception {
        configScheduler(runtimeThreadPoolSize);
    }

    /**
     * Default constructor - 4 threads.
     * @throws Exception Throws if the thread pool size is not correct.
     */
    public RuntimeScheduler() throws Exception {
        this(4);
    }
}
