package org.kuali.ext.mm.document.validation.impl;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.PickListDocument;
import org.kuali.ext.mm.service.PickListService;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.DocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;


public class PickListDocumentRule extends DocumentRuleBase {

	/**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean processCustomSaveDocumentBusinessRules(Document document) {
        PickListDocument pickListDocument = (PickListDocument) document;

        pickListDocument.refreshReferenceObject(MMConstants.PickListDocument.WAREHOUSE);
        if(ObjectUtils.isNull(pickListDocument.getWarehouse()))
        	return false;

        boolean isWarehouseValid = SpringContext.getBean(PickListService.class).pickListLinesMatchDocumentWarehouse(pickListDocument);
        boolean isPickStatusValid = SpringContext.getBean(PickListService.class).isPickListLinesStatusInit(pickListDocument);

        if(!isWarehouseValid) {
        	GlobalVariables.getMessageMap().putError(MMConstants.PickListDocument.WAREHOUSE_CD, MMKeyConstants.ERROR_WAREHOUSE_NOT_UNIFORM, pickListDocument.getWarehouse().getWarehouseCd());
        }

        if(!isPickStatusValid) {
	        GlobalVariables.getMessageMap().putError(MMConstants.PickStatusCode.PICK_STATUS_CODE, MMKeyConstants.ERROR_PICKLIST_NOT_INIT, MMConstants.PickStatusCode.PICK_STATUS_INIT);
        }

        boolean hasPickListLines = true;
        if(pickListDocument.getPickListLines().isEmpty()) {
	        GlobalVariables.getMessageMap().putError(MMConstants.PickListDocument.PICK_LIST_LINE_ERROR, MMKeyConstants.ERROR_PICKLIST_EMPTY);
	       hasPickListLines = false;
        }

        return  isPickStatusValid && isWarehouseValid && hasPickListLines;
    }
}
