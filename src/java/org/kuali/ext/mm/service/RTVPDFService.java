package org.kuali.ext.mm.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.kuali.ext.mm.businessobject.ReturnDetail;

public interface RTVPDFService {

    /**
     * @return a new document for the pdf list
     */
    public Object createDocument();


    /**
     * Adds the given lines to the document as a RTV
     *
     * @param returnOrderLines - lines to be added as a RTV
     * @param messages - A string containing any messages to go in the footer of the packing list
     */
    public void addReturnLineToList(List<ReturnDetail> returnOrderLines, String messages);

    /**
     * Close document stream.
     *
     * @param document - a pdf document
     * @return the output buffer where the document is stored
     */
    public ByteArrayOutputStream closeDocument(Object document);

}
