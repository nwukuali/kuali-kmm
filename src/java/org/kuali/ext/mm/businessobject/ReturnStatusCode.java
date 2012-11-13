package org.kuali.ext.mm.businessobject;

// Generated Apr 13, 2009 2:30:37 PM by Hibernate Tools 3.2.4.GA

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ReturnStatusCode generated by hbm2java
 */
@Entity
@Table(name = "MM_RETURN_STATUS_CODE_T")
public class ReturnStatusCode extends MMPersistableBusinessObjectBase implements
		java.io.Serializable {

	private static final long serialVersionUID = 462871855663831572L;

	@Id
	@Column(name = "RETURN_STATUS_CD", unique = true, nullable = false, length = 4)
	private String returnStatusCode;

	public String getReturnStatusCode() {
		return returnStatusCode;
	}

	public void setReturnStatusCode(String returnStatusCode) {
		this.returnStatusCode = returnStatusCode;
	}

	public String getReturnStatusCodeName() {
		return returnStatusCodeName;
	}

	public void setReturnStatusCodeName(String returnStatusCodeName) {
		this.returnStatusCodeName = returnStatusCodeName;
	}

	@Column(name = "NM", length = 45)
	private String returnStatusCodeName;

	public ReturnStatusCode() {
	}

	public String getReturnStatusCodeLookable(){
		return this.getReturnStatusCode();
	}

    @Column(name = "CUSTOMER_VENDOR_RETURN_INDD", unique = true, nullable = false, length = 1)
    private String customerVendorReturnInd;

    public String getCustomerVendorReturnInd() {
        return customerVendorReturnInd;
    }

    public void setCustomerVendorReturnInd(String customerVendorReturnInd) {
        this.customerVendorReturnInd = customerVendorReturnInd;
    }

	/**
	 * toStringMapper
	 * @return LinkedHashMap
	 */
	@Override
    public LinkedHashMap toStringMapper() {
		LinkedHashMap propMap = new LinkedHashMap();
		//TODO:  Autogenerated method

		return propMap;
	}

}