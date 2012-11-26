/*
 * Copyright 2011 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl2.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.ext.mm.b2b.cxml.invoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.StoresPersistableBusinessObject;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.TransactionalServiceUtils;

/**
 * @author harsha07
 */
public class B2bInvoiceDetail extends StoresPersistableBusinessObject {
    private static final long serialVersionUID = 8949997770689899564L;
    private Integer invoiceDetailId;
    private String payloadId;
    private Integer lineNumber;
    private Integer quantity;
    private String unitOfMeasure;
    private MMDecimal unitPrice;
    private String supplierPartId;
    private MMDecimal subTotalAmount;
    private MMDecimal specialHandlingAmount;
    private MMDecimal shippingAmount;
    private MMDecimal discountAmount;
    private MMDecimal grossAmount;
    private MMDecimal taxAmount;
    private MMDecimal netAmount;
    private MMDecimal depositAmount;
    private Integer orderDetailId;
    private String orderDocId;
    private boolean matched;
    private boolean glProcessed;
    private transient OrderDetail orderDetail;
    private List<B2bInvoiceAccount> accounts = new ArrayList<B2bInvoiceAccount>();

    public OrderDetail matchingOrderDetail() {
        if (ObjectUtils.isNull(this.orderDetail)) {
            this.orderDetail = findBySuppierPartId();
            if (ObjectUtils.isNull(orderDetail)) {
                this.orderDetail = findByItemLineNumber();
            }
        }
        return this.orderDetail;
    }

    /**
     * @param orderDetail
     * @return
     */
    private OrderDetail findBySuppierPartId() {
        OrderDetail orderDetail = null;
        if (StringUtils.isNotBlank(this.orderDocId) && StringUtils.isNotBlank(this.supplierPartId)) {
            HashMap<String, String> fieldValues = new HashMap<String, String>();
            fieldValues.put("orderDocumentNbr", this.orderDocId);
            fieldValues.put("distributorNbr", this.supplierPartId);
            Collection matching = MMServiceLocator.getBusinessObjectService().findMatching(
                    OrderDetail.class, fieldValues);
            if (matching != null) {
                orderDetail = (OrderDetail) TransactionalServiceUtils
                        .retrieveFirstAndExhaustIterator(matching.iterator());
            }
        }
        return orderDetail;
    }

    private OrderDetail findByItemLineNumber() {
        OrderDetail orderDetail = null;
        if (StringUtils.isNotBlank(this.orderDocId) && this.lineNumber != null) {
            HashMap<String, Object> fieldValues = new HashMap<String, Object>();
            fieldValues.put("orderDocumentNbr", this.orderDocId);
            fieldValues.put("itemLineNumber", this.lineNumber);
            Collection matching = MMServiceLocator.getBusinessObjectService().findMatching(
                    OrderDetail.class, fieldValues);
            if (matching != null) {
                orderDetail = (OrderDetail) TransactionalServiceUtils
                        .retrieveFirstAndExhaustIterator(matching.iterator());
            }
        }
        return orderDetail;
    }

    /**
     * Gets the lineNumber property
     * 
     * @return Returns the lineNumber
     */
    public Integer getLineNumber() {
        return this.lineNumber;
    }

    /**
     * Sets the lineNumber property value
     * 
     * @param lineNumber The lineNumber to set
     */
    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Gets the quantity property
     * 
     * @return Returns the quantity
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * Sets the quantity property value
     * 
     * @param quantity The quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the unitOfMeasure property
     * 
     * @return Returns the unitOfMeasure
     */
    public String getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    /**
     * Sets the unitOfMeasure property value
     * 
     * @param unitOfMeasure The unitOfMeasure to set
     */
    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    /**
     * Gets the unitPrice property
     * 
     * @return Returns the unitPrice
     */
    public MMDecimal getUnitPrice() {
        return this.unitPrice;
    }

