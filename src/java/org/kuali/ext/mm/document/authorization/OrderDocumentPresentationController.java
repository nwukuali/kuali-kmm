package org.kuali.ext.mm.document.authorization;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class OrderDocumentPresentationController extends
        TransactionalDocumentPresentationControllerBase {

    @Override
    public boolean canSendAdhocRequests(Document document) {
        return false;
    }

    @Override
    public boolean canBlanketApprove(Document document) {
        Map<String,String> permissionDetails = new HashMap<String, String>();
        permissionDetails.put("documentTypeName", "SORD");
        // check permission
        WorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();
        if ((workflowDocument.isInitiated() || workflowDocument.isSaved())
                && StringUtils.equals(workflowDocument.getInitiatorPrincipalId(), GlobalVariables
                        .getUserSession().getPrincipalId())
                && KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(
                        GlobalVariables.getUserSession().getPrincipalId(),
                        KimConstants.KIM_GROUP_WORKFLOW_NAMESPACE_CODE,
                        KimConstants.PermissionTemplateNames.BLANKET_APPROVE_DOCUMENT,
                        permissionDetails, null)) {
            return true;
        }
        return false;
    }

    @Override
    public Set<String> getEditModes(Document document) {
        Set<String> editModes = super.getEditModes(document);

        if (MMServiceLocator.getMMDocumentUtilService().isDocInMyActionList(
                document.getDocumentNumber()))
            editModes.add(MMConstants.ReturnDocEditMode.DOC_IN_MY_ACTIONLIST);

        return editModes;
    }

    @Override
    public boolean canCopy(Document document) {
        return false;
    }

}