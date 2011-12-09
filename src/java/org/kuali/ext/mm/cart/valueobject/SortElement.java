/**
 * 
 */
package org.kuali.ext.mm.cart.valueobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author schneppd
 *
 */
public class SortElement {
    
    private Map<String, Boolean> sortAscendingMap = new HashMap<String, Boolean>();
    
    private List<String> sortFields = new ArrayList<String>();

    public List<String> getSortFields() {
        return sortFields;
    }
    
    public void addField(String field, Boolean sortAscending) {
        sortFields.add(field);
        sortAscendingMap.put(field, sortAscending != null ? sortAscending : false);
    }
    
    public void removeField(String field) {
        sortFields.remove(field);
        sortAscendingMap.remove(field);
    }
    
    public Boolean isFieldSortAscending(String field) {
        return sortAscendingMap.get(field);
    }
    
    
}
