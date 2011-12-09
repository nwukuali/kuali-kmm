/**
 *
 */
package org.kuali.ext.mm.document.validation.impl;

import org.kuali.ext.mm.businessobject.Agreement;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorContract;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;


/**
 * @author harsha07
 */
public class AgreementRule extends MaintenanceDocumentRuleBase {
    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomRouteDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = super.processCustomRouteDocumentBusinessRules(document);
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        valid &= factory.checkAndErrorSystemAvailability();
        Agreement agreement = (Agreement) document.getNewMaintainableObject().getBusinessObject();
        if (valid && agreement.getVndrContrGnrtdId() != null) {
            FinancialVendorContract vendorContract = factory.getFinancialVendorService()
                    .getVendorContract(agreement.getVndrContrGnrtdId());
            if (vendorContract == null || !vendorContract.isActive()) {
                GlobalVariables.getMessageMap().putError(
                        KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + "vndrContrGnrtdId",
                        MMKeyConstants.Agreement.CONTRACT_NOT_VALID);
                valid &= false;
            }
        }
        return valid;
    }
}
