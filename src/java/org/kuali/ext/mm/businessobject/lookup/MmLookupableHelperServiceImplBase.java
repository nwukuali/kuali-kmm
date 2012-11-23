/**
 *
 */
package org.kuali.ext.mm.businessobject.lookup;

import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author harsha07
 */
public class MmLookupableHelperServiceImplBase extends KualiLookupableHelperServiceImpl {
    private boolean reverseSortOrder = true;
    /**
     *
     */
    private static final long serialVersionUID = -641958666987818380L;

    /**
     * @see org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl#getSearchResultsHelper(java.util.Map, boolean)
     */
    @Override
    protected List<? extends BusinessObject> getSearchResultsHelper(
            Map<String, String> fieldValues, boolean unbounded) {
        List<? extends BusinessObject> searchResultsHelper = super.getSearchResultsHelper(
                fieldValues, unbounded);
        if (reverseSortOrder) {
            Collections.reverse(searchResultsHelper);
        }
        return searchResultsHelper;
    }

    /**
     * Gets the reverseSortOrder property
     *
     * @return Returns the reverseSortOrder
     */
    public boolean isReverseSortOrder() {
        return this.reverseSortOrder;
    }

    /**
     * Sets the reverseSortOrder property value
     *
     * @param reverseSortOrder The reverseSortOrder to set
     */
    public void setReverseSortOrder(boolean reverseSortOrder) {
        this.reverseSortOrder = reverseSortOrder;
    }
}
