/**
 *
 */
package org.kuali.ext.mm.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.CatalogGroup;
import org.kuali.ext.mm.businessobject.CatalogSubgroup;
import org.kuali.ext.mm.businessobject.Markup;
import org.kuali.ext.mm.businessobject.MarkupType;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author schneppd
 *
 */
public class MarkupMaintainableImpl extends KualiMaintainableImpl {

	/**
     *
     */
    private static final long serialVersionUID = -6114159984639149392L;

    @Override
    public void prepareForSave() {
		super.prepareForSave();
		updateMarkupReference();
		updateCatalogGroupSubgroupReference();	
	}
    
    private void updateMarkupReference(){
        Markup markup = (Markup)getBusinessObject();        
        if(StringUtils.isNotBlank(markup.getMarkupType().getMarkupTypeCd()) && 
                markup.getMarkupType().getMarkupTypeCd() != null){
            markup.setMarkupTypeCd(markup.getMarkupType().getMarkupTypeCd());
            BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
            MarkupType markUpType = bOS.findBySinglePrimaryKey(MarkupType.class, markup.getMarkupType().getMarkupTypeCd());
            if(markUpType != null){
                markup.setMarkupType(markUpType);
            }
        }
    }
    
	private void updateCatalogGroupSubgroupReference() {
		Markup markup = (Markup)getBusinessObject();		

		if(StringUtils.isNotBlank(markup.getCatalogGroupCode())) {
		    BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
            markup.setCatalogGroup(bOS.findBySinglePrimaryKey(CatalogGroup.class, markup.getCatalogGroupCode()));
		}
		
		if(StringUtils.isNotBlank(markup.getCatalogGroupCode()) && markup.getCatalogSubgroup() != null) {
			CatalogSubgroup subgroupTemp = markup.getCatalogSubgroup();
			markup.setCatalogSubgroup(getCatalogSubgroup(markup.getCatalogGroupCode(), markup.getCatalogSubgroup().getCatalogSubgroupCd()));
			if(markup.getCatalogSubgroup() != null)
				markup.setCatalogSubgroupId(markup.getCatalogSubgroup().getCatalogSubgroupId());
			else
				markup.setCatalogSubgroup(subgroupTemp);
		}
	}

	/**
	 * @param catalogGroupCode
	 * @param catalogSubgroupCd
	 * @return
	 */
	private CatalogSubgroup getCatalogSubgroup(String catalogGroupCd, String catalogSubgroupCd) {
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		fieldValues.put(MMConstants.CatalogSubgroup.CATALOG_GROUP_CD, catalogGroupCd);
		fieldValues.put(MMConstants.CatalogSubgroup.CATALOG_SUBGROUP_CD, catalogSubgroupCd);
		fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");

		Collection results = KRADServiceLocator.getBusinessObjectService().findMatching(CatalogSubgroup.class, fieldValues);

		Iterator<CatalogSubgroup> subgroups = results.iterator();
		return (subgroups.hasNext() ? subgroups.next() : null);
	}
}
