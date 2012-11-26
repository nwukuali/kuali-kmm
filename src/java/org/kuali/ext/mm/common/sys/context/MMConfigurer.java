/*
 * Copyright 2008 The Kuali Foundation.
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
package org.kuali.ext.mm.common.sys.context;

import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.config.module.ModuleConfigurer;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;


public class MMConfigurer extends ModuleConfigurer {
    private DataSource applicationDataSource;

    public MMConfigurer() {
        super("MM");
    }

    /**
     * Prevents the loading of an OJB file
     *
     * @see org.kuali.rice.core.config.BaseModuleConfigurer#loadLifecycles()
     */
    @Override
    public List<Lifecycle> loadLifecycles() throws Exception {
        List<Lifecycle> lifecycles = new LinkedList<Lifecycle>();
        return lifecycles;
    }

    /**
     * @see org.kuali.rice.core.config.ModuleConfigurer#loadConfig(org.kuali.rice.core.config.Config)
     */
		//TODO: NWU - Revisit if method is still required
//    @Override
//    public Config loadConfig(Config parentConfig) throws Exception {
//        Config currentConfig = super.loadConfig(parentConfig);
//        configureDataSource(currentConfig);
//        return currentConfig;
//    }

    protected void configureDataSource(Config config) {
        config.getObjects().put("kmmDataSource", getApplicationDataSource());

    }

    /**
     * Gets the applicationDataSource property
     *
     * @return Returns the applicationDataSource
     */
    public DataSource getApplicationDataSource() {
        return this.applicationDataSource;
    }

    /**
     * Sets the applicationDataSource property value
     *
     * @param applicationDataSource The applicationDataSource to set
     */
    public void setApplicationDataSource(DataSource applicationDataSource) {
        this.applicationDataSource = applicationDataSource;
    }
}
