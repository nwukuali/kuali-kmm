/*
 * Copyright 2007 The Kuali Foundation.
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
package org.kuali.ext.mm.web;

import edu.yale.its.tp.cas.auth.provider.WatchfulPasswordHandler;

public class KualiPasswordHandler extends WatchfulPasswordHandler {
	protected static org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(KualiPasswordHandler.class);

	/**
	 * Authenticates the given username/password pair, returning true on success
	 * and false on failure.
	 */
	@Override
    public boolean authenticate(javax.servlet.ServletRequest request,
			String username, String password) {
		return true;

	}
}
