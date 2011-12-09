package org.kuali.ext.mm.cart.web.struts;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.cart.service.ShopCartProfileService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.service.DictionaryValidationService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.KualiConfigurationService;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;


public class ProfileAction extends StoresShoppingActionBase {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	@Override
	public ActionForward refresh(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward parentAction = super.refresh(mapping, form, request, response);
		ProfileForm pForm = (ProfileForm)form;

		String campusCode = pForm.getProfileToEdit().getCampusCode();
		String shipToBuildingCode = pForm.getProfileToEdit().getDeliveryBuildingCode();
		String billingBuildingCode = pForm.getProfileToEdit().getBillingBuildingCode();
		if(StringUtils.isNotBlank(shipToBuildingCode)) {
			ShopCartServiceLocator.getShopCartProfileService().updateAddressWithBuildingInfo(campusCode, shipToBuildingCode, pForm.getShipToAddress());
		}
		if(StringUtils.isNotBlank(billingBuildingCode)) {
			ShopCartServiceLocator.getShopCartProfileService().updateAddressWithBuildingInfo(campusCode, billingBuildingCode, pForm.getBillingAddress());
		}


		return parentAction;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProfileForm pForm = (ProfileForm)form;

        String profileId = request.getParameter(MMConstants.Profile.PROFILE_ID);
        if(StringUtils.isNotBlank(profileId)) {
    		ShopCartProfileService profileService = ShopCartServiceLocator.getShopCartProfileService();
    		pForm.setProfileToEdit(profileService.getCustomerProfileById(profileId, pForm.getCustomer().getPrincipalName()));
    		pForm.setShipToAddress(profileService.getShippingAddress(pForm.getProfileToEdit()));
    		pForm.setBillingAddress(profileService.getBillingAddress(pForm.getProfileToEdit()));
    	}
        else {
        	resetProfileForm(pForm);
        }
        
        boolean isPersonalUse = pForm.getProfileToEdit().isPersonalUseIndicator();
        pForm.getProfileToEdit().setPersonalUseIndicator(isPersonalUse && pForm.isAllowsPersonalUse());

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProfileForm pForm = (ProfileForm)form;

        pForm.getShipToAddress().setAddressPhoneNumber(pForm.getProfileToEdit().getProfilePhoneNumber());
        pForm.getBillingAddress().setAddressPhoneNumber(pForm.getProfileToEdit().getProfilePhoneNumber());
        
        boolean isValid = isProfileFormValid(pForm);

        ShopCartProfileService profileService = ShopCartServiceLocator.getShopCartProfileService();       

        if(isValid) {
            boolean profileExists = profileService.customerProfileExists(pForm.getProfileToEdit());        
            if(profileExists) {
                Profile existingProfile = profileService.getCustomerProfile(pForm.getProfileToEdit().getProfileName(), pForm.getCustomer().getPrincipalName());
                String existingProfileId = (existingProfile == null) ? null : existingProfile.getProfileId();
            	if(!StringUtils.equals(pForm.getProfileToEdit().getProfileId(), existingProfileId)) {
            		 GlobalVariables.getMessageMap().putError(ShopCartConstants.ProfileForm.PROFILE_TO_EDIT + "." + MMConstants.Profile.PROFILE_NAME, ShopCartKeyConstants.ERROR_PROFILE_NAME_EXISTS);
            		 isValid=false;
            	}
            }            
            boolean isNewProfile = StringUtils.isBlank(pForm.getProfileToEdit().getProfileId());
        	if(isValid && (profileExists || !isNewProfile)) {
	    		buildSaveConfirmation(pForm, request);
	    		request.getSession().setAttribute(ShopCartConstants.Session.PROFILE_TO_EDIT, pForm.getProfileToEdit());
	    		request.getSession().setAttribute(ShopCartConstants.Session.SHIP_TO_ADDRESS, pForm.getShipToAddress());
	    		request.getSession().setAttribute(ShopCartConstants.Session.BILLING_ADDRESS, pForm.getBillingAddress());
        	}
        	else if(isValid && isNewProfile) {
        	    prepareNewProfile(pForm);
        	    saveNewProfile(pForm, request);
        	}
        }
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward saveConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProfileForm pForm = (ProfileForm)form;

		pForm.setProfileToEdit((Profile)request.getSession().getAttribute(ShopCartConstants.Session.PROFILE_TO_EDIT));
		pForm.setShipToAddress((Address)request.getSession().getAttribute(ShopCartConstants.Session.SHIP_TO_ADDRESS));
		pForm.setBillingAddress((Address)request.getSession().getAttribute(ShopCartConstants.Session.BILLING_ADDRESS));

		saveNewProfile(pForm, request);
		request.getSession().removeAttribute(ShopCartConstants.Session.PROFILE_TO_EDIT);
		request.getSession().removeAttribute(ShopCartConstants.Session.SHIP_TO_ADDRESS);
		request.getSession().removeAttribute(ShopCartConstants.Session.BILLING_ADDRESS);

		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward confirmActionDecline(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProfileForm pForm = (ProfileForm)form;

		pForm.setProfileToEdit((Profile)request.getSession().getAttribute(ShopCartConstants.Session.PROFILE_TO_EDIT));
		pForm.setShipToAddress((Address)request.getSession().getAttribute(ShopCartConstants.Session.SHIP_TO_ADDRESS));
		pForm.setBillingAddress((Address)request.getSession().getAttribute(ShopCartConstants.Session.BILLING_ADDRESS));

		request.getSession().removeAttribute(ShopCartConstants.Session.PROFILE_TO_EDIT);
		request.getSession().removeAttribute(ShopCartConstants.Session.SHIP_TO_ADDRESS);
		request.getSession().removeAttribute(ShopCartConstants.Session.BILLING_ADDRESS);

		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward clear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProfileForm pForm = (ProfileForm)form;

        resetProfileForm(pForm);
        boolean isPersonalUse = pForm.getProfileToEdit().isPersonalUseIndicator();
        pForm.getProfileToEdit().setPersonalUseIndicator(isPersonalUse && pForm.isAllowsPersonalUse());

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	/**
	 * @param pForm
	 * @param request
	 */
	private void buildSaveConfirmation(ProfileForm pForm, HttpServletRequest request) {
		Map<String, String> confirmParameters = new HashMap<String, String>();
		Map<String, String> declineParameters = new HashMap<String, String>();

		pForm.createNewConfirmAction(request);
		pForm.getConfirmAction().setConfirmAction(ShopCartConstants.PROFILE_ACTION);
		pForm.getConfirmAction().setDeclineAction(ShopCartConstants.PROFILE_ACTION);
		pForm.getConfirmAction().setMessage(KNSServiceLocator.getKualiConfigurationService().getPropertyString(ShopCartKeyConstants.QUESTION_CONFIRM_PROFILE_SAVE));
		confirmParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.SAVE_CONFRIM_METHOD);
		declineParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.CONFIRM_ACTION_DECLINE_METHOD);
		pForm.getConfirmAction().setConfirmParameters(confirmParameters);
		pForm.getConfirmAction().setDeclineParameters(declineParameters);
	}

