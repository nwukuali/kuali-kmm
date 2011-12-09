package org.kuali.ext.mm.document.authorization;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.util.KimConstants;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.workflow.service.KualiWorkflowDocument;


public class OrderDocumentPresentationController extends
        TransactionalDocumentPresentationControllerBase {

    @Override
    protected boolean canSendAdhocRequests(Document document) {
        return false;
    }

    @Override
    protected boolean canBlanketApprove(Document document) {
        AttributeSet permissionDetails = new AttributeSet();
        permissionDetails.put("documentTypeName", "SORD");
        // check permission
        KualiWorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();
        if ((workflowDocument.stateIsInitiated() || workflowDocument.stateIsSaved())
                && StringUtils.equals(workflowDocument.getInitiatorPrincipalId(), GlobalVariables
                        .getUserSession().getPrincipalId())
                && KIMServiceLocator.getIdentityManagementService().isAuthorizedByTemplateName(
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