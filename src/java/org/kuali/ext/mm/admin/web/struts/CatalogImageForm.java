/**
 * 
 */
package org.kuali.ext.mm.admin.web.struts;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;
import org.kuali.rice.kns.web.struts.form.KualiForm;

/**
 * @author harsha07
 */
public class CatalogImageForm extends KualiForm {
    private static final long serialVersionUID = 256343463097741341L;
    private FormFile imageFile;
    private List<String> uploadedImages = new ArrayList<String>();

    /**
     * Gets the imageFile property
     * 
     * @return Returns the imageFile
     */
    public FormFile getImageFile() {
        return this.imageFile;
    }

    /**
     * Sets the imageFile property value
     * 
     * @param imageFile The imageFile to set
     */
    public void setImageFile(FormFile imageFile) {
        this.imageFile = imageFile;
    }

    /**
     * Gets the uploadedImages property
     * 
     * @return Returns the uploadedImages
     */
    public List<String> getUploadedImages() {
        return this.uploadedImages;
    }

    /**
     * Sets the uploadedImages property value
     * 
     * @param uploadedImages The uploadedImages to set
     */
    public void setUploadedImages(List<String> uploadedImages) {
        this.uploadedImages = uploadedImages;
    }


}
