package org.kuali.ext.mm.document.validation.impl;

import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.PickVerifyDocument;
import org.kuali.ext.mm.service.PickVerifyService;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.util.RiceConstants;
import org.kuali.rice.kns.rules.PromptBeforeValidationBase;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


public class PickVerifyDocumentPreRules extends PromptBeforeValidationBase {

	/**
	 * @see org.kuali.rice.kns.rules.PromptBeforeValidationBase#doPrompts(org.kuali.rice.kns.document.Document)
	 */
	@Override
	public boolean doPrompts(Document document) {
		PickVerifyDocument verifyDocument = (PickVerifyDocument)document;

		if(ObjectUtils.isNotNull(verifyDocument.getPickTicket())
		        && (document.getDocumentHeader().getWorkflowDocument().isInitiated()
		                || document.getDocumentHeader().getWorkflowDocument().isSaved())) {

			if(hasQuantitySumMismatch(verifyDocument)) {
				if (!isOkHavingQuantitySumMismatch()) {
	                event.setActionForwardName(RiceConstants.MAPPING_BASIC);
	                return false;
	            }
			}
			if(hasBackOrderWithQuantityOnHand(verifyDocument)) {
				if(!isOkBackOrderWithQuantityOnHand((PickVerifyDocument) document)) {
					event.setActionForwardName(RiceConstants.MAPPING_BASIC);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @param document - PickVerifyDocument
	 * @return True if the BackOrder Quantity + PickStock Quantity is less than the Quantity Ordered
	 */
	public boolean hasQuantitySumMismatch(PickVerifyDocument document) {
        for(PickListLine line : document.getPickTicket().getPickListLines()) {
        	if(line.getBackOrderQty() == null || line.getPickStockQty() == null || line.getStockQty() == null)
        		return false;
        	if((line.getBackOrderQty() + line.getTotalPickStockQty()) < line.getStockQty()) {
        		return true;
        	}
        }

        return false;
	}

	/**
	 * @param document - PickVerifyDocument
	 * @return True if the the BackOrder Quantity is greater than zero when the Quantity On Hand is
	 * greater than the Quantity Picked.
	 */
	public boolean hasBackOrderWithQuantityOnHand(PickVerifyDocument document) {
        for(PickListLine line : document.getPickTicket().getPickListLines()) {
        	if(line.getBackOrderQty() == null)
        		return false;
        	if(line.getBackOrderQty() > 0 && getPickVerifyService().hasPickedLessThanQuantityOnHand(line))
        		return true;
        }

        return false;
	}

	/**
	 * @return True if the user wants to proceed with the Quantity sum is match
	 */
	private boolean isOkHavingQuantitySumMismatch() {
        ConfigurationService kualiConfiguration = SpringContext.getBean(ConfigurationService.class);
        String warningMessage = kualiConfiguration.getPropertyValueAsString(MMKeyConstants.PickVerifyDocument.SUM_QUANTITY_QUESTION);
        return super.askOrAnalyzeYesNoQuestion(MMConstants.PickVerifyDocument.SUM_QUANTITY_QUESTION, warningMessage);
	}

	/**
	 * @param document
	 * @return True if the user wants to proceed with BackOrders despite a positive quantity on hand
	 */
	private boolean isOkBackOrderWithQuantityOnHand(PickVerifyDocument document) {
		ConfigurationService kualiConfiguration = SpringContext.getBean(ConfigurationService.class);
		List<String> itemNumbers = new ArrayList<String>();
		for(PickListLine line : document.getPickTicket().getPickListLines()) {
			if(line.getBackOrderQty() > 0 && getPickVerifyService().hasPickedLessThanQuantityOnHand(line))
        		itemNumbers.add(line.getStock().getStockDistributorNbr());
		}
        String warningMessage = kualiConfiguration.getPropertyValueAsString(MMKeyConstants.PickVerifyDocument.QTY_ON_HAND_QUESTION);
        warningMessage = warningMessage.replace("{0}", itemNumbers.toString());
        return super.askOrAnalyzeYesNoQuestion(MMConstants.PickVerifyDocument.QTY_ON_HAND_QUESTION, warningMessage);
	}

	/**
	 * @return PickVerifyService
	 */
	private PickVerifyService getPickVerifyService() {
		return SpringContext.getBean(PickVerifyService.class);
	}
}
