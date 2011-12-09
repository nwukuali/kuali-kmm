/**
 *
 */
package org.kuali.ext.mm.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.service.FinancialLocationService;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;
import org.kuali.ext.mm.service.AddressService;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.DictionaryValidationService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author schneppd
 *
 */
@Transactional
public class AddressServiceImpl implements AddressService {

	private BusinessObjectService businessObjectService;

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public Address getShippingAddress(Profile profile) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put(MMConstants.Address.ADDRESS_PROFILE_ID, profile.getProfileId());
		fieldMap.put(MMConstants.Address.ADDRESS_TYPE_CODE, MMConstants.AddressType.SHIP_TO_ADDRESS);
		fieldMap.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
		Collection results = getBusinessObjectService().findMatching(Address.class, fieldMap);

		return (results != null && results.iterator().hasNext()) ? (Address)results.iterator().next() : null;
	}

	public void setShippingAddress(Profile profile, Address shippingAddress) {
		Address oldAddress = getShippingAddress(profile);
		shippingAddress.setAddressTypeCode(MMConstants.AddressType.SHIP_TO_ADDRESS);

		if(shippingAddress.equals(oldAddress))
			return;

		if(ObjectUtils.isNotNull(oldAddress)) {
				oldAddress.setActive(false);
				getBusinessObjectService().save(oldAddress);
		}
		shippingAddress.setActive(true);
		profile.getAddresses().add(shippingAddress);
	}

	public Address getBillingAddress(Profile profile) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put(MMConstants.Address.ADDRESS_PROFILE_ID, profile.getProfileId());
		fieldMap.put(MMConstants.Address.ADDRESS_TYPE_CODE, MMConstants.AddressType.BILL_TO_ADDRESS);
		fieldMap.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
		Collection results = getBusinessObjectService().findMatching(Address.class, fieldMap);

		return (results != null && results.iterator().hasNext()) ? (Address)results.iterator().next() : null;
	}

	public void setBillingAddress(Profile profile, Address billingAddress) {
		Address oldAddress = getBillingAddress(profile);
		billingAddress.setAddressTypeCode(MMConstants.AddressType.BILL_TO_ADDRESS);

		if(billingAddress.equals(oldAddress))
			return;

		if(ObjectUtils.isNotNull(oldAddress)) {
				oldAddress.setActive(false);
				getBusinessObjectService().save(oldAddress);
		}
		billingAddress.setActive(true);
		profile.getAddresses().add(billingAddress);
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartProfileService#copyAsBillingAddress(org.kuali.ext.mm.businessobject.Address)
	 */
	public Address copyAsBillingAddress(Address address) {
		Address newBillingAddress = new Address(address);

		newBillingAddress.setAddressTypeCode(MMConstants.AddressType.BILL_TO_ADDRESS);

		return newBillingAddress;
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartProfileService#saveAddressAsNonActiveRecord(org.kuali.ext.mm.businessobject.Address, org.kuali.ext.mm.businessobject.Profile)
	 */
	public String saveAddressAsNonActiveRecord(Address address, Profile profile) {
		address.setActive(false);
		address.setAddressProfileId(profile.getProfileId());
		String addressId = findExistingAddress(address);
		if(addressId == null) {
			addressId = String.valueOf(KNSServiceLocator.getSequenceAccessorService().getNextAvailableSequenceNumber(ShopCartConstants.Sequence.ADDERSS_ID_SEQ));
			address.setAddressId(String.valueOf(addressId));
			getBusinessObjectService().save(address);
		}
		return addressId;
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartProfileService#updateAddressWithBuildingInfo(java.lang.String, java.lang.String, org.kuali.ext.mm.businessobject.Address)
	 */
	public boolean updateAddressWithBuildingInfo(String campusCode, String buildingCode, Address addressToUpdate) {

		if(StringUtils.isBlank(campusCode) || StringUtils.isBlank(buildingCode))
			return false;

		FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
    	.getBean(FinancialSystemAdaptorFactory.class);
		if (financialSystemAdaptorFactory.isSystemAvailable()) {
			FinancialLocationService locationService = financialSystemAdaptorFactory.getFinancialLocationService();
			FinancialBuilding building = locationService.getBuilding(campusCode, buildingCode);
			if(building != null && building.isActive()) {
				addressToUpdate.setAddressLine1(building.getBuildingStreetAddress());
				addressToUpdate.setAddressCityName(building.getBuildingAddressCityName());
				addressToUpdate.setAddressStateCode(building.getBuildingAddressStateCode());
				addressToUpdate.setAddressPostalCode(building.getBuildingAddressZipCode());
				addressToUpdate.setAddressCountryCode(building.getBuildingAddressCountryCode());
			}
			else
				return false;
		}

		return true;
	}

	private String findExistingAddress(Address address) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put(MMConstants.Address.ADDRESS_NAME, address.getAddressName());
		fieldMap.put(MMConstants.Address.ADDRESS_LINE_1, address.getAddressLine1());
		if(StringUtils.isNotBlank(address.getAddressLine2()))
			fieldMap.put(MMConstants.Address.ADDRESS_LINE_2, address.getAddressLine2());
		fieldMap.put(MMConstants.Address.ADDRESS_STATE, address.getAddressStateCode());
		fieldMap.put(MMConstants.Address.ADDRESS_CITY_NAME, address.getAddressCityName());
		fieldMap.put(MMConstants.Address.ADDRESS_COUNTRY, address.getAddressCountryCode());
		fieldMap.put(MMConstants.Address.ADDRESS_POSTAL_CODE, address.getAddressPostalCode());
		fieldMap.put(MMConstants.Address.ADDRESS_PHONE_NUMBER, address.getAddressPhoneNumber());
		fieldMap.put(MMConstants.Address.ADDRESS_TYPE_CODE, address.getAddressTypeCode());
		fieldMap.put(MMConstants.Address.ADDRESS_PROFILE_ID, address.getAddressProfileId());
		Collection results = getBusinessObjectService().findMatching(Address.class, fieldMap);

		return (results != null && results.iterator().hasNext()) ? ((Address)results.iterator().next()).getAddressId() : null;
	}

	/**
     * @see org.kuali.ext.mm.service.AddressService#getPhoneNumberAreaCode(java.lang.String)
     */
    public String getPhoneNumberAreaCode(String phoneNumber) {
        String result = "";
        if(isPhoneNumberFormatValid(phoneNumber)) {
            String[] phoneNumberTokens = phoneNumber.split(MMConstants.Address.PHONE_NUMBER_DELIM);
            result = phoneNumberTokens.length > 0 ? phoneNumberTokens[0] : "";
        }
        return result;
    }
    
    /**
     * @see org.kuali.ext.mm.service.AddressService#getPhoneNumberNoAreaCode(java.lang.String)
     */
    public String getPhoneNumberNoAreaCode(String phoneNumber) {
        String result = "";
        if(isPhoneNumberFormatValid(phoneNumber)) {
            String[] phoneNumberTokens = phoneNumber.split(MMConstants.Address.PHONE_NUMBER_DELIM);
            result = phoneNumberTokens.length > 2 ? phoneNumberTokens[1].concat(MMConstants.Address.PHONE_NUMBER_DELIM).concat(phoneNumberTokens[2]) : "";
        }
        return result;          
    }

    /**
     * @see org.kuali.ext.mm.service.AddressService#getPhoneNumberExtension(java.lang.String)
     */
    public String getPhoneNumberExtension(String phoneNumber) {
        String result = "";
        if(isPhoneNumberFormatValid(phoneNumber)) {
            String[] phoneNumberTokens = phoneNumber.split(MMConstants.Address.PHONE_NUMBER_EXT_DELIM);        
            result = phoneNumberTokens.length > 1 ? phoneNumberTokens[1] : "";  
        }
        return result; 
    }
    
    /**
     * @see org.kuali.ext.mm.service.AddressService#isPhoneNumberFormatValid(java.lang.String)
     */
    public boolean isPhoneNumberFormatValid(String phoneNumber) {        
        if(StringUtils.isBlank(phoneNumber))
            return false;
        
        Pattern p = Pattern.compile("\\d{3}-\\d{3}-\\d{4}(x\\d+)??");
        Matcher m = p.matcher(phoneNumber);
        
        return m.matches();
    }
    
    /**
     * @see org.kuali.ext.mm.service.AddressService#validateAddress(org.kuali.ext.mm.businessobject.Address, java.lang.String)
     */
    public boolean validateAddress(Address address, String errorPath) {
        DictionaryValidationService validationService = KNSServiceLocator.getDictionaryValidationService();
        boolean isValid = validationService.isBusinessObjectValid(address, errorPath);
        
        if(!isPhoneNumberFormatValid(address.getAddressPhoneNumber())) {
            GlobalVariables.getMessageMap().putError(errorPath, ShopCartKeyConstants.ERROR_PROFILE_PHONE_NUMBER);
            isValid=false;
        }
        
        return isValid;
    }
}
