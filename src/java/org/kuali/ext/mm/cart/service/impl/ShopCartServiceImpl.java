package org.kuali.ext.mm.cart.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.b2b.cxml.types.CXML;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.*;
import org.kuali.ext.mm.cart.valueobject.DirectEntry;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.document.TrueBuyoutDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.TrueBuyoutService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DictionaryValidationService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AutoPopulatingList;

import java.util.*;


/**
 * @author schneppd
 *
 */
@Transactional
public class ShopCartServiceImpl implements ShopCartService {

    private BusinessObjectService businessObjectService;

    private ShopCartOrderService shopCartOrderService;
    
    private ShopCartCatalogService shopCartCatalogService;


    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#addCatalogItemToSessionCart(org.kuali.ext.mm.businessobject.ShoppingCart, org.kuali.ext.mm.businessobject.Profile, org.kuali.ext.mm.businessobject.CatalogItem, java.lang.Integer, boolean)
     */
    public void addCatalogItemToSessionCart(ShoppingCart cart, Profile profile, CatalogItem catalogItem,
            Integer quantity, boolean willCall) {
        ShopCartDetail shopCartDetail = new ShopCartDetail();

        //Make sure catalogItem is coming from an authorized Catalog.
        if(!ShopCartServiceLocator.getShopCartCatalogService().isCatalogAuthorized(catalogItem.getCatalog(), profile))
            return;
        
        if (quantity == null || !catalogItem.isActive())
            return;

        cart.setSessionCartSaved(false);
        for (ShopCartDetail detailCheck : cart.getShopCartDetails()) {
            if (detailCheck.getCatalogItem().getCatalogItemId().equals(
                    catalogItem.getCatalogItemId())) {
                detailCheck.setShopCartItemQty(detailCheck.getShopCartItemQty() + quantity);
                return;
            }
        }
        shopCartDetail.setShoppingCart(cart);
        shopCartDetail.setCatalogItem(catalogItem);
        shopCartDetail.setCatalogItemId(catalogItem.getCatalogItemId());
        shopCartDetail.setDistributorNumber(catalogItem.getDistributorNbr());
//        shopCartDetail.setFinObjectCode(catalogItem.getCatalog().getDefaultObjectCd());
        shopCartDetail.setShopCartItemQty(quantity);
        shopCartDetail.setItemUnitOfIssueCd(catalogItem.getCatalogUnitOfIssueCd());
        shopCartDetail.setManufacturerNbr(catalogItem.getManufacturerNbr());
        shopCartDetail.setShippingHt(catalogItem.getShippingHt());
        shopCartDetail.setShippingLh(catalogItem.getShippingLh());
        shopCartDetail.setShippingWd(catalogItem.getShippingWd());
        shopCartDetail.setShippingWgt(catalogItem.getShippingWgt());
        shopCartDetail.setShopCartItemDetailDesc(catalogItem.getCatalogDesc());
        shopCartDetail.setActive(true);
        // WillCall override if true in stock, otherwise, use the user specified value
        if (MMConstants.CatalogType.WAREHOUSE.equals(catalogItem.getCatalog().getCatalogTypeCd()))
            shopCartDetail.setWillcallInd((catalogItem.getStock().isWillcallInd() ? true : willCall));
        else
            shopCartDetail.setWillcallInd(willCall);

        shopCartDetail.setShopCartLineNbr(cart.getShopCartDetails().size() + 1);

        Agreement agreement = catalogItem.getCatalog().getAgreement();
        if (ObjectUtils.isNotNull(agreement)) {
            shopCartDetail.setVendorNm(agreement.getVendorNm());
            shopCartDetail.setVendorHeaderGeneratedId(agreement.getVndrHeaderGnrtdId());
            shopCartDetail.setVendorDetailAssignedId(agreement.getVndrDetailAsgnId());
        }

        shopCartDetail = computeShopCartDetailPricingInfo(shopCartDetail,
                profile);

        cart.getShopCartDetails().add(shopCartDetail);
    }
    
