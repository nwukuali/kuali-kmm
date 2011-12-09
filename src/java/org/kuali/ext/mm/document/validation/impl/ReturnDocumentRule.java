package org.kuali.ext.mm.document.validation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.businessobject.StagingRental;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.document.service.BusinessObjectLockingService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.OrderService;
import org.kuali.ext.mm.service.RentalService;
import org.kuali.ext.mm.service.ReturnOrderService;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rule.event.ApproveDocumentEvent;
import org.kuali.rice.kns.rules.DocumentRuleBase;
import org.kuali.rice.kns.service.DataDictionaryService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.RiceKeyConstants;
import org.kuali.rice.kns.util.UrlFactory;


public class ReturnDocumentRule extends DocumentRuleBase {
    
    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    public boolean processCustomSaveDocumentBusinessRules(Document document) {
        boolean isValid = true;
        BusinessObjectLockingService lockingService = SpringContext.getBean(BusinessObjectLockingService.class);
        List<String> lockingDocIds = new ArrayList<String>();
        for(ReturnDetail detail : ((ReturnDocument)document).getReturnDetails()) {
            detail.refreshReferenceObject(MMConstants.ReturnDetail.CATALOG_ITEM);
            detail.getCatalogItem().refreshReferenceObject(MMConstants.CatalogItem.STOCK);
            lockingDocIds.addAll(lockingService.getLockingDocumentIds(detail.getCatalogItem().getStock(), MMConstants.Stock.STOCK_ID, ""));
        }
        if(!lockingDocIds.isEmpty()) {
            Properties parameters = new Properties();
            parameters.put(KNSConstants.PARAMETER_DOC_ID, lockingDocIds.get(0));
            parameters.put(KNSConstants.PARAMETER_COMMAND, KNSConstants.METHOD_DISPLAY_DOC_SEARCH_VIEW);
            String blockingUrl = UrlFactory.parameterizeUrl(getKualiConfigurationService().getPropertyString(KNSConstants.WORKFLOW_URL_KEY) + "/" + KNSConstants.DOC_HANDLER_ACTION, parameters);
            GlobalVariables.getMessageMap().putError(KNSConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_MAINTENANCE_LOCKED, blockingUrl, lockingDocIds.get(0));
            isValid = false;
        }
        
        return isValid;
    }
    
    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomApproveDocumentBusinessRules(org.kuali.rice.kns.rule.event.ApproveDocumentEvent)
     */
    @Override
    protected boolean processCustomApproveDocumentBusinessRules(ApproveDocumentEvent approveEvent) {
        boolean isValid = true;
        
        ReturnDocument document = (ReturnDocument)approveEvent.getDocument();
        DataDictionaryService dictionaryService = KNSServiceLocator.getDataDictionaryService();
        
        int i=0;
        for(ReturnDetail detail : document.getReturnDetails()) {
            String errorKey = MMConstants.ReturnDocument.RETURN_DETAILS + "[" + i + "]";
            if(StringUtils.isBlank(detail.getDispostitionCode())) {
                String errorLabel = dictionaryService.getAttributeErrorLabel(ReturnDetail.class, 
                        MMConstants.ReturnDetail.DISPOSITION_CODE);
                GlobalVariables.getMessageMap().putError(
                        errorKey + "." + MMConstants.ReturnDetail.DISPOSITION_CODE,
                        RiceKeyConstants.ERROR_REQUIRED,
                        errorLabel);
                isValid = false;
            }
            if(detail.isDeptCreditRequired()) {
                isValid &= validateYesNoField(detail.getDepartmentCreditStringInd(), 
                        MMConstants.ReturnDetail.DEPARTMENT_CREDIT_STRING_IND, errorKey);
            }
            isValid &= validateReturnDetails(document);
            
//            if(!detail.isCustomerLine()) {
//                isValid &= validateYesNoField(detail.isVendorCreditInd(), 
//                        MMConstants.ReturnDetail.VENDOR_CREDIT_IND, errorKey);
//                isValid &= validateYesNoField(detail.isVendorReshipInd(), 
//                        MMConstants.ReturnDetail.VENDOR_RESHIP_IND, errorKey);        
//            } 
//            isValid &= validateYesNoField(detail.isVendorDispositionInd(), 
//                    MMConstants.ReturnDetail.VENDOR_DISPOSITION_IND, errorKey);           
            
            i++;
        }
        
        return isValid;
    }
    
