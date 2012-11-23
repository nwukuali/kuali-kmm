/**
 *
 */
package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.CatalogSubgroup;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.HashMap;
import java.util.List;


/**
 * @author rshrivas
 *
 */
public class CatalogSubgroupBusinessRule extends MaintenanceDocumentRuleBase {
    @SuppressWarnings("unchecked")
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {

        boolean subGroupCodeUnique = true;
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);

        Maintainable newMaintaibaleCatalogItemSubgroupObject = document.getNewMaintainableObject();
        CatalogSubgroup catalogSubgroupItem = (CatalogSubgroup) newMaintaibaleCatalogItemSubgroupObject.getBusinessObject();

        String catalogSubGroupCode = catalogSubgroupItem.getCatalogSubgroupCd();

        if(StringUtils.isBlank(catalogSubGroupCode)){
            GlobalVariables.getMessageMap().putErrorForSectionId(
                    MMConstants.CatalogSubgroupItem.CATALOG_SUBGROUP_SECTION,
                    MMKeyConstants.CatalogSubgroup.CATALOG_SUBGROUP_REQUIRED);
            subGroupCodeUnique = false;
        }else{
            HashMap fieldValues = new HashMap();
            fieldValues.put("catalogSubgroupCd", catalogSubGroupCode);
            List <CatalogSubgroup> sG = (List<CatalogSubgroup>) bOS.findMatching(CatalogSubgroup.class, fieldValues);

            if(!"Edit".equalsIgnoreCase(newMaintaibaleCatalogItemSubgroupObject.getMaintenanceAction())){
                if(!sG.isEmpty()){
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.CatalogSubgroupItem.CATALOG_SUBGROUP_SECTION,
                            MMKeyConstants.CatalogSubgroup.CATALOG_SUBGROUP_CD_ALREADY_PRESENT);
                    subGroupCodeUnique = false;
                }
            }


        }

        return subGroupCodeUnique;
    }
}
