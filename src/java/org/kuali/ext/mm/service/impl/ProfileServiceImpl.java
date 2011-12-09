/**
 *
 */
package org.kuali.ext.mm.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.fp.service.FinancialDataService;
import org.kuali.ext.mm.service.AddressService;
import org.kuali.ext.mm.service.ProfileService;
import org.kuali.rice.kns.bo.Campus;
import org.kuali.rice.kns.bo.CampusImpl;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author schneppd
 *
 */
@Transactional
public class ProfileServiceImpl implements ProfileService {

	private BusinessObjectService businessObjectService;
	
	private AddressService addressService;

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#getDefaultCustomerProfile(java.lang.String)
	 */
	public Profile getDefaultCustomerProfile(String principalId) {
		if(principalId == null)
			return null;
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put(MMConstants.Profile.PRINCIPAL_NAME, principalId.toLowerCase());
		fieldMap.put(MMConstants.Profile.PROFILE_DEFAULT_IND, "Y");
		Collection results = getBusinessObjectService().findMatching(Profile.class, fieldMap);

		return (results != null && results.iterator().hasNext()) ? (Profile)results.iterator().next() : null;
	}

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#getCustomerProfileById(java.lang.String, java.lang.String)
	 */
	public Profile getCustomerProfileById(String profileId, String principalId) {
		Profile profile = getBusinessObjectService().findBySinglePrimaryKey(Profile.class, profileId);

		if(!StringUtils.equals(principalId, profile.getPrincipalName()))
			profile = null;

		return profile;
	}

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#getCustomerProfile(java.lang.String, java.lang.String)
	 */
	public Profile getCustomerProfile(String profileName, String principalName) {
	    HashMap<String, Object> fieldValues = new HashMap<String, Object>();
	    fieldValues.put(MMConstants.Profile.PROFILE_NAME, profileName);
	    fieldValues.put(MMConstants.Profile.PRINCIPAL_NAME, principalName);
	    
        Collection results = getBusinessObjectService().findMatching(Profile.class, fieldValues);
        Profile profile = null;
        if(results.iterator().hasNext())
            profile = (Profile)results.iterator().next();
        
        return profile;
    }
	
	/**
	 * @see org.kuali.ext.mm.service.ProfileService#saveCustomerProfile(org.kuali.ext.mm.businessobject.Profile)
	 */
	public void saveCustomerProfile(Profile profile) {
		if(profile.isPersonalUseIndicator()) {
			clearInternalUserInfo(profile);
		}
		if(profile.isProfileDefaultIndicator()) {
			Profile oldDefaultProfile = getDefaultCustomerProfile(profile.getPrincipalName());
			if(oldDefaultProfile != null && !StringUtils.equals(oldDefaultProfile.getProfileId(), profile.getProfileId())) {
				oldDefaultProfile.setProfileDefaultIndicator(false);
				getBusinessObjectService().save(oldDefaultProfile);
			}
			profile.setActive(true);
		}
		getBusinessObjectService().save(profile);
	}

	/**
	 * @param profile
	 */
	private void clearInternalUserInfo(Profile profile) {
		profile.setCampusCode(null);
		profile.setDeliveryBuildingCode(null);
		profile.setDeliveryBuildingRoomNumber(null);
		profile.setBillingBuildingCode(null);
		profile.setFinacialChartOfAccountsCode(null);
		profile.setAccountNumber(null);
		profile.setOrganizationCode(null);
		profile.setSubAccountNumber(null);
		profile.setProjectCode(null);
	}

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public AddressService getAddressService() {
        return addressService;
    }

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#customerProfileExists(org.kuali.ext.mm.businessobject.Profile)
	 */
	public boolean customerProfileExists(Profile profile) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put(MMConstants.Profile.PRINCIPAL_NAME, profile.getPrincipalName().toLowerCase());
		fieldMap.put(MMConstants.Profile.PROFILE_NAME, profile.getProfileName());
		Collection results = getBusinessObjectService().findMatching(Profile.class, fieldMap);

