/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.ext.mm.integration;

import java.io.Serializable;

/**
 * value object class which can hold details of a service that is exposed and visible to KMM application through its financial
 * system configuration
 */
public class ServiceNameInfo implements Serializable {
    private static final long serialVersionUID = -1914071281972722455L;
    private String name;
    private String serviceNameSpaceURI;
    private String localServiceName;

    /**
     * Gets the name attribute.
     *
     * @return Returns the name
     */

    public String getName() {
        return name;
    }


    /**
     * Sets the name attribute.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the serviceNameSpaceURI attribute.
     *
     * @return Returns the serviceNameSpaceURI
     */

    public String getServiceNameSpaceURI() {
        return serviceNameSpaceURI;
    }

    /**
     * Sets the serviceNameSpaceURI attribute.
     *
     * @param serviceNameSpaceURI The serviceNameSpaceURI to set.
     */

    public void setServiceNameSpaceURI(String serviceNameSpaceURI) {
        this.serviceNameSpaceURI = serviceNameSpaceURI;
    }

    /**
     * Gets the localServiceName attribute.
     *
     * @return Returns the localServiceName
     */

    public String getLocalServiceName() {
        return localServiceName;
    }

    /**
     * Sets the localServiceName attribute.
     *
     * @param localServiceName The localServiceName to set.
     */

    public void setLocalServiceName(String localServiceName) {
        this.localServiceName = localServiceName;
    }


}
