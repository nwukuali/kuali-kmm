/**
 *
 */
package org.kuali.ext.mm.document;

import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.Stock;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.purap.document.FinancialRequisitionDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.krad.rules.rule.event.KualiDocumentEvent;
import org.kuali.rice.krad.rules.rule.event.RouteDocumentEvent;

import java.util.ArrayList;
import java.util.List;


/**
 * @author rponraj
 */
public class ReOrderDocument extends OrderDocument {
    private static final long serialVersionUID = -3775114185349833737L;
    private List<String> relatedFinancialDocNumbers;

    /**
     *
     */
    public ReOrderDocument() {
        super();
        this.relatedFinancialDocNumbers = new ArrayList<String>(0);
    }

    @Override
    public void prepareForSave(KualiDocumentEvent event) {

        List<OrderDetail> lisData = new ArrayList<OrderDetail>(0);

        List<Stock> stocks = new ArrayList<Stock>(0);

        for (OrderDetail odetail : this.getOrderDetails()) {
            if (odetail.getCatalogItem().getStock() != null
                    && odetail.getCatalogItem().getStock().getRemoveUntilDate() != null)
                stocks.add(odetail.getCatalogItem().getStock());
        }

        MMServiceLocator.getBusinessObjectService().save(stocks);

        if (!MMUtil.isCollectionEmpty(this.getOrderDetailsForReorder())) {
            for (OrderDetail odetail : this.getOrderDetailsForReorder()) {
                this.getOrderDetails().add(odetail);
            }
            this.getOrderDetailsForReorder().clear();
        }

        if (event instanceof RouteDocumentEvent) {
            for (OrderDetail rd : this.getOrderDetails()) {
                if (rd.isItemToBeRemoved())
                    lisData.add(rd);
            }

            if (!MMUtil.isCollectionEmpty(lisData)) {
                for (OrderDetail rd : lisData) {
                    this.getOrderDetails().remove(rd);
                }
            }
            // re-index the items here again, so it can avoid index errors in MM
            List<OrderDetail> details = this.getOrderDetails();
            for (int i = 0; i < details.size(); i++) {
                OrderDetail detail = details.get(i);
                detail.setItemLineNumber(i + 1);
            }

        }

    }

    /**
     * Gets the relatedFinancialDocNumbers property
     * 
     * @return Returns the relatedFinancialDocNumbers
     */
    public List<String> getRelatedFinancialDocNumbers() {
        if (this.relatedFinancialDocNumbers.isEmpty() && getReqsId() != null) {
            FinancialSystemAdaptorFactory adaptorFactory = SpringContext
                    .getBean(FinancialSystemAdaptorFactory.class);
            if (adaptorFactory.isSystemAvailable()) {
                FinancialRequisitionDocument requisitionById = adaptorFactory
                        .getFinancialRequisitionService().getRequisitionById(getReqsId());
                if (requisitionById != null) {
                    this.relatedFinancialDocNumbers.add(requisitionById.getDocumentNumber());
                }
            }
        }
        return this.relatedFinancialDocNumbers;
    }


    /**
     * Sets the relatedFinancialDocNumbers property value
     * 
     * @param relatedFinancialDocNumbers The relatedFinancialDocNumbers to set
     */
    public void setRelatedFinancialDocNumbers(List<String> relatedFinancialDocNumbers) {
        this.relatedFinancialDocNumbers = relatedFinancialDocNumbers;
    }

    /**
     * @see org.kuali.ext.mm.document.OrderDocument#buildListOfDeletionAwareLists()
     */
    @Override
    public List buildListOfDeletionAwareLists() {
        List list = super.buildListOfDeletionAwareLists();
        list.add(getOrderDetails());
        return list;
    }
}
