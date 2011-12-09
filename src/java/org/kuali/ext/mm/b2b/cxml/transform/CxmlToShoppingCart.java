/**
 *
 */
package org.kuali.ext.mm.b2b.cxml.transform;

import java.util.ArrayList;
import java.util.List;

import org.kuali.ext.mm.b2b.cxml.types.ItemIn;
import org.kuali.ext.mm.b2b.cxml.types.PunchOutOrderMessage;
import org.kuali.ext.mm.businessobject.ShopCartDetail;
import org.kuali.ext.mm.businessobject.ShoppingCart;


/**
 * @author harsha07
 */
public class CxmlToShoppingCart implements CxmlTransformer<ShoppingCart, PunchOutOrderMessage> {

    /**
     * @see org.kuali.ext.mm.b2b.cxml.transform.CxmlTransformer#transform(java.lang.Object)
     */
    public ShoppingCart transform(PunchOutOrderMessage orderMessage, Object... options) {
        ShoppingCart cart = new ShoppingCart();
        List<ItemIn> itemIns = orderMessage.getItemIn();
        List<ShopCartDetail> shopCartDetails = new ArrayList<ShopCartDetail>();
        CxmlToShoppingCartDetail cxmlToShoppingCartDetail = new CxmlToShoppingCartDetail();
        for (ItemIn itemLine : itemIns) {
            shopCartDetails.add(cxmlToShoppingCartDetail.transform(itemLine));
        }
        cart.setShopCartDetails(shopCartDetails);
        return cart;
    }
}
