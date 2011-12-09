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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.businessobject.CheckinDetail;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReceiptCorrectionDocument;
import org.kuali.ext.mm.util.MMUtil;


public class ReceiptCorrectionForm extends RentalTrackingFormBase {

    /**
     *
     */
    private static final long serialVersionUID = -1614719317754158811L;
    private Integer acceptedItemQty;
    private OrderDetailVo newOrderDetailVo = null;
    private List<OrderDetailVo> poOrderDetails = new ArrayList<OrderDetailVo>(0);
    private List<CheckinDetail> checkinDetails = new ArrayList<CheckinDetail>(0);

    private boolean onlyLineViewed = false;
    
    public ReceiptCorrectionForm() {
        super();
        setDocument(new ReceiptCorrectionDocument());
        setDocTypeName(MMConstants.ReceiptCorrection.DOCUMENT_TYPE);

    }
    
    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
    }

    public boolean isOnlyLineViewed() {
        return onlyLineViewed;
    }

    public void setOnlyLineViewed(boolean onlyLineViewed) {
        this.onlyLineViewed = onlyLineViewed;
    }

    public List<CheckinDetail> getCheckinDetails() {
        return checkinDetails;
    }

    public void setCheckinDetails(List<CheckinDetail> checkinDetails) {
        this.checkinDetails = checkinDetails;
    }

    private CheckinDetail newCheckinDetail = new CheckinDetail();

    public CheckinDetail getNewCheckinDetail() {
        return newCheckinDetail;
    }

    public void setNewCheckinDetail(CheckinDetail newCheckinDetail) {
        this.newCheckinDetail = newCheckinDetail;
    }

    public ReceiptCorrectionDocument getReceiptCorrectionDocument() {
        return (ReceiptCorrectionDocument) getDocument();
    }

    public List<OrderDetailVo> getPoOrderDetails() {
        return poOrderDetails;
    }

    public void setPoOrderDetails(List<OrderDetailVo> poOrderDetails) {
        this.poOrderDetails = poOrderDetails;
    }

    public void addPoOrderDetails(OrderDetailVo orderDetailVo) {
        this.poOrderDetails.add(orderDetailVo);
    }

    public void addCheckinDetails(CheckinDetail cdetail) {
        this.checkinDetails.add(cdetail);
    }

    public void removeCheckinDetail(CheckinDetail cdetail) {
        this.checkinDetails.remove(cdetail);
    }

    public void clearCheckinDetails() {
        this.checkinDetails.clear();
    }

    public Integer getAcceptedItemQty() {
        return acceptedItemQty;
    }

    public void setAcceptedItemQty(Integer acceptedItemQty) {
        this.acceptedItemQty = acceptedItemQty;
    }

    public OrderDetailVo getNewOrderDetailVo() {
        return newOrderDetailVo;
    }

    public void setNewOrderDetailVo(OrderDetailVo newOrderDetailVo) {
        this.newOrderDetailVo = newOrderDetailVo;
    }

    public boolean isNewPoAdded() {
        return !MMUtil.isCollectionEmpty(this.poOrderDetails);
    }

    /**
     * Gets the checkedInOrderDetails property
     * 
     * @return Returns the checkedInOrderDetails
     */
    public List<OrderDetail> getCheckedInOrderDetails() {
        List<OrderDetail> checkedInOrderDetails = new ArrayList<OrderDetail>();
        ReceiptCorrectionDocument receiptCorrDoc = getReceiptCorrectionDocument();
        List<CheckinDetail> checkinDetailsList = receiptCorrDoc.getCheckinDetails();
        HashSet<Integer> orderDetailIds = new HashSet<Integer>();
        for (CheckinDetail checkinDetail : checkinDetailsList) {
            if (orderDetailIds.add(checkinDetail.getOrderDetailId())) {
                checkedInOrderDetails.add(checkinDetail.getOrderDetail());
            }
        }
        return checkedInOrderDetails;
    }

}
