package org.kuali.ext.mm.cart.web.struts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.CatalogImage;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.util.MMFileUtil;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;


public class ItemAction extends StoresShoppingActionBase {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ItemForm iForm = (ItemForm)form;

        String catalogItemId = request.getParameter("itemId");

        loadCatalogItem(catalogItemId, iForm);

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward addToFavorites(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ItemForm iForm = (ItemForm)form;

		List<String> item = new ArrayList<String>();
		item.add(iForm.getDisplayItem().getCatalogItemId());
		request.getSession().setAttribute(ShopCartConstants.Session.ADD_TO_FAVORITES_ITEMS, item);

		Properties parameters = new Properties();
        parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.ADD_TO_FAVORITES_METHOD);

		return new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.FAVORITES_ACTION, parameters), true);
    }

	public ActionForward addToCart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ItemForm iForm = (ItemForm)form;

        if(iForm.getAddQuantity() != null && iForm.getAddQuantity() > 0) {
        	ShopCartServiceLocator.getShopCartService().addCatalogItemToSessionCart(iForm.getSessionCart(), iForm.getCustomerProfile(), iForm.getDisplayItem(), iForm.getAddQuantity(), iForm.getDisplayItem().isWillcallInd());
        }

        iForm.setItemInCart(true);
        iForm.setAddQuantity(1);

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public void loadCatalogItem(String catalogItemId, ItemForm iForm) {
		iForm.setDisplayItem(ShopCartServiceLocator.getShopCartCatalogService().getCatalogItemById(catalogItemId));
        if(!iForm.getDisplayItem().getCatalogItemImages().isEmpty())
        	iForm.setDisplayImage(iForm.getDisplayItem().getCatalogItemImages().get(0).getCatalogImage());

        if(iForm.getSessionCart() != null)
        	iForm.setItemInCart(ShopCartServiceLocator.getShopCartService().isCatalogItemInCart(iForm.getDisplayItem(), iForm.getSessionCart()));
	}
	/**
     * This method will stream image of an item based on the path provided by the URL
     * 
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return stream data so return will be null
     * @throws Exception
     */
    public ActionForward streamImage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("image/jpg");
        String imageDir = KNSServiceLocator.getKualiConfigurationService().getPropertyString(
                "catalog.images.dir");
        File imageFile = new File(imageDir, request.getParameter("imagePath"));
        if (!imageFile.exists()) {
            return null;
        }
        MMFileUtil.streamOut(new FileInputStream(imageFile), response.getOutputStream());

        return null;
    }

    public ActionForward downloadWebreqImages(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html");
        String imageDir = KNSServiceLocator.getKualiConfigurationService().getPropertyString(
                "catalog.images.dir");
        File imageDirFile = new File(imageDir);
        // create if director doesn't exist
        if (!imageDirFile.exists()) {
            imageDirFile.mkdirs();
        }
        // This is safety check to make sure that program doesn't overwrite already existing images after the first RUN
        File[] listFiles = imageDirFile.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            response.getWriter().println("Directory is not empty, so cannot be overwritten!!");
            response.getWriter().flush();
            response.getWriter().close();
            return null;
        }
        Collection allImages = SpringContext.getBean(BusinessObjectService.class).findAll(
                CatalogImage.class);
        Iterator iterator = allImages.iterator();
        response.getWriter().println("STARTING IMAGE DOWNLOADS <br/>");
        while (iterator.hasNext()) {
            CatalogImage catalogImage = (CatalogImage) iterator.next();
            String urlStr = catalogImage.getCatalogImageUrl();
            HttpURLConnection urlCon = null;
            try {
                URL imageUrl = new URL(urlStr);
                urlCon = (HttpURLConnection) imageUrl.openConnection();
                InputStream is = urlCon.getInputStream();
                MMFileUtil.streamOut(is,
                        new FileOutputStream(imageDir + urlStr.substring(urlStr.lastIndexOf('/'))));
            }
            catch (Exception e) {
                response.getWriter().println("Failed downloading " + urlStr + "<br/>");
                response.getWriter().flush();
            }
            finally {
                if (urlCon != null) {
                    urlCon.disconnect();
                }
            }
        }
        response.getWriter().println("FINISHED IMAGE DOWNLOADS");
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }
}
