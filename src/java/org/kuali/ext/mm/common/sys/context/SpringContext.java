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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.resourceloader.RiceResourceLoaderFactory;
import org.kuali.rice.kns.util.cache.MethodCacheInterceptor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.ltd.getahead.dwr.create.SpringCreator;

@SuppressWarnings("unchecked")
public class SpringContext {
    protected static final Logger LOG = Logger.getLogger(SpringContext.class);
    protected static final String MEMORY_MONITOR_THRESHOLD_KEY = "memory.monitor.threshold";
    protected static ConfigurableApplicationContext applicationContext;
    protected static final Set<Class<? extends Object>> SINGLETON_TYPES = new HashSet<Class<? extends Object>>();
    protected static final Set<String> SINGLETON_NAMES = new HashSet<String>();
    protected static final Map<Class<? extends Object>, Object> SINGLETON_BEANS_BY_TYPE_CACHE = new HashMap<Class<? extends Object>, Object>();
    protected static final Map<String, Object> SINGLETON_BEANS_BY_NAME_CACHE = new HashMap<String, Object>();
    protected static final Map<Class<? extends Object>, Map> SINGLETON_BEANS_OF_TYPE_CACHE = new HashMap<Class<? extends Object>, Map>();

    /**
     * Use this method to retrieve a service which may or may not be implemented locally. (That is, defined in the main Spring
     * ApplicationContext created by Rice.
     */
    public static Object getService(String serviceName) {
        return GlobalResourceLoader.getService(serviceName);
    }