    public void addPunchOutShoppingCartDetailToSessionCart(ShopCartDetail detail, ShoppingCart cart, CXML cxml) {
        detail.setCxmlPayloadId(cxml.getPayloadID());
        detail.setShopCartLineNbr(cart.getShopCartDetails().size() + 1);
        detail.setShoppingCart(cart);
        detail.getCatalogItem().refreshReferenceObject(MMConstants.CatalogItem.CATALOG);
//        detail.setFinObjectCode(detail.getCatalogItem().getCatalog().getDefaultObjectCd());
        cart.getShopCartDetails().add(detail);
        cart.getCxmlPayloadIdMap().put(cxml.getPayloadID(), cxml);
    }
    
    public void addTrueBuyoutItemToSessionCart(DirectEntry trueBuyoutItem, ShoppingCart cart) {
        CatalogItem trueBuyoutCatalogItem = MMServiceLocator.getTrueBuyoutService()
                .getDummyTrueBuyoutCatalogItem(trueBuyoutItem.getCatalogId());
        if(trueBuyoutCatalogItem != null) {
            ShopCartDetail detail = new ShopCartDetail();
            detail.setShoppingCart(cart);
            detail.setCatalogItem(trueBuyoutCatalogItem);
            detail.setCatalogItemId(trueBuyoutCatalogItem.getCatalogItemId());
            detail.setDistributorNumber("N/A");
            detail.setShopCartItemQty(Integer.parseInt(trueBuyoutItem.getQuantity()));
            detail.setItemUnitOfIssueCd(trueBuyoutItem.getUnitOfIssue());
            detail.setShopCartItemDetailDesc(trueBuyoutItem.getCatalogDescription());
            detail.setActive(true);
            detail.setWillcallInd(trueBuyoutItem.isWillCall());
            detail.setShopCartLineNbr(cart.getShopCartDetails().size() + 1);
            detail.setShopCartItemCostAmt(new MMDecimal(0.0));
            detail.setShopCartItemPriceAmt(new MMDecimal(0.0));
            detail.setShopCartItemMarkupAmt(new MMDecimal(0.0));
            detail.setShopCartItemTaxAmt(new MMDecimal(0.0));
            detail.setShopCartItemAdditionalCostAmt(new MMDecimal(0.0));            
//            trueBuyoutCatalogItem.refreshReferenceObject(MMConstants.CatalogItem.CATALOG);
//            if(StringUtils.isBlank(trueBuyoutItem.getFinObjectCode()))
//                detail.setFinObjectCode(detail.getCatalogItem().getCatalog().getDefaultObjectCd());
//            else
//                detail.setFinObjectCode(trueBuyoutItem.getFinObjectCode());
            
            cart.getShopCartDetails().add(detail);
        }
    }
    

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#deleteSavedShopCart(org.kuali.ext.mm.businessobject.ShoppingCart)
     */
    public void deleteSavedShopCart(ShoppingCart cart) {
        if (ObjectUtils.isNotNull(cart)) {
            getBusinessObjectService().delete(cart);
        }
    }

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#computeShopCartLineTotal(org.kuali.ext.mm.businessobject.ShopCartDetail)
     */
    public Double computeShopCartLineTotal(ShopCartDetail detail) {
    	return (detail.getShopCartItemPriceAmt().doubleValue() 
    	            + detail.getShopCartItemAdditionalCostAmt().doubleValue())
    	                * detail.getShopCartItemQty().doubleValue();
    }

    public KualiDecimal computeShopCartLineTotalWithTax(ShopCartDetail detail) {
    	Double subTotal = computeShopCartLineTotal(detail);
    	Double totalTax = detail.getShopCartItemTaxAmt().doubleValue() * detail.getShopCartItemQty().doubleValue();
    	return new KualiDecimal(subTotal + totalTax);
    }


    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#computeShopCartSubtotal(org.kuali.ext.mm.businessobject.ShoppingCart)
     */
    public KualiDecimal computeShopCartSubtotal(ShoppingCart cart) {
    	KualiDecimal subtotal = new KualiDecimal(0);

    	if(cart.getShopCartDetails() == null)
    	    cart.refreshReferenceObject(MMConstants.ShoppingCart.SHOP_CART_DETAILS);

    	if(cart.getShopCartDetails() != null) {
            for (ShopCartDetail detail : cart.getShopCartDetails()) {
            	subtotal = subtotal.add(new KualiDecimal(computeShopCartLineTotal(detail)));
            }
    	}

        return subtotal;
    }

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#computeShopCartTaxTotal(org.kuali.ext.mm.businessobject.ShoppingCart)
     */
    public Double computeShopCartTaxTotal(ShoppingCart cart) {
        Double taxTotal = new Double(0.0);
        for (ShopCartDetail detail : cart.getShopCartDetails()) {
            taxTotal = taxTotal + detail.getShopCartItemTaxAmt().doubleValue()
                    * detail.getShopCartItemQty().doubleValue();
        }
        return taxTotal;
    }

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#computeShopCartTotal(org.kuali.ext.mm.businessobject.ShoppingCart)
     */
    public KualiDecimal computeShopCartTotal(ShoppingCart cart) {
        Double total = computeShopCartSubtotal(cart).doubleValue();
        return new KualiDecimal(total + computeShopCartTaxTotal(cart));
    }

