/**
 *
 */
package edu.msu.ebsp.common.sys.context;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;

import edu.msu.ebsp.mm.service.OrderQueryService;
import edu.msu.ebsp.mm.service.dto.OrderInfoDto;

/**
 * @author harsha07
 */
public class KSBLookupTest extends MmTestBase {
    /**
     * @see org.kuali.ext.mm.common.sys.context.MmTestBase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testKSBLookup() throws Exception {
        QName serviceName = new QName("http://kuali.org/kmm", "kmmOrderQueryService");
        OrderQueryService service = (OrderQueryService) GlobalResourceLoader
                .getService(serviceName);
        OrderInfoDto order = service.findOrderInfoById(5013l);
    }
}
