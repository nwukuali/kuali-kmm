/**
 *
 */
package org.kuali.ext.mm.cart.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kuali.ext.mm.businessobject.Customer;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartProfileService;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.impl.ProfileServiceImpl;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author schneppd
 *
 */
@Transactional
public class ShopCartProfileServiceImpl extends ProfileServiceImpl implements ShopCartProfileService {

	public Collection<Profile> getActiveProfiles(Customer customer) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put(MMConstants.Profile.PRINCIPAL_NAME, customer.getPrincipalName().toLowerCase());
		fieldMap.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
		if(!isPersonalUseProfileEnabled(customer))
		    fieldMap.put(MMConstants.Profile.PERSONAL_USE_IND, "N");

		return getBusinessObjectService().findMatching(Profile.class, fieldMap);
	}

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartProfileService#isPersonalUseProfileEnabled(org.kuali.ext.mm.businessobject.Customer)
     */
    public boolean isPersonalUseProfileEnabled(Customer customer) {
        boolean isAllowed = KNSServiceLocator.getParameterService().getIndicatorParameter(ShoppingCart.class, ShopCartConstants.Parameters.ALLOW_PERSONAL_USE);
        if(!isAllowed) {
            isAllowed = KIMServiceLocator.getIdentityManagementService().isAuthorized(
                    GlobalVariables.getUserSession().getPrincipalId(), MMConstants.MM_NAMESPACE,
                    ShopCartConstants.Permission.PERSONAL_USE_SHOPPING, null, null);
        }
        return isAllowed;
    }
}
