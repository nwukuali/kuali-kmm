/**
 * 
 */
package org.kuali.ext.mm.b2b.cxml.transform;

import org.kuali.ext.mm.b2b.cxml.invoice.*;
import org.kuali.ext.mm.b2b.cxml.types.*;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;
import org.kuali.ext.mm.businessobject.CxmlInvoice;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.List;
import java.lang.Object;

/**
 * @author harsha07
 */
public class CxmlToB2bInvoice implements CxmlTransformer<B2bInvoice, CXML> {
    protected static final String CREDIT_MEMO = "creditMemo";
    protected static final String OPERATION_NEW = "new";
    protected static final String INV_STANDARD = "standard";
    protected static final String LINE_CREDIT_MEMO = "lineLevelCreditMemo";

    /**
     * @see org.kuali.ext.mm.b2b.cxml.transform.CxmlTransformer#transform(java.lang.Object)
     */
    @Override
    public B2bInvoice transform(CXML cxml, Object... options) {
        Request request = cxml.getRequest();
        Object value = request.getCxmlRequests().getValue();
        B2bInvoice b2bInvoice = null;

        if (InvoiceDetailRequest.class.isAssignableFrom(value.getClass())) {
            InvoiceDetailRequest invoice = (InvoiceDetailRequest) value;
            InvoiceDetailRequestHeader idrh = invoice.getInvoiceDetailRequestHeader();
            String purpose = idrh.getPurpose().toLowerCase();
            String operation = idrh.getOperation();

            if ("delete".equalsIgnoreCase(operation)) {
                return createCancellationInvoice(cxml, idrh, purpose, operation);
            }
            if ((b2bInvoice = getSupportedInvoiceObject(idrh, purpose, operation)) != null) {
                b2bInvoice.setPayloadId(cxml.getPayloadID());
                b2bInvoice.setPurpose(purpose);
                b2bInvoice.setOperation(operation);
                b2bInvoice.setInvoiceId(idrh.getInvoiceID());
                List<InvoiceDetailOrder> invoiceDetailOrders = invoice.getInvoiceDetailOrder();
                for (InvoiceDetailOrder invoiceDetailOrder : invoiceDetailOrders) {
                    InvoiceDetailOrderInfo invoiceDetailOrderInfo = invoiceDetailOrder
                            .getInvoiceDetailOrderInfo();
                    Object content = invoiceDetailOrderInfo.getContent().get(0);
                    if (OrderReference.class.isAssignableFrom(content.getClass())) {
                        OrderReference orderReference = (OrderReference) content;
                        b2bInvoice.setOrderDocId(orderReference.getOrderID());
                        DocumentReference docRef = orderReference.getDocumentReference();
                        b2bInvoice.setOrderCxmlPayloadId(docRef.getPayloadID());
                    }
                    OrderDocument orderDocument = b2bInvoice.matchingOrder();
                    String orderDocNumber = null;
                    if (ObjectUtils.isNotNull(orderDocument)) {
                        orderDocNumber = orderDocument.getDocumentNumber();
                        b2bInvoice.setOrderDocId(orderDocNumber);
                    }
                    buildB2bInvoiceDetails(b2bInvoice, invoiceDetailOrder, orderDocNumber);
                }
                InvoiceDetailSummary invoiceDetailSummary = invoice.getInvoiceDetailSummary();
                if (invoiceDetailSummary != null) {
                    extractSummaryAmounts(b2bInvoice, invoiceDetailSummary);
                }
                b2bInvoice.checkMatch();
                b2bInvoice.distributeAdditionalCharges();
            }
        }
        return b2bInvoice;
    }

