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

import org.kuali.ext.mm.integration.service.FinancialTaxService;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialTaxDetail;
import org.kuali.kfs.sys.businessobject.TaxDetail;
import org.kuali.kfs.sys.service.TaxService;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import javax.xml.namespace.QName;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author harsha07
 */
public class KfsTaxServiceAdaptor extends KfsServiceAdaptor<TaxService> implements
        FinancialTaxService {

    /**
     * @param serviceName
     */
    public KfsTaxServiceAdaptor(QName serviceName) {
        super(serviceName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialTaxService#getPretaxAmount(java.sql.Date, java.lang.String,
     *      org.kuali.rice.kns.util.KualiDecimal)
     */
    public KualiDecimal getPretaxAmount(Date dateOfTransaction, String postalCode,
            KualiDecimal amountWithTax) {
        return getService().getPretaxAmount(dateOfTransaction, postalCode, amountWithTax);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialTaxService#getSalesTaxDetails(java.sql.Date, java.lang.String,
     *      org.kuali.rice.kns.util.KualiDecimal)
     */
    public List<FinancialTaxDetail> getSalesTaxDetails(Date dateOfTransaction, String postalCode,
            KualiDecimal amount) {
        List<TaxDetail> salesTaxDetails = getService().getSalesTaxDetails(dateOfTransaction,
                postalCode, amount);
        if (salesTaxDetails == null) {
            return new ArrayList<FinancialTaxDetail>(0);
        }
        List<FinancialTaxDetail> financeList = new ArrayList<FinancialTaxDetail>();
        FinancialTaxDetail target = null;
        for (TaxDetail source : salesTaxDetails) {
            target = new FinancialTaxDetail();
            adapt(source, target);
            financeList.add(target);
        }
        return financeList;
    }

    protected void adapt(TaxDetail taxDtl, FinancialTaxDetail taxDetail) {
        taxDetail.setAccountNumber(taxDtl.getAccountNumber());
        taxDetail.setChartOfAccountsCode(taxDtl.getChartOfAccountsCode());
        taxDetail.setFinancialObjectCode(taxDtl.getFinancialObjectCode());
        taxDetail.setRateCode(taxDtl.getRateCode());
        taxDetail.setRateName(taxDtl.getRateName());
        taxDetail.setTaxAmount(taxDtl.getTaxAmount());
        taxDetail.setTaxRate(taxDtl.getTaxRate());
        taxDetail.setTypeCode(taxDtl.getTypeCode());
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialTaxService#getTotalSalesTaxAmount(java.sql.Date, java.lang.String,
     *      org.kuali.rice.kns.util.KualiDecimal)
     */
    public KualiDecimal getTotalSalesTaxAmount(Date dateOfTransaction, String postalCode,
            KualiDecimal amount) {
        return getService().getTotalSalesTaxAmount(dateOfTransaction, postalCode, amount);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialTaxService#getUseTaxDetails(java.sql.Date, java.lang.String,
     *      org.kuali.rice.kns.util.KualiDecimal)
     */
    public List<FinancialTaxDetail> getUseTaxDetails(Date dateOfTransaction, String postalCode,
            KualiDecimal amount) {
        List<TaxDetail> kfsUseTaxDetails = getService().getUseTaxDetails(dateOfTransaction,
                postalCode, amount);
        if (kfsUseTaxDetails == null) {
            return new ArrayList<FinancialTaxDetail>(0);
        }
        List<FinancialTaxDetail> financialList = new ArrayList<FinancialTaxDetail>();
        FinancialTaxDetail taxDetail = null;
        for (TaxDetail taxDtl : kfsUseTaxDetails) {
            taxDetail = new FinancialTaxDetail();
            adapt(taxDtl, taxDetail);
            financialList.add(taxDetail);
        }
        return financialList;
    }

}
