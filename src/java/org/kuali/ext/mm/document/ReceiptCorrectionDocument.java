/**
 *
 */
package org.kuali.ext.mm.document;

import org.kuali.ext.mm.businessobject.CheckinDetail;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.WarehouseObject;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.kuali.ext.mm.gl.service.GeneralLedgerProcessor;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.WarehouseAccountingService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.krad.rules.rule.event.KualiDocumentEvent;
import org.kuali.rice.krad.rules.rule.event.RouteDocumentEvent;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author rponraj
 */
public class ReceiptCorrectionDocument extends CheckinDocument {

    /**
     *
     */
    private static final long serialVersionUID = -8610393750707784117L;

    @Override
    public void doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) {
        super.doRouteStatusChange(statusChangeEvent);
        WorkflowDocument workflowDocument = getDocumentHeader().getWorkflowDocument();

        if (workflowDocument.isProcessed()) {
            try {
                MMServiceLocator.getReceiptCorrectionService().processReceiptCorrectionDocument(this);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        else if (workflowDocument.isFinal()) {
            this.setFinalInd(true);
        }

        SpringContext.getBean(GeneralLedgerProcessor.class).doRouteStatusChange(this,
                getDocumentHeader());
    }

    @Override
    public List<FinancialGeneralLedgerPendingEntry> generateGlpeEntries() {
        GeneralLedgerBuilderService generalLedgerBuilderService = SpringContext
                .getBean(GeneralLedgerBuilderService.class);
        WarehouseAccountingService warehouseAcctService = SpringContext
                .getBean(WarehouseAccountingService.class);
        Warehouse warehouse = SpringContext.getBean(BusinessObjectService.class)
                .findBySinglePrimaryKey(Warehouse.class, getWarehouseCode());
        if (warehouse == null || !warehouse.isActive()) {
            throw new RuntimeException("Warehouse is not valid.");
        }
        WarehouseObject finObject = warehouseAcctService.findWarehouseObjectByReason(
                getWarehouseCode(), MMConstants.StockTransReason.TRANS_IN);
        if (finObject == null) {
            throw new RuntimeException("Warehouse object code is not specified");
        }
        KualiDecimal totalCheckInOrderAmount = this.getTotalCorrectedOrderAmount();
        if (totalCheckInOrderAmount == null || totalCheckInOrderAmount.isZero()) {
            return new ArrayList<FinancialGeneralLedgerPendingEntry>();
        }

        HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups = new HashMap<WarehouseGlGroup, WarehouseGlGroup>();
        generalLedgerBuilderService.decrementResaleItems(glGroups, warehouse,MMConstants.StockTransReason.DCORRECT,
                totalCheckInOrderAmount, "Order received");
        ArrayList<FinancialGeneralLedgerPendingEntry> entries = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        for (WarehouseGlGroup group : glGroups.values()) {
            entries.add(group.getTargetEntry());
        }
        return entries;
    }

    public KualiDecimal getTotalCorrectedOrderAmount() {
        Double totPrice = 0.0;
        for (CheckinDetail cdetail : this.getCheckinDetails()) {
            MMDecimal qty = new MMDecimal(cdetail.getCorrectedQuantity());
            OrderDetail odetail = cdetail.getOrderDetail();
            MMDecimal price = MMDecimal.ZERO;

            if (odetail != null) {
                price = (odetail.getOrderItemCostAmt() == null ? MMDecimal.ZERO : odetail
                        .getOrderItemCostAmt());
            }
            else {
                MMDecimal sprice = cdetail.getStock().getStockPrice();
                price = ObjectUtils.isNull(sprice) ? MMDecimal.ZERO : sprice;

            }
            totPrice = totPrice + price.multiply(qty).doubleValue();
        }
        return new KualiDecimal(totPrice);
    }

    @Override
    public void prepareForSave(KualiDocumentEvent event) {
        if (event instanceof RouteDocumentEvent) {
            List<CheckinDetail> listData = new ArrayList<CheckinDetail>(0);
            for (CheckinDetail rd : this.getCheckinDetails()) {
                if (!rd.isLineCorrected())
                    listData.add(rd);
            }

            if (!MMUtil.isCollectionEmpty(listData)) {
                for (CheckinDetail rd : listData) {
                    this.getCheckinDetails().remove(rd);
                    rd.delete();
                }
            }
        }
    }
}
