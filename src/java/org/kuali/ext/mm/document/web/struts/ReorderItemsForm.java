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

import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.OrderDetailForReorder;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReOrderDocument;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;


public class ReorderItemsForm extends KualiTransactionalDocumentFormBase {

	/**
     *
     */
    private static final long serialVersionUID = 4071051043743096608L;
    private OrderDetailForReorder newOrderDetail = new OrderDetailForReorder();
	private List<OrderDetailForReorder> poOrderDetails = new ArrayList<OrderDetailForReorder>(0);
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>(0);

	public OrderDetailForReorder getNewOrderDetail() {
        return this.newOrderDetail;
    }

    public void setNewOrderDetail(OrderDetailForReorder newOrderDetail) {
        this.newOrderDetail = newOrderDetail;
    }

    public void addNewOrderDetail(){
        this.newOrderDetail = new OrderDetailForReorder();
    }

    public List<OrderDetail> getOrderDetails() {
        return this.orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        this.orderDetails.add(orderDetail);
    }

	public ReorderItemsForm() {
		super();
		setDocument(new ReOrderDocument());
		setDocTypeName(MMConstants.ReOrderDocument.STORES_REORDER_DOCUMENT);
	}

	public ReOrderDocument getOrderDocument() {
		return (ReOrderDocument) getDocument();
	}

	public List<OrderDetailForReorder> getPoOrderDetails() {
		return poOrderDetails;
	}

	public void setPoOrderDetails(List<OrderDetailForReorder> poOrderDetails) {
		this.poOrderDetails = poOrderDetails;
	}

	public void addPoOrderDetails(OrderDetailForReorder orderDetailVo) {
		this.poOrderDetails.add(orderDetailVo);
	}

	@Override
	public void populate(HttpServletRequest request) {
		super.populate(request);
	}

}