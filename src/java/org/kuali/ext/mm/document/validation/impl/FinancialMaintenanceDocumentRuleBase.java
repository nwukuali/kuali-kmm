package org.kuali.ext.mm.document.validation.impl;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.fp.service.FinancialDataService;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;


public class FinancialMaintenanceDocumentRuleBase extends MaintenanceDocumentRuleBase {
    /**
     * Validates chart code
     *
     * @param chartCode Chart code to be validated
     * @param errorPath Error path
     * @param errorKey Error property
     * @return true if valid
     */
    protected boolean validateChart(String chartCode, String errorPath, String errorKey) {
        boolean valid = getFinancialDataService().validateChart(chartCode);
        if (!valid) {
            GlobalVariables.getMessageMap().putError(errorPath, errorKey, chartCode);
        }
        return valid;
    }

    /**
     * Validates organization code
     *
     * @param chartCode Chart code
     * @param orgCode Org code
     * @param errorPath Error path
     * @param errorKey Error property
     * @return true if chart\org code is valid
     */
    protected boolean validateOrg(String chartCode, String orgCode, String errorPath,
            String errorKey) {
        boolean valid = getFinancialDataService().validateOrg(chartCode, orgCode);
        if (!valid) {
            GlobalVariables.getMessageMap().putError(errorPath, errorKey, chartCode, orgCode);
        }
        return valid;
    }

    /**
     * Validates account number
     *
     * @param chartCode Chart code
     * @param accountNumber Account number
     * @param errorPath Error path
     * @param errorKey Error property
     * @return true if chart/account is valid
     */
    protected boolean validateAccount(String chartCode, String accountNumber, String errorPath,
            String errorKey) {
        boolean valid = getFinancialDataService().validateAccount(chartCode, accountNumber);
        if (!valid) {
            GlobalVariables.getMessageMap().putError(errorPath, errorKey, chartCode, accountNumber);
        }
        return valid;
    }

    /**
     * Validates sub account number
     *
     * @param chartCode Chart code
     * @param accountNumber Account Number
     * @param subAccountNumber Sub Account Number
     * @param errorPath Error path
     * @param errorKey Error key
     * @return true if chart/account/sub account is valid
     */
    protected boolean validateSubAccount(String chartCode, String accountNumber,
            String subAccountNumber, String errorPath, String errorKey) {
        boolean valid = getFinancialDataService().validateSubAccount(chartCode, accountNumber,
                subAccountNumber);
        if (!valid) {
            GlobalVariables.getMessageMap().putError(errorPath, errorKey, chartCode, accountNumber,
                    subAccountNumber);
        }
        return valid;
    }

    /**
     * Validates financial object code
     *
     * @param fiscalYear Fiscal year
     * @param chartCode Chart Code
     * @param objectCode Object Code
     * @param errorPath Error Path
     * @param errorKey Error Key
     * @return boolean
     */
    protected boolean validateObjectCode(Integer fiscalYear, String chartCode, String objectCode,
            String errorPath, String errorKey) {
        boolean valid = getFinancialDataService().validateObjectCode(fiscalYear, chartCode,
                objectCode);
        if (!valid) {
            GlobalVariables.getMessageMap().putError(errorPath, errorKey, chartCode, objectCode);
        }
        return valid;
    }

    /**
     * Validates financial sub object code
     *
     * @param fiscalYear Fiscal year
     * @param chartCode Chart Code
     * @param acctNbr Account Number
     * @param objectCode Object Code
     * @param subObjCode Sub Object Code
     * @param errorPath Error Path
     * @param errorKey Error Key
     * @return boolean
     */
    protected boolean validateSubObjectCode(Integer fiscalYear, String chartCode, String acctNbr,
            String objectCode, String subObjCode, String errorPath, String errorKey) {
        boolean valid = getFinancialDataService().validateSubObjectCode(fiscalYear, chartCode,
                acctNbr, objectCode, subObjCode);
        if (!valid) {
            GlobalVariables.getMessageMap().putError(errorPath, errorKey, chartCode, acctNbr,
                    objectCode, subObjCode);
        }
        return valid;
    }

    public FinancialDataService getFinancialDataService() {
        return SpringContext.getBean(FinancialDataService.class);
    }
}
