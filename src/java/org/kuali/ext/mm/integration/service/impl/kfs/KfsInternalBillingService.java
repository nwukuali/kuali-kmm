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

import org.apache.log4j.Logger;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformation;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformationDetail;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.service.FinancialDocumentService;
import org.kuali.ext.mm.integration.service.FinancialInternalBillingService;
import org.kuali.ext.mm.integration.service.FinancialParameterService;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.kfs.fp.businessobject.CapitalAssetInformation;
import org.kuali.kfs.fp.businessobject.CapitalAssetInformationDetail;
import org.kuali.kfs.fp.businessobject.InternalBillingItem;
import org.kuali.kfs.fp.document.InternalBillingDocument;
import org.kuali.kfs.sys.KFSConstants;
import org.kuali.kfs.sys.businessobject.AccountingLineBase;
import org.kuali.kfs.sys.businessobject.AccountingLineOverride;
import org.kuali.kfs.sys.businessobject.SourceAccountingLine;
import org.kuali.kfs.sys.businessobject.TargetAccountingLine;
import org.kuali.kfs.sys.service.NonTransactional;
import org.kuali.kfs.sys.service.impl.KfsParameterConstants;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * KFS Internal Billing Service implementation helps to submit an IB document directly to KFS
 */
public class KfsInternalBillingService implements FinancialInternalBillingService {
    private static final Logger LOG = Logger.getLogger(KfsInternalBillingService.class);
    private FinancialDocumentService financialDocumentService;
    private FinancialParameterService financialParameterService;

    /**
     * @param financialDocumentService Financial Document Service
     */
    public KfsInternalBillingService(FinancialDocumentService financialDocumentService,
            FinancialParameterService financialParameterService) {
        this.financialDocumentService = financialDocumentService;
        this.financialParameterService = financialParameterService;
    }


    /**
     * @see org.kuali.ext.mm.integration.service.FinancialInternalBillingService#submitInternalBillingDocument(org.kuali.ext.mm.businessobject.Profile,
     *      java.util.List, java.util.List, java.util.List,
     *      org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformation)
     */
    @Transactional
    public DocumentHeader submitInternalBillingDocument(Profile billingProfile,
            List<FinancialInternalBillingItem> billItems,
            List<FinancialAccountingLine> incomeAcctLines,
            List<FinancialAccountingLine> expenseAcctLines,
            FinancialCapitalAssetInformation assetInfo) {
        return createAndSubmitInternalBilling(billingProfile, billItems, incomeAcctLines,
                expenseAcctLines, assetInfo);
    }

    @NonTransactional
    public DocumentHeader submitBatchInternalBillingDocument(Profile billingProfile,
            List<FinancialInternalBillingItem> billItems,
            List<FinancialAccountingLine> incomeAcctLines,
            List<FinancialAccountingLine> expenseAcctLines,
            FinancialCapitalAssetInformation assetInfo) {
        try {
            return createAndSubmitInternalBilling(billingProfile, billItems, incomeAcctLines,
                    expenseAcctLines, assetInfo);
        }
        catch (Exception e) {
            // WE dont want the exception to propagate into the batch job
            LOG.error("Could not submit the IB document successfully", e);
        }
        return null;
    }


