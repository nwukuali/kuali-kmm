/**
 *
 */
package org.kuali.ext.mm.web.format;

/*
 * Copyright 2007-2008 The Kuali Foundation
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


import org.apache.commons.lang.StringUtils;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;
import org.kuali.rice.kns.web.comparator.CellComparatorHelper;
import org.kuali.rice.krad.util.KRADConstants;

import javax.servlet.jsp.PageContext;

public class ReorderColumnFormatter implements DisplaytagColumnDecorator {

    private boolean hasOneText(String[] vals){

        int index = 0;

        for (int i=0; i<vals.length; i++){
            if(StringUtils.isNotBlank(vals[i]))
                index++;
        }
        return index < 2;
    }

    /**
     * Empty values don't show up properly in HTML. So, the String "&nbsp;" is substituted for an empty or null value of cellValue
     * if mediaType is MediaTypeEnum.HTML. If mediaType is not {@link MediaTypeEnum.HTML} and cellValue is not null, then
     * <code>CellComparatorHelper.getSanitizedValue(cellValue.toString())</code> is returned.
     *
     * @param cellValue
     * @param pageContext
     * @param mediaType
     */
    public Object decorate(Object cellValue, PageContext pageContext, MediaTypeEnum mediaType) throws DecoratorException {
        Object decoratedOutput = null;
        if (null == cellValue) {
            decoratedOutput = MediaTypeEnum.HTML.equals(mediaType) ? "&nbsp" : KRADConstants.EMPTY_STRING;
        }

        //If a column resulting from lookup contains collection values, each of the collection entry
        //should be printed on one line (i.e. separated by a <BR>). If there is no entry in the
        //collection, then we'll just print an &nbsp for the column.
        else if (cellValue.toString().indexOf("[") == 0 && cellValue.toString().indexOf("]") > 0) {
            String cellContentToBeParsed = cellValue.toString().substring(1, cellValue.toString().indexOf("]"));
            if (StringUtils.isNotBlank(cellContentToBeParsed)) {
                String[] parsed = cellContentToBeParsed.split(",");
                decoratedOutput = new String();

                if(this.hasOneText(parsed)){
                    decoratedOutput = parsed[0];
                }else{
                for (int i=0; i<parsed.length; i++) {
                    decoratedOutput = decoratedOutput + parsed[i] + "<BR>";
                }
                }
            }
            else { //if the cellContentToBeParsed is blank
                decoratedOutput = "&nbsp";
            }
        }
        else {
            decoratedOutput = MediaTypeEnum.HTML.equals(mediaType) ? new StringBuffer(cellValue.toString().trim()).append("&nbsp;").toString() : CellComparatorHelper.getSanitizedStaticValue(cellValue.toString());
        }

        return decoratedOutput;
    }

}


