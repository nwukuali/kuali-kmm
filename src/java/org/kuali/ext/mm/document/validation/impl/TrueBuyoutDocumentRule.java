package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.MMPersistableBusinessObjectBase;
import org.kuali.ext.mm.businessobject.Markup;
import org.kuali.ext.mm.businessobject.RentalObjectCode;
import org.kuali.ext.mm.businessobject.StockType;
import org.kuali.ext.mm.businessobject.TrueBuyoutDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.document.TrueBuyoutDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.StockService;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rule.event.ApproveDocumentEvent;
import org.kuali.rice.kns.rules.DocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.util.RiceKeyConstants;


public class TrueBuyoutDocumentRule extends DocumentRuleBase {

    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean processCustomSaveDocumentBusinessRules(Document document) {
        boolean valid = super.processCustomSaveDocumentBusinessRules(document);
        
        return valid;
    }

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(Document document) {
        boolean valid = super.processCustomRouteDocumentBusinessRules(document);


        return valid;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomApproveDocumentBusinessRules(org.kuali.rice.kns.rule.event.ApproveDocumentEvent)
     */
    @Override
    protected boolean processCustomApproveDocumentBusinessRules(ApproveDocumentEvent approveEvent) {
        boolean valid = super.processCustomApproveDocumentBusinessRules(approveEvent);
        TrueBuyoutDocument document = (TrueBuyoutDocument)approveEvent.getDocument();
        StockService stockService = MMServiceLocator.getStockService();
        
        
        int i = 0;
        String index = "";
        String detailErrorKey = "";
        String errorLabel = "";
        for(TrueBuyoutDetail detail : document.getTrueBuyoutDetails()) {
            index = "[" + i++ + "]";
            detailErrorKey = MMConstants.DOCUMENT + "." + MMConstants.TrueBuyoutDocument.TRUE_BUYOUT_DETAILS + index;
            if(stockService.getStockByDistributorNumber(detail.getStockDistributorNumber()) != null) {
                GlobalVariables.getMessageMap().putError(
                        detailErrorKey + "." + MMConstants.TrueBuyoutDetail.STOCK_DISTRIBUTOR_NUMBER,
                        MMKeyConstants.TrueBuyoutDocument.ERROR_STOCK_ALREADY_EXISTS,
                        detail.getStockDistributorNumber());
                valid = false;
            }
            
            if(StringUtils.isBlank(detail.getStockTypeCode())) {
                errorLabel = getDataDictionaryService().getAttributeErrorLabel(TrueBuyoutDetail.class, MMConstants.TrueBuyoutDetail.STOCK_TYPE_CODE);
                GlobalVariables.getMessageMap().putError(detailErrorKey + "." + MMConstants.TrueBuyoutDetail.STOCK_TYPE_CODE, RiceKeyConstants.ERROR_REQUIRED, errorLabel);
                valid = false;
            }            
            else {
                valid = validateReferenceObject(StockType.class, detail.getStockTypeCode(), 
                        detailErrorKey + "." + MMConstants.TrueBuyoutDetail.STOCK_TYPE_CODE, 
                        MMKeyConstants.TrueBuyoutDocument.ERROR_STOCK_TYPE_INVALID);
                if(MMConstants.StockType.RENTAL.equals(detail.getStockTypeCode())) {
                    if(StringUtils.isBlank(detail.getRentalObjectCode())) {
                        errorLabel = getDataDictionaryService().getAttributeErrorLabel(TrueBuyoutDetail.class, MMConstants.TrueBuyoutDetail.RENTAL_OBJECT_CODE);
                        GlobalVariables.getMessageMap().putError(detailErrorKey + "." + MMConstants.TrueBuyoutDetail.RENTAL_OBJECT_CODE, RiceKeyConstants.ERROR_REQUIRED, errorLabel);
                        valid = false;
                    }
                    else {
                        valid = validateReferenceObject(RentalObjectCode.class, detail.getRentalObjectCode(), 
                                detailErrorKey + "." + MMConstants.TrueBuyoutDetail.RENTAL_OBJECT_CODE, 
                                MMKeyConstants.TrueBuyoutDocument.ERROR_RENTAL_OBJECT_CODE_INVALID);
                    }
                }
                else {
                    if(StringUtils.isNotBlank(detail.getRentalObjectCode())) {
                        GlobalVariables.getMessageMap().putError(
                                detailErrorKey + "." + MMConstants.TrueBuyoutDetail.RENTAL_OBJECT_CODE, 
                                MMKeyConstants.TrueBuyoutDocument.ERROR_WRONG_STOCK_TYPE_FOR_RENTAL_OBJECT, 
                                detail.getStockTypeCode());
                        valid = false;
                    }
                }
            }
            
            if(detail.getOrderItemCost() == null) {
                errorLabel = getDataDictionaryService().getAttributeErrorLabel(TrueBuyoutDetail.class, MMConstants.TrueBuyoutDetail.ORDER_ITEM_COST);
                GlobalVariables.getMessageMap().putError(detailErrorKey + "." + MMConstants.TrueBuyoutDetail.ORDER_ITEM_COST, RiceKeyConstants.ERROR_REQUIRED, errorLabel);
                valid = false;
            }            
            
            if(StringUtils.isBlank(detail.getMarkupCode())) {
                errorLabel = getDataDictionaryService().getAttributeErrorLabel(TrueBuyoutDetail.class, MMConstants.TrueBuyoutDetail.MARKUP_CODE);
                GlobalVariables.getMessageMap().putError(detailErrorKey + "." + MMConstants.TrueBuyoutDetail.MARKUP_CODE, RiceKeyConstants.ERROR_REQUIRED, errorLabel);
                valid = false;
            }
            else {
                valid = validateReferenceObject(Markup.class, detail.getMarkupCode(), 
                        detailErrorKey + "." + MMConstants.TrueBuyoutDetail.MARKUP_CODE, 
                        MMKeyConstants.TrueBuyoutDocument.ERROR_MARKUP_CODE_INVALID);
            }
            detail.refreshReferenceObject(MMConstants.TrueBuyoutDetail.CATALOG);
            if(ObjectUtils.isNotNull(detail.getCatalog()) 
                    && !MMConstants.CatalogType.TRUE_BUYOUT.equals(detail.getCatalog().getCatalogTypeCd())) {
                GlobalVariables.getMessageMap().putError(
                        detailErrorKey + "." + MMConstants.TrueBuyoutDetail.CATALOG + "." + MMConstants.Catalog.CATALOG_CD,
                        MMKeyConstants.TrueBuyoutDocument.ERROR_CATALOG_NOT_TRUE_BUYOUT,
                        detail.getCatalog().getCatalogCd());
                valid = false;
            }
            if(StringUtils.isNotBlank(detail.getAgreementNumber())
                    && !MMServiceLocator.getStockService().isAgreementNumberValid(detail.getAgreementNumber())) {
                GlobalVariables.getMessageMap().putError(
                        detailErrorKey + "." + MMConstants.TrueBuyoutDetail.AGREEMENT_NUMBER,
                        MMKeyConstants.TrueBuyoutDocument.ERROR_AGREEMENT_NUMBER_INVALID,
                        detail.getAgreementNumber());
                valid = false;
            }
                
            
        }

        return valid;
    }

    
    private boolean validateReferenceObject(Class clazz, String primaryKeyValue, String errorPath, String errorKey) {
        MMPersistableBusinessObjectBase mmOject = (MMPersistableBusinessObjectBase)KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(clazz, primaryKeyValue);
        if(mmOject == null || !mmOject.isActive()) {
            GlobalVariables.getMessageMap().putError(
                    errorPath,
                    errorKey,
                    primaryKeyValue);
            return false;
        }
        return true;
    }
}
