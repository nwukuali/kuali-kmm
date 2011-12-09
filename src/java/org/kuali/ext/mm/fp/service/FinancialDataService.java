/**
 *
 */
package org.kuali.ext.mm.fp.service;


/**
 * @author harsha07
 */
public interface FinancialDataService {

    public boolean validateChart(String chartCode);

    public boolean validateOrg(String chartCode, String orgCode);

    public boolean validateAccount(String chartCode, String accountNumber);

    public boolean validateSubAccount(String chartCode, String accountNumber,
            String subAccountNumber);

    public boolean validateObjectCode(Integer fiscalYear, String chartCode, String objectCode);

    public boolean validateSubObjectCode(Integer fiscalYear, String chartCode, String acctNbr,
            String objectCode, String subObjCode);

    public boolean validateProject(String projectCode);

    public boolean validateBuilding(String campusCode, String buildingCode);

    public boolean validateBuildingRoom(String campusCode, String buildingCode, String roomNumber);

    public boolean validateInternalBillingProperty(String parameterName, String propertyValue);

}