	private void resetProfileForm(ProfileForm pForm) {
		pForm.setProfileToEdit(new Profile());
    	pForm.setShipToAddress(new Address());
    	pForm.setBillingAddress(new Address());

    	Person user = KIMServiceLocator.getPersonService().getPersonByPrincipalName(pForm.getCustomer().getPrincipalName());
    	boolean isPersonalUse = true;
    	if(user != null)
    		isPersonalUse = pForm.getProfileToEdit().isPersonalUseIndicator();
    	pForm.getProfileToEdit().setPersonalUseIndicator(isPersonalUse);

    	if(ShopCartServiceLocator.getShopCartCustomerService().isExternalUser(pForm.getCustomer()))
    		pForm.getProfileToEdit().setProfileEmail(pForm.getCustomer().getPrincipalName());

    	pForm.getProfileToEdit().setPrincipalName(pForm.getCustomer().getPrincipalName());
    	if(ObjectUtils.isNull(pForm.getCustomerProfile())) {
    		pForm.getProfileToEdit().setProfileDefaultIndicator(true);
    	}
    	else {
	    	pForm.getProfileToEdit().setCustomer(pForm.getCustomer());
    	}
    	pForm.getProfileToEdit().setActive(true);
	}

	/**
	 * @param pForm
	 */
	private void prepareNewProfile(ProfileForm pForm) {
    	pForm.getProfileToEdit().setProfileId(null);
    	pForm.getProfileToEdit().setVersionNumber(null);
    	pForm.getProfileToEdit().setObjectId(null);
    	prepareNewAddress(pForm);
	}

	private void prepareNewAddress(ProfileForm pForm) {
		pForm.getShipToAddress().setAddressId(null);
    	pForm.getShipToAddress().setVersionNumber(null);
    	pForm.getShipToAddress().setObjectId(null);
    	pForm.getShipToAddress().setActive(true);
    	pForm.getBillingAddress().setAddressId(null);
    	pForm.getBillingAddress().setVersionNumber(null);
    	pForm.getBillingAddress().setObjectId(null);
    	pForm.getBillingAddress().setActive(true);
	}

	private void saveNewProfile(ProfileForm pForm, HttpServletRequest request) {
		ShopCartProfileService profileService = ShopCartServiceLocator.getShopCartProfileService();
		profileService.setShippingAddress(pForm.getProfileToEdit(), pForm.getShipToAddress());
		profileService.setBillingAddress(pForm.getProfileToEdit(), pForm.getBillingAddress());
		profileService.saveCustomerProfile(pForm.getProfileToEdit());
		
		KualiConfigurationService configService = KNSServiceLocator.getKualiConfigurationService();
		pForm.setProfileMessage(configService.getPropertyString(ShopCartKeyConstants.MESSAGE_PROFILE_SAVED));
		if(pForm.getCustomerProfile() == null || StringUtils.equals(pForm.getProfileToEdit().getProfileId(), pForm.getCustomerProfile().getProfileId())) {
		    pForm.setCustomerProfile(pForm.getProfileToEdit());
		    request.getSession().setAttribute(ShopCartConstants.Session.CUSTOMER_PROFILE, pForm.getCustomerProfile());
		    resetBrowseManager(pForm, request);
		}
	}

	/**
	 * @param pForm
	 * @return
	 */
	private boolean isProfileFormValid(ProfileForm pForm) {
		DictionaryValidationService validationService = KNSServiceLocator.getDictionaryValidationService();

		boolean isValid = validationService.isBusinessObjectValid(pForm.getProfileToEdit(), "profileToEdit");
		isValid &=  validationService.isBusinessObjectValid(pForm.getShipToAddress(), "shipToAddress");
		isValid &=  validationService.isBusinessObjectValid(pForm.getBillingAddress(), "billingAddress");

        isValid &= ShopCartServiceLocator.getShopCartProfileService().isValidNewCustomerProfile(pForm.getProfileToEdit());

		return isValid;
	}
}
