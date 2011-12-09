/*
 * Copyright 2006-2007 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.document.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.Agreement;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.OrderDetailForReorder;
import org.kuali.ext.mm.businessobject.StoresPersistableBusinessObject;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReOrderDocument;
import org.kuali.ext.mm.document.validation.impl.ReOrderValidator;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;


/**
 * This is the action class for the WorksheetDoc.
 */
public class ReOrderAction extends KualiTransactionalDocumentActionBase {

    private org.apache.log4j.Logger loggerAc = org.apache.log4j.Logger
            .getLogger(ReOrderAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionForward ad = super.execute(mapping, form, request, response);
        return ad;
    }

    public ActionForward deleteOrderLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReorderItemsForm reorderForm = (ReorderItemsForm) form;
        int index = getSelectedLine(request);
        ReOrderDocument odoc = reorderForm.getOrderDocument();
        OrderDetail selDetail = odoc.getOrderDetailsForReorder().get(index);
        selDetail.setOrderDocumentNbr(null);
        selDetail.setOrderDocument(null);
        odoc.getOrderDetailsForReorder().remove(index);
        return mapping.findForward(MMConstants.MAPPING_BASIC);

    }

    public ActionForward addOrderLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ReorderItemsForm reorderForm = (ReorderItemsForm) form;
        ReOrderDocument odoc = reorderForm.getOrderDocument();

        Agreement ag = odoc.getAgreement();

        if (ag == null)
            ag = StoresPersistableBusinessObject.getObjectByPrimaryKey(Agreement.class, odoc
                    .getAgreementNbr());

        OrderDetail newLine = reorderForm.getNewOrderDetail().getOrderDetail();

        if (validateNewOrderLine(reorderForm.getNewOrderDetail(), odoc)) {
            newLine.setOrderStatusCd(MMConstants.OrderStatus.INITIATED);
            newLine.setOrderDocument(odoc);           
            Agreement agreement = (newLine.getCatalogItem() != null && newLine.getCatalogItem()
                    .getStock() != null) ? newLine.getCatalogItem().getStock().getAgreement()
                    : null;
            CatalogItem catalogItem = newLine.getCatalogItem();
            Integer orderItemQty = newLine.getOrderItemQty();
            OrderDetail addedLine = newLine;
            if (catalogItem != null && orderItemQty != null) {
                addedLine = MMServiceLocator.getReOrderService().createOrderDetail(catalogItem, odoc,
                        agreement, orderItemQty, (odoc.getOrderDetails().size()+ odoc.getOrderDetailsForReorder().size() + 1));
                MMServiceLocator.getReOrderService().loadAccountingInfoForOrderLine(newLine,
                        odoc.getWarehouse().getCostOfGoodsAccount());
            }
            if(newLine.getExpectedDate()!=null){
                addedLine.setExpectedDate(newLine.getExpectedDate());
            }
            if (newLine.getAdditionalCostTypeCode() != null ) {
                addedLine.setAdditionalCostTypeCode(newLine.getAdditionalCostTypeCode());
                addedLine.setOrderItemAdditionalCostAmt(newLine.getOrderItemAdditionalCostAmt());
            }
            odoc.addOrderDetailsForReorder(addedLine);
            reorderForm.addNewOrderDetail();
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);

    }

    private boolean validateNewOrderLine(OrderDetailForReorder odetail, ReOrderDocument odoc) {
        return ReOrderValidator.validateNewReturnDetail(odetail, odoc);
    }

    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        // ReturnOrderForm rform = (ReturnOrderForm) form;

        ActionForward ad = super.docHandler(mapping, form, request, response);

        ReorderItemsForm returnOrderform = (ReorderItemsForm) form;
        ReOrderDocument orderDoc = returnOrderform.getOrderDocument();
        String action = request.getParameter(MMConstants.ReorderItem.EDIT_ITEMS_ACTION);

        String agreementNumber = request
                .getParameter(MMConstants.ReorderItem.REORDER_AGREEMENT_NUMBER);
        String subCatalogName = request
                .getParameter(MMConstants.ReorderItem.REORDER_SUB_CATALOG_GROUP_NAME);
        String warehouseCode = request.getParameter(MMConstants.Warehouse.WAREHOUSE_CD);

        String catalogCode = request
                .getParameter(MMConstants.ReorderItem.CATALOG_SUBGROUP_CATALOG_CODE);
        String catalogSubGroupCode = request
                .getParameter(MMConstants.ReorderItem.CATALOG_SUBGROUP_CATALOG_SUBGROUPCODE);

        if (MMUtil.isCollectionEmpty(orderDoc.getOrderDetails()) && !StringUtils.isEmpty(action))
            orderDoc = MMServiceLocator.getReOrderService().createOrderDocument(action,
                    agreementNumber, catalogCode, catalogSubGroupCode, warehouseCode, orderDoc);
        
        returnOrderform.setDocument(orderDoc);

        return ad;
    }

    @Override
    public ActionForward performRouteReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward(MMConstants.MAPPING_BACK);
    }

    /**
     * This method adds the newly added order lines to the existing collection of order lines
     * 
     * @param reoderDoc
     */
    private void addOrderlinesNotOnPO(ReOrderDocument reoderDoc) {

        if (!MMUtil.isCollectionEmpty(reoderDoc.getOrderDetailsForReorder())) {
            reoderDoc.getOrderDetails().addAll(reoderDoc.getOrderDetailsForReorder());
            reoderDoc.getOrderDetailsForReorder().clear();
        }

    }

    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ReorderItemsForm returnOrderform = (ReorderItemsForm) form;
        addOrderlinesNotOnPO(returnOrderform.getOrderDocument());
        ActionForward ad = super.save(mapping, form, request, response);

        return ad;
    }
    
    @Override
    public ActionForward route(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ReorderItemsForm returnOrderform = (ReorderItemsForm) form;
        addOrderlinesNotOnPO(returnOrderform.getOrderDocument());
        ActionForward ad = super.route(mapping, form, request, response);
        return ad;
    }

}