    public ShopCartDetail synchronizeWithCatalogItem(ShopCartDetail detail) {
        ShopCartDetail synchedDetail = detail;
        if (!detail.getCatalogItem().isActive()) {
            boolean foundCurrent = false;
            String catalogCode = detail.getCatalogItem().getCatalog().getCatalogCd();
            String distributorNumber = detail.getCatalogItem().getDistributorNbr();
            ShopCartCatalogService catalogService = SpringContext
                    .getBean(ShopCartCatalogService.class);
            Catalog catalog = catalogService.getCurrentCatalogByCatalogCode(catalogCode);
            if (catalog != null) {
                CatalogItem catalogItem = catalogService.getCatalogItem(distributorNumber, catalog
                        .getCatalogId());
                if (catalogItem != null) {
                    synchedDetail.setCatalogItem(catalogItem);
                    synchedDetail.setCatalogItemId(catalogItem.getCatalogItemId());
                    foundCurrent = true;
                }
            }
            if (!foundCurrent) {
                synchedDetail.setActive(false);
            }
        }
        return synchedDetail;
    }

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#updateShoppingCartPricingInfo(org.kuali.ext.mm.businessobject.ShoppingCart, org.kuali.ext.mm.businessobject.Profile, boolean)
     */
    public void updateShoppingCartPricingInfo(ShoppingCart cart, Profile profile, boolean withTax) {
        List<ShopCartDetail> updatedDetailList = new ArrayList<ShopCartDetail>();
        boolean isUnchanged;
        for (ShopCartDetail detail : cart.getShopCartDetails()) {
			ShopCartDetail updateDetail = detail;
        	ShopCartDetail oldDetail = (ShopCartDetail) getBusinessObjectService().retrieve(detail);
        	detail.getCatalogItem().refreshReferenceObject(MMConstants.CatalogItem.CATALOG);
        	String catalogTypeCode = detail.getCatalogItem().getCatalog().getCatalogTypeCd();
			if(MMConstants.CatalogType.WAREHOUSE.equals(catalogTypeCode)
			        || MMConstants.CatalogType.HOSTED.equals(catalogTypeCode)) {
        		updateDetail = computeShopCartDetailPricingInfo(detail, profile);
			}
        	if(withTax) {
        	    MMDecimal lineItemTotalPrice = detail.getShopCartItemPriceAmt().multiply(new MMDecimal(detail.getShopCartItemQty()));
        	    MMDecimal lineItemTotalTax = getShopCartCatalogService().computeCatalogItemTax(detail.getCatalogItem(), lineItemTotalPrice, cart.getShippingAddress(), detail.isWillcallInd());
        	    updateDetail.setShopCartItemTaxAmt(lineItemTotalTax.divide(new MMDecimal(detail.getShopCartItemQty())));
        	}
            if (ObjectUtils.isNull(oldDetail)) {
            	oldDetail = updateDetail;
            }
            isUnchanged = true;
            isUnchanged &= (updateDetail.isActive() == oldDetail.isActive());
            isUnchanged &= updateDetail.getShopCartItemPriceAmt().equals(
            		oldDetail.getShopCartItemPriceAmt());
            isUnchanged &= updateDetail.getShopCartItemMarkupAmt().equals(
            		oldDetail.getShopCartItemMarkupAmt());
            isUnchanged &= updateDetail.getShopCartItemCostAmt().equals(
            		oldDetail.getShopCartItemCostAmt());
            isUnchanged &= updateDetail.getShopCartItemAdditionalCostAmt().equals(
            		oldDetail.getShopCartItemAdditionalCostAmt());
            isUnchanged &= updateDetail.getShopCartItemTaxAmt().equals(
            		oldDetail.getShopCartItemTaxAmt());

            if (!isUnchanged)
                getBusinessObjectService().save(updateDetail);
            
            updatedDetailList.add(updateDetail);
        }
        cart.setShopCartDetails(updatedDetailList);
    }

