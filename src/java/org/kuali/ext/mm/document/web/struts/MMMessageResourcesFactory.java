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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;
import org.kuali.ext.mm.common.sys.context.ContextConstants;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.kns.web.struts.action.KualiPropertyMessageResourcesFactory;


public class MMMessageResourcesFactory extends KualiPropertyMessageResourcesFactory {

    /**
     *
     */
    private static final long serialVersionUID = 4959923382692505347L;

    /**
     * Uses KFSPropertyMessageResources, which allows multiple property files to be loaded into the defalt message set.
     *
     * @see org.apache.struts.util.MessageResourcesFactory#createResources(java.lang.String)
     */
    @Override
    public MessageResources createResources(String config) {
        if (StringUtils.isBlank(config)) {
            config = ConfigContext.getCurrentContextConfig().getProperty(
                    ContextConstants.MM_MESSAGE_SOURCE_FILES);
        }
        return new MMPropertyMessageResources(this, config, this.returnNull);
    }
}
