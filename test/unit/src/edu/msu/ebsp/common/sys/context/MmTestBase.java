package edu.msu.ebsp.common.sys.context;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.common.sys.context.ContextConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.web.format.MMDecimalFormatter;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.config.SimpleConfig;
import org.kuali.rice.kns.web.format.Formatter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Log4jConfigurer;

public class MmTestBase extends TestCase {
    private static final Logger LOG = Logger.getLogger(MmTestBase.class);
    private static boolean initialized;

    private TransactionStatus transactionStatus;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setupCommon();
    }

    private void setupCommon() {
        if (!initialized) {
            SimpleConfig config = new SimpleConfig("classpath:"
                    + ContextConstants.CONFIGURATION_LOCATIONS_FILE);
            try {
                config.parseConfig();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                String serverPlatform = config.getProperty("server.platform");
                if (!"JBOSS".equals(serverPlatform)) {
                    // refresh property every 5 minutes
                    Log4jConfigurer.initLogging(config
                            .getProperty(ContextConstants.LOG4J_SETTINGS_FILE_KEY), 300000);
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            ConfigContext.init(config);
            if (LOG.isDebugEnabled()) {
                Properties p = ConfigContext.getCurrentContextConfig().getProperties();
                Enumeration e = p.propertyNames();
                while (e.hasMoreElements()) {
                    String name = e.nextElement().toString();
                    LOG.debug("name=" + name + " value=" + p.getProperty(name));
                }
            }
            SpringContext.initializeApplicationContext();
            Formatter.registerFormatter(MMDecimal.class, MMDecimalFormatter.class);
            LOG.info("Finished web application context initialization");
            initialized = true;
        }
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setTimeout(3600);
        transactionStatus = SpringContext.getBean(PlatformTransactionManager.class).getTransaction(
                defaultTransactionDefinition);
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void setInitialized(boolean initialized) {
        MmTestBase.initialized = initialized;
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        SpringContext.getBean(PlatformTransactionManager.class).rollback(this.transactionStatus);
    }
}
