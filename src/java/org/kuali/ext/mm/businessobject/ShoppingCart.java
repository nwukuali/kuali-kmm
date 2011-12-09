package org.kuali.ext.mm.businessobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.kuali.ext.mm.b2b.cxml.types.CXML;


public class ShoppingCart extends StoresPersistableBusinessObject {

	/**
     *
     */
    private static final long serialVersionUID = -4046053106889111209L;

    private String shoppingCartId;

	private String shopCartHeaderName;

	private Profile customerProfile;

	private Address billingAddress;

	private Address shippingAddress;

	private String billingAddressId;

	private String shippingAddressId;

	private String customerProfileId;

	private String deliveryBuildingCode;

	private String deliveryBuildingRoomNumber;

	private String deliveryInstructionText;

	private boolean ordered;

	private String departmentReferenceText;

	private List<ShopCartDetail> shopCartDetails;

	private boolean sessionCartSaved;
	
	@Transient
	private Map<String, CXML> cxmlPayloadIdMap = new HashMap<String, CXML>();

	public ShoppingCart() {
		setShopCartDetails(new ArrayList<ShopCartDetail>());
	}

	public void setShoppingCartId(String shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public String getShoppingCartId() {
		return shoppingCartId;
	}

	public void setCustomerProfile(Profile customerProfile) {
		this.customerProfile = customerProfile;
	}

	public Profile getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfileId(String customerProfileId) {
		this.customerProfileId = customerProfileId;
	}

	public String getCustomerProfileId() {
		return customerProfileId;
	}

	public String getShopCartHeaderName() {
		return shopCartHeaderName;
	}

	public void setShopCartHeaderName(String shopCartHeaderName) {
		this.shopCartHeaderName = shopCartHeaderName;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingAddressId() {
		return billingAddressId;
	}

	public void setBillingAddressId(String billingAddressId) {
		this.billingAddressId = billingAddressId;
	}

	public String getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(String shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getDeliveryBuildingCode() {
		return deliveryBuildingCode;
	}

	public void setDeliveryBuildingCode(String deliveryBuildingCode) {
		this.deliveryBuildingCode = deliveryBuildingCode;
	}

	public String getDeliveryBuildingRoomNumber() {
		return deliveryBuildingRoomNumber;
	}

	public void setDeliveryBuildingRoomNumber(String deliveryBuildingRoomNumber) {
		this.deliveryBuildingRoomNumber = deliveryBuildingRoomNumber;
	}

	public void setDeliveryInstructionText(String deliveryInstructionText) {
		this.deliveryInstructionText = deliveryInstructionText;
	}

	public String getDeliveryInstructionText() {
		return deliveryInstructionText;
	}

	public String getDepartmentReferenceText() {
		return departmentReferenceText;
	}

	public void setDepartmentReferenceText(String departmentReferenceText) {
		this.departmentReferenceText = departmentReferenceText;
	}

	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}

	public boolean isOrdered() {
		return ordered;
	}


	public void setShopCartDetails(List<ShopCartDetail> shopCartDetails) {
		this.shopCartDetails = shopCartDetails;
	}

	public List<ShopCartDetail> getShopCartDetails() {
		return shopCartDetails;
	}

	public void setSessionCartSaved(boolean sessionCartSaved) {
		this.sessionCartSaved = sessionCartSaved;
	}

	public boolean isSessionCartSaved() {
		return sessionCartSaved;
	}

    public void setCxmlPayloadIdMap(Map<String, CXML> cxmlPayloadIdMap) {
        this.cxmlPayloadIdMap = cxmlPayloadIdMap;
    }

    public Map<String, CXML> getCxmlPayloadIdMap() {
        return cxmlPayloadIdMap;
    }

    @Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}


}