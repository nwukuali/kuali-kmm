/**
 *
 */
package org.kuali.ext.mm.document.validation.impl;

import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.commons.lang.time.DateUtils;
import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.document.ReOrderDocument;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rules.DocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;


/**
 * @author rponraj
 *
 */
public class ReOrderDocumentRule extends DocumentRuleBase {
    Timestamp today;

    @Override
    public boolean processRouteDocument(Document document) {
        ReOrderDocument reorderDoc = (ReOrderDocument) document;
        boolean retVal = true;
        
        if(ObjectUtils.isNull(reorderDoc.getCustomerProfile())) {
            GlobalVariables.getMessageMap().putError(MMConstants.DOCUMENT,
                    MMKeyConstants.OrderDocument.NO_DEFAULT_PROFILE);
            retVal = false;
        }

        
        int index = 0;
        for(OrderDetail odetail : reorderDoc.getOrderDetails()){
            String dispString = "[" + index + "].";
            if(!odetail.isItemToBeRemoved()){
            //validation for order item quantity
            if(odetail.getOrderItemQty() == null || odetail.getOrderItemQty() < 1){
                GlobalVariables.getMessageMap().putError(MMConstants.OrderDocument.ORDER_DETAILS+ dispString+MMConstants.OrderDocument.ORDER_ITEM_QUANTITY ,
                        MMKeyConstants.OrderDocument.INVALID_ORDER_ITEM_QUANTITY,
                        odetail.getOrderItemQty() == null ? "" :odetail.getOrderItemQty().toString()
                            );

                retVal = false;
            }
            
            else if (retVal && odetail.getBuyOrderQuantity().doubleValue() % 1d != 0d){
                GlobalVariables.getMessageMap().putError(MMConstants.OrderDocument.ORDER_DETAILS+ dispString+MMConstants.OrderDocument.ORDER_ITEM_QUANTITY ,
                        MMKeyConstants.OrderDocument.INVALID_BUY_QUANTITY,
                        odetail.getOrderItemQty() == null ? "" :odetail.getOrderItemQty().toString(), odetail.getBuyUnitOfIssueRt().toString()
                            );

                retVal = false;
            }

            //validation for expected date
            if(odetail.getExpectedDate() == null){
               GlobalVariables.getMessageMap().putError(MMConstants.OrderDocument.ORDER_DETAILS+ dispString+MMConstants.OrderDocument.ORDER_ITEM_EXPECTED_DATE ,
                       MMKeyConstants.OrderDocument.INVALID_ORDER_ITEM_EXPECTED_DATE
                           );

               retVal = false;

           }

            // validation for additional cost type and additional cost amount
            if(!StringUtils.isEmpty(odetail.getAdditionalCostTypeCode()) || ObjectUtils.isNotNull(odetail.getAdditionalCostType())
                    ){
                if(odetail.getOrderItemAdditionalCostAmt() == null || odetail.getOrderItemAdditionalCostAmt().equals(KualiDecimal.ZERO)){

                    GlobalVariables.getMessageMap().putError(MMConstants.OrderDocument.ORDER_DETAILS+ dispString+MMConstants.OrderDocument.ORDER_ITEM_ADDITIONAL_COST_AMOUNT ,
                            MMKeyConstants.OrderDocument.INVALID_ORDER_ITEM_ADDITIONAL_COST_AMOUNT
                                );

                    retVal = false;
                }
            }

            if(odetail.getOrderItemAdditionalCostAmt() != null && !odetail.getOrderItemAdditionalCostAmt().equals(KualiDecimal.ZERO)){
                if(StringUtils.isEmpty(odetail.getAdditionalCostTypeCode())){

                    GlobalVariables.getMessageMap().putError(MMConstants.OrderDocument.ORDER_DETAILS+ dispString+MMConstants.OrderDocument.ORDER_ITEM_ADDITIONAL_COST_TYPE ,
                            MMKeyConstants.OrderDocument.INVALID_ORDER_ITEM_ADDITIONAL_COST_TYPE
                                );

                    retVal = false;
                }
            }
        }
            //validation for remove until date

            if(odetail.isItemToBeRemoved()){
                if(odetail.getCatalogItem().getStock().getRemoveUntilDate() == null ){
                    GlobalVariables.getMessageMap().putError(MMConstants.OrderDocument.ORDER_DETAILS+ dispString+MMConstants.OrderDocument.STOCK_REMOVE_UNTIL_DATE ,
                            MMKeyConstants.OrderDocument.INVALID_ORDER_ITEM_STOCK_REMOVE_UNTIL_DATE
                                );

                    retVal = false;
                }
            }
            
            today = KNSServiceLocator.getDateTimeService().getCurrentTimestamp();
            today.setTime(DateUtils.truncate(today, Calendar.DAY_OF_MONTH).getTime());
            if(odetail.getExpectedDate().before(today)){
                GlobalVariables.getMessageMap().putError(MMConstants.OrderDocument.ORDER_DETAILS+dispString+MMConstants.OrderDocument.EXPECTED_DATE,MMKeyConstants.OrderDocument.ERROR_EXPECTED_DATE_INVALID);
                retVal=false;
            }

            index++;
        }

        return retVal;
    }

}
