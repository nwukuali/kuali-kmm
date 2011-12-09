/**
 *
 */
package org.kuali.ext.mm.businessobject;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

/**
 * @author rponraj
 *
 */
public class ReorderItem extends StoresPersistableBusinessObject {

    /**
     *
     */
    private static final long serialVersionUID = 8226941144973774156L;

    private Timestamp reorderDate;

    private CatalogSubgroup catalogSubGroup;

    private CatalogGroup catalogGroup;

    private Integer rowval;

    private String catalogSubgroupId;

    private String warehouseCd;

    private String catalogSubGroupCode;

    private String catalogGroupCode;

    private String groupDesc;

    private String subgroupDesc;

    private String agreementNbr;

    private Agreement agreement;

    private Warehouse warehouse;

    private String expInd;

    private String descrepancyInd;

    private String minimumOrderQtyInd;

    private String noSalesOverYearInd;

    private String noSalesLastMonthInd;

    private Integer reorderQuantity;

    private Integer backOrderQuantity;

    public Agreement getAgreement() {
        return this.agreement;
    }

    public String getAgreementNbr() {
        if(StringUtils.isEmpty(this.agreementNbr))
            return "";
        return this.agreementNbr.trim();
    }

    public Integer getBackOrderQuantity() {
        return this.backOrderQuantity;
    }

    public CatalogGroup getCatalogGroup() {
        return this.catalogGroup;
    }

    public String getCatalogGroupCode() {
        return this.catalogGroupCode;
    }

    public CatalogSubgroup getCatalogSubGroup() {
        return this.catalogSubGroup;
    }

    public String getCatalogSubGroupCode() {
        return this.catalogSubGroupCode;
    }

    public String getCatalogSubgroupId() {
        return this.catalogSubgroupId;
    }

    public String getGroupDesc() {
        return this.groupDesc;
    }

    public Timestamp getReorderDate() {
        return this.reorderDate;
    }

    public Integer getReorderQuantity() {
        return this.reorderQuantity;
    }

    public Integer getRowval() {
        return this.rowval;
    }

    public String getSubgroupDesc() {
        return this.subgroupDesc;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public String getWarehouseCd() {
        return this.warehouseCd;
    }

    public String isDescrepancyInd() {
        return this.descrepancyInd;
    }

    public String isExpInd() {
        return this.expInd;
    }

    public String isMinimumOrderQtyInd() {
        return this.minimumOrderQtyInd;
    }

    public String isNoSalesLastMonthInd() {
        return this.noSalesLastMonthInd;
    }

    public String isNoSalesOverYearInd() {
        return this.noSalesOverYearInd;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public void setAgreementNbr(String agreementNbr) {
        this.agreementNbr = agreementNbr;
    }

    public void setBackOrderQuantity(Integer backOrderQuantity) {
        this.backOrderQuantity = backOrderQuantity;
    }

    public void setCatalogGroup(CatalogGroup catalogGroup) {
        this.catalogGroup = catalogGroup;
    }

    public void setCatalogGroupCode(String catalogGroupCode) {
        this.catalogGroupCode = catalogGroupCode;
    }

    public void setCatalogSubGroup(CatalogSubgroup catalogSubgroup) {
        this.catalogSubGroup = catalogSubgroup;
    }

    public void setCatalogSubGroupCode(String catalogSubGroupCode) {
        this.catalogSubGroupCode = catalogSubGroupCode;
    }

    public void setCatalogSubgroupId(String catalogSubgroupId) {
        this.catalogSubgroupId = catalogSubgroupId;
    }

    public void setDescrepancyInd(String descrepancyInd) {
        this.descrepancyInd = descrepancyInd;
    }

    public void setExpInd(String expInd) {
        this.expInd = expInd;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public void setMinimumOrderQtyInd(String minimumOrderQtyInd) {
        this.minimumOrderQtyInd = minimumOrderQtyInd;
    }

    public void setNoSalesLastMonthInd(String noSalesLastMonthInd) {
        this.noSalesLastMonthInd = noSalesLastMonthInd;
    }

    public void setNoSalesOverYearInd(String noSalesOverYearInd) {
        this.noSalesOverYearInd = noSalesOverYearInd;
    }

    public void setReorderDate(Timestamp reorderDate) {
        this.reorderDate = reorderDate;
    }

    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }


    public void setRowval(Integer rowval) {
        this.rowval = rowval;
    }

    public void setSubgroupDesc(String subgroupDesc) {
        this.subgroupDesc = subgroupDesc;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setWarehouseCd(String warehouseCd) {
        this.warehouseCd = warehouseCd;
    }


}
