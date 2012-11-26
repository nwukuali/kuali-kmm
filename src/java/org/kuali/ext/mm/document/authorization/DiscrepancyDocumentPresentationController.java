/**
 * 
 */
package org.kuali.ext.mm.document.authorization;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.document.DiscrepancyDocument;
import org.kuali.ext.mm.document.DiscrepancyLine;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.krad.document.Document;

import java.util.List;

/**
 * @author rshrivas
 *
 */
public class DiscrepancyDocumentPresentationController extends TransactionalDocumentPresentationControllerBase{
    @Override
    public boolean canSendAdhocRequests(Document document) {
        return false;
    }

    @Override
    public boolean canSave(Document document){
        return false;
    }

    @Override
    public boolean canBlanketApprove(Document document) {
        return false;
    }
    
    @Override
    public boolean canReload(Document document) {
        return false;
    }
   
    @Override
    public boolean canCancel(Document document) {
        DiscrepancyDocument rDoc = (DiscrepancyDocument) document;        
        if(!StringUtils.isBlank(rDoc.getReportName()) && "PCard/Invoice - Transaction Discrepancies".equalsIgnoreCase(rDoc.getReportName())) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean canRoute(Document document) {
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
