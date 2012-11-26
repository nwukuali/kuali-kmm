package org.kuali.ext.mm.businessobject.lookup;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.KualiLookupableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.Properties;



public class VendorOrderDetailForReturnsLookupableImpl extends KualiLookupableImpl{

	/**
     *
     */
    private static final long serialVersionUID = -8692692205414576138L;

    @Override
    public String getCreateNewUrl() {
	    String url = "";
            Properties params = new Properties();
	        params.setProperty(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.DOC_HANDLER_METHOD);
	        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
	        params.setProperty(MMConstants.CheckinDocument.ORDER_DOC_NUMBER,MMConstants.ReturnDocument.NEW_ORDER_DOCUMENT);
	        params.setProperty(KRADConstants.DOCUMENT_TYPE_NAME, MMConstants.CHECKIN_VENDOR_RETURNDOC_TYPE);
	        url = getCreateNewUrl(KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(KRADConstants.APPLICATION_URL_KEY) + "/" +
	        		UrlFactory.parameterizeUrl(MMConstants.ReturnDocument.VENDOR_RETURN_DOCUMENT_RECEIVE_ACTION, params));
        return url;
	}
}
