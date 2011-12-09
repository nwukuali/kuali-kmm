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

package org.kuali.ext.mm.integration;

import org.kuali.ext.mm.integration.kfs.KfsSystemAdaptorFactory;
import org.kuali.ext.mm.integration.service.FinancialAccountService;
import org.kuali.ext.mm.integration.service.FinancialBusinessObjectService;
import org.kuali.ext.mm.integration.service.FinancialCapitalAssetService;
import org.kuali.ext.mm.integration.service.FinancialChartService;
import org.kuali.ext.mm.integration.service.FinancialDocumentService;
import org.kuali.ext.mm.integration.service.FinancialElectronicInvoiceService;
import org.kuali.ext.mm.integration.service.FinancialGeneralLedgerPendingEntryService;
import org.kuali.ext.mm.integration.service.FinancialGeneralLedgerService;
import org.kuali.ext.mm.integration.service.FinancialInternalBillingService;
import org.kuali.ext.mm.integration.service.FinancialLocationService;
import org.kuali.ext.mm.integration.service.FinancialObjectCodeService;
import org.kuali.ext.mm.integration.service.FinancialOrganizationService;
import org.kuali.ext.mm.integration.service.FinancialParameterService;
import org.kuali.ext.mm.integration.service.FinancialProjectCodeService;
import org.kuali.ext.mm.integration.service.FinancialPurchaseOrderService;
import org.kuali.ext.mm.integration.service.FinancialPurchasingService;
import org.kuali.ext.mm.integration.service.FinancialRequisitionService;
import org.kuali.ext.mm.integration.service.FinancialSubAccountService;
import org.kuali.ext.mm.integration.service.FinancialSubObjectCodeService;
import org.kuali.ext.mm.integration.service.FinancialTaxService;
import org.kuali.ext.mm.integration.service.FinancialUniversityDateService;
import org.kuali.ext.mm.integration.service.FinancialVendorService;

/**
 * This is an adaptor factory interface which will be implemented for MM to interface with respective financial system.Please
 * FinancialSystemConfiguration.xml to see how we interface with KFS is done. {@link FinancialSystemConfiguration} For sample
 * implementation see {@link KfsSystemAdaptorFactory}
 */
public interface FinancialSystemAdaptorFactory {
    /**
     * Returns true if finacial system services can be accessed
     *
     * @return
     */
    public boolean isSystemAvailable();

    /**
     * Returns true if system available and adds a global error is not available
     *
     * @return
     */
    public boolean checkAndErrorSystemAvailability();

    /**
     * Gets the financial system configuration data
     *
     * @see FinancialSystemConfiguration
     * @return Financial system configuration data
     */
    FinancialSystemConfiguration getFinancialSystemConfiguration();

    /**
     * Sets the configuration data
     *
     * @param financialSystemConfiguration
     */
    void setFinancialSystemConfiguration(FinancialSystemConfiguration financialSystemConfiguration);

    /**
     * @return Chart Service
     */
    FinancialChartService getChartService();

    /**
     * @return Organization Service
     */
    FinancialOrganizationService getOrganizationService();

    /**
     * @return Account Service
     */
    FinancialAccountService getAccountService();

    /**
     * @return Sub Account service
     */
    FinancialSubAccountService getSubAccountService();

    /**
     * @return Sub Account Service
     */
    FinancialObjectCodeService getFinancialObjectCodeService();

    /**
     * @return Project Code Service
     */
    FinancialProjectCodeService getFinancialProjectCodeService();

    /**
     * @return Sub Object Code Service
     */
    FinancialSubObjectCodeService getFinancialSubObjectCodeService();

    /**
     * @return Location service providing Building and Room information
     */
    FinancialLocationService getFinancialLocationService();

    /**
     * @return Tax service interface
     */
    FinancialTaxService getFinancialTaxService();

    /**
     * @return General Ledger Service
     */
    FinancialGeneralLedgerPendingEntryService getFinancialGeneralLedgerPendingEntryService();

    /**
     * @return University Date Service
     */
    FinancialUniversityDateService getFinancialUniversityDateService();

    /**
     * @return Financial Document Service
     */
    FinancialDocumentService getFinancialDocumentService();

    /**
     * @return Financial BusinessObject Service
     */
    FinancialBusinessObjectService getFinancialBusinessObjectService();

    /**
     * @return Financial Vendor Service
     */
    FinancialVendorService getFinancialVendorService();

    /**
     * @return FinancialPurchasingService
     */
    FinancialPurchasingService getFinancialPurchasingService();

    /**
     * @return FinancialInternalBillingService
     */
    FinancialInternalBillingService getFinancialInternalBillingService();

    /**
     * @return FinancialCapitalAssetService
     */
    FinancialCapitalAssetService getFinancialCapitalAssetService();

    /**
     * @return FinancialParameterService
     */
    FinancialParameterService getFinancialParameterService();

    /**
     * @return FinancialElectronicInvoiceService
     */
    FinancialElectronicInvoiceService getFinancialElectronicInvoiceService();

    /**
     * @return FinancialPurchaseOrderService
     */
    FinancialPurchaseOrderService getFinancialPurchaseOrderService();

    /**
     * @return FinancialRequisitionService
     */
    FinancialRequisitionService getFinancialRequisitionService();
    
    public FinancialGeneralLedgerService getFinancialGeneralLedgerService();
}
