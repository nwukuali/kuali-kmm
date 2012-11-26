package org.kuali.ext.mm.businessobject.options;

import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * generic base class for all the option finders All the subclasses should extend this class and should call the three argument
 * constructor from their no arg constructor with their corresponding values
 *
 * @author rponraj
 */
public class MMKeyValuesBase extends KeyValuesBase {

    private Class className = null;
    private String codeProperty = null, valProperty = null;
    private OptionsValueFinder optionsValueFinder = null;
    private Map<String, Object> param = new HashMap<String, Object>(0);

    /**
     * @param className fully qualified class
     * @param codeProperty property name for getting the code value from
     * @param valProperty
     */
    public MMKeyValuesBase(java.lang.Class className, String codeProperty, String valProperty) {
        this.className = className;
        this.codeProperty = codeProperty;
        this.valProperty = valProperty;
        this.optionsValueFinder = OptionsValueFinder.getInstance();
    }

    public MMKeyValuesBase(java.lang.Class className, String codeProperty, String valProperty,
            Map<String, Object> param) {
        this.className = className;
        this.codeProperty = codeProperty;
        this.valProperty = valProperty;
        this.param = param;
        this.optionsValueFinder = OptionsValueFinder.getInstance();
    }

    public List getKeyValues() {
        if (MMUtil.isMapEmpty(param))
            return optionsValueFinder.getKeyValue(className, codeProperty, valProperty);
        return optionsValueFinder.getKeyValue(className, codeProperty, valProperty, param);
    }

    public String getValue() {
        List<KeyValue> labels = optionsValueFinder.getKeyValue(className, codeProperty,
                valProperty);
        if (labels != null) {
            if (labels.size() > 1)
                return labels.get(1).getValue();
        }
        return "";
    }

}
