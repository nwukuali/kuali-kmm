package org.kuali.ext.mm.document.validation.impl;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Markup;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;


public class MarkupRule extends FinancialMaintenanceDocumentRuleBase {

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = super.processCustomRouteDocumentBusinessRules(document);
        valid &= SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .checkAndErrorSystemAvailability();
        Markup markup = (Markup) document.getNewMaintainableObject().getBusinessObject();
        // validate chart code
        valid &= validateChart(markup.getMarkupCoaCd(), KNSConstants.MAINTENANCE_NEW_MAINTAINABLE
                + "markupCoaCd", MMKeyConstants.Markup.CHART_NOT_VALID);
        // validate org code
        valid &= validateOrg(markup.getMarkupCoaCd(), markup.getMarkupOrg(),
                KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + "markupOrg",
                MMKeyConstants.Markup.ORG_NOT_VALID);
        // validate account number
        valid &= validateAccount(markup.getMarkupCoaCd(), markup.getMarkupAccountNbr(),
                KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + "markupAccountNbr",
                MMKeyConstants.Markup.ACCT_NOT_VALID);


        if(StringUtils.isNotBlank(markup.getCatalogGroupCode()) && markup.getCatalogSubgroup() != null 
                && StringUtils.isNotBlank(markup.getCatalogSubgroup().getCatalogSubgroupCd())) {
        	if(!StringUtils.equals(markup.getCatalogSubgroup().getCatalogGroupCd(), markup.getCatalogGroupCode())) {
        		GlobalVariables.getMessageMap().putError(
        				KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Markup.CATALOG_SUBGROUP_CD,
                        MMKeyConstants.Markup.ERROR_MARKUP_SUBGROUP_NOT_VALID,
                        markup.getCatalogSubgroup().getCatalogSubgroupCd(), markup.getCatalogGroupCode());
        		valid = false;
        	}
        }
        if(markup.getMarkupFixed() == null && markup.getMarkupRt() == null) {
        	GlobalVariables.getMessageMap().putError(
        			KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Markup.MARKUP_FIXED,
                       MMKeyConstants.Markup.ERROR_MARKUP_AMOUNT_BLANK);
       		valid = false;
        }
        else if(markup.getMarkupFixed() != null && markup.getMarkupRt() != null) {
        	GlobalVariables.getMessageMap().putError(
        			KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Markup.MARKUP_FIXED,
                      MMKeyConstants.Markup.ERROR_MARKUP_AMOUNT_CONFLICT);
      		valid = false;
        }
        if(markup.getMarkupFromQty() != null && markup.getMarkupToQty() != null) {
	        if(markup.getMarkupFromQty() > markup.getMarkupToQty()) {
	        	GlobalVariables.getMessageMap().putError(
	        			KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Markup.MARKUP_FROM_QTY,
	                     MMKeyConstants.Markup.ERROR_MARKUP_FROM_QTY_INVALID);
	     		valid = false;
	        }
        }
        if(markup.getMarkupBeginDt() != null && markup.getMarkupEndDt() != null) {
	        if(markup.getMarkupBeginDt().compareTo(markup.getMarkupEndDt()) > 0) {
	        	GlobalVariables.getMessageMap().putError(
	        			KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Markup.MARKUP_BEGIN_DT,
	                    MMKeyConstants.Markup.ERROR_MARKUP_BEGIN_DATE_INVALID);
	    		valid = false;
	        }
        }

        valid &= isValidDataProvidedByTypeCode(markup);

        return valid;
    }

	/**
	 * @param markup
	 * @return
	 */
	private boolean isValidDataProvidedByTypeCode(Markup markup) {
		boolean isValid = true;

		if(MMConstants.MarkupType.ACCOUNT_MARKUP.equals(markup.getMarkupTypeCd())
                || MMConstants.MarkupType.ACCOUNT_CATALOG_ITEM_MARKUP.equals(markup.getMarkupTypeCd())
                || MMConstants.MarkupType.PASS_THROUGH.equals(markup.getMarkupTypeCd())) {
		    String[] validateMarkupAsType = {MMConstants.MarkupType.ACCOUNT_MARKUP, 
		            MMConstants.MarkupType.ACCOUNT_CATALOG_ITEM_MARKUP, MMConstants.MarkupType.PASS_THROUGH};
			isValid &= validateField(markup.getMarkupCoaCd(), MMConstants.Markup.COA_CODE, validateMarkupAsType);
			isValid &= validateField(markup.getMarkupOrg(), MMConstants.Markup.ORG_CODE, validateMarkupAsType);
			isValid &= validateField(markup.getMarkupAccountNbr(), MMConstants.Markup.ACCOUNT_NUMBER, validateMarkupAsType);
		}
		else if(MMConstants.MarkupType.ORGANIZATION_MARKUP.equals(markup.getMarkupTypeCd())) {
		    String[] validateMarkupAsType = {MMConstants.MarkupType.ORGANIZATION_MARKUP};
			isValid &= validateField(markup.getMarkupCoaCd(), MMConstants.Markup.COA_CODE, validateMarkupAsType);
			isValid &= validateField(markup.getMarkupOrg(), MMConstants.Markup.ORG_CODE, validateMarkupAsType);
		}
		else if(MMConstants.MarkupType.ITEM_CATEGORY_MARKUP.equals(markup.getMarkupTypeCd())
                || MMConstants.MarkupType.ITEM_CATEGORY_CATALOG_ITEM_MARKUP.equals(markup.getMarkupTypeCd())) {
		    String[] validateMarkupAsType = {MMConstants.MarkupType.ITEM_CATEGORY_MARKUP, 
		            MMConstants.MarkupType.ITEM_CATEGORY_CATALOG_ITEM_MARKUP};
			isValid &= validateField(markup.getCatalogGroupCode(), MMConstants.Markup.CATALOG_GROUP_CD, validateMarkupAsType);
		}


		return isValid;
	}

	private boolean validateField(Object fieldValue, String fieldName, String[] markupTypeCode) {
		if(fieldValue == null || StringUtils.isBlank(fieldValue.toString())) {
			String errorLabel = KNSServiceLocator.getDataDictionaryService().getAttributeErrorLabel(Markup.class, fieldName);
			GlobalVariables.getMessageMap().putError(
        			KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + fieldName,
                    MMKeyConstants.Markup.ERROR_MARKUP_FIELD_REQUIRED,
                    errorLabel, Arrays.toString(markupTypeCode));
    		return false;
		}
		return true;
	}




}
