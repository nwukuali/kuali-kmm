package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.fp.service.FinancialDataService;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.service.FinancialCapitalAssetService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.OrderService;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.DocumentRuleBase;
import org.kuali.rice.krad.rules.rule.event.ApproveDocumentEvent;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.location.api.campus.Campus;
import org.kuali.rice.location.api.campus.CampusService;

import java.util.List;


public class OrderDocumentRule extends DocumentRuleBase {

    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean processCustomSaveDocumentBusinessRules(Document document) {
        boolean valid = super.processCustomSaveDocumentBusinessRules(document);
        OrderDocument orderDocument = (OrderDocument) document;
        List<Accounts> orderAccounts = orderDocument.getAccounts();
        OrderService orderService = SpringContext.getBean(OrderService.class);        
        if (orderAccounts != null) {
            int count = 0;
            for (Accounts account : orderAccounts) {
                valid &= orderService.validateAccountingLine(account,
                		MMConstants.DOCUMENT + ".accounts[" + count + "].");
                count++;
            }
        }
        List<OrderDetail> orderDetails = orderDocument.getOrderDetails();
        if (orderDetails != null) {
            int pos1 = 0;
            for (OrderDetail orderDetail : orderDetails) {
                List<Accounts> accounts = orderDetail.getAccounts();
                if (accounts != null) {
                    int pos2 = 0;
                    for (Accounts account : accounts) {
                        String errorPath = MMConstants.DOCUMENT + ".orderDetails[" + pos1 + "].accounts[" + pos2 + "].";
                        valid &= orderService.validateAccountingLine(account,
                        		errorPath);                        
                        valid &= validateCatalogRestrictions(orderDetail.getCatalogItem(), account, errorPath);                        
                        pos2++;
                    }
                }
                pos1++;
            }
        }

        if(orderDocument.isRecurringOrderInd())
        	valid &= isValidRecurringOrderData(orderDocument.getRecurringOrder(), MMConstants.OrderDocument.RECURRING_ORDER);

        return valid;
    }

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(Document document) {
        OrderDocument orderDocument = (OrderDocument) document;

        if(MMConstants.OrderDocument.PROFILE_TYPE_PERSONAL.equals(orderDocument.getProfileTypeCode()))
        	return true;

        return validateOrderDocument(orderDocument);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomApproveDocumentBusinessRules(org.kuali.rice.krad.rules.rule.event.ApproveDocumentEvent)
     */
    @Override
    protected boolean processCustomApproveDocumentBusinessRules(ApproveDocumentEvent approveEvent) {
        OrderDocument orderDocument = (OrderDocument) approveEvent.getDocument();

        return validateOrderDocument(orderDocument);
    }

    protected boolean validateOrderDocument(OrderDocument orderDocument) {
        boolean isValid = true;

        OrderService orderService = SpringContext.getBean(OrderService.class);
        FinancialSystemAdaptorFactory adaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);

        isValid &= orderService.isValidAccountingLinesTotalAmount(orderDocument);
        if (!isValid) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.DOCUMENT + "." + MMConstants.OrderDocument.ACCOUNTS,
                    MMKeyConstants.Accounts.ERROR_ACCOUNTING_LINES_TOTAL,
                    String.valueOf(orderDocument.getDisplayTotal()));
        }

        int i = 0;
        for (OrderDetail orderDetail : orderService.getApprovedOrderDetails(orderDocument)) {
            String index = "[" + i++ + "]";
            String errorProperty = MMConstants.OrderDocument.ORDER_DETAILS + index;
            
            if(!orderService.isValidDetailLevelAccountingLinesAmount(orderDetail)) {
                GlobalVariables.getMessageMap().putError(
                        errorProperty + ".accounts",
                        MMKeyConstants.Accounts.ERROR_DETAIL_ACCOUNTING_LINES_TOTAL,
                        String.valueOf(orderDetail.getDetailTotal()), 
                        String.valueOf(orderService.getOrderDetailAccountsTotalAmount(orderDetail)));
                isValid = false;
            }
            if (adaptorFactory.checkAndErrorSystemAvailability()) {
            	Integer fiscalYear = adaptorFactory.getFinancialUniversityDateService()
                .getCurrentFiscalYear();
            	MMCapitalAssetInformation assetInfo = orderDetail.getCapitalAssetInformation();
            	List<Accounts> accounts = orderDetail.getAccounts();
                if (accounts != null) {
                    for (Accounts account : accounts) {
                    	if (adaptorFactory.getFinancialCapitalAssetService().isCapitalAssetObjectSubType(
                    			fiscalYear, account.getFinCoaCd(), account.getFinObjectCd())) {
                            isValid &= isValidCapitalAssetInfo(assetInfo, orderDetail, errorProperty);
                        }
                    	else if(!assetInfo.isEmpty()) {
                			 GlobalVariables.getMessageMap().putError(
                					 errorProperty + "." + MMConstants.OrderDetail.CAPITAL_ASSET_INFO + "." + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_NUMBER,
                                     MMKeyConstants.CapitalAsset.ERROR_ASSET_DATA_NOT_REQUIRED,
                                     account.getFinObjectCd());
                             isValid = false;
                    	}
                    }
                }

            }
        }

        return isValid;
    }
    
    /**
     * @param catalogItem
     * @param account
     * @return
     */
    private boolean validateCatalogRestrictions(CatalogItem catalogItem, Accounts account, String errorPath) {
        boolean isAuthorized = true;
        
        if(!MMServiceLocator.getCatalogService().isCatalogItemAuthorized(catalogItem, account.getFinCoaCd(), account.getAccountNbr(), "")) {
            GlobalVariables.getMessageMap().putError(
                    errorPath + MMConstants.Accounts.ACCOUNT_NUMBER,
                    MMKeyConstants.Accounts.ERROR_ACCOUNTING_LINE_NOT_AUTHORIZED_FOR_ITEM,
                    account.getFinCoaCd(),
                    account.getAccountNbr(),
                    catalogItem.getDistributorNbr());
            isAuthorized = false;
        }
        
        return isAuthorized;
    }

    private boolean isValidCapitalAssetInfo(MMCapitalAssetInformation assetInfo, OrderDetail orderDetail, String errorProperty) {
    	boolean isValid = true;
    	errorProperty = errorProperty + "." + MMConstants.OrderDetail.CAPITAL_ASSET_INFO;
    	FinancialSystemAdaptorFactory adaptorFactory = SpringContext
        .getBean(FinancialSystemAdaptorFactory.class);
    	FinancialCapitalAssetService fcaService = adaptorFactory.getFinancialCapitalAssetService();
    	FinancialDataService finDataService = SpringContext.getBean(FinancialDataService.class);
    	if (assetInfo.getCapitalAssetNumber() != null) {
            if (!fcaService.isValidActiveAsset(assetInfo.getCapitalAssetNumber())) {
                GlobalVariables.getMessageMap().putError(
                        errorProperty + "." + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_NUMBER,
                        MMKeyConstants.CapitalAsset.ERROR_ASSET_NUMBER,
                        String.valueOf(assetInfo.getCapitalAssetNumber()));
                isValid = false;
            }
        }
    	else if(assetInfo.isEmpty()) {
    		 GlobalVariables.getMessageMap().putError(
                     errorProperty + "." + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_NUMBER,
                     MMKeyConstants.CapitalAsset.ERROR_ASSET_DATA_REQUIRED,
                     orderDetail.getDistributorNbr());
             isValid = false;
    	}
        else {
        	if(assetInfo.getCapitalAssetQuantity() == null) {
        		GlobalVariables.getMessageMap().putError(
                        errorProperty + "." + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_QUANTITY,
                        MMKeyConstants.CapitalAsset.ERROR_ASSET_QUANTITY_REQUIRED);
                isValid = false;
        	}
        	else if(assetInfo.getCapitalAssetQuantity() < 1) {
        		GlobalVariables.getMessageMap().putError(
                        errorProperty + "." + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_QUANTITY,
                        MMKeyConstants.CapitalAsset.ERROR_ASSET_QUANTITY_NON_ZERO);
                isValid = false;
        	}
        	else if(ObjectUtils.isNull(assetInfo.getCapitalAssetInformationDetails()) || assetInfo.getCapitalAssetQuantity() > assetInfo.getCapitalAssetInformationDetails().size()) {
        		GlobalVariables.getMessageMap().putError(
                        errorProperty + "." + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_QUANTITY,
                        MMKeyConstants.CapitalAsset.ERROR_ASSET_QUANTITY_TAG_LINE_MISMATCH);
                isValid = false;
        	}
            String typeCode = assetInfo.getCapitalAssetTypeCode();
            if(StringUtils.isBlank(typeCode)) {
            	 GlobalVariables.getMessageMap().putError(
                         errorProperty + "." + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_TYPE_CODE,
                         MMKeyConstants.CapitalAsset.ERROR_ASSET_TYPECODE_REQUIRED);
                 isValid = false;
            }
            else if (!fcaService.isValidAssetType(typeCode)) {
                GlobalVariables.getMessageMap().putError(
                        errorProperty + "." + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_TYPE_CODE,
                        MMKeyConstants.CapitalAsset.ERROR_ASSET_TYPECODE, typeCode);
                isValid = false;
            }
            if(StringUtils.isBlank(assetInfo.getVendorName())) {
            	GlobalVariables.getMessageMap().putError(
                        errorProperty + "." + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_VENDOR_NAME,
                        MMKeyConstants.CapitalAsset.ERROR_ASSET_VENDOR_NAME_REQUIRED);
                isValid = false;
            }
            if(StringUtils.isBlank(assetInfo.getCapitalAssetManufacturerName())) {
            	GlobalVariables.getMessageMap().putError(
                        errorProperty + "." + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_MANUFACTUREER_NAME,
                        MMKeyConstants.CapitalAsset.ERROR_ASSET_MANUFACTURER_NAME_REQUIRED);
                isValid = false;
            }
            if(StringUtils.isBlank(assetInfo.getCapitalAssetDescription())) {
            	GlobalVariables.getMessageMap().putError(
                        errorProperty + "." + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_DESCRIPTION,
                        MMKeyConstants.CapitalAsset.ERROR_ASSET_DESCRIPTION_REQUIRED);
                isValid = false;
            }


            int j = 0;
            errorProperty = errorProperty + "."
                    + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_DETAIL;
            for (MMCapitalAssetInformationDetail infoDetail : assetInfo.getCapitalAssetInformationDetails()) {
                String infoDetailIndex = "[" + j++ + "]";
                if (fcaService.isTagAlreadyInUse(infoDetail.getCapitalAssetTagNumber())) {
                    GlobalVariables.getMessageMap().putError(
                            		errorProperty + infoDetailIndex + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_TAG_NUMBER,
                                    MMKeyConstants.CapitalAsset.ERROR_ASSET_TAG_NUMBER,
                                    infoDetail.getCapitalAssetTagNumber());
                    isValid = false;
                }
                if(StringUtils.isBlank(infoDetail.getCampusCode())) {
                	GlobalVariables.getMessageMap().putError(
                    		errorProperty + infoDetailIndex + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_CAMPUS_CODE,
                            MMKeyConstants.CapitalAsset.ERROR_CAMPUS_CODE_BLANK,
                            orderDetail.getDistributorNbr());
                    isValid = false;
                }
                else if(!isValidCampus(infoDetail.getCampusCode())){
                	GlobalVariables.getMessageMap().putError(
                    		errorProperty + infoDetailIndex + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_CAMPUS_CODE,
                            MMKeyConstants.CapitalAsset.ERROR_CAMPUS_CODE_INVALID,
                            infoDetail.getCampusCode());
                    isValid = false;
                }
                if(StringUtils.isBlank(infoDetail.getBuildingCode())) {
                	GlobalVariables.getMessageMap().putError(
                    		errorProperty + infoDetailIndex + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_BUILDING_CODE,
                            MMKeyConstants.CapitalAsset.ERROR_BUILDING_CODE_BLANK,
                            orderDetail.getDistributorNbr());
                    isValid = false;
                }
                else if(!finDataService.validateBuilding(infoDetail.getCampusCode(), infoDetail.getBuildingCode())) {
                	GlobalVariables.getMessageMap().putError(
                    		errorProperty + infoDetailIndex + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_BUILDING_CODE,
                            MMKeyConstants.CapitalAsset.ERROR_BUILDING_CODE_INVALID,
                            infoDetail.getCampusCode(), infoDetail.getBuildingCode());
                    isValid = false;
                }
                if(StringUtils.isBlank(infoDetail.getBuildingRoomNumber())) {
                	GlobalVariables.getMessageMap().putError(
                    		errorProperty + infoDetailIndex + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_ROOM_NUMBER,
                            MMKeyConstants.CapitalAsset.ERROR_ROOM_NUMBER_BLANK,
                            orderDetail.getDistributorNbr());
                    isValid = false;
                }
                else if(!finDataService.validateBuildingRoom(infoDetail.getCampusCode(), infoDetail.getBuildingCode(), infoDetail.getBuildingRoomNumber())) {
                	GlobalVariables.getMessageMap().putError(
                    		errorProperty + infoDetailIndex + MMConstants.CapitalAssetInfo.CAPITAL_ASSET_ROOM_NUMBER,
                            MMKeyConstants.CapitalAsset.ERROR_ROOM_NUMBER_INVALID,
                            infoDetail.getBuildingCode(), infoDetail.getBuildingRoomNumber());
                    isValid = false;
                }
            }
        }

    	return isValid;
    }

	/**
	 * @param recurringOrder
	 * @param errorPath
	 * @return true if the dates validate for Recurring Order
	 */
    private boolean isValidRecurringOrderData(RecurringOrder recurringOrder, String errorPath) {
		boolean isValid = true;

		if(recurringOrder.getStartDt() == null) {
			GlobalVariables.getMessageMap().putError(
					errorPath + "." + MMConstants.RecurringOrder.START_DT,
                    MMKeyConstants.RecurringOrder.ERROR_RECURRING_ORDER_START_DT_REQUIRED);
			isValid = false;
		}
		if(recurringOrder.getTimesPerYr() == null) {
			GlobalVariables.getMessageMap().putError(
					errorPath + "." + MMConstants.RecurringOrder.TIMES_PER_YEAR,
                    MMKeyConstants.RecurringOrder.ERROR_RECURRING_ORDER_TIMES_PER_YEAR_REQUIRED);
			isValid = false;
		}
		if(recurringOrder.isNoEndDateInd()) {
			if(recurringOrder.getEndDt() != null) {
				GlobalVariables.getMessageMap().putError(
						errorPath + "." + MMConstants.RecurringOrder.END_DT,
                        MMKeyConstants.RecurringOrder.ERROR_RECURRING_ORDER_END_DT_CONFLICT);
				isValid = false;
			}
		}
		else {
			if(recurringOrder.getEndDt() == null) {
				GlobalVariables.getMessageMap().putError(
						errorPath + "." + MMConstants.RecurringOrder.END_DT,
                        MMKeyConstants.RecurringOrder.ERROR_RECURRING_ORDER_END_DT_BLANK);
				isValid = false;
			}
			else if(recurringOrder.getStartDt() != null && recurringOrder.getEndDt().before(recurringOrder.getStartDt())) {
				GlobalVariables.getMessageMap().putError(
						errorPath + "." + MMConstants.RecurringOrder.END_DT,
                        MMKeyConstants.RecurringOrder.ERROR_RECURRING_ORDER_END_DT_INVALID);
				isValid = false;
			}
		}

		return isValid;
	}

    private boolean isValidCampus(String campusCode) {
		boolean valid = true;
        if (StringUtils.isBlank(campusCode)) {
            return valid;
        }

				//TODO NWU - Determine if same behaviour as before
        Campus campus = SpringContext.getBean(CampusService.class).getCampus(campusCode);
        if (campus == null || !campus.isActive()) {
            valid = false;
        }
        return valid;
	}


}
