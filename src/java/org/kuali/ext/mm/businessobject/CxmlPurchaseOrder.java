package org.kuali.ext.mm.businessobject;

public class CxmlPurchaseOrder extends StoresPersistableBusinessObject {

	/**
     * 
     */
    private static final long serialVersionUID = 909845369311090932L;

    private String xmlPurchaseOrderId;

	private String keyId;

	private String orderId;

	private String profileId;

	private String purchaseOrderUrl;

	private String purchaseOrderXml;

	public CxmlPurchaseOrder() {

	}

	public String getXmlPurchaseOrderId() {
		return xmlPurchaseOrderId;
	}

	public void setXmlPurchaseOrderId(String xmlPurchaseOrderId) {
		this.xmlPurchaseOrderId = xmlPurchaseOrderId;
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

	public String getPurchaseOrderUrl() {
		return purchaseOrderUrl;
	}

	public void setPurchaseOrderUrl(String purchaseOrderUrl) {
		this.purchaseOrderUrl = purchaseOrderUrl;
	}

	public String getPurchaseOrderXml() {
		return purchaseOrderXml;
	}

	public void setPurchaseOrderXml(String purchaseOrderXml) {
		this.purchaseOrderXml = purchaseOrderXml;
	}


}
