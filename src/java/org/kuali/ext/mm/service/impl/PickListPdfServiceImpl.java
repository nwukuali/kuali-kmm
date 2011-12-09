package org.kuali.ext.mm.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.PickListPdfService;
import org.kuali.rice.kns.service.KualiConfigurationService;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.lowagie.text.pdf.draw.VerticalPositionMark;


public class PickListPdfServiceImpl implements PickListPdfService {
	protected static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PickListServiceImpl.class);

	private List<String> leftHeaderColumn;
	private List<String> rightHeaderColumn;

	private ByteArrayOutputStream pdfContent;

	public PickListPdfServiceImpl() {
		leftHeaderColumn = new ArrayList<String>();
		rightHeaderColumn = new ArrayList<String>();
	}

	private class EndPage extends PdfPageEventHelper {

		private final int HEADER_MARGIN = 20;

		 /**
	     * @see com.lowagie.text.pdf.PdfPageEventHelper#onEndPage(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
	     */
	    @Override
        public void onEndPage(PdfWriter writer, Document document) {

			Rectangle page = document.getPageSize();

			PdfPTable header = new PdfPTable(3);
			Chunk title = new Chunk(SpringContext.getBean(KualiConfigurationService.class).getPropertyString(MMKeyConstants.PickTicket.HEADER_TITLE), new Font(Font.BOLD, 14));

			try {
				float[] widths = {45, 25, 30};
				header.setWidths(widths);
				header.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
				header.getDefaultCell().setBorder(0);
				header.getDefaultCell().setBorderWidthBottom(1);
				header.getDefaultCell().setBorderWidthRight(1);
				header.addCell(new Phrase(title));

				header.getDefaultCell().setBorder(0);
				header.addCell("");
				header.addCell("");

				Font headerFont = new Font(Font.NORMAL, 10);
				for(int i=0; i < (leftHeaderColumn.size() > rightHeaderColumn.size() ? leftHeaderColumn.size() : rightHeaderColumn.size()); i++) {
					header.getDefaultCell().setColspan(2);
					if(leftHeaderColumn.size() > i)
						header.addCell(new Phrase(new Chunk(leftHeaderColumn.get(i), headerFont)));
					else
						header.addCell(new Phrase(new Chunk("", headerFont)));
					if(rightHeaderColumn.size() > i)
						header.addCell(new Phrase(new Chunk(rightHeaderColumn.get(i), headerFont)));
					else
						header.addCell(new Phrase(new Chunk("", headerFont)));

				}
				header.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
				header.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight() - HEADER_MARGIN, writer.getDirectContent());
			}
			catch (Exception e) {
				throw new ExceptionConverter(e);
			}
	    }

	}

	/**
	 * @see org.kuali.ext.mm.service.PickListPdfService#createDocument(java.lang.String)
	 */
	public Object createDocument(String fileName) {
		Document doc = new Document(PageSize.LETTER, 20, 20, 138, 20);

		try {
			LOG.info("Creating PDF Document: " + fileName);
			pdfContent = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(doc, pdfContent);
			writer.setPageEvent(new EndPage());
			doc.open();

			VerticalPositionMark separator = new LineSeparator();
			doc.add(separator);
		}
		catch (Exception e) {
			throw new ExceptionConverter(e);
		}

		return doc;
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListPdfService#completeDocument(java.lang.Object, int, int)
	 */
	public void completeDocument(Object document, int lines, int orders) {
		Document doc = (Document)document;
		try {
			Paragraph totals = new Paragraph(new Chunk("Total Lines: " + lines + "    Total Orders: " + orders, new Font(Font.HELVETICA, 9)));
			totals.setAlignment(Element.ALIGN_CENTER);
			doc.add(totals);
		}
		catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListPdfService#closeDocument(java.lang.Object)
	 */
	public ByteArrayOutputStream closeDocument(Object document) {
		Document doc = (Document)document;
		try {
			doc.close();
		}
		catch (Exception e) {
			throw new ExceptionConverter(e);
		}

		return pdfContent;
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListPdfService#nextPage(java.lang.Object)
	 */
	public void nextPage(Object document) {
		Document doc = (Document)document;

		try {
			doc.add(Chunk.NEXTPAGE);
			VerticalPositionMark separator = new LineSeparator();
			doc.add(separator);
		}
		catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListPdfService#setHeader(java.util.List, java.util.List)
	 */
	public void setHeader(List<String> leftColumn, List<String> rightColumn) {
		this.leftHeaderColumn = leftColumn;
		this.rightHeaderColumn = rightColumn;
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListPdfService#writeZone(java.lang.Object, java.lang.String, java.util.List, java.util.Map)
	 */
	public void writeZone(Object document, String zoneCode, List<String> zoneHeader, Map<String, List<String>> zoneData) {
		Document doc = (Document)document;
		Rectangle page = doc.getPageSize();

		try {
			Paragraph zoneLabel = new Paragraph("Zone - " + zoneCode);
			zoneLabel.setAlignment(Element.ALIGN_CENTER);
			zoneLabel.getFont().setStyle(Font.BOLD);
			zoneLabel.getFont().setSize(10);

			Font zoneHeaderFont = new Font(Font.HELVETICA, 10);
			PdfPTable zoneTable = new PdfPTable(8);
			zoneTable.getDefaultCell().setPaddingBottom(10);
			zoneTable.getDefaultCell().setPaddingTop(5);
			zoneTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			zoneTable.setSpacingBefore(5);
			zoneTable.setSpacingAfter(5);

			// Format cell and add Zone label
			zoneTable.getDefaultCell().setColspan(8);
			zoneTable.getDefaultCell().setBorderWidth(0);
			zoneTable.addCell(zoneLabel);

			//	Format cell and add header info
			zoneTable.getDefaultCell().setBorderWidth(1);
			zoneTable.getDefaultCell().setColspan(1);
			for(String zoneHeaderCell : zoneHeader) {
				zoneTable.addCell(new Phrase(new Chunk(zoneHeaderCell, zoneHeaderFont)));
			}

			zoneTable.setHeaderRows(2);

			float[] widths = {25, 6, 5, 30, 5, 11, 11, 7};
			zoneTable.setWidthPercentage(widths, page);
			zoneTable.setWidthPercentage(100);

			Font listFont = new Font(Font.HELVETICA, 9);

			for(int i=0; i < zoneData.get(zoneHeader.get(0)).size(); i++) {
				for(String zoneHeaderCell : zoneHeader) {
					zoneTable.addCell(new Phrase(new Chunk(zoneData.get(zoneHeaderCell).get(i), listFont)));
				}
			}
			doc.add(zoneTable);

		}
		catch (Exception e) {
			throw new ExceptionConverter(e);
		}

	}

}
