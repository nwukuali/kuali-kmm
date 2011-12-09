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

package org.kuali.ext.mm.integration.service;

import org.kuali.ext.mm.integration.coa.businessobject.FinancialOrganization;

/**
 * Declares methods required by Organization Service implementor
 */
public interface FinancialOrganizationService {
    /**
     * Finds a financial organization object by its primary identification fields
     *
     * @param chartOfAccountsCode Chart of Accounts Code
     * @param organizationCode Organization Code
     * @return Organization object
     */
    FinancialOrganization getByPrimaryId(String chartOfAccountsCode, String organizationCode);
}
