package org.kuali.ext.mm.businessobject.lookup;

import java.util.Properties;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.KualiLookupableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;



public class VendorOrderDetailForReturnsLookupableImpl extends KualiLookupableImpl{

	/**
     *
     */
    private static final long serialVersionUID = -8692692205414576138L;

    @Override
    public String getCreateNewUrl() {
	    String url = "";
            Properties params = new Properties();
	        params.setProperty(KNSConstants.DISPATCH_REQUEST_PARAMETER, KNSConstants.DOC_HANDLER_METHOD);
	        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
	        params.setProperty(MMConstants.CheckinDocument.ORDER_DOC_NUMBER,MMConstants.ReturnDocument.NEW_ORDER_DOCUMENT);
	        params.setProperty(KNSConstants.DOCUMENT_TYPE_NAME, MMConstants.CHECKIN_VENDOR_RETURNDOC_TYPE);
	        url = getCreateNewUrl(KNSServiceLocator.getKualiConfigurationService().getPropertyString(KNSConstants.APPLICATION_URL_KEY) + "/" +
	        		UrlFactory.parameterizeUrl(MMConstants.ReturnDocument.VENDOR_RETURN_DOCUMENT_RECEIVE_ACTION, params));
        return url;
	}
}
