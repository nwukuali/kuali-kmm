package org.kuali.ext.mm.document.authorization;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentAuthorizerBase;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.Set;


public class WorksheetDocumentAuthorizerBase extends TransactionalDocumentAuthorizerBase {

    @Override
    public Set<String> getDocumentActions(Document document, Person user, Set<String> documentActionsFromPresentationController) {
        Set<String> documentActions = super.getDocumentActions(document, user, documentActionsFromPresentationController);

        WorksheetCountDocument wdoc = (WorksheetCountDocument) document;
        if(wdoc.getWorksheetStatusCode().equals(MMConstants.WorksheetStatus.WORKSHEET_PRINTED)){
            if( !KimApiServiceLocator.getPermissionService().isAuthorized(GlobalVariables.getUserSession().getPrincipalId(),
							MMConstants.MM_NAMESPACE, MMConstants.WorksheetCountDocument.EDIT_WORKSHEET_PERMISSION,
							null)){

        			throw new AuthorizationException(user.getPrincipalName(), "Entering count", "Worksheet sheet");
        }
        }
         return documentActions;
    }

   }
