/**
 *
 */
package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.ShoppingFrontPage;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.ShoppingFrontPageService;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.bo.DocumentHeader;

//import org.kuali.rice.krad.bo.DocumentHeader;

/**
 * @author schneppd
 *
 */
public class ShoppingFrontPageMaintainableImpl extends KualiMaintainableImpl {

    /**
     * 
     */
    private static final long serialVersionUID = 4487434465183875338L;

    @Override
    public void doRouteStatusChange(DocumentHeader documentHeader) {
        WorkflowDocument document = documentHeader.getWorkflowDocument();
        ShoppingFrontPage frontPage = (ShoppingFrontPage)this.getBusinessObject();
        if(document.isProcessed()) {
            if(frontPage.isCurrent()) {
                SpringContext.getBean(ShoppingFrontPageService.class).setAsCurrentFrontPage(frontPage);
            }
        }
        
    }
}
