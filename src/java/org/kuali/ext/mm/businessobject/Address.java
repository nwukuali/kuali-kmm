package org.kuali.ext.mm.businessobject;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.kuali.rice.kns.util.ObjectUtils;

@Entity
@Table(name = "MM_ADDRESS_T")
public class Address extends MMPersistableBusinessObjectBase {

	/**
     *
     */
    private static final long serialVersionUID = 2219181463778187812L;

    @Id
	@Column(name = "ADDRESS_ID")
	private String addressId;

	private Profile customerProfile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESS_TYPE_CD")
	private AddressType addressType;

	@Column(name = "ADDRESS_TYPE_CD")
	private String addressTypeCode;

	@Column(name = "ADDRESS_PROFILE_ID")
	private String addressProfileId;

	@Column(name = "ADDRESS_NM")
	private String addressName;

	@Column(name = "ADDRESS_LN1")
	private String addressLine1;

	@Column(name = "ADDRESS_LN2")
	private String addressLine2;

	@Column(name = "ADDRESS_CITY_NM")
	private String addressCityName;

	@Column(name = "ADDRESS_STATE_CD")
	private String addressStateCode;

	@Column(name = "ADDRESS_POSTAL_CD")
	private String addressPostalCode;

	@Column(name = "ADDRESS_COUNTRY_CD")
	private String addressCountryCode;

	@Column(name = "ADDRESS_PHONE_NBR")
	private String addressPhoneNumber;

//	@Column(name = "ADDRESS_DEFAULT_IND")
//	private boolean addressDefaultIndicator;

	public Address() {

	}

	public Address(Address address) {
		this.setActive(address.isActive());
		this.setAddressName(address.getAddressName());
		this.setAddressLine1(address.getAddressLine1());
		this.setAddressLine2(address.getAddressLine2());
		this.setAddressCityName(address.getAddressCityName());
		this.setAddressStateCode(address.getAddressStateCode());
		this.setAddressCountryCode(address.getAddressCountryCode());
		this.setAddressPostalCode(address.getAddressPostalCode());
		this.setAddressPhoneNumber(address.getAddressPhoneNumber());
		this.setAddressTypeCode(address.getAddressTypeCode());
		this.setAddressProfileId(address.getAddressProfileId());
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public void setAddressProfileId(String addressProfileId) {
		this.addressProfileId = addressProfileId;
	}

	public String getAddressProfileId() {
		return addressProfileId;
	}

	public void setCustomerProfile(Profile customerProfile) {
		this.customerProfile = customerProfile;
	}

	public Profile getCustomerProfile() {
		return customerProfile;
	}

	public String getAddressTypeCode() {
		return addressTypeCode;
	}

	public void setAddressTypeCode(String addressTypeCode) {
		this.addressTypeCode = addressTypeCode;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressCityName() {
		return addressCityName;
	}

	public void setAddressCityName(String addressCityName) {
		this.addressCityName = addressCityName;
	}

	public String getAddressStateCode() {
		return addressStateCode;
	}

	public void setAddressStateCode(String addressStateCode) {
		this.addressStateCode = addressStateCode;
	}

	public String getAddressPostalCode() {
		return addressPostalCode;
	}

	public void setAddressPostalCode(String addressPostalCode) {
		this.addressPostalCode = addressPostalCode;
	}

	public String getAddressCountryCode() {
		return addressCountryCode;
	}

	public void setAddressCountryCode(String addressCountryCode) {
		this.addressCountryCode = addressCountryCode;
	}

	public String getAddressPhoneNumber() {
		return addressPhoneNumber;
	}

	public void setAddressPhoneNumber(String addressPhoneNumber) {
		this.addressPhoneNumber = addressPhoneNumber;
	}


//	public void setAddressDefaultIndicator(boolean addressDefaultIndicator) {
//		this.addressDefaultIndicator = addressDefaultIndicator;
//	}
//
//	public boolean isAddressDefaultIndicator() {
//		return addressDefaultIndicator;
//	}

	@Override
    public boolean equals(Object address) {
        if (ObjectUtils.isNull(address))
            return false;

        return StringUtils.equals(this.getAddressName(),((Address)address).getAddressName()) &&
        		StringUtils.equals(this.getAddressLine1(),((Address)address).getAddressLine1()) &&
        		StringUtils.equals(this.getAddressLine2(),((Address)address).getAddressLine2()) &&
        		StringUtils.equals(this.getAddressCityName(),((Address)address).getAddressCityName()) &&
        		StringUtils.equals(this.getAddressStateCode(),((Address)address).getAddressStateCode()) &&
        		StringUtils.equals(this.getAddressPostalCode(),((Address)address).getAddressPostalCode()) &&
        		StringUtils.equals(this.getAddressCountryCode(),((Address)address).getAddressCountryCode()) &&
        		StringUtils.equals(this.getAddressTypeCode(),((Address)address).getAddressTypeCode()) &&
        		StringUtils.equals(this.getAddressPhoneNumber(),((Address)address).getAddressPhoneNumber());
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(37, 41);
        hashCodeBuilder.append(this.getAddressName());
        hashCodeBuilder.append(this.getAddressLine1());
        hashCodeBuilder.append(this.getAddressLine2());
        hashCodeBuilder.append(this.getAddressCityName());
        hashCodeBuilder.append(this.getAddressStateCode());
        hashCodeBuilder.append(this.getAddressPostalCode());
        hashCodeBuilder.append(this.getAddressCountryCode());
        hashCodeBuilder.append(this.getAddressTypeCode());
        hashCodeBuilder.append(this.getAddressPhoneNumber());
        return hashCodeBuilder.toHashCode();
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
