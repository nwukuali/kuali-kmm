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
package org.kuali.ext.mm.document.web.struts;

import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

public class MMPropertyMessageResources extends PropertyMessageResources {
    /**
     *
     */
    private static final long serialVersionUID = -4841279270783177786L;

    public MMPropertyMessageResources(MessageResourcesFactory factory, String config, boolean returnNull) {
        super(factory, config, returnNull);
    }

    @Override
    protected void loadLocale(String localeKey) {
    	String initialConfig = config;
        String[] propertyFiles = config.split(",");
        for (String propertyFile : propertyFiles) {
            config = propertyFile;
            locales.remove(localeKey);
            super.loadLocale(localeKey);
        }
        config = initialConfig;
    }
}