    /**
     * @param b2bInvoice
     * @param invoiceDetailOrder
     * @param orderDocNumber
     */
    protected void buildB2bInvoiceDetails(B2bInvoice b2bInvoice,
            InvoiceDetailOrder invoiceDetailOrder, String orderDocNumber) {
        List<Object> invoiceItems = invoiceDetailOrder
                .getInvoiceDetailItemOrInvoiceDetailServiceItem();
        for (Object object : invoiceItems) {
            if (InvoiceDetailItem.class.isAssignableFrom(object.getClass())) {
                InvoiceDetailItem item = (InvoiceDetailItem) object;
                if (Integer.valueOf(item.getQuantity()) != 0) {
                    B2bInvoiceDetail detail = new B2bInvoiceDetail();
                    detail.setOrderDocId(orderDocNumber);
                    detail.setLineNumber(Integer.valueOf(item.getInvoiceLineNumber()));
                    detail.setQuantity((int)Double.parseDouble(item.getQuantity()));
                    detail.setUnitOfMeasure(item.getUnitOfMeasure());
                    UnitPrice unitPrice = item.getUnitPrice();
                    detail.setUnitPrice(CxmlUtil.extractDollar(unitPrice.getMoney()));
                    InvoiceDetailItemReference itemReference = item.getInvoiceDetailItemReference();
                    detail.setSupplierPartId(itemReference.getItemID().getSupplierPartID());
                    // get amounts for item
                    extractLineAmounts(detail, item);
                    // get accounting information
                    extractAccountDistribution(detail, item);
                    OrderDetail orderDetail = detail.matchingOrderDetail();
                    if (ObjectUtils.isNotNull(orderDetail)) {
                        detail.setOrderDetailId(orderDetail.getOrderDetailId());
                    }
                    b2bInvoice.getDetails().add(detail);
                }
            }
        }
    }

    /**
     * @param cxml
     * @param idrh
     * @param purpose
     * @param operation
     * @return
     */
    protected B2bInvoice createCancellationInvoice(CXML cxml, InvoiceDetailRequestHeader idrh,
            String purpose, String operation) {
        CancellationInvoice b2bInvoice = new CancellationInvoice();
        b2bInvoice.setPayloadId(cxml.getPayloadID());
        b2bInvoice.setInvoiceId(idrh.getInvoiceID());
        b2bInvoice.setPurpose(purpose);
        b2bInvoice.setOperation(operation);
        b2bInvoice.setRefPayloadId(idrh.getDocumentReference().getPayloadID());
        CxmlInvoice cxmlInvoice = b2bInvoice.matchingCxmlInvoice();
        if (ObjectUtils.isNotNull(cxmlInvoice)) {
            b2bInvoice.setPunchOutVendorId(cxmlInvoice.getPunchOutVendorId());
            b2bInvoice.setOrderDocId(cxmlInvoice.getOrderId());
        }
        StandardInvoice cancelledInvoice = b2bInvoice.matchingB2bInvoice();
        if (ObjectUtils.isNotNull(cancelledInvoice)) {
            b2bInvoice.setOrderCxmlPayloadId(cancelledInvoice.getOrderCxmlPayloadId());
            copyAndNegateAmounts(cancelledInvoice, b2bInvoice);
            List<B2bInvoiceDetail> details = cancelledInvoice.getDetails();
            for (B2bInvoiceDetail cancelDetail : details) {
                B2bInvoiceDetail detail = new B2bInvoiceDetail();
                detail.setOrderDocId(cancelledInvoice.getOrderDocId());
                detail.setLineNumber(cancelDetail.getLineNumber());
                detail.setQuantity(cancelDetail.getQuantity());
                detail.setUnitOfMeasure(cancelDetail.getUnitOfMeasure());
                detail.setUnitPrice(cancelDetail.getUnitPrice());
                detail.setSupplierPartId(cancelDetail.getSupplierPartId());
                copyAndNegateAmounts(cancelDetail, detail);
                List<B2bInvoiceAccount> accounts = cancelDetail.getAccounts();
                for (B2bInvoiceAccount invoiceAccount : accounts) {
                    cancelDetail.getAccounts().add(invoiceAccount);
                }
                OrderDetail orderDetail = cancelDetail.matchingOrderDetail();
                if (ObjectUtils.isNotNull(orderDetail)) {
                    detail.setOrderDetailId(orderDetail.getOrderDetailId());
                }
                b2bInvoice.getDetails().add(detail);
            }

        }
        return b2bInvoice;
    }

