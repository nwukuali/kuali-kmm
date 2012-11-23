package org.kuali.ext.mm.cart.web.struts;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Customer;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.cart.valueobject.BrowseManager;
import org.kuali.ext.mm.cart.valueobject.ConfirmAction;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.web.struts.form.KualiForm;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;


public class StoresShoppingFormBase extends KualiForm {
	/**
     *
     */
    private static final long serialVersionUID = 669519195783992619L;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StoresShoppingFormBase.class);

	private ShoppingCart sessionCart;
	private Customer customer;
	private Profile customerProfile;
	private BrowseManager browseManager;
	
	private ConfirmAction confirmAction;

	private Calendar currentDate;
	
	private boolean orderCompletionWaiting;

	public StoresShoppingFormBase() {
		super();
	}

	@Override
    public void populate(HttpServletRequest request) {	    
	              
	    initializeCustomer(request);
	    
	    initializeCustomerProfile(request);

        if(request.getSession().getAttribute(ShopCartConstants.Session.BROWSE_MANAGER) == null) {
        	request.getSession().setAttribute(ShopCartConstants.Session.BROWSE_MANAGER, new BrowseManager(getCustomerProfile()));
        }
        setBrowseManager((BrowseManager)request.getSession().getAttribute(ShopCartConstants.Session.BROWSE_MANAGER));

        setConfirmAction((ConfirmAction)request.getSession().getAttribute(ShopCartConstants.Session.CONFIRM_ACTION));

        setCurrentDate(new java.util.GregorianCalendar());
        
        setOrderCompletionWaiting(!MMUtil.isCollectionEmpty(
                ShopCartServiceLocator.getShopCartOrderService()
                    .getIncompleteOrders(getCustomerProfile())));

        super.populate(request);
	}
	
	public void initializeCustomer(HttpServletRequest request) {
	    setCustomer((Customer)request.getSession().getAttribute(ShopCartConstants.Session.CUSTOMER));
        if(getCustomer() == null || !StringUtils.equals(getCustomer().getPrincipalName(), GlobalVariables.getUserSession().getPrincipalName())) {
            Customer loggedInCustomer = ShopCartServiceLocator.getShopCartCustomerService().getLoggedInCustomer();
            request.getSession().setAttribute(ShopCartConstants.Session.CUSTOMER, loggedInCustomer);
            setCustomer((Customer)request.getSession().getAttribute(ShopCartConstants.Session.CUSTOMER));
            request.getSession().setAttribute(ShopCartConstants.Session.CUSTOMER_PROFILE, null);
            request.getSession().setAttribute(ShopCartConstants.Session.SESSION_SHOP_CART, null);
            if(!ShopCartConstants.User.SHOP_GUEST.equals(GlobalVariables.getUserSession().getPrincipalName()))
                request.getSession().setAttribute(ShopCartConstants.Session.BROWSE_MANAGER, null);
        }      
	}

	public void initializeCustomerProfile(HttpServletRequest request) {
	    if(request.getSession().getAttribute(ShopCartConstants.Session.CUSTOMER_PROFILE) == null) {
            String principalName = (getCustomer() == null) ? null : getCustomer().getPrincipalName();
            request.getSession().setAttribute(ShopCartConstants.Session.CUSTOMER_PROFILE, ShopCartServiceLocator.getShopCartProfileService().getDefaultCustomerProfile(principalName));
        }
        setCustomerProfile((Profile)request.getSession().getAttribute(ShopCartConstants.Session.CUSTOMER_PROFILE));

        if(getCustomerProfile() != null) {
            setSessionCart((ShoppingCart)request.getSession().getAttribute(ShopCartConstants.Session.SESSION_SHOP_CART));
            if(getSessionCart() == null || getSessionCart().isOrdered()) {
               request.getSession().setAttribute(ShopCartConstants.Session.SESSION_SHOP_CART, new ShoppingCart());
               setSessionCart((ShoppingCart)request.getSession().getAttribute(ShopCartConstants.Session.SESSION_SHOP_CART));
               ShopCartServiceLocator.getShopCartService().setShoppingCartCustomerInfo(getSessionCart(), getCustomerProfile());
            }
            if(ObjectUtils.isNull(getCustomerProfile().getCustomer())) {
                getCustomerProfile().refreshReferenceObject(MMConstants.Profile.CUSTOMER);
            }
        }
	}
	
	public void setSessionCart(ShoppingCart sessionCart) {
		this.sessionCart = sessionCart;
	}

	public ShoppingCart getSessionCart() {
		return sessionCart;
	}

	public void setCustomerProfile(Profile customerProfile) {
		this.customerProfile = customerProfile;
	}

	public Profile getCustomerProfile() {
		return customerProfile;
	}

	public Calendar getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Calendar currentDate) {
		this.currentDate = currentDate;
	}

	public void setBrowseManager(BrowseManager browseManager) {
		this.browseManager = browseManager;
	}

	public BrowseManager getBrowseManager() {
		return browseManager;
	}

	public void setConfirmAction(ConfirmAction confirmAction) {
		this.confirmAction = confirmAction;
	}

	public ConfirmAction getConfirmAction() {
		return confirmAction;
	}

	public ConfirmAction createNewConfirmAction(HttpServletRequest request) {
		setConfirmAction( new ConfirmAction());
		request.getSession().setAttribute(ShopCartConstants.Session.CONFIRM_ACTION, getConfirmAction());
		return getConfirmAction();
	}

	public void clearConfirmAction(HttpServletRequest request) {
		request.getSession().removeAttribute(ShopCartConstants.Session.CONFIRM_ACTION);
		setConfirmAction(null);
	}

	public int getCartItemCount() {
		if(getSessionCart() != null)
			return getSessionCart().getShopCartDetails().size();

		return 0;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	public boolean isInternalProfile() {
		return getCustomerProfile() != null && !getCustomerProfile().isPersonalUseIndicator();
	}

	public boolean isExternalUser() {
		return getCustomer() != null && ShopCartServiceLocator.getShopCartCustomerService().isExternalUser(getCustomer());
	}

	/**
	 * @return true if Profile is personal use and 'force will  call' system parameter is set to true, else return false.
	 */
	public boolean isForceWillCall() {
		if(getCustomerProfile() != null && getCustomerProfile().isPersonalUseIndicator())
			return MMConstants.Parameters.FORCE_WILLCALL_ON_PERSONAL_USE;

		return false;
	}	

    public boolean isAllowsPersonalUse() {
        return ShopCartServiceLocator.getShopCartProfileService().isPersonalUseProfileEnabled(getCustomer());
    }
    
    public String getShoppingBaseUrl() {
        return KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(MMKeyConstants.Shopping.SHOPPING_URL);
    }

    public boolean isOrderCompletionWaiting() {
        return orderCompletionWaiting;
    }

    public void setOrderCompletionWaiting(boolean orderCompletionWaiting) {
        this.orderCompletionWaiting = orderCompletionWaiting;
    }
    
    protected boolean requiresProfile() {
        return true;
    }

}
