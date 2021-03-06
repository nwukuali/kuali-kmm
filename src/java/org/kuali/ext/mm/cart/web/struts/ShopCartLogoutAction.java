/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.cart.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.common.sys.MMConstants;


/**
 * This class handles the logout. After logout it will do an external redirect to an url
 * specified by a config property (shopping.portal.logout.redirectUrl).
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */
public class ShopCartLogoutAction extends Action {

    /**
     * Invalidates the users session.
     * 
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String redirectString = null;        
//        redirectString = ConfigContext.getCurrentContextConfig().getProperty(ShopCartKeyConstants.LOGOFF_REDIRECT_URL_PROPERTY);
//      return new ActionForward(redirectString, true);
        
        request.getSession().invalidate();
        return mapping.findForward(MMConstants.MAPPING_BASIC);

    }

}
