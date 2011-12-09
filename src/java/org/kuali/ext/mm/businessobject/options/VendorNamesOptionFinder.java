/**
 * 
 */
package org.kuali.ext.mm.businessobject.options;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.lookup.valueFinder.ValueFinder;
import org.kuali.rice.kns.service.BusinessObjectService;


/**
 * @author rshrivas
 *
 */
public class VendorNamesOptionFinder extends KeyValuesBase implements ValueFinder {

    /**
     * Returns a list of key/value pairs for this ValueFinder, in this case no pending entries, approved pending entries, and all pending entries
     * @return a List of key/value pairs to populate a control
     * @see org.kuali.rice.kns.lookup.keyvalues.KeyValuesFinder#getKeyValues()
     */
    public List<KeyLabelPair> getKeyValues() {
        BusinessObjectService bOService = SpringContext.getBean(BusinessObjectService.class);

        List<Catalog> catalogList = (List<Catalog>) bOService.findAll(Catalog.class);
        List<KeyLabelPair> labels = new ArrayList<KeyLabelPair>();
        labels.add(new KeyLabelPair("", ""));
        labels.add(new KeyLabelPair("Select All", "Select All"));
        for (Iterator iterator = catalogList.iterator(); iterator.hasNext();) {
            Catalog catalog = (Catalog) iterator.next();
            if(!catalog.getCatalogTypeCd().equalsIgnoreCase("1")){
                labels.add(new KeyLabelPair(catalog.getCatalogDesc(), catalog.getCatalogDesc()));
            }
        }              
       return labels;
    }     

    /**
     * Returns the default value for this ValueFinder, in this case OPTION_TRAN_DISCREPANCY
     * @return the key of the default value
     * @see org.kuali.rice.kns.lookup.valueFinder.ValueFinder#getValue()
     */
    public String getValue() {
        return "";
    }

}
