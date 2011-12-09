/**
 * 
 */
package org.kuali.ext.mm.service;

import java.util.List;

import org.kuali.ext.mm.businessobject.Bin;
import org.kuali.ext.mm.businessobject.CatalogItem;

/**
 * This class provides a way to customize the bin selection process for generating
 * pick list lines.  Implement this interface and override the pickListHelperService
 * spring bean with that implementation to get an institution specific picking order.
 * 
 * 
 * @author schneppd
 *
 */
public interface PickListHelperService {

    /**
     * Override this method to provide a new sorted order or new paradigm
     * for determining bin priority.
     * 
     * @param item
     * @param quantity
     * @return List of Bins in sorted order for picking
     */
    public List<Bin> getBinsForPicking(CatalogItem item, Integer quantity);
    
}
