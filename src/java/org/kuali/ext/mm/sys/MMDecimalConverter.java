package org.kuali.ext.mm.sys;

import org.directwebremoting.ConversionException;
import org.directwebremoting.extend.Converter;
import org.directwebremoting.extend.ConverterManager;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.OutboundContext;
import org.kuali.ext.mm.util.MMDecimal;
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
		@Override
    public Object convertInbound(Class<?> paramType, InboundVariable inboundVariable) throws ConversionException {
        String value = inboundVariable.getValue();
        try {

            if(paramType == MMDecimal.class) {
            	return new MMDecimal(value.trim());
            }
						//TODO: NWU - Find an alternative to dwr Messages
            throw new ConversionException(paramType,"BigNumberConverter.NonPrimitive"/*, aClass.getName()*/); //$NON-NLS-1$
//            throw new ConversionException(Messages.getString("BigNumberConverter.NonPrimitive", aClass.getName())); //$NON-NLS-1$
        }
        catch (NumberFormatException ex) {
					//TODO: NWU - Find an alternative to dwr Messages
            throw new ConversionException(paramType, "BigNumberConverter.FormatError"); //$NON-NLS-1$
//            throw new ConversionException(Messages.getString("BigNumberConverter.FormatError", value, aClass.getName()), ex); //$NON-NLS-1$
        }
    }



	public String convertOutbound(Object o, String varname, OutboundContext outboundContext) throws ConversionException {
		return "var " + varname + "=" + o.toString() + ';'; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
