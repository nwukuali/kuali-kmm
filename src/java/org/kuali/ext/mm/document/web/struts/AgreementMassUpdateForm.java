package org.kuali.ext.mm.document.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;
import org.kuali.ext.mm.document.MassUpdateDocument;
import org.kuali.ext.mm.sys.valueobject.MassUpdateUploadSummary;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

/**
 * @author schneppd
 */
public class AgreementMassUpdateForm extends KualiTransactionalDocumentFormBase {

    private FormFile file;
    
    private MassUpdateUploadSummary uploadSummary;    

    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
        
    }
    
    public MassUpdateDocument getMassUpdateDocument() {
        return (MassUpdateDocument)getDocument();
    }
    
    public void setFile(FormFile file) {
        this.file = file;
    }

    public FormFile getFile() {
        return file;
    }
    
    public void reset() {
        file = null;
    }

    public void setUploadSummary(MassUpdateUploadSummary uploadSummary) {
        this.uploadSummary = uploadSummary;
    }

    public MassUpdateUploadSummary getUploadSummary() {
        return uploadSummary;
    }
    
}
