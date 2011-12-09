/**
 *
 */
package org.kuali.ext.mm.fp.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.fp.service.FinancialDataService;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialAccount;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialChart;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialObjectCode;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialOrganization;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialProjectCode;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialSubAccount;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialSubObjectCode;
import org.kuali.ext.mm.integration.service.FinancialAccountService;
import org.kuali.ext.mm.integration.service.FinancialChartService;
import org.kuali.ext.mm.integration.service.FinancialInternalBillingService;
import org.kuali.ext.mm.integration.service.FinancialLocationService;
import org.kuali.ext.mm.integration.service.FinancialObjectCodeService;
import org.kuali.ext.mm.integration.service.FinancialOrganizationService;
import org.kuali.ext.mm.integration.service.FinancialProjectCodeService;
import org.kuali.ext.mm.integration.service.FinancialSubAccountService;
import org.kuali.ext.mm.integration.service.FinancialSubObjectCodeService;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialRoom;


/**
 * @author harsha07
 */
public class FinancialDataServiceImpl implements FinancialDataService {


    /**
     * @see org.kuali.ext.mm.fp.service.FinancialDataService#validateChart(java.lang.String)
     */
    public boolean validateChart(String chartCode) {
        boolean valid = true;
        if (StringUtils.isBlank(chartCode)) {
            return valid;
        }
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (financialSystemAdaptorFactory.isSystemAvailable()) {
            FinancialChartService chartService = financialSystemAdaptorFactory.getChartService();
            FinancialChart chart = chartService.getByPrimaryId(chartCode);
            if (chart == null || !chart.isActive()) {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * @see org.kuali.ext.mm.fp.service.FinancialDataService#validateOrg(java.lang.String, java.lang.String)
     */
    public boolean validateOrg(String chartCode, String orgCode) {
        boolean valid = true;
        if (StringUtils.isBlank(orgCode)) {
            return valid;
        }
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (financialSystemAdaptorFactory.isSystemAvailable()) {
            FinancialOrganizationService organizationService = financialSystemAdaptorFactory
                    .getOrganizationService();
            FinancialOrganization org = organizationService.getByPrimaryId(chartCode, orgCode);
            if (org == null || !org.isActive()) {
                valid = false;
            }
        }
        return valid;
    }


    public boolean validateAccount(String chartCode, String accountNumber) {
        boolean valid = true;
        if (StringUtils.isBlank(accountNumber)) {
            return valid;
        }
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (financialSystemAdaptorFactory.isSystemAvailable()) {
            FinancialAccountService accountService = financialSystemAdaptorFactory
                    .getAccountService();
            FinancialAccount acct = accountService.getByPrimaryId(chartCode, accountNumber);
            if (acct == null || !acct.isActive() || acct.isExpired()) {
                valid = false;
            }
        }
        return valid;
    }


    /**
     * @see org.kuali.ext.mm.fp.service.FinancialDataService#validateSubAccount(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public boolean validateSubAccount(String chartCode, String accountNumber,
            String subAccountNumber) {
        boolean valid = true;
        if (StringUtils.isBlank(subAccountNumber)) {
            return valid;
        }
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (financialSystemAdaptorFactory.isSystemAvailable()) {
            FinancialSubAccountService subAccountService = financialSystemAdaptorFactory
                    .getSubAccountService();
            FinancialSubAccount subAccount = subAccountService.getByPrimaryId(chartCode,
                    accountNumber, subAccountNumber);
            if (subAccount == null || !subAccount.isActive()) {
                valid = false;
            }
        }
        return valid;
    }


    public boolean validateObjectCode(Integer fiscalYear, String chartCode, String objectCode) {
        boolean valid = true;
        if (StringUtils.isBlank(objectCode)) {
            return valid;
        }
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (financialSystemAdaptorFactory.isSystemAvailable()) {
            FinancialObjectCodeService service = financialSystemAdaptorFactory
                    .getFinancialObjectCodeService();
            FinancialObjectCode objCode = service.getByPrimaryId(
                    fiscalYear == null ? financialSystemAdaptorFactory
                            .getFinancialUniversityDateService().getCurrentFiscalYear()
                            : fiscalYear, chartCode, objectCode);
            if (objCode == null || !objCode.isActive()) {
                valid = false;
            }
        }
        return valid;
    }


    /**
     * @see org.kuali.ext.mm.fp.service.FinancialDataService#validateSubObjectCode(java.lang.Integer, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean validateSubObjectCode(Integer fiscalYear, String chartCode, String acctNbr,
            String objectCode, String subObjCode) {
        boolean valid = true;
        if (StringUtils.isBlank(subObjCode)) {
            return valid;
        }
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (financialSystemAdaptorFactory.isSystemAvailable()) {
            FinancialSubObjectCodeService service = financialSystemAdaptorFactory
                    .getFinancialSubObjectCodeService();
            FinancialSubObjectCode subObj = service.getByPrimaryId(
                    fiscalYear == null ? financialSystemAdaptorFactory
                            .getFinancialUniversityDateService().getCurrentFiscalYear()
                            : fiscalYear, chartCode, acctNbr, objectCode, subObjCode);
            if (subObj == null || !subObj.isActive()) {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * @see org.kuali.ext.mm.fp.service.FinancialDataService#validateBuilding(java.lang.String)
     */
    public boolean validateBuilding(String campusCode, String buildingCode) {
        boolean valid = true;
        if (StringUtils.isBlank(buildingCode)) {
            return valid;
        }
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (financialSystemAdaptorFactory.isSystemAvailable()) {
            FinancialLocationService locationService = financialSystemAdaptorFactory
                    .getFinancialLocationService();
            FinancialBuilding building = locationService.getBuilding(campusCode, buildingCode);
            if (building == null || !building.isActive()) {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * @see org.kuali.ext.mm.fp.service.FinancialDataService#validateBuildingRoom(java.lang.String, java.lang.String)
     */
    public boolean validateBuildingRoom(String campusCode, String buildingCode, String roomNumber) {
        boolean valid = true;
        if (StringUtils.isBlank(roomNumber)) {
            return valid;
        }
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (financialSystemAdaptorFactory.isSystemAvailable()) {
            FinancialLocationService locationService = financialSystemAdaptorFactory
                    .getFinancialLocationService();
            FinancialRoom room = locationService.getRoom(campusCode, buildingCode, roomNumber);
            if (room == null || !room.isActive()) {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * @see org.kuali.ext.mm.fp.service.FinancialDataService#validateProject(java.lang.String)
     */
    public boolean validateProject(String projectCode) {
        boolean valid = true;
        if (StringUtils.isBlank(projectCode)) {
            return valid;
        }
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (financialSystemAdaptorFactory.isSystemAvailable()) {
            FinancialProjectCodeService projectService = financialSystemAdaptorFactory
                    .getFinancialProjectCodeService();
            FinancialProjectCode project = projectService.getByPrimaryId(projectCode);
            if (project == null || !project.isActive()) {
                valid = false;
            }
        }
        return valid;
    }

    public boolean validateInternalBillingProperty(String parameterName, String propertyValue) {
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (financialSystemAdaptorFactory.isSystemAvailable()) {
            FinancialInternalBillingService ibService = financialSystemAdaptorFactory
                    .getFinancialInternalBillingService();
            return ibService.validateProperty(parameterName, propertyValue);
        }
        return true;
    }

}
