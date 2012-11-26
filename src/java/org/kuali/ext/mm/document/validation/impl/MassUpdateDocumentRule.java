package org.kuali.ext.mm.document.validation.impl;

import org.kuali.ext.mm.businessobject.MassUpdateDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.document.MassUpdateDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.DocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;


public class MassUpdateDocumentRule extends DocumentRuleBase {

    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomRouteDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(Document document) {
        boolean isValid = true;
        MassUpdateDocument updateDocument = (MassUpdateDocument) document;
        int lineCount = 0;
        StockService stockService = MMServiceLocator.getStockService();
//        if(!stockService.isAgreementNumberValid(updateDocument.getPreviousAgreementNumber())) {
//            String pAgreementLabel = getDataDictionaryService().getAttributeErrorLabel(MassUpdateDocument.class, MMConstants.MassUpdateDocument.PREVIOUS_AGREEMENT_NUMBER);
//            GlobalVariables.getMessageMap().putError(MMConstants.DOCUMENT + "." + MMConstants.MassUpdateDocument.PREVIOUS_AGREEMENT_NUMBER, 
//                    MMKeyConstants.AgreementMassMaintenance.ERROR_AGREEMENT_NBR_INVALID,
//                    pAgreementLabel,
//                    updateDocument.getPreviousAgreementNumber());
//            isValid = false;
//        }
        if(!stockService.isAgreementNumberValid(updateDocument.getNewAgreementNumber())) {
            String nAgreementLabel = getDataDictionaryService().getAttributeErrorLabel(MassUpdateDocument.class, MMConstants.MassUpdateDocument.NEW_AGREEMENT_NUMBER);
            GlobalVariables.getMessageMap().putError(MMConstants.DOCUMENT + "." + MMConstants.MassUpdateDocument.NEW_AGREEMENT_NUMBER,
                    MMKeyConstants.AgreementMassMaintenance.ERROR_AGREEMENT_NBR_INVALID,
                    nAgreementLabel,
                    updateDocument.getNewAgreementNumber());
            isValid = false;
        }            
        if(MMUtil.isCollectionEmpty(updateDocument.getMassUpdateDetails())) {
            GlobalVariables.getMessageMap().putError(MMConstants.MassUpdateDocument.FILE, 
                    MMKeyConstants.AgreementMassMaintenance.ERROR_NO_LINES_TO_UPLOAD);
            isValid = false;
        }
        for(MassUpdateDetail detail : updateDocument.getMassUpdateDetails()) {
            lineCount++;
            if(detail.getStockCost() != null && detail.getStockCost().isNegative()) {
                GlobalVariables.getMessageMap().putError(MMConstants.MassUpdateDocument.UPLOAD_SUMMARY, 
                        MMKeyConstants.AgreementMassMaintenance.ERROR_STOCK_COST_INVALID,
                        String.valueOf(lineCount)+1);
                isValid = false;
            }
            if(!updateDocument.getPreviousAgreementNumber().equals(detail.getStock().getAgreementNbr())) {
                GlobalVariables.getMessageMap().putError(MMConstants.MassUpdateDocument.UPLOAD_SUMMARY, 
                        MMKeyConstants.AgreementMassMaintenance.ERROR_AGREEMENT_NBR_MISMATCH,
                        updateDocument.getPreviousAgreementNumber(),
                        detail.getStock().getAgreementNbr(),
                        detail.getStock().getStockDistributorNbr());
                isValid = false;
            }
        }
        return isValid;
    }
    

}
