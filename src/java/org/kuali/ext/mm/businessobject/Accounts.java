package org.kuali.ext.mm.businessobject;

// Generated Jul 13, 2009 9:05:12 AM by Hibernate Tools 3.2.4.GA

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialAccount;
import org.kuali.rice.core.api.util.type.AbstractKualiDecimal;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * Accounts generated by hbm2java
 */
@Entity
@Table(name = "MM_ACCOUNTS_T")
public class Accounts extends MMPersistableBusinessObjectBase {

    /**
     *
     */
    private static final long serialVersionUID = -4716250814727132130L;
    @Id
    @Column(name = "ACCOUNTS_ID", unique = true, nullable = false, length = 36)
    private String accountsId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOC_NBR", nullable = false, insertable = false, updatable = false)
    private OrderDocument orderDocument;

    private OrderDetail orderDetail;

    private String amountType;

    // BEGIN OJB
    @Column(name = "DOC_NBR")
    private String orderDocumentNumber;

    private Integer orderDetailId;
    // END OJB

    @Column(name = "LINE_NBR", nullable = false, precision = 8, scale = 0)
    private Integer lineNbr;

    @Column(name = "FIN_COA_CD", nullable = false, length = 2)
    private String finCoaCd;

    @Column(name = "ACCOUNT_NBR", nullable = false, length = 7)
    private String accountNbr;

    @Column(name = "SUB_ACCT_NBR", nullable = false, length = 5)
    private String subAcctNbr;

    @Column(name = "FIN_OBJECT_CD", length = 4)
    private String finObjectCd;

    private String finSubObjectCd;

    @Column(name = "PROJECT_CD", length = 10)
    private String projectCd;

    @Column(name = "ACCOUNT_PCT", nullable = false, precision = 8)
    private BigDecimal accountPct;

    @Column(name = "ACCOUNT_FIXED_AMT", nullable = false, scale = 4)
    private KualiDecimal accountFixedAmt;

    private KualiDecimal formAmount;

    @Column(name = "DEPARTMENT_REFERENCE_TXT")
    private String departmentReferenceText;
    
    private String rentalId;
    
    private Rental rental;

    public Accounts() {
    }

    public Accounts(Accounts account) {
        setFinCoaCd(account.getFinCoaCd());
        setAccountNbr(account.getAccountNbr());
        setSubAcctNbr(account.getSubAcctNbr());
        setFinObjectCd(account.getFinObjectCd());
        setFinSubObjectCd(account.getFinSubObjectCd());
        setProjectCd(account.getProjectCd());
        setDepartmentReferenceText(account.getDepartmentReferenceText());
        setAccountFixedAmt(account.getAccountFixedAmt());
        setAccountPct(account.getAccountPct());
        setAmountType(account.getAmountType());
        setFormAmount(account.getFormAmount());
    }

    public String getAccountsId() {
        return this.accountsId;
    }

    public void setAccountsId(String accountsId) {
        this.accountsId = accountsId;
    }

    public OrderDocument getOrderDocument() {
        return this.orderDocument;
    }

