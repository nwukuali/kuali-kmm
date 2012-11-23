/**
 *
 */
package org.kuali.ext.mm.cart.service.impl;

import org.kuali.ext.mm.businessobject.Customer;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartProfileService;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.impl.ProfileServiceImpl;
import org.kuali.rice.coreservice.framework.CoreFrameworkServiceLocator;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


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
        boolean isAllowed = CoreFrameworkServiceLocator.getParameterService().getParameterValueAsBoolean(ShoppingCart.class, ShopCartConstants.Parameters.ALLOW_PERSONAL_USE);
        if(!isAllowed) {
            isAllowed = KimApiServiceLocator.getPermissionService().isAuthorized(
                    GlobalVariables.getUserSession().getPrincipalId(), MMConstants.MM_NAMESPACE,
                    ShopCartConstants.Permission.PERSONAL_USE_SHOPPING, null);
        }
        return isAllowed;
    }
}