    /**
     * Use this method to retrieve a spring bean when one of the following is the case. Pass in the type of the service interface,
     * NOT the service implementation. 1. there is only one bean of the specified type in our spring context 2. there is only one
     * bean of the specified type in our spring context, but you want the one whose bean id is the same as type.getSimpleName() with
     * the exception of the first letter being lower case in the former and upper case in the latter, For example, there are two
     * beans of type DateTimeService in our context – dateTimeService and testDateTimeService. To retrieve the former, you should
     * specific DateTimeService.class as the type. To retrieve the latter, you should specify ConfigurableDateService.class as the
     * type. Unless you are writing a unit test and need to down cast to an implementation, you do not need to cast the result of
     * this method.
     *
     * @param <T>
     * @param type
     * @return an object that has been defined as a bean in our spring context and is of the specified type
     */
    public static <T> T getBean(Class<T> type) {
        verifyProperInitialization();
        T bean = null;
        if (SINGLETON_BEANS_BY_TYPE_CACHE.containsKey(type)) {
            bean = (T) SINGLETON_BEANS_BY_TYPE_CACHE.get(type);
        }
        else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Bean not already in cache: " + type + " - calling getBeansOfType() ");
            }
            Collection<T> beansOfType = getBeansOfType(type).values();
            if (!beansOfType.isEmpty()) {
                if (beansOfType.size() > 1) {
                    bean = getBean(type, StringUtils.uncapitalize(type.getSimpleName()));
                }
                else {
                    bean = beansOfType.iterator().next();
                }
            }
            else { // unable to find bean - check GRL
                // this is needed in case no beans of the given type exist locally
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Bean not found in local context: " + type.getName()
                            + " - calling GRL");
                }
                Object remoteServiceBean = getService(StringUtils
                        .uncapitalize(type.getSimpleName()));
                if (remoteServiceBean != null) {
                    if (type.isAssignableFrom(remoteServiceBean.getClass())) {
                        bean = (T) remoteServiceBean;
                    }
                }
            }
            if (bean != null) {
                if (SINGLETON_TYPES.contains(type) || hasSingletonSuperType(type)) {
                    SINGLETON_TYPES.add(type);
                    SINGLETON_BEANS_BY_TYPE_CACHE.put(type, bean);
                }
            }
            else {
                throw new RuntimeException(
                    "Request for non-existent bean.  Unable to find in local context on on the GRL: "
                            + type.getName());
            }
        }
        return bean;
    }

    /**
     * Use this method to retrieve all beans of a give type in our spring context. Pass in the type of the service interface, NOT
     * the service implementation.
     *
     * @param <T>
     * @param type
     * @return a map of the spring bean ids / beans that are of the specified type
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        verifyProperInitialization();
        Map<String, T> beansOfType = null;
        if (SINGLETON_BEANS_OF_TYPE_CACHE.containsKey(type)) {
            beansOfType = SINGLETON_BEANS_OF_TYPE_CACHE.get(type);
        }
        else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Bean not already in \"OF_TYPE\" cache: " + type
                        + " - calling getBeansOfType() on Spring context");
            }
            boolean allOfTypeAreSingleton = true;
            beansOfType = applicationContext.getBeansOfType(type);
            for (String key : beansOfType.keySet()) {
                if (!applicationContext.isSingleton(key)) {
                    allOfTypeAreSingleton = false;
                }
            }
            if (allOfTypeAreSingleton) {
                SINGLETON_TYPES.add(type);
                SINGLETON_BEANS_OF_TYPE_CACHE.put(type, beansOfType);
            }
        }
        return beansOfType;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getBean(Class<T> type, String name) {
        T bean = null;
        if (SINGLETON_BEANS_BY_NAME_CACHE.containsKey(name)) {
            bean = (T) SINGLETON_BEANS_BY_NAME_CACHE.get(name);
        }
        else {
            try {
                bean = (T) applicationContext.getBean(name);
                if (applicationContext.isSingleton(name)) {
                    SINGLETON_BEANS_BY_NAME_CACHE.put(name, bean);
                }
            }
            catch (NoSuchBeanDefinitionException nsbde) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Bean with name and type not found in local context: " + name + "/"
                            + type.getName() + " - calling GRL");
                }
                Object remoteServiceBean = getService(name);
                if (remoteServiceBean != null) {
                    if (type.isAssignableFrom(remoteServiceBean.getClass())) {
                        bean = (T) remoteServiceBean;
                        // assume remote beans are services and thus singletons
                        SINGLETON_BEANS_BY_NAME_CACHE.put(name, bean);
                    }
                }
                throw new RuntimeException(
                    "No bean of this type and name exist in the application context or from the GRL: "
                            + type.getName() + ", " + name);
            }
        }
        return bean;
    }

    private static boolean hasSingletonSuperType(Class<? extends Object> type) {
        for (Class<? extends Object> singletonType : SINGLETON_TYPES) {
            if (singletonType.isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }

    public static List<MethodCacheInterceptor> getMethodCacheInterceptors() {
        List<MethodCacheInterceptor> methodCacheInterceptors = new ArrayList<MethodCacheInterceptor>();
        methodCacheInterceptors.add(getBean(MethodCacheInterceptor.class));
        return methodCacheInterceptors;
    }

    public static Object getBean(String beanName) {
        return getBean(Object.class, beanName);
    }

    public static String[] getBeanNames() {
        verifyProperInitialization();
        return applicationContext.getBeanDefinitionNames();
    }

    public static void initializeApplicationContext() {
        initializeApplicationContext(ConfigContext
                .getCurrentContextConfig().getProperty(ContextConstants.DEFAULT_SPRING_FILE));
    }

    public static void initializeTestApplicationContext() {
        initializeApplicationContext(ConfigContext.getCurrentContextConfig()
                .getProperty(ContextConstants.DEFAULT_TEST_SPRING_FILE));
    }

    protected static void close() {
        if (applicationContext != null)
            applicationContext.close();
    }

    private static void verifyProperInitialization() {
        if (applicationContext == null) {
            throw new RuntimeException(
                "Spring not initialized properly.  Initialization has begun and the application context is null.  Probably spring loaded bean is trying to use KNSServiceLocator before the application context is initialized.");
        }
    }

    private static void initializeApplicationContext(String riceInitializationSpringFile) {
        LOG.info("Starting Spring context initialization");
        // use the base config file to bootstrap the real application context started by Rice
        new ClassPathXmlApplicationContext(riceInitializationSpringFile);
        // pull the Rice application context into here for further use and efficiency
        applicationContext = RiceResourceLoaderFactory.getSpringResourceLoader().getContext();
        LOG.info("Completed Spring context initialization");
        SpringCreator.setOverrideBeanFactory(applicationContext.getBeanFactory());
    }
}
