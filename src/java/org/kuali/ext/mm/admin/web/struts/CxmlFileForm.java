/**
 * 
 */
package org.kuali.ext.mm.admin.web.struts;

import org.apache.struts.upload.FormFile;
import org.kuali.rice.kns.web.struts.form.KualiForm;

/**
 * @author harsha07
 */
public class CxmlFileForm extends KualiForm {
    private static final long serialVersionUID = 256343463097741341L;
    private FormFile cxmlFile;
    private String cxmlResponse;

    /**
     * Gets the cxmlFile property
     * 
     * @return Returns the cxmlFile
     */
    public FormFile getCxmlFile() {
        return this.cxmlFile;
    }

    /**
     * Sets the cxmlFile property value
     * 
     * @param cxmlFile The cxmlFile to set
     */
    public void setCxmlFile(FormFile cxmlFile) {
        this.cxmlFile = cxmlFile;
    }

    /**
     * Gets the cxmlResponse property
     * 
     * @return Returns the cxmlResponse
     */
    public String getCxmlResponse() {
        if (this.cxmlResponse != null) {
            return this.cxmlResponse.replace("<", "&lt;").replace(">", "&gt;");
        }
        return this.cxmlResponse;
    }

    /**
     * Sets the cxmlResponse property value
     * 
     * @param cxmlResponse The cxmlResponse to set
     */
    public void setCxmlResponse(String cxmlResponse) {
        this.cxmlResponse = cxmlResponse;
    }


}
