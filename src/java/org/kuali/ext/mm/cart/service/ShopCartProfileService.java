package org.kuali.ext.mm.cart.service;

import java.util.Collection;

import org.kuali.ext.mm.businessobject.Customer;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.service.ProfileService;


public interface ShopCartProfileService extends ProfileService {

	/**
	 * Returns the active profiles for a customer, with or without a restriction on
	 * PersonalUse profiles.
	 *
	 * @param customer
	 * @return list of active profiles for customer
	 */
	public Collection<Profile> getActiveProfiles(Customer customer);
	
	/**
	 * Get Personal Use profile enabled/disabled status.
	 * 
	 * @param customer
	 * @return true if system allows the use of Personal Use profiles by the customer
	 */
	public boolean isPersonalUseProfileEnabled(Customer customer);
}
