/*
 * Copyright 2005-2007 The Kuali Foundation.
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
package org.kuali.ext.mm;


/**
 * provides centralized storage of constants that occur throughout the tests
 */
public interface KualiTestConstants {

    /**
     * contains Test related constants
     */
    public final class TestConstants {
        private static final String HOST = "localhost";
        private static final String PORT = "8080";
        public static final String BASE_PATH = "http://" + HOST + ":" + PORT + "/";
        public static final String MESSAGE = "JUNIT test entry. If this exist after the tests are not cleaning up correctly. Created by class";
        public static final String TEST_BATCH_STAGING_DIRECTORY = "/java/projects/kuali_project/test/unit/src/org/kuali/test/staging/";

    }
}

