/**
 * 
 */
package org.kuali.ext.mm.admin.web.struts;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.kuali.ext.mm.businessobject.CatalogImage;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMFileUtil;
import org.kuali.rice.core.api.util.RiceConstants;
import org.kuali.rice.kns.util.KNSGlobalVariables;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.kns.web.struts.action.KualiAction;
import org.kuali.rice.krad.util.KRADConstants;


/**
 * @author harsha07
 */
public class CatalogImageAction extends KualiAction {
    public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String imageDir = KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(
					"catalog.images.dir");
        File imageDirFile = new File(imageDir);
        // create if directory doesn't exist
        if (!imageDirFile.exists()) {
            imageDirFile.mkdirs();
        }
        CatalogImageForm catalogImageForm = (CatalogImageForm) form;
        catalogImageForm.getUploadedImages().clear();
        FormFile imageFile = catalogImageForm.getImageFile();
        if (imageFile != null && imageFile.getFileSize() > 0) {
            if (imageFile.getFileName().toLowerCase().endsWith(".zip")) {
                File zipFileName = new File(imageDir, imageFile.getFileName());
                ZipFile zipFile = null;
                try {
                    MMFileUtil.streamOut(imageFile.getInputStream(), new FileOutputStream(
                        zipFileName));
                    zipFile = new ZipFile(zipFileName);
                    Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry zipEntry = entries.nextElement();
                        if (!zipEntry.isDirectory()) {
                            File newImageFile = new File(imageDir, zipEntry.getName());
                            if (!newImageFile.exists()) {
                                // if new add to the database
                                saveImageToDb(imageDirFile, newImageFile);
                            }
                            MMFileUtil.streamOut(zipFile.getInputStream(zipEntry),
                                    new FileOutputStream(newImageFile));
                            catalogImageForm.getUploadedImages().add(zipEntry.getName());
                        }
                        else {
                            new File(imageDir, zipEntry.getName()).mkdirs();
                        }

                    }
                }
                finally {
                    if (zipFile != null) {
                        zipFile.close();
                    }
                    if (zipFileName.exists()) {
                        zipFileName.delete();
                    }
                }
            }
            else {
                File newImageFile = new File(imageDir, imageFile.getFileName());
                if (!newImageFile.exists()) {
                    // if new add to the database
                    saveImageToDb(imageDirFile, newImageFile);
                }
                MMFileUtil.streamOut(imageFile.getInputStream(), new FileOutputStream(new File(
                    imageDir, imageFile.getFileName())));
                catalogImageForm.getUploadedImages().add(imageFile.getFileName());
            }
            KNSGlobalVariables.getMessageList().add(MMKeyConstants.IMAGES_SAVED_SUCCESSFULLY,
							imageFile.getFileName());
            GlobalVariables.getUserSession().addObject("catalogUploadImages",
                    catalogImageForm.getUploadedImages());
        }

        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    /**
     * @param imageDir
     * @param zipEntry
     * @param newImageFile
     */
    private void saveImageToDb(File imageDir, File newImageFile) {
        CatalogImage catalogImage = new CatalogImage();
        catalogImage.setCatalogImageName(newImageFile.getName().length() > 30 ? newImageFile
                .getName().substring(0, 30) : newImageFile.getName());
        catalogImage.setCatalogImageUrl(newImageFile.getAbsolutePath().substring(
                imageDir.getAbsolutePath().length()).replace('\\', '/'));
        catalogImage.setActive(true);
        catalogImage.save();
    }

    public ActionForward close(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return mapping.findForward(KRADConstants.MAPPING_PORTAL);
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String imageName = request.getParameter("imageName");
        String imageDir = KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(
                "catalog.images.dir");
        File file = new File(imageDir, imageName);
        List<String> uploadedImages = (List<String>) GlobalVariables.getUserSession()
                .retrieveObject("catalogUploadImages");
        if (file.exists() && file.delete()) {
            deleteImageFromDb(imageDir, file);
            KNSGlobalVariables.getMessageList().add(MMKeyConstants.IMAGES_DELETED_SUCCESSFULLY,
                    imageName);
            uploadedImages.remove(imageName);
        }
        CatalogImageForm catalogImageForm = (CatalogImageForm) form;
        catalogImageForm.setUploadedImages(uploadedImages);
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    /**
     * @param imageDir
     * @param file
     */
    private void deleteImageFromDb(String imageDir, File file) {
        String imageurl = file.getAbsolutePath().substring(
                new File(imageDir).getAbsolutePath().length()).replace('\\', '/');
        BusinessObjectService bos = MMServiceLocator.getBusinessObjectService();
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("catalogImageUrl", imageurl);
        Collection<CatalogImage> matches = bos.findMatching(CatalogImage.class, fieldValues);
        for (CatalogImage img : matches) {
            img.delete();
        }
    }
}
