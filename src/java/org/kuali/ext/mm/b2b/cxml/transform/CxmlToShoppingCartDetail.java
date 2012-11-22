/**
 *
 */
package org.kuali.ext.mm.b2b.cxml.transform;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.b2b.cxml.types.Description;
import org.kuali.ext.mm.b2b.cxml.types.ItemDetail;
import org.kuali.ext.mm.b2b.cxml.types.ItemID;
import org.kuali.ext.mm.b2b.cxml.types.ItemIn;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;
import org.kuali.ext.mm.businessobject.ShopCartDetail;
import org.kuali.ext.mm.businessobject.UnitOfIssue;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.io.Serializable;
import java.util.List;

/**
 * @author harsha07
 */
public class CxmlToShoppingCartDetail implements CxmlTransformer<ShopCartDetail, ItemIn> {
   
    /**
     * @see org.kuali.ext.mm.b2b.cxml.transform.CxmlTransformer#transform(java.lang.Object)
     */
    public ShopCartDetail transform(ItemIn item, Object... options) {
        ShopCartDetail cartDetail = new ShopCartDetail();  
        ItemID itemID = item.getItemID();

        String supplierPartAuxId = CxmlUtil.unwrap(itemID.getSupplierPartAuxiliaryID()
                .getFirstContent());
        cartDetail.setSupplierPartAuxId(supplierPartAuxId);        
        cartDetail.setDistributorNumber(itemID.getSupplierPartID());
        cartDetail.setShopCartItemQty((int)Double.parseDouble(item.getQuantity()));
        ItemDetail itemDetail = item.getItemDetail();        
        String unitPriceAmount = itemDetail.getUnitPrice().getMoney().getContent();
        
        cartDetail.setShopCartItemCostAmt(new MMDecimal(unitPriceAmount));
        cartDetail.setShopCartItemPriceAmt(cartDetail.getShopCartItemCostAmt());
        cartDetail.setShopCartItemMarkupAmt(new MMDecimal(0.0));
        cartDetail.setShopCartItemTaxAmt(new MMDecimal(0.0));
        cartDetail.setShopCartItemAdditionalCostAmt(new MMDecimal(0.0));
        
        StringBuilder descString = new StringBuilder();        
        List<Description> descriptions = itemDetail.getDescription();
        for (Description description : descriptions) {
            List<Serializable> descContents = description.getContent();
            for (Serializable serializable : descContents) {
                descString.append(CxmlUtil.unwrap(serializable.toString()) + " ");
            }
        }
        cartDetail.setShopCartItemDetailDesc(StringUtils.left(CxmlUtil.cleanString(descString.toString()), 400));
        
        String unitOfMeasure = itemDetail.getUnitOfMeasure();
        UnitOfIssue unitOfIssue = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(UnitOfIssue.class, unitOfMeasure);
        if(unitOfIssue == null) {
            unitOfIssue = new UnitOfIssue();
            unitOfIssue.setUnitOfIssueCode(unitOfMeasure);
            unitOfIssue.setUnitOfIssueDesc(unitOfMeasure);
            if(StringUtils.isNotBlank(unitOfMeasure))
                unitOfIssue.save();
        }
        cartDetail.setItemUnitOfIssueCd(unitOfIssue.getUnitOfIssueCode());
        
        cartDetail.setActive(true);
        cartDetail.setFromPunchOut(true);
//        List<Classification> classifications = itemDetail.getClassification();
//        String unspcCode = null;
//        for (Classification classification : classifications) {
//            if ("UNSPSC".equalsIgnoreCase(classification.getDomain())) {
//                unspcCode = CxmlUtil.unwrap(classification.getContent());
//                break;
//            } 
//        }

        return cartDetail;
    }

}
