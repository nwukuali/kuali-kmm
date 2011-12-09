package org.kuali.ext.mm.cart.web.struts;

import java.util.Collection;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;



public class PunchOutForm extends StoresShoppingFormBase {

    private static final long serialVersionUID = 2200845992668180749L;
    
    private String punchOutUrl;
    
    private Collection<Catalog> punchOutCatalogs;
    
    public PunchOutForm() {
        setPunchOutCatalogs(ShopCartServiceLocator.getShopCartCatalogService().getAvailableCatalogsPunchOut(getCustomerProfile()));
    }


    public void setPunchOutUrl(String punchOutUrl) {
        this.punchOutUrl = punchOutUrl;
    }

    public String getPunchOutUrl() {
        return punchOutUrl;
    }


    public void setPunchOutCatalogs(Collection<Catalog> punchOutCatalogs) {
        this.punchOutCatalogs = punchOutCatalogs;
    }


    public Collection<Catalog> getPunchOutCatalogs() {
        return punchOutCatalogs;
    }

}
