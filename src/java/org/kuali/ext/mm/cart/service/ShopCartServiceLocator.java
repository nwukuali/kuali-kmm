package org.kuali.ext.mm.cart.service;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;



public final class ShopCartServiceLocator {
                   
	private static final Logger LOG = Logger.getLogger(ShopCartServiceLocator.class);

	private static final String SHOPCART_SEARCH_STRING_BUILDER = "shopCartSearchStringBuilder";  

    /**
	 * @param serviceName
	 *            the name of the service bean
	 * @return the service
	 */
	public static Object getService(String serviceName) {
		return getBean(serviceName);
	}
	
	public static <T> T getBean(Class<T> clazz) {
	    return SpringContext.getBean(clazz);
	}

	public static Object getBean(String serviceName) {
		if ( LOG.isDebugEnabled() ) {
			LOG.debug("Fetching service " + serviceName);
		}
		return GlobalResourceLoader.getResourceLoader().getService(new QName(serviceName));
	}

	public static ShopCartCatalogService getShopCartCatalogService() {
	    return getBean(ShopCartCatalogService.class);
	}
	
	public static ShopCartCustomerService getShopCartCustomerService() {
        return getBean(ShopCartCustomerService.class);
    }
	
	public static ShopCartFavoriteService getShopCartFavoriteService() {
        return getBean(ShopCartFavoriteService.class);
    }
	
	public static ShopCartOrderService getShopCartOrderService() {
        return getBean(ShopCartOrderService.class);
    }
	
	public static ShopCartProfileService getShopCartProfileService() {
        return getBean(ShopCartProfileService.class);
    }
	
    public static ShopCartSearchService getShopCartSearchService() {
        return getBean(ShopCartSearchService.class);
    }
	
	public static ShopCartService getShopCartService() {
        return getBean(ShopCartService.class);
    }
	
	public static ShopCartSearchStringBuilder getShopCartSearchStringBuilder() {
        return (ShopCartSearchStringBuilder)getBean(SHOPCART_SEARCH_STRING_BUILDER);
    }
	
    public static ShopCartSuggestionService getShopCartSuggestionService() {
        return getBean(ShopCartSuggestionService.class);
    }	
}
