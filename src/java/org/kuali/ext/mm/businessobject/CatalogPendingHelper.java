/**
 *
 */
package org.kuali.ext.mm.businessobject;

import org.kuali.ext.mm.document.CatalogPending;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;

/**
 * @author rshrivas
 */
public class CatalogPendingHelper extends CatalogPending {

    /**
     *
     */
    private static final long serialVersionUID = -2655224732673949695L;

    public CatalogPendingHelper() {

    }

    @Override
    public void doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) {
        super.doRouteStatusChange(statusChangeEvent);
    }
}