		return !results.isEmpty();
	}

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#getShippingAddress(org.kuali.ext.mm.businessobject.Profile)
	 */
	public Address getShippingAddress(Profile profile) {
		return getAddressService().getShippingAddress(profile);
	}

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#setShippingAddress(org.kuali.ext.mm.businessobject.Profile, org.kuali.ext.mm.businessobject.Address)
	 */
	public void setShippingAddress(Profile profile, Address shippingAddress) {
		getAddressService().setShippingAddress(profile, shippingAddress);
	}

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#getBillingAddress(org.kuali.ext.mm.businessobject.Profile)
	 */
	public Address getBillingAddress(Profile profile) {
	    return getAddressService().getBillingAddress(profile);
	}

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#setBillingAddress(org.kuali.ext.mm.businessobject.Profile, org.kuali.ext.mm.businessobject.Address)
	 */
	public void setBillingAddress(Profile profile, Address billingAddress) {
		getAddressService().setBillingAddress(profile, billingAddress);
	}

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#copyAsBillingAddress(org.kuali.ext.mm.businessobject.Address)
	 */
	public Address copyAsBillingAddress(Address address) {
	    return getAddressService().copyAsBillingAddress(address);
	}

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#saveAddressAsNonActiveRecord(org.kuali.ext.mm.businessobject.Address, org.kuali.ext.mm.businessobject.Profile)
	 */
	public String saveAddressAsNonActiveRecord(Address address, Profile profile) {
	    return getAddressService().saveAddressAsNonActiveRecord(address, profile);
	}

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#updateAddressWithBuildingInfo(java.lang.String, java.lang.String, org.kuali.ext.mm.businessobject.Address)
	 */
	public boolean updateAddressWithBuildingInfo(String campusCode, String buildingCode, Address addressToUpdate) {
	    return getAddressService().updateAddressWithBuildingInfo(campusCode, buildingCode, addressToUpdate);
	}

	/**
	 * @see org.kuali.ext.mm.service.ProfileService#isValidNewCustomerProfile(org.kuali.ext.mm.businessobject.Profile)
	 */
	public boolean isValidNewCustomerProfile(Profile profile) {
		boolean isValid=true;
		FinancialDataService finDataService = SpringContext.getBean(FinancialDataService.class);
		
		if(!getAddressService().isPhoneNumberFormatValid(profile.getProfilePhoneNumber())) {
		    GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.PROFILE_PHONE_NUMBER, ShopCartKeyConstants.ERROR_PROFILE_PHONE_NUMBER);
            isValid=false;
		}
		
		if(!profile.isPersonalUseIndicator()) {
        	if(StringUtils.isBlank(profile.getFinacialChartOfAccountsCode())) {
	        	GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.FIN_COA_CODE, ShopCartKeyConstants.ERROR_PROFILE_COA_CODE);
	   		 	isValid=false;
        	}
        	else {
        		if(!finDataService.validateChart(profile.getFinacialChartOfAccountsCode())) {
        			GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.FIN_COA_CODE, ShopCartKeyConstants.ERROR_PROFILE_COA_CODE);
    	   		 	isValid=false;
        		}
        	}
        	if(StringUtils.isBlank(profile.getOrganizationCode())){
				GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.ORGANIZATION_CODE, ShopCartKeyConstants.ERROR_PROFILE_ORG_CODE_BLANK,  profile.getOrganizationCode());
	   		 	isValid=false;
			}
			else {
        		if(!finDataService.validateOrg(profile.getFinacialChartOfAccountsCode(), profile.getOrganizationCode())) {
        			GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.ORGANIZATION_CODE, ShopCartKeyConstants.ERROR_PROFILE_ORG_CODE,  profile.getOrganizationCode(), profile.getFinacialChartOfAccountsCode());
    	   		 	isValid=false;
        		}
        	}
        	if(StringUtils.isBlank(profile.getAccountNumber())) {
	        	GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.ACCOUNT_NBR, ShopCartKeyConstants.ERROR_PROFILE_ACCOUNT_NUMBER);
	   		 	isValid=false;
        	}
        	else {
        		if(!finDataService.validateAccount(profile.getFinacialChartOfAccountsCode(), profile.getAccountNumber())) {
        			GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.ACCOUNT_NBR, ShopCartKeyConstants.ERROR_PROFILE_ACCOUNT_NUMBER);
    	   		 	isValid=false;
        		}
        		else {
        			if(StringUtils.isNotBlank(profile.getSubAccountNumber())) {
            			if(!finDataService.validateSubAccount(profile.getFinacialChartOfAccountsCode(), profile.getAccountNumber(), profile.getSubAccountNumber())) {
                			GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.SUB_ACCOUNT_NUMBER, ShopCartKeyConstants.ERROR_PROFILE_SUB_ACCOUNT,  profile.getSubAccountNumber());
            	   		 	isValid=false;
                		}
                	}
        		}
        	}
        	if(StringUtils.isNotBlank(profile.getProjectCode()) && !finDataService.validateProject(profile.getProjectCode())) {
	        	GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.PROJECT_CODE, ShopCartKeyConstants.ERROR_PROFILE_PROJECT_CODE, profile.getProjectCode());
	   		 	isValid=false;
        	}
        	if(StringUtils.isBlank(profile.getCampusCode()) || !isValidCampus(profile.getCampusCode())) {
	        	GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.CAMPUS_CODE, ShopCartKeyConstants.ERROR_PROFILE_CAMPUS_CODE);
	   		 	isValid=false;
        	}
        	if(StringUtils.isBlank(profile.getDeliveryBuildingCode()) || !finDataService.validateBuilding(profile.getCampusCode(), profile.getDeliveryBuildingCode())) {
	        	GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.DELIVERY_BUILDING_CODE, ShopCartKeyConstants.ERROR_PROFILE_DELIVERY_BUILDING);
	   		 	isValid=false;
        	}
        	if(StringUtils.isBlank(profile.getDeliveryBuildingRoomNumber())|| !finDataService.validateBuildingRoom(profile.getCampusCode(), profile.getDeliveryBuildingCode(), profile.getDeliveryBuildingRoomNumber())) {
	        	GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.DELIVERY_BUILDING_ROOM, ShopCartKeyConstants.ERROR_PROFILE_DELIVERY_BUILDING_ROOM);
	   		 	isValid=false;
        	}
        	if(StringUtils.isBlank(profile.getBillingBuildingCode())|| !finDataService.validateBuilding(profile.getCampusCode(), profile.getBillingBuildingCode())) {
	        	GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." +  MMConstants.Profile.BILLING_BUILDING_CODE, ShopCartKeyConstants.ERROR_PROFILE_BILLING_BUILDING);
	   		 	isValid=false;
        	}

        }
		return isValid;
	}

	/**
	 * @param campusCode
	 * @return true if campus code is a valid campus
	 */
	private boolean isValidCampus(String campusCode) {
		boolean valid = true;
        if (StringUtils.isBlank(campusCode)) {
            return valid;
        }

        BusinessObjectService boService = KNSServiceLocator.getBusinessObjectService();
        Campus campus = boService.findBySinglePrimaryKey(CampusImpl.class, campusCode);
        if (campus == null || !campus.isActive()) {
            valid = false;
        }
        return valid;
	}
}
