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
package org.kuali.ext.mm.context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.junit.Test;
import org.kuali.ext.mm.ConfigureContext;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.suite.AnnotationTestSuite;
import org.kuali.ext.mm.suite.PreCommitSuite;
import org.springframework.aop.framework.AopProxyUtils;

@AnnotationTestSuite(PreCommitSuite.class)
@ConfigureContext
/**
 * This test checks that services are properly annotated as either Transactional
 * or @link NonTransactional.  The first test is a superset of the subsequent
 * test.  The first test will always fail if one of the subsequent test fails.
 * Acceptable annotations are either at the class level or on each of the public
 * methods, but not both.
 */
public class TransactionalAnnotationTest extends KualiTestBase {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TransactionalAnnotationTest.class);

    Map<Class<? extends Object>, Boolean> seenClasses = new HashMap<Class<? extends Object>, Boolean>();
    List<Class<? extends Object>> excludedClasses = new ArrayList<Class<? extends Object>>();
    Map<String, String> doubleAnnotatedTransactionalServices;
    Map<String, String> nonAnnotatedTransactionalServices;
    Map<String, Class<? extends Object>> incorrectlyAnnotatedTransactionalServices;

//    {
//        excludedClasses.add( SubFundGroupServiceImpl.class );
//        excludedClasses.add( SensitiveDataServiceImpl.class );
//    }
//

    @Test
    public void testTransactionAnnotations() {
        getNonAnnotatedTransactionalServices();
        for (String beanName : new TreeSet<String>(incorrectlyAnnotatedTransactionalServices.keySet())) {
            LOG.error(String.format("Service Bean improperly annotated: %s <%s>\n", beanName, incorrectlyAnnotatedTransactionalServices.get(beanName).getName()));
        }
        int count = incorrectlyAnnotatedTransactionalServices.size();
        StringBuffer failureMessage = new StringBuffer("Transaction support for ").append(count).append(count == 1 ? " Service" : " Services").append(" improperly annotated: ");
        for (String serviceName : incorrectlyAnnotatedTransactionalServices.keySet()) {
            failureMessage.append("\t").append(serviceName).append(": ").append(incorrectlyAnnotatedTransactionalServices.get(serviceName));
        }
        assertTrue(failureMessage.toString(), incorrectlyAnnotatedTransactionalServices.isEmpty());

    }

    @Test
    public void testNoTransactionAnnotations() {
        getNonAnnotatedTransactionalServices();
        for (String beanName : new TreeSet<String>(nonAnnotatedTransactionalServices.keySet())) {
            LOG.error(String.format("Service Bean not annotated: %s <%s>\n", beanName, nonAnnotatedTransactionalServices.get(beanName)));
        }
        int count = nonAnnotatedTransactionalServices.size();
        StringBuffer failureMessage = new StringBuffer("Transaction support for ").append(count).append(count == 1 ? " Service" : " Services").append(" not annotated: ");
        for (String serviceName : nonAnnotatedTransactionalServices.keySet()) {
            failureMessage.append("\t").append(serviceName).append(": ").append(nonAnnotatedTransactionalServices.get(serviceName));
        }
        assertTrue(failureMessage.toString(), nonAnnotatedTransactionalServices.isEmpty());

    }

    @Test
    public void testDoubleTransactionAnnotations() {
        getNonAnnotatedTransactionalServices();
        for (String beanName : new TreeSet<String>(doubleAnnotatedTransactionalServices.keySet())) {
            LOG.error(String.format("Service Bean improperly annotated: %s <%s>\n", beanName, doubleAnnotatedTransactionalServices.get(beanName)));
        }
        int count = doubleAnnotatedTransactionalServices.size();
        StringBuffer failureMessage = new StringBuffer("Transaction support for ").append(count).append(count == 1 ? " Service" : " Services").append(" double annotated: ");
        for (String serviceName : doubleAnnotatedTransactionalServices.keySet()) {
            failureMessage.append("\t").append(serviceName).append(": ").append(doubleAnnotatedTransactionalServices.get(serviceName));
        }
        assertTrue(failureMessage.toString(), doubleAnnotatedTransactionalServices.isEmpty());

    }

    @SuppressWarnings("deprecation")
    public void getNonAnnotatedTransactionalServices() {
        /* We only want to run getNonAnnotatedTransactionalSerivces once.
         * The tests actually just read the Maps that are generated here.
         */
        if (incorrectlyAnnotatedTransactionalServices != null) return;
        incorrectlyAnnotatedTransactionalServices = new HashMap<String, Class<? extends Object>>();
        nonAnnotatedTransactionalServices = new HashMap<String, String>();
        doubleAnnotatedTransactionalServices = new HashMap<String, String>();

        String[] beanNames = SpringContext.getBeanNames();
        for (String beanName : beanNames) {
            Object bean = null;
            try {
                bean = SpringContext.getBean(beanName);
            }
            catch (Exception e) {
                LOG.warn("Caught exception while trying to obtain service: " + beanName);
            }
            if (bean != null) {
                Class<? extends Object> beanClass = bean.getClass();
                if (beanClass.getName().startsWith("$Proxy")) {
                    beanClass = AopProxyUtils.getTargetClass(bean);
                }
                if (beanClass.getName().startsWith("org.kuali")
                        && !Modifier.isAbstract(beanClass.getModifiers())
                        && !beanClass.getName().endsWith("DaoOjb")
                        && !beanClass.getName().endsWith("Factory")
                        && !beanClass.getName().contains("Lookupable")
                        && !isClassAnnotated(beanName, beanClass)) {
                    incorrectlyAnnotatedTransactionalServices.put(beanName, beanClass);
                }
            }
        }
        return;
    }

    private boolean isExcludedClass( Class<? extends Object> beanClass ) {
        return beanClass.getName().startsWith("org.kuali.rice")
                || excludedClasses.contains(beanClass);
    }

    private boolean isClassAnnotated(String beanName, Class<? extends Object> beanClass) {
        boolean hasClassAnnotation = false;
        if (shouldHaveTransaction(beanClass)&& !isExcludedClass(beanClass)){
            if (beanClass.getAnnotation(org.springframework.transaction.annotation.Transactional.class) != null) {
                hasClassAnnotation = true;
            }
//            if (beanClass.getAnnotation(org.kuali.ext.mm.service.NonTransactional.class) != null){
//                hasClassAnnotation =  true;
//            }


            boolean hasMethodAnnotation;

            for( Method beanMethod : beanClass.getDeclaredMethods()){
                if (Modifier.isPublic(beanMethod.getModifiers())){
                    hasMethodAnnotation = false;
                    if (beanMethod.getAnnotation(org.springframework.transaction.annotation.Transactional.class) != null) hasMethodAnnotation = true;
//                    if (beanMethod.getAnnotation(org.kuali.ext.mm.service.NonTransactional.class) != null) hasMethodAnnotation = true;
                    if (hasMethodAnnotation == false && hasClassAnnotation == false) {
                        nonAnnotatedTransactionalServices.put(beanName, beanMethod.getName());
                        return false;
                    }
                    if (hasMethodAnnotation == true && hasClassAnnotation == true){
                        doubleAnnotatedTransactionalServices.put(beanName, beanMethod.getName());
                        return false;
                    }
                }

            }
            return true;
        }
        return true;
    }

    /*
     * Recursively seek evidence that a Transaction is necessary by examining the fields of the given class and recursively
     * investigating its superclass.
     */
    private boolean shouldHaveTransaction(Class<? extends Object> beanClass) {
        Boolean result = seenClasses.get(beanClass);
        if (result != null)
            return result;
        if (seenClasses.containsKey(beanClass)) {
            return false;
        }
        seenClasses.put(beanClass, null); // placeholder to avoid recursive problems
        result = Boolean.FALSE;
        for (Field field : beanClass.getDeclaredFields()) {
            String name = field.getType().getName();
            String fieldTypeName = field.getType().getName();
            if (fieldTypeName.startsWith("org.apache.ojb")) {
                if (!fieldTypeName.equals("org.apache.ojb.broker.metadata.DescriptorRepository")) {
                    result = Boolean.TRUE;
                }
            }
            if (name.startsWith("org.kuali")) {
                if (name.contains("Dao")) {
                    result = Boolean.TRUE;
                }
                if (result == false) {
                    result = shouldHaveTransaction(field.getType());
                }
            }
        }
        if (result == false) {
            if (beanClass.getSuperclass() != null && beanClass.getSuperclass().getName().startsWith("org.kuali")) {
                result = shouldHaveTransaction(beanClass.getSuperclass());
            }
        }
        seenClasses.put(beanClass, result);
        return result;
    }
}
