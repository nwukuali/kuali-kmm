package org.kuali.ext.mm.document.validation.impl;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.businessobject.ReturnStatusCode;
import org.kuali.ext.mm.businessobject.StoresPersistableBusinessObject;
import org.kuali.ext.mm.businessobject.UnitOfIssue;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KualiDecimal;


public class ReturnOrderValidator {

	/**
	 * validates new Return Detail object
	 * @param rdetail
	 * @return
	 */
	public static boolean validateNewReturnDetail(ReturnDetail rdetail){

		boolean isValid = true;

		if(rdetail.getReturnQuantity() == null || rdetail.getReturnQuantity()< 1){
			GlobalVariables
			.getMessageMap()
			.putError(
					MMConstants.ReturnDocument.NEW_RETURN_DETAIL_RETURN_QUANTITY,
					MMKeyConstants.ReturnDocument.EMPTY_RETURN_ITEM_QUANTITY);
			isValid = false;
		}

        if(StringUtils.isEmpty(rdetail.getReturnDetailStatusCode())){
            GlobalVariables
            .getMessageMap()
            .putError(
                    MMConstants.ReturnDocument.NEW_RETURN_RETURN_STATUS_CODE,
                    MMKeyConstants.ReturnDocument.EMPTY_RETURN_STATUS_CODE);
            isValid = false;
        }

        String statusCode = rdetail.getReturnDetailStatusCode();
        ReturnStatusCode dataObj = StoresPersistableBusinessObject.getObjectByPrimaryKey(ReturnStatusCode.class, statusCode);

        if(dataObj != null)
            rdetail.setReturnStatus(dataObj);

		if(StringUtils.isEmpty(rdetail.getReturnUnitOfIssueOfCode())){
			GlobalVariables
			.getMessageMap()
			.putError(
					MMConstants.ReturnDocument.NEW_RETURN_RETURN_UNIT_ISSUE_OF_CODE,
					MMKeyConstants.ReturnDocument.EMPTY_RETURN_UNIT_ISSUE_OF_CODE);
			isValid = false;
		}

		String unitOfIssue = rdetail.getReturnUnitOfIssueOfCode();
		UnitOfIssue data   = StoresPersistableBusinessObject.getObjectByPrimaryKey(UnitOfIssue.class, unitOfIssue);

		if(data != null)
		    rdetail.setReturnUnitOfIssue(data);

		if(StringUtils.isEmpty(rdetail.getStockDistributorNumber())){
			GlobalVariables
			.getMessageMap()
			.putError(
					MMConstants.ReturnDocument.NEW_RETURN_STOCK_DISTRIBUTOR_NUMBER,
					MMKeyConstants.ReturnDocument.EMPTY_STOCK_DISTRIBUTOR_NUMBER);
			isValid = false;
		}else{

			 ReturnDetail popObj = MMServiceLocator.getReturnOrderService().getReturnDetailWithStock(rdetail);

			 if(popObj == null){
					GlobalVariables
					.getMessageMap()
					.putError(
							MMConstants.ReturnDocument.NEW_RETURN_STOCK_DISTRIBUTOR_NUMBER,
							MMKeyConstants.ReturnDocument.INVALID_STOCK_DISTRIBUTOR_NUMBER,
							rdetail.getStockDistributorNumber());
					isValid = false;

			 } else{
				 rdetail = popObj;
			 }
		}

//		if(StringUtils.isEmpty(rdetail.getReturnItemDetailDescription())){
//			GlobalVariables
//			.getMessageMap()
//			.putError(
//					MMConstants.ReturnDocument.NEW_RETURN_RETURN_ITEM_DESCRIPTION,
//					MMKeyConstants.ReturnDocument.EMPTY_RETURN_ITEM_DESCRIPTION);
//			isValid = false;
//		}

		if(rdetail.getReturnItemPrice() == null || rdetail.getReturnItemPrice().equals(KualiDecimal.ZERO)){
			GlobalVariables
			.getMessageMap()
			.putError(
					MMConstants.ReturnDocument.NEW_RETURN_RETURN_ITEM_UNIT_COST,
					MMKeyConstants.ReturnDocument.EMPTY_RETURN_ITEM_UNIT_COST);
			isValid = false;
		}

		return isValid;
	}

}
