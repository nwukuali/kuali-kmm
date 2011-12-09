/**
 *
 */
package org.kuali.ext.mm.integration.purap.document;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.kns.bo.ExternalizableBusinessObject;
import org.kuali.rice.kns.bo.TransientBusinessObjectBase;

/**
 * @author harsha07
 */
public class FinancialPurchaseOrderAccount extends TransientBusinessObjectBase implements
        ExternalizableBusinessObject, FinancialSystemComponent {
    /**
     *
     */
    private static final long serialVersionUID = 5713323908076044228L;
    private String accountsId;
    private String amountType;
    private String orderDocumentNumber;
    private Integer orderDetailId;
    private Integer lineNbr;
    private String finCoaCd;
    private String accountNbr;
    private String subAcctNbr;
    private String finObjectCd;
    private String finSubObjectCd;
    private String projectCd;
    private BigDecimal accountPct;
    private BigDecimal accountFixedAmt;

    /**
     * Gets the accountsId property
     *
     * @return Returns the accountsId
     */
    public String getAccountsId() {
        return this.accountsId;
    }

    /**
     * Sets the accountsId property value
     *
     * @param accountsId The accountsId to set
     */
    public void setAccountsId(String accountsId) {
        this.accountsId = accountsId;
    }

    /**
     * Gets the amountType property
     *
     * @return Returns the amountType
     */
    public String getAmountType() {
        return this.amountType;
    }

    /**
     * Sets the amountType property value
     *
     * @param amountType The amountType to set
     */
    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    /**
     * Gets the orderDocumentNumber property
     *
     * @return Returns the orderDocumentNumber
     */
    public String getOrderDocumentNumber() {
        return this.orderDocumentNumber;
    }

    /**
     * Sets the orderDocumentNumber property value
     *
     * @param orderDocumentNumber The orderDocumentNumber to set
     */
    public void setOrderDocumentNumber(String orderDocumentNumber) {
        this.orderDocumentNumber = orderDocumentNumber;
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
     * Gets the lineNbr property
     *
     * @return Returns the lineNbr
     */
    public Integer getLineNbr() {
        return this.lineNbr;
    }

    /**
     * Sets the lineNbr property value
     *
     * @param lineNbr The lineNbr to set
     */
    public void setLineNbr(Integer lineNbr) {
        this.lineNbr = lineNbr;
    }

    /**
     * Gets the finCoaCd property
     *
     * @return Returns the finCoaCd
     */
    public String getFinCoaCd() {
        return this.finCoaCd;
    }

    /**
     * Sets the finCoaCd property value
     *
     * @param finCoaCd The finCoaCd to set
     */
    public void setFinCoaCd(String finCoaCd) {
        this.finCoaCd = finCoaCd;
    }

    /**
     * Gets the accountNbr property
     *
     * @return Returns the accountNbr
     */
    public String getAccountNbr() {
        return this.accountNbr;
    }

    /**
     * Sets the accountNbr property value
     *
     * @param accountNbr The accountNbr to set
     */
    public void setAccountNbr(String accountNbr) {
        this.accountNbr = accountNbr;
    }

    /**
     * Gets the subAcctNbr property
     *
     * @return Returns the subAcctNbr
     */
    public String getSubAcctNbr() {
        return this.subAcctNbr;
    }

    /**
     * Sets the subAcctNbr property value
     *
     * @param subAcctNbr The subAcctNbr to set
     */
    public void setSubAcctNbr(String subAcctNbr) {
        this.subAcctNbr = subAcctNbr;
    }

    /**
     * Gets the finObjectCd property
     *
     * @return Returns the finObjectCd
     */
    public String getFinObjectCd() {
        return this.finObjectCd;
    }

    /**
     * Sets the finObjectCd property value
     *
     * @param finObjectCd The finObjectCd to set
     */
    public void setFinObjectCd(String finObjectCd) {
        this.finObjectCd = finObjectCd;
    }

    /**
     * Gets the finSubObjectCd property
     *
     * @return Returns the finSubObjectCd
     */
    public String getFinSubObjectCd() {
        return this.finSubObjectCd;
    }

    /**
     * Sets the finSubObjectCd property value
     *
     * @param finSubObjectCd The finSubObjectCd to set
     */
    public void setFinSubObjectCd(String finSubObjectCd) {
        this.finSubObjectCd = finSubObjectCd;
    }

    /**
     * Gets the projectCd property
     *
     * @return Returns the projectCd
     */
    public String getProjectCd() {
        return this.projectCd;
    }

    /**
     * Sets the projectCd property value
     *
     * @param projectCd The projectCd to set
     */
    public void setProjectCd(String projectCd) {
        this.projectCd = projectCd;
    }

    /**
     * Gets the accountPct property
     *
     * @return Returns the accountPct
     */
    public BigDecimal getAccountPct() {
        return this.accountPct;
    }

    /**
     * Sets the accountPct property value
     *
     * @param accountPct The accountPct to set
     */
    public void setAccountPct(BigDecimal accountPct) {
        this.accountPct = accountPct;
    }

    /**
     * Gets the accountFixedAmt property
     *
     * @return Returns the accountFixedAmt
     */
    public BigDecimal getAccountFixedAmt() {
        return this.accountFixedAmt;
    }

    /**
     * Sets the accountFixedAmt property value
     *
     * @param accountFixedAmt The accountFixedAmt to set
     */
    public void setAccountFixedAmt(BigDecimal accountFixedAmt) {
        this.accountFixedAmt = accountFixedAmt;
    }

    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    @Override
    protected LinkedHashMap<String, String> toStringMapper() {
        return null;
    }

}
