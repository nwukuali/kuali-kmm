package org.kuali.ext.mm.cart.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ShopCartDetail;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.dataaccess.impl.ShopCartBusinessObjectDaoOjb;
import org.kuali.ext.mm.cart.service.ShopCartOrderService;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.QueryElement;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialOrganization;
import org.kuali.ext.mm.service.B2BPunchOutService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.bo.AdHocRouteRecipient;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;


@Transactional
public class ShopCartOrderServiceImpl implements ShopCartOrderService {

	private DocumentService documentService;
	private BusinessObjectService businessObjectService;


	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartOrderService#createNewOrderDocument(org.kuali.ext.mm.businessobject.ShoppingCart, org.kuali.ext.mm.businessobject.Profile, java.util.List)
	 */
	public OrderDocument createNewOrderDocument(ShoppingCart shoppingCart, Profile customerProfile, List<ShopCartDetail> shopCartDetails) {
		OrderDocument doc = null;
		try {
			doc = (OrderDocument)documentService.getNewDocument(OrderDocument.class);

			String catalogTypeCode = shopCartDetails.get(0).getCatalogItem().getCatalog().getCatalogType().getCatalogTypeCd();
			if(MMConstants.CatalogType.WAREHOUSE.equals(catalogTypeCode))
				doc.setOrderTypeCode(MMConstants.OrderType.WAREHS);
			else if(MMConstants.CatalogType.HOSTED.equals(catalogTypeCode))
				doc.setOrderTypeCode(MMConstants.OrderType.HOSTED);
			else if(MMConstants.CatalogType.PUNCHOUT.equals(catalogTypeCode))
				doc.setOrderTypeCode(MMConstants.OrderType.PUNCH);
			else
				throw new RuntimeException("Error instantiating OrderDocument. No Order Type Code for Catalog Type Code: " + catalogTypeCode);

			doc.setOrderId(KRADServiceLocator.getSequenceAccessorService().getNextAvailableSequenceNumber(ShopCartConstants.Sequence.ORDER_ID_SEQ));
			doc.getDocumentHeader().setDocumentDescription(String.valueOf(doc.getOrderId()));

			doc.setOrderStatusCd(MMConstants.OrderStatus.INITIATED);
			doc.setCustomerProfileId(customerProfile.getProfileId());
			doc.refreshReferenceObject(MMConstants.OrderDocument.CUSTOMER_PROFILE);
			doc.setCampusCd(customerProfile.getCampusCode());
			doc.setWarehouseCd(shopCartDetails.get(0).getCatalogItem().getCatalog().getWarehouseCd());
			doc.setShippingAddress(shoppingCart.getShippingAddress());
			doc.setShippingAddressId(shoppingCart.getShippingAddressId());
			doc.setBillingAddress(shoppingCart.getBillingAddress());
			doc.setBillingAddressId(shoppingCart.getBillingAddressId());
			doc.setDeliveryBuildingCd(customerProfile.getDeliveryBuildingCode());
			doc.setDeliveryBuildingRmNbr(customerProfile.getDeliveryBuildingRoomNumber());

			if(StringUtils.isNotBlank(customerProfile.getOrganizationCode())) {
				FinancialSystemAdaptorFactory adaptorFactory = SpringContext.getBean(FinancialSystemAdaptorFactory.class);
				FinancialOrganization org = adaptorFactory.getOrganizationService().getByPrimaryId(customerProfile.getFinacialChartOfAccountsCode(), customerProfile.getOrganizationCode());
				doc.setDeliveryDepartmentNm(org.getOrganizationName());
			}

			doc.setAgreementNbr(shopCartDetails.get(0).getCatalogItem().getCatalog().getAgreementNbr());
			doc.setDeliveryInstructionTxt(shoppingCart.getDeliveryInstructionText());

			int lineCount = 0;
			Set<String> cxmlPayloadIds = new HashSet<String>();
			for(ShopCartDetail detail : shopCartDetails) {
			    if(StringUtils.isNotBlank(detail.getCxmlPayloadId()))
			        cxmlPayloadIds.add(detail.getCxmlPayloadId());
				doc.getOrderDetails().add(createNewOrderDetail(detail, ++lineCount));
			}
			doc.setVendorDetailAssignedId(doc.getOrderDetails().get(0).getVendorDetailAssignedId());
			doc.setVendorHeaderGeneratedId(doc.getOrderDetails().get(0).getVendorHeaderGeneratedId());
			doc.setVendorNm(doc.getOrderDetails().get(0).getVendorNm());
			doc.setCreationDate(CoreApiServiceLocator.getDateTimeService().getCurrentTimestamp());

			if(customerProfile.isPersonalUseIndicator())
				doc.setProfileTypeCode(MMConstants.OrderDocument.PROFILE_TYPE_PERSONAL);
			else
				doc.setProfileTypeCode(MMConstants.OrderDocument.PROFILE_TYPE_INTERNAL);		 
			
			if(cxmlPayloadIds.size() > 0)
			    saveCxmlRecords(shoppingCart, cxmlPayloadIds, doc.getDocumentNumber());
		}
		catch(WorkflowException e) {
			throw new RuntimeException("Error instantiating OrderDocument", e);
		}
		return doc;
	}

