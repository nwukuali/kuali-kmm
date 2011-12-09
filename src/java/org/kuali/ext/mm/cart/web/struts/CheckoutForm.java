package org.kuali.ext.mm.cart.web.struts;

import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.ObjectUtils;



public class CheckoutForm extends ShopCartFormBase {

	/**
     *
     */
    private static final long serialVersionUID = -6033634396541779151L;

    private Boolean sameAsShipping;

	private String cardNumber;

	private String cardholderName;

	private String expireMonth;

	private String expireYear;

	private String cardType;

	private Boolean validated;

	private Profile checkOutAsProfile;

	private String profileId;
	
	@Override
    public void populate(HttpServletRequest request) {
	    super.populate(request);

        if(getValidated() == null) {
        	setValidated(false);
        }

        if(getSameAsShipping() == null)
        	setSameAsShipping(false);

	}

	public Integer getCurrentYear() {
		return getCurrentDate().get(Calendar.YEAR);
	}

	public Boolean getSameAsShipping() {
		return sameAsShipping;
	}

	public void setSameAsShipping(Boolean sameAsShipping) {
		this.sameAsShipping = sameAsShipping;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getExpireMonth() {
		return expireMonth;
	}

	public void setExpireMonth(String expireMonth) {
		this.expireMonth = expireMonth;
	}

	public String getExpireYear() {
		return expireYear;
	}

	public void setExpireYear(String expireYear) {
		this.expireYear = expireYear;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Boolean getValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public void setCheckOutAsProfile(Profile checkOutAsProfile) {
		this.checkOutAsProfile = checkOutAsProfile;
	}

	public Profile getCheckOutAsProfile() {
		return checkOutAsProfile;
	}

	public Collection<Profile> getAvailableProfiles() {
		return ShopCartServiceLocator.getShopCartProfileService().getActiveProfiles(getCustomerProfile().getCustomer());
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getProfileId() {
		return profileId;
	}
	
	@Override
	protected Profile getAuthorizationProfile() {
	    if(ObjectUtils.isNull(getCheckOutAsProfile()) || StringUtils.isBlank(getCheckOutAsProfile().getProfileId())) {
            setCheckOutAsProfile(getCustomerProfile());
            ShopCartServiceLocator.getShopCartService().setShoppingCartCustomerInfo(getSessionCart(), getCheckOutAsProfile());
            setProfileId(getCheckOutAsProfile().getProfileId());
        }
        else {
            setCheckOutAsProfile((Profile)KNSServiceLocator.getBusinessObjectService().retrieve(getCheckOutAsProfile()));
            if(!StringUtils.equals(getProfileId(), getCheckOutAsProfile().getProfileId())) {
                ShopCartServiceLocator.getShopCartService().setShoppingCartCustomerInfo(getSessionCart(), getCheckOutAsProfile());
                setProfileId(getCheckOutAsProfile().getProfileId());
            }
        }
        return getCheckOutAsProfile();
    }

	/**
	 * @see org.kuali.ext.mm.cart.web.struts.StoresShoppingFormBase#isForceWillCall()
	 */
	@Override
	public boolean isForceWillCall() {
		if(getCheckOutAsProfile() != null && getCheckOutAsProfile().isPersonalUseIndicator())
			return MMConstants.Parameters.FORCE_WILLCALL_ON_PERSONAL_USE;

		return false;
	}

}
