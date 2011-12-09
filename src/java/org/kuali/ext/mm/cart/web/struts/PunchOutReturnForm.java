package org.kuali.ext.mm.cart.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.rice.kns.util.KualiDecimal;


public class PunchOutReturnForm extends ShopCartFormBase {

    private static final long serialVersionUID = 2200845992668180749L;
    
    private ShoppingCart punchOutCart;
    
    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
        
        setPunchOutCart((ShoppingCart)request.getSession()
                .getAttribute(ShopCartConstants.Session.PUNCHOUT_RETURN_CART));
        buildItemAuthorizationMap(punchOutCart);
        request.getSession().removeAttribute(ShopCartConstants.Session.PUNCHOUT_RETURN_CART);
        ShopCartService shopCartService = ShopCartServiceLocator.getShopCartService();
        setSubTotal(shopCartService.computeShopCartSubtotal(getPunchOutCart()));
        setTaxTotal(new KualiDecimal(shopCartService.computeShopCartTaxTotal(getPunchOutCart())));
        setTotal(shopCartService.computeShopCartTotal(getPunchOutCart()));
    }
    
    public ShoppingCart getPunchOutCart() {
        return punchOutCart;
    }


    public void setPunchOutCart(ShoppingCart punchOutCart) {
        this.punchOutCart = punchOutCart;
    }


   
}