	/**
	 * Save vendor request records.
	 * 
     * @param shoppingCart
     * @param cxmlPayloadIds
     * @param orderId
     */
    private void saveCxmlRecords(ShoppingCart shoppingCart, Set<String> cxmlPayloadIds, String orderDocNumber) {
        B2BPunchOutService punchOutService = MMServiceLocator.getB2BPunchOutService();
        // Shopping cart can contain items from multiple vendors, or multiple orders from the same vendor
        // save cxml records by payloadId
        for(String payloadId : cxmlPayloadIds) {
            punchOutService.saveVendorPurchaseRequest(
                    orderDocNumber, 
                    payloadId, 
                    shoppingCart.getCustomerProfile().getProfileId(), 
                    shoppingCart.getCxmlPayloadIdMap().get(payloadId));
        }
    }

    /**
	 * @see org.kuali.ext.mm.cart.service.ShopCartOrderService#routeOrderDocument(org.kuali.ext.mm.document.OrderDocument)
	 */
	public void routeOrderDocument(OrderDocument document) {
		try {
			documentService.routeDocument(document, "", new ArrayList<AdHocRouteRecipient>());
		}
		catch (WorkflowException e) {
			throw new RuntimeException("Error Routing OrderDocument", e);
		}
	}

	public void saveOrderDocument(OrderDocument document) {
		try {
			documentService.saveDocument(document);
		}
		catch (WorkflowException e) {
			throw new RuntimeException("Error saving OrderDocument", e);
		}
	}

	/**
	 * Creates a new OrderDetail object from shopCartDetail
	 *
	 * @param shopCartDetail
	 * @return an OrderDetail object
	 */
	private OrderDetail createNewOrderDetail(ShopCartDetail shopCartDetail, Integer detailLineNumber) {
		OrderDetail orderDetail = new OrderDetail();

		orderDetail.setOrderStatusCd(MMConstants.OrderStatus.INITIATED);
		orderDetail.setCatalogItem(shopCartDetail.getCatalogItem());
		orderDetail.setCatalogItemId(shopCartDetail.getCatalogItemId());
		orderDetail.setItemLineNumber(detailLineNumber);
		orderDetail.setDistributorNbr(shopCartDetail.getDistributorNumber());
		orderDetail.setShoppingCartDetailId(shopCartDetail.getShopCartDetailId());
		orderDetail.setOrderItemQty(shopCartDetail.getShopCartItemQty());
		orderDetail.setOrderItemDetailDesc(shopCartDetail.getShopCartItemDetailDesc());
		orderDetail.setOrderItemCostAmt(shopCartDetail.getShopCartItemCostAmt());
		orderDetail.setOrderItemMarkupAmt(shopCartDetail.getShopCartItemMarkupAmt());
		orderDetail.setOrderItemTaxAmt(shopCartDetail.getShopCartItemTaxAmt());
		orderDetail.setOrderItemPriceAmt(shopCartDetail.getShopCartItemPriceAmt());
		orderDetail.setOrderItemAdditionalCostAmt(shopCartDetail.getShopCartItemAdditionalCostAmt());
		orderDetail.setStockUnitOfIssueCd(shopCartDetail.getItemUnitOfIssueCd());
		orderDetail.setShippingHt(shopCartDetail.getShippingHt());
		orderDetail.setShippingLh(shopCartDetail.getShippingLh());
		orderDetail.setShippingWd(shopCartDetail.getShippingWd());
		orderDetail.setShippingWgt(shopCartDetail.getShippingWgt());
		orderDetail.setWillCall(shopCartDetail.isWillcallInd());
		orderDetail.setVendorDetailAssignedId(shopCartDetail.getVendorDetailAssignedId());
		orderDetail.setVendorHeaderGeneratedId(shopCartDetail.getVendorHeaderGeneratedId());
		orderDetail.setVendorNm(shopCartDetail.getVendorNm());
		orderDetail.setSPaidId(shopCartDetail.getSupplierPartAuxId());	

		if(ObjectUtils.isNotNull(shopCartDetail.getCatalogItem().getStock()) 
		        && shopCartDetail.getCatalogItem().getStock().isRental())
		    orderDetail.setAdditionalCostTypeCode(MMConstants.AdditionalCostType.DEPOSIT);
		
		return orderDetail;
	}

