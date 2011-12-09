/**
 * 
 */
package org.kuali.ext.mm.document.authorization;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.document.DiscrepancyDocument;
import org.kuali.ext.mm.document.DiscrepancyLine;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;

/**
 * @author rshrivas
 *
 */
public class DiscrepancyDocumentPresentationController extends TransactionalDocumentPresentationControllerBase{
    @Override
    protected boolean canSendAdhocRequests(Document document) {
        return false;
    }

    @Override
    protected boolean canSave(Document document){
        return false;
    }

    @Override
    protected boolean canBlanketApprove(Document document) {
        return false;
    }
    
    @Override
    protected boolean canReload(Document document) {        
        return false;
    }
   
    @Override
    protected boolean canCancel(Document document) {
        DiscrepancyDocument rDoc = (DiscrepancyDocument) document;        
        if(!StringUtils.isBlank(rDoc.getReportName()) && "PCard/Invoice - Transaction Discrepancies".equalsIgnoreCase(rDoc.getReportName())) {
            return true;
        }
        return false;
    }
    
    @Override
    protected boolean canRoute(Document document) {
        DiscrepancyDocument rDoc = (DiscrepancyDocument) document;        
        if(!StringUtils.isBlank(rDoc.getReportName()) && "PCard/Invoice - Transaction Discrepancies".equalsIgnoreCase(rDoc.getReportName())) {
            List<DiscrepancyLine> rLines = rDoc.getDiscrepancyLines();
            if(!rLines.isEmpty()){
                return true;    
            }            
        }
        return false;
    }
}
