package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.BackOrder;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.StockService;
import org.kuali.rice.core.util.RiceConstants;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.MaintenanceDocumentBase;
import org.kuali.rice.kns.rules.PromptBeforeValidationBase;
import org.kuali.rice.kns.service.KualiConfigurationService;
import org.kuali.rice.kns.util.ObjectUtils;


public class BackOrderPreRules extends PromptBeforeValidationBase {

	/**
	 * @see org.kuali.rice.kns.rules.PromptBeforeValidationBase#doPrompts(org.kuali.rice.kns.document.Document)
	 */
	@Override
	public boolean doPrompts(Document document) {
	    MaintenanceDocumentBase maintDoc = (MaintenanceDocumentBase)document;
	    BackOrder oldBackorder = (BackOrder)maintDoc.getOldMaintainableObject().getBusinessObject();
		BackOrder newBackorder = (BackOrder)maintDoc.getNewMaintainableObject().getBusinessObject();
		
		StockService stockService = SpringContext.getBean(StockService.class);
        String warehouseCode = null;
        //Get warehouse from order document
        if(ObjectUtils.isNotNull(newBackorder.getFromPickListLine())
                && ObjectUtils.isNotNull(newBackorder.getFromPickListLine().getOrderDetail())
                && ObjectUtils.isNotNull(newBackorder.getFromPickListLine().getOrderDetail().getOrderDocument())) {
            warehouseCode = newBackorder.getFromPickListLine().getOrderDetail().getOrderDocument().getWarehouseCd();
        }        
		//Get quantity on had for the warehouse
        Integer quantityOnHand = stockService.getStockQuantityOnHand(newBackorder.getStockId(), warehouseCode);
        
        //Do not call this if a question has already been asked and answered successfully
        if(StringUtils.isBlank(event.getQuestionContext())) {
    		if(MMConstants.BackOrder.OPTION_CANCEL.equals(newBackorder.getActionCode())) {
    			if (!isOkCancelingBackOrder()) {
                    event.setActionForwardName(RiceConstants.MAPPING_BASIC);
                    return false;
                }
    
    		}
    		else if (newBackorder.getBackOrderStockQty() < oldBackorder.getBackOrderStockQty()) {
    		    if(MMConstants.BackOrder.OPTION_FILL.equals(newBackorder.getActionCode())) {
    		       Integer remainingQuantity = oldBackorder.getBackOrderStockQty() - newBackorder.getBackOrderStockQty();
    	           if(!isOkReduceAndFill(newBackorder.getBackOrderStockQty(), remainingQuantity)) {
    	               event.setActionForwardName(RiceConstants.MAPPING_BASIC);
                       return false;
    	           }	           
    	        }
    		    else {
        		    if (!isOkAlteringQuantity()) {
                        event.setActionForwardName(RiceConstants.MAPPING_BASIC);
                        return false;
                    }
    		    }
    		}
        }
		if(MMConstants.BackOrder.OPTION_FILL.equals(newBackorder.getActionCode()) 
                && quantityOnHand < newBackorder.getBackOrderStockQty()) {
            if(!isOkFillingWithoutStockQuantity(newBackorder.getBackOrderStockQty(), quantityOnHand)) {
                event.setActionForwardName(RiceConstants.MAPPING_BASIC);
                return false;
            }
        }
		return true;
	}

	/**
     * @return true if the user wants to proceed with filling the
     */
    private boolean isOkFillingWithoutStockQuantity(Integer fillQuantity, Integer quantityOnHand) {
        KualiConfigurationService kualiConfiguration = SpringContext.getBean(KualiConfigurationService.class);
        String warningMessage = kualiConfiguration.getPropertyString(MMKeyConstants.BackOrder.FILL_WITHOUT_STOCK_QUESTION);
        warningMessage = warningMessage.replace("{0}", fillQuantity.toString());
        warningMessage = warningMessage.replace("{1}", quantityOnHand.toString());
        return super.askOrAnalyzeYesNoQuestion(MMKeyConstants.BackOrder.FILL_WITHOUT_STOCK_QUESTION, warningMessage);
    }

    /**
     * @return True if the user wants to proceed with reducing the quantity and filling the backorder
     */
    private boolean isOkReduceAndFill(Integer fillQuantity, Integer remainingQuantity) {
        KualiConfigurationService kualiConfiguration = SpringContext.getBean(KualiConfigurationService.class);
        String warningMessage = kualiConfiguration.getPropertyString(MMKeyConstants.BackOrder.REDUCE_AND_FILL_QUESTION);
        warningMessage = warningMessage.replace("{0}", fillQuantity.toString());
        warningMessage = warningMessage.replace("{1}", remainingQuantity.toString());
        return super.askOrAnalyzeYesNoQuestion(MMKeyConstants.BackOrder.REDUCE_AND_FILL_QUESTION, warningMessage);
    }

    /**
	 * @return True if the user wants to proceed with canceling the backorder
	 */
	private boolean isOkCancelingBackOrder() {
        KualiConfigurationService kualiConfiguration = SpringContext.getBean(KualiConfigurationService.class);
        String warningMessage = kualiConfiguration.getPropertyString(MMKeyConstants.BackOrder.CANCEL_QUESTION);
        return super.askOrAnalyzeYesNoQuestion(MMKeyConstants.BackOrder.CANCEL_QUESTION, warningMessage);
	}
	
	/**
     * @return True if the user wants to proceed with altering the quantity on the backorder
     */
    private boolean isOkAlteringQuantity() {
        KualiConfigurationService kualiConfiguration = SpringContext.getBean(KualiConfigurationService.class);
        String warningMessage = kualiConfiguration.getPropertyString(MMKeyConstants.BackOrder.QUANTITY_MODIFY_QUESTION);
        return super.askOrAnalyzeYesNoQuestion(MMKeyConstants.BackOrder.QUANTITY_MODIFY_QUESTION, warningMessage);
    }

}
