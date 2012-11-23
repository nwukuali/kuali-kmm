/**
 *
 */
package org.kuali.ext.mm.integration.purap.document;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.bo.TransientBusinessObjectBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author harsha07
 */
public class FinancialPurchaseOrderDetail extends TransientBusinessObjectBase implements
	ExternalizableBusinessObject, FinancialSystemComponent {

    /**
     *
     */
    private static final long serialVersionUID = 789825741232947709L;
    private String stockUnitOfIssueCd;
    private KualiDecimal orderItemQty;
    private BigDecimal orderItemCostAmt;
    private Integer poId;
    private Integer itemLineNumber;

    private List<FinancialPurchaseOrderAccount> accounts;

    /**
     *
     */
    public FinancialPurchaseOrderDetail() {
        this.accounts = new ArrayList<FinancialPurchaseOrderAccount>();
    }

    /**
     * Gets the stockUnitOfIssueCd property
     * 
     * @return Returns the stockUnitOfIssueCd
     */
    public String getStockUnitOfIssueCd() {
        return this.stockUnitOfIssueCd;
    }

    /**
     * Sets the stockUnitOfIssueCd property value
     * 
     * @param stockUnitOfIssueCd The stockUnitOfIssueCd to set
     */
    public void setStockUnitOfIssueCd(String stockUnitOfIssueCd) {
        this.stockUnitOfIssueCd = stockUnitOfIssueCd;
    }

    /**
     * Gets the orderItemQty property
     * 
     * @return Returns the orderItemQty
     */
    public KualiDecimal getOrderItemQty() {
        return this.orderItemQty;
    }

    /**
     * Sets the orderItemQty property value
     * 
     * @param orderItemQty The orderItemQty to set
     */
    public void setOrderItemQty(KualiDecimal orderItemQty) {
        this.orderItemQty = orderItemQty;
    }

    /**
     * Gets the orderItemCostAmt property
     * 
     * @return Returns the orderItemCostAmt
     */
    public BigDecimal getOrderItemCostAmt() {
        return this.orderItemCostAmt;
    }

    /**
     * Sets the orderItemCostAmt property value
     * 
     * @param orderItemCostAmt The orderItemCostAmt to set
     */
    public void setOrderItemCostAmt(BigDecimal orderItemCostAmt) {
        this.orderItemCostAmt = orderItemCostAmt;
    }

    /**
     * Gets the poId property
     * 
     * @return Returns the poId
     */
    public Integer getPoId() {
        return this.poId;
    }

    /**
     * Sets the poId property value
     * 
     * @param poId The poId to set
     */
    public void setPoId(Integer poId) {
        this.poId = poId;
    }


    /**
     * Gets the itemLineNumber property
     * 
     * @return Returns the itemLineNumber
     */
    public Integer getItemLineNumber() {
        return this.itemLineNumber;
    }

    /**
     * Sets the itemLineNumber property value
     * 
     * @param itemLineNumber The itemLineNumber to set
     */
    public void setItemLineNumber(Integer itemLineNumber) {
        this.itemLineNumber = itemLineNumber;
    }


    /**
     * Gets the accounts property
     * 
     * @return Returns the accounts
     */
    public List<FinancialPurchaseOrderAccount> getAccounts() {
        return this.accounts;
    }

    /**
     * Sets the accounts property value
     * 
     * @param accounts The accounts to set
     */
    public void setAccounts(List<FinancialPurchaseOrderAccount> accounts) {
        this.accounts = accounts;
    }

}