    public void setOrderDocument(OrderDocument orderDocument) {
        this.orderDocument = orderDocument;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    // BEGIN OJB

    public String getOrderDocumentNumber() {
        return orderDocumentNumber;
    }

    public void setOrderDocumentNumber(String orderDocumentNumber) {
        this.orderDocumentNumber = orderDocumentNumber;
    }

    // END OJB

    public Integer getLineNbr() {
        return this.lineNbr;
    }

    public void setLineNbr(Integer lineNbr) {
        this.lineNbr = lineNbr;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getAmountType() {
        return amountType;
    }

    public String getFinCoaCd() {
        return this.finCoaCd;
    }

    public void setFinCoaCd(String finCoaCd) {
        this.finCoaCd = finCoaCd;
    }

    public String getAccountNbr() {
        return this.accountNbr;
    }

    public void setAccountNbr(String accountNbr) {
        this.accountNbr = accountNbr;
    }

    public String getSubAcctNbr() {
        return this.subAcctNbr;
    }

    public void setSubAcctNbr(String subAcctNbr) {
        this.subAcctNbr = subAcctNbr;
    }

    public String getFinObjectCd() {
        return this.finObjectCd;
    }

    public void setFinObjectCd(String finObjectCd) {
        this.finObjectCd = finObjectCd;
    }

    public void setFinSubObjectCd(String finSubObjectCd) {
        this.finSubObjectCd = finSubObjectCd;
    }

    public String getFinSubObjectCd() {
        return finSubObjectCd;
    }

    public String getProjectCd() {
        return this.projectCd;
    }

    public void setProjectCd(String projectCd) {
        this.projectCd = projectCd;
    }

    public BigDecimal getAccountPct() {
        return this.accountPct;
    }

    public void setAccountPct(BigDecimal accountPct) {
        if (accountPct != null) {
            this.accountPct = accountPct.setScale(KualiDecimal.SCALE,
                    AbstractKualiDecimal.ROUND_BEHAVIOR);
        }
    }

    public KualiDecimal getAccountFixedAmt() {
        return this.accountFixedAmt;
    }

    public void setAccountFixedAmt(KualiDecimal accountFixedAmt) {
        this.accountFixedAmt = accountFixedAmt;
    }

    public void setFormAmount(KualiDecimal formAmount) {
        this.formAmount = formAmount;
    }

    public KualiDecimal getFormAmount() {
        if(this.formAmount == null) {
            if(MMConstants.Accounts.OPTION_FXD.equals(this.amountType) && this.getAccountFixedAmt() != null)
                this.formAmount = this.accountFixedAmt;
            else if(MMConstants.Accounts.OPTION_PCT.equals(this.amountType) && this.getAccountPct() != null)
                this.formAmount = new KualiDecimal(this.accountPct);
        }
        
        return formAmount;
    }

    public void setDepartmentReferenceText(String departmentReferenceText) {
        this.departmentReferenceText = departmentReferenceText;
    }

    public String getDepartmentReferenceText() {
        return departmentReferenceText;
    }


    @Override
    public boolean equals(Object obj) {
        Accounts account = (Accounts) obj;
        return StringUtils.equals(this.getFinCoaCd(), account.getFinCoaCd())
                && StringUtils.equals(this.getAccountNbr(), account.getAccountNbr())
                && StringUtils.equals(this.getSubAcctNbr(), account.getSubAcctNbr())
                && StringUtils.equals(this.getFinObjectCd(), account.getFinObjectCd())
                && StringUtils.equals(this.getFinSubObjectCd(), account.getFinSubObjectCd())
                && StringUtils.equals(this.getProjectCd(), account.getProjectCd())
                && StringUtils.equals(this.getDepartmentReferenceText(), account
                        .getDepartmentReferenceText());
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(37, 41);
        hashCodeBuilder.append(this.finCoaCd);
        hashCodeBuilder.append(this.accountNbr);
        hashCodeBuilder.append(this.subAcctNbr);
        hashCodeBuilder.append(this.finObjectCd);
        hashCodeBuilder.append(this.finSubObjectCd);
        hashCodeBuilder.append(this.projectCd);
        hashCodeBuilder.append(this.departmentReferenceText);

        return hashCodeBuilder.toHashCode();
    }

    /**
     * Returns account organization code
     * 
     * @return
     */
    public String getAccountOrganizationCode() {
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (factory.isSystemAvailable()) {
            FinancialAccount acct = factory.getAccountService().getByPrimaryId(getFinCoaCd(),
                    getAccountNbr());
            if (acct != null) {
                return acct.getOrganizationCode();
            }
        }
        return null;
    }

    /**
     * Always returns override code as NONE
     * 
     * @return
     */
    public String getOverrideCode() {
        return "NONE";
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public Rental getRental() {
        return rental;
    }
}
