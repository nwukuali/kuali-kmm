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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CheckinDetail;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.StagingRental;
import org.kuali.ext.mm.businessobject.Stock;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.document.validation.impl.CheckinValidator;
import org.kuali.ext.mm.service.CheckinOrderService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;


public class CheckinReceiveAction extends RentalTrackingActionBase {

    public CheckinReceiveAction() {
        super();
    }

    private org.apache.log4j.Logger loggerAc = org.apache.log4j.Logger
            .getLogger(CheckinReceiveAction.class);

    private CheckinOrderService checkinOrderService(){
        return  MMServiceLocator.getCheckinOrderService();
    }

    private StockService stockService(){
        return MMServiceLocator.getStockService();
    }


    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#docHandler(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionForward ad = super.docHandler(mapping, form, request, response);

        CheckinReceiveForm checkinForm = (CheckinReceiveForm) form;
        CheckinDocument checkinDoc = checkinForm.getCheckinDoc();
        String orderDocNumber = request.getParameter(MMConstants.CheckinDocument.ORDER_DOC_NUMBER);
        String orderLineNumber = request.getParameter(MMConstants.CheckinDocument.ORDER_DETAIL_ID);

        if (StringUtils.isNotEmpty(orderDocNumber)
                && MMUtil.isCollectionEmpty(checkinDoc.getCheckinDetails()))
            checkinDoc = checkinOrderService().createCheckinDocItems(checkinDoc, orderDocNumber,
                    orderLineNumber);

        // used for disabling submit button when lines are viewed separately
        checkinForm.setOnlyLineViewed(StringUtils.isNotEmpty(request
                .getParameter(MMConstants.CheckinDocument.LINE_CHECKED_IN)));

        if (!StringUtils.isEmpty(orderLineNumber))
            checkinDoc.setSelectedOrderDetailId(Integer.valueOf(orderLineNumber));
        else
            checkinDoc.setSelectedOrderDetailId(null);

        createRentals(checkinForm);
        return ad;
    }

    @Override
    public ActionForward performRouteReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward(MMConstants.MAPPING_BACK);
    }


    @Override
    public ActionForward route(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        CheckinReceiveForm checkinForm = (CheckinReceiveForm) form;
        CheckinDocument checkinDoc = checkinForm.getCheckinDoc();
        if (checkinForm.isNewPoAdded()) {
            checkinDoc = this.checkinOrderService().addNewPo(checkinDoc, checkinForm
                    .getPoOrderDetails());
            clearNewPoItems(checkinForm);
        }
        ActionForward ad = super.route(mapping, form, request, response);
        return ad;
    }

    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        CheckinReceiveForm checkinForm = (CheckinReceiveForm) form;
        CheckinDocument checkinDoc = checkinForm.getCheckinDoc();
        if (checkinForm.isNewPoAdded()) {
            checkinDoc = this.checkinOrderService().addNewPo(checkinDoc, checkinForm
                    .getPoOrderDetails());
            clearNewPoItems(checkinForm);
        }
