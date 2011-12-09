package org.kuali.ext.mm.businessobject;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialChart;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialObjectCode;



@Entity
@Table(name = "MM_WAREHOUSE_OBJECT_T", uniqueConstraints = @UniqueConstraint(columnNames = {
        "WAREHOUSE_CD", "WAREHOUSE_OBJECT_TYPE_CD" }))
public class WarehouseObject extends MMPersistableBusinessObjectBase implements
        java.io.Serializable {

    private static final long serialVersionUID = 7090173603462273491L;
    private String warehouseObjectId;
    private String warehouseCd;
    private String warehouseObjectTypeCd;
    private String finCoaCd;
    private String finObjectCd;
    private String finSubObjCd;
    private String offsetObjectCd;
    private String offsetSubObjCd;

    private Warehouse warehouse;
    private WarehouseObjectType warehouseObjectType;

    /*
     * Financial object references
     */
    private FinancialChart financialChart;
    private FinancialObjectCode financialObjectCode;
    private FinancialObjectCode financialOffsetObjectCode;
    private Integer currentFiscalYear;

    public WarehouseObject() {
    }

    @Id
    @Column(name = "WAREHOUSE_OBJECT_ID", unique = true, nullable = false, precision = 18, scale = 0)
    public String getWarehouseObjectId() {
        return this.warehouseObjectId;
    }

    public void setWarehouseObjectId(String warehouseObjectId) {
        this.warehouseObjectId = warehouseObjectId;
    }

    @Column(name = "WAREHOUSE_CD", nullable = false, length = 2)
    public String getWarehouseCd() {
        return this.warehouseCd;
    }

    public void setWarehouseCd(String warehouseCd) {
        this.warehouseCd = warehouseCd;
    }

    @Column(name = "WAREHOUSE_OBJECT_TYPE_CD", nullable = false, length = 4)
    public String getWarehouseObjectTypeCd() {
        return this.warehouseObjectTypeCd;
    }

    public void setWarehouseObjectTypeCd(String warehouseObjectTypeCd) {
        this.warehouseObjectTypeCd = warehouseObjectTypeCd;
    }

    @Column(name = "FIN_COA_CD", nullable = false, length = 2)
    public String getFinCoaCd() {
        return this.finCoaCd;
    }

    public void setFinCoaCd(String finCoaCd) {
        this.finCoaCd = finCoaCd;
    }

    @Column(name = "FIN_OBJECT_CD", length = 4)
    public String getFinObjectCd() {
        return this.finObjectCd;
    }

    public void setFinObjectCd(String finObjectCd) {
        this.finObjectCd = finObjectCd;
    }

    @Column(name = "FIN_SUB_OBJ_CD", length = 3)
    public String getFinSubObjCd() {
        return this.finSubObjCd;
    }

    public void setFinSubObjCd(String finSubObjCd) {
        this.finSubObjCd = finSubObjCd;
    }

    @Column(name = "OFFSET_OBJECT_CD", length = 4)
    public String getOffsetObjectCd() {
        return this.offsetObjectCd;
    }

    public void setOffsetObjectCd(String offsetObjectCd) {
        this.offsetObjectCd = offsetObjectCd;
    }

    @Column(name = "OFFSET_SUB_OBJ_CD", length = 3)
    public String getOffsetSubObjCd() {
        return this.offsetSubObjCd;
    }

    public void setOffsetSubObjCd(String offsetSubObjCd) {
        this.offsetSubObjCd = offsetSubObjCd;
    }

    /**
     * Gets the warehouse property
     *
     * @return Returns the warehouse
     */
    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    /**
     * Sets the warehouse property value
     *
     * @param warehouse The warehouse to set
     */
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Gets the warehouseObjectType property
     *
     * @return Returns the warehouseObjectType
     */
    public WarehouseObjectType getWarehouseObjectType() {
        return this.warehouseObjectType;
    }

    /**
     * Sets the warehouseObjectType property value
     *
     * @param warehouseObjectType The warehouseObjectType to set
     */
    public void setWarehouseObjectType(WarehouseObjectType warehouseObjectType) {
        this.warehouseObjectType = warehouseObjectType;
    }

    /**
     * Gets the financialChart property
     *
     * @return Returns the financialChart
     */
    public FinancialChart getFinancialChart() {
        return this.financialChart;
    }

    /**
     * Sets the financialChart property value
     *
     * @param financialChart The financialChart to set
     */
    public void setFinancialChart(FinancialChart financialChart) {
        this.financialChart = financialChart;
    }

    /**
     * Gets the financialObjectCode property
     *
     * @return Returns the financialObjectCode
     */
    public FinancialObjectCode getFinancialObjectCode() {
        return this.financialObjectCode;
    }

    /**
     * Sets the financialObjectCode property value
     *
     * @param financialObjectCode The financialObjectCode to set
     */
    public void setFinancialObjectCode(FinancialObjectCode financialObjectCode) {
        this.financialObjectCode = financialObjectCode;
    }

    /**
     * Gets the financialOffsetObjectCode property
     *
     * @return Returns the financialOffsetObjectCode
     */
    public FinancialObjectCode getFinancialOffsetObjectCode() {
        return this.financialOffsetObjectCode;
    }

    /**
     * Sets the financialOffsetObjectCode property value
     *
     * @param financialOffsetObjectCode The financialOffsetObjectCode to set
     */
    public void setFinancialOffsetObjectCode(FinancialObjectCode financialOffsetObjectCode) {
        this.financialOffsetObjectCode = financialOffsetObjectCode;
    }

    /**
     * Gets the currentFiscalYear property
     *
     * @return Returns the currentFiscalYear
     */
    public Integer getCurrentFiscalYear() {
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (this.currentFiscalYear == null && factory.isSystemAvailable()) {
            this.currentFiscalYear = factory.getFinancialUniversityDateService()
                    .getCurrentFiscalYear();
        }
        return this.currentFiscalYear;
    }

    /**
     * Sets the currentFiscalYear property value
     *
     * @param currentFiscalYear The currentFiscalYear to set
     */
    public void setCurrentFiscalYear(Integer currentFiscalYear) {
        this.currentFiscalYear = currentFiscalYear;
    }
}
