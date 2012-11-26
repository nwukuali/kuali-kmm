/**
 *
 */
package org.kuali.ext.mm.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.service.MMDocumentUtilService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kew.actionitem.ActionItem;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rponraj
 *
 */
public class MMDocumentUtilServiceImpl implements MMDocumentUtilService {

    /**
     * @see org.kuali.ext.mm.service.MMDocumentUtilService#getProfileForUser(java.lang.String)
     */
    public Profile getProfileForUser(String id) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("principalName", id);

        List<Profile> data = (List<Profile>)MMServiceLocator.getBusinessObjectService().findMatching(Profile.class, fieldValues);

        if(!MMUtil.isCollectionEmpty(data))
            return data.get(0);

        return null;
    }

    /**
     * @see org.kuali.ext.mm.service.MMDocumentUtilService#getProfileOfLoggedinUser()
     */
    public Profile getProfileOfLoggedinUser() {
        String id = GlobalVariables.getUserSession().getPerson().getPrincipalId();
        return getProfileForUser(id);
    }

    /**
     * @see org.kuali.ext.mm.service.MMDocumentUtilService#isDocInMyActionList(java.lang.String, java.lang.String)
     */
    public boolean isDocInMyActionList(String docNumber, String principalId) {
        if(StringUtils.isEmpty(docNumber) || StringUtils.isEmpty(principalId))
            return false;

        Collection<ActionItem> actionsItems = KEWServiceLocator.getActionListService().findByWorkflowUserDocumentId(principalId, docNumber);

        return !MMUtil.isCollectionEmpty(actionsItems);
    }

    /**
     * @see org.kuali.ext.mm.service.MMDocumentUtilService#isDocInMyActionList(java.lang.String)
     */
    public boolean isDocInMyActionList(String docNumber) {
        Person luser = GlobalVariables.getUserSession().getPerson();
        String principalId = luser.getPrincipalId();

        if(StringUtils.isEmpty(docNumber) || StringUtils.isEmpty(principalId))
            return false;



        Collection<ActionItem> actionsItems = KEWServiceLocator.getActionListService().findByWorkflowUserDocumentId(principalId, docNumber);

        return !MMUtil.isCollectionEmpty(actionsItems);
    }

}
