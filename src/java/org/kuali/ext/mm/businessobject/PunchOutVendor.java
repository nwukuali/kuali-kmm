package org.kuali.ext.mm.businessobject;

// Generated Apr 8, 2009 10:12:44 AM by Hibernate Tools 3.2.4.GA


import java.util.LinkedHashMap;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Zone generated by hbm2java
 */
@Entity
@Table(name = "MM_PUNCH_OUT_VNDR_T")
public class PunchOutVendor extends MMPersistableBusinessObjectBase implements java.io.Serializable {

    private static final long serialVersionUID = 6893549800796721276L;

    private String punchOutVendorId;

    private String punchOutVendorName;

    private String catalogId;

    private String vendorCredentialDomain;

    private String vendorIdentity;

    private String vendorSharedSecret;

    private String punchOutUrl;

    private String purchaseOrderUrl;

    private String localCredentialDomain;

    private String localIdentity;

    private String localUserAgent;

    private String deploymentMode;

    private Catalog catalog;

    private PCard procurementCard;

    private String procurementCardId;

    public PunchOutVendor() {
    }

    public String getPunchOutVendorId() {
        return this.punchOutVendorId;
    }


    public void setPunchOutVendorId(String punchOutVendorId) {
        this.punchOutVendorId = punchOutVendorId;
    }


    public String getPunchOutVendorName() {
        return this.punchOutVendorName;
    }


    public void setPunchOutVendorName(String punchOutVendorName) {
        this.punchOutVendorName = punchOutVendorName;
    }


    public String getCatalogId() {
        return this.catalogId;
    }


    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }


    public String getVendorCredentialDomain() {
        return this.vendorCredentialDomain;
    }


    public void setVendorCredentialDomain(String vendorCredentialDomain) {
        this.vendorCredentialDomain = vendorCredentialDomain;
    }


    public String getVendorIdentity() {
        return this.vendorIdentity;
    }


    public void setVendorIdentity(String vendorIdentity) {
        this.vendorIdentity = vendorIdentity;
    }


    public String getVendorSharedSecret() {
        return this.vendorSharedSecret;
    }


    public void setVendorSharedSecret(String vendorSharedSecret) {
        this.vendorSharedSecret = vendorSharedSecret;
    }


    public String getPunchOutUrl() {
        return this.punchOutUrl;
    }


    public void setPunchOutUrl(String punchOutUrl) {
        this.punchOutUrl = punchOutUrl;
    }


    public String getPurchaseOrderUrl() {
        return purchaseOrderUrl;
    }

    public void setPurchaseOrderUrl(String purchaseOrderUrl) {
        this.purchaseOrderUrl = purchaseOrderUrl;
    }

    public String getLocalCredentialDomain() {
        return this.localCredentialDomain;
    }


    public void setLocalCredentialDomain(String localCredentialDomain) {
        this.localCredentialDomain = localCredentialDomain;
    }


    public String getLocalIdentity() {
        return this.localIdentity;
    }


    public void setLocalIdentity(String localIdentity) {
        this.localIdentity = localIdentity;
    }


    public String getLocalUserAgent() {
        return this.localUserAgent;
    }


    public void setLocalUserAgent(String localUserAgent) {
        this.localUserAgent = localUserAgent;
    }


    public String getDeploymentMode() {
        return this.deploymentMode;
    }


    public void setDeploymentMode(String deploymentMode) {
        this.deploymentMode = deploymentMode;
    }

    public Catalog getCatalog() {
        return this.catalog;
    }


    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public PCard getProcurementCard() {
        return this.procurementCard;
    }

    public void setProcurementCard(PCard procurementCard) {
        this.procurementCard = procurementCard;
    }

    public String getProcurementCardId() {
        return this.procurementCardId;
    }

    public void setProcurementCardId(String procurementCardId) {
        this.procurementCardId = procurementCardId;
    }

    /**
     * toStringMapper
     * 
     * @return LinkedHashMap
     */
    @Override
    public LinkedHashMap toStringMapper() {
        LinkedHashMap propMap = new LinkedHashMap();
        return propMap;
    }

}
