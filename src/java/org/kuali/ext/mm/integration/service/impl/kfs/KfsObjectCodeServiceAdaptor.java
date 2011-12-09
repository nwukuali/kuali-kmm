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

import org.kuali.ext.mm.integration.coa.businessobject.FinancialObjectCode;
import org.kuali.ext.mm.integration.service.FinancialObjectCodeService;
import org.kuali.kfs.coa.businessobject.ObjectCode;
import org.kuali.kfs.coa.service.ObjectCodeService;

public class KfsObjectCodeServiceAdaptor extends KfsServiceAdaptor<ObjectCodeService> implements
        FinancialObjectCodeService {
    /**
     * @param serviceName
     */
    public KfsObjectCodeServiceAdaptor(QName serviceName) {
        super(serviceName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialObjectCodeService#getByPrimaryId(java.lang.Integer, java.lang.String,
     *      java.lang.String)
     */
    public FinancialObjectCode getByPrimaryId(Integer fiscalYear, String chartOfAccountsCode,
            String objectCode) {
        ObjectCode source = getService()
                .getByPrimaryId(fiscalYear, chartOfAccountsCode, objectCode);
        if (source == null) {
            return null;
        }
        FinancialObjectCode target = new FinancialObjectCode();
        adapt(source, target);
        return target;
    }

    /**
     * Adapts ObjectCode to FinancialObjectCode
     *
     * @param source ObjectCode
     * @param target Financial Object Code
     */
    protected void adapt(ObjectCode source, FinancialObjectCode target) {
        target.setUniversityFiscalYear(source.getUniversityFiscalYear());
        target.setChartOfAccountsCode(source.getChartOfAccountsCode());
        target.setFinancialObjectCode(source.getFinancialObjectCode());
        target.setFinancialObjectCodeName(source.getFinancialObjectCodeName());
        target.setFinancialObjectCodeShortName(source.getFinancialObjectCodeShortName());
        target.setHistoricalFinancialObjectCode(source.getHistoricalFinancialObjectCode());
        target.setActive(source.isActive());
        target.setFinancialObjectLevelCode(source.getFinancialObjectLevelCode());
        target.setReportsToChartOfAccountsCode(source.getReportsToChartOfAccountsCode());
        target.setReportsToFinancialObjectCode(source.getReportsToFinancialObjectCode());
        target.setFinancialObjectTypeCode(source.getFinancialObjectTypeCode());
        target.setFinancialObjectSubTypeCode(source.getFinancialObjectSubTypeCode());
        target.setFinancialBudgetAggregationCd(source.getFinancialBudgetAggregationCd());
        target.setNextYearFinancialObjectCode(source.getNextYearFinancialObjectCode());
        target.setFinObjMandatoryTrnfrelimCd(source.getFinObjMandatoryTrnfrelimCd());
        target.setFinancialFederalFundedCode(source.getFinancialFederalFundedCode());
    }


}
