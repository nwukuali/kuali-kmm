package org.kuali.ext.mm.cart.service;

import org.kuali.ext.mm.businessobject.Customer;
import org.kuali.rice.kim.api.identity.Person;


public interface ShopCartCustomerService {

	public Customer getLoggedInCustomer();

	public Customer getCustomerById(String principalId);

	public Customer createAndSaveNewCustomer(String principalId, String firstName, String lastName, String password);

	public Customer createAndSaveNewCustomerFromPerson(Person person);

	public void saveCustomer(Customer customer);

	public boolean validateNewCustomer(Customer customer, String errorPath);

	public boolean isExternalUser(Customer customer);

	public boolean isValidEmailAddress(String email);

	/**
	 * @param customer
	 * @param suppliedPassword
	 * @return returns true if user and suppliedPassword are correct
	 */
	public boolean authenticateUser(Customer customer, String suppliedPassword);

}
