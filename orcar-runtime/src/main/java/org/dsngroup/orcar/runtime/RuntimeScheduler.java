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

package org.dsngroup.orcar.runtime;

import org.dsngroup.orcar.runtime.task.TaskEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * The RuntimeScheduler class schedule the task registered in TaskRegistry.
 */
public class RuntimeScheduler {

    private static int runtimeThreadPoolSize;

    private static ScheduledExecutorService service;

    /**
     * configScheduler is responsive for config a RuntimeSchedulerThreadPool
     * @param runtimeThreadPoolSize The thread pool size.
     * @throws Exception Throws if the thread pool size is not correct.
     */
    public static void configScheduler(int runtimeThreadPoolSize) throws Exception {

        // The runtimeThreadPoolSize is limited into 8.
        if (runtimeThreadPoolSize > 8 || runtimeThreadPoolSize < 1) {
            throw new Exception("Incorrect runtimeThreadPoolSize, it is limited from 1 to 8");
        }
        RuntimeScheduler.runtimeThreadPoolSize = runtimeThreadPoolSize;
        RuntimeScheduler.service = Executors.newScheduledThreadPool(runtimeThreadPoolSize);
    }

    /**
     * Schedule a task sent from TaskController
     * @param task Desired scheduled task.
     * @throws Exception Throws if the scheduler is not initialized.
     */
    public synchronized static void fireTask(TaskEvent task) throws Exception {
        // TODO: Consider a better way, instead of synchronized the big block.
        if (runtimeThreadPoolSize == 0 && service == null) {
            throw new Exception("The configScheduler for initialized should be called first");
        }
        // TODO: Schedule a task.
    }

    private RuntimeScheduler(){}
}
