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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.StoresPersistableBusinessObject;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.gl.GlConstants;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.sys.batch.service.B2bInvoiceService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.util.ObjectUtils;


/**
 * @author harsha07
 */
/**
 * Super class for all invoices that are accepted in the system. Defines commons fields and methods across all invoice types
 */
public abstract class B2bInvoice extends StoresPersistableBusinessObject {
    private String payloadId;
    private String purpose;
    private String operation;
    private String invoiceId;
    private Timestamp invoiceDate;
    private String orderDocId;
    private String orderCxmlPayloadId;

    private String punchOutVendorId;
    private MMDecimal subTotalAmount;
    private MMDecimal taxAmount;
    private MMDecimal specialHandlingAmount;
    private MMDecimal shippingAmount;
    private MMDecimal grossAmount;
    private MMDecimal discountAmount;
    private MMDecimal netAmount;
    private MMDecimal depositAmount;
    private MMDecimal dueAmount;
    private boolean processed;
    private boolean matched;
    private boolean glProcessed;
    private transient OrderDocument orderDocument;

    private List<B2bInvoiceDetail> details = new ArrayList<B2bInvoiceDetail>();

    public abstract B2bInvoiceStatus validate();

    public abstract void process();

    public OrderDocument matchingOrder() {
        if (ObjectUtils.isNull(this.orderDocument) && StringUtils.isNotBlank(this.orderDocId)) {
            String orderDocNumber = this.orderDocId;
            if (this.orderDocId.contains("-")) {
                orderDocNumber = this.orderDocId.split("-")[0];
            }
            this.orderDocument = MMServiceLocator.getBusinessObjectService()
                    .findBySinglePrimaryKey(OrderDocument.class, orderDocNumber);
        }
        return orderDocument;
    }

    /**
     * Gets the purpose property
     * 
     * @return Returns the purpose
     */
    public String getPurpose() {
        return this.purpose;
    }

    /**
     * Sets the purpose property value
     * 
     * @param purpose The purpose to set
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * Gets the operation property
     * 
     * @return Returns the operation
     */
    public String getOperation() {
        return this.operation;
    }

    /**
     * Sets the operation property value
     * 
     * @param operation The operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * Gets the invoiceId property
     * 
     * @return Returns the invoiceId
     */
    public String getInvoiceId() {
        return this.invoiceId;
    }

    /**
     * Sets the invoiceId property value
     * 
     * @param invoiceId The invoiceId to set
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Gets the invoiceDate property
     * 
     * @return Returns the invoiceDate
     */
    public Timestamp getInvoiceDate() {
        if (this.invoiceDate == null) {
            return new Timestamp(new Date().getTime());
        }
        return this.invoiceDate;
    }