//        createRentals(checkinForm);
        ActionForward ad = super.save(mapping, form, request, response);
        return ad;
    }

    /**
     * Method for adding a new checkin line to the Checkin Document
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addCheckinDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        CheckinReceiveForm checkinForm = (CheckinReceiveForm) form;
        Integer orderDetailId = getSelectedLine(request);
        addNewRow(orderDetailId, checkinForm);
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    /**
     * Method for removing a checkin line from the Checkin Document
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteCheckinDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        CheckinReceiveForm checkinForm = (CheckinReceiveForm) form;
        CheckinDocument checkinDoc = checkinForm.getCheckinDoc();
        Integer checkinDetailId = getSelectedLine(request);
        deleteCheckinDetail(checkinDetailId, checkinDoc);
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    /**
     * Method for updating the stock price for the items checked in
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    /**
     * clears all the Po items
     * 
     * @param checkinForm
     */
    private void clearNewPoItems(CheckinReceiveForm checkinForm) {
        if (checkinForm.isNewPoAdded()) {
            checkinForm.getPoOrderDetails().clear();
        }
    }

    /**
     * Method for adding a new PO checkin line not in the corresponding Order document to the Checkin Document
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addPoOrderDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        loggerAc.debug("Inside add Worksheet Counter method");
        CheckinReceiveForm ccrForm = (CheckinReceiveForm) form;
        OrderDetailVo orderDetailVo = ccrForm.getNewOrderDetailVo();

        if (CheckinValidator.validateNewOrderDetailVo(orderDetailVo)
                && validateManufNumber(orderDetailVo)) {
            ccrForm.addPoOrderDetails(orderDetailVo);
            ccrForm.setNewOrderDetailVo(new OrderDetailVo());
        }
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    /**
     * Method for removing a PO line item from the checkin document
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deletePoOrderDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        loggerAc.debug("Inside Worksheet Counter delete method");
        CheckinReceiveForm ccrForm = (CheckinReceiveForm) form;
        int index = getSelectedLine(request);
        ccrForm.getPoOrderDetails().remove(index);
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    /**
     * validates the selected manufacturer number while creating a new PO Item
     * 
     * @param vo
     * @return
     */
    private boolean validateManufNumber(OrderDetailVo vo) {
        CatalogItem citem = this.checkinOrderService().getCatalogItem(vo.getManufacturerNumber(), vo
                .getItemNumber());
        if (citem != null) {
            if (!StringUtils.isEmpty(citem.getStockId()) && ObjectUtils.isNotNull(citem.getStock())) {
                vo.setCatalogItem(citem);
            }
            else {
                Stock stock = this.stockService().getStockByDistributorNumber(vo.getItemNumber());
                if (stock == null) {

                    GlobalVariables.getMessageMap().putError(
                            MMConstants.CheckinDoc.NEW_ORDER_DETAIL
                                    + MMConstants.CheckinDoc.NEW_PO_ITEM_NUMBER,
                            MMKeyConstants.CheckinDoc.INVALID_DISTRIBUTOR_NUMBER,
                            new String[] { vo.getItemNumber() });
                    return false;
                }
                citem.setStock(stock);
                vo.setCatalogItem(citem);
            }
            return true;
        }

        GlobalVariables.getMessageMap()
                .putError(
                        MMConstants.CheckinDoc.NEW_ORDER_DETAIL
                                + MMConstants.CheckinDoc.NEW_PO_ITEM_NUMBER,
                        MMKeyConstants.CheckinDoc.INVALID_DISTRIBUTOR_NUMBER,
                        new String[] { vo.getItemNumber() });

        return false;
    }

    /**
     * method for removing a checkin line item
     * 
     * @param selIndex
     * @param checkinDoc
     * @param checkinForm
     */
    private void deleteCheckinDetail(Integer selIndex, CheckinDocument checkinDoc) {
        CheckinDetail cdetail = checkinDoc.getCheckinDetails().get(selIndex);

        if (cdetail.getRejectedItemQty() != null && cdetail.getRejectedItemQty() > 0) {
            cdetail.setAcceptedItemQty(0);
            cdetail.setBin(null);
            cdetail.setBinId(null);
            return;
        }

        if (cdetail.isRentalsCheckedIn()) {

            for (StagingRental cc : cdetail.getCheckinRentals()) {
                cc.setCheckinDetail(null);
                cc.setCheckinDetailId(null);
            }
            cdetail.getRentals().clear();
        }
        checkinDoc.getCheckinDetails().remove(cdetail);
        cdetail.getOrderDetail().getCheckinDetails().remove(cdetail);
    }


    /**
     * method for adding a new checkin line to the checkin document
     * 
     * @param selIndex
     * @param checkinDoc
     * @param checkinForm
     */
    private void addNewRow(Integer orderLinenumber, CheckinReceiveForm checkinForm) {
        CheckinDocument checkinDoc = checkinForm.getCheckinDoc();
        List<CheckinDetail> addCheckinDetails = checkinForm.getAddCheckinDetails();
        CheckinDetail addCheckinDetail = findSelectedNewLocation(orderLinenumber, addCheckinDetails);
        List<OrderDetail> orderDetails = checkinDoc.getOrderDocument().getOrderDetails();
        OrderDetail orderDetail = findSelectedOrderDetail(orderLinenumber, orderDetails);
        CheckinDetail checkinDetail = new CheckinDetail();

        if (orderDetail != null && addCheckinDetail.getBinId() != null) {
            checkinDetail.setCheckinDocumentNumber(checkinDoc.getDocumentNumber());
            checkinDetail.setStockId(orderDetail.getCatalogItem().getStockId());
            checkinDetail.setStock(orderDetail.getCatalogItem().getStock());
            checkinDetail.setPoId(orderDetail.getPoId());
            checkinDetail.setRejectedItemQty(0);
            checkinDetail.setOrderDetailId(orderDetail.getOrderDetailId());
            checkinDetail.setOrderDetail(orderDetail);
            checkinDetail.setBinId(addCheckinDetail.getBinId());
            checkinDetail.setAcceptedItemQty(addCheckinDetail.getAcceptedItemQty() == null ? 0
                    : addCheckinDetail.getAcceptedItemQty());
            boolean found = false;
            List<CheckinDetail> currList = checkinDoc.getCheckinDetails();
            for (CheckinDetail checkinDet : currList) {
                if (orderLinenumber.equals(checkinDet.getOrderDetailId())
                        && addCheckinDetail.getBinId().equals(checkinDet.getBinId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                orderDetail.getCheckinDetails().add(checkinDetail);
                checkinDoc.getCheckinDetails().add(checkinDetail);
                if (orderDetail.getCatalogItem().getStock().isRental()) {
                    checkinForm.getAddSerialNumbers().add(new StagingRental());
                    checkinForm.getSelectedItems().add(new String());
                }
            }
        }
        addCheckinDetail.setBinId(null);
        addCheckinDetail.setBinZoneDesc(null);
        addCheckinDetail.setBin(null);
        addCheckinDetail.setAcceptedItemQty(null);
        addCheckinDetail.setAvailableQty(null);
    }

    /**
     * @param orderLinenumber
     * @param orderDetails
     * @return
     */
    private OrderDetail findSelectedOrderDetail(Integer orderLinenumber,
            List<OrderDetail> orderDetails) {
        OrderDetail orderDetail = null;
        for (OrderDetail orderDet : orderDetails) {
            if (orderLinenumber.equals(orderDet.getOrderDetailId())) {
                orderDetail = orderDet;
                break;
            }
        }
        return orderDetail;
    }

    /**
     * @param orderLinenumber
     * @param addCheckinDetails
     * @return
     */
    private CheckinDetail findSelectedNewLocation(Integer orderLinenumber,
            List<CheckinDetail> addCheckinDetails) {
        CheckinDetail addCheckinDetail = null;
        for (CheckinDetail checkinDetail : addCheckinDetails) {
            if (orderLinenumber.equals(checkinDetail.getOrderDetailId())) {
                addCheckinDetail = checkinDetail;
                break;
            }
        }
        return addCheckinDetail;
    }
}
