/*
 * Copyright 2005-2008 The Kuali Foundation
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
package org.kuali.ext.mm.util;

import java.math.BigDecimal;

import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;


/**
 * This class converts NUMERIC fields from the database into KualiDecimal instances, and vice-versa.
 *
 *
 */

public class OjbMMDecimalFieldConversion implements FieldConversion {
    private static final long serialVersionUID = 2450111778124335242L;

    /**
     * @see FieldConversion#javaToSql(Object)
     */
    public Object javaToSql(Object source) {
        Object converted = source;

        if (source instanceof MMDecimal) {
            converted = ((MMDecimal) source).bigDecimalValue();
        }

        return converted;
    }

    /**
     * @see FieldConversion#sqlToJava(Object)
     */
    public Object sqlToJava(Object source) {
        Object converted = source;

        if (source instanceof BigDecimal) {
            converted = new MMDecimal((BigDecimal) source);
        }

        return converted;
    }
}
