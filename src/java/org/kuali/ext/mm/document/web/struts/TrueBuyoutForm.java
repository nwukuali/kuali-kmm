/**
 * 
 */
package org.kuali.ext.mm.document.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.document.TrueBuyoutDocument;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

/**
 * @author schneppd
 */
public class TrueBuyoutForm extends KualiTransactionalDocumentFormBase {

    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);

        
    }
    
    
    public TrueBuyoutDocument getTrueBuyoutDocument() {
        return (TrueBuyoutDocument) getDocument();
    }

}
