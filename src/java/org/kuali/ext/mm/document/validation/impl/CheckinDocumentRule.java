package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.document.service.BusinessObjectLockingService;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.RentalService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.DocumentRuleBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.*;


public class CheckinDocumentRule extends DocumentRuleBase {

    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processRouteDocument(org.kuali.rice.kns.document.Document)
     */
    @Override
    public boolean processRouteDocument(Document document) {
        boolean retVal = SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .checkAndErrorSystemAvailability();
        CheckinDocument checkinDocument = (CheckinDocument) document;
        List<CheckinDetail> cdetails = checkinDocument.getCheckinDetails();
        int index = 0;

        Integer selectedItem = checkinDocument.getSelectedOrderDetailId();

        List<String> rentalSerialnumbers = getAllRentalSerialNumbers(cdetails);

        List<Integer> addedBins = new ArrayList<Integer>(0);

        int count = 0;

        for (CheckinDetail cdetail : cdetails) {
            if ((cdetail.getAcceptedItemQty() == null || cdetail.getAcceptedItemQty() < 1)
                    && (cdetail.getRejectedItemQty() == null || cdetail.getRejectedItemQty() < 1)
                    && !cdetail.isClosedInd())
                count++;
        }

        if (count == cdetails.size()) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + "[0]."
                            + MMConstants.CheckinDoc.ACCEPTED_ITEM_QUANTITY,
                    MMKeyConstants.CheckinDoc.INVALID_DOCUMENT);

