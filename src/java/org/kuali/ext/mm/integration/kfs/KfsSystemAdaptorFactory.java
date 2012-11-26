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

package org.kuali.ext.mm.integration.kfs;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.FinancialSystemConfiguration;
import org.kuali.ext.mm.integration.ServiceNameInfo;
import org.kuali.ext.mm.integration.service.*;
import org.kuali.ext.mm.integration.service.impl.kfs.*;
import org.kuali.kfs.sys.service.NonTransactional;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

import javax.xml.namespace.QName;


/**
 * {@link FinancialSystemAdaptorFactory} KFS Implementation of System Adaptor Factory
 */
public class KfsSystemAdaptorFactory implements FinancialSystemAdaptorFactory {
    private static final Logger LOG = Logger.getLogger(KfsSystemAdaptorFactory.class);
    private FinancialSystemConfiguration financialSystemConfiguration;
    private FinancialChartService chartService;
    private FinancialOrganizationService organizationService;
    private FinancialAccountService accountService;
    private FinancialSubAccountService subAccountService;
    private FinancialObjectCodeService objectCodeService;
    private FinancialProjectCodeService projectCodeService;
    private FinancialSubObjectCodeService subObjectCodeService;
    private FinancialLocationService locationService;
    private FinancialTaxService taxService;
    private FinancialGeneralLedgerPendingEntryService generalLedgerPendingEntryService;
    private FinancialUniversityDateService universityDateService;
    private FinancialBusinessObjectService businessObjectService;
    private FinancialDocumentService documentService;
    private FinancialVendorService vendorService;
    private FinancialPurchasingService purchasingService;
    private FinancialInternalBillingService internalBillingService;
    private FinancialCapitalAssetService capitalAssetService;
    private FinancialParameterService parameterService;
    private FinancialElectronicInvoiceService electronicInvoiceService;
    private FinancialPurchaseOrderService purchaseOrderService;
    private FinancialRequisitionService requisitionService;
    private FinancialGeneralLedgerService generalLedgerService;

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#isSystemAvailable()
     */
    @NonTransactional
    public boolean isSystemAvailable() {
        // System availability check is done only once for a thread
        try {
            KfsUniversityDateServiceAdaptor dateService = (KfsUniversityDateServiceAdaptor) getFinancialUniversityDateService();
            if (dateService == null || dateService.getService() == null) {
                LOG.error("KFS services unreachable. ");
                return false;
            }
            // invoke the service
            dateService.getCurrentFiscalYear();
        }
        catch (Exception e) {
            LOG.error("KFS services unreachable. ", e);
            return false;
        }
        return true;
    }

