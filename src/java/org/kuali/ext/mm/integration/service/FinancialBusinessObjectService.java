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

package org.kuali.ext.mm.integration.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.bo.PersistableBusinessObject;

/**
 */
@SuppressWarnings("unchecked")
public interface FinancialBusinessObjectService {
    public void save(PersistableBusinessObject bo);

    public void save(List<? extends PersistableBusinessObject> businessObjects);

    public PersistableBusinessObject findByPrimaryKey(Class clazz, Map primaryKeys);

    public Collection findAll(Class clazz);

    public Collection findMatching(Class clazz, Map fieldValues);

    public int countMatching(Class clazz, Map fieldValues);

    public int countMatching(Class clazz, Map positiveFieldValues, Map negativeFieldValues);

    public Collection findMatchingOrderBy(Class clazz, Map fieldValues, String sortField,
            boolean sortAscending);

    public void delete(PersistableBusinessObject bo);

    public void delete(List<? extends PersistableBusinessObject> boList);

    public void deleteMatching(Class clazz, Map fieldValues);

    public BusinessObject getReferenceIfExists(BusinessObject bo, String referenceName);

    public <T> T findBySinglePrimaryKey(Class<T> clazz, Object primaryKey);
}
