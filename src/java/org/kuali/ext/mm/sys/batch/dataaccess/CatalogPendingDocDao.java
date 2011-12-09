/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.upload.FormFile;
import org.kuali.ext.mm.businessobject.CatalogItemPending;
import org.kuali.ext.mm.businessobject.CatalogPendingDoc;

/**
 * @author rshrivas
 */
public interface CatalogPendingDocDao {

    public ArrayList<CatalogPendingDoc> getCatalogPendingDocs(Date time);

    public String getCatalogCodeFromPendingDocNbr(String catalogPendingDocNbr);

    public void uplaodCatalogPendingDoc(String catalogPendingDocNbr,
            ArrayList<CatalogItemPending> loadList, boolean endOfFile);

    public void uploadCatalogActionSave(String fDocNumber, String catalogCd, String catalogDesc,
            FormFile uploadedFile);

    public File createTempCatalogFile(String fdocNbr);

}
