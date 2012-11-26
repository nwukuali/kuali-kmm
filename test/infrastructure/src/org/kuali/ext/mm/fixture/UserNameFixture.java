/*
 * Copyright 2006-2007 The Kuali Foundation.
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
package org.kuali.ext.mm.fixture;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;


public enum UserNameFixture {

    NO_SESSION, // This is not a user name. It is a Sentinal value telling KualiTestBase not to create a session. (It's needed
                // because null is not a valid default for the ConfigureContext annotation's session element.)
    admin;  //Current administrative user for MM-Dev

    static {
        // Assert.assertEquals(KualiUser.SYSTEM_USER, kuluser.toString());
    }

    public Person getPerson() {
        return SpringContext.getBean(PersonService.class).getPersonByPrincipalName(toString());
    }
}

