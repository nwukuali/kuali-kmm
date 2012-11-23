package org.kuali.ext.mm.businessobject.lookup;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.KualiLookupableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.Properties;



public class OrderDetailForReturnsLookupableImpl extends KualiLookupableImpl{

	/**
     *
     */
    private static final long serialVersionUID = 6035150860134930190L;

    @Override
    public String getCreateNewUrl() {
	    String url = "";
//        if((getLookupableHelperService()).allowsNewOrCopyAction(KimConstants.KimUIConstants.KIM_GROUP_DOCUMENT_TYPE_NAME)){
//            Properties parameters = new Properties();
            Properties params = new Properties();
//            parameters.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, KimTypeImpl.class.getName());
//            parameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, KRADConstants.PORTAL_ACTION);
//            parameters.put(KRADConstants.DOC_FORM_KEY, KimConstants.KimUIConstants.KIM_GROUP_DOCUMENT_SHORT_KEY);

	        params.setProperty(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.DOC_HANDLER_METHOD);
	        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
	        params.setProperty(MMConstants.CheckinDocument.ORDER_DOC_NUMBER,MMConstants.ReturnDocument.NEW_ORDER_DOCUMENT);
	        params.setProperty(KRADConstants.DOCUMENT_TYPE_NAME, MMConstants.CHECKIN_RETURNDOC_TYPE);
//	        String href = getKualiConfigurationService().getPropertyValueAsString(KRADConstants.APPLICATION_URL_KEY) + "/" +UrlFactory.parameterizeUrl(MMConstants.ReturnDocument.RETURN_DOCUMENT_RECEIVE_ACTION, params);
	        url = getCreateNewUrl(KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(KRADConstants.APPLICATION_URL_KEY) + "/" +
	        		UrlFactory.parameterizeUrl(MMConstants.ReturnDocument.RETURN_DOCUMENT_RECEIVE_ACTION, params));
            //String url = "lookup.do?businessObjectClassName=org.kuali.rice.kim.bo.types.impl.KimTypeImpl&returnLocation=portal.do&docFormKey="+KimConstants.KimUIConstants.KIM_ROLE_DOCUMENT_SHORT_KEY;
        return url;
	}


}