    /**
     * @param detail
     * @param profile
     * @param billingAddress
     * @return a ShopCartDetail object with recomputed pricing information
     */
    private ShopCartDetail computeShopCartDetailPricingInfo(ShopCartDetail detail, Profile profile) {

        detail = synchronizeWithCatalogItem(detail);
        if(detail.isActive()) {
	        detail.setShopCartItemCostAmt(detail.getCatalogItem().getCatalogPrc());
	        detail.setShopCartItemAdditionalCostAmt(computeShopCartDetailAddCostAmount(detail));
	        detail.setShopCartItemPriceAmt(getShopCartCatalogService().computeCatalogItemPrice(detail.getCatalogItem(), profile, detail.getShopCartItemQty()));
	        detail.setShopCartItemMarkupAmt(detail.getShopCartItemPriceAmt().subtract(detail.getShopCartItemCostAmt()));
	        detail.setShopCartItemTaxAmt(new MMDecimal(0.0));
        }
        else {
	        detail.setShopCartItemCostAmt(new MMDecimal(0.0));
            detail.setShopCartItemAdditionalCostAmt(new MMDecimal(0.0));
            detail.setShopCartItemMarkupAmt(new MMDecimal(0.0));
            detail.setShopCartItemPriceAmt(new MMDecimal(0.0));
            detail.setShopCartItemTaxAmt(new MMDecimal(0.0));
            detail.setShopCartItemQty(0);
        }
        return detail;
    }

    /**
     * @param detail
     * @return Additional cost amount for the ShopCartDetail
     */
    private MMDecimal computeShopCartDetailAddCostAmount(ShopCartDetail detail) {
        MMDecimal addCostAmount = new MMDecimal(0.0);
        
        if(detail.getCatalogItem().getStock() != null 
                && detail.getCatalogItem().getStock().isRental()) {
            addCostAmount = addCostAmount.add(detail.getCatalogItem().getStock().getRentalObject().getDepositPrice());
        }
        
        return addCostAmount;
    }

    /**
     * Sets the BusinessObjectService
     *
     * @param businessObjectService
     */
    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    /**
     * Gets the BusinessObjectService
     *
     * @return businessObjectService
     */
    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    /**
     * Sets the ShopCartOrderService
     *
     * @param shopCartOrderService
     */
    public void setShopCartOrderService(ShopCartOrderService shopCartOrderService) {
        this.shopCartOrderService = shopCartOrderService;
    }

    /**
     * Gets the ShopCartOrderService
     *
     * @return shopCartOrderService
     */
    public ShopCartOrderService getShopCartOrderService() {
        return shopCartOrderService;
    }

    public void setShopCartCatalogService(ShopCartCatalogService shopCartCatalogService) {
		this.shopCartCatalogService = shopCartCatalogService;
	}

