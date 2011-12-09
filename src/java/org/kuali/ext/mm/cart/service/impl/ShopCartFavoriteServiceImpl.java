package org.kuali.ext.mm.cart.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.Customer;
import org.kuali.ext.mm.businessobject.CustomerFavDetail;
import org.kuali.ext.mm.businessobject.CustomerFavHeader;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.cart.service.ShopCartCatalogService;
import org.kuali.ext.mm.cart.service.ShopCartFavoriteService;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.DictionaryValidationService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class ShopCartFavoriteServiceImpl implements ShopCartFavoriteService {

	private BusinessObjectService businessObjectService;

	private ShopCartCatalogService shopCartCatalogService;

	public void addItemToFavorites(CustomerFavDetail detail, CustomerFavHeader customerFavHeader) {

		for(CustomerFavDetail item : customerFavHeader.getCustomerFavDetails()) {
			if(item.getCatalogItem().getCatalogItemId().equals(detail.getCatalogItem().getCatalogItemId())) {
				return;
			}
		}
		customerFavHeader.getCustomerFavDetails().add(detail);
	}

	public CustomerFavHeader createNewFavoritesList(String favoritesListName, Customer customer) {
		CustomerFavHeader header = new CustomerFavHeader();

		header.setCustomer(customer);
		header.setPrincipalName(customer.getPrincipalName());
		header.setCustomerFavName(favoritesListName);
		header.setCustomerFavShareInd(false);
		header.setActive(true);

		return header;
	}

	public void saveFavoritesList(CustomerFavHeader customerFavHeader) {
		getBusinessObjectService().save(customerFavHeader);
	}

	public void deleteFavoritesList(CustomerFavHeader customerFavHeader) {
		getBusinessObjectService().delete(customerFavHeader);
	}

	public void shareFavoritesList(CustomerFavHeader customerFavHeader, boolean share) {
		customerFavHeader.setCustomerFavShareInd(share);
		saveFavoritesList(customerFavHeader);
	}

	public CustomerFavHeader getFavoriteHeaderById(String customerFavHeaderId, String customerId) {
		CustomerFavHeader header = getBusinessObjectService().findBySinglePrimaryKey(CustomerFavHeader.class, customerFavHeaderId);

		if(header != null && (StringUtils.equals(header.getPrincipalName(), customerId) || header.isCustomerFavShareInd()))
			return header;

		return null;
	}

	public CustomerFavHeader getFavoriteHeaderByName(String customerFavHeaderName, String customerId) {
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
		fieldValues.put(MMConstants.CustomerFavHeader.CUSTOMER_FAV_NM, customerFavHeaderName);
		fieldValues.put(MMConstants.CustomerFavHeader.PRINCIPAL_NAME, customerId.toLowerCase());
		Collection results =  getBusinessObjectService().findMatching(CustomerFavHeader.class, fieldValues);

		return (results.iterator().hasNext() ? (CustomerFavHeader)results.iterator().next() : null);
	}

	public void removeItemFromFavorites(CustomerFavHeader customerFavHeader, CustomerFavDetail detail) {
		for(CustomerFavDetail favDetail : customerFavHeader.getCustomerFavDetails()) {
			if(favDetail.getCustomerFavDetailId().equals(detail.getCustomerFavDetailId())) {
				customerFavHeader.getCustomerFavDetails().remove(favDetail);
				getBusinessObjectService().save(customerFavHeader);
				break;
			}
		}
	}

	public Collection<CustomerFavHeader> getFavoritesByCustomerId(String customerId, boolean sharedOnly) {
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
		fieldValues.put(MMConstants.CustomerFavHeader.PRINCIPAL_NAME, customerId.toLowerCase());
		if(sharedOnly)
			fieldValues.put(MMConstants.CustomerFavHeader.SHARED_IND, true);

		return getBusinessObjectService().findMatching(CustomerFavHeader.class, fieldValues);
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartFavoriteService#deleteFavoritesDetail(org.kuali.ext.mm.businessobject.CustomerFavHeader, java.lang.String)
	 */
	public void deleteFavoritesDetail(CustomerFavHeader header, String itemId) {
		CustomerFavDetail detailMatch = null;
		for(CustomerFavDetail detail : header.getCustomerFavDetails()) {
			if(detail.getCustomerFavDetailId().equals(itemId)) {
				detailMatch=detail;
				break;
			}
		}
		header.getCustomerFavDetails().remove(detailMatch);
		getBusinessObjectService().delete(detailMatch);
	}


	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public void setShopCartCatalogService(ShopCartCatalogService shopCartCatalogService) {
		this.shopCartCatalogService = shopCartCatalogService;
	}

	public ShopCartCatalogService getShopCartCatalogService() {
		return shopCartCatalogService;
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartFavoriteService#validateNewFavoriteList(org.kuali.ext.mm.businessobject.CustomerFavHeader)
	 */
	public boolean validateNewFavoriteList(CustomerFavHeader header) {
		boolean isValid = true;
		DictionaryValidationService validationService = KNSServiceLocator.getDictionaryValidationService();
		isValid = validationService.isBusinessObjectValid(header);

		CustomerFavHeader checkHeader = getFavoriteHeaderByName(header.getCustomerFavName(), header.getPrincipalName());
		if(isValid && checkHeader != null) {
			GlobalVariables.getMessageMap().putError(ShopCartConstants.FavoritesForm.NEW_FAVORITES_NAME, ShopCartKeyConstants.ERROR_CUSTOMER_FAVORITE_EXISTS);
			isValid=false;
		}


		return isValid;
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartFavoriteService#checkAndUpdateFavoriteDetails(org.kuali.ext.mm.businessobject.CustomerFavHeader)
	 */
	public void checkAndUpdateFavoriteDetails(CustomerFavHeader header) {
		List<CustomerFavDetail> currentDetail = new ArrayList<CustomerFavDetail>();

		for(CustomerFavDetail detail : header.getCustomerFavDetails()) {
			if(detail.getCatalogItem().isActive()) {
				currentDetail.add(detail);
			}
			else {
				boolean foundCurrent = false;
				String catalogCode = detail.getCatalogItem().getCatalog().getCatalogCd();
				String distributorNumber = detail.getCatalogItem().getDistributorNbr();
				Catalog catalog = getShopCartCatalogService().getCurrentCatalogByCatalogCode(catalogCode);
				if(catalog != null) {
					CatalogItem catalogItem = getShopCartCatalogService().getCatalogItem(distributorNumber, catalog.getCatalogId());
					if(catalogItem != null) {
						detail.setCatalogItem(catalogItem);
						detail.setCatalogItemId(catalogItem.getCatalogItemId());
						currentDetail.add(detail);
						foundCurrent = true;
					}
				}
				if(!foundCurrent) {
					getBusinessObjectService().delete(detail);
				}
			}
		}

		header.setCustomerFavDetails(currentDetail);
	}

}
