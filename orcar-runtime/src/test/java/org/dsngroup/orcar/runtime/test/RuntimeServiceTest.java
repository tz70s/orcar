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

package org.dsngroup.orcar.runtime.test;

import org.dsngroup.orcar.runtime.RuntimeService;
import org.dsngroup.orcar.runtime.RuntimeServiceContext;
import org.junit.Test;

public class RuntimeServiceTest {

    /**
     * Test for only create and close.
     */
    @Test
    public void testRuntimeServiceCreateAndClose() {
        RuntimeService srv = new RuntimeService(new RuntimeServiceContext()).serve();
        try {
            srv.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
