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

import org.kuali.ext.mm.integration.coa.businessobject.FinancialSubObjectCode;

/**
 * Declares methods required by Financial Object Code Service implementor
 */
public interface FinancialSubObjectCodeService {
    /**
     * Finds a financial sub object code using its id fields
     *
     * @param fiscalYear Fiscal Year
     * @param chartOfAccountsCode Chart of Accounts code
     * @param accountNumber Account number
     * @param objectCode Object code
     * @param subObjectCode Sub Object Code
     * @return Sub Object Code
     */
    FinancialSubObjectCode getByPrimaryId(Integer fiscalYear, String chartOfAccountsCode,
            String accountNumber, String objectCode, String subObjectCode);
}
