package org.kuali.ext.mm.sys;

import org.kuali.ext.mm.util.MMDecimal;

import uk.ltd.getahead.dwr.ConversionException;
import uk.ltd.getahead.dwr.Converter;
import uk.ltd.getahead.dwr.ConverterManager;
import uk.ltd.getahead.dwr.InboundContext;
import uk.ltd.getahead.dwr.InboundVariable;
import uk.ltd.getahead.dwr.Messages;
import uk.ltd.getahead.dwr.OutboundContext;
import uk.ltd.getahead.dwr.compat.BaseV10Converter;

/**
 * Converter for all MMDecimal
 *
 * @see org.kuali.rice.kns.util.KualiDecimal
 * @see org.kuali.rice.kns.util.KualiInteger
 */
public class MMDecimalConverter extends BaseV10Converter implements Converter {
    /**
     * @see uk.ltd.getahead.dwr.Converter#init(uk.ltd.getahead.dwr.DefaultConfiguration)
     */
    public void setConverterManager(ConverterManager config) {
    }

    /**
     * @see uk.ltd.getahead.dwr.Converter#convertInbound(java.lang.Class, java.util.List, uk.ltd.getahead.dwr.InboundVariable,
     *      uk.ltd.getahead.dwr.InboundContext)
     */
    public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws ConversionException {
        String value = iv.getValue();
        try {

            if(paramType == MMDecimal.class) {
            	return new MMDecimal(value.trim());
            }

            throw new ConversionException(Messages.getString("BigNumberConverter.NonPrimitive", paramType.getName())); //$NON-NLS-1$
        }
        catch (NumberFormatException ex) {
            throw new ConversionException(Messages.getString("BigNumberConverter.FormatError", value, paramType.getName()), ex); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see uk.ltd.getahead.dwr.Converter#convertOutbound(java.lang.Object, java.lang.String, uk.ltd.getahead.dwr.OutboundContext)
     */
    public String convertOutbound(Object object, String varname, OutboundContext outctx) {
        return "var " + varname + "=" + object.toString() + ';'; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
