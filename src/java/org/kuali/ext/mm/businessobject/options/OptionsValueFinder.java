package org.kuali.ext.mm.businessobject.options;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.service.KeyValuesService;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OptionsValueFinder {
    private static final OptionsValueFinder instance = new OptionsValueFinder();

    private OptionsValueFinder() {
    }

    public static OptionsValueFinder getInstance() {
        return instance;
    }

    public List<KeyValue> getKeyValue(Class classVal, String codeProperty,
            String valProperty) {
        return this.getKeyValue(classVal, codeProperty, valProperty, null);
    }

    public List<KeyValue> getKeyValue(Class classVal, String codeProperty,
            String valProperty, Map<String, Object> param) {
        return getKeyValMap(classVal, codeProperty, valProperty, param);
    }

    private List<KeyValue> getKeyValMap(Class className, String codeProperty,
            String valProperty, Map<String, Object> param) {
        List<KeyValue> labels = null;
        List objects = new ArrayList();
        KeyValuesService keyValuesService = SpringContext.getBean(KeyValuesService.class);

        if (MMUtil.isMapEmpty(param))
            objects = (List) keyValuesService.findAll(className);
        else
            objects = (List) keyValuesService.findMatching(className, param);

        labels = constructKayLabels(objects, codeProperty, valProperty);

        return labels;

    }

    private List<KeyValue> constructKayLabels(List objects, String codeProperty,
            String valProperty) {
        List<KeyValue> labels = null;
        if (!MMUtil.isCollectionEmpty(objects))
            labels = new ArrayList<KeyValue>();
        else
            return new ArrayList<KeyValue>();
        labels
                .add(new ConcreteKeyValue(null,
                    MMConstants.OptionFinderParms.OPTION_FINDER_DEFAULT_LABEL));

        for (Object object : objects) {
            String code = (String) ObjectUtils.getPropertyValue(object,
							codeProperty);
            String codeDesc = (String) ObjectUtils.getPropertyValue(object,
                    valProperty);
            labels.add(new ConcreteKeyValue(code, codeDesc));
        }
        return labels;
    }
}
