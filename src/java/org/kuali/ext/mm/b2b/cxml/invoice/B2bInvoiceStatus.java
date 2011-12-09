/*
 * Copyright 2011 The Kuali Foundation
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

package org.kuali.ext.mm.b2b.cxml.invoice;

import java.io.Serializable;

/**
 * @author harsha07
 */
public class B2bInvoiceStatus implements Serializable{
    private static final long serialVersionUID = 7640768108435583968L;
    private boolean valid = true;
    private String responseCode;
    private String responeText;
    private String message;

    public B2bInvoiceStatus() {
    }
    public B2bInvoiceStatus(boolean valid, String responseCode, String responeText, String message) {
        super();
        this.valid = valid;
        this.responseCode = responseCode;
        this.responeText = responeText;
        this.message = message;
    }

    /**
     * Gets the valid property
     * 
     * @return Returns the valid
     */
    public boolean isValid() {
        return this.valid;
    }

    /**
     * Sets the valid property value
     * 
     * @param valid The valid to set
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Gets the responseCode property
     * 
     * @return Returns the responseCode
     */
    public String getResponseCode() {
        return this.responseCode;
    }

    /**
     * Sets the responseCode property value
     * 
     * @param responseCode The responseCode to set
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * Gets the responeText property
     * 
     * @return Returns the responeText
     */
    public String getResponeText() {
        return this.responeText;
    }

    /**
     * Sets the responeText property value
     * 
     * @param responeText The responeText to set
     */
    public void setResponeText(String responeText) {
        this.responeText = responeText;
    }

    /**
     * Gets the message property
     * 
     * @return Returns the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message property value
     * 
     * @param message The message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
