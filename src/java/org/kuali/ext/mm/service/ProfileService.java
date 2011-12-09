package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Profile;


public interface ProfileService {
	/**
	 * @param profileId
	 * @param principalId
	 * @return Profile identified by the profileId and verified to be owned by the principalId
	 */
	public Profile getCustomerProfileById(String profileId, String principalId);
	
	/**
	 * @param profileName
	 * @param principalName
	 * @return Profile identified by the profileName and principalName
	 */
	public Profile getCustomerProfile(String profileName, String principalName);

	/**
	 * @param principalId
	 * @return The default customer profile for a given customer(principalId)
	 */
	public Profile getDefaultCustomerProfile(String principalId);

	/**
	 * Saves customer profile to the database
	 *
	 * @param profile
	 */
	public void saveCustomerProfile(Profile profile);


	/**
	 * @param profile
	 * @return true if profile name and principalId from profile returns a match.
	 */
	public boolean customerProfileExists(Profile profile);

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
	 * @param profile
	 * @return true if customer profile form has all the correct data.
	 */
	public boolean isValidNewCustomerProfile(Profile profile);
	
}