    /**
     * @param detail
     * @param item
     */
    protected void extractAccountDistribution(B2bInvoiceDetail detail, InvoiceDetailItem item) {
        List<Distribution> distributions = item.getDistribution();
        for (Distribution distribution : distributions) {
            Accounting accounting = distribution.getAccounting();
            List<AccountingSegment> accts = accounting.getAccountingSegment();
            for (AccountingSegment accountingSegment : accts) {
                B2bInvoiceAccount invoiceAccount = new B2bInvoiceAccount();
                invoiceAccount.setId(accountingSegment.getId());
                Description description = accountingSegment.getDescription();
                if (description != null && !description.getContent().isEmpty()) {
                    String acctDetailLine = description.getContent().get(0).toString();
                    if (acctDetailLine != null && acctDetailLine.contains("-")) {
                        String chartCode = acctDetailLine.split("-")[0];
                        String acctNumber = acctDetailLine.split("-")[1];
                        invoiceAccount.setChartCode(chartCode);
                        invoiceAccount.setAccountNumber(acctNumber);
                    }
                }
                detail.getAccounts().add(invoiceAccount);
            }
        }
    }

    /**
     * @param detail
     * @param item
     */
    protected void extractLineAmounts(B2bInvoiceDetail detail, InvoiceDetailItem item) {
        SubtotalAmount subtotalAmount = item.getSubtotalAmount();
        if (subtotalAmount != null) {
            detail.setSubTotalAmount(CxmlUtil.extractDollar(subtotalAmount.getMoney()));
        }

        Tax tax = item.getTax();
        if (tax != null) {
            detail.setTaxAmount(CxmlUtil.extractDollar(tax.getMoney()));
        }

        InvoiceDetailLineSpecialHandling splAmount = item.getInvoiceDetailLineSpecialHandling();
        if (splAmount != null) {
            detail.setSpecialHandlingAmount(CxmlUtil.extractDollar(splAmount.getMoney()));
        }

        InvoiceDetailLineShipping shipAmount = item.getInvoiceDetailLineShipping();
        if (shipAmount != null) {
            detail.setShippingAmount(CxmlUtil.extractDollar(shipAmount.getMoney()));
        }

        GrossAmount grossAmount = item.getGrossAmount();
        if (grossAmount != null) {
            detail.setGrossAmount(CxmlUtil.extractDollar(grossAmount.getMoney()));
        }

        NetAmount netAmount = item.getNetAmount();
        if (netAmount != null) {
            detail.setNetAmount(CxmlUtil.extractDollar(netAmount.getMoney()));
        }
    }

    /**
     * @param b2bInvoice
     * @param invoiceDetailSummary
     */
    protected void extractSummaryAmounts(B2bInvoice b2bInvoice,
            InvoiceDetailSummary invoiceDetailSummary) {
        SubtotalAmount subtotalAmount = invoiceDetailSummary.getSubtotalAmount();
        if (subtotalAmount != null) {
            b2bInvoice.setSubTotalAmount(CxmlUtil.extractDollar(subtotalAmount.getMoney()));
        }

        Tax tax = invoiceDetailSummary.getTax();
        if (tax != null) {
            b2bInvoice.setTaxAmount(CxmlUtil.extractDollar(tax.getMoney()));
        }

        SpecialHandlingAmount splAmount = invoiceDetailSummary.getSpecialHandlingAmount();
        if (splAmount != null) {
            b2bInvoice.setSpecialHandlingAmount(CxmlUtil.extractDollar(splAmount.getMoney()));
        }

        ShippingAmount shipAmount = invoiceDetailSummary.getShippingAmount();
        if (shipAmount != null) {
            b2bInvoice.setShippingAmount(CxmlUtil.extractDollar(shipAmount.getMoney()));
        }

        GrossAmount grossAmount = invoiceDetailSummary.getGrossAmount();
        if (grossAmount != null) {
            b2bInvoice.setGrossAmount(CxmlUtil.extractDollar(grossAmount.getMoney()));
        }

        InvoiceDetailDiscount discount = invoiceDetailSummary.getInvoiceDetailDiscount();
        if (discount != null) {
            b2bInvoice.setDiscountAmount(CxmlUtil.extractDollar(discount.getMoney()));
        }


        NetAmount netAmount = invoiceDetailSummary.getNetAmount();
        if (netAmount != null) {
            b2bInvoice.setNetAmount(CxmlUtil.extractDollar(netAmount.getMoney()));
        }

        DepositAmount deposit = invoiceDetailSummary.getDepositAmount();
        if (deposit != null) {
            b2bInvoice.setDepositAmount(CxmlUtil.extractDollar(deposit.getMoney()));
        }

        DueAmount dueAmount = invoiceDetailSummary.getDueAmount();
        if (dueAmount != null) {
            b2bInvoice.setDueAmount(CxmlUtil.extractDollar(dueAmount.getMoney()));
        }
    }

