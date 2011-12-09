package org.kuali.ext.mm.document.authorization;

import java.util.Set;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentAuthorizerBase;
import org.kuali.rice.kns.exception.AuthorizationException;
import org.kuali.rice.kns.util.GlobalVariables;


public class WorksheetDocumentAuthorizerBase extends TransactionalDocumentAuthorizerBase {

    @Override
    public Set<String> getDocumentActions(Document document, Person user, Set<String> documentActionsFromPresentationController) {
        Set<String> documentActions = super.getDocumentActions(document, user, documentActionsFromPresentationController);

        WorksheetCountDocument wdoc = (WorksheetCountDocument) document;
        if(wdoc.getWorksheetStatusCode().equals(MMConstants.WorksheetStatus.WORKSHEET_PRINTED)){
            if( !KIMServiceLocator
					.getIdentityManagementService().isAuthorized( GlobalVariables.getUserSession().getPrincipalId(),
							MMConstants.MM_NAMESPACE, MMConstants.WorksheetCountDocument.EDIT_WORKSHEET_PERMISSION,
							null,null)){

        			throw new AuthorizationException(user.getPrincipalName(), "Entering count", "Worksheet sheet");
        }
        }
         return documentActions;
    }

   }