    private boolean validateYesNoField(String fieldValue, String attribute, String errorPath) {
        DataDictionaryService dictionaryService = KNSServiceLocator.getDataDictionaryService();
        if(StringUtils.isBlank(fieldValue)) {
            String errorLabel = dictionaryService.getAttributeErrorLabel(ReturnDetail.class, attribute);
            GlobalVariables.getMessageMap().putError(
                    errorPath + "." + attribute,
                    RiceKeyConstants.ERROR_REQUIRED,
                    errorLabel);
            return false;
        }
        return true;
    }
    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomRouteDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(Document document) {
        ReturnDocument returnDocument = (ReturnDocument) document;
        if (!MMUtil.isCollectionEmpty(returnDocument.getNewReturnDetails())) {
            returnDocument.getReturnDetails().addAll(returnDocument.getNewReturnDetails());
            returnDocument.getNewReturnDetails().clear();
        }
        List<ReturnDetail> rdetails = returnDocument.getReturnDetails();
        int count = 0;

        for (ReturnDetail rdetail : rdetails) {
            if (rdetail.isItemReturned()) {
                count++;
            }
        }

        if (count < 1
                && (document.getDocumentHeader().getWorkflowDocument().stateIsInitiated() || document
                        .getDocumentHeader().getWorkflowDocument().stateIsSaved())) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.ReturnDocument.RETURN_DETAILS + "[" + 0 + "]."
                            + MMConstants.ReturnDocument.RETURN_DETAIL_LINE_SELECTED,
                    MMKeyConstants.ReturnDocument.NO_RETURN_LINE_SELECTED);
            return false;
        }
        
        return validateReturnDetails(returnDocument);
    }
    
    private boolean validateReturnDetails(ReturnDocument returnDocument) {
        OrderService orderService = MMServiceLocator.getOrderService();
        ReturnOrderService returnOrderService = MMServiceLocator.getReturnOrderService();
        boolean isRouted = !(returnDocument.getDocumentHeader().getWorkflowDocument().stateIsInitiated()
                || returnDocument.getDocumentHeader().getWorkflowDocument().stateIsSaved());
        List<ReturnDetail> rdetails = returnDocument.getReturnDetails();
        List<String> serialNumberList = getAllRentalSerialNumbers(rdetails);
        
        boolean isValid = true;
        int index = 0;
        for (ReturnDetail rdetail : rdetails) {
            if (!rdetail.isItemReturned() && !isRouted ) {
                index++;
                continue;
            }
            String dispString = "[" + index + "].";
            Integer returnQuantity = rdetail.getReturnQuantity() != null ? rdetail
                    .getReturnQuantity() : 0;
            if (returnQuantity == null || returnQuantity < 1) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.ReturnDocument.RETURN_DETAILS + dispString
                                + MMConstants.ReturnDocument.RETURN_QUANTITY,
                        MMKeyConstants.ReturnDocument.EMPTY_RETURN_ITEM_QUANTITY);
                isValid = false;
            }
            
            Integer returnableQuantity = orderService.getOrderDetailQuantityShipped(rdetail.getOrderDetail())
                        - returnOrderService.getQuantityAlreadyReturned(rdetail);
                
            if (returnQuantity > returnableQuantity) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.ReturnDocument.RETURN_DETAILS + dispString
                                + MMConstants.ReturnDocument.RETURN_QUANTITY,
                        MMKeyConstants.ReturnDocument.QUANTITY_GREATER_THAN_BALANCE_QUANITTY,
                        String.valueOf(returnQuantity), String.valueOf(returnableQuantity));
                isValid = false;

            }

            if (StringUtils.isEmpty(rdetail.getReturnDetailStatusCode())) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.ReturnDocument.RETURN_DETAILS + dispString
                                + MMConstants.ReturnDocument.RETURN_DETAIL_STATUS_CODE,
                        MMKeyConstants.ReturnDocument.EMPTY_RETURN_DETAIL_STATUS_CODE);
                isValid = false;
            }

            String unitOfIssueCode = rdetail.getReturnUnitOfIssueOfCode();

            if (StringUtils.isEmpty(unitOfIssueCode)) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.ReturnDocument.RETURN_DETAILS + dispString
                                + MMConstants.ReturnDocument.RETURN_UNIT_OF_ISSUE_CODE,
                        MMKeyConstants.ReturnDocument.EMPTY_RETURN_UNIT_ISSUE_OF_CODE);
                isValid = false;
            }

            if (rdetail.getCatalogItem() != null && rdetail.getCatalogItem().getStock() != null
                    && rdetail.getCatalogItem().getStock().isRental()) {
                isValid = validateRentals(rdetail.getReturnRentals(), rdetail, dispString, serialNumberList);                
            }
            index++;
        }
        return isValid;
    }

    /**
     * @param returnRentals
     * @param rdetail
     * @return
     */
    private boolean validateRentals(List<StagingRental> rentals, ReturnDetail rdetail, String dispString, List<String> serialNumberList) {
        boolean isValid = true;
        if (MMUtil.isCollectionEmpty(rentals) || rentals.size() != rdetail.getReturnQuantity()) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.RentalTrackingDocument.SELECTED_ITEMS + dispString,
                    MMKeyConstants.CheckinDoc.RENTAL_COUNT_INVALID,
                    new String[] { String.valueOf(rdetail.getReturnQuantity()) });
            isValid = false;
            rdetail.setRentalError(true);
        }
        else {
            RentalService rentalService = MMServiceLocator.getRentalService();
            
            List<Rental> validRentals = rentalService.getRentalsForOrderDetail(rdetail.getOrderDetail());
            
            boolean found;
            for (StagingRental rental : rentals) {
                found = false;
                for(Rental validRental : validRentals) {
                    if(rental.getSerialNumber().equals(validRental.getRentalSerialNumber())) {
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    GlobalVariables.getMessageMap().putError(
                            MMConstants.RentalTrackingDocument.SELECTED_ITEMS + dispString,
                            MMKeyConstants.ReturnDocument.RENTAL_SERIAL_NUMBER_NOT_VALID_FOR_RETURN,
                            rental.getSerialNumber());
                    isValid = false;
                    rdetail.setRentalError(true);
                }
                String serialNumber = rental.getSerialNumber();
                //Serial number should not appear in this document twice
                if (serialNumberList.indexOf(serialNumber) != serialNumberList.lastIndexOf(serialNumber)) {
                    GlobalVariables.getMessageMap().putError(
                        MMConstants.RentalTrackingDocument.SELECTED_ITEMS + dispString,
                        MMKeyConstants.CheckinDoc.RENTAL_SERIAL_NUMBER_ALREADY_EXISTS_IN_DOCUMENT,
                        serialNumber);
                    isValid = false;
                    rdetail.setRentalError(true);
                }
            }                   
        }
        return isValid;
    }
    
    /**
     * @param rdetails
     * @return
     */
    private List<String> getAllRentalSerialNumbers(List<ReturnDetail> rdetails) {
        List<String> serialNumbers = new ArrayList<String>();
        for(ReturnDetail rdetail : rdetails) {
            if(!MMUtil.isCollectionEmpty(rdetail.getReturnRentals())) {
                for(StagingRental srental : rdetail.getReturnRentals()) {
                    serialNumbers.add(srental.getSerialNumber());
                }
            }
        }
        return serialNumbers;
    }

}