    public boolean checkAndErrorSystemAvailability() {
        boolean financialSystemAvailable = isSystemAvailable();
        if (!financialSystemAvailable) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,
                    MMKeyConstants.FIN_SERVICES_UNAVAILABLE);
        }
        return financialSystemAvailable;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getChartService()
     */
    public FinancialChartService getChartService() {
        if (chartService == null) {
            chartService = new KfsChartServiceAdaptor(buildQName("financialChartService"));
        }
        return chartService;
    }

    /**
     * Builds service name from the configuration data
     * 
     * @param name Name of the service in the map
     * @return Q
     */
    private QName buildQName(String name) {
        ServiceNameInfo serviceNameInfo = getFinancialSystemConfiguration()
                .getServiceNameInfo(name);
        QName serviceName = new QName(serviceNameInfo.getServiceNameSpaceURI(), serviceNameInfo
                .getLocalServiceName());
        return serviceName;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialSystemConfiguration()
     */
    public FinancialSystemConfiguration getFinancialSystemConfiguration() {
        return financialSystemConfiguration;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#setFinancialSystemConfiguration(org.kuali.ext.mm.integration.FinancialSystemConfiguration)
     */
    public void setFinancialSystemConfiguration(
            FinancialSystemConfiguration financialSystemConfiguration) {
        this.financialSystemConfiguration = financialSystemConfiguration;

    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getOrganizationService()
     */
    public FinancialOrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = new KfsOrganizationServiceAdaptor(
                buildQName("financialOrganizationService"));

        }
        return organizationService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getAccountService()
     */
    public FinancialAccountService getAccountService() {
        if (accountService == null) {
            accountService = new KfsAccountServiceAdaptor(buildQName("financialAccountService"));

        }
        return accountService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getSubAccountService()
     */
    public FinancialSubAccountService getSubAccountService() {
        if (subAccountService == null) {
            subAccountService = new KfsSubAccountServiceAdaptor(
                buildQName("financialSubAccountService"));
        }
        return subAccountService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialLocationService()
     */
    public FinancialLocationService getFinancialLocationService() {
        if (locationService == null) {
            locationService = new KfsLocationServiceAdaptor(buildQName("financialLocationService"));
        }
        return locationService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialObjectCodeService()
     */
    public FinancialObjectCodeService getFinancialObjectCodeService() {
        if (objectCodeService == null) {
            objectCodeService = new KfsObjectCodeServiceAdaptor(
                buildQName("financialObjectCodeService"));
        }
        return objectCodeService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialProjectCodeService()
     */
    public FinancialProjectCodeService getFinancialProjectCodeService() {
        if (projectCodeService == null) {
            projectCodeService = new KfsProjectCodeServiceAdaptor(
                buildQName("financialProjectCodeService"));
        }
        return projectCodeService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialSubObjectCodeService()
     */
    public FinancialSubObjectCodeService getFinancialSubObjectCodeService() {
        if (subObjectCodeService == null) {
            subObjectCodeService = new KfsSubObjectCodeServiceAdaptor(
                buildQName("financialSubObjectCodeService"));
        }
        return subObjectCodeService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialTaxService()
     */
    public FinancialTaxService getFinancialTaxService() {
        if (taxService == null) {
            taxService = new KfsTaxServiceAdaptor(buildQName("financialTaxService"));
        }
        return taxService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialGeneralLedgerPendingEntryService()
     */
    public FinancialGeneralLedgerPendingEntryService getFinancialGeneralLedgerPendingEntryService() {
        if (generalLedgerPendingEntryService == null) {
            generalLedgerPendingEntryService = new KfsGeneralLedgerPendingEntryServiceAdaptor(
                buildQName("financialGeneralLedgerPendingEntryService"));
        }
        return generalLedgerPendingEntryService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialUniversityDateService()
     */
    public FinancialUniversityDateService getFinancialUniversityDateService() {
        if (universityDateService == null) {
            universityDateService = new KfsUniversityDateServiceAdaptor(
                buildQName("financialUniversityDateService"));
        }
        return universityDateService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialBusinessObjectService()
     */
    public FinancialBusinessObjectService getFinancialBusinessObjectService() {
        if (businessObjectService == null) {
            businessObjectService = new KfsBusinessObjectServiceAdaptor(
                buildQName("financialBusinessObjectService"));
        }
        return businessObjectService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialDocumentService()
     */
    public FinancialDocumentService getFinancialDocumentService() {
        if (documentService == null) {
            documentService = new KfsDocumentServiceAdaptor(buildQName("financialDocumentService"));
        }
        return documentService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialVendorService()
     */
    public FinancialVendorService getFinancialVendorService() {
        if (vendorService == null) {
            vendorService = new kfsVendorServiceAdaptor(buildQName("financialVendorService"),
                getFinancialBusinessObjectService(), getFinancialPurchaseOrderService());
        }
        return vendorService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialPurchasingService()
     */
    public FinancialPurchasingService getFinancialPurchasingService() {
        if (purchasingService == null) {
            purchasingService = new KfsPurchasingService(getFinancialDocumentService(),
                getFinancialVendorService(), getFinancialParameterService(), getAccountService(),
                getFinancialBusinessObjectService());
        }
        return purchasingService;
    }

    public FinancialInternalBillingService getFinancialInternalBillingService() {
        if (internalBillingService == null) {
            internalBillingService = new KfsInternalBillingService(getFinancialDocumentService(),
                getFinancialParameterService());
        }
        return internalBillingService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialCapitalAssetService()
     */
    public FinancialCapitalAssetService getFinancialCapitalAssetService() {
        if (this.capitalAssetService == null) {
            this.capitalAssetService = new KfsCapitalAssetServiceAdaptor(
                buildQName("financialCapitalAssetService"), getFinancialBusinessObjectService(),
                getFinancialObjectCodeService(), getFinancialParameterService());
        }
        return this.capitalAssetService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialParameterService()
     */
    public FinancialParameterService getFinancialParameterService() {
        if (this.parameterService == null) {
            this.parameterService = new KfsParamServiceAdaptor(
                buildQName("financialParameterService"));
        }
        return this.parameterService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialElectronicInvoiceService()
     */
    public FinancialElectronicInvoiceService getFinancialElectronicInvoiceService() {
        if (this.electronicInvoiceService == null) {
            this.electronicInvoiceService = new KfsElectronicInvoiceService(
                getFinancialPurchasingService(), getFinancialVendorService());
        }
        return this.electronicInvoiceService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialPurchaseOrderService()
     */
    public FinancialPurchaseOrderService getFinancialPurchaseOrderService() {
        if (this.purchaseOrderService == null) {
            this.purchaseOrderService = new KfsPurchaseOrderServiceAdaptor(
                buildQName("financialPurchaseOrderService"));
        }
        return this.purchaseOrderService;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory#getFinancialRequisitionService()
     */
    public FinancialRequisitionService getFinancialRequisitionService() {
        if (this.requisitionService == null) {
            this.requisitionService = new KfsRequisitionServiceAdaptor(
                buildQName("financialRequisitionService"));
        }
        return this.requisitionService;
    }

    public FinancialGeneralLedgerService getFinancialGeneralLedgerService() {
        if (this.generalLedgerService == null) {
            this.generalLedgerService = new KfsGeneralLedgerServiceAdaptor(
                buildQName("financialGeneralLedgerService"));
        }
        return this.generalLedgerService;
    }

}
