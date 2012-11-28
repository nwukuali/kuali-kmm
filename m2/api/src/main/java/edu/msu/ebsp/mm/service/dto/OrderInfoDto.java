package edu.msu.ebsp.mm.service.dto;

import java.io.Serializable;

public class OrderInfoDto implements Serializable {
    private static final long serialVersionUID = -47021966546125889L;
    private Long orderId;
    private String customerProfileId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String deliveryAddressLine1;
    private String deliveryAddressLine2;
    private String shippingAddressId;
    private String profilePhoneNumber;
    private String profileEmail;
    private String deliveryBuildingCd;
    private String deliveryDepartmentNm;
    private String orgCode;
    private String vendorNm;
    private String orgReferenceId;
    private String deliveryAddressName;

    /**
     * @return the orderId
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the customerProfileId
     */
    public String getCustomerProfileId() {
        return customerProfileId;
    }

    /**
     * @param customerProfileId the customerProfileId to set
     */
    public void setCustomerProfileId(String customerProfileId) {
        this.customerProfileId = customerProfileId;
    }

    /**
     * @return the shippingAddressId
     */
    public String getShippingAddressId() {
        return shippingAddressId;
    }

    /**
     * @param shippingAddressId the shippingAddressId to set
     */
    public void setShippingAddressId(String shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    /**
     * @return the profilePhoneNumber
     */
    public String getProfilePhoneNumber() {
        return profilePhoneNumber;
    }

    /**
     * @param profilePhoneNumber the profilePhoneNumber to set
     */
    public void setProfilePhoneNumber(String profilePhoneNumber) {
        this.profilePhoneNumber = profilePhoneNumber;
    }

    /**
     * @return the profileEmail
     */
    public String getProfileEmail() {
        return profileEmail;
    }

    /**
     * @param profileEmail the profileEmail to set
     */
    public void setProfileEmail(String profileEmail) {
        this.profileEmail = profileEmail;
    }

    /**
     * @return the deliveryBuildingCd
     */
    public String getDeliveryBuildingCd() {
        return deliveryBuildingCd;
    }

    /**
     * @param deliveryBuildingCd the deliveryBuildingCd to set
     */
    public void setDeliveryBuildingCd(String deliveryBuildingCd) {
        this.deliveryBuildingCd = deliveryBuildingCd;
    }

    /**
     * @return the deliveryDepartmentNm
     */
    public String getDeliveryDepartmentNm() {
        return deliveryDepartmentNm;
    }

    /**
     * @param deliveryDepartmentNm the deliveryDepartmentNm to set
     */
    public void setDeliveryDepartmentNm(String deliveryDepartmentNm) {
        this.deliveryDepartmentNm = deliveryDepartmentNm;
    }

    /**
     * @return the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgCode the orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return the vendorNm
     */
    public String getVendorNm() {
        return vendorNm;
    }

    /**
     * @param vendorNm the vendorNm to set
     */
    public void setVendorNm(String vendorNm) {
        this.vendorNm = vendorNm;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the deliveryAddressLine1
     */
    public String getDeliveryAddressLine1() {
        return deliveryAddressLine1;
    }

    /**
     * @param deliveryAddressLine1 the deliveryAddressLine1 to set
     */
    public void setDeliveryAddressLine1(String deliveryAddressLine1) {
        this.deliveryAddressLine1 = deliveryAddressLine1;
    }

    /**
     * @return the deliveryAddressLine2
     */
    public String getDeliveryAddressLine2() {
        return deliveryAddressLine2;
    }

    /**
     * @param deliveryAddressLine2 the deliveryAddressLine2 to set
     */
    public void setDeliveryAddressLine2(String deliveryAddressLine2) {
        this.deliveryAddressLine2 = deliveryAddressLine2;
    }

    /**
     * @return the orgReferenceId
     */
    public String getOrgReferenceId() {
        return orgReferenceId;
    }

    /**
     * @param orgReferenceId the orgReferenceId to set
     */
    public void setOrgReferenceId(String orgReferenceId) {
        this.orgReferenceId = orgReferenceId;
    }

    /**
     * @return the deliveryAddressName
     */
    public String getDeliveryAddressName() {
        return deliveryAddressName;
    }

    /**
     * @param deliveryAddressName the deliveryAddressName to set
     */
    public void setDeliveryAddressName(String deliveryAddressName) {
        this.deliveryAddressName = deliveryAddressName;
    }

}
