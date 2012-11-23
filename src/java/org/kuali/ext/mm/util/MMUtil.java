package org.kuali.ext.mm.util;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kns.datadictionary.BusinessObjectEntry;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.sql.Timestamp;
import java.util.*;


public class MMUtil {
    
    public static String formatNullString(String str) {                
        return str == null ? "" : str;
    }

    public static boolean isEmptyText(String str) {
        if (str == null || str.trim().length() < 1)
            return true;
        return false;
    }

    public static boolean isMapEmpty(Map mp) {
        if (mp == null || mp.size() < 1)
            return true;
        return false;
    }

    public static boolean isCollectionEmpty(Collection mp) {
        if (mp == null || mp.size() < 1)
            return true;
        return false;
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    public static Timestamp getSQLTimeStampForDate(Date date) {

        if (date == null)
            return null;

        return new Timestamp(date.getTime());
    }

    /**
     * This method returns the date for the passed year month day hours minutes seconds
     * 
     * @param year
     * @param month
     * @param day
     * @param hours
     * @param minutes
     * @param seconds
     * @return
     */
    public static Date getDateFor(int year, int month, int day, int hours, int minutes, int seconds) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);

        return cal.getTime();
    }

    public static Integer getDateDifferenceinDays(Date date1, Date date2) {

        if (date1 == null || date2 == null)
            return null;

        long diff = 0L;

        diff = Math.abs(date1.getTime() - date2.getTime());
        long oneDayseconds = getSecondsForDay();
        Integer result = (int) (diff / oneDayseconds);
        return result;
    }

    public static java.sql.Date getCurrentSQLTime() {
        return new java.sql.Date(new Date().getTime());
    }

    public static long getSecondsForDay() {

        Date d1 = getDateFor(2009, 10, 10, 00, 00, 00);
        Date d2 = getDateFor(2009, 10, 10, 23, 59, 59);
        long seconds = d2.getTime() - d1.getTime();
        return seconds;
    }

    public static String getFileName() {
        String tag = UUID.randomUUID().toString();
        String fileName = MMConstants.WorksheetCountDocument.REPORT_ZIP_FILE_NAME + tag + ".pdf";
        return fileName;
    }

    public static String getFileNameForPackingList() {
        String tag = UUID.randomUUID().toString();
        String fileName = MMConstants.WorksheetCountDocument.REPORT_ZIP_FILE_NAME + tag + ".pdf";
        return fileName;
    }

    public static List<Date> getDateRangeForThirdFiscalYear() {
        List<Date> result = new ArrayList<Date>(0);
        return result;
    }

    public static KualiDecimal subtractKualiDecimal(KualiDecimal first, KualiDecimal second) {

        if ((second == null && first == null) || (second.isZero() && first.isZero()))
            return KualiDecimal.ZERO;

        if (second.isZero())
            return first;

        if (first == null)
            return second.negated();

        return first.subtract(second);
    }

    public static MMDecimal subtractMMDecimal(MMDecimal first, MMDecimal second) {

        if ((second == null && first == null) || (second.isZero() && first.isZero()))
            return MMDecimal.ZERO;

        if (second.isZero())
            return first;

        if (first == null)
            return second.negated();

        return first.subtract(second);
    }
    
    public static void postMMObjectLockingError(Class objectClass, String objectIdentifier, String lockingDocId, String errorPath) {
        String objectLabel = ((BusinessObjectEntry)KNSServiceLocator.getDataDictionaryService()
                .getDataDictionary().getDictionaryObjectEntry(objectClass.getName())).getObjectLabel();
        Properties parameters = new Properties();
        parameters.put(KRADConstants.PARAMETER_DOC_ID, lockingDocId);
        parameters.put(KRADConstants.PARAMETER_COMMAND, KRADConstants.METHOD_DISPLAY_DOC_SEARCH_VIEW);
        String blockingUrl = UrlFactory.parameterizeUrl(
					KRADServiceLocator.getKualiConfigurationService()
                    .getPropertyValueAsString(KRADConstants.WORKFLOW_URL_KEY) + "/" + KRADConstants.DOC_HANDLER_ACTION, parameters);
        GlobalVariables.getMessageMap().putError(
                errorPath,
                MMKeyConstants.ERROR_MM_OBJECT_LOCKED, 
                objectLabel, 
                objectIdentifier, 
                blockingUrl,
                lockingDocId);
    }
}
