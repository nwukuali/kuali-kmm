package org.kuali.ext.mm.businessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MM_ADDRESS_TYPE_T")
public class AddressType extends MMPersistableBusinessObjectBase {

	/**
     *
     */
    private static final long serialVersionUID = 2857681291320253148L;

    @Id
	@Column(name = "ADDRESS_TYPE_CD")
	private String addressTypeCode;

	@Column(name ="NM")
	private String name;

	public AddressType() {

	}

	public String getAddressTypeCode() {
		return addressTypeCode;
	}

	public void setAddressTypeCode(String addressTypeCode) {
		this.addressTypeCode = addressTypeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