	public ShopCartCatalogService getShopCartCatalogService() {
		return shopCartCatalogService;
	}

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#getSessionCartDetailById(org.kuali.ext.mm.businessobject.ShoppingCart,
     *      java.lang.String)
     */
    public ShopCartDetail getSessionCartDetailById(ShoppingCart cart, String shopCartDetailId) {
        for (ShopCartDetail detail : cart.getShopCartDetails()) {
            if (shopCartDetailId.equals(detail.getShopCartDetailId())) {
                return detail;
            }
        }
        return null;
    }


    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#getSessionCartDetailByLineNumber(org.kuali.ext.mm.businessobject.ShoppingCart,
     *      java.lang.Integer)
     */
    public Object getSessionCartDetailByLineNumber(ShoppingCart cart, Integer lineNumber) {
        for (ShopCartDetail detail : cart.getShopCartDetails()) {
            if (lineNumber.equals(detail.getShopCartLineNbr())) {
                return detail;
            }
        }
        return null;
    }

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#getSavedShoppingCarts(org.kuali.ext.mm.businessobject.Profile)
     */
    public List<ShoppingCart> getSavedShoppingCarts(Profile customerProfile) {
        List<ShoppingCart> savedCarts = new ArrayList<ShoppingCart>();
        if(customerProfile == null)
            return savedCarts;

        Map<String, String> fieldValues = new HashMap<String, String>();
//        fieldValues.put(ShopCartConstants.SessionCart.ORDERED_IND, "N");
        fieldValues.put(ShopCartConstants.SessionCart.CUSTOMER_PROFILE_ID, customerProfile.getProfileId());

        Collection results = getBusinessObjectService().findMatching(ShoppingCart.class,
                fieldValues);

        Iterator<ShoppingCart> it = results.iterator();
        while (it.hasNext()) {
            savedCarts.add(it.next());
        }

        return savedCarts;

    }

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#isCatalogItemInCart(org.kuali.ext.mm.businessobject.CatalogItem,
     *      org.kuali.ext.mm.businessobject.ShoppingCart)
     */
    public boolean isCatalogItemInCart(CatalogItem item, ShoppingCart cart) {
        if (cart != null) {
            for (ShopCartDetail detail : cart.getShopCartDetails()) {
                if (detail.getCatalogItem().getCatalogItemId().equals(item.getCatalogItemId()))
                    return true;
            }
        }
        return false;

    }

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#getCatalogItemInCartCount(org.kuali.ext.mm.businessobject.CatalogItem,
     *      org.kuali.ext.mm.businessobject.ShoppingCart)
     */
    public Integer getCatalogItemInCartCount(CatalogItem item, ShoppingCart cart) {
        for (ShopCartDetail detail : cart.getShopCartDetails()) {
            if (detail.getCatalogItem().getCatalogItemId().equals(item.getCatalogItemId()))
                return detail.getShopCartItemQty();
        }
        return 0;

    }

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#saveShoppingCart(org.kuali.ext.mm.businessobject.ShoppingCart, boolean)
     */
    public void saveShoppingCart(ShoppingCart cart, boolean asNew) {
        if (asNew) {
            cart.setShoppingCartId(StringUtils.EMPTY);
            for (ShopCartDetail detail : cart.getShopCartDetails()) {
                detail.setShopCartDetailId(StringUtils.EMPTY);
            }
        }

        getBusinessObjectService().save(cart);
    }

    public ShoppingCart setShoppingCartCustomerInfo(ShoppingCart cart, Profile profile) {
    	cart.setCustomerProfile(profile);
    	ShopCartProfileService profileService = ShopCartServiceLocator.getShopCartProfileService();
    	cart.setShippingAddress(profileService.getShippingAddress(profile));
    	cart.setBillingAddress(profileService.getBillingAddress(profile));
    	cart.setDeliveryBuildingCode(profile.getDeliveryBuildingCode());
    	cart.setDeliveryBuildingRoomNumber(profile.getDeliveryBuildingRoomNumber());
    	return cart;
    }


    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#processShoppingCart(org.kuali.ext.mm.businessobject.ShoppingCart, org.kuali.ext.mm.businessobject.Profile)
     */
    public List<OrderDocument> processShoppingCart(ShoppingCart cart, Profile customerProfile) {
        Map<String, List<ShopCartDetail>> detailMapByVendor = new HashMap<String, List<ShopCartDetail>>();
        List<OrderDocument> documents = new ArrayList<OrderDocument>();
        for (ShopCartDetail detail : cart.getShopCartDetails()) {
            if (!detail.isActive())
                continue;
            
            String vendorKey = getVendorKeyFromDetail(detail);
            
            if(StringUtils.isNotBlank(vendorKey)) {
                if (detailMapByVendor.containsKey(vendorKey)) {
                    detailMapByVendor.get(vendorKey)
                            .add(detail);
                }
                else {
                    List<ShopCartDetail> detailList = new AutoPopulatingList(ShopCartDetail.class);
                    detailList.add(detail);
                    detailMapByVendor.put(vendorKey, detailList);
                }
            }
        }

        documents.addAll(createOrderDocumentsFromDetailsMap(cart, customerProfile, detailMapByVendor));

        return documents;
    }
    
    /**
     * @param detail
     * @return a string representation of the vendorNumber or warehouse code for detail.
     */
    private String getVendorKeyFromDetail(ShopCartDetail detail) {
        String vendorNumber = null;
        String detailCatalogTypeCode = detail.getCatalogItem().getCatalog().getCatalogTypeCd();
        //Build vendorKey by catalogType
        if (MMConstants.CatalogType.WAREHOUSE.equals(detailCatalogTypeCode)) {
            vendorNumber = MMConstants.CatalogType.WAREHOUSE
                    + MMConstants.VENDOR_NBR_SEPARATOR
                    + detail.getCatalogItem().getCatalog().getWarehouseCd();
        }
        else if(MMConstants.CatalogType.PUNCHOUT.equals(detailCatalogTypeCode)) {
            PunchOutVendor punchOutVendor = MMServiceLocator.getPunchOutVendorService().getPunchOutVendorByCatalogId(detail.getCatalogItem().getCatalogId());
            vendorNumber = MMConstants.CatalogType.PUNCHOUT
                    + MMConstants.VENDOR_NBR_SEPARATOR
                    + punchOutVendor.getVendorCredentialDomain()
                    + MMConstants.VENDOR_NBR_SEPARATOR
                    + punchOutVendor.getVendorIdentity();                    
        }
        else if(MMConstants.CatalogType.HOSTED.equals(detailCatalogTypeCode)) {
            vendorNumber = MMConstants.CatalogType.HOSTED
                    + MMConstants.VENDOR_NBR_SEPARATOR
                    + detail.getCatalogItem().getCatalog().getCatalogCd();
        }
        else if(MMConstants.CatalogType.TRUE_BUYOUT.equals(detailCatalogTypeCode)) {
            vendorNumber = MMConstants.CatalogType.TRUE_BUYOUT;
        }
        return vendorNumber;
    }

    /**
     * Creates and routes OrderDocuments separated by vendor.  If the detailsMap contains any
     * True Buyout items, a TrueBuyoutDocument is created and routed; no order documents for 
     * those items are created. 
     * 
     * @param cart
     * @param customerProfile
     * @param detailsMap
     * @return a list of saved or routed OrderDocument objects created from detailsMap
     */
    private List<OrderDocument> createOrderDocumentsFromDetailsMap(ShoppingCart cart, Profile customerProfile, Map<String, List<ShopCartDetail>> detailsMap) {
        List<OrderDocument> documents = new ArrayList<OrderDocument>();
        TrueBuyoutService trueBuyoutService = MMServiceLocator.getTrueBuyoutService();
        for (String key : detailsMap.keySet()) {
            if(MMConstants.CatalogType.TRUE_BUYOUT.equals(detailsMap.get(key).get(0)
                    .getCatalogItem().getCatalog().getCatalogTypeCd())) {
                TrueBuyoutDocument tbDoc = trueBuyoutService.createTrueBuyoutDocument(cart, customerProfile, detailsMap.get(key));
                trueBuyoutService.routeTrueBuyoutDocument(tbDoc);
            }
            else {
                OrderDocument doc = shopCartOrderService.createNewOrderDocument(cart, customerProfile,
                    detailsMap.get(key));
                if(MMConstants.OrderDocument.PROFILE_TYPE_PERSONAL.equals(doc.getProfileTypeCode()))
                    shopCartOrderService.routeOrderDocument(doc);
                else
                    shopCartOrderService.saveOrderDocument(doc);
                documents.add(doc);
            }
        }
        return documents;
    }

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#validateNewShoppingCart(org.kuali.ext.mm.businessobject.ShoppingCart)
     */
    public boolean validateNewShoppingCart(ShoppingCart cart) {
        boolean isValid = true;
        DictionaryValidationService validationService = KRADServiceLocatorWeb
                .getDictionaryValidationService();
        isValid = validationService.isBusinessObjectValid(cart);
        
        for(ShopCartDetail detail : cart.getShopCartDetails())
            isValid &= !detail.isFromPunchOut();
        
        return isValid;
    }


    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartService#cleanShoppingCartPunchOutMap(org.kuali.ext.mm.businessobject.ShoppingCart)
     */
    public void cleanShoppingCartPunchOutMap(ShoppingCart cart) {
       Set<String> payloadIdSet = new HashSet<String>();
       for(ShopCartDetail detail : cart.getShopCartDetails()) {
           if(StringUtils.isNotBlank(detail.getCxmlPayloadId())) {
               payloadIdSet.add(detail.getCatalogItemId());
           }
       }        
       for(String payloadId : payloadIdSet)
           cart.getCxmlPayloadIdMap().remove(payloadId);
    }

}
