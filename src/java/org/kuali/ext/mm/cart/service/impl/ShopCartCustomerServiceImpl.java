/**
 *
 */
package org.kuali.ext.mm.cart.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Customer;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.cart.service.ShopCartCustomerService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.encryption.EncryptionService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DictionaryValidationService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;


/**
 * @author schneppd
 *
 */
@Transactional
public class ShopCartCustomerServiceImpl implements ShopCartCustomerService {

	private BusinessObjectService businessObjectService;


	public Customer getCustomerById(String principalId) {
		return getBusinessObjectService().findBySinglePrimaryKey(Customer.class, StringUtils.lowerCase(principalId));
	}



	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartCustomerService#createAndSaveNewCustomer(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Customer createAndSaveNewCustomer(String principalId, String firstName, String lastName, String password) {
		Customer customer = new Customer();
		customer.setPrincipalName(principalId.toLowerCase());
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setCustomerPassword(password);
		customer.setActive(true);
		saveCustomer(customer);
		customer = (Customer)getBusinessObjectService().retrieve(customer);

		return customer;
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartProfileService#createNewCustomerFromPerson(org.kuali.rice.kim.bo.Person)
	 */
	public Customer createAndSaveNewCustomerFromPerson(Person person) {
		//KIMServiceLocator.getRoleManagementService().assignPrincipalToRole(person.getPrincipalId(), MMConstants.MM_NAMESPACE, ShopCartConstants.Role.MM_SHOP_BROWSER, new AttributeSet());
		return createAndSaveNewCustomer(person.getPrincipalName(), person.getFirstNameUnmasked(), person.getLastNameUnmasked(), null);
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartCustomerService#saveCustomer(org.kuali.ext.mm.businessobject.Customer)
	 */
	public void saveCustomer(Customer customer) {
		getBusinessObjectService().save(customer);
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartProfileService#getLoggedInCustomer()
	 */
	public Customer getLoggedInCustomer() {
		Customer customer = null;

		if(!GlobalVariables.getUserSession().getPrincipalName().equals(ShopCartConstants.User.SHOP_GUEST)) {
			customer = getCustomerById(GlobalVariables.getUserSession().getPrincipalName());
			if(customer == null) {
			    Person user = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(GlobalVariables.getUserSession().getPrincipalName());
			    if(user != null)
			        customer = ShopCartServiceLocator.getShopCartCustomerService().createAndSaveNewCustomerFromPerson(user);
			}
		}

		return customer;
	}

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartCustomerService#validateNewCustomer(org.kuali.ext.mm.businessobject.Customer, java.lang.String)
	 */
	public boolean validateNewCustomer(Customer customer, String errorPath) {
		boolean isValid = true;
		DictionaryValidationService validationService = KRADServiceLocatorWeb.getDictionaryValidationService();
		isValid = validationService.isBusinessObjectValid(customer, errorPath);

		if(StringUtils.isBlank(customer.getPrincipalName())) {
			GlobalVariables.getMessageMap().putError(errorPath + "." + MMConstants.Customer.PRINCIPAL_NAME, ShopCartKeyConstants.ERROR_CUSTOMER_PRINCIPAL_BLANK);
			isValid=false;
		}

		if(isValid) {
			Person user = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(customer.getPrincipalName());
			if(user != null || getCustomerById(customer.getPrincipalName()) != null) {
				 GlobalVariables.getMessageMap().putError(errorPath + "." + MMConstants.Customer.PRINCIPAL_NAME, ShopCartKeyConstants.ERROR_CUSTOMER_PRINCIPAL_EXISTS);
				 isValid=false;
			}

			if(!isValidEmailAddress(customer.getPrincipalName())) {
				GlobalVariables.getMessageMap().putError(errorPath + "." + MMConstants.Customer.PRINCIPAL_NAME, ShopCartKeyConstants.ERROR_CUSTOMER_PRINCIPAL_NOT_VALID);
				isValid=false;
			}
		}

		if(StringUtils.isBlank(customer.getCustomerPassword())) {
			GlobalVariables.getMessageMap().putError(errorPath + "." + MMConstants.Customer.CUSTOMER_PASSWORD, ShopCartKeyConstants.ERROR_CUSTOMER_PASSWORD_BLANK);
			isValid=false;
		}
		else {
			if(!StringUtils.equals(customer.getCustomerPassword(), customer.getConfirmPassword())) {
				GlobalVariables.getMessageMap().putError(errorPath + "." + MMConstants.Customer.CONFIRM_PASSWORD, ShopCartKeyConstants.ERROR_CUSTOMER_PASSWORD_MISMATCH);
				isValid=false;
			}
		}

		return isValid;
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartCustomerService#isExternalUser(org.kuali.ext.mm.businessobject.Customer)
	 */
	public boolean isExternalUser(Customer customer) {
		return isValidEmailAddress(customer.getPrincipalName());
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartCustomerService#isValidEmailAddress(java.lang.String)
	 */
	public boolean isValidEmailAddress(String email) {
		boolean isValid = true;

		if(StringUtils.isBlank(email))
			return false;
		Integer atIndex = email.indexOf("@");
		isValid &= (atIndex >= 0) && (atIndex.equals(email.lastIndexOf("@")));
		if(isValid)
			isValid &= StringUtils.contains(email.substring(email.indexOf("@"), email.length()), ".");

		return isValid;
	}



	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartCustomerService#authenticateUser(org.kuali.ext.mm.businessobject.Customer, java.lang.String)
	 */
	public boolean authenticateUser(Customer customer, String suppliedPassword) {
		if(customer == null)
			return false;

		String hashword=null;
		try {
			hashword = CoreApiServiceLocator.getEncryptionService().hash(suppliedPassword);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}

		String customerPassword =  customer.getCustomerPassword();
		if(customerPassword != null && hashword != null && customerPassword.endsWith(EncryptionService.HASH_POST_PREFIX) )
			hashword += EncryptionService.HASH_POST_PREFIX;

		if(StringUtils.equals(customerPassword, hashword))
			return true;

		return false;
	}

}
