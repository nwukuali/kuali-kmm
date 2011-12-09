/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;


/**
 * @author harsha07
 */
public class FinancialSystemVerifyStep implements BatchStep {

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (factory == null || !factory.isSystemAvailable()) {
            throw new RuntimeException("Financial System Services are not available.");
        }
    }
}
