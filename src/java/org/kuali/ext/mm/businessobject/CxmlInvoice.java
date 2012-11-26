package org.kuali.ext.mm.businessobject;

public class CxmlInvoice extends MMPersistableBusinessObjectBase {
    private static final long serialVersionUID = 303214523110210161L;

    private String keyId;

	private String punchOutVendorId;

	private String orderId;

	private String invoiceNbr;

	private boolean processedInd;

	private String invoiceXml;

	public CxmlInvoice() {

	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getPunchOutVendorId() {
		return punchOutVendorId;
	}

	public void setPunchOutVendorId(String punchOutVendorId) {
		this.punchOutVendorId = punchOutVendorId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getInvoiceNbr() {
		return invoiceNbr;
	}

	public void setInvoiceNbr(String invoiceNbr) {
		this.invoiceNbr = invoiceNbr;
	}

	public boolean isProcessedInd() {
		return processedInd;
	}

	public void setProcessedInd(boolean processedInd) {
		this.processedInd = processedInd;
	}

	public String getInvoiceXml() {
		return invoiceXml;
	}

	public void setInvoiceXml(String invoiceXml) {
		this.invoiceXml = invoiceXml;
	}
	
}
