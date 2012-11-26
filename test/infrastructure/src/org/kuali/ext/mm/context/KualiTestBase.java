package org.kuali.ext.mm.context;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.kuali.ext.mm.KualiTestConstants;
import org.kuali.ext.mm.common.sys.context.ContextConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.framework.config.property.SimpleConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Log4jConfigurer;


public class KualiTestBase extends TestCase implements KualiTestConstants {
    private static boolean initialized;
    private static boolean log4jConfigured = false;

    private static TransactionStatus transactionStatus;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setupContext();
    }

    @BeforeClass
    public static void setupContext() throws Exception {
        setupCommon();
    }

    private static void setupCommon() {
        if (!initialized) {
            SimpleConfig config = new SimpleConfig("classpath:"
                    + ContextConstants.CONFIGURATION_LOCATIONS_FILE);
            try {
                config.parseConfig();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }

            ConfigContext.init(config);
            if (!log4jConfigured) {
                try {
                    Log4jConfigurer.initLogging(config
                            .getProperty(ContextConstants.LOG4J_SETTINGS_FILE_KEY), 300000);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                log4jConfigured = true;
            }
            SpringContext.initializeApplicationContext();
            initialized = true;
        }

    }

    @Before
    public void setupTransaction() {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setTimeout(3600);
        transactionStatus = SpringContext.getBean(PlatformTransactionManager.class).getTransaction(
                defaultTransactionDefinition);
    }

    @After
    public void tearDownTransaction() throws Exception {
        SpringContext.getBean(PlatformTransactionManager.class).rollback(transactionStatus);
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        KualiTestBase.transactionStatus = transactionStatus;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void setInitialized(boolean initialized) {
        KualiTestBase.initialized = initialized;
    }
}
