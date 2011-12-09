/*
 * Copyright 2008 The Kuali Foundation
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

package org.kuali.ext.mm.integration.service.impl.kfs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.service.FinancialBusinessObjectService;
import org.kuali.ext.mm.integration.service.FinancialPurchaseOrderService;
import org.kuali.ext.mm.integration.service.FinancialVendorService;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialPaymentTermType;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorAddress;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorContract;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorDetail;
import org.kuali.kfs.vnd.businessobject.PaymentTermType;
import org.kuali.kfs.vnd.businessobject.VendorAddress;
import org.kuali.kfs.vnd.businessobject.VendorContract;
import org.kuali.kfs.vnd.businessobject.VendorContractOrganization;
import org.kuali.kfs.vnd.businessobject.VendorDetail;
import org.kuali.kfs.vnd.document.service.VendorService;
import org.kuali.rice.kns.util.KualiDecimal;


/**
 * @author harsha07
 */
public class kfsVendorServiceAdaptor extends KfsServiceAdaptor<VendorService> implements
        FinancialVendorService {
    private static final Logger LOG = Logger.getLogger(kfsVendorServiceAdaptor.class);
    private FinancialBusinessObjectService financialBusinessObjectService;
    private FinancialPurchaseOrderService financialPurchaseOrderService;

    /**
     * @param name
     */
    public kfsVendorServiceAdaptor(QName name,
            FinancialBusinessObjectService financialBusinessObjectService,
            FinancialPurchaseOrderService financialPurchaseOrderService) {
        super(name);
        this.financialBusinessObjectService = financialBusinessObjectService;
        this.financialPurchaseOrderService = financialPurchaseOrderService;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialVendorService#getApoLimitFromContract(java.lang.Integer, java.lang.String,
     *      java.lang.String)
     */
    public KualiDecimal getApoLimitFromContract(Integer contractId, String chart, String org) {
        return getService().getApoLimitFromContract(contractId, chart, org);
    }


    /**
     * @see org.kuali.ext.mm.integration.service.FinancialVendorService#getParentVendor(java.lang.Integer)
     */
    public FinancialVendorDetail getParentVendor(Integer vendorHeaderGeneratedIdentifier) {
        VendorDetail source = getService().getParentVendor(vendorHeaderGeneratedIdentifier);
        if (source == null) {
            return null;
        }
        FinancialVendorDetail target = new FinancialVendorDetail();
        adapt(source, target);
        return target;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialVendorService#getVendorByDunsNumber(java.lang.String)
     */
    public FinancialVendorDetail getVendorByDunsNumber(String vendorDunsNumber) {
        VendorDetail source = getService().getVendorByDunsNumber(vendorDunsNumber);
        if (source == null) {
            return null;
        }
        FinancialVendorDetail target = new FinancialVendorDetail();
        adapt(source, target);
        return target;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialVendorService#getVendorDefaultAddress(java.lang.Integer,
     *      java.lang.Integer, java.lang.String, java.lang.String)
     */
    public FinancialVendorAddress getVendorDefaultAddress(Integer vendorHeaderId,
            Integer vendorDetailId, String addressType, String campus) {

        VendorAddress source = getService().getVendorDefaultAddress(vendorHeaderId, vendorDetailId,
                addressType, campus);
        if (source == null) {
            return null;
        }
        FinancialVendorAddress target = new FinancialVendorAddress();
        adapt(source, target);
        return target;
    }


    /**
     * @see org.kuali.ext.mm.integration.service.FinancialVendorService#getVendorDetail(java.lang.Integer, java.lang.Integer)
     */
    public FinancialVendorDetail getVendorDetail(Integer headerId, Integer detailId) {
        VendorDetail source = getService().getVendorDetail(headerId, detailId);
        if (source == null) {
            return null;
        }
        FinancialVendorDetail target = new FinancialVendorDetail();
        adapt(source, target);
        return target;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialVendorService#getVendorDetailByContract(java.lang.Integer)
     */
    public FinancialVendorDetail getVendorDetailByContract(Integer vndrContrGnrtdId) {
        LOG.debug("Vendor Detail By Contract Id");
        FinancialVendorContract vendorContract = getVendorContract(vndrContrGnrtdId);
        VendorDetail source = getService().getVendorDetail(
                vendorContract.getVendorContractGeneratedIdentifier(),
                vendorContract.getVendorDetailAssignedIdentifier());
        if (source == null) {
            return null;
        }
        FinancialVendorDetail target = new FinancialVendorDetail();
        adapt(source, target);
        return target;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialVendorService#getVendorContract(java.lang.Integer)
     */
    public FinancialVendorContract getVendorContract(Integer vndrContrGnrtdId) {
        LOG.debug("getVendorContract by id ");
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        VendorContract source = factory.getFinancialBusinessObjectService().findBySinglePrimaryKey(
                VendorContract.class, vndrContrGnrtdId);
        if (source == null) {
            return null;
        }
        FinancialVendorContract target = new FinancialVendorContract();
        adapt(source, target);
        return target;
    }

    /**
     * Adapts KFS contract details to MM Vendor contract object
     * 
     * @param source KFS Object
     * @param target MM Object
     */
    private void adapt(VendorContract source, FinancialVendorContract target) {
        target.setVendorContractGeneratedIdentifier(source.getVendorContractGeneratedIdentifier());
        target.setVendorHeaderGeneratedIdentifier(source.getVendorHeaderGeneratedIdentifier());
        target.setVendorDetailAssignedIdentifier(source.getVendorDetailAssignedIdentifier());
        target.setVendorContractName(source.getVendorContractName());
        target.setVendorContractDescription(source.getVendorContractDescription());
        target.setVendorCampusCode(source.getVendorCampusCode());
        target.setVendorContractBeginningDate(source.getVendorContractBeginningDate());
        target.setVendorContractEndDate(source.getVendorContractEndDate());
        target.setContractManagerCode(source.getContractManagerCode());
        target.setPurchaseOrderCostSourceCode(source.getPurchaseOrderCostSourceCode());
        target.setVendorPaymentTermsCode(source.getVendorPaymentTermsCode());
        target.setVendorShippingPaymentTermsCode(source.getVendorShippingPaymentTermsCode());
        target.setVendorShippingTitleCode(source.getVendorShippingTitleCode());
        target.setVendorContractExtensionDate(source.getVendorContractExtensionDate());
        target.setVendorB2bIndicator(source.getVendorB2bIndicator());
        target.setOrganizationAutomaticPurchaseOrderLimit(source
                .getOrganizationAutomaticPurchaseOrderLimit());
        target.setActive(source.isActive());
    }

    /**
     * @param source Source kfs vendor detail object
     * @param target Target MM vendor detail object
     */
    protected void adapt(VendorDetail source, FinancialVendorDetail target) {
        target.setActiveIndicator(source.isActiveIndicator());
        target.setTaxableIndicator(source.isTaxableIndicator());
        target.setVendorConfirmationIndicator(source.getVendorConfirmationIndicator());
        target.setVendorCreditCardIndicator(source.getVendorCreditCardIndicator());
        target.setVendorDetailAssignedIdentifier(source.getVendorDetailAssignedIdentifier());
        target.setVendorDunsNumber(source.getVendorDunsNumber());
        target.setVendorFirstLastNameIndicator(source.isVendorFirstLastNameIndicator());
        target.setVendorFirstName(source.getVendorFirstName());
        target.setVendorHeaderGeneratedIdentifier(source.getVendorHeaderGeneratedIdentifier());
        target.setVendorInactiveReasonCode(source.getVendorInactiveReasonCode());
        target.setVendorLastName(source.getVendorLastName());
        target.setVendorMinimumOrderAmount(source.getVendorMinimumOrderAmount());
        target.setVendorName(source.getVendorName());
        target.setVendorNumber(source.getVendorNumber());
        target.setVendorParentIndicator(source.isVendorParentIndicator());
        target.setVendorPaymentTermsCode(source.getVendorPaymentTermsCode());
        target.setVendorPrepaymentIndicator(source.getVendorPrepaymentIndicator());
        target.setVendorRemitName(source.getVendorRemitName());
        target.setVendorRestrictedDate(source.getVendorRestrictedDate());
        target.setVendorRestrictedIndicator(source.getVendorRestrictedIndicator());
        target.setVendorRestrictedPersonIdentifier(source.getVendorRestrictedPersonIdentifier());
        target.setVendorRestrictedReasonText(source.getVendorRestrictedReasonText());
        target.setVendorShippingPaymentTermsCode(source.getVendorShippingPaymentTermsCode());
        target.setVendorShippingTitleCode(source.getVendorShippingTitleCode());
        target.setVendorSoldToAssignedIdentifier(source.getVendorSoldToAssignedIdentifier());
        target.setVendorSoldToGeneratedIdentifier(source.getVendorSoldToGeneratedIdentifier());
        target.setVendorSoldToName(source.getVendorSoldToName());
        target.setVendorSoldToNumber(source.getVendorSoldToNumber());
        target.setVendorStateForLookup(source.getVendorStateForLookup());
        target.setVendorUrlAddress(source.getVendorUrlAddress());
    }

    /**
     * @param source Source kfs address
     * @param target Target MM object
     */
    protected void adapt(VendorAddress source, FinancialVendorAddress target) {
        target.setVendorAddressEmailAddress(source.getVendorAddressEmailAddress());
        target.setVendorAddressGeneratedIdentifier(source.getVendorAddressGeneratedIdentifier());
        target.setVendorAddressInternationalProvinceName(source
                .getVendorAddressInternationalProvinceName());
        target.setVendorAddressTypeCode(source.getVendorAddressTypeCode());
        target.setVendorAttentionName(source.getVendorAttentionName());
        target
                .setVendorBusinessToBusinessUrlAddress(source
                        .getVendorBusinessToBusinessUrlAddress());
        target.setVendorCityName(source.getVendorCityName());
        target.setVendorCountryCode(source.getVendorCountryCode());
        target.setVendorDefaultAddressIndicator(source.isVendorDefaultAddressIndicator());
        target.setVendorDetailAssignedIdentifier(source.getVendorDetailAssignedIdentifier());
        target.setVendorFaxNumber(source.getVendorFaxNumber());
        target.setVendorHeaderGeneratedIdentifier(source.getVendorHeaderGeneratedIdentifier());
        target.setVendorLine1Address(source.getVendorLine1Address());
        target.setVendorLine2Address(source.getVendorLine2Address());
        target.setVendorStateCode(source.getVendorStateCode());
        target.setVendorZipCode(source.getVendorZipCode());
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialVendorService#getVendorContractsByOrg(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<FinancialVendorContract> getVendorContractsByOrg(String chartCode, String orgCode) {
        ArrayList<FinancialVendorContract> list = new ArrayList<FinancialVendorContract>();
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("chartOfAccountsCode", chartCode);
        fieldValues.put("organizationCode", orgCode);
        Collection<VendorContractOrganization> matches = getFinancialBusinessObjectService()
                .findMatching(VendorContractOrganization.class, fieldValues);
        if (matches != null && !matches.isEmpty()) {
            for (VendorContractOrganization contractOrganization : matches) {
                FinancialVendorContract vendorContract = getVendorContract(contractOrganization
                        .getVendorContractGeneratedIdentifier());
                int latestPurchaseOrderId = financialPurchaseOrderService
                        .getLatestPurchaseOrderId(vendorContract
                                .getVendorContractGeneratedIdentifier());
                KualiDecimal totalAmountByOrgContract = financialPurchaseOrderService
                        .getTotalAmountByContract(vendorContract
                                .getVendorContractGeneratedIdentifier());
                vendorContract.setTotalUsedAmt(totalAmountByOrgContract);
                vendorContract.setPoId(latestPurchaseOrderId == 0 ? null : latestPurchaseOrderId);
                list.add(vendorContract);
            }
        }
        return list;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialVendorService#getPaymentTermType(java.lang.String)
     */
    public FinancialPaymentTermType getPaymentTermType(String paymentTermTypeCode) {
        PaymentTermType source = financialBusinessObjectService.findBySinglePrimaryKey(
                PaymentTermType.class, paymentTermTypeCode);
        if (source != null) {
            FinancialPaymentTermType target = new FinancialPaymentTermType();
            adapt(source, target);
            return target;
        }
        return null;
    }

    /**
     * @param source
     * @param target
     */
    protected void adapt(PaymentTermType source, FinancialPaymentTermType target) {
        target.setVendorPaymentTermsCode(source.getVendorPaymentTermsCode());
        target.setVendorDiscountDueTypeDescription(source.getVendorDiscountDueTypeDescription());
        target.setVendorDiscountDueNumber(source.getVendorDiscountDueNumber());
        target.setVendorPaymentTermsPercent(source.getVendorPaymentTermsPercent());
        target.setVendorNetDueTypeDescription(source.getVendorNetDueTypeDescription());
        target.setVendorNetDueNumber(source.getVendorNetDueNumber());
        target.setVendorPaymentTermsDescription(source.getVendorPaymentTermsDescription());
        target.setActive(source.isActive());
    }

    /**
     * Gets the financialBusinessObjectService property
     * 
     * @return Returns the financialBusinessObjectService
     */
    public FinancialBusinessObjectService getFinancialBusinessObjectService() {
        return this.financialBusinessObjectService;
    }

    /**
     * Sets the financialBusinessObjectService property value
     * 
     * @param financialBusinessObjectService The financialBusinessObjectService to set
     */
    public void setFinancialBusinessObjectService(
            FinancialBusinessObjectService financialBusinessObjectService) {
        this.financialBusinessObjectService = financialBusinessObjectService;
    }

}
