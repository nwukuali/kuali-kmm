package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.ShoppingFrontPage;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.core.util.RiceConstants;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.MaintenanceDocumentBase;
import org.kuali.rice.kns.rules.PromptBeforeValidationBase;
import org.kuali.rice.kns.service.KualiConfigurationService;

public class ShoppingFrontPagePreRules extends PromptBeforeValidationBase {

	/**
	 * @see org.kuali.rice.kns.rules.PromptBeforeValidationBase#doPrompts(org.kuali.rice.kns.document.Document)
	 */
	@Override
	public boolean doPrompts(Document document) {
	    MaintenanceDocumentBase maintDoc = (MaintenanceDocumentBase)document;
		ShoppingFrontPage newFrontPage = (ShoppingFrontPage)maintDoc.getNewMaintainableObject().getBusinessObject();
		
		if(StringUtils.isNotBlank(newFrontPage.getFrontPageHTML())
		        && StringUtils.isNotBlank(newFrontPage.getFrontPageURL())) {
			if (!isOkUrlPrecedence()) {
                event.setActionForwardName(RiceConstants.MAPPING_BASIC);
                return false;
            }

		}
		return true;
	}

	
    /**
	 * @return True if the user wants to proceed with canceling the backorder
	 */
	private boolean isOkUrlPrecedence() {
        KualiConfigurationService kualiConfiguration = SpringContext.getBean(KualiConfigurationService.class);
        String warningMessage = kualiConfiguration.getPropertyString(MMKeyConstants.ShoppingFrontPage.URL_PRECEDENCE_QUESTION);
        return super.askOrAnalyzeYesNoQuestion(MMKeyConstants.ShoppingFrontPage.URL_PRECEDENCE_QUESTION, warningMessage);
	}

}
