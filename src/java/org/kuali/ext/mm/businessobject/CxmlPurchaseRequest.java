package org.kuali.ext.mm.businessobject;

// Generated Apr 8, 2009 10:12:44 AM by Hibernate Tools 3.2.4.GA


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MM_XML_PURHCASE_REQUEST")
public class CxmlPurchaseRequest extends StoresPersistableBusinessObject implements java.io.Serializable {

    private static final long serialVersionUID = 7149553368202803536L;

    private String xmlPurchaseRequestId;

    private String keyId;

    private String orderId;

    private String profileId;

    private String purchaseRequestXml;
    
    public CxmlPurchaseRequest() {
        
    }

    public String getXmlPurchaseRequestId() {
        return xmlPurchaseRequestId;
    }

    public void setXmlPurchaseRequestId(String xmlPurchaseRequestId) {
        this.xmlPurchaseRequestId = xmlPurchaseRequestId;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getPurchaseRequestXml() {
        return purchaseRequestXml;
    }

    public void setPurchaseRequestXml(String purchaseRequestXml) {
        this.purchaseRequestXml = purchaseRequestXml;
    }


}
