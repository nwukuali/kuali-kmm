package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.document.web.struts.OrderDetailVo;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;


public class CheckinValidator {

	public static boolean validateNewOrderDetailVo(OrderDetailVo vo) {

		boolean allNull = StringUtils.isEmpty(vo.getPoNumber())
				&& StringUtils.isEmpty(vo.getItemNumber()) // &&
															// StringUtils.isEmpty(vo.getManufacturerNumber())
				&& ObjectUtils.isNull(vo.getAcceptedItemQuantity())
				&& StringUtils.isEmpty(vo.getUnitOfIssueCode())
				&& ObjectUtils.isNull(vo.getZoneCode())
				&& StringUtils.isEmpty(vo.getReasonCode());

//		if (allNull)
//			return allNull;

		boolean allNotNull = !StringUtils.isEmpty(vo.getPoNumber())
				&& !StringUtils.isEmpty(vo.getItemNumber()) // &&
															// !StringUtils.isEmpty(vo.getManufacturerNumber())
				&& !ObjectUtils.isNull(vo.getAcceptedItemQuantity())
				&& !StringUtils.isEmpty(vo.getUnitOfIssueCode())
				&& !ObjectUtils.isNull(vo.getZoneCode())
				&& !StringUtils.isEmpty(vo.getReasonCode());

			if (StringUtils.isEmpty(vo.getPoNumber()))
				GlobalVariables.getMessageMap().putError(
						MMConstants.CheckinDoc.NEW_ORDER_DETAIL+MMConstants.CheckinDoc.NEW_PO_PONUMBER,
						MMKeyConstants.CheckinDoc.INVALID_VALUE_PASSED,
						new String[] { "Po Number" });

			if (ObjectUtils.isNull(vo.getAcceptedItemQuantity()))
				GlobalVariables.getMessageMap().putError(
						MMConstants.CheckinDoc.NEW_ORDER_DETAIL+MMConstants.CheckinDoc.NEW_PO_ACCEPTED_ITEM_QUANTITY_VAL,
						MMKeyConstants.CheckinDoc.INVALID_VALUE_PASSED,
						new String[] { "Accepted Item Quantity" });

			if (!ObjectUtils.isNull(vo.getAcceptedItemQuantity())
					&& vo.getAcceptedItemQuantity() < 0)
				GlobalVariables
						.getMessageMap()
						.putError(
								MMConstants.CheckinDoc.NEW_ORDER_DETAIL+MMConstants.CheckinDoc.NEW_PO_ACCEPTED_ITEM_QUANTITY_VAL,
								MMKeyConstants.CheckinDoc.INVALID_ACCEPTED_ITEM_QUANTITY_ENTERED,
								new String[] { vo.getAcceptedItemQuantityVal() });

//			if (StringUtils.isEmpty(vo.getManufacturerNumber()))
//				GlobalVariables.getMessageMap().putError(
//						MMConstants.CheckinDoc.NEW_ORDER_DETAIL+MMConstants.CheckinDoc.NEW_PO_MANUF_NUMBER,
//						MMKeyConstants.CheckinDoc.INVALID_VALUE_PASSED,
//						new String[] { "Manufacturer Number" });

			if (StringUtils.isEmpty(vo.getItemNumber()))
				GlobalVariables.getMessageMap().putError(
						MMConstants.CheckinDoc.NEW_ORDER_DETAIL+MMConstants.CheckinDoc.NEW_PO_ITEM_NUMBER,
						MMKeyConstants.CheckinDoc.INVALID_VALUE_PASSED,
						new String[] { "Item Number" });

//			if (StringUtils.isEmpty(vo.getUnitOfIssueCode()))
//				GlobalVariables.getMessageMap().putError(
//						MMConstants.CheckinDoc.NEW_ORDER_DETAIL+MMConstants.CheckinDoc.NEW_PO_UNIT_OF_ISSUE_CODE,
//						MMKeyConstants.CheckinDoc.INVALID_VALUE_PASSED,
//						new String[] { "Unit Of issue Code" });

			if (StringUtils.isEmpty(vo.getBinNumber()))
				GlobalVariables.getMessageMap().putError(
						MMConstants.CheckinDoc.NEW_ORDER_DETAIL+MMConstants.CheckinDoc.BIN_NUMBER,
						MMKeyConstants.CheckinDoc.INVALID_VALUE_PASSED,
						new String[] { "Bin Number" });

			if (StringUtils.isEmpty(vo.getReasonCode()))
				GlobalVariables.getMessageMap().putError(
						MMConstants.CheckinDoc.NEW_ORDER_DETAIL+MMConstants.CheckinDoc.NEW_PO_REASON_CODE,
						MMKeyConstants.CheckinDoc.INVALID_VALUE_PASSED,
						new String[] { "Reason Code" });

//		}
		return allNotNull;
	}

}
