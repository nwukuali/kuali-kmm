/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import java.io.Serializable;

import org.kuali.ext.mm.common.sys.MMConstants;


/**
 * @author harsha07
 */
public class BatchStatusVO implements Serializable {
    private static final long serialVersionUID = 2562295491350223832L;
    private boolean success;
    private String errorCode;
    private String errorMessage;
    private String log4jMessage;
    private String log4jHtmlMessage;

    /**
     * Gets the success property
     *
     * @return Returns the success
     */
    public boolean isSuccess() {
        return this.success;
    }

    /**
     * Sets the success property value
     *
     * @param success The success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the errorCode property
     *
     * @return Returns the errorCode
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * Sets the errorCode property value
     *
     * @param errorCode The errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets the errorMessage property
     *
     * @return Returns the errorMessage
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Sets the errorMessage property value
     *
     * @param errorMessage The errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the errorLog4jMessage property
     *
     * @return Returns the errorLog4jMessage
     */
    public String getLog4jMessage() {
        return this.log4jMessage;
    }

    /**
     * Sets the errorLog4jMessage property value
     *
     * @param errorLog4jMessage The errorLog4jMessage to set
     */
    public void setLog4jMessage(String errorLog4jMessage) {
        this.log4jMessage = errorLog4jMessage;
    }

    /**
     * Gets the log4jHtmlMessage property
     * @return Returns the log4jHtmlMessage
     */
    public String getLog4jHtmlMessage() {
        if(this.log4jMessage != null){
            return this.log4jMessage.replace(MMConstants.LF, "<br/>");
        }
        return this.log4jHtmlMessage;
    }

    /**
     * Sets the log4jHtmlMessage property value
     * @param log4jHtmlMessage The log4jHtmlMessage to set
     */
    public void setLog4jHtmlMessage(String log4jHtmlMessage) {
        this.log4jHtmlMessage = log4jHtmlMessage;
    }

}
