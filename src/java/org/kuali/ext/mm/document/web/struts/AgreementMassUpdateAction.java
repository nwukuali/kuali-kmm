package org.kuali.ext.mm.document.web.struts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.MassUpdateService;
import org.kuali.ext.mm.sys.valueobject.MassUpdateUploadSummary;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;


/**
 * @author schneppd
 */
public class AgreementMassUpdateAction extends KualiTransactionalDocumentActionBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AgreementMassUpdateAction.class);
    
    public ActionForward loadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AgreementMassUpdateForm ammForm = (AgreementMassUpdateForm)form;
        boolean uploadSuccess = false;
        
        FormFile file = ammForm.getFile();        
        String fileName = file.getFileName();
        if(fileName == null || fileName.length() < 1) {
            GlobalVariables.getMessageMap().putError("file", 
                    MMKeyConstants.AgreementMassMaintenance.ERROR_NO_FILE_TO_UPLOAD);
        }
        else if(!fileName.toLowerCase().endsWith(".csv")) {
            GlobalVariables.getMessageMap().putError("file", 
                    MMKeyConstants.AgreementMassMaintenance.ERROR_FILE_TYPE_NOT_SUPPORTED);
        }
        else if(file.getFileSize() == 0) {
            GlobalVariables.getMessageMap().putError("file", 
                    MMKeyConstants.AgreementMassMaintenance.ERROR_FILE_BLANK_OR_WRONG_FORMAT);
        }
        else {        
            FileOutputStream fos = null;
            File temp = null;
            try{
                try {
                    temp = File.createTempFile("agreement", null);
                    fos = new FileOutputStream(temp);
                    fos.write(file.getFileData());
                } catch (IOException ioe) {
                    GlobalVariables.getMessageMap().putError("file", 
                            MMKeyConstants.AgreementMassMaintenance.ERROR_UPLOADING_FILE);
                } finally {
                    if (fos != null) try {
                        fos.close();
                    } catch (IOException ioe) {
                        LOG.error("Error closing temp file output stream: " + temp, ioe);
                    }
                }
                String previousAgreementNbr = ammForm.getMassUpdateDocument().getPreviousAgreementNumber();
                if(temp != null && StringUtils.isNotBlank(previousAgreementNbr)) {
                    MassUpdateService updateService = SpringContext.getBean(MassUpdateService.class);
                    MassUpdateUploadSummary uploadSummary = updateService.loadAgreementMassUpdateFile(temp, MMConstants.MassUpdateDocument.UPLOAD_SUMMARY);
                    ammForm.setUploadSummary(uploadSummary);
                    ammForm.getMassUpdateDocument().setUpdateFileName(file.getFileName());
                    uploadSuccess = true;
                    if(uploadSummary.getErrorCount() == 0) {
                        ammForm.getMassUpdateDocument().setMassUpdateDetails(uploadSummary.getMassUpdateDetails());
                    }
                }
            }finally{
                if(temp != null && temp.exists()){
                    if(!temp.delete()){
                        LOG.warn("File could not be deleted.");
                    }
                }
            }
        }       
        if(!uploadSuccess) {
            ammForm.setUploadSummary(null);
        }
        
        return mapping.findForward(MMConstants.MAPPING_BASIC); 
    }
    
    public ActionForward clear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AgreementMassUpdateForm ammForm = (AgreementMassUpdateForm)form;
        ammForm.setUploadSummary(null);
        ammForm.setFile(null);
        ammForm.getMassUpdateDocument().setPreviousAgreement(null);
        ammForm.getMassUpdateDocument().setPreviousAgreementNumber(null);
        ammForm.getMassUpdateDocument().setNewAgreement(null);
        ammForm.getMassUpdateDocument().setNewAgreementNumber(null);
        return mapping.findForward(MMConstants.MAPPING_BASIC); 
    }
}
