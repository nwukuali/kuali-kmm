/*
 * Copyright 2007 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.context;

import org.junit.BeforeClass;
import org.kuali.ext.mm.ConfigureContext;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.suite.RelatesTo;


/**
 * This class should be extended by all Kuali mock unit tests.
 *
 * @see ConfigureContext
 * @see RelatesTo
 */

public class KualiMockTestBase {
    private static boolean springContextInitialized = false;

    @BeforeClass
    public static void setupContext() throws Exception {
        setupCommon();
    }

    private static void setupCommon() {
        if (!springContextInitialized) {
            try {
                SpringContext.initializeApplicationContext();
                springContextInitialized = true;
            }
            catch (RuntimeException e) {
                throw e;
            }
        }
    }
}
