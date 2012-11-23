/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.businessobject.MMCapitalAssetInformation;
import org.kuali.ext.mm.businessobject.MMCapitalAssetInformationDetail;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.gl.GlConstants;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformation;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformationDetail;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.ext.mm.sys.batch.dataaccess.RentalDemurrageDao;
import org.kuali.ext.mm.sys.batch.service.RentalDemurrageService;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author harsha07
 */
public class RentalDemurrageServiceImpl implements RentalDemurrageService {
    private static final Logger LOG = Logger.getLogger(RentalDemurrageServiceImpl.class);
    private RentalDemurrageDao rentalDemurrageDao;

    /**
     * Gets the rentalDemurrageDao property
     *
     * @return Returns the rentalDemurrageDao
     */
    public RentalDemurrageDao getRentalDemurrageDao() {
        return this.rentalDemurrageDao;
    }

    /**
     * Sets the rentalDemurrageDao property value
     *
     * @param rentalDemurrageDao The rentalDemurrageDao to set
     */
    public void setRentalDemurrageDao(RentalDemurrageDao rentalDemurrageDao) {
        this.rentalDemurrageDao = rentalDemurrageDao;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.service.RentalDemurrageService#processRentalDemurrageCharges()
     */
    public void processRentalDemurrageCharges() {
        Map<String, List<FinancialInternalBillingItem>> rentalDemurrageItemLines = rentalDemurrageDao
                .getRentalDemurrageItemLines();        
        Map<String, List<FinancialAccountingLine>> rentalDemurrageAccountLines = rentalDemurrageDao
                .getRentalDemurrageAccountLines();

        for (String key : rentalDemurrageItemLines.keySet()) {
            Warehouse warehouse = SpringContext.getBean(BusinessObjectService.class)
                    .findBySinglePrimaryKey(Warehouse.class, key.split("-")[0]);
            List<FinancialInternalBillingItem> lineItems = rentalDemurrageItemLines.get(key);
            List<FinancialAccountingLine> acctLines = rentalDemurrageAccountLines.get(key);
            if (warehouse != null && warehouse.isActive()) {
                DocumentHeader ibDoc = processInternalBilling(warehouse, lineItems, acctLines);
                if (ibDoc != null) {
                    for (FinancialInternalBillingItem rental : lineItems) {
                        rentalDemurrageDao.updateLastChargeDate(rental.getRentalId());
                    }
                }
            }
            else {
                // LOG error here
                LOG.warn("Warehouse " + lineItems.get(0).getWarehouseCode()
                        + " is not valid, so batch did not post charges to the depts");
            }
        }

    }

    protected DocumentHeader processInternalBilling(Warehouse warehouse,
            List<FinancialInternalBillingItem> ibItems,
            List<FinancialAccountingLine> expenseAccountLines) {
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (factory.isSystemAvailable()) {
            KualiDecimal totalChargeAmt = KualiDecimal.ZERO;
            for (FinancialAccountingLine account : expenseAccountLines) {
                totalChargeAmt = totalChargeAmt.add(account.getAmount());
            }
            ArrayList<FinancialAccountingLine> incomeAcctLines = new ArrayList<FinancialAccountingLine>();
            FinancialAccountingLine finAcctLine = createFinancialAccountingLine(warehouse
                    .getIncomeAccount(), totalChargeAmt);
            incomeAcctLines.add(finAcctLine);
            return factory.getFinancialInternalBillingService().submitBatchInternalBillingDocument(
                    warehouse.getBillingProfile(), ibItems, incomeAcctLines, expenseAccountLines,
                    null);
        }
        return null;
    }


    /**
     * @param assetInfo MMCapitalAssetInformation
     * @param capitalAssetInformation FinancialCapitalAssetInformation
     */
    protected void adapt(MMCapitalAssetInformation assetInfo,
            FinancialCapitalAssetInformation capitalAssetInformation) {
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
     * @param source MMCapitalAssetInformationDetail
     * @param target FinancialCapitalAssetInformationDetail
     */
    protected void adapt(MMCapitalAssetInformationDetail source,
            FinancialCapitalAssetInformationDetail target) {
        target.setBuildingCode(source.getBuildingCode());
        target.setBuildingRoomNumber(source.getBuildingRoomNumber());
        target.setBuildingSubRoomNumber(source.getBuildingSubRoomNumber());
        target.setCampusCode(source.getCampusCode());
        target.setCapitalAssetSerialNumber(source.getCapitalAssetSerialNumber());
        target.setCapitalAssetTagNumber(source.getCapitalAssetTagNumber());
    }

    /**
     * Creates a financial accounting line using Warehouse Accouting information
     *
     * @param mmAcctLine Warehouse Account
     * @param chargeAmt Charge Amount
     * @return
     */
    protected FinancialAccountingLine createFinancialAccountingLine(WarehouseAccounts mmAcctLine,
            KualiDecimal chargeAmt) {
        FinancialAccountingLine finAcctLine = new FinancialAccountingLine();
        finAcctLine.setAccountNumber(mmAcctLine.getAccountNbr());
        finAcctLine.setAmount(chargeAmt);
        finAcctLine.setBalanceTypeCode("AC");
        finAcctLine.setChartOfAccountsCode(mmAcctLine.getFinCoaCd());
        finAcctLine.setFinancialDocumentLineDescription("Pay warehouse"
                + mmAcctLine.getWarehouseCd());
        finAcctLine.setFinancialDocumentLineTypeCode(MMConstants.FIN_ACCT_LINE_TYP_FROM);
        finAcctLine.setFinancialObjectCode(mmAcctLine.getFinObjectCd());
        finAcctLine.setFinancialSubObjectCode(mmAcctLine.getFinSubObjCd());
        finAcctLine.setObjectBudgetOverride(false);
        finAcctLine.setObjectBudgetOverrideNeeded(false);
        finAcctLine.setOrganizationReferenceId("");
        finAcctLine.setOverrideCode("");
        finAcctLine.setPostingYear(SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .getFinancialUniversityDateService().getCurrentFiscalYear());
        finAcctLine.setProjectCode(mmAcctLine.getProjectCd());
        finAcctLine.setReferenceNumber("");
        finAcctLine.setReferenceOriginCode(GlConstants.getFinancialSystemOriginCode());
        finAcctLine.setReferenceTypeCode("");
        finAcctLine.setSalesTaxRequired(false);
        finAcctLine.setSubAccountNumber(mmAcctLine.getSubAcctNbr());
        return finAcctLine;
    }

}
