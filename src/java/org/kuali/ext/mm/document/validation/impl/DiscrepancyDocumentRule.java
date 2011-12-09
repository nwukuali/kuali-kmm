/**
 * 
 */
package org.kuali.ext.mm.document.validation.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.document.DiscrepancyDocument;
import org.kuali.ext.mm.document.DiscrepancyLine;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rules.DocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;


/**
 * @author rshrivas
 *
 */
public class DiscrepancyDocumentRule extends DocumentRuleBase{
       
    protected boolean processCustomRouteDocumentBusinessRules(Document document) {
        DiscrepancyDocument rDocument = (DiscrepancyDocument) document;
      
        if(rDocument != null){
              List<DiscrepancyLine> rLines = rDocument.getDiscrepancyLines();
               for (Iterator iterator = rLines.iterator(); iterator.hasNext();) {
                   DiscrepancyLine rDiscrepancy = (DiscrepancyLine) iterator.next();
                   if(rDiscrepancy.isSelected()){
                       if(StringUtils.isBlank(rDiscrepancy.getDiscrepancyComment())){
                           GlobalVariables.getMessageMap().putErrorForSectionId(MMConstants.DiscrepancyDocument.SECTION_RECO_DISCREPANCY, MMKeyConstants.DiscrepancyDocument.ERROR_MISSING_COMMENT);
                           return false;
                       }
                   }            
               }
          }            
        return true;
    }
}
