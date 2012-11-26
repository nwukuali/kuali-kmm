package org.kuali.ext.mm.document.web.struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.CatalogPending;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;
import org.kuali.rice.krad.service.BusinessObjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author rshrivas
 *
 */
public class UploadCatalogAction extends KualiTransactionalDocumentActionBase {
    @Override
    @SuppressWarnings("unused")
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

       UploadCatalogForm uCForm = (UploadCatalogForm) form;
       CatalogPending catalogPending = (CatalogPending)uCForm.getDocument();
       BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
       if(catalogPending!=null){
           FormFile uploadedFile = catalogPending.getFileContent();

           if(uploadedFile!=null){
               catalogPending.setBatchLog(uploadedFile.getFileName());
           }

           java.util.Date today = new java.util.Date();
           catalogPending.setCatalogTimestamp(new java.sql.Timestamp(today.getTime()));
       }

       return super.execute(mapping, form, request, response);
    }
}
