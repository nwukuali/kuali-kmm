/**
 *
 */
package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.Profile;

/**
 * @author rponraj
 *
 */
public interface MMDocumentUtilService {

    public Profile getProfileOfLoggedinUser();

    public Profile getProfileForUser(String id);

    public boolean isDocInMyActionList(String docNumber, String principalId);

    public boolean isDocInMyActionList(String docNumber);
}
