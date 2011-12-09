/**
 *
 */
package org.kuali.ext.mm.sys.service.impl;

/**
 *
 */

import java.util.Calendar;
import java.util.Date;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;


/**
 * @author rponraj
 */
public class FiscalYear {

    private Date beginDate = null;
    private Date endDate = null;

    private FiscalYear(int index) {
        int fiscalYear = Calendar.getInstance().get(Calendar.YEAR) - index;
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (factory.isSystemAvailable()) {
            this.beginDate = factory.getFinancialUniversityDateService().getFirstDateOfFiscalYear(
                    fiscalYear);
            this.endDate = factory.getFinancialUniversityDateService().getLastDateOfFiscalYear(
                    fiscalYear);
        }
        else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, fiscalYear);
            this.beginDate = cal.getTime();
            this.endDate = cal.getTime();
        }
    }

    public static final FiscalYear getLastFiscalYear() {
        return new FiscalYear(0);
    }

    public static final FiscalYear getSecondFiscalYear() {
        return new FiscalYear(1);
    }

    public static final FiscalYear getThirdFiscalYear() {
        return new FiscalYear(2);
    }

    /**
     * Gets the beginDate property
     * 
     * @return Returns the beginDate
     */
    public Date getBeginDate() {
        return this.beginDate;
    }

    /**
     * Sets the beginDate property value
     * 
     * @param beginDate The beginDate to set
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * Gets the endDate property
     * 
     * @return Returns the endDate
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the endDate property value
     * 
     * @param endDate The endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
