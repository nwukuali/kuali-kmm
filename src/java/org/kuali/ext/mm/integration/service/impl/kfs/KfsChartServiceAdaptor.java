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

import org.kuali.ext.mm.integration.coa.businessobject.FinancialChart;
import org.kuali.ext.mm.integration.service.FinancialChartService;
import org.kuali.kfs.coa.businessobject.Chart;
import org.kuali.kfs.coa.service.ChartService;


/**
 * Implementing adaptor for {@link FinancialChartService}
 */
public class KfsChartServiceAdaptor extends KfsServiceAdaptor<ChartService> implements
        FinancialChartService {
    public KfsChartServiceAdaptor(QName serviceName) {
        super(serviceName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialChartService#getByPrimaryId(java.lang.String)
     */
    public FinancialChart getByPrimaryId(String chartOfAccountsCode) {
        Chart source = getService().getByPrimaryId(chartOfAccountsCode);
        if (source == null) {
            return null;
        }
        FinancialChart target = new FinancialChart();
        adapt(source, target);
        return target;
    }


    /**
     * Adapts Chart to FinancialChart
     *
     * @param source Chart
     * @param target FinancialChart
     */
    private void adapt(Chart source, FinancialChart target) {
        target.setActive(source.isActive());
        target.setChartOfAccountsCode(source.getChartOfAccountsCode());
        target.setExpBdgtEliminationsFinObjCd(source.getExpBdgtEliminationsFinObjCd());
        target.setFinAccountsPayableObjectCode(source.getFinAccountsPayableObjectCode());
        target.setFinAccountsReceivableObjCode(source.getFinAccountsReceivableObjCode());
        target.setFinancialCashObjectCode(source.getFinancialCashObjectCode());
        target.setFinChartOfAccountDescription(source.getFinChartOfAccountDescription());
        // target.setFinCoaManagerPrincipalId(source.getFinCoaManagerPrincipalId());
        target.setFinExternalEncumbranceObjCd(source.getFinExternalEncumbranceObjCd());
        target.setFinInternalEncumbranceObjCd(source.getFinInternalEncumbranceObjCd());
        target.setFinPreEncumbranceObjectCode(source.getFinPreEncumbranceObjectCode());
        target.setFundBalanceObjectCode(source.getFundBalanceObjectCode());
        target.setIcrExpenseFinancialObjectCd(source.getIcrExpenseFinancialObjectCd());
        target.setIcrIncomeFinancialObjectCode(source.getIcrIncomeFinancialObjectCode());
        target.setIncBdgtEliminationsFinObjCd(source.getIncBdgtEliminationsFinObjCd());
        target.setReportsToChartOfAccountsCode(source.getReportsToChartOfAccountsCode());
    }
}
