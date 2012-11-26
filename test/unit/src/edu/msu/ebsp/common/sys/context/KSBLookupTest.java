/**
 *
 */
package edu.msu.ebsp.common.sys.context;

import edu.msu.ebsp.mm.service.dto.OrderInfoDto;
import org.kuali.ext.mm.integration.service.impl.mm.OrderQueryServiceImpl;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;

import javax.xml.namespace.QName;


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
        OrderQueryServiceImpl service = (OrderQueryServiceImpl) GlobalResourceLoader
                .getService(serviceName);
        OrderInfoDto order = service.findOrderInfoById(5013l);
    }
}
