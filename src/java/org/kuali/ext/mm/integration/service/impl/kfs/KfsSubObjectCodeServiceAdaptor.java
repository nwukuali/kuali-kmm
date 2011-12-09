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

import org.kuali.ext.mm.integration.coa.businessobject.FinancialSubObjectCode;
import org.kuali.ext.mm.integration.service.FinancialSubObjectCodeService;
import org.kuali.kfs.coa.businessobject.SubObjectCode;
import org.kuali.kfs.coa.service.SubObjectCodeService;

/**
 * {@link FinancialSubObjectCodeService}
 */
public class KfsSubObjectCodeServiceAdaptor extends KfsServiceAdaptor<SubObjectCodeService>
        implements FinancialSubObjectCodeService {

    public KfsSubObjectCodeServiceAdaptor(QName serviceName) {
        super(serviceName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialSubObjectCodeService#getByPrimaryId(java.lang.Integer, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public FinancialSubObjectCode getByPrimaryId(Integer universityFiscalYear,
            String chartOfAccountsCode, String accountNumber, String financialObjectCode,
            String financialSubObjectCode) {
        SubObjectCode source = getService().getByPrimaryId(universityFiscalYear,
                chartOfAccountsCode, accountNumber, financialObjectCode, financialSubObjectCode);
        if (source == null) {
            return null;
        }
        FinancialSubObjectCode target = new FinancialSubObjectCode();
        adapt(source, target);
        return target;
    }

    protected void adapt(SubObjectCode source, FinancialSubObjectCode target) {
        target.setChartOfAccountsCode(source.getChartOfAccountsCode());
        target.setAccountNumber(source.getAccountNumber());
        target.setFinancialObjectCode(source.getFinancialObjectCode());
        target.setFinancialSubObjectCode(source.getFinancialSubObjectCode());
        target.setFinancialSubObjectCodeName(source.getFinancialSubObjectCodeName());
        target.setFinancialSubObjectCdshortNm(source.getFinancialSubObjectCdshortNm());
        target.setActive(source.isActive());
        target.setUniversityFiscalYear(source.getUniversityFiscalYear());
    }
}
