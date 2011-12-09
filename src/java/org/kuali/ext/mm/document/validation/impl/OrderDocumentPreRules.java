package org.kuali.ext.mm.document.validation.impl;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.rice.core.util.RiceConstants;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rules.PromptBeforeValidationBase;
import org.kuali.rice.kns.service.KualiConfigurationService;
import org.kuali.rice.kns.util.ObjectUtils;


public class OrderDocumentPreRules extends PromptBeforeValidationBase {

	/**
	 * @see org.kuali.rice.kns.rules.PromptBeforeValidationBase#doPrompts(org.kuali.rice.kns.document.Document)
	 */
	@Override
	public boolean doPrompts(Document document) {
		OrderDocument orderDocument = (OrderDocument)document;
		
		if(ObjectUtils.isNotNull(orderDocument.getRecurringOrder())) {
			if (!isOkHavingRecurringOrder()) {
                event.setActionForwardName(RiceConstants.MAPPING_BASIC);
                return false;
            }
		}
		return true;
	}
	
	/**
	 * @return True if the user wants to proceed with the Recurring Order
	 */
	private boolean isOkHavingRecurringOrder() {
        KualiConfigurationService kualiConfiguration = SpringContext.getBean(KualiConfigurationService.class);
        String warningMessage = kualiConfiguration.getPropertyString(MMKeyConstants.OrderDocument.RECURRING_ORDER_QUESTION);
        return super.askOrAnalyzeYesNoQuestion(MMConstants.OrderDocument.RECURRING_ORDER_QUESTION, warningMessage);
	}
}