    /**
     * Sets the invoiceDate property value
     * 
     * @param invoiceDate The invoiceDate to set
     */
    public void setInvoiceDate(Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
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
     * Gets the orderCxmlPayloadId property
     * 
     * @return Returns the orderCxmlPayloadId
     */
    public String getOrderCxmlPayloadId() {
        return this.orderCxmlPayloadId;
    }

    /**
     * Sets the orderCxmlPayloadId property value
     * 
     * @param orderCxmlPayloadId The orderCxmlPayloadId to set
     */
    public void setOrderCxmlPayloadId(String orderCxmlPayloadId) {
        this.orderCxmlPayloadId = orderCxmlPayloadId;
    }


    /**
     * Gets the details property
     * 
     * @return Returns the details
     */
    public List<B2bInvoiceDetail> getDetails() {
        return this.details;
    }

    /**
     * Sets the details property value
     * 
     * @param details The details to set
     */
    public void setDetails(List<B2bInvoiceDetail> details) {
        this.details = details;
    }

    /**
     * Gets the subtotalAmount property
     * 
     * @return Returns the subtotalAmount
     */
    public MMDecimal getSubTotalAmount() {
        return this.subTotalAmount != null ? this.subTotalAmount : MMDecimal.ZERO;
    }

    /**
     * Sets the subtotalAmount property value
     * 
     * @param subtotalAmount The subtotalAmount to set
     */
    public void setSubTotalAmount(MMDecimal subtotalAmount) {
        this.subTotalAmount = subtotalAmount;
    }

    /**
     * Gets the tax property
     * 
     * @return Returns the tax
     */
    public MMDecimal getTaxAmount() {
        return this.taxAmount;
    }

    /**
     * Sets the tax property value
     * 
     * @param tax The tax to set
     */
    public void setTaxAmount(MMDecimal tax) {
        this.taxAmount = tax;
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
     * Gets the invoiceDetailDiscount property
     * 
     * @return Returns the invoiceDetailDiscount
     */
    public MMDecimal getDiscountAmount() {
        return this.discountAmount;
    }

    /**
     * Sets the invoiceDetailDiscount property value
     * 
     * @param invoiceDetailDiscount The invoiceDetailDiscount to set
     */
    public void setDiscountAmount(MMDecimal invoiceDetailDiscount) {
        this.discountAmount = invoiceDetailDiscount;
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
     * Gets the dueAmount property
     * 
     * @return Returns the dueAmount
     */
    public MMDecimal getDueAmount() {
        if (this.dueAmount == null) {
            return getNetAmount();
        }
        return this.dueAmount;
    }

    /**
     * Sets the dueAmount property value
     * 
     * @param dueAmount The dueAmount to set
     */
    public void setDueAmount(MMDecimal dueAmount) {
        this.dueAmount = dueAmount;
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
     * Gets the punchOutVendorId property
     * 
     * @return Returns the punchOutVendorId
     */
    public String getPunchOutVendorId() {
        return this.punchOutVendorId;
    }

    /**
     * Sets the punchOutVendorId property value
     * 
     * @param punchOutVendorId The punchOutVendorId to set
     */
    public void setPunchOutVendorId(String punchOutVendorId) {
        this.punchOutVendorId = punchOutVendorId;
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
     * Gets the processed property
     * 
     * @return Returns the processed
     */
    public boolean isProcessed() {
        return this.processed;
    }

    /**
     * Sets the processed property value
     * 
     * @param processed The processed to set
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
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

    protected B2bInvoiceStatus validateCommon() {
        if (StringUtils.isBlank(getOrderDocId())) {
            return new B2bInvoiceStatus(false, "406", "Not Acceptable",
                "Order Id information is missing.");
        }
        if (ObjectUtils.isNull(matchingOrder())) {
            return new B2bInvoiceStatus(false, "417", "Expectation failed.", "Order Id: "
                    + getOrderDocId() + " is not a valid order in the system.");
        }
        if (getDetails().isEmpty()) {
            return new B2bInvoiceStatus(false, "417", "Expectation failed.",
                "No valid invoice detail found.");

        }
        return new B2bInvoiceStatus();
    }

    /**
     * @param order
     * @param warehouse
     * @return
     */
    protected List<FinancialAccountingLine> createWarehouseAcctLines(OrderDocument order,
            Warehouse warehouse) {
        FinancialAccountingLine incFinLine = createFinancialAccountingLine(warehouse
                .getIncomeAccount(), getDueAmount().kualiDecimalValue(), order.getDocumentNumber());
        List<FinancialAccountingLine> warehouseAccountLines = new ArrayList<FinancialAccountingLine>();
        warehouseAccountLines.add(incFinLine);
        return warehouseAccountLines;
    }

    /**
     * @return
     */
    protected List<FinancialAccountingLine> createCustomerAccountLines() {
        List<FinancialAccountingLine> customerAcctLines = new ArrayList<FinancialAccountingLine>();

        for (B2bInvoiceDetail invoiceDetail : getDetails()) {
            MMDecimal invoiceLineAmount = invoiceDetail.getNetAmount();
            if (invoiceLineAmount == null || invoiceLineAmount.isZero() || isGlProcessed()) {
                continue;
            }
            MMDecimal allocated = MMDecimal.ZERO;
            List<B2bInvoiceAccount> accounts = invoiceDetail.getAccounts();
            FinancialAccountingLine financialAccountingLine = null;
            for (B2bInvoiceAccount invoiceAccount : accounts) {
                Accounts matchingAccount = invoiceAccount.matchingAccount();
                if (matchingAccount != null) {
                    financialAccountingLine = createFinancialAccountingLine(matchingAccount);
                    financialAccountingLine.setDocumentNumber(getOrderDocId());
                    MMDecimal amount = new MMDecimal(invoiceLineAmount.doubleValue() * 0.01d
                            * matchingAccount.getAccountPct().doubleValue());
                    financialAccountingLine.setAmount(amount.kualiDecimalValue());
                    allocated = allocated.add(amount);
                    customerAcctLines.add(financialAccountingLine);
                }
            }
            if (financialAccountingLine != null) {
                // round the difference to last account line in the item
                financialAccountingLine.setAmount(financialAccountingLine.getAmount().add(
                        invoiceLineAmount.subtract(allocated).kualiDecimalValue()));
            }
            invoiceDetail.setGlProcessed(true);
        }

        // Round the difference and adjust to the last financial account line
        KualiDecimal total = KualiDecimal.ZERO;
        for (FinancialAccountingLine financialAccountingLine : customerAcctLines) {
            total = total.add(financialAccountingLine.getAmount());
        }

        if (getDueAmount().kualiDecimalValue().subtract(total).isNonZero()) {
            KualiDecimal diff = getDueAmount().kualiDecimalValue().subtract(total);
            FinancialAccountingLine last = customerAcctLines.get(customerAcctLines.size() - 1);
            last.setAmount(last.getAmount().add(diff));
        }
        return customerAcctLines;
    }

    protected FinancialAccountingLine createFinancialAccountingLine(
            WarehouseAccounts incomeAccount, KualiDecimal chargeAmt, String documentNumber) {
        FinancialAccountingLine finAcctLine = new FinancialAccountingLine();
        finAcctLine.setAccountNumber(incomeAccount.getAccountNbr());
        finAcctLine.setAmount(chargeAmt);
        finAcctLine.setBalanceTypeCode("AC");
        finAcctLine.setChartOfAccountsCode(incomeAccount.getFinCoaCd());
        finAcctLine.setFinancialDocumentLineDescription("Sale Generated");
        finAcctLine.setFinancialDocumentLineTypeCode(MMConstants.FIN_ACCT_LINE_TYP_FROM);
        finAcctLine.setFinancialObjectCode(incomeAccount.getFinObjectCd());
        finAcctLine.setFinancialSubObjectCode(incomeAccount.getFinSubObjCd());
        finAcctLine.setObjectBudgetOverride(false);
        finAcctLine.setObjectBudgetOverrideNeeded(false);
        finAcctLine.setOrganizationReferenceId("");
        finAcctLine.setOverrideCode("");
        finAcctLine.setPostingYear(SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .getFinancialUniversityDateService().getCurrentFiscalYear());
        finAcctLine.setProjectCode(incomeAccount.getProjectCd());
        finAcctLine.setReferenceNumber("");
        finAcctLine.setReferenceOriginCode(GlConstants.getFinancialSystemOriginCode());
        finAcctLine.setReferenceTypeCode("");
        finAcctLine.setSalesTaxRequired(false);
        finAcctLine.setSubAccountNumber(incomeAccount.getSubAcctNbr());
        finAcctLine.setDocumentNumber(documentNumber);
        finAcctLine.setFinancialDocumentTypeCode(MMConstants.OrderDocument.STORES_ORDER_DOCUMENT);
        return finAcctLine;
    }

    public FinancialAccountingLine createFinancialAccountingLine(Accounts account) {
        FinancialAccountingLine finAcctLine = new FinancialAccountingLine();
        finAcctLine.setAccountNumber(account.getAccountNbr());
        finAcctLine.setBalanceTypeCode(GlConstants.BALANCE_TYPE_ACTUAL);
        finAcctLine.setChartOfAccountsCode(account.getFinCoaCd());
        finAcctLine.setFinancialObjectCode(account.getFinObjectCd());
        finAcctLine.setFinancialSubObjectCode(account.getFinSubObjectCd());
        finAcctLine.setProjectCode(account.getProjectCd());
        finAcctLine.setOrganizationReferenceId(account.getDepartmentReferenceText());
        finAcctLine.setSubAccountNumber(account.getSubAcctNbr());
        finAcctLine.setFinancialDocumentLineDescription("Stores charge/credit");
        finAcctLine.setObjectBudgetOverride(false);
        finAcctLine.setObjectBudgetOverrideNeeded(false);
        finAcctLine.setOverrideCode("");
        finAcctLine.setPostingYear(SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .getFinancialUniversityDateService().getCurrentFiscalYear());
        finAcctLine.setReferenceOriginCode(GlConstants.getFinancialSystemOriginCode());
        finAcctLine.setReferenceTypeCode("");
        finAcctLine.setSalesTaxRequired(false);
        finAcctLine.setFinancialDocumentTypeCode(MMConstants.OrderDocument.STORES_ORDER_DOCUMENT);
        return finAcctLine;
    }

    @Override
    public List buildListOfDeletionAwareLists() {
        List list = super.buildListOfDeletionAwareLists();
        list.add(getDetails());
        return list;
    }

    /**
     * This method decides if invoice has an exact match at each item level. In some cases we anticipate unit price differences and
     * excessive quantity.
     */
    public void checkMatch() {
        OrderDocument matchingOrder = matchingOrder();
        int mismatchCount = 0;
        if (ObjectUtils.isNotNull(matchingOrder)) {
            for (B2bInvoiceDetail detail : getDetails()) {
                OrderDetail orderDetail = detail.matchingOrderDetail();
                if (ObjectUtils.isNotNull(orderDetail)) {
                    // check unit price match and quantity
                    if (detail.getUnitPrice().equals(orderDetail.getOrderItemPriceAmt())
                            && Math.abs(detail.getQuantity()) <= orderDetail.getOrderItemQty()
                            && withinOrderQuantity(detail, orderDetail)) {
                        detail.setMatched(true);
                    }
                    else {
                        mismatchCount++;
                    }
                }
            }
            if (mismatchCount == 0) {
                setMatched(true);
            }
        }
    }

    public boolean withinOrderQuantity(B2bInvoiceDetail invoiceDetail, OrderDetail orderDetail) {
        if (invoiceDetail == null || orderDetail == null) {
            return false;
        }
        Integer totalInvoiced = SpringContext.getBean(B2bInvoiceService.class).getInvoicedQuantity(
                orderDetail.getOrderDetailId());
        totalInvoiced = totalInvoiced + invoiceDetail.getQuantity();
        if (Math.abs(totalInvoiced) <= orderDetail.getOrderItemQty()) {
            return true;
        }
        return false;
    }

    /**
     * This method finds out the difference between invoice DUE amount and sum of invoice detail NET amounts. Difference basically
     * includes taxes and additional charges that are applied across the complete invoice. If a difference is found then difference
     * is distributed evenly across all item details based on their contribution ratio to total amount.
     */
    public void distributeAdditionalCharges() {
        MMDecimal invoiceSum = MMDecimal.ZERO;
        for (B2bInvoiceDetail detail : getDetails()) {
            invoiceSum = invoiceSum.add(detail.getNetAmount());
        }
        if (getDueAmount().isNonZero() && !getDueAmount().equals(invoiceSum)) {
            MMDecimal diff = getDueAmount().subtract(invoiceSum);
            for (B2bInvoiceDetail detail : getDetails()) {
                detail.distributeAdditionalCharge(invoiceSum, diff);
            }
            invoiceSum = MMDecimal.ZERO;
            for (B2bInvoiceDetail detail : getDetails()) {
                invoiceSum = invoiceSum.add(detail.getNetAmount());
            }
            // if still didnt match allocate to last item in the invoice
            if (!getDueAmount().equals(invoiceSum)) {
                B2bInvoiceDetail last = getDetails().get(getDetails().size() - 1);
                last.setNetAmount(last.getNetAmount().add(getDueAmount().subtract(invoiceSum)));
            }
        }
    }
}
