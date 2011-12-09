/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.ext.mm.integration.service.impl.kfs;

import javax.xml.namespace.QName;

import org.kuali.ext.mm.integration.coa.businessobject.FinancialProjectCode;
import org.kuali.ext.mm.integration.service.FinancialProjectCodeService;
import org.kuali.kfs.coa.businessobject.ProjectCode;
import org.kuali.kfs.coa.service.ProjectCodeService;

public class KfsProjectCodeServiceAdaptor extends KfsServiceAdaptor<ProjectCodeService> implements
        FinancialProjectCodeService {

    public KfsProjectCodeServiceAdaptor(QName serviceName) {
        super(serviceName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialProjectCodeService#getByPrimaryId(java.lang.String)
     */
    public FinancialProjectCode getByPrimaryId(String code) {
        ProjectCode source = getService().getByPrimaryId(code);
        if (source == null) {
            return null;
        }
        FinancialProjectCode target = new FinancialProjectCode();
        target.setCode(code);
        target.setName(source.getName());
        target.setActive(source.isActive());
        return target;
    }

}
