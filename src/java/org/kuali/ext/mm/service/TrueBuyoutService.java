package org.kuali.ext.mm.service;

import java.util.List;

import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ShopCartDetail;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.document.TrueBuyoutDocument;


public interface TrueBuyoutService {
    
    public void processTrueBuyoutDocument(TrueBuyoutDocument document);
   
    public TrueBuyoutDocument createTrueBuyoutDocument(ShoppingCart cart, Profile customerProfile, List<ShopCartDetail> details);
    
    public CatalogItem getDummyTrueBuyoutCatalogItem(String catalogId);
    
    public void routeTrueBuyoutDocument(TrueBuyoutDocument document);
    
    public void inactivateTrueBuyoutStock(OrderDocument document);
    
    public void finalizeTrueBuyoutStock(OrderDocument document);
    
}
