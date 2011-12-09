/**
 *
 */
package org.kuali.ext.mm.document.service.impl;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.service.CatalogItemMaintenanceService;
import org.kuali.rice.kns.service.SequenceAccessorService;


/**
 * @author harsha07
 */
public class CatalogItemMaintenanceServiceImpl implements CatalogItemMaintenanceService {

    private static final String MM_CATALOG_ITEM_SEQ = "mm_catalog_item_s";

    /**
     * @see org.kuali.ext.mm.document.service.CatalogItemMaintenanceService#getNextCatalogItemId()
     */
    public String getNextCatalogItemId() {
        return String.valueOf(SpringContext.getBean(SequenceAccessorService.class)
                .getNextAvailableSequenceNumber(MM_CATALOG_ITEM_SEQ));
    }


}
