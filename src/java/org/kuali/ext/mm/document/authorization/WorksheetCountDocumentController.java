package org.kuali.ext.mm.document.authorization;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kew.actionitem.ActionItem;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.Collection;
import java.util.Set;


public class WorksheetCountDocumentController extends
        TransactionalDocumentPresentationControllerBase {
    @Override
    public boolean canSendAdhocRequests(Document document) {
        return false;
    }

    @Override
    public Set<String> getDocumentActions(Document doc) {
        WorksheetCountDocument wdoc = (WorksheetCountDocument) doc;
        String docNumber = wdoc.getDocumentNumber();
        Set<String> lisActions = super.getDocumentActions(doc);
        boolean canDisplay = true;
        if (wdoc != null) {
            if (wdoc.getWorksheetStatusCode().equals(MMConstants.WorksheetStatus.WORKSHEET_PRINTED)
                    || wdoc.getWorksheetStatusCode().equals(
                            MMConstants.WorksheetStatus.WORKSHEET_REPRINTED)) {

                if (KimApiServiceLocator.getPermissionService().isAuthorized(
									GlobalVariables.getUserSession().getPrincipalId(),
									MMConstants.MM_NAMESPACE, "Edit Worksheet", null)) {
                    canDisplay = true;
                }
                else {
                    canDisplay = false;
                }

            }
            else if (wdoc.getWorksheetStatusCode().equals(
                    MMConstants.WorksheetStatus.WORKSHEET_ENTERED)) {

                if (isDocInMyActionList(docNumber))
                    canDisplay = true;
                else
                    canDisplay = false;

            }

        }
        if (canDisplay) {
            if (!lisActions.contains(MMConstants.CountWorksheetEditMode.STOCK_ITEMS_DISPLAY_ENTRY))
                lisActions.add(MMConstants.CountWorksheetEditMode.STOCK_ITEMS_DISPLAY_ENTRY);
        }
        else {
            if (lisActions.contains(MMConstants.CountWorksheetEditMode.STOCK_ITEMS_DISPLAY_ENTRY))
                lisActions.remove(MMConstants.CountWorksheetEditMode.STOCK_ITEMS_DISPLAY_ENTRY);

        }
        return lisActions;
    }


    private boolean isDocInMyActionList(String docNumber) {

        if (StringUtils.isEmpty(docNumber))
            return false;

        Person luser = GlobalVariables.getUserSession().getPerson();

        String principalId = luser.getPrincipalId().trim();

        Collection<ActionItem> actionsItems = KEWServiceLocator.getActionListService()
                .findByWorkflowUserDocumentId(principalId,docNumber);

        return !MMUtil.isCollectionEmpty(actionsItems);
    }

}
