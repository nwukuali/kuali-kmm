/*
 * Copyright 2007 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.common.sys.context;

import org.apache.log4j.Logger;
import org.kuali.rice.core.web.listener.KualiInitializeListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


@SuppressWarnings("unchecked")
public class WebApplicationInitListener extends KualiInitializeListener /*extends JstlConstantsInitListener*/ implements
        ServletContextListener {
    /**
     *
     */
    public static final String JSTL_CONSTANTS_CLASSNAMES_KEY = "jstl.constants.classnames";
    public static final String JSTL_CONSTANTS_MAIN_CLASS = "jstl.constants.main.class";
    public static final String JSTL_MAIN_CLASS_CONTEXT_NAME = "Constants";
    public static final Logger LOG = Logger.getLogger(WebApplicationInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
			//TODO: NWU - Expose Constants attribute on servlet context if required....
//        sce.getServletContext().setAttribute("Constants", new JSTLConstants(KEWConstants.class));

				System.setProperty("spring.mm.datasource.xml", "spring-mm-datasource.xml");
        System.setProperty("spring.mm.rice.jta.xml", "spring-mm-rice-jta.xml");

				LOG.info( "Initializing Web Context" );
        LOG.info( "Calling KualiInitializeListener.contextInitialized" );
        super.contextInitialized(sce);
        LOG.info( "Completed KualiInitializeListener.contextInitialized" );

        org.kuali.kfs.sys.context.SpringContext.finishInitializationAfterRiceStartup();




//				 PropertyConfigurator.configureAndWatch(settingsFile, reloadMilliseconds);
//        SimpleConfig config = new SimpleConfig("classpath:" + ContextConstants.CONFIGURATION_LOCATIONS_FILE);
//        try {
//            config.parseConfig();
//        }
//        catch (IOException e1) {
//            e1.printStackTrace();
//        }

//        try {
//            String serverPlatform = config.getProperty("server.platform");
//            if (!"JBOSS".equals(serverPlatform)) {
//                // refresh property every 5 minutes
//                Log4jConfigurer.initLogging(config
//                        .getProperty(ContextConstants.LOG4J_SETTINGS_FILE_KEY), 300000);
//            }
//        }
//        catch (Exception e2) {
//            e2.printStackTrace();
//        }
//        ConfigContext.init(config);
//        if (LOG.isDebugEnabled()) {
//            Properties p = ConfigContext.getCurrentContextConfig().getProperties();
//            Enumeration e = p.propertyNames();
//            while (e.hasMoreElements()) {
//                String name = e.nextElement().toString();
//                LOG.debug("name=" + name + " value=" + p.getProperty(name));
//            }
//        }
//        SpringContext.initializeApplicationContext();
//        super.contextInitialized(sce);
//        Formatter.registerFormatter(MMDecimal.class, MMDecimalFormatter.class);
//        LOG.info("Finished web application context initialization");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Started web application context destruction");
        SpringContext.close();
        super.contextDestroyed(sce);
    }
}
