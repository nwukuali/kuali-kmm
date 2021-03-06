/*
 * Copyright 2004 Jonathan M. Lehr
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 * MODIFIED BY THE KUALI FOUNDATION
 */
// begin Kuali Foundation modification
package org.kuali.ext.mm.web.format;
// import order changed, and java.util.Calendar, org.kuali.KeyConstants and org.kuali.rice.KNSServiceLocator added
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;

import org.kuali.rice.kns.service.DateTimeService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.RiceKeyConstants;
import org.kuali.rice.kns.web.format.FormatException;
import org.kuali.rice.kns.web.format.Formatter;

/**
 * begin Kuali Foundation modification
 * This class is used to format Date objects.
 * end Kuali Foundation modification
 */
public class TimestampFormatter extends Formatter {
    /**
     * 
     */
    private static final long serialVersionUID = 7306593640310080831L;
    // begin Kuali Foundation modification
    // serialVersionUID changed from 1L

    private static DateTimeService dateTimeService;
    // end Kuali Foundation modification

	// begin Kuali Foundation modification
	// static variables DATE_ERROR_KEY and PARSE_MSG removed
	// method public String getErrorKey() removed
	// end Kuali Foundation modification

	// begin Kuali Foundation modification
	// added this method
    /**
     *
     * For a given user input date, this method returns the exact string the user entered after the last slash. This allows the
     * formatter to distinguish between ambiguous values such as "/06" "/6" and "/0006"
     *
     * @param date
     * @return
     */
    private String verbatimYear(String date) {
        String result = "";

        int pos = date.lastIndexOf("/");
        if (pos >= 0) {
            result = date.substring(pos);
        }

        return result;
    }
    // end Kuali Foundation modification


    /**
     * Unformats its argument and return a java.util.Date instance initialized with the resulting string.
     *
     * @return a java.util.Date intialized with the provided string
     */
    protected Object convertToObject(String target) {
    	// begin Kuali Foundation modification
        try {
            Timestamp result = getDateTimeService().convertToSqlTimestamp(target);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(result);
            if (calendar.get(Calendar.YEAR) < 1000 && verbatimYear(target).length() < 4) {
                throw new FormatException("illegal year format", RiceKeyConstants.ERROR_DATE, target);
            }
            return result;
        }
        catch (ParseException e) {
            throw new FormatException("parsing", RiceKeyConstants.ERROR_DATE, target, e);
        }
        // end Kuali Foundation modification
    }

    /**
     * Returns a string representation of its argument, formatted as a date with the "MM/dd/yyyy" format.
     *
     * @return a formatted String
     */
    public Object format(Object value) {
        if (value == null) {
            return null;
        }
        // begin Kuali Foundation modification
        if ("".equals(value)) {
            return null;
        }
        return getDateTimeService().toDateTimeString((java.util.Date)value);
        // end Kuali Foundation modification
    }


    public static DateTimeService getDateTimeService() {
        if ( dateTimeService == null ) {
            dateTimeService = KNSServiceLocator.getDateTimeService();
        }
        return dateTimeService;
    }

    /**
     * This method is invoked to validate a date string using the KNS Service
     * DateTimeService.
     *
     * @param dateString
     * @return
     */
    public boolean validate(String dateString) {
        boolean isValid=false;

        DateTimeService service=getDateTimeService();
        try {
            service.convertToSqlTimestamp(dateString);
            isValid=true;
        } catch (Exception e) {

        }

        return isValid;

    }

}