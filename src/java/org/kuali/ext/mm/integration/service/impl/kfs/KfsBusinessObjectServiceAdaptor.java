/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.ext.mm.integration.service.impl.kfs;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemConfiguration;
import org.kuali.ext.mm.integration.service.FinancialBusinessObjectService;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.service.BusinessObjectService;


/**
 * @author harsha07
 */
@SuppressWarnings("unchecked")
public class KfsBusinessObjectServiceAdaptor extends KfsServiceAdaptor<BusinessObjectService>
        implements FinancialBusinessObjectService {
    /**
     * @param qname
     */
    public KfsBusinessObjectServiceAdaptor(QName qname) {
        super(qname);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#countMatching(java.lang.Class, java.util.Map)
     */
    public int countMatching(Class clazz, Map fieldValues) {
        return getService().countMatching(getTargetClass(clazz), fieldValues);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#countMatching(java.lang.Class, java.util.Map,
     *      java.util.Map)
     */
    public int countMatching(Class clazz, Map positiveFieldValues, Map negativeFieldValues) {
        return getService().countMatching(getTargetClass(clazz), positiveFieldValues,
                negativeFieldValues);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#delete(org.kuali.rice.kns.bo.PersistableBusinessObject)
     */
    public void delete(PersistableBusinessObject bo) {
        getService().delete(bo);

    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#delete(java.util.List)
     */
    public void delete(List<? extends PersistableBusinessObject> boList) {
        getService().delete(boList);

    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#deleteMatching(java.lang.Class, java.util.Map)
     */
    public void deleteMatching(Class clazz, Map fieldValues) {
        getService().deleteMatching(getTargetClass(clazz), fieldValues);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#findAll(java.lang.Class)
     */
    public Collection findAll(Class clazz) {
        return getService().findAll(getTargetClass(clazz));
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#findByPrimaryKey(java.lang.Class, java.util.Map)
     */
    public PersistableBusinessObject findByPrimaryKey(Class clazz, Map primaryKeys) {
        return getService().findByPrimaryKey(getTargetClass(clazz), primaryKeys);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#findMatching(java.lang.Class, java.util.Map)
     */
    public Collection findMatching(Class clazz, Map fieldValues) {
        return getService().findMatching(getTargetClass(clazz), fieldValues);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#findMatchingOrderBy(java.lang.Class, java.util.Map,
     *      java.lang.String, boolean)
     */
    public Collection findMatchingOrderBy(Class clazz, Map fieldValues, String sortField,
            boolean sortAscending) {
        return getService().findMatchingOrderBy(getTargetClass(clazz), fieldValues, sortField,
                sortAscending);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#getReferenceIfExists(org.kuali.rice.kns.bo.BusinessObject,
     *      java.lang.String)
     */
    public BusinessObject getReferenceIfExists(BusinessObject bo, String referenceName) {
        return getService().getReferenceIfExists(bo, referenceName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#save(org.kuali.rice.kns.bo.PersistableBusinessObject)
     */
    public void save(PersistableBusinessObject bo) {
        getService().save(bo);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#save(java.util.List)
     */
    public void save(List<? extends PersistableBusinessObject> businessObjects) {
        getService().save(businessObjects);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialBusinessObjectService#findBySinglePrimaryKey(java.lang.Class,
     *      java.lang.Object)
     */
    public <T> T findBySinglePrimaryKey(Class<T> clazz, Object primaryKey) {
        return getService().findBySinglePrimaryKey(getTargetClass(clazz), primaryKey);
    }

    /**
     * @param <T>
     * @param clazz
     * @return
     */
    private <T> Class<T> getTargetClass(Class<T> clazz) {
        FinancialSystemConfiguration config = SpringContext
                .getBean(FinancialSystemConfiguration.class);
        Class<T> target = config.getIntegrationClassMap().get(clazz);
        if (target == null) {
            return clazz;
        }
        return target;
    }

}
