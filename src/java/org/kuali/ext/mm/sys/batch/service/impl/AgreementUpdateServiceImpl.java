package org.kuali.ext.mm.sys.batch.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.kuali.ext.mm.businessobject.Agreement;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.service.FinancialVendorService;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorContract;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorDetail;
import org.kuali.ext.mm.sys.batch.service.AgreementUpdateService;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KualiDecimal;


/**
 * @author rshrivas
 */
public class AgreementUpdateServiceImpl implements AgreementUpdateService {

    /**
     * @see org.kuali.ext.mm.sys.batch.service.AgreementUpdateService#updateAgreement()
     */

    @SuppressWarnings("unchecked")
    public void updateAgreement() {
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        // proceed only if Finance system is available
        if (!financialSystemAdaptorFactory.isSystemAvailable()) {
            return;
        }
        BusinessObjectService bos = SpringContext.getBean(BusinessObjectService.class);
        FinancialVendorService financialVendorService = financialSystemAdaptorFactory
                .getFinancialVendorService();
        HashMap<String, Object> fieldVals = new HashMap<String, Object>();
        fieldVals.put("active", Boolean.TRUE);
        List<Warehouse> warehouseList = (List<Warehouse>) bos.findMatching(Warehouse.class,
                fieldVals);
        HashSet<String> orgsList = new HashSet<String>();
        HashSet<Integer> contractIds = new HashSet<Integer>();
        for (Warehouse warehouse : warehouseList) {
            String chartCode = warehouse.getDefaultChartCode();
            String orgCode = warehouse.getDefaultOrgCode();
            if (chartCode != null && orgCode != null) {
                if (orgsList.add(chartCode + "-" + orgCode)) {
                    List<FinancialVendorContract> financialVendorContractsList = financialVendorService
                            .getVendorContractsByOrg(chartCode, orgCode);
                    for (FinancialVendorContract contract : financialVendorContractsList) {
                        contractIds.add(contract.getVendorContractGeneratedIdentifier());
                        HashMap fieldValues = new HashMap();
                        fieldValues.put("agreementNbr", contract.getVendorContractName());
                        List<Agreement> agreements = (List<Agreement>) bos.findMatching(
                                Agreement.class, fieldValues);
                        if (agreements.isEmpty()) {
                            addNewAgreement(financialVendorService, contract);
                        }
                        else {
                            for (int b = 0; b < agreements.size(); b++) {
                                updateExistingAgreement(financialVendorService, contract,
                                        agreements.get(b));
                            }
                        }
                    }

                }
            }
        }

        // update the agreements which are not associated with warehouse organizations
        Collection<Agreement> allAgreements = bos.findAll(Agreement.class);
        for (Agreement agreement : allAgreements) {
            Integer vndrContrGnrtdId = agreement.getVndrContrGnrtdId();
            if (vndrContrGnrtdId != null && contractIds.add(vndrContrGnrtdId)) {
                FinancialVendorContract contract = financialVendorService
                        .getVendorContract(vndrContrGnrtdId);
                if (contract != null) {
                    updateExistingAgreement(financialVendorService, contract, agreement);
                }
            }
        }
    }

    /**
     * @param fVendorService
     * @param contract
     * @param agreements
     * @param b
     */
    protected void updateExistingAgreement(FinancialVendorService fVendorService,
            FinancialVendorContract contract, Agreement agreement) {
        agreement.setVendorContractName(contract.getVendorContractName());
        agreement.setVndrContrGnrtdId(contract.getVendorContractGeneratedIdentifier());
        agreement.setVndrHeaderGnrtdId(contract.getVendorHeaderGeneratedIdentifier());
        agreement.setVndrDetailAsgnId(contract.getVendorDetailAssignedIdentifier());
        FinancialVendorDetail fVD = fVendorService
                .getVendorDetail(contract.getVendorHeaderGeneratedIdentifier(), contract
                        .getVendorDetailAssignedIdentifier());
        agreement.setVendorNm(fVD.getVendorName());
        agreement.setAgreementBeginDt(contract.getVendorContractBeginningDate());
        agreement.setAgreementEndDt(contract.getVendorContractEndDate());
        agreement.setB2bInd(contract.getVendorB2bIndicator());
        agreement.setPoId(contract.getPoId());
        KualiDecimal apoLimit = contract.getOrganizationAutomaticPurchaseOrderLimit();
        agreement.setPoTotalLimit(apoLimit == null ? KualiDecimal.ZERO : apoLimit);
        KualiDecimal usedAmount = contract.getTotalUsedAmt();
        agreement.setAgreementUsedAmt(usedAmount == null ? KualiDecimal.ZERO : usedAmount);
        agreement.setLastUpdateDate(KNSServiceLocator.getDateTimeService().getCurrentTimestamp());
        agreement.save();
    }

    /**
     * @param fVendorService
     * @param contract
     */
    protected void addNewAgreement(FinancialVendorService fVendorService,
            FinancialVendorContract contract) {
        Agreement agreement = new Agreement();
        agreement.setAgreementNbr(contract.getVendorContractName());
        agreement.setVendorContractName(contract.getVendorContractName());
        agreement.setVndrContrGnrtdId(contract.getVendorContractGeneratedIdentifier());
        agreement.setVndrHeaderGnrtdId(contract.getVendorHeaderGeneratedIdentifier());
        agreement.setVndrDetailAsgnId(contract.getVendorDetailAssignedIdentifier());
        FinancialVendorDetail fVD = fVendorService
                .getVendorDetail(contract.getVendorHeaderGeneratedIdentifier(), contract
                        .getVendorDetailAssignedIdentifier());
        agreement.setVendorNm(fVD.getVendorName());
        KualiDecimal apoLimit = contract.getOrganizationAutomaticPurchaseOrderLimit();
        agreement.setPoTotalLimit(apoLimit == null ? KualiDecimal.ZERO : apoLimit);
        KualiDecimal usedAmount = contract.getTotalUsedAmt();
        agreement.setAgreementUsedAmt(usedAmount == null ? KualiDecimal.ZERO : usedAmount);
        agreement.setAgreementBeginDt(contract.getVendorContractBeginningDate());
        agreement.setAgreementEndDt(contract.getVendorContractEndDate());
        agreement.setB2bInd(contract.getVendorB2bIndicator());
        agreement.setGhostCardInd(false);
        agreement.setElectronicInvoiceInd(false);
        agreement.setActive(true);
        agreement.setLastUpdateDate(KNSServiceLocator.getDateTimeService().getCurrentTimestamp());
        agreement.save();
    }
}