    /**
     * @param billingProfile
     * @param billItems
     * @param incomeAcctLines
     * @param expenseAcctLines
     * @param assetInfo
     * @return
     */
    @SuppressWarnings("deprecation")
    protected DocumentHeader createAndSubmitInternalBilling(Profile billingProfile,
            List<FinancialInternalBillingItem> billItems,
            List<FinancialAccountingLine> incomeAcctLines,
            List<FinancialAccountingLine> expenseAcctLines,
            FinancialCapitalAssetInformation assetInfo) {
        InternalBillingDocument ibDocument = null;
        try {
            LOG.debug("Before initiating IB document");
            ibDocument = (InternalBillingDocument) financialDocumentService.getNewDocument(
                    KFSConstants.FinancialDocumentTypeCodes.INTERNAL_BILLING, billingProfile
                            .getPrincipalName());
            String documentNumber = ibDocument.getDocumentHeader().getDocumentNumber();
            LOG.debug("Created new document " + documentNumber);
            ibDocument.getDocumentHeader().setDocumentDescription("University Stores Bill");
            int sequence = 0;
            // add source accounting lines
            KualiDecimal incomeAmount = KualiDecimal.ZERO;
            if (incomeAcctLines != null) {
                LOG.debug("Adding income accounting lines");
                for (FinancialAccountingLine mmAcctLine : incomeAcctLines) {
                    SourceAccountingLine kfsAcctLine = new SourceAccountingLine();
                    kfsAcctLine.setDocumentNumber(documentNumber);
                    kfsAcctLine.setSequenceNumber(++sequence);
                    adapt(mmAcctLine, kfsAcctLine);
                    ibDocument.addSourceAccountingLine(kfsAcctLine);
                    incomeAmount = incomeAmount
                            .add(mmAcctLine.getAmount() == null ? KualiDecimal.ZERO : mmAcctLine
                                    .getAmount());
                }
            }
            if (incomeAmount.isZero()) {
                return null;
            }
            sequence = 0;
            // add target accounting lines
            if (expenseAcctLines != null) {
                LOG.debug("Adding expense account lines");
                for (FinancialAccountingLine mmAcctLine : expenseAcctLines) {
                    TargetAccountingLine kfsAcctLine = new TargetAccountingLine();
                    kfsAcctLine.setDocumentNumber(documentNumber);
                    kfsAcctLine.setSequenceNumber(++sequence);
                    adapt(mmAcctLine, kfsAcctLine);
                    ibDocument.addTargetAccountingLine(kfsAcctLine);
                }
            }
            sequence = 0;
            // add internal billing items
            if (billItems != null) {
                LOG.debug("Adding billing item lines");
                for (FinancialInternalBillingItem source : billItems) {
                    InternalBillingItem target = new InternalBillingItem();
                    target.setDocumentNumber(documentNumber);
                    target.setItemSequenceId(++sequence);
                    source.setItemSequenceId(sequence);
                    adapt(source, target);
                    ibDocument.addItem(target);
                }
            }
            // configure capital asset information
            CapitalAssetInformation capitalAssetInformation = new CapitalAssetInformation();
            capitalAssetInformation.setDocumentNumber(documentNumber);
            if (assetInfo != null) {
                LOG.debug("Adding capital asset information");
                adapt(assetInfo, capitalAssetInformation);
                List<FinancialCapitalAssetInformationDetail> assetInformationDetails = assetInfo
                        .getCapitalAssetInformationDetails();
                if (assetInformationDetails != null) {
                    sequence = 0;
                    for (FinancialCapitalAssetInformationDetail source : assetInformationDetails) {
                        CapitalAssetInformationDetail target = new CapitalAssetInformationDetail();
                        target.setDocumentNumber(documentNumber);
                        target.setItemLineNumber(++sequence);
                        adapt(source, target);
                        capitalAssetInformation.getCapitalAssetInformationDetails().add(target);
                    }
                }
            }
            ibDocument.setCapitalAssetInformation(Arrays.asList(capitalAssetInformation));
            try {
                ibDocument = (InternalBillingDocument) financialDocumentService
                        .blanketApproveDocument(ibDocument, null, null, billingProfile
                                .getPrincipalName());
                LOG.debug("Blanket approved the document");
            }
            catch (Exception e) {
                try {
                    LOG.error(e);
                    ibDocument = (InternalBillingDocument) financialDocumentService.routeDocument(
                            ibDocument, null, null, billingProfile.getPrincipalName());
                    LOG.warn("Submitted the document because of some errors occured during approval");
                }
                catch (Exception e2) {
                    LOG.error(e2);
                    ibDocument = (InternalBillingDocument) financialDocumentService.saveDocument(
                            ibDocument, billingProfile.getPrincipalName());
                    LOG.warn("Saved the document because of some errors occured during approval");
                }
            }
            
        }
        catch (Exception e) {
            LOG.error("Unexpected error occurred while submitting IB document", e);
            throw new RuntimeException("Unexpected error occurred while submitting IB document", e);
        }
        return ibDocument.getDocumentHeader();
    }

    /**
     * Adapts MM accounting line data to KFS accounting line
     * 
     * @param mmAcctLine MM Accounting Line
     * @param kfsAcctLine KFS Accounting Line
     * @return
     */
    protected void adapt(FinancialAccountingLine mmAcctLine, AccountingLineBase kfsAcctLine) {
        kfsAcctLine.setChartOfAccountsCode(mmAcctLine.getChartOfAccountsCode());
        kfsAcctLine.setAccountNumber(mmAcctLine.getAccountNumber());
        kfsAcctLine.setAmount(mmAcctLine.getAmount());
        kfsAcctLine.setBalanceTypeCode(mmAcctLine.getBalanceTypeCode());        
        kfsAcctLine.setFinancialDocumentLineDescription(mmAcctLine
                .getFinancialDocumentLineDescription());
        kfsAcctLine.setFinancialDocumentLineTypeCode(mmAcctLine.getFinancialDocumentLineTypeCode());
        kfsAcctLine.setFinancialObjectCode(mmAcctLine.getFinancialObjectCode());
        kfsAcctLine.setFinancialSubObjectCode(mmAcctLine.getFinancialSubObjectCode());
        kfsAcctLine.setObjectBudgetOverride(mmAcctLine.isObjectBudgetOverride());
        kfsAcctLine.setObjectBudgetOverrideNeeded(mmAcctLine.isObjectBudgetOverrideNeeded());
        kfsAcctLine.setOrganizationReferenceId(mmAcctLine.getOrganizationReferenceId());
        kfsAcctLine.setOverrideCode(AccountingLineOverride.CODE.NONE);
        kfsAcctLine.setPostingYear(mmAcctLine.getPostingYear());
        kfsAcctLine.setProjectCode(mmAcctLine.getProjectCode());
        kfsAcctLine.setReferenceNumber(mmAcctLine.getReferenceNumber());
        kfsAcctLine.setReferenceOriginCode(mmAcctLine.getReferenceOriginCode());
        kfsAcctLine.setReferenceTypeCode(mmAcctLine.getReferenceTypeCode());
        kfsAcctLine.setSalesTaxRequired(mmAcctLine.isSalesTaxRequired());
        kfsAcctLine.setSubAccountNumber(mmAcctLine.getSubAccountNumber());
    }


