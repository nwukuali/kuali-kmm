package org.kuali.ext.mm.cart.web.struts;

import java.util.Collection;

import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;



public class SettingsForm extends StoresShoppingFormBase {

	/**
     *
     */
    private static final long serialVersionUID = 989571894000490919L;
    private Profile currentProfile;

	public Collection<Profile> getAvailableProfiles() {
		return ShopCartServiceLocator.getShopCartProfileService().getActiveProfiles(getCustomerProfile().getCustomer());
	}

	public void setCurrentProfile(Profile currentProfile) {
		this.currentProfile = currentProfile;
	}

	public Profile getCurrentProfile() {
		return currentProfile;
	}
}
