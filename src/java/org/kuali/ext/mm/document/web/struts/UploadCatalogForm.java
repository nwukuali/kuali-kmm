package org.kuali.ext.mm.document.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.document.CatalogPending;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

/**
 * @author rshrivas
 */
@SuppressWarnings("serial")
public class UploadCatalogForm extends KualiTransactionalDocumentFormBase {

    public UploadCatalogForm() {
        super();
    }

    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
    }

    public CatalogPending getCatalogPending() {
        return (CatalogPending) getDocument();
    }



    /**
     * @see org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase#customInitMaxUploadSizes()
     */
    @Override
    protected void customInitMaxUploadSizes() {
        super.customInitMaxUploadSizes();
        addMaxUploadSize("155M");
    }

}
