package org.kuali.ext.mm.document.authorization;

import org.kuali.ext.mm.businessobject.Markup;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.document.MaintenanceDocumentBase;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationControllerBase;
import org.kuali.rice.krad.bo.BusinessObject;

import java.util.HashSet;
import java.util.Set;


public class MarkupPresentationController extends MaintenanceDocumentPresentationControllerBase {
    
    @Override
    public Set<String> getConditionallyHiddenPropertyNames(BusinessObject businessObject) {
        HashSet<String> hiddenProperties =  new HashSet<String>();
        Markup markup = (Markup)((MaintenanceDocumentBase)businessObject).getNewMaintainableObject().getBusinessObject();
        //Establish full set of conditionally hidden properties
        hiddenProperties.add(MMConstants.Markup.MARKUP_COA_CD);
        hiddenProperties.add(MMConstants.Markup.ORG_CODE);
        hiddenProperties.add(MMConstants.Markup.ACCOUNT_NUMBER);
        hiddenProperties.add(MMConstants.Markup.CATALOG_GROUP_CD);
        hiddenProperties.add(MMConstants.Markup.CATALOG_SUBGROUP_CD);
        
        if(markup.getMarkupTypeCd() != null) {
            //Remove the properties from set that are desired on the screen
            if(MMConstants.MarkupType.ACCOUNT_MARKUP.equals(markup.getMarkupTypeCd())
                    || MMConstants.MarkupType.ACCOUNT_CATALOG_ITEM_MARKUP.equals(markup.getMarkupTypeCd())
                    || MMConstants.MarkupType.PASS_THROUGH.equals(markup.getMarkupTypeCd())) {
                hiddenProperties.remove(MMConstants.Markup.MARKUP_COA_CD);
                hiddenProperties.remove(MMConstants.Markup.ORG_CODE);
                hiddenProperties.remove(MMConstants.Markup.ACCOUNT_NUMBER);
            }
            else if(MMConstants.MarkupType.ORGANIZATION_MARKUP.equals(markup.getMarkupTypeCd())) {
                hiddenProperties.remove(MMConstants.Markup.MARKUP_COA_CD);
                hiddenProperties.remove(MMConstants.Markup.ORG_CODE);
            }
            else if(MMConstants.MarkupType.ITEM_CATEGORY_MARKUP.equals(markup.getMarkupTypeCd())
                    || MMConstants.MarkupType.ITEM_CATEGORY_CATALOG_ITEM_MARKUP.equals(markup.getMarkupTypeCd())) {
                hiddenProperties.remove(MMConstants.Markup.CATALOG_GROUP_CD);
                hiddenProperties.remove(MMConstants.Markup.CATALOG_SUBGROUP_CD);
            }
        }
        
        
        return hiddenProperties;
    }
    
    

}