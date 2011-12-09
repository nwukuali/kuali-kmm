/**
 *
 */
package org.kuali.ext.mm.document.authorization;

import java.util.Map;

import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentAuthorizerBase;
import org.kuali.rice.kns.util.ObjectUtils;

/**
 * @author harsha07
 */
public class ReturnDocumentAuthorizer extends TransactionalDocumentAuthorizerBase {
    /**
     * @see org.kuali.rice.kns.document.authorization.DocumentAuthorizerBase#addRoleQualification(org.kuali.rice.kns.bo.BusinessObject,
     *      java.util.Map)
     */
    @Override
    protected void addRoleQualification(BusinessObject businessObject,
            Map<String, String> attributes) {
        super.addRoleQualification(businessObject, attributes);
        ReturnDocument returnDoc = null;
        if (ReturnDocument.class.isAssignableFrom(businessObject.getClass())) {
            returnDoc = (ReturnDocument) businessObject;
        }
        if (ObjectUtils.isNotNull(returnDoc) && ObjectUtils.isNotNull(returnDoc.getOrderDocument())) {
            String chartOfAccountsCode = returnDoc.getOrderDocument().getChartOfAccountsCode();
            String organizationCode = returnDoc.getOrderDocument().getOrganizationCode();
            attributes.put("chartOfAccountsCode", chartOfAccountsCode == null ? ""
                    : chartOfAccountsCode);
            attributes.put("organizationCode", organizationCode == null ? "" : organizationCode);
        }
    }
}
