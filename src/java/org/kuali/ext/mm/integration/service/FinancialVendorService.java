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

package org.kuali.ext.mm.integration.service;

import org.kuali.ext.mm.integration.vnd.businessobject.FinancialPaymentTermType;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorAddress;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorContract;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorDetail;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import java.util.List;

/**
 * @author harsha07
 */
public interface FinancialVendorService {

    /**
     * Finds out vendor detail using vendor contract id
     *
     * @param vndrContrGnrtdId Vendor contract id
     * @return Vendor Detail
     */
    public FinancialVendorDetail getVendorDetailByContract(Integer vndrContrGnrtdId);

    /**
     * Finds vendor contract details using vendor contract id
     *
     * @param vndrContrGnrtdId Contract id
     * @return Vendor contract
     */
    public FinancialVendorContract getVendorContract(Integer vndrContrGnrtdId);


    /**
     * Finds vendor details using header ids
     *
     * @param headerId Header Id
     * @param detailId Detail id
     * @return Financial Vendor Detail
     */
    public FinancialVendorDetail getVendorDetail(Integer headerId, Integer detailId);


    /**
     * Finds the parent vendor using header id
     *
     * @param vendorHeaderGeneratedIdentifier Header Id
     * @return Parent Vendor Detail
     */
    public FinancialVendorDetail getParentVendor(Integer vendorHeaderGeneratedIdentifier);


    /**
     * Finds a vendor using Duns Number
     *
     * @param vendorDunsNumber Vendor Duns Number
     * @return Financial Vendor Detail
     */
    public FinancialVendorDetail getVendorByDunsNumber(String vendorDunsNumber);


    /**
     * Returns the Auto Purchase Order limit amount specified by a contract
     *
     * @param contractId Contract id
     * @param chart Chart
     * @param org Org
     * @return APO Limit Amount
     */
    public KualiDecimal getApoLimitFromContract(Integer contractId, String chart, String org);

    /**
     * Finds the default address of a vendor based on the parameters
     *
     * @param vendorHeaderId Header id
     * @param vendorDetailId Detail id
     * @param addressType Address type code - for e.g PO
     * @param campus Campus code
     * @return vendor address details
     */
    public FinancialVendorAddress getVendorDefaultAddress(Integer vendorHeaderId,
            Integer vendorDetailId, String addressType, String campus);

    /**
     * Returns the complete list of vendor contract by chart and org code
     *
     * @param chartCode Financial Chart Of Accounts Code
     * @param orgCode Financial Organization Code
     * @return List
     */
    public List<FinancialVendorContract> getVendorContractsByOrg(String chartCode, String orgCode);

    /**
     * Looks up FinancialPaymentTermType by type code
     *
     * @param paymentTermTypeCode
     * @return
     */
    public FinancialPaymentTermType getPaymentTermType(String paymentTermTypeCode);

}
