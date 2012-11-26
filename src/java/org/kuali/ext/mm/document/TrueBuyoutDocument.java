package org.kuali.ext.mm.document;

import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.StoresTransactionalDocumentBase;
import org.kuali.ext.mm.businessobject.TrueBuyoutDetail;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;

import java.util.ArrayList;
import java.util.List;


public class TrueBuyoutDocument extends StoresTransactionalDocumentBase {
	
	private String customerProfileId;
	
	private Profile customerProfile;
	
	private String orderDocumentNumber;
	
    private String billingAddressId;

    private String shippingAddressId;
	
	private Address billingAddress;

	private Address shippingAddress;
	
	private String campusCode;
	
	private String deliveryBuildingCode;
	
	private String deliveryBuildingRmNbr;
	
	private String deliveryDepartmentName;

	private List<TrueBuyoutDetail> trueBuyoutDetails = new ArrayList<TrueBuyoutDetail>();

	public String getOrderDocumentNumber() {
        return orderDocumentNumber;
    }

    public void setOrderDocumentNumber(String orderDocumentNumber) {
        this.orderDocumentNumber = orderDocumentNumber;
    }
	
	public String getCustomerProfileId() {
        return customerProfileId;
    }

    public void setCustomerProfileId(String customerProfileId) {
        this.customerProfileId = customerProfileId;
    }

    /**
     * Gets the customerProfile property
     * @return Returns the customerProfile
     */
    public Profile getCustomerProfile() {
        return this.customerProfile;
    }

    /**
     * Sets the customerProfile property value
     * @param customerProfile The customerProfile to set
     */
    public void setCustomerProfile(Profile customerProfile) {
        this.customerProfile = customerProfile;
    }

    /**
     * Gets the billingAddressId property
     * @return Returns the billingAddressId
     */
    public String getBillingAddressId() {
        return this.billingAddressId;
    }

    /**
     * Sets the billingAddressId property value
     * @param billingAddressId The billingAddressId to set
     */
    public void setBillingAddressId(String billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    /**
     * Gets the shippingAddressId property
     * @return Returns the shippingAddressId
     */
    public String getShippingAddressId() {
        return this.shippingAddressId;
    }

    /**
     * Sets the shippingAddressId property value
     * @param shippingAddressId The shippingAddressId to set
     */
    public void setShippingAddressId(String shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    /**
     * Gets the billingAddress property
     * @return Returns the billingAddress
     */
    public Address getBillingAddress() {
        return this.billingAddress;
    }

    /**
     * Sets the billingAddress property value
     * @param billingAddress The billingAddress to set
     */
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    /**
     * Gets the shippingAddress property
     * @return Returns the shippingAddress
     */
    public Address getShippingAddress() {
        return this.shippingAddress;
    }

    /**
     * Sets the shippingAddress property value
     * @param shippingAddress The shippingAddress to set
     */
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getDeliveryBuildingCode() {
        return deliveryBuildingCode;
    }

    public void setDeliveryBuildingCode(String deliveryBuildingCode) {
        this.deliveryBuildingCode = deliveryBuildingCode;
    }

    public String getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    public String getDeliveryBuildingRmNbr() {
        return deliveryBuildingRmNbr;
    }

    public void setDeliveryBuildingRmNbr(String deliveryBuildingRmNbr) {
        this.deliveryBuildingRmNbr = deliveryBuildingRmNbr;
    }

    public String getDeliveryDepartmentName() {
        return deliveryDepartmentName;
    }

    public void setDeliveryDepartmentName(String deliveryDepartmentName) {
        this.deliveryDepartmentName = deliveryDepartmentName;
    }

    public List<TrueBuyoutDetail> getTrueBuyoutDetails() {
        return trueBuyoutDetails;
    }

    public void setTrueBuyoutDetails(List<TrueBuyoutDetail> trueBuyoutDetails) {
        this.trueBuyoutDetails = trueBuyoutDetails;
    }

    @Override
    public void doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) {
        super.doRouteStatusChange(statusChangeEvent);
        WorkflowDocument workflowDocument = getDocumentHeader().getWorkflowDocument();
        if (workflowDocument.isDisapproved()) {
        }
        else if (workflowDocument.isCanceled()) {
        }
        else if (workflowDocument.isEnroute()) {
        }
        else if (workflowDocument.isProcessed()) {
            MMServiceLocator.getTrueBuyoutService().processTrueBuyoutDocument(this);            
        }
    }

	
}