/**
 * 
 */
package org.kuali.ext.mm.document.web.struts;

import java.util.ArrayList;
import java.util.List;

import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.rice.kns.web.struts.form.KualiForm;

/**
 * @author harsha07
 */
public class UnassignBinForm extends KualiForm {
    private static final long serialVersionUID = 5990327890313948098L;

    private String stockId;

    private CatalogItem catalogItem;

    private List<StockBalance> emptyLocations = new ArrayList<StockBalance>();
    
    private boolean readOnly;

    public List<StockBalance> getEmptyLocations() {
        return this.emptyLocations;
    }

    public void setEmptyLocations(List<StockBalance> emptyLocations) {
        this.emptyLocations = emptyLocations;
    }

    public String getStockId() {
        return this.stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public CatalogItem getCatalogItem() {
        return this.catalogItem;
    }

    public void setCatalogItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }


}
