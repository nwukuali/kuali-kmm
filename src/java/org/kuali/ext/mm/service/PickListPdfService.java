package org.kuali.ext.mm.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public interface PickListPdfService {

	/**
	 * @param fileName
	 * @return a new document for the pdf ticket
	 */
	public Object createDocument(String fileName);

	/**
	 * Sets the pdf header columns that will be accessed in the onEndPage event
	 *
	 * @param leftColumn
	 * @param rightColumn
	 */
	public void setHeader(List<String> leftColumn, List<String> rightColumn);

	/**
	 * writes a zone section to the pdf
	 *
	 * @param document
	 * @param zoneCode
	 * @param zoneHeader
	 * @param zoneData
	 */
	public void writeZone(Object document, String zoneCode, List<String> zoneHeader, Map<String, List<String>> zoneData);

	/**
	 * Appends summary data to pdf document
	 *
	 * @param document
	 * @param lines
	 * @param orders
	 */
	public void completeDocument(Object document, int lines, int orders);

	/**
	 * Close document stream.
	 *
	 * @param document
	 * @return the output buffer where the document is stored
	 */
	public ByteArrayOutputStream closeDocument(Object document);

	/**
	 * Tells the document buffer to begin on a new page
	 *
	 * @param document
	 */
	public void nextPage(Object document);

}
