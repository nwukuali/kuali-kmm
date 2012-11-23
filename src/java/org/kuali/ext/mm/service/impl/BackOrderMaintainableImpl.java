/**
 *
 */
package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.BackOrder;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.BackOrderService;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.bo.DocumentHeader;


/**
 * @author schneppd
 *
 */
public class BackOrderMaintainableImpl extends KualiMaintainableImpl {

    /**
     * 
     */
    private static final long serialVersionUID = 8788373531686195470L;

    @Override
    public void doRouteStatusChange(DocumentHeader documentHeader) {
        WorkflowDocument workflowDocument = documentHeader.getWorkflowDocument();
        if (workflowDocument.isProcessed()) {
            BackOrder backorder = (BackOrder)this.getBusinessObject();
            BackOrderService backOrderService = SpringContext.getBean(BackOrderService.class);
            if(MMConstants.BackOrder.OPTION_CANCEL.equals(backorder.getActionCode()))
                backOrderService.cancelBackOrder(backorder);
            else if(MMConstants.BackOrder.OPTION_REDUCE.equals(backorder.getActionCode()))
                backOrderService.reduceBackOrder(backorder);    
            else if(MMConstants.BackOrder.OPTION_FILL.equals(backorder.getActionCode()))
                backOrderService.relieveBackOrder(backorder, false);
            //Set fromPickListLine referenceObject to null to prevent a bug in linkAndSave and from
            //blowing up during an ObjectUtils.setProperty
            backorder.setFromPickListLine(null);
        }       
        
    }



}