    /**
     * Sets the unitPrice property value
     * 
     * @param unitPrice The unitPrice to set
     */
    public void setUnitPrice(MMDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets the supplierPartId property
     * 
     * @return Returns the supplierPartId
     */
    public String getSupplierPartId() {
        return this.supplierPartId;
    }

    /**
     * Sets the supplierPartId property value
     * 
     * @param supplierPartId The supplierPartId to set
     */
    public void setSupplierPartId(String supplierPartId) {
        this.supplierPartId = supplierPartId;
    }

    /**
     * Gets the subTotalAmount property
     * 
     * @return Returns the subTotalAmount
     */
    public MMDecimal getSubTotalAmount() {
        if (this.subTotalAmount == null && this.unitPrice != null && this.quantity != null) {
            return this.unitPrice.multiply(new MMDecimal(this.quantity));
        }
        return this.subTotalAmount != null ? this.subTotalAmount : MMDecimal.ZERO;
    }

    /**
     * Sets the subTotalAmount property value
     * 
     * @param subTotalAmount The subTotalAmount to set
     */
    public void setSubTotalAmount(MMDecimal subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    /**
     * Gets the specialHandlingAmount property
     * 
     * @return Returns the specialHandlingAmount
     */
    public MMDecimal getSpecialHandlingAmount() {
        return this.specialHandlingAmount;
    }

    /**
     * Sets the specialHandlingAmount property value
     * 
     * @param specialHandlingAmount The specialHandlingAmount to set
     */
    public void setSpecialHandlingAmount(MMDecimal specialHandlingAmount) {
        this.specialHandlingAmount = specialHandlingAmount;
    }

    /**
     * Gets the shippingAmount property
     * 
     * @return Returns the shippingAmount
     */
    public MMDecimal getShippingAmount() {
        return this.shippingAmount;
    }

    /**
     * Sets the shippingAmount property value
     * 
     * @param shippingAmount The shippingAmount to set
     */
    public void setShippingAmount(MMDecimal shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    /**
     * Gets the discountAmount property
     * 
     * @return Returns the discountAmount
     */
    public MMDecimal getDiscountAmount() {
        return this.discountAmount;
    }

    /**
     * Sets the discountAmount property value
     * 
     * @param discountAmount The discountAmount to set
     */
    public void setDiscountAmount(MMDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * Gets the grossAmount property
     * 
     * @return Returns the grossAmount
     */
    public MMDecimal getGrossAmount() {
        if (this.grossAmount == null) {
            return getSubTotalAmount();
        }
        return this.grossAmount;
    }

    /**
     * Sets the grossAmount property value
     * 
     * @param grossAmount The grossAmount to set
     */
    public void setGrossAmount(MMDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    /**
     * Gets the taxAmount property
     * 
     * @return Returns the taxAmount
     */
    public MMDecimal getTaxAmount() {
        return this.taxAmount;
    }

    /**
     * Sets the taxAmount property value
     * 
     * @param taxAmount The taxAmount to set
     */
    public void setTaxAmount(MMDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * Gets the netAmount property
     * 
     * @return Returns the netAmount
     */
    public MMDecimal getNetAmount() {
        if (this.netAmount == null) {
            return getGrossAmount();
        }
        return this.netAmount;
    }

    /**
     * Sets the netAmount property value
     * 
     * @param netAmount The netAmount to set
     */
    public void setNetAmount(MMDecimal netAmount) {
        this.netAmount = netAmount;
    }

    /**
     * Gets the depositAmount property
     * 
     * @return Returns the depositAmount
     */
    public MMDecimal getDepositAmount() {
        return this.depositAmount;
    }

    /**
     * Sets the depositAmount property value
     * 
     * @param depositAmount The depositAmount to set
     */
    public void setDepositAmount(MMDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    /**
     * Gets the payloadId property
     * 
     * @return Returns the payloadId
     */
    public String getPayloadId() {
        return this.payloadId;
    }

    /**
     * Sets the payloadId property value
     * 
     * @param payloadId The payloadId to set
     */
    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    /**
     * Gets the invoiceDetailId property
     * 
     * @return Returns the invoiceDetailId
     */
    public Integer getInvoiceDetailId() {
        return this.invoiceDetailId;
    }

    /**
     * Sets the invoiceDetailId property value
     * 
     * @param invoiceDetailId The invoiceDetailId to set
     */
    public void setInvoiceDetailId(Integer invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    /**
     * Gets the orderDetailId property
     * 
     * @return Returns the orderDetailId
     */
    public Integer getOrderDetailId() {
        return this.orderDetailId;
    }

    /**
     * Sets the orderDetailId property value
     * 
     * @param orderDetailId The orderDetailId to set
     */
    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    /**
     * Gets the orderDocId property
     * 
     * @return Returns the orderDocId
     */
    public String getOrderDocId() {
        return this.orderDocId;
    }

    /**
     * Sets the orderDocId property value
     * 
     * @param orderDocId The orderDocId to set
     */
    public void setOrderDocId(String orderDocId) {
        this.orderDocId = orderDocId;
    }

    /**
     * Gets the matched property
     * 
     * @return Returns the matched
     */
    public boolean isMatched() {
        return this.matched;
    }

    /**
     * Sets the matched property value
     * 
     * @param matched The matched to set
     */
    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    /**
     * Gets the glProcessed property
     * 
     * @return Returns the glProcessed
     */
    public boolean isGlProcessed() {
        return this.glProcessed;
    }

    /**
     * Sets the glProcessed property value
     * 
     * @param glProcessed The glProcessed to set
     */
    public void setGlProcessed(boolean glProcessed) {
        this.glProcessed = glProcessed;
    }

    /**
     * Gets the accounts property
     * 
     * @return Returns the accounts
     */
    public List<B2bInvoiceAccount> getAccounts() {
        return this.accounts;
    }

    /**
     * Sets the accounts property value
     * 
     * @param accounts The accounts to set
     */
    public void setAccounts(List<B2bInvoiceAccount> accounts) {
        this.accounts = accounts;
    }

    public void distributeAdditionalCharge(MMDecimal totalAmt, MMDecimal diff) {
        MMDecimal netamt = getNetAmount();
        double ratio = netamt.doubleValue() / totalAmt.doubleValue();
        double component = diff.doubleValue() * ratio;
        setNetAmount(netamt.add(new MMDecimal(component)));
    }
}
