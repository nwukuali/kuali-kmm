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

import org.kuali.ext.mm.integration.coa.businessobject.FinancialOrganization;
import org.kuali.ext.mm.integration.service.FinancialOrganizationService;
import org.kuali.kfs.coa.businessobject.Organization;
import org.kuali.kfs.coa.service.OrganizationService;

public class KfsOrganizationServiceAdaptor extends KfsServiceAdaptor<OrganizationService> implements
        FinancialOrganizationService {

    public KfsOrganizationServiceAdaptor(QName serviceName) {
        super(serviceName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialOrganizationService#getByPrimaryId(java.lang.String, java.lang.String)
     */
    public FinancialOrganization getByPrimaryId(String chartOfAccountsCode, String organizationCode) {
        Organization source = getService().getByPrimaryId(chartOfAccountsCode, organizationCode);
        if (source == null) {
            return null;
        }
        FinancialOrganization target = new FinancialOrganization();
        adapt(source, target);
        return target;
    }

    /**
     * Adapts Organization to FinancialOrganization
     *
     * @param source Organization
     * @param target FinancialOrganization
     */
    protected void adapt(Organization source, FinancialOrganization target) {
        target.setChartOfAccountsCode(source.getChartOfAccountsCode());
        target.setOrganizationCode(source.getOrganizationCode());
        target.setOrganizationName(source.getOrganizationName());
        target.setActive(source.isActive());
    }


}
