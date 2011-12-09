package org.kuali.ext.mm.document.validation.impl;

import org.kuali.ext.mm.businessobject.CatalogRestriction;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.util.KNSConstants;


public class CatalogRestrictionRule extends FinancialMaintenanceDocumentRuleBase {

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = super.processCustomRouteDocumentBusinessRules(document);
        valid &= SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .checkAndErrorSystemAvailability();
        CatalogRestriction restriction = (CatalogRestriction) document.getNewMaintainableObject().getBusinessObject();
        // validate chart code
        valid &= validateChart(restriction.getFinacialChartOfAccountsCode(), KNSConstants.MAINTENANCE_NEW_MAINTAINABLE
                + "finacialChartOfAccountsCode", MMKeyConstants.CatalogRestriction.CHART_NOT_VALID);
        // validate org code
        valid &= validateOrg(restriction.getFinacialChartOfAccountsCode(), restriction.getOrganizationCode(),
                KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + "organizationCode",
                MMKeyConstants.CatalogRestriction.ORG_NOT_VALID);
        // validate account number
        valid &= validateAccount(restriction.getFinacialChartOfAccountsCode(), restriction.getAccountNumber(),
                KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + "accountNumber",
                MMKeyConstants.CatalogRestriction.ACCT_NOT_VALID);


        return valid;
    }
}
