/**
 *
 */
package org.kuali.ext.mm.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Agreement;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorContract;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorDetail;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;


/**
 * @author harsha07
 */
public class AgreementMaintainableImpl extends KualiMaintainableImpl {
    private static final long serialVersionUID = 933034869901344897L;

    /**
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#refresh(java.lang.String, java.util.Map,
     *      org.kuali.rice.kns.document.MaintenanceDocument)
     */
    @Override
    public void refresh(String refreshCaller, Map fieldValues, MaintenanceDocument document) {
        super.refresh(refreshCaller, fieldValues, document);
        if ("vendorContractLookupable".equalsIgnoreCase(refreshCaller)) {
            Agreement agreement = (Agreement) getBusinessObject();
            if (agreement != null && agreement.getVndrContrGnrtdId() != null) {
                // find the vendor details and apply
                FinancialSystemAdaptorFactory factory = SpringContext
                        .getBean(FinancialSystemAdaptorFactory.class);
                if (factory.isSystemAvailable()) {
                    FinancialVendorContract vendorContract = factory.getFinancialVendorService()
                            .getVendorContract(agreement.getVndrContrGnrtdId());
                    if (vendorContract != null) {
                        agreement.setVendorContractName(vendorContract.getVendorContractName());
                        agreement.setVndrHeaderGnrtdId(vendorContract
                                .getVendorHeaderGeneratedIdentifier());
                        agreement.setVndrDetailAsgnId(vendorContract
                                .getVendorDetailAssignedIdentifier());
                        agreement.setAgreementBeginDt(vendorContract
                                .getVendorContractBeginningDate());
                        agreement.setAgreementEndDt(vendorContract.getVendorContractEndDate());
                        agreement.setB2bInd(vendorContract.getVendorB2bIndicator().booleanValue());
                        agreement.setPoTotalLimit(vendorContract
                                .getOrganizationAutomaticPurchaseOrderLimit());
                        if (StringUtils.isBlank(agreement.getVendorNm())) {
                            FinancialVendorDetail vendorDetail = factory
                                    .getFinancialVendorService().getVendorDetail(
                                            vendorContract.getVendorHeaderGeneratedIdentifier(),
                                            vendorContract.getVendorDetailAssignedIdentifier());
                            if (vendorDetail != null) {
                                agreement.setVendorNm(vendorDetail.getVendorName());
                            }
                        }
                    }
                }
            }
        }
    }
}
