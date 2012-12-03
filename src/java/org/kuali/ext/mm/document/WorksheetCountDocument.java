package org.kuali.ext.mm.document;


import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.gl.GeneralLedgerPostable;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.kuali.ext.mm.gl.service.GeneralLedgerProcessor;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.coreservice.framework.CoreFrameworkServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DataDictionaryService;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author harsha07
 */
@Entity
@Table(name = "MM_WORKSHEET_COUNT_T")
public class WorksheetCountDocument extends StoresTransactionalDocumentBase implements
        java.io.Serializable, GeneralLedgerPostable {

    private static final long serialVersionUID = -5895422859869476670L;

    private WorksheetStatus worksheetStatus;

    private String warehouseCd;

    private String parentDocumentNumber;

    private WorksheetCountDocument parentDocument;

    private Warehouse warehouse;

    private String zoneCd;

    private String worksheetStatusCode;

    private Integer worksheetCnt;

    private Timestamp worksheetOriginalDt;

    private Timestamp worksheetCompletionDt;

    private List<WorksheetCounter> worksheetCounters = new ArrayList<WorksheetCounter>();

    private List<StockCount> stockCounts = new ArrayList<StockCount>();

    private boolean fullInventory;

    private List<FinancialGeneralLedgerPendingEntry> financialGeneralLedgerPendingEntries;

    private String cycleCntCd;

    public WorksheetCountDocument() {
    }

    public WorksheetStatus getWorksheetStatus() {
        return this.worksheetStatus;
    }

    public void setWorksheetStatus(WorksheetStatus worksheetStatus) {
        this.worksheetStatus = worksheetStatus;
    }

    public String getWarehouseCd() {
        return this.warehouseCd;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getWarehouseName() {
        return this.warehouse != null ? this.warehouse.getWarehouseNme() : "";
    }

    public Integer getWorksheetCnt() {
        return this.worksheetCnt;
    }

    public void incrementWorksheetCount() {
        if (this.worksheetCnt != null) {
            this.worksheetCnt++;
        }
        else {
            this.worksheetCnt = 1;
        }
    }

    public int getStockCountSize() {

        return (this.getStockCounts() == null || this.getStockCounts().size() < 1) ? 0 : this
                .getStockCounts().size();
    }

    public String getWorksheetCntText() {

        if (this.worksheetCnt != null)
            return this.worksheetCnt.toString();

        return "";
    }

    public int getWorksheetCntLength() {

        if (this.worksheetCnt != null)
            return this.worksheetCnt.toString().trim().length();

        return -1;
    }

    public void setWorksheetCnt(Integer worksheetCnt) {
        this.worksheetCnt = worksheetCnt;
    }

    public Timestamp getWorksheetOriginalDt() {
        return this.worksheetOriginalDt;
    }

    public void setWorksheetOriginalDt(Timestamp worksheetOriginalDt) {
        this.worksheetOriginalDt = worksheetOriginalDt;
    }

    public Timestamp getWorksheetCompletionDt() {
        return this.worksheetCompletionDt;
    }

    public void setWorksheetCompletionDt(Timestamp worksheetCompletionDt) {
        this.worksheetCompletionDt = worksheetCompletionDt;
    }

    public List<WorksheetCounter> getWorksheetCounters() {
        return this.worksheetCounters;
    }

    public void setWorksheetCounters(List<WorksheetCounter> worksheetCounters) {
        this.worksheetCounters = worksheetCounters;
    }
    // Item return by location
    public List<StockCount> getStockCounts() {
        return this.stockCounts;
    }

    public List<String> getStockIds() {

        ArrayList<String> lisStockIds = new ArrayList<String>();
        if (MMUtil.isCollectionEmpty(getStockCounts()))
            return null;

        for (StockCount scount : getStockCounts()) {
            lisStockIds.add(scount.getStockId());
        }
        return lisStockIds;
    }

    public void setStockCounts(List<StockCount> stockCounts) {
        this.stockCounts = stockCounts;
    }

    /**
     * This method returns the count percentage of the items
     * 
     * @return
     */
    public KualiDecimal getCountPercentage() {
        if (MMConstants.WorksheetStatus.WORKSHEET_INITIATED.equals(getWorksheetStatusCode())
                || MMConstants.WorksheetStatus.WORKSHEET_PRINTED.equals(getWorksheetStatusCode())
                || MMConstants.WorksheetStatus.WORKSHEET_REPRINTED.equals(getWorksheetStatusCode())) {
            return KualiDecimal.ZERO;
        }
        Integer mismatchedCount = StockCountMap.getMismatchedCount(getDocumentNumber());
        Integer totalStockCount = StockCountMap.getTotalStockCount(getDocumentNumber());
        if (totalStockCount == 0) {
            return KualiDecimal.ZERO;
        }
        int couPer = Math
                .round(((float) (totalStockCount - mismatchedCount) / totalStockCount) * 100);
        return new KualiDecimal(couPer);
    }


    /**
     * This method returns whether the stock item count is balanced
     * 
     * @return
     */
    public boolean isBalanced() {
        if (MMConstants.WorksheetStatus.WORKSHEET_INITIATED.equals(getWorksheetStatusCode())
                || MMConstants.WorksheetStatus.WORKSHEET_PRINTED.equals(getWorksheetStatusCode())
                || MMConstants.WorksheetStatus.WORKSHEET_REPRINTED.equals(getWorksheetStatusCode())) {
            return false;

        }
        Integer mismatchedCount = StockCountMap.getMismatchedCount(getDocumentNumber());
        return mismatchedCount == 0;
    }

    public String getBalanceDesc() {

        if (this.isBalanced())
            return "YES";

        return "NO";

    }

    public void addWorksheetCounter(WorksheetCounter worksheetCounter) {
        this.worksheetCounters.add(worksheetCounter);
    }

    public int getWorksheetCountersSize() {
        return this.worksheetCounters.size();
    }

    public void removeWorksheetCounter(int index) {
        this.worksheetCounters.remove(index);
    }

    public void removeWorkSheetCounter(int index) {
        this.worksheetCounters.remove(index);
    }

    public void setWarehouseCd(String warehouseCd) {
        this.warehouseCd = warehouseCd;
    }

    public void addStockCount(StockCount aStockCount) {
        getStockCounts().add(aStockCount);
    }

    public boolean isDocumentReprinted() {
        return this.worksheetStatusCode != null
                && this.worksheetStatusCode
                        .equalsIgnoreCase(MMConstants.WorksheetStatus.WORKSHEET_REPRINTED);
    }

    public boolean isDocumentPrinted() {
        return this.worksheetStatusCode != null
                && this.worksheetStatusCode
                        .equalsIgnoreCase(MMConstants.WorksheetStatus.WORKSHEET_PRINTED);
    }

    public String getWorksheetStatusCode() {
        return worksheetStatusCode;
    }

    public void setWorksheetStatusCode(String worksheetStatusCode) {
        this.worksheetStatusCode = worksheetStatusCode;
    }

    public String getZoneCd() {
        return zoneCd;
    }

    public String getParentDocumentNumber() {
        return parentDocumentNumber;
    }

    public void setParentDocumentNumber(String parentDocumentNumber) {
        this.parentDocumentNumber = parentDocumentNumber;
    }

    public WorksheetCountDocument getParentDocument() {
        return parentDocument;
    }

    public void setParentDocument(WorksheetCountDocument parentDocument) {
        this.parentDocument = parentDocument;
    }

    public void setZoneCd(String zoneCd) {
        this.zoneCd = zoneCd;
    }

    public String getCycleCntCd() {
        return this.cycleCntCd;
    }

    public void setCycleCntCd(String cycleCntCd) {
        this.cycleCntCd = cycleCntCd;
    }

    public boolean isWorksheetItemsMatching() {
        if (getStockCounts() != null && getStockCounts().size() > 0) {
            for (StockCount st : getStockCounts()) {
                if (!st.isReprinted() && !st.isItemCountMatching()
                        && StringUtils.isBlank(st.getStockTransReasonCd()))
                    return false;
            }
        }
        return true;
    }

    /**
     * returns the list of mismatched stock count objects .This excludes items having reprinted as stock transaction reason code
     * 
     * @return
     */
    public List<StockCount> getOveriddenMismatchedStocks() {

        List<StockCount> scounts = new ArrayList<StockCount>(0);

        for (StockCount scount : getStockCounts()) {
            if ((!StringUtils.isEmpty(scount.getStockTransReasonCd()) && !scount
                    .getStockTransReasonCd().equalsIgnoreCase(
                            MMConstants.WorksheetCountDocument.WORKSHEET_STOCK_ITEM_REPRINTED))
                    && scount.isStockOverridden())
                scounts.add(scount);
        }

        return scounts;
    }

    @Override
    public void doRouteStatusChange(DocumentRouteStatusChange documentroutestatuschangedto) {
        StockCountMap.reset();
        super.doRouteStatusChange(documentroutestatuschangedto);
        WorkflowDocument workflowDocument = getDocumentHeader().getWorkflowDocument();

        /**
         * SETS THE STOCK TARNSACTION REAOSN CODE TO WITH_IN_TOLERANCE IF THE ENETERED QUANTITY IS LESS THAN OR EQUAL TO THE MAXIMUM
         * ALLOWED TOLERANCE
         */

        if (workflowDocument.isEnroute()) {
            String docStatusCode = this.getWorksheetStatusCode();
            KualiDecimal maxToleranceVal = new KualiDecimal();
          //TODO: NWU - This the if you wanted here?
            if (CoreFrameworkServiceLocator.getParameterService().parameterExists(
                    MMConstants.MM_NAMESPACE, MMConstants.Parameters.DOCUMENT,
                    MMConstants.Parameters.MAX_PRICE_TOLERANCE_ALLOWED)) {
                maxToleranceVal = new KualiDecimal(CoreFrameworkServiceLocator
                        .getParameterService()
                        .getParameterValuesAsString(MMConstants.MM_NAMESPACE,
                                MMConstants.Parameters.DOCUMENT,
                                MMConstants.Parameters.MAX_PRICE_TOLERANCE_ALLOWED).iterator()
                        .next());
            }
            if (!StringUtils.isEmpty(docStatusCode)
                    && docStatusCode
                            .equalsIgnoreCase(MMConstants.WorksheetStatus.WORKSHEET_ENTERED)) {
                for (StockCount scount : this.getStockCounts()) {
                    MMDecimal varianceCost = scount.getVarianceCost() != null ? scount
                            .getVarianceCost().abs() : MMDecimal.ZERO;
                    if (scount.getVarianceCost() != null
                            && varianceCost.isLessEqual(maxToleranceVal)) {
                        scount
                                .setStockTransReasonCd(MMConstants.StockTransReason.WITH_IN_TOLERANCE);
                    }
                }
            }
        }

        if (workflowDocument.isCanceled() || workflowDocument.isDisapproved()) {
            cancelWorksheetCountDocument();
        }
        if (!workflowDocument.isFinal()) {
            // Update snapshot before changing stock balance
            for (StockCount scount : this.getStockCounts()) {
                scount.setSnapshotQty(scount.getBeforeItemQty());
            }
        }
        if (workflowDocument.isProcessed()) {
            setWorksheetStatusCode(MMConstants.WorksheetStatus.WORKSHEET_CLOSED);
            setWorksheetCompletionDt(SpringContext.getBean(DateTimeService.class)
                    .getCurrentTimestamp());
            MMServiceLocator.getStockService().updateStockBalancesForApprovedItems(
                    getOveriddenMismatchedStocks());
            List<StockHistory> lisHisData = MMServiceLocator.getStockHistoryService()
                    .createStockHistoryForWorksheetCountDocument(this);
            if (!MMUtil.isCollectionEmpty(lisHisData))
                SpringContext.getBean(BusinessObjectService.class).save(lisHisData);
        }
        // generate GL entries
        SpringContext.getBean(GeneralLedgerProcessor.class).doRouteStatusChange(this,
                getDocumentHeader());
    }

    /**
     * 
     */
    private void cancelWorksheetCountDocument() {
        MMServiceLocator.getStockItemLookupService().updateCountWorksheetStatus(
                getDocumentHeader().getDocumentNumber(),
                MMConstants.WorksheetStatus.WORKSHEET_CANCELED);
        List<StockCount> stockCountsList = getStockCounts();
        for (StockCount stockCount : stockCountsList) {
            stockCount.delete();
        }
        getStockCounts().clear();
    }

    public boolean isAnyWorksheetItemReprinted() {
        for (StockCount scount : this.getStockCounts()) {
            if (scount.isReprinted())
                return true;
        }
        return false;
    }

    public boolean isDocInFinalState() {
        return this.documentHeader.getWorkflowDocument().isFinal();
    }


    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#generateGlpeEntries()
     */
    public List<FinancialGeneralLedgerPendingEntry> generateGlpeEntries() {
        GeneralLedgerBuilderService generalLedgerBuilderService = SpringContext
                .getBean(GeneralLedgerBuilderService.class);
        List<StockCount> mismatchedStocks = getOveriddenMismatchedStocks();
        HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups = new HashMap<WarehouseGlGroup, WarehouseGlGroup>();
        for (StockCount stockCount : mismatchedStocks) {
            KualiDecimal stockPrice = new KualiDecimal(stockCount.getStock().getStockPrice()
                    .doubleValue());
            KualiDecimal stockCntDifference = new KualiDecimal(stockCount
                    .getStockItemQuantityDifference().doubleValue());
            KualiDecimal stockDiffAmt = new KualiDecimal(stockPrice.multiply(stockCntDifference)
                    .doubleValue());
            if (stockDiffAmt.isNonZero()) {
                // get the accounting type code from stock transaction reason code
                if (stockDiffAmt.isNegative()) {
                    generalLedgerBuilderService
                            .decrementInventory(glGroups, warehouse, stockCount
                                    .getStockTransReasonCd(), stockDiffAmt, "Inventory shrinkage");
                }
                else {
                    generalLedgerBuilderService
                            .incrementInventory(glGroups, warehouse, stockCount
                                    .getStockTransReasonCd(), stockDiffAmt, "Inventory shrinkage");
                }
            }
        }
        ArrayList<FinancialGeneralLedgerPendingEntry> entries = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        for (WarehouseGlGroup group : glGroups.values()) {
            entries.add(group.getTargetEntry());
        }
        return entries;
    }


    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getDocumentTypeCode()
     */
    public String getDocumentTypeCode() {
        return SpringContext.getBean(DataDictionaryService.class).getDocumentTypeNameByClass(
                getClass());
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getFinancialGeneralLedgerPendingEntries()
     */
    public List<FinancialGeneralLedgerPendingEntry> getFinancialGeneralLedgerPendingEntries() {
        if (financialGeneralLedgerPendingEntries == null) {
            financialGeneralLedgerPendingEntries = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        }
        return financialGeneralLedgerPendingEntries;
    }

    /**
     * @return
     */
    public List<FinancialGeneralLedgerPendingEntry> getApprovedGeneralLedgerPendingEntries() {
        return SpringContext.getBean(GeneralLedgerProcessor.class)
                .getApprovedGeneralLedgerPendingEntries(getDocumentNumber());
    }


    /**
     * @return
     */
    public List<StockCount> getStocksToBeReprinted() {
        List<StockCount> lisData = new ArrayList<StockCount>(0);
        for (StockCount st : this.getStockCounts()) {
            if (st.isMarkedForReprint()) {
                lisData.add(st);
            }
        }
        return lisData;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#setFinancialGeneralLedgerPendingEntries(java.util.List)
     */
    public void setFinancialGeneralLedgerPendingEntries(
            List<FinancialGeneralLedgerPendingEntry> entries) {
        this.financialGeneralLedgerPendingEntries = entries;
    }


    /**
     * Gets the fullInventory property
     * 
     * @return Returns the fullInventory
     */
    public boolean isFullInventory() {
        return this.fullInventory;
    }

    /**
     * Sets the fullInventory property value
     * 
     * @param fullInventory The fullInventory to set
     */
    public void setFullInventory(boolean fullInventory) {
        this.fullInventory = fullInventory;
    }

}
