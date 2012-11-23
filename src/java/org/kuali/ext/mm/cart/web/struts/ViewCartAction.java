package org.kuali.ext.mm.cart.web.struts;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.cart.valueobject.DirectEntry;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.UrlFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class ViewCartAction extends StoresShoppingActionBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ViewCartAction.class);

	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward deleteItem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ViewCartForm vcForm = (ViewCartForm)form;
        ConfigurationService configService = KRADServiceLocator.getKualiConfigurationService();

        if(vcForm.getSelectedItems() == null) {
            vcForm.setFormMessage(configService.getPropertyValueAsString(ShopCartKeyConstants.ERROR_NO_ITEMS_SELECTED));
        }
        else {
	        for(String itemId : vcForm.getSelectedItems()) {
	        	vcForm.getSessionCart().getShopCartDetails().remove(ShopCartServiceLocator.getShopCartService().getSessionCartDetailByLineNumber(vcForm.getSessionCart(), Integer.parseInt(itemId)));
	        	ShopCartServiceLocator.getShopCartService().cleanShoppingCartPunchOutMap(vcForm.getSessionCart());
	        }
	        vcForm.getSessionCart().setSessionCartSaved(false);
	        //re-index list
	        int i = 1;
            for(ShopCartDetail detail : vcForm.getSessionCart().getShopCartDetails()) {
                detail.setShopCartLineNbr(i++);
            }
	        vcForm.buildItemAuthorizationMap(vcForm.getSessionCart());
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward editItems(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ViewCartForm vcForm = (ViewCartForm)form;

        vcForm.setEditMode(true);
        //request.getSession().setAttribute("cartBackup", vcForm.getWebCart().getShoppingCart());


        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward updateItems(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ViewCartForm vcForm = (ViewCartForm)form;

        vcForm.setEditMode(false);
        vcForm.getSessionCart().setSessionCartSaved(false);
        //request.getSession().setAttribute("sessionShopCart", vcForm.getSessionCart());
        //request.getSession().removeAttribute("cartBackup");

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward clearCart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ViewCartForm vcForm = (ViewCartForm)form;

        vcForm.setSessionCart(new ShoppingCart());
        request.getSession().setAttribute(ShopCartConstants.Session.SESSION_SHOP_CART, vcForm.getSessionCart());

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward addToFavorites(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ViewCartForm vcForm = (ViewCartForm)form;
        ConfigurationService configService = KRADServiceLocator.getKualiConfigurationService();

        List<String> catalogItemIds = new ArrayList<String>();
        if(vcForm.getSelectedItems() == null) {
        	vcForm.setFormMessage(configService.getPropertyValueAsString(ShopCartKeyConstants.ERROR_NO_ITEMS_SELECTED));
        	return mapping.findForward(MMConstants.MAPPING_BASIC);
        }

        boolean containsPunchOutItems = false;
        for(String itemLineId : vcForm.getSelectedItems()) {
        	ShopCartDetail item = (ShopCartDetail)ShopCartServiceLocator.getShopCartService().getSessionCartDetailByLineNumber(vcForm.getSessionCart(), Integer.parseInt(itemLineId));
        	if(item.isFromPunchOut())
        	    containsPunchOutItems = true;
        	else 
        	    catalogItemIds.add(item.getCatalogItem().getCatalogItemId());
        }
        if(containsPunchOutItems) {
            vcForm.setFormMessage(configService.getPropertyValueAsString(ShopCartKeyConstants.ERROR_ADDING_PUNCHOUT_ITEMS_TO_FAVORITES));
            return mapping.findForward(MMConstants.MAPPING_BASIC);
        }

       	request.getSession().setAttribute(ShopCartConstants.Session.ADD_TO_FAVORITES_ITEMS, catalogItemIds);

		Properties parameters = new Properties();
        parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.ADD_TO_FAVORITES_METHOD);

        return new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.FAVORITES_ACTION, parameters), true);
    }

	public ActionForward addDirectEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ViewCartForm vcForm = (ViewCartForm)form;

        DirectEntry entry = vcForm.getDirectEntry();
        CatalogItem catalogItem;
        if(validateDirectEntry(entry)) {
        	catalogItem = retrieveCatalogItem(entry);
        	if(catalogItem != null) {
        		ShopCartServiceLocator.getShopCartService().addCatalogItemToSessionCart(vcForm.getSessionCart(), vcForm.getCustomerProfile(), catalogItem, Integer.parseInt(entry.getQuantity()), vcForm.getDirectEntry().isWillCall());
        		resetDirectEntryObject(vcForm.getDirectEntry());
                vcForm.getSessionCart().setSessionCartSaved(false);
                vcForm.buildItemAuthorizationMap(vcForm.getSessionCart());
        	}
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
	
	public ActionForward addTrueBuyoutEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ViewCartForm vcForm = (ViewCartForm)form;

        DirectEntry entry = vcForm.getTrueBuyoutEntry();
        if(vcForm.getBrowseManager().getTrueBuyoutCatalogs() != null) {
            Iterator<Catalog> catalogIt = vcForm.getBrowseManager().getTrueBuyoutCatalogs().iterator();
            if(catalogIt.hasNext())
                entry.setCatalogId(catalogIt.next().getCatalogId());
        }
        
        if(validateTrueBuyoutEntry(entry)) {
            ShopCartServiceLocator.getShopCartService().addTrueBuyoutItemToSessionCart(entry, vcForm.getSessionCart());
            resetDirectEntryObject(vcForm.getTrueBuyoutEntry());
            vcForm.getSessionCart().setSessionCartSaved(false);
            vcForm.buildItemAuthorizationMap(vcForm.getSessionCart());
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
	
	public ActionForward toggleTrueBuyout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ViewCartForm vcForm = (ViewCartForm)form;

        vcForm.setShowTrueBuyout(!vcForm.isShowTrueBuyout());

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
	
	public ActionForward toggleDirectEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ViewCartForm vcForm = (ViewCartForm)form;

        vcForm.setShowDirectEntry(!vcForm.isShowDirectEntry());

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
	
	private void resetDirectEntryObject(DirectEntry directEntryObject) {
	    directEntryObject.setCatalogNumber(null);
        directEntryObject.setQuantity("1");
        directEntryObject.setUnitOfIssue(null);
        directEntryObject.setCatalogPrice("");
        directEntryObject.setCatalogDescription("");
        directEntryObject.setWillCall(false);
        directEntryObject.setSuggestedVendor("");
//        directEntryObject.setFinObjectCode("");
	}

	/**
     * Determines which tab was requested to be toggled
     *
     * @param request
     * @return
     */
    protected String getMethodCaller(HttpServletRequest request) {
        String caller = "";
        String parameterName = (String) request.getAttribute(KRADConstants.METHOD_TO_CALL_ATTRIBUTE);
        if (StringUtils.isNotBlank(parameterName)) {
            caller = StringUtils.substringBetween(parameterName, ".caller", ".");
        }

        return caller;
    }

    private boolean validateDirectEntry(DirectEntry entry) {
    	boolean isValid = true;
    	String prefix = ShopCartConstants.DirectEntry.DIRECT_ENTRY + ".";
        if(StringUtils.isBlank(entry.getCatalogNumber())) {
        	GlobalVariables.getMessageMap().putError(prefix + ShopCartConstants.DirectEntry.CATALOG_NUMBER, ShopCartKeyConstants.ERROR_DIRECT_ENTRY_ITEM_BLANK);
        	isValid = false;
        }

        if(StringUtils.isBlank(entry.getQuantity())) {
        	GlobalVariables.getMessageMap().putError(prefix + ShopCartConstants.DirectEntry.QUANTITY, ShopCartKeyConstants.ERROR_DIRECT_ENTRY_QTY_BLANK);
        	isValid = false;
        }
        else if(!StringUtils.isNumeric(entry.getQuantity())) {
        	GlobalVariables.getMessageMap().putError(prefix + ShopCartConstants.DirectEntry.QUANTITY, ShopCartKeyConstants.ERROR_DIRECT_ENTRY_QTY);
        	isValid = false;
        }

    	return isValid;
    }
    
    private boolean validateTrueBuyoutEntry(DirectEntry entry) {
        String prefix = ShopCartConstants.DirectEntry.TRUE_BUYOUT_ENTRY+ ".";

        if(StringUtils.isBlank(entry.getCatalogId())) {
            GlobalVariables.getMessageMap().putError(prefix + ShopCartConstants.DirectEntry.CATALOG_ID, ShopCartKeyConstants.ERROR_TRUE_BUYOUT_NO_CATALOGS);
        }
        
        if(StringUtils.isBlank(entry.getCatalogDescription())) {
            GlobalVariables.getMessageMap().putError(prefix + ShopCartConstants.DirectEntry.DESCRIPTION, ShopCartKeyConstants.ERROR_TRUE_BUYOUT_DESC_BLANK);
        }

        if(StringUtils.isBlank(entry.getQuantity())) {
            GlobalVariables.getMessageMap().putError(prefix + ShopCartConstants.DirectEntry.QUANTITY, ShopCartKeyConstants.ERROR_DIRECT_ENTRY_QTY_BLANK);
        }
        else if(!StringUtils.isNumeric(entry.getQuantity())) {
            GlobalVariables.getMessageMap().putError(prefix + ShopCartConstants.DirectEntry.QUANTITY, ShopCartKeyConstants.ERROR_DIRECT_ENTRY_QTY);
        }
        if(StringUtils.isBlank(entry.getUnitOfIssue())) {
            GlobalVariables.getMessageMap().putError(prefix + ShopCartConstants.DirectEntry.UNIT_OF_ISSUE, ShopCartKeyConstants.ERROR_TRUE_BUYOUT_UI_BLANK);
        }
        else {
            UnitOfIssue unitOfIssue = KRADServiceLocator.getBusinessObjectService()
                    .findBySinglePrimaryKey(UnitOfIssue.class, entry.getUnitOfIssue());
            if(unitOfIssue == null) {
                GlobalVariables.getMessageMap().putError(prefix + ShopCartConstants.DirectEntry.UNIT_OF_ISSUE, ShopCartKeyConstants.ERROR_TRUE_BUYOUT_UI_INVALID);
            }
        }
        KNSServiceLocator.getKNSDictionaryValidationService().validateAttributeFormat(TrueBuyoutDetail.class.getName(), MMConstants.TrueBuyoutDetail.ORDER_ITEM_QTY, entry.getQuantity(), prefix + ShopCartConstants.DirectEntry.QUANTITY);
        KNSServiceLocator.getKNSDictionaryValidationService().validateAttributeFormat(TrueBuyoutDetail.class.getName(), MMConstants.TrueBuyoutDetail.ORDER_ITEM_UI, entry.getUnitOfIssue(), prefix + ShopCartConstants.DirectEntry.UNIT_OF_ISSUE);
        return !GlobalVariables.getMessageMap().hasErrors();
    }

    private CatalogItem retrieveCatalogItem(DirectEntry entry) {
    	CatalogItem catalogItem = null;
    	if(entry.getCatalogId() != null) {
        	catalogItem = ShopCartServiceLocator.getShopCartCatalogService().getCatalogItem(entry.getCatalogNumber(), entry.getCatalogId());
        	if(ObjectUtils.isNull(catalogItem)) {
        		GlobalVariables.getMessageMap().putError(ShopCartConstants.DirectEntry.CATALOG_NUMBER, ShopCartKeyConstants.ERROR_DIRECT_ENTRY_ITEM, entry.getCatalogNumber(), entry.getCatalogId());
        	}
        }

    	return catalogItem;
    }


}
