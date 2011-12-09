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

import java.util.Date;

import javax.xml.namespace.QName;

import org.kuali.ext.mm.integration.service.FinancialUniversityDateService;
import org.kuali.kfs.sys.businessobject.UniversityDate;
import org.kuali.kfs.sys.service.UniversityDateService;

/**
 * @author harsha07
 */
public class KfsUniversityDateServiceAdaptor extends KfsServiceAdaptor<UniversityDateService>
        implements FinancialUniversityDateService {

    /**
     * @param serviceName
     */
    public KfsUniversityDateServiceAdaptor(QName serviceName) {
        super(serviceName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialUniversityDateService#getCurrentFiscalPeriod()
     */
    public String getCurrentFiscalPeriod() {
        UniversityDate currentUniversityDate = getService().getCurrentUniversityDate();
        if (currentUniversityDate != null) {
            return currentUniversityDate.getUniversityFiscalAccountingPeriod();
        }
        return null;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialUniversityDateService#getCurrentFiscalYear()
     */
    public Integer getCurrentFiscalYear() {
        UniversityDate currentUniversityDate = getService().getCurrentUniversityDate();
        if (currentUniversityDate != null) {
            return currentUniversityDate.getUniversityFiscalYear();
        }
        return null;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialUniversityDateService#getFirstDateOfFiscalYear(java.lang.Integer)
     */
    public Date getFirstDateOfFiscalYear(Integer fiscalYear) {
        return getService().getFirstDateOfFiscalYear(fiscalYear);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialUniversityDateService#getLastDateOfFiscalYear(java.lang.Integer)
     */
    public Date getLastDateOfFiscalYear(Integer fiscalYear) {
        return getService().getLastDateOfFiscalYear(fiscalYear);
    }

}
