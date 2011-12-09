/**
 * 
 */
package edu.msu.ebsp.common.sys.batch;

import java.util.Date;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.service.BatchControlService;
import org.springframework.transaction.PlatformTransactionManager;

import edu.msu.ebsp.common.sys.context.MmTestBase;

/**
 * @author harsha07
 */
public class BatchJobRunnerTest extends MmTestBase {
    public void testInvoke() throws Exception {
        SpringContext.getBean(BatchControlService.class).performJob("orderReconciliationJob",
                new Date());
    }

    @Override
    protected void tearDown() throws Exception {
        SpringContext.getBean(PlatformTransactionManager.class).rollback(getTransactionStatus());
    }
}