    /**
     * Adapts financial internal billing item to KFS internal billing item line
     * 
     * @param source MM Internal billing item
     * @param target KFS Internal Billing item
     */
    protected void adapt(FinancialInternalBillingItem source, InternalBillingItem target) {
        target.setItemQuantity(source.getItemQuantity());
        target.setItemServiceDate(source.getItemServiceDate());
        String itemStockDescription = source.getItemStockDescription();
        // Making sure it doesn't exceed the max length available in KFS
        if (itemStockDescription != null && itemStockDescription.length() > 40) {
            itemStockDescription = itemStockDescription.substring(0, 40);
        }
        target.setItemStockDescription(itemStockDescription);
        target.setItemStockNumber(source.getItemStockNumber());
        target.setItemUnitAmount(source.getItemUnitAmount());
        target.setUnitOfMeasureCode(source.getUnitOfMeasureCode());
    }


    /**
     * Adapts Capital Asset Information to KFS Capital Asset Information
     * 
     * @param assetInfo
     * @param capitalAssetInformation
     */
    protected void adapt(FinancialCapitalAssetInformation assetInfo,
            CapitalAssetInformation capitalAssetInformation) {
        capitalAssetInformation.setCapitalAssetDescription(assetInfo.getCapitalAssetDescription());
        capitalAssetInformation.setCapitalAssetManufacturerModelNumber(assetInfo
                .getCapitalAssetManufacturerModelNumber());
        capitalAssetInformation.setCapitalAssetManufacturerName(assetInfo
                .getCapitalAssetManufacturerName());
        capitalAssetInformation.setCapitalAssetNumber(assetInfo.getCapitalAssetNumber());
        capitalAssetInformation.setCapitalAssetQuantity(assetInfo.getCapitalAssetQuantity());
        capitalAssetInformation.setCapitalAssetTypeCode(assetInfo.getCapitalAssetTypeCode());
        capitalAssetInformation.setVendorDetailAssignedIdentifier(assetInfo
                .getVendorDetailAssignedIdentifier());
        capitalAssetInformation.setVendorHeaderGeneratedIdentifier(assetInfo
                .getVendorHeaderGeneratedIdentifier());
        capitalAssetInformation.setVendorName(assetInfo.getVendorName());
    }


    /**
     * Adapts Capital Asset Information Detail to KFS Capital Asset Information Detail
     * 
     * @param source
     * @param target
     */
    protected void adapt(FinancialCapitalAssetInformationDetail source,
            CapitalAssetInformationDetail target) {
        target.setBuildingCode(source.getBuildingCode());
        target.setBuildingRoomNumber(source.getBuildingRoomNumber());
        target.setBuildingSubRoomNumber(source.getBuildingSubRoomNumber());
        target.setCampusCode(source.getCampusCode());
        target.setCapitalAssetSerialNumber(source.getCapitalAssetSerialNumber());
        target.setCapitalAssetTagNumber(source.getCapitalAssetTagNumber());
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialInternalBillingService#validateProperty(java.lang.String,
     *      java.lang.String)
     */
		public boolean validateProperty(String parameterName, String propertyValue) {
			boolean isAllowed = true;
			if (financialParameterService.parameterExists(
				KfsParameterConstants.FINANCIAL_PROCESSING_DOCUMENT.class, parameterName)) {
				//TODO: NWU - Determine if same behaviour as parameterEvaluator
				isAllowed = propertyValue.equals(financialParameterService.getParameterValueAsString(KfsParameterConstants.FINANCIAL_PROCESSING_DOCUMENT.class, parameterName));
			}
			if (financialParameterService.parameterExists(InternalBillingDocument.class, parameterName)) {
				//TODO: NWU - Determine if same behaviour as parameterEvaluator
				isAllowed = propertyValue.equals(financialParameterService.getParameterValueAsString(InternalBillingDocument.class, parameterName));
			}
			return isAllowed;
		}

    /**
     * Gets the financialDocumentService property
     * 
     * @return Returns the financialDocumentService
     */
    public FinancialDocumentService getFinancialDocumentService() {
        return this.financialDocumentService;
    }

    /**
     * Sets the financialDocumentService property value
     * 
     * @param financialDocumentService The financialDocumentService to set
     */
    public void setFinancialDocumentService(FinancialDocumentService financialDocumentService) {
        this.financialDocumentService = financialDocumentService;
    }

}
