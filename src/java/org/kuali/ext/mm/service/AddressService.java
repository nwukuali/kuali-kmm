package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Profile;


public interface AddressService {

	/**
	 * @param profile
	 * @return The current, active shipping address of profile
	 */
	public Address getShippingAddress(Profile profile);

	/**
	 * Sets the current, active shipping address for profile
	 *
	 * @param profile
	 * @param shippingAddress
	 */
	public void setShippingAddress(Profile profile, Address shippingAddress);

	/**
	 * @param profile
	 * @return the current, active billing address of profile
	 */
	public Address getBillingAddress(Profile profile);

	/**
	 * Sets the current, active billing address for profile
	 *
	 * @param profile
	 * @param billingAddress
	 */
	public void setBillingAddress(Profile profile, Address billingAddress);

	/**
	 * @param address
	 * @return An copy of address turned into a proper billing address
	 */
	public Address copyAsBillingAddress(Address address);

	/**
	 * Creates a new address but does not set it as either the current shipping or billing address
	 * for profile.  Allows this an address to be associated to orders without changing the
	 * default shipping/billing addresses on profile.
	 *
	 * @param address
	 * @param profile
	 * @return Id of the address record created.
	 */
	public String saveAddressAsNonActiveRecord(Address address, Profile profile);

	/**
	 * Updates the address information from the campus and building information in the financial system.
	 *
	 * @param campusCode
	 * @param buildingCode
	 * @param addressToUpdate
	 * @return true if address was updated, false otherwise
	 */
	public boolean updateAddressWithBuildingInfo(String campusCode, String buildingCode, Address addressToUpdate);

	/**
     * Gets the area code from the address phone number
     * 
     * @param phoneNumber
     * @return the area code from the address phone number
     */
    public String getPhoneNumberAreaCode(String phoneNumber);
    
    /**
     * Gets the seven digit phone number.
     * 
     * @param phoneNumber
     * @return seven digit phone number without the area code
     */
    public String getPhoneNumberNoAreaCode(String phoneNumber);
    
    /**
     * Gets the phone number extension from address phone number
     * 
     * @param phoneNumber
     * @return extension from address phone number
     */
    public String getPhoneNumberExtension(String phoneNumber);

    /**
     * Valid phone number formats: 000-000-0000x000 or 000-000-0000
     * 
     * @param phoneNumber
     * @return true if phone number is formatted correctly
     */
    public boolean isPhoneNumberFormatValid(String phoneNumber);
    
    /**
     * @param address
     * @param errorPath
     * @return true if address is valid
     */
    public boolean validateAddress(Address address, String errorPath);
}
