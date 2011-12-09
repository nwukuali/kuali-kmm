package org.kuali.ext.mm.cart.web.struts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.ShoppingFrontPage;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.ShoppingFrontPageService;




public class ShopCartHomeForm extends StoresShoppingFormBase {

    /**
     *
     */
    private static final long serialVersionUID = -1946064485563131049L;

    
    private List<CatalogItem> bestSellingList = new ArrayList<CatalogItem>();
    
    private Collection<Catalog> punchOutCatalogs;
    
	private ShoppingFrontPage frontPage;
    
    public ShopCartHomeForm() {
        setPunchOutCatalogs(ShopCartServiceLocator.getShopCartCatalogService().getAvailableCatalogsPunchOut(getCustomerProfile()));
    }

 	@Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
        setFrontPage(SpringContext.getBean(ShoppingFrontPageService.class).getCurrentShoppingFrontPage());
    }

    public void setBestSellingList(List<CatalogItem> bestSellingList) {
        this.bestSellingList = bestSellingList;
    }

    public List<CatalogItem> getBestSellingList() {
        return bestSellingList;
    }

    public void setPunchOutCatalogs(Collection<Catalog> punchOutCatalogs) {
        this.punchOutCatalogs = punchOutCatalogs;
    }

    public Collection<Catalog> getPunchOutCatalogs() {
        return punchOutCatalogs;
    }
    
    @Override
    protected boolean requiresProfile() {
        return false;
    }
    
    public ShoppingFrontPage getFrontPage() {
        return frontPage;
    }

    public void setFrontPage(ShoppingFrontPage frontPage) {
        this.frontPage = frontPage;
    }
}
