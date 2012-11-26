package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.ShoppingFrontPage;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.util.RiceConstants;
import org.kuali.rice.kns.document.MaintenanceDocumentBase;
import org.kuali.rice.kns.rules.PromptBeforeValidationBase;
import org.kuali.rice.krad.document.Document;

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
        ConfigurationService kualiConfiguration = SpringContext.getBean(ConfigurationService.class);
        String warningMessage = kualiConfiguration.getPropertyValueAsString(MMKeyConstants.ShoppingFrontPage.URL_PRECEDENCE_QUESTION);
        return super.askOrAnalyzeYesNoQuestion(MMKeyConstants.ShoppingFrontPage.URL_PRECEDENCE_QUESTION, warningMessage);
	}

}
