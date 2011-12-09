package org.kuali.ext.mm.businessobject.options;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.service.KeyValuesService;


public class OptionsValueFinder {
    private static final OptionsValueFinder instance = new OptionsValueFinder();

    private OptionsValueFinder() {
    }

    public static OptionsValueFinder getInstance() {
        return instance;
    }

    public List<KeyLabelPair> getKeyLabelPair(Class classVal, String codeProperty,
            String valProperty) {
        return this.getKeyLabelPair(classVal, codeProperty, valProperty, null);
    }

    public List<KeyLabelPair> getKeyLabelPair(Class classVal, String codeProperty,
            String valProperty, Map<String, Object> param) {
        return getKeyValMap(classVal, codeProperty, valProperty, param);
    }

    private List<KeyLabelPair> getKeyValMap(Class className, String codeProperty,
            String valProperty, Map<String, Object> param) {
        List<KeyLabelPair> labels = null;
        List objects = new ArrayList();
        KeyValuesService keyValuesService = SpringContext.getBean(KeyValuesService.class);

        if (MMUtil.isMapEmpty(param))
            objects = (List) keyValuesService.findAll(className);
        else
            objects = (List) keyValuesService.findMatching(className, param);

        labels = constructKayLabels(objects, codeProperty, valProperty);

        return labels;

    }

    private List<KeyLabelPair> constructKayLabels(List objects, String codeProperty,
            String valProperty) {
        List<KeyLabelPair> labels = null;
        if (!MMUtil.isCollectionEmpty(objects))
            labels = new ArrayList<KeyLabelPair>();
        else
            return new ArrayList<KeyLabelPair>();
        labels
                .add(new KeyLabelPair(null,
                    MMConstants.OptionFinderParms.OPTION_FINDER_DEFAULT_LABEL));

        for (Object object : objects) {
            String code = (String) org.kuali.rice.kns.util.ObjectUtils.getPropertyValue(object,
                    codeProperty);
            String codeDesc = (String) org.kuali.rice.kns.util.ObjectUtils.getPropertyValue(object,
                    valProperty);
            labels.add(new KeyLabelPair(code, codeDesc));
        }
        return labels;
    }
}
