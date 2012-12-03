package org.kuali.ext.mm;

import org.junit.Test;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.impl.config.property.JAXBConfigImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

public class LoadSpringContextTest {

	@Test
	public void loadContext() throws Exception {
//				Properties baseProps = new Properties();
//        baseProps.putAll(System.getProperties());
//				baseProps.put("log4j.settings.path","classpath:log4j.properties");
//				baseProps.setProperty("web.bootstrap.spring.file","classpath:spring-mm-rice-startup.xml");
//        JAXBConfigImpl config = new JAXBConfigImpl(baseProps);
//        ConfigContext.init(config);


		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
		context.setConfigLocation("classpath:spring-mm-rice-startup.xml");
		context.refresh();
//        context.start();
	}

}
