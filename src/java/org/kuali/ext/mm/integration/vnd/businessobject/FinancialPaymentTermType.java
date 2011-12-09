/**
 *
 */
package org.kuali.ext.mm.integration.vnd.businessobject;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * @author harsha07
 */
public class FinancialPaymentTermType {
    private String vendorPaymentTermsCode;
    private String vendorDiscountDueTypeDescription;
    private Integer vendorDiscountDueNumber;
    private BigDecimal vendorPaymentTermsPercent;
    private String vendorNetDueTypeDescription;
    private Integer vendorNetDueNumber;
    private String vendorPaymentTermsDescription;
    private boolean active;

    /**
     * Default constructor.
     */
    public FinancialPaymentTermType() {

    }

    public String getVendorPaymentTermsCode() {

        return vendorPaymentTermsCode;
    }

    public void setVendorPaymentTermsCode(String vendorPaymentTermsCode) {
        this.vendorPaymentTermsCode = vendorPaymentTermsCode;
    }

    public String getVendorDiscountDueTypeDescription() {

        return vendorDiscountDueTypeDescription;
    }

    public void setVendorDiscountDueTypeDescription(String vendorDiscountDueTypeDescription) {
        this.vendorDiscountDueTypeDescription = vendorDiscountDueTypeDescription;
    }

    public Integer getVendorDiscountDueNumber() {

        return vendorDiscountDueNumber;
    }

    public void setVendorDiscountDueNumber(Integer vendorDiscountDueNumber) {
        this.vendorDiscountDueNumber = vendorDiscountDueNumber;
    }

    public BigDecimal getVendorPaymentTermsPercent() {

        return vendorPaymentTermsPercent;
    }

    public void setVendorPaymentTermsPercent(BigDecimal vendorPaymentTermsPercent) {
        this.vendorPaymentTermsPercent = vendorPaymentTermsPercent;
    }

    public String getVendorNetDueTypeDescription() {

        return vendorNetDueTypeDescription;
    }

    public void setVendorNetDueTypeDescription(String vendorNetDueTypeDescription) {
        this.vendorNetDueTypeDescription = vendorNetDueTypeDescription;
    }

    public Integer getVendorNetDueNumber() {

        return vendorNetDueNumber;
    }

    public void setVendorNetDueNumber(Integer vendorNetDueNumber) {
        this.vendorNetDueNumber = vendorNetDueNumber;
    }

    public String getVendorPaymentTermsDescription() {

        return vendorPaymentTermsDescription;
    }

    public void setVendorPaymentTermsDescription(String vendorPaymentTermsDescription) {
        this.vendorPaymentTermsDescription = vendorPaymentTermsDescription;
    }

    public boolean isActive() {

        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    protected LinkedHashMap<String, String> toStringMapper() {
        LinkedHashMap<String, String> m = new LinkedHashMap<String, String>();
        m.put("vendorPaymentTermsCode", this.vendorPaymentTermsCode);
        return m;
    }


}
