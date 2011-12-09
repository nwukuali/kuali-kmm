/**
 *
 */
package org.kuali.ext.mm.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.kuali.ext.mm.businessobject.ShoppingFrontPage;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.ShoppingFrontPageService;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.TransactionalServiceUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author schneppd
 *
 */
@Transactional
public class ShoppingFrontPageServiceImpl implements ShoppingFrontPageService {

	private BusinessObjectService businessObjectService;

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}
	
	/**
     * @see org.kuali.ext.mm.service.impl.ShoppingFrontPageService#setAsCurrentFrontPage(org.kuali.ext.mm.businessobject.ShoppingFrontPage)
     */
	public void setAsCurrentFrontPage(ShoppingFrontPage frontPage) {
	    Map<String,Object> fieldValues = new HashMap<String,Object>();        
        fieldValues.put(MMConstants.ShoppingFrontPage.CURRENT, "Y");
        
        Collection<ShoppingFrontPage> results = getBusinessObjectService().findMatching(ShoppingFrontPage.class, fieldValues);
        Iterator<ShoppingFrontPage> frontPageIt = results.iterator();
        ShoppingFrontPage previousFrontPage = null;
        while(frontPageIt.hasNext()) {
            previousFrontPage = frontPageIt.next();
    	    if(!previousFrontPage.getFrontPageId().equals(frontPage.getFrontPageId())) {
    	        previousFrontPage.setCurrent(false);
    	        getBusinessObjectService().save(previousFrontPage);
    	    }
        }
	    frontPage.setCurrent(true);
	}

    /**
     * @see org.kuali.ext.mm.service.impl.ShoppingFrontPageService#getCurrentShoppingFrontPage()
     */
    public ShoppingFrontPage getCurrentShoppingFrontPage() {
        Map<String,Object> fieldValues = new HashMap<String,Object>();        
        fieldValues.put(MMConstants.ShoppingFrontPage.CURRENT, "Y");
        fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
        
        Collection<ShoppingFrontPage> results = getBusinessObjectService().findMatching(ShoppingFrontPage.class, fieldValues);
        return TransactionalServiceUtils.retrieveFirstAndExhaustIterator(results.iterator());
    }
}
