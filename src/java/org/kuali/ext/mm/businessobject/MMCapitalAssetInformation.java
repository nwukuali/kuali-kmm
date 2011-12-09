/*
 * Copyright 2008-2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.businessobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.integration.cam.businessobject.FinancialAsset;
import org.kuali.ext.mm.integration.cam.businessobject.FinancialAssetType;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorDetail;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;
import org.kuali.rice.kns.util.ObjectUtils;


public class MMCapitalAssetInformation extends PersistableBusinessObjectBase {

    /**
     *
     */
    private static final long serialVersionUID = -5498142382666081382L;
    private Integer orderDetailId;
    private Integer vendorHeaderGeneratedIdentifier;
    private Integer vendorDetailAssignedIdentifier;
    private String vendorName;
    private Long capitalAssetNumber;
    private Integer capitalAssetQuantity;
    private String capitalAssetTypeCode;
    private String capitalAssetManufacturerName;
    private String capitalAssetDescription;
    private String capitalAssetManufacturerModelNumber;


    private FinancialAsset asset;
    private FinancialAssetType assetType;
    private List<MMCapitalAssetInformationDetail> capitalAssetInformationDetails;

    private FinancialVendorDetail vendorDetail;
    // non-persistent field
    private Integer nextItemLineNumber;

    /**
     * Constructs a CapitalAssetInformation.java.
     */
    public MMCapitalAssetInformation() {
        super();
        capitalAssetInformationDetails = new ArrayList<MMCapitalAssetInformationDetail>();
    }

    @Override
    protected LinkedHashMap toStringMapper() {
        LinkedHashMap m = new LinkedHashMap();

        m.put(MMConstants.OrderDetail.ORDER_DETAIL_ID, getOrderDetailId());

        return m;
    }

    public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Integer getOrderDetailId() {
		return orderDetailId;
	}

	public String getCapitalAssetDescription() {
        return capitalAssetDescription;
    }

    public void setCapitalAssetDescription(String capitalAssetDescription) {
        this.capitalAssetDescription = capitalAssetDescription;
    }

    public String getCapitalAssetManufacturerModelNumber() {
        return capitalAssetManufacturerModelNumber;
    }

    public void setCapitalAssetManufacturerModelNumber(String capitalAssetManufacturerModelNumber) {
        this.capitalAssetManufacturerModelNumber = capitalAssetManufacturerModelNumber;
    }

    public String getCapitalAssetManufacturerName() {
        return capitalAssetManufacturerName;
    }

    public void setCapitalAssetManufacturerName(String capitalAssetManufacturerName) {
        this.capitalAssetManufacturerName = capitalAssetManufacturerName;
    }

    public Long getCapitalAssetNumber() {
        return capitalAssetNumber;
    }

    public void setCapitalAssetNumber(Long capitalAssetNumber) {
        this.capitalAssetNumber = capitalAssetNumber;
    }

    public Integer getCapitalAssetQuantity() {
        // Return capitalAssetQuantity first if it already set. Otherwise, return the size of details. If the order is reversed, the
        // user input of quantity may be overridden.
        if (this.capitalAssetQuantity != null) {
            return this.capitalAssetQuantity;
        }

        if (ObjectUtils.isNotNull(capitalAssetInformationDetails) && !capitalAssetInformationDetails.isEmpty()) {
            return capitalAssetInformationDetails.size();
        }
        return null;
    }

    public void setCapitalAssetQuantity(Integer capitalAssetQuantity) {
        this.capitalAssetQuantity = capitalAssetQuantity;
    }


    public String getCapitalAssetTypeCode() {
        return capitalAssetTypeCode;
    }

    public void setCapitalAssetTypeCode(String capitalAssetTypeCode) {
        this.capitalAssetTypeCode = capitalAssetTypeCode;
    }

    public void setAsset(FinancialAsset asset) {
		this.asset = asset;
	}

	public FinancialAsset getAsset() {
		return asset;
	}

	public void setAssetType(FinancialAssetType assetType) {
		this.assetType = assetType;
	}

	public FinancialAssetType getAssetType() {
		return assetType;
	}

	public Integer getVendorDetailAssignedIdentifier() {
        return vendorDetailAssignedIdentifier;
    }

    public void setVendorDetailAssignedIdentifier(Integer vendorDetailedAssignedIdentifier) {
        this.vendorDetailAssignedIdentifier = vendorDetailedAssignedIdentifier;
    }

    public Integer getVendorHeaderGeneratedIdentifier() {
        return vendorHeaderGeneratedIdentifier;
    }

    public void setVendorHeaderGeneratedIdentifier(Integer vendorHeaderGeneratedIdentifier) {
        this.vendorHeaderGeneratedIdentifier = vendorHeaderGeneratedIdentifier;
    }

    /**
     * Gets the vendorDetail attribute.
     *
     * @return Returns the vendorDetail.
     */
    public FinancialVendorDetail getVendorDetail() {
        return vendorDetail;
    }

    /**
     * Sets the vendorDetail attribute value.
     *
     * @param vendorDetail The vendorDetail to set.
     */
    public void setVendorDetail(FinancialVendorDetail vendorDetail) {
        this.vendorDetail = vendorDetail;
    }

    /**
     * Returns a map with the primitive field names as the key and the primitive values as the map value.
     *
     * @return Map a map with the primitive field names as the key and the primitive values as the map value.
     */
    public Map<String, Object> getValuesMap() {
        Map<String, Object> simpleValues = new HashMap<String, Object>();

        simpleValues.put(MMConstants.MMCapitalAssetInformation.ORDER_DETAIL_ID, this.getOrderDetailId());
        simpleValues.put(MMConstants.MMCapitalAssetInformation.VENDOR_HEADER_GENERATED_ID, this.getVendorHeaderGeneratedIdentifier());
        simpleValues.put(MMConstants.MMCapitalAssetInformation.VENDOR_DETAIL_ASSIGNED_ID, this.getVendorDetailAssignedIdentifier());
        simpleValues.put(MMConstants.MMCapitalAssetInformation.VENDOR_NAME, this.getVendorName());
        simpleValues.put(MMConstants.MMCapitalAssetInformation.CAPITAL_ASSET_NUMBER, this.getCapitalAssetNumber());
        simpleValues.put(MMConstants.MMCapitalAssetInformation.CAPITAL_ASSET_TYPE_CODE, this.getCapitalAssetTypeCode());

        return simpleValues;
    }

    /**
     * Gets the vendorName attribute.
     *
     * @return Returns the vendorName.
     */
    public String getVendorName() {
        if (ObjectUtils.isNotNull(vendorDetail)) {
            vendorName = vendorDetail.getVendorName();
        }
        else if (StringUtils.isNotBlank(vendorName) && vendorName.indexOf(" > ") > 0){
                vendorName = vendorName.substring(vendorName.indexOf(" > ") + 2, vendorName.length());
        }

        return vendorName;
    }

    /**
     * Sets the vendorName attribute value.
     *
     * @param vendorName The vendorName to set.
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * Gets the capitalAssetInformationDetails attribute.
     *
     * @return Returns the capitalAssetInformationDetails.
     */
    public List<MMCapitalAssetInformationDetail> getCapitalAssetInformationDetails() {
        return capitalAssetInformationDetails;
    }

    /**
     * Sets the capitalAssetInformationDetails attribute value.
     *
     * @param capitalAssetInformationDetails The capitalAssetInformationDetails to set.
     */
    public void setCapitalAssetInformationDetails(List<MMCapitalAssetInformationDetail> capitalAssetInformationDetails) {
        this.capitalAssetInformationDetails = capitalAssetInformationDetails;
    }

    /**
     * Gets the nextItemLineNumber attribute.
     * @return Returns the nextItemLineNumber.
     */
    public Integer getNextItemLineNumber() {
        return nextItemLineNumber;
    }

    /**
     * Sets the nextItemLineNumber attribute value.
     * @param nextItemLineNumber The nextItemLineNumber to set.
     */
    public void setNextItemLineNumber(Integer nextItemLineNumber) {
        this.nextItemLineNumber = nextItemLineNumber;
    }

    public boolean isEmpty() {
    	return this.getCapitalAssetQuantity() == null
		&& StringUtils.isBlank(this.getCapitalAssetTypeCode())
		&& StringUtils.isBlank(this.getCapitalAssetManufacturerName())
		&& StringUtils.isBlank(this.getVendorName())
		&& StringUtils.isBlank(this.getCapitalAssetDescription())
    	&& this.getCapitalAssetNumber() == null;
    }

}
