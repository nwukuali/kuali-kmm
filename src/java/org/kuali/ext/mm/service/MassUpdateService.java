package org.kuali.ext.mm.service;

import java.io.File;
import java.util.Map;

import org.kuali.ext.mm.document.MassUpdateDocument;
import org.kuali.ext.mm.sys.valueobject.MassUpdateUploadSummary;
import org.kuali.ext.mm.util.MMDecimal;


public interface MassUpdateService {
    
    /**
     * Validates and summarizes the data file. 
     * 
     * @param uploadFile
     * @param errorPath
     * @return A MassUpdateUploadSummary object containing the errors, temp file, and total line count of the upload file.
     */
    public MassUpdateUploadSummary loadAgreementMassUpdateFile(File uploadFile, String errorPath);


    /**
     * @param massUpdateDocument
     * @return A map of each warehouse and the total value of its change in cost 
     */
    public Map<String, MMDecimal> processMassUpdateDocument(MassUpdateDocument massUpdateDocument);
}
