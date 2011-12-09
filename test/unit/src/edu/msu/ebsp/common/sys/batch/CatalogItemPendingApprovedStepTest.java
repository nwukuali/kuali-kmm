/**
 * 
 */
package edu.msu.ebsp.common.sys.batch;

import java.util.Date;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.PublishApprovedCatalogsStep;
import org.kuali.ext.mm.sys.batch.service.CatalogItemPendingService;

import edu.msu.ebsp.common.sys.context.MmTestBase;

/**
 * @author harsha07
 */
public class CatalogItemPendingApprovedStepTest extends MmTestBase {
    public void testInvoke() throws Exception {
        PublishApprovedCatalogsStep step = new PublishApprovedCatalogsStep();
        step.setCatalogItemPendingApprovedService(SpringContext
                .getBean(CatalogItemPendingService.class));
        long start = System.currentTimeMillis();
        step.execute("CatalogItemPendingApprovedJob", new Date());
        long end = System.currentTimeMillis();
        System.out.println("Time taken " + (end - start) + " ms");
    }
}