    protected void copyAndNegateAmounts(B2bInvoice fromInvoice, B2bInvoice toInvoice) {
        if (fromInvoice.getSubTotalAmount() != null) {
            toInvoice.setSubTotalAmount(fromInvoice.getSubTotalAmount().negated());
        }
        if (fromInvoice.getTaxAmount() != null) {
            toInvoice.setTaxAmount(fromInvoice.getTaxAmount().negated());
        }
        if (fromInvoice.getSpecialHandlingAmount() != null) {
            toInvoice.setSpecialHandlingAmount(fromInvoice.getSpecialHandlingAmount().negated());
        }
        if (fromInvoice.getShippingAmount() != null) {
            toInvoice.setShippingAmount(fromInvoice.getShippingAmount().negated());
        }
        if (fromInvoice.getGrossAmount() != null) {
            toInvoice.setGrossAmount(fromInvoice.getGrossAmount().negated());
        }
        if (fromInvoice.getDiscountAmount() != null) {
            toInvoice.setDiscountAmount(fromInvoice.getDiscountAmount().negated());
        }
        if (fromInvoice.getNetAmount() != null) {
            toInvoice.setNetAmount(fromInvoice.getNetAmount().negated());
        }
        if (fromInvoice.getDepositAmount() != null) {
            toInvoice.setDepositAmount(fromInvoice.getDepositAmount().negated());
        }
        if (fromInvoice.getDueAmount() != null) {
            toInvoice.setDueAmount(fromInvoice.getDueAmount().negated());
        }
    }

    protected void copyAndNegateAmounts(B2bInvoiceDetail from, B2bInvoiceDetail to) {
        if (from.getSubTotalAmount() != null) {
            to.setSubTotalAmount(from.getSubTotalAmount().negated());
        }
        if (from.getTaxAmount() != null) {
            to.setTaxAmount(from.getTaxAmount().negated());
        }
        if (from.getSpecialHandlingAmount() != null) {
            to.setSpecialHandlingAmount(from.getSpecialHandlingAmount().negated());
        }
        if (from.getShippingAmount() != null) {
            to.setShippingAmount(from.getShippingAmount().negated());
        }
        if (from.getGrossAmount() != null) {
            to.setGrossAmount(from.getGrossAmount().negated());
        }
        if (from.getDiscountAmount() != null) {
            to.setDiscountAmount(from.getDiscountAmount().negated());
        }
        if (from.getNetAmount() != null) {
            to.setNetAmount(from.getNetAmount().negated());
        }
        if (from.getDepositAmount() != null) {
            to.setDepositAmount(from.getDepositAmount().negated());
        }
    }

    /**
     * @param b2bInvoice
     * @param idrh
     * @param purpose
     * @param operation
     * @return
     */
    protected B2bInvoice getSupportedInvoiceObject(InvoiceDetailRequestHeader idrh, String purpose,
            String operation) {
        B2bInvoice b2bInvoice = null;

        // We don't support header level invoices
        InvoiceDetailHeaderIndicator idhi = idrh.getInvoiceDetailHeaderIndicator();
        if (idhi != null && idhi.getIsHeaderInvoice() != null
                && "yes".equalsIgnoreCase(idhi.getIsHeaderInvoice())) {
            return null;
        }

        if (INV_STANDARD.equalsIgnoreCase(purpose) && OPERATION_NEW.equalsIgnoreCase(operation)) {
            b2bInvoice = new StandardInvoice();
        }
        else if ((CREDIT_MEMO.equalsIgnoreCase(purpose) || LINE_CREDIT_MEMO
                .equalsIgnoreCase(purpose))
                && OPERATION_NEW.equalsIgnoreCase(operation)) {
            b2bInvoice = new CreditMemo();
        }
        return b2bInvoice;
    }
}
