package org.kuali.ext.mm.cart.web.struts;

import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;



public class ProfileForm extends StoresShoppingFormBase {

	/**
     *
     */
    private static final long serialVersionUID = -690984531801654150L;

    private Profile profileToEdit;

	private Address shipToAddress;

	private Address billingAddress;

	private String profileMessage;

	private boolean hideInternalInfo;

	public ProfileForm() {
	}

	@Override
	public void populate(HttpServletRequest request) {
		super.populate(request);

		if(!ObjectUtils.isNull(getCustomer()))
			getCustomer().refreshReferenceObject("profiles");

		Person user = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(getCustomer().getPrincipalName());
		setHideInternalInfo(user == null);

	}

	public void setProfileToEdit(Profile profileToEdit) {
		this.profileToEdit = profileToEdit;
	}

	public Profile getProfileToEdit() {
		return profileToEdit;
	}

	public void setShipToAddress(Address shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public Address getShipToAddress() {
		return shipToAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public boolean isDefaultProfile() {
		Profile defaultProfile = ShopCartServiceLocator.getShopCartProfileService().getDefaultCustomerProfile(getCustomer().getPrincipalName());

		if(defaultProfile != null && defaultProfile.getProfileId().equals(getProfileToEdit().getProfileId()))
			return true;

		return false;
	}

	public void setProfileMessage(String profileMessage) {
		this.profileMessage = profileMessage;
	}

	public String getProfileMessage() {
		return profileMessage;
	}

    public void setHideInternalInfo(boolean hideInternalInfo) {
        this.hideInternalInfo = hideInternalInfo;
    }

    public boolean isHideInternalInfo() {
        return hideInternalInfo;
    }
    
    @Override
    protected boolean requiresProfile() {
        return false;
    }
}