            return false;
        }

        boolean isLineSleected = ObjectUtils.isNull(selectedItem) || selectedItem == 0;
        for (CheckinDetail cdetail : cdetails) {
            String dispString = "[" + index + "].";
            if ((isLineSleected || cdetail.getOrderDetailId().equals(selectedItem))
                    && cdetail.getOrderDetail().isLineStatusCheckinable()) {

                Integer accpetedItemQty = cdetail.getAcceptedItemQty() == null ? 0 : cdetail
                        .getAcceptedItemQty();
                Integer rejectedItemQty = cdetail.getRejectedItemQty() == null ? 0 : cdetail
                        .getRejectedItemQty();

                if (rejectedItemQty > 0) {
                    retVal &= validateRejectedFields(cdetail, dispString);
                }
                if (retVal && accpetedItemQty == 0 && rejectedItemQty > 0) {
                    index++;
                    continue;
                }
                if (cdetail.getStock().isRental()) {
                    retVal &= validateRentalData(cdetail, dispString, rentalSerialnumbers);
                }
                if (cdetail.getStock().isPerishableInd()) {
                    if (ObjectUtils.isNull(cdetail.getStockPerishableDate())) {
                        GlobalVariables.getMessageMap().putError(
                                MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + dispString
                                        + MMConstants.CheckinDocument.STOCK_PERISHABLE_DATE,
                                MMKeyConstants.CheckinDoc.INVALID_STOCK_PERISHABLE_DATE,
                                cdetail.getStock().getStockDistributorNbr());
                        retVal = false;
                    }
                }


                retVal &= validateBinSelection(addedBins, cdetail, dispString, accpetedItemQty);
            }
            index++;
        }
        retVal &= validateAcceptedQuantities(cdetails);


        return retVal;
    }

    /**
     * @param cdetails
     * @return
     */
    private List<String> getAllRentalSerialNumbers(List<CheckinDetail> cdetails) {
        List<String> serialNumbers = new ArrayList<String>();
        for(CheckinDetail cdetail : cdetails) {
            if(!MMUtil.isCollectionEmpty(cdetail.getCheckinRentals())) {
                for(StagingRental srental : cdetail.getCheckinRentals()) {
                    serialNumbers.add(srental.getSerialNumber());
                }
            }
        }
        return serialNumbers;
    }

    /**
     * @param retVal
     * @param cdetails
     * @return
     */
    protected boolean validateAcceptedQuantities(List<CheckinDetail> cdetails) {
        boolean retVal = true;
        HashMap<Integer, Integer> orderQtyMap = new HashMap<Integer, Integer>();
        HashMap<Integer, OrderDetail> orderMap = new HashMap<Integer, OrderDetail>();
        HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();

        int index = 0;
        for (CheckinDetail cdetail : cdetails) {
            Integer accpetedItemQty = cdetail.getAcceptedItemQty() == null ? 0 : cdetail
                    .getAcceptedItemQty();
            Integer rejectedItemQty = cdetail.getRejectedItemQty() == null ? 0 : cdetail
                    .getRejectedItemQty();
            if (accpetedItemQty > 0 || rejectedItemQty > 0) {
                if (orderQtyMap.get(cdetail.getOrderDetailId()) == null) {
                    orderQtyMap.put(cdetail.getOrderDetailId(), accpetedItemQty + rejectedItemQty);
                    orderMap.put(cdetail.getOrderDetailId(), cdetail.getOrderDetail());
                    indexMap.put(cdetail.getOrderDetailId(), index);
                }
                else {
                    orderQtyMap.put(cdetail.getOrderDetailId(), orderQtyMap.get(cdetail
                            .getOrderDetailId())
                            + (accpetedItemQty + rejectedItemQty));
                }

            }
            index++;
        }
        Set<Integer> orderIds = orderQtyMap.keySet();
        for (Integer orderId : orderIds) {
            OrderDetail orderDetail = orderMap.get(orderId);
            Integer orderRemainingQty = orderDetail.getRemainingItemQuantity();
            Integer orderAcceptedQty = orderQtyMap.get(orderId);
            if (orderRemainingQty < orderAcceptedQty) {
                GlobalVariables
                        .getMessageMap()
                        .putError(
                                MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + "["
                                        + indexMap.get(orderId) + "]."
                                        + MMConstants.CheckinDoc.ACCEPTED_ITEM_QUANTITY,
                                MMKeyConstants.CheckinDoc.ACCEPTED_ITEM_QUANTITY_EXCEEDS_ORDER_ITEM_QUANTITY,
                                String.valueOf(orderAcceptedQty), String.valueOf(orderRemainingQty));
                retVal = false;
            }
            // check for buy unit of issue rate to make sure that we recieved in correct package sizing
            if (computeAcceptedBuyQuantity(orderDetail, orderAcceptedQty) % 1d != 0) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + "["
                                + indexMap.get(orderId) + "]."
                                + MMConstants.CheckinDoc.ACCEPTED_ITEM_QUANTITY,
                        MMKeyConstants.CheckinDoc.ACCEPTED_ITEM_INVALID_BUY_RATE_QUANTITY,
                        orderDetail.getBuyUnitOfIssueRt().toString());
                retVal = false;
            }
        }
        return retVal;
    }

    public double computeAcceptedBuyQuantity(OrderDetail orderDetail, Integer acceptedQty) {
        if (ObjectUtils.isNotNull(orderDetail)) {
            MMDecimal buyUnitOfIssueRt = orderDetail.getBuyUnitOfIssueRt();
            return acceptedQty / buyUnitOfIssueRt.doubleValue();
        }
        return acceptedQty;
    }

    /**
     * @param retVal
     * @param addedBins
     * @param cdetail
     * @param dispString
     * @param accpetedItemQty
     * @return
     */
    protected boolean validateBinSelection(List<Integer> addedBins, CheckinDetail cdetail,
            String dispString, Integer accpetedItemQty) {
        boolean retVal = true;
        if (cdetail.getBinId() == null) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + dispString
                            + MMConstants.CheckinDoc.BIN_NUMBER,
                    MMKeyConstants.CheckinDoc.BIN_NOT_SELECTED);
            retVal = false;

        }


        Integer binId = cdetail.getBinId();
        Bin bin = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Bin.class, binId);

        if (ObjectUtils.isNull(bin)) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + dispString
                            + MMConstants.CheckinDoc.BIN_NUMBER,
                    MMKeyConstants.CheckinDoc.BIN_NOT_SELECTED);
            retVal = false;

        }
        else {
            if (addedBins.contains(cdetail.getBinId())) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + dispString
                                + MMConstants.CheckinDoc.BIN_NUMBER,
                        MMKeyConstants.CheckinDoc.SAME_BIN_SELECTED);
                retVal = false;
            }
            else {
                addedBins.add(cdetail.getBinId());
            }

            StockBalance sb = bin.getStockBalance();

            if (ObjectUtils.isNotNull(sb) && !sb.getStockId().equals(cdetail.getStockId())
                    && sb.getQtyOnHand() > 0) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + dispString
                                + MMConstants.CheckinDoc.BIN_NUMBER,
                        MMKeyConstants.CheckinDoc.BIN_HAS_STOCK,
                        new String[] { bin.getBinNbr(), sb.getStock().getStockDistributorNbr() });
                retVal = false;

            }
            if (bin.getAvailableQty() < accpetedItemQty) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + dispString
                                + MMConstants.CheckinDoc.BIN_NUMBER,
                        MMKeyConstants.CheckinDoc.BIN_QUANTITY_EXCEEDED,
                        new String[] { bin.getBinNbr(), accpetedItemQty.toString() });
                retVal = false;
            }
        }
        return retVal;
    }

    /**
     * @param retVal
     * @param serialNumberList
     * @param cdetail
     * @param dispString
     * @return
     */
    protected boolean validateRentalData(CheckinDetail cdetail, String dispString, List<String> serialNumberList) {
        boolean retVal = true;
        List<StagingRental> rentals = cdetail.getCheckinRentals();
        RentalService rentalService = MMServiceLocator.getRentalService();
        if (MMUtil.isCollectionEmpty(rentals) || rentals.size() != cdetail.getAcceptedItemQty()) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.RentalTrackingDocument.SELECTED_ITEMS + dispString,
                        MMKeyConstants.CheckinDoc.RENTAL_COUNT_INVALID,
                        new String[] { String.valueOf(cdetail.getAcceptedItemQty()) });
                retVal = false;
        }
        else {
            for (StagingRental stagingRental : rentals) {
                String serialNumber = stagingRental.getSerialNumber();
                if (serialNumberList.indexOf(serialNumber) != serialNumberList.lastIndexOf(serialNumber)) {
                    GlobalVariables.getMessageMap().putError(
                        MMConstants.RentalTrackingDocument.SELECTED_ITEMS + dispString,
                        MMKeyConstants.CheckinDoc.RENTAL_SERIAL_NUMBER_ALREADY_EXISTS_IN_DOCUMENT,
                        serialNumber);
                    retVal = false;
                }
                boolean isValidRental = rentalService.isSerialNumberValidForCheckin(
                        cdetail.getStock().getRentalObject().getRentalTypeCode(),
                        stagingRental.getSerialNumber(), cdetail.getCorrectedCheckinDetailId());
                if(!isValidRental) {
                    GlobalVariables.getMessageMap().putError(
                            MMConstants.RentalTrackingDocument.SELECTED_ITEMS + dispString,
                            MMKeyConstants.CheckinDoc.RENTAL_EXISTS,
                            stagingRental.getSerialNumber() );
                    retVal = false;
                }
            }
            
            
        }
        return retVal;
    }

    /**
     * @param retVal
     * @param cdetail
     * @param dispString
     * @return
     */
    protected boolean validateRejectedFields(CheckinDetail cdetail, String dispString) {
        boolean retVal = true;
        String statusCode = cdetail.getReturnDetailStatusCode();
        if (StringUtils.isEmpty(statusCode)) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + dispString
                            + MMConstants.CheckinDocument.RETURN_DETAIL_STATUS_CODE,
                    MMKeyConstants.CheckinDoc.EMPTY_REJECTED_ITEM_REASON_CODE,
                    cdetail.getStock().getStockDistributorNbr());
            retVal = false;
        }

        String unitOfIssueCode = cdetail.getReturnUnitOfIssueOfCode();
        if (StringUtils.isEmpty(unitOfIssueCode)) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + dispString
                            + MMConstants.CheckinDocument.RETUNR_UNIT_ISSUE_OF_CODE,
                    MMKeyConstants.CheckinDoc.EMPTY_REJECTED_ITEM_UNIT_OF_ISSUE,
                    cdetail.getStock().getStockDistributorNbr());
            retVal = false;
        }
        return retVal;
    }

    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    public boolean processCustomSaveDocumentBusinessRules(Document document) {
        boolean isValid = true;
        BusinessObjectLockingService lockingService = SpringContext
                .getBean(BusinessObjectLockingService.class);
        List<String> lockingDocIds = new ArrayList<String>();
        for (CheckinDetail detail : ((CheckinDocument) document).getCheckinDetails()) {
            detail.refreshReferenceObject(MMConstants.CheckinDetail.STOCK);
            lockingDocIds.addAll(lockingService.getLockingDocumentIds(detail.getStock(),
                    MMConstants.Stock.STOCK_ID, ""));
        }
        if (!lockingDocIds.isEmpty()) {
            Properties parameters = new Properties();
            parameters.put(KRADConstants.PARAMETER_DOC_ID, lockingDocIds.get(0));
            parameters.put(KRADConstants.PARAMETER_COMMAND,
                    KRADConstants.METHOD_DISPLAY_DOC_SEARCH_VIEW);
            String blockingUrl = UrlFactory.parameterizeUrl(getKualiConfigurationService()
							.getPropertyValueAsString(KRADConstants.WORKFLOW_URL_KEY)
							+ "/" + KRADConstants.DOC_HANDLER_ACTION, parameters);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,
							RiceKeyConstants.ERROR_MAINTENANCE_LOCKED, blockingUrl, lockingDocIds.get(0));
            isValid = false;
        }

        return isValid;
    }


}
