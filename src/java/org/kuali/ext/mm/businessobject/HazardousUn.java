package org.kuali.ext.mm.businessobject;

// Generated Apr 2, 2009 11:05:56 AM by Hibernate Tools 3.2.2.GA

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * HazardousUn generated by hbm2java
 */
@Entity
@Table(name = "MM_HAZARDOUS_UN_T")
public class HazardousUn extends MMPersistableBusinessObjectBase implements java.io.Serializable {

    private static final long serialVersionUID = 8071733784912740017L;

    @Id
    @Column(name = "HAZARDOUS_UN_CD", unique = true, nullable = false, length = 10)
    private String hazardousUnCode;

    @Column(name = "HAZARDOUS_UN_DESC")
    private String hazardousUnDesc;

    @Column(name = "HAZARDOUS_UN_STD_UNIT", length = 5)
    private String hazardousUnStdUnit;

    public HazardousUn() {
    }

    public HazardousUn(String hazardousUnCode) {
        this.hazardousUnCode = hazardousUnCode;
    }

    public HazardousUn(String hazardousUnCode, String hazardousUnDesc, String hazardousUnStdUnit) {
        this.hazardousUnCode = hazardousUnCode;
        this.hazardousUnDesc = hazardousUnDesc;
        this.hazardousUnStdUnit = hazardousUnStdUnit;
    }

    public String getHazardousUnCode() {
        return this.hazardousUnCode;
    }

    public void setHazardousUnCode(String hazardousUnCode) {
        this.hazardousUnCode = hazardousUnCode;
    }

    public String getHazardousUnDesc() {
        return this.hazardousUnDesc;
    }

    public void setHazardousUnDesc(String hazardousUnDesc) {
        this.hazardousUnDesc = hazardousUnDesc;
    }

    public String getHazardousUnStdUnit() {
        return this.hazardousUnStdUnit;
    }

    public void setHazardousUnStdUnit(String hazardousUnStdUnit) {
        this.hazardousUnStdUnit = hazardousUnStdUnit;
    }

    /**
     * toStringMapper
     * 
     * @return LinkedHashMap
     */
    @Override
    public LinkedHashMap toStringMapper() {
        LinkedHashMap propMap = new LinkedHashMap();
        // TODO: Autogenerated method

        return propMap;
    }

}
