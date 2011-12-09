/**
 * 
 */
package org.kuali.ext.mm.document.validation.impl;

import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.OrderAutoLimit;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.MMBusinessObjectDao;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.util.KNSConstants;


/**
 * @author harsha07
 */
public class OrderAutoLimitRule extends FinancialMaintenanceDocumentRuleBase {
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = super.processCustomRouteDocumentBusinessRules(document);
        valid &= SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .checkAndErrorSystemAvailability();
        OrderAutoLimit autoLimit = (OrderAutoLimit) document.getNewMaintainableObject()
                .getBusinessObject();
        // validate chart code
        valid &= validateChart(autoLimit.getChartCode(), KNSConstants.MAINTENANCE_NEW_MAINTAINABLE
                + "chartCode", MMKeyConstants.OrderAutoLimit.CHART_NOT_VALID);
        // validate org code
//        valid &= validateOrg(autoLimit.getChartCode(), autoLimit.getOrgCode(),
//                KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + "orgCode",
//                MMKeyConstants.OrderAutoLimit.ORG_NOT_VALID);
        // validate account number
        valid &= validateAccount(autoLimit.getChartCode(), autoLimit.getAccountNumber(),
                KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + "accountNumber",
                MMKeyConstants.OrderAutoLimit.ACCT_NOT_VALID);

        if (document.isNew()) {
            valid &= checkDuplicateEntry(autoLimit);
        }
        return valid;
    }

    /**
     * @param valid
     * @param autoLimit
     * @return
     */
    private boolean checkDuplicateEntry(OrderAutoLimit autoLimit) {
        boolean valid = true;
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("chartCode", autoLimit.getChartCode());
        fieldValues.put("orgCode", autoLimit.getOrgCode());
        if (StringUtils.isNotBlank(autoLimit.getAccountNumber())) {
            fieldValues.put("accountNumber", autoLimit.getAccountNumber());
        }
        else {
            fieldValues.put("accountNumber", null);
        }
        // check for duplicate records
        Collection matched = SpringContext.getBean(MMBusinessObjectDao.class).findMatching(
                OrderAutoLimit.class, fieldValues);
        if (matched != null && !matched.isEmpty()) {
            putFieldError("orgCode", MMKeyConstants.OrderAutoLimit.ERROR_DUPLICATE_LIMIT,
                    new String[] { autoLimit.getChartCode(), autoLimit.getOrgCode(),
                            autoLimit.getAccountNumber() });
            valid = false;
        }
        return valid;
    }
}
