/**
 * 
 */
package org.kuali.ext.mm.admin.web.struts;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.kuali.ext.mm.b2b.cxml.types.CXML;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.rice.core.api.util.RiceConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.kns.web.struts.action.KualiAction;


/**
 * @author harsha07
 */
public class CxmlFileAction extends KualiAction {
    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiAction#checkAuthorization(org.apache.struts.action.ActionForm,
     *      java.lang.String)
     */
    @Override
    protected void checkAuthorization(ActionForm form, String methodToCall)
            throws AuthorizationException {
        if (CxmlUtil.isProductionEnvironment()) {
            throw new AuthorizationException(GlobalVariables.getUserSession().getPrincipalName(),
                "CXML Upload", "Production servers doesn't support this request.");
        }
    }

    public ActionForward postCxml(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        CxmlFileForm cxmlFileForm = (CxmlFileForm) form;
        FormFile cxmlFile = cxmlFileForm.getCxmlFile();
        if (cxmlFile != null && cxmlFile.getFileSize() > 0) {
            String cxmlReceiveUrl = KRADServiceLocator.getKualiConfigurationService()
                    .getPropertyValueAsString(MMKeyConstants.Shopping.SHOPPING_URL)
                    + "/" + ShopCartConstants.PunchOut.VENDOR_REQUEST_ACTION;
            CXML cxml = CxmlUtil.unmarshal(cxmlFile.getInputStream());
            CXML resp = CxmlUtil.sendRequest(cxml, cxmlReceiveUrl);
            StringWriter stringWriter = new StringWriter();
            CxmlUtil.marshal(resp, stringWriter);
            cxmlFileForm.setCxmlResponse(stringWriter.getBuffer().toString());
        }
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    public ActionForward close(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return mapping.findForward(KRADConstants.MAPPING_PORTAL);
    }


}
