/**
 *
 */
package org.kuali.ext.mm.sys.batch.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.upload.FormFile;
import org.kuali.ext.mm.businessobject.CatalogItemPending;
import org.kuali.ext.mm.businessobject.CatalogPendingDoc;

/**
 * @author rshrivas
 */
public interface CatalogPendingDocQueryService {

    public ArrayList<CatalogPendingDoc> loadCatalogPendingDoc(Date time);

    public String getCatalogCodeFromPendingDocNbr(String catalogPendingDocNbr);


    public void uploadCatalogPendingDoc(String catalogPendingDocNbr,
            ArrayList<CatalogItemPending> loadList, boolean endOfFile);

    public void uploadCatalogActionSave(String fDocNumber, String catalogCd, String catalogDesc,
            FormFile uploadedFile);

    public File createTempCatalogFile(String fdocNbr);
}