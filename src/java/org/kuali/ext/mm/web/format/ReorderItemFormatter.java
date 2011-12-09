package org.kuali.ext.mm.web.format;

import org.kuali.rice.kns.web.format.Formatter;
/**
 * @author rponraj
 *
 */
public class ReorderItemFormatter extends Formatter {

    /**
     *
     */
    private static final long serialVersionUID = -7074790343667464697L;
    private static final String CHECKED_CHECK_BOX = "<center><input type=checkbox checked disabled=true/></center>";
    private static final String UNCHECKED_CHECK_BOX = "<center><input type=checkbox disabled=true /></center>";
    /**
     * Converts the given true/false String into a Boolean.
     */
    @Override
    protected Object convertToObject(String target) {
        return Boolean.valueOf(target);
    }

    /**
     * Converts the given Boolean into a boolean String.
     */
    @Override
    public Object format(Object value) {
        Object formatted = value;
        if ((value != null) && (value instanceof Boolean)) {

            if((Boolean) value)
                formatted = CHECKED_CHECK_BOX ;
            else
                formatted = UNCHECKED_CHECK_BOX;
        }

        if ((value != null) && (value instanceof String)) {
            if(((String)value).trim().equalsIgnoreCase("yes") ||
                    ((String)value).trim().equalsIgnoreCase("y") ||
                    ((String)value).trim().equalsIgnoreCase("1"))
                formatted = CHECKED_CHECK_BOX ;
            else
                formatted = UNCHECKED_CHECK_BOX;

        }

        return formatted;
    }
}
