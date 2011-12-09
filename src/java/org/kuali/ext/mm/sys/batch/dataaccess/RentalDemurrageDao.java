/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;


/**
 * @author harsha07
 */
public interface RentalDemurrageDao {

    public Map<String, List<FinancialAccountingLine>> getRentalDemurrageAccountLines();

    public Map<String, List<FinancialInternalBillingItem>> getRentalDemurrageItemLines();

    public int updateLastChargeDate(String rentalId);

}