	/**
	 * Sets the DocumentService
	 *
	 * @param documentService
	 */
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	/**
	 * Gets the DocumentService
	 *
	 * @return documentService
	 */
	public DocumentService getDocumentService() {
		return documentService;
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
	 * @see org.kuali.ext.mm.cart.service.ShopCartOrderService#getIncompleteOrders(org.kuali.ext.mm.businessobject.Profile)
	 */
	public List<OrderDocument> getIncompleteOrders(Profile customerProfile) {
		List<OrderDocument> orderDocList = new ArrayList<OrderDocument>();
		if(customerProfile == null)
			return orderDocList;
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		fieldValues.put(MMConstants.OrderDocument.ORDER_STATUS_CD, MMConstants.OrderStatus.INITIATED);
		fieldValues.put("customerProfile.principalName", customerProfile.getPrincipalName());

		Collection<OrderDocument> results = SpringContext.getBean(ShopCartBusinessObjectDaoOjb.class).findByReportQuery(OrderDocument.class, fieldValues, MMConstants.OrderDocument.CREATION_DATE, false);

		for(OrderDocument orderDoc : results) {
			orderDocList.add(orderDoc);
		}
		return orderDocList;
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartOrderService#getOrderHistory(org.kuali.ext.mm.businessobject.Profile, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public List<OrderDocument> getOrderHistory(Profile customerProfile, Timestamp fromDate, Timestamp toDate) {
		List<OrderDocument> orderDocList = new ArrayList<OrderDocument>();
		List<String> orderTypeList = new ArrayList<String>();
		orderTypeList.add(MMConstants.OrderType.WAREHS);
		orderTypeList.add(MMConstants.OrderType.HOSTED);
		orderTypeList.add(MMConstants.OrderType.PUNCH);
		orderTypeList.add(MMConstants.OrderType.TRUE_BUYOUT);
		
		if(customerProfile == null)
			return orderDocList;
		QueryElement queryElement = new QueryElement(MMConstants.OrderDocument.CUSTOMER_PROFILE_PRINCIPAL_NAME, customerProfile.getPrincipalName());
		queryElement.getAndList().add(new QueryElement(MMConstants.OrderDocument.ORDER_TYPE_CD, orderTypeList));
		QueryElement lessThanDate = new QueryElement(MMConstants.OrderDocument.CREATION_DATE, toDate);
		QueryElement greaterThanDate = new QueryElement(MMConstants.OrderDocument.CREATION_DATE, fromDate);
		lessThanDate.setLessThan(true);
		greaterThanDate.setGreaterThan(true);
		queryElement.getAndList().add(lessThanDate);
		queryElement.getAndList().add(greaterThanDate);
		Collection<OrderDocument> results = SpringContext.getBean(ShopCartBusinessObjectDaoOjb.class).findByReportQuery(OrderDocument.class, queryElement, MMConstants.OrderDocument.CREATION_DATE, false);

		for(OrderDocument orderDoc : results) {
			orderDocList.add(orderDoc);
		}
		return orderDocList;
	}
	
}
