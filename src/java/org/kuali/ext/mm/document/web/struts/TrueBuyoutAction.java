/**
 * 
 */
package org.kuali.ext.mm.document.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;

/**
 * @author schneppd
 */
public class TrueBuyoutAction extends KualiTransactionalDocumentActionBase {
    
    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward actionForward = super.docHandler(mapping, form, request, response);
        
        
        return actionForward;
    }
    
}
