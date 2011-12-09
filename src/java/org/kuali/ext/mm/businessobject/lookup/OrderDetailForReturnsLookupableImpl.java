package org.kuali.ext.mm.businessobject.lookup;

import java.util.Properties;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.KualiLookupableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;



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
//            parameters.put(KNSConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, KimTypeImpl.class.getName());
//            parameters.put(KNSConstants.RETURN_LOCATION_PARAMETER, KNSConstants.PORTAL_ACTION);
//            parameters.put(KNSConstants.DOC_FORM_KEY, KimConstants.KimUIConstants.KIM_GROUP_DOCUMENT_SHORT_KEY);

	        params.setProperty(KNSConstants.DISPATCH_REQUEST_PARAMETER, KNSConstants.DOC_HANDLER_METHOD);
	        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
	        params.setProperty(MMConstants.CheckinDocument.ORDER_DOC_NUMBER,MMConstants.ReturnDocument.NEW_ORDER_DOCUMENT);
	        params.setProperty(KNSConstants.DOCUMENT_TYPE_NAME, MMConstants.CHECKIN_RETURNDOC_TYPE);
//	        String href = getKualiConfigurationService().getPropertyString(KNSConstants.APPLICATION_URL_KEY) + "/" +UrlFactory.parameterizeUrl(MMConstants.ReturnDocument.RETURN_DOCUMENT_RECEIVE_ACTION, params);
	        url = getCreateNewUrl(KNSServiceLocator.getKualiConfigurationService().getPropertyString(KNSConstants.APPLICATION_URL_KEY) + "/" +
	        		UrlFactory.parameterizeUrl(MMConstants.ReturnDocument.RETURN_DOCUMENT_RECEIVE_ACTION, params));
            //String url = "lookup.do?businessObjectClassName=org.kuali.rice.kim.bo.types.impl.KimTypeImpl&returnLocation=portal.do&docFormKey="+KimConstants.KimUIConstants.KIM_ROLE_DOCUMENT_SHORT_KEY;
        return url;
	}


}
