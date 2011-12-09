package org.kuali.ext.mm.context;

import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemConfiguration;
import org.kuali.ext.mm.integration.ServiceNameInfo;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;



public class KFSRemoteServiceTest extends KualiTestBase {

    public void testAllServiceAvailability() throws Exception {
        FinancialSystemConfiguration config = SpringContext
                .getBean(FinancialSystemConfiguration.class);
        List<ServiceNameInfo> serviceNames = config.getServiceNames();
        for (ServiceNameInfo serviceNameInfo : serviceNames) {
            try {
                QName serviceName = new QName(serviceNameInfo.getServiceNameSpaceURI(),
                    serviceNameInfo.getLocalServiceName());
                Object service = GlobalResourceLoader.getService(serviceName);
                assertNotNull(service);
            }
            catch (Exception e) {
                fail("Failed accessing service " + serviceNameInfo.getName());
            }
        }
    }
}
