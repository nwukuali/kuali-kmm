package org.kuali.ext.mm.document.validation.impl;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.OrderDetailForReorder;
import org.kuali.ext.mm.businessobject.StoresPersistableBusinessObject;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.document.ReOrderDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;


public class ReOrderValidator {

    /**
     * validates new Return Detail object
     * 
     * @param rdetail
     * @return
     */
    public static boolean validateNewReturnDetail(OrderDetailForReorder odetail,
            ReOrderDocument odoc) {

        boolean retVal = true;

        if (odetail.getOrderItemQty() == null || odetail.getOrderItemQty() < 1) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.ReOrderDocument.NEW_ORDER_LINE
                            + MMConstants.OrderDocument.ORDER_ITEM_QUANTITY,
                    MMKeyConstants.OrderDocument.INVALID_ORDER_ITEM_QUANTITY,
                    odetail.getOrderItemQty() == null ? "" : odetail.getOrderItemQty().toString());

            retVal = false;
        }


        if ((odetail.getOrderItemAdditionalCostAmt() == null || odetail
                .getOrderItemAdditionalCostAmt().equals(KualiDecimal.ZERO))
                && (!StringUtils.isEmpty(odetail.getAdditionalCostTypeCode()))) {

            GlobalVariables.getMessageMap().putError(
                    MMConstants.ReOrderDocument.NEW_ORDER_LINE
                            + MMConstants.OrderDocument.ORDER_ITEM_ADDITIONAL_COST_AMOUNT,
                    MMKeyConstants.OrderDocument.INVALID_ORDER_ITEM_ADDITIONAL_COST_AMOUNT);

            retVal = false;
        }


        if (StringUtils.isEmpty(odetail.getAdditionalCostTypeCode())
                && (ObjectUtils.isNotNull(odetail.getOrderItemAdditionalCostAmt()) && !odetail
                        .getOrderItemAdditionalCostAmt().equals(MMDecimal.ZERO))) {

            GlobalVariables.getMessageMap().putError(
                    MMConstants.ReOrderDocument.NEW_ORDER_LINE
                            + MMConstants.OrderDocument.ORDER_ITEM_ADDITIONAL_COST_TYPE,
                    MMKeyConstants.OrderDocument.INVALID_ORDER_ITEM_ADDITIONAL_COST_TYPE);

            retVal = false;
        }


        if (StringUtils.isEmpty(odetail.getItemNumber())) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.ReOrderDocument.NEW_ORDER_LINE
                            + MMConstants.ReOrderDocument.ITEM_NUMBER,
                    MMKeyConstants.OrderDocument.INVALID_NEW_ORDER_LINE_PARAMATER,
                    MMConstants.ReOrderDocument.ITEM_NUMBER,
                    MMConstants.ReOrderDocument.ITEM_NUMBER);

            retVal = false;
        }

        CatalogItem citem = null;


        if (!StringUtils.isEmpty(odetail.getCatalogItemId()))
            citem = StoresPersistableBusinessObject.getObjectByPrimaryKey(CatalogItem.class,
                    odetail.getCatalogItemId());

        if (citem == null)
            citem = MMServiceLocator.getCheckinOrderService().getCatalogItem(
                    odetail.getManufacturerNumber(), odetail.getDistributorNumber());

        if (citem != null) {
            for (OrderDetail ordetail : odoc.getOrderDetails()) {
                if (ordetail.getCatalogItemId().equalsIgnoreCase(citem.getCatalogItemId())) {
                    GlobalVariables.getMessageMap().putError(
                            MMConstants.ReOrderDocument.NEW_ORDER_LINE
                                    + MMConstants.ReOrderDocument.DISTRIBUTOR_NUMBER,
                            MMKeyConstants.OrderDocument.CATALOG_ITEM_ALREADY_EXISTS,
                            odetail.getManufacturerNumber(), odetail.getItemNumber(),
                            odetail.getDistributorNumber());

                    retVal = false;

                }
            }
            if (citem.getStock() != null
                    && !citem.getStock().getAgreementNbr().equalsIgnoreCase(odoc.getAgreementNbr())) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.ReOrderDocument.NEW_ORDER_LINE
                                + MMConstants.ReOrderDocument.DISTRIBUTOR_NUMBER,
                        MMKeyConstants.OrderDocument.INVALID_ORDER_ITEM_NOT_IN_AGREEMENT,
                        citem.getStock().getAgreementNbr(), odetail.getManufacturerNumber(),
                        odetail.getItemNumber(), odetail.getDistributorNumber());

                retVal = false;
            }
        }
        return retVal;
    }

}
