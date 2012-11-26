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

package org.kuali.ext.mm.integration.service.impl.kfs;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;

import javax.xml.namespace.QName;
import java.io.Serializable;

/**
 * Abstract base class for all KFS implemented adaptors
 *
 * @param <KfsService> Service that is used by this adaptor
 */
public abstract class KfsServiceAdaptor<KfsService> implements Serializable{
    protected QName qName;
    protected KfsService service;

    /**
     * Overrides default constructor so that a service adaptor can be created only with it is Service Name for lookup defined
     *
     * @param qName
     */
    public KfsServiceAdaptor(QName qName) {
        this.qName = qName;
    }


    /**
     * Gets the serviceName property
     *
     * @return Returns the serviceName
     */
    public QName getQName() {
        return this.qName;
    }


    /**
     * Sets the serviceName property value
     *
     * @param serviceName The serviceName to set
     */
    public void setQName(QName serviceName) {
        this.qName = serviceName;
    }


    @SuppressWarnings("unchecked")
    public KfsService getService() {
        if (this.service == null) {
            this.service = (KfsService) GlobalResourceLoader.getService(getQName());
        }
        return this.service;
    }
}
