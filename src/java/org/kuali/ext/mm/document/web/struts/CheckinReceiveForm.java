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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.businessobject.CheckinDetail;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.util.MMUtil;


public class CheckinReceiveForm extends RentalTrackingFormBase {

    /**
     *
     */
    private static final long serialVersionUID = 5775950459057852298L;
    private Integer acceptedItemQty;
    private OrderDetailVo newOrderDetailVo = null;
    private List<OrderDetailVo> poOrderDetails = new ArrayList<OrderDetailVo>(0);
    private boolean onlyLineViewed = false;
    private List<CheckinDetail> addCheckinDetails = new ArrayList<CheckinDetail>();
    
    public CheckinReceiveForm() {
        super();
        setDocument(new CheckinDocument());
        setDocTypeName(MMConstants.CHECKIN_DOC_TYPE);

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

    public CheckinDocument getCheckinDoc() {
        return (CheckinDocument) getDocument();
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
     * Gets the addCheckinDetails property
     * 
     * @return Returns the addCheckinDetails
     */
    public List<CheckinDetail> getAddCheckinDetails() {
        if (this.addCheckinDetails.isEmpty()) {
            List<OrderDetail> orderDetails = getCheckinDoc().getOrderDocument().getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                CheckinDetail newCheckinDetail = new CheckinDetail();
                newCheckinDetail.setOrderDetailId(orderDetail.getOrderDetailId());
                this.addCheckinDetails.add(newCheckinDetail);
            }
        }
        return this.addCheckinDetails;
    }

    /**
     * Sets the addCheckinDetails property value
     * 
     * @param addCheckinDetails The addCheckinDetails to set
     */
    public void setAddCheckinDetails(List<CheckinDetail> addCheckinDetails) {
        this.addCheckinDetails = addCheckinDetails;
    }

    public CheckinDetail getAddCheckinDetail(Integer pos) {
        if (this.addCheckinDetails.get(pos) == null) {
            this.addCheckinDetails.add(pos, new CheckinDetail());
        }
        return this.addCheckinDetails.get(pos);
    }
}
