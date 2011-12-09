/**
 * 
 */
package edu.msu.ebsp.common.sys.batch;

import java.util.Date;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.LoadPendingCatalogItemsStep;
import org.kuali.ext.mm.sys.batch.service.CatalogPendingDocLoadService;

import edu.msu.ebsp.common.sys.context.MmTestBase;

/**
 * @author harsha07
 */
public class CatalogPendingDocLoadStepTest extends MmTestBase {

    public void testInvoke() throws Exception {
        LoadPendingCatalogItemsStep loadPendingCatalogItemsStep = new LoadPendingCatalogItemsStep();
        loadPendingCatalogItemsStep.setCatalogPendingDocLoadService(SpringContext
                .getBean(CatalogPendingDocLoadService.class));
        long start = System.currentTimeMillis();
        loadPendingCatalogItemsStep.execute("CatalogPendingDocLoadJob", new Date());
        long end = System.currentTimeMillis();
        System.out.println("Time taken " + (end - start) + " ms");

    }